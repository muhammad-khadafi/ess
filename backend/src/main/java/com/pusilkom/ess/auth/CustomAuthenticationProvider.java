package com.pusilkom.ess.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired JwtService jwtService;
    @Autowired AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            log.info("CustomAuthenticationProvider.authenticate");

//            Optional<MinimalProfile> possibleProfile = jwtService.verify((String) authentication.getCredentials());
//            return new JwtAuthenticatedProfile(possibleProfile.get());

            if (authentication instanceof UserAuthentication) {
                UserAuthentication userAuthentication = (UserAuthentication) authentication;

                return authService.authenticate(userAuthentication);

            } else if (authentication instanceof AppAuthentication) {
                AppAuthentication appAuthentication = (AppAuthentication) authentication;

                return authService.authenticate(appAuthentication);
            }

            // TODO: sementara
            return authentication;
        } catch (Exception e) {
            log.error("authenticate : ", e);
            throw new CustomAuthenticationException("Failed to authenticate", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.info("supports auth : {}", authentication);
        return UserAuthentication.class.equals(authentication) || AppAuthentication.class.equals(authentication);
    }
}
