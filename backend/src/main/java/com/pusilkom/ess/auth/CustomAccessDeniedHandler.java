package com.pusilkom.ess.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

/**
 * Created by fahri on 4/6/17.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestValidatorUtil rv;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.error("access denied : ", e);

        httpServletResponse.setStatus(SC_FORBIDDEN);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String message = null;
        if(e.getCause() != null) {
            message = e.getCause().getMessage();
        }
        if (message == null) {
            message = e.getMessage();
        }

        byte[] body = new ObjectMapper().writeValueAsBytes(rv.buildError(e, message));

        httpServletResponse.getOutputStream().write(body);
    }
}
