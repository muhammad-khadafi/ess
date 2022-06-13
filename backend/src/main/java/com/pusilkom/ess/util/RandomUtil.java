package com.pusilkom.ess.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by pusilkom on 3/24/2017.
 */
@Component
public class RandomUtil {
    Logger log = LoggerFactory.getLogger(this.getClass());

    public String shortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

}
