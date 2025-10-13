package com.nonisystems.jit.common.config.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class Util {

    /**
     * Get domain from url
     *
     * @param urlString url string
     * @return domain
     */
    public static String extractDomain(String urlString) {
        log.debug("urlString: {}", urlString);
        if (StringUtils.isBlank(urlString)) {
            return null;
        }
        try {
            URL urlObject = new URL(urlString);
            String domain = urlObject.getHost();
            if (!StringUtils.isBlank(domain)) {
                if (domain.toLowerCase().startsWith("www.")) {
                    return domain.substring(4);
                }
                log.debug("domain: {}", domain);
                return domain;
            }
        } catch (MalformedURLException e) {
            log.error("Invalid URL: " + urlString);
            return null;
        }
        return null;
    }
}
