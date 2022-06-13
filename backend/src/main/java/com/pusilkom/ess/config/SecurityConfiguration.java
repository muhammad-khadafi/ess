package com.pusilkom.ess.config;

import com.pusilkom.ess.auth.CustomAccessDeniedHandler;
import com.pusilkom.ess.auth.CustomAuthFilter;
import com.pusilkom.ess.auth.CustomAuthenticationEntryPoint;
import com.pusilkom.ess.auth.CustomAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by fahri on 2/8/17.
 */
@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired CustomAuthFilter customAuthFilter;
    @Autowired CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    public void configure(AuthenticationManagerBuilder auth)  throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.csrf().ignoringAntMatchers("/api/auth/login");
        http.csrf().disable();

        String[] patterns = new String[] {
                "/api/auth/login",
                "/api/auth/mlogin",
                "/static/**/*",
                "/index.html",
                "/api/download/**/*",
                "/api/public/*",
                "/api/userGenerator/**/*",
                "/api/petty-cash/guest/add",
                "/api/petty-cash/upload/*",
                "/api/employee/all",
                "/api/funding-source/all"
        };

//        http.authorizeRequests()
//                .antMatchers(patterns).permitAll()
//                .antMatchers("/api/**/*").authenticated()
//                .and()
//                .addFilterBefore(customAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
//
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(patterns).permitAll()
                .antMatchers("/api/**/*").authenticated()
                .and()
                .addFilterBefore(customAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
