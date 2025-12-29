package com.nonisystems.jit.common.config;

import com.nonisystems.jit.common.config.filter.JwtPayloadFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ConditionalOnClass(EnableWebSecurity.class)
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtPayloadFilter jwtPayloadFilter;

    public SecurityConfig(JwtPayloadFilter jwtPayloadFilter) {
        this.jwtPayloadFilter = jwtPayloadFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/jwks.json").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 无状态会话
                )
                .addFilterBefore(this.jwtPayloadFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Disable registering JwtPayloadFilter to Servlet container
     *
     * @param filter JwtPayloadFilter
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<JwtPayloadFilter> registration(JwtPayloadFilter filter) {
        FilterRegistrationBean<JwtPayloadFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

}
