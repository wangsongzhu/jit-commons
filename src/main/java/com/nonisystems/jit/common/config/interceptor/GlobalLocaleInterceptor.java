package com.nonisystems.jit.common.config.interceptor;

import com.alibaba.nacos.common.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@Slf4j
@Component
public class GlobalLocaleInterceptor implements HandlerInterceptor {

    /**
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  Object
     * @return true
     * @throws Exception IllegalStateException
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver == null) {
            throw new IllegalStateException("No LocaleResolver found.");
        }

        String lang = request.getParameter("lang");
        log.debug("preHandle lang: {}", lang);

        if (StringUtils.isEmpty(lang) || StringUtils.isBlank(lang)) {
            localeResolver.setLocale(request, response, Locale.JAPAN);
        } else {
            if (StringUtils.equalsIgnoreCase(lang, "en")) {
                localeResolver.setLocale(request, response, Locale.US);
            } else if (StringUtils.equalsIgnoreCase(lang, "ja")) {
                localeResolver.setLocale(request, response, Locale.JAPAN);
            }
        }

//        if (lang != null && !lang.isEmpty()) {
//            int index = lang.indexOf("_");
//            if (index > 0) {
//                lang = lang.substring(0, index);
//            }
//            Locale locale = Locale.forLanguageTag(lang);
//            log.debug("locale: {}", locale);
//            localeResolver.setLocale(request, response, locale);
//        } else {
//            localeResolver.setLocale(request, response, Locale.JAPAN);
//        }

        return true;
    }

//    public static void main(String [] args) {
//        String lang = "ja_JP";
//        int index = lang.indexOf("_");
//        if (index > 0) {
//            lang = lang.substring(0, index);
//        }
//
//        System.out.println(lang);
//
//        Locale locale = Locale.forLanguageTag(lang);
//        System.out.println("locale: " + locale);
//    }
}
