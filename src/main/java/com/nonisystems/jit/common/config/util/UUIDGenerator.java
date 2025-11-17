package com.nonisystems.jit.common.config.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
public class UUIDGenerator {

    /**
     * Generate 36 bytes UUID
     *
     * @param input input string
     * @return UUID
     */
    public String generateUUID(String input) {
        UUID uuid1 = UUID.nameUUIDFromBytes((input + System.nanoTime()).getBytes());
        if (log.isDebugEnabled()) {
            log.debug("Generating UUID {} for {}", uuid1, input);
        }
        return uuid1.toString();
    }

    /**
     * Generate 32 bytes Compact UUID
     *
     * @param input input string
     * @return Compact UUID
     */
    public String generateCompactUUID(String input) {
        UUID uuid = UUID.nameUUIDFromBytes((input + System.nanoTime()).getBytes(StandardCharsets.UTF_8));
        String compactUuid = uuid.toString().replace("-", "");
        if (log.isDebugEnabled()) {
            log.debug("Generating Compact UUID {} for {}", compactUuid, input);
        }
        return compactUuid;
    }
}