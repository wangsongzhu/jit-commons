package com.nonisystems.jit.common.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonisystems.jit.common.dto.JwtUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class JwtPayloadFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtPayload = request.getHeader("x-jwt-payload");
        if (jwtPayload != null && !jwtPayload.isEmpty()) {
            byte[] decodedPayload = Base64.getUrlDecoder().decode(jwtPayload);
            String payloadJson = new String(decodedPayload);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> claims = objectMapper.readValue(payloadJson, Map.class);

            List<String> authorities = new ArrayList<>();
            Object rolesObject = claims.get("roles");
            if (rolesObject != null) {
                if (rolesObject instanceof List) {
                    authorities.addAll((List<String>) rolesObject);
                }
            }
            Object permissionsObject = claims.get("permissions");
            if (permissionsObject != null) {
                if (permissionsObject instanceof List) {
                    authorities.addAll((List<String>) permissionsObject);
                }
            }
            log.debug("authorities: {}", authorities);

            List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            String subject = (String) claims.get("sub");
            log.debug("subject: {}", subject);

            JwtUserDetails userDetails = new JwtUserDetails(claims, grantedAuthorities);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, grantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
