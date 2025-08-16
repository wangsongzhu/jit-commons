package com.nonisystems.jit.common.config.util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class UUIDGenerator {

    public String generateUUID(String input) {
        UUID uuid1 = UUID.nameUUIDFromBytes((input + System.nanoTime()).getBytes());
        if (log.isDebugEnabled()) {
            log.debug("Generating UUID {} for {}", uuid1, input);
        }
        return uuid1.toString();
    }
}