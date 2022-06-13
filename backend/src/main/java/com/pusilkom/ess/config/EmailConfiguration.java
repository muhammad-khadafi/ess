package com.pusilkom.ess.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by fahri on 2/2/17.
 */
@Configuration
public class EmailConfiguration {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${email.from}") String from;
    @Value("${email.host}") String host;
    @Value("${email.port}") Integer port;
    @Value("${email.username}") String username;
    @Value("${email.password}") String password;
    @Value("${email.properties.mail.smtp.from}") String smtpFrom;

    @Bean
    public JavaMailSender javaMailSender() {
        log.info("[email.from] : {}", from);
        log.info("[email.host] : {}", host);
        log.info("[email.port] : {}", port);
        log.info("[email.username] : {}", username);
        log.info("[email.password] : {}", password);
        log.info("[email.properties.mail.smtp.from] : {}", smtpFrom);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.smtp.from", smtpFrom);
        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }
}
