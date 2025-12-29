package com.nonisystems.jit.common.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonisystems.jit.common.dto.JwtUserDetails;
import com.nonisystems.jit.common.dto.User;
import com.nonisystems.jit.service.UserService;
import com.nonisystems.jit.service.props.UserSubjectProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtPayloadFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UserService userService;
    private final UserSubjectProperties userSubjectProperties;

    public JwtPayloadFilter(RedisTemplate<String, Object> redisTemplate, UserService userService, UserSubjectProperties userSubjectProperties) {
        this.redisTemplate = redisTemplate;
        this.userService = userService;
        this.userSubjectProperties = userSubjectProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        log.debug("path: {}", path);

        if ("/public/auth/user/init".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtPayload = request.getHeader("x-jwt-payload");
        if (jwtPayload != null && !jwtPayload.isEmpty()) {
            byte[] decodedPayload = Base64.getUrlDecoder().decode(jwtPayload);
            String payloadJson = new String(decodedPayload);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> claims = objectMapper.readValue(payloadJson, Map.class);

            String subject = (String) claims.get("sub");
            log.debug("subject: {}", subject);

            // Get user information using sub from Redis
            String key = this.userSubjectProperties.getPrefix() + subject;
            Boolean exists = this.redisTemplate.hasKey(key);
            if (exists) {
                Map<Object, Object> allEntries = this.redisTemplate.opsForHash().entries(key);
                if (!allEntries.isEmpty()) {
                    String userId = (String) allEntries.get("userId");
                    String role = (String) allEntries.get("role");
                    List<String> authorities = new ArrayList<>();
                    authorities.add(role);
                    log.debug("authorities: {}", authorities);

                    // Set authentication in SecurityContextHolder
                    List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    JwtUserDetails userDetails = new JwtUserDetails(userId, subject, grantedAuthorities);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, grantedAuthorities
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } else {
                // Get user information using sub from database
                User user = this.userService.getUserBySub(subject);
                if (user != null) {
                    // Save user information to Redis
                    Map<String, Object> redisMap = new HashMap<>();

                    String userId = user.getId();
                    log.debug("userId: {}", userId);
                    redisMap.put("userId", userId);

                    List<String> authorities = new ArrayList<>();
                    if (user.getRole() != null) {
                        String role = user.getRole().getName();
                        log.debug("role: {}", role);
                        redisMap.put("role", role);
                        authorities.add(role);
                    }
                    log.debug("authorities: {}", authorities);

                    // Update redis with user information
                    long ttlInSeconds = TimeUnit.MINUTES.toSeconds(Long.parseLong(this.userSubjectProperties.getTtl()));
                    log.debug("ttlInSeconds: {}", ttlInSeconds);
                    this.redisTemplate.opsForHash().putAll(key, redisMap);
                    this.redisTemplate.expire(key, ttlInSeconds, TimeUnit.SECONDS);

                    // Set authentication in SecurityContextHolder
                    List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    JwtUserDetails userDetails = new JwtUserDetails(userId, subject, grantedAuthorities);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, grantedAuthorities
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
