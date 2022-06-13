package com.pusilkom.ess.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired RestValidatorUtil rv;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        log.info("commence");

        httpServletResponse.setStatus(SC_FORBIDDEN);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String message;
        if(e.getCause() != null) {
            message = e.getCause().getMessage();
        } else {
            message = e.getMessage();
        }

//        byte[] body = new ObjectMapper()
//                .writeValueAsBytes(Collections.singletonMap("error", message));

        byte[] body = new ObjectMapper().writeValueAsBytes(rv.buildError(e, message));

        httpServletResponse.getOutputStream().write(body);
    }
}
