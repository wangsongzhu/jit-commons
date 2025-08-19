package com.nonisystems.jit.common.config;

import com.nonisystems.jit.common.config.interceptor.GlobalLocaleInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.time.Duration;
import java.util.Locale;

@Configuration
public class LocaleInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver("language");
        cookieLocaleResolver.setDefaultLocale(Locale.JAPAN);
        cookieLocaleResolver.setCookieMaxAge(Duration.ofDays(7));
        return cookieLocaleResolver;
    }

    @Bean
    public GlobalLocaleInterceptor globalLocaleInterceptor() {
        return new GlobalLocaleInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalLocaleInterceptor()).addPathPatterns("/**");
    }
}
