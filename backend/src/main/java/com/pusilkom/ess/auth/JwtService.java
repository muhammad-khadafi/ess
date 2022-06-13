package com.pusilkom.ess.auth;

import com.pusilkom.ess.dto.auth.LoginRequest;
import com.pusilkom.ess.model.Userweb;
import com.pusilkom.ess.service.MenuService;
import com.pusilkom.ess.service.RoleService;
import com.pusilkom.ess.service.UserwebService;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.ZoneOffset.UTC;

@Component
public class JwtService {
    private static final String ISSUER = "com.pusilkom.ess";
    public static final String USERNAME = "username";

    public static final String CLAIM_USER = "user";
    public static final String CLAIM_AUTH = "auth";
    public static final String CLAIM_LIST_MENU = "list_menu";

    @Autowired UserwebService userwebService;
    @Autowired RoleService roleService;
    @Autowired MenuService menuService;

    @Autowired SecretKeyProvider secretKeyProvider;

    public String tokenFor(LoginRequest loginRequest) throws Exception {
        byte[] secretKey = secretKeyProvider.getKey();
        Date expiration = Date.from(LocalDateTime.now(UTC).plusHours(2).toInstant(UTC));

        // $$$ LIBRARY 1 $$$
////        Claims claims = new DefaultClaims();
////        claims.put("roles", Arrays.asList("R_USER", "R_ADD_USER"));
//
//        return Jwts.builder()
//                .setSubject(loginCredentials.getUsername())
//                .setExpiration(expiration)
//                .setIssuer(ISSUER)
////                .setClaims(claims)
//                .claim("roles", new ArrayList<String>(){{
//                    add("R_USER");
//                    add("R_ADD_USER");
//                }})
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();

//        // $$$ LIBRARY JOSE4J 1 $$$
//        EllipticCurveJsonWebKey senderJwk = EcJwkGenerator.generateJwk(EllipticCurves.P256);
//
//        // Give the JWK a Key ID (kid), which is just the polite thing to do
//        senderJwk.setKeyId("sender's key");
//
//        // Create the Claims, which will be the content of the JWT
//        JwtClaims claims = new JwtClaims();
//        claims.setIssuer("sender");  // who creates the token and signs it
//        claims.setAudience("receiver"); // to whom the token is intended to be sent
//        claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
//        claims.setGeneratedJwtId(); // a unique identifier for the token
//        claims.setIssuedAtToNow();  // when the token was issued/created (now)
//        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
//        claims.setSubject("subject"); // the subject/principal is whom the token is about
//        claims.setClaim("email","mail@example.com"); // additional claims/attributes about the subject can be added
//        List<String> groups = Arrays.asList("group-1", "other-group", "group-3");
//        claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array
//
//        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
//        // In this example it is a JWS nested inside a JWE
//        // So we first create a JsonWebSignature object.
//        JsonWebSignature jws = new JsonWebSignature();
//
//        // The payload of the JWS is JSON content of the JWT Claims
//        jws.setPayload(claims.toJson());
//
//        // The JWT is signed using the sender's private key
//        jws.setKey(senderJwk.getPrivateKey());
//
//        // Set the Key ID (kid) header because it's just the polite thing to do.
//        // We only have one signing key in this example but a using a Key ID helps
//        // facilitate a smooth key rollover process
//        jws.setKeyIdHeaderValue(senderJwk.getKeyId());
//
//        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
//        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);
//
//        // Sign the JWS and produce the compact serialization, which will be the inner JWT/JWS
//        // representation, which is a string consisting of three dot ('.') separated
//        // base64url-encoded parts in the form Header.Payload.Signature
//        String innerJwt = jws.getCompactSerialization();

        // $$$ JOSE4J 2 $$$

        JwtClaims claims = new JwtClaims();
        claims.setExpirationTimeMinutesInTheFuture(5);
        claims.setSubject("DEMO");
        claims.setIssuer("com.pusilkom.ess");
        claims.setAudience("com.pusilkom.ess");
        claims.setExpirationTimeMinutesInTheFuture(120); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setClaim("email","mail@example.com"); // additional claims/attributes about the subject can be added
        claims.setClaim("auth", true);

        List<String> roles = Arrays.asList("R_USER", "R_ADD_USER", "R_EDIT_USER");
        claims.setStringListClaim("roles", roles); // multi-valued claims work too and will end up as a JSON array

//        String secret = "secret";
//        Key key = new HmacKey(secret.getBytes("UTF-8"));

        Key key = new HmacKey(secretKey);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(key);
        jws.setDoKeyValidation(false); // relaxes the key length requirement

        String innerJwt = jws.getCompactSerialization();
        return innerJwt;
    }

//    public Optional<MinimalProfile> verify(String token) throws IOException, URISyntaxException {
//        byte[] secretKey = secretKeyProvider.getKey();
//        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//        return profileService.minimal(claims.getBody().getSubject().toString());
//    }

    public Userweb verify(String token) throws Exception {
//        Jwt<Header, Claims> headerClaimsJwt = Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token);

        byte[] secretKey = secretKeyProvider.getKey();
        Key key = new HmacKey(secretKey);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setKey(key);
        jws.setCompactSerialization(token);

        if(!jws.verifySignature()) return null;

        String payload = jws.getPayload();
        JwtClaims jwtClaims = JwtClaims.parse(payload);

        String username = jwtClaims.getClaimValue(CLAIM_USER, String.class);

        return userwebService.getUserwebByUsername(username);
    }

    public String generateToken(LoginRequest loginRequest) throws Exception {
        Userweb user = userwebService.getUserwebByUsername(loginRequest.getUsername());

        Date expiration = Date.from(LocalDateTime.now(UTC).plusHours(2).toInstant(UTC));

        JwtClaims claims = new JwtClaims();
        claims.setExpirationTimeMinutesInTheFuture(5);
        claims.setSubject("APP");
        claims.setIssuer("com.app");
        claims.setAudience("com.app");
        claims.setExpirationTimeMinutesInTheFuture(120); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setClaim(CLAIM_USER, user.getUsername()); // additional claims/attributes about the subject can be added
        claims.setClaim(CLAIM_AUTH, true);

        List<Long> listIdRole = roleService.getListIdRoleByIdUser(user.getId());
        Set<String> setKodeMenu = menuService.getSetKodeMenuByListIdRole(listIdRole);

        claims.setStringListClaim(CLAIM_LIST_MENU, new ArrayList<>(setKodeMenu)); // multi-valued claims work too and will end up as a JSON array

        byte[] secretKey = secretKeyProvider.getKey();
        Key key = new HmacKey(secretKey);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(key);
        jws.setDoKeyValidation(false); // relaxes the key length requirement

        String innerJwt = jws.getCompactSerialization();
        return innerJwt;
    }

    public String generateMToken(LoginRequest loginRequest) throws Exception {
        Userweb user = userwebService.getUserwebByUsername(loginRequest.getUsername());

        Date expiration = Date.from(LocalDateTime.now(UTC).plusHours(2).toInstant(UTC));

        JwtClaims claims = new JwtClaims();
        claims.setExpirationTimeMinutesInTheFuture(5);
        claims.setSubject("APP");
        claims.setIssuer("com.app");
        claims.setAudience("com.app");
        claims.setExpirationTimeMinutesInTheFuture(120); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setClaim(CLAIM_USER, user.getUsername()); // additional claims/attributes about the subject can be added
        claims.setClaim(CLAIM_AUTH, true);

        byte[] secretKey = secretKeyProvider.getKey();
        Key key = new HmacKey(secretKey);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(key);
        jws.setDoKeyValidation(false); // relaxes the key length requirement

        String innerJwt = jws.getCompactSerialization();
        return innerJwt;
    }
}
