package com.pusilkom.ess.auth;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

@Component
public class SecretKeyProvider {
    private static final String keyPath = "C:/Users/muhammad.khadafi/Documents/Project/ESS/jwt.key";
//    private static final String keyPath = "/home/dafi/jar/jwt.key";

    public byte[] getKey() throws URISyntaxException, IOException {

//        System.out.println(this.getClass().getResource("/jwt.key"));
//        return Files.readAllBytes(Paths.get(this.getClass().getResource("/jwt.key").toURI()));
//        return Files.readAllBytes(Paths.get(keyPath));
        InputStream in = getClass().getResourceAsStream("/jwt.key");
        byte[] key = IOUtils.toByteArray(in);
        return key;
    }
}
