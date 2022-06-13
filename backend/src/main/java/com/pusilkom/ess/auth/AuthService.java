package com.pusilkom.ess.auth;

import com.google.common.collect.Lists;
import com.pusilkom.ess.dto.auth.LoginRequest;
import com.pusilkom.ess.model.*;
import com.pusilkom.ess.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fahri on 3/8/17.
 */
@Component
public class AuthService {

    @Autowired JwtService jwtService;

    @Autowired UserwebService userwebService;
    @Autowired AppService appService;
    @Autowired PermissionService permissionService;
    @Autowired RoleService roleService;

    final static String FAILED_MSG = "failed to authenticate";
    Logger log = LoggerFactory.getLogger(this.getClass());

    public UserAuthentication authenticate(UserAuthentication userAuthentication) throws Exception {

        String jwtToken = (String) userAuthentication.getCredentials();
        Userweb user = jwtService.verify(jwtToken);
        if (user == null) {
            throw new RuntimeException(FAILED_MSG);
        }

        userAuthentication.setAuthenticated(true);
        userAuthentication.setPrincipal(user);

        List<Long> listIdRole = roleService.getListIdRoleByIdUser(user.getId());
        userAuthentication.setAuthorities(this.getListSimpleGrantedAuthorityByListIdRole(listIdRole));

        return userAuthentication;
    }

    public AppAuthentication authenticate(AppAuthentication appAuthentication) {
        AppCredentials appCredentials = (AppCredentials) appAuthentication.getCredentials();

        App app = appService.getAppByKode(appCredentials.getKode());
        if (app == null) {
            throw new RuntimeException(FAILED_MSG);
        }

        if (app.getApiKey().equals(appCredentials.getApiKey())) {
            appAuthentication.setAuthenticated(true);
            appAuthentication.setPrincipal(app);

            List<Long> listIdRole = roleService.getListIdRoleByIdApp(app.getId());
            appAuthentication.setAuthorities(this.getListSimpleGrantedAuthorityByListIdRole(listIdRole));
        }

        return appAuthentication;
    }

    public List<SimpleGrantedAuthority> getListSimpleGrantedAuthorityByListIdRole(List<Long> listIdRole){
        List<SimpleGrantedAuthority> listAuthority = Lists.newArrayList();

        for (String kodePermission : permissionService.getSetKodePermissionByListIdRole(listIdRole)) {
            listAuthority.add(new SimpleGrantedAuthority(kodePermission));
        }

        return listAuthority;
    }


    public boolean checkLogin(LoginRequest loginRequest) {
        Userweb user = userwebService.getUserwebByUsername(loginRequest.getUsername());
        if (user == null) {
            return false;
        }

        return BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());
    }

    public Userweb getCurrentUserweb() {
        UserAuthentication userAuthentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();

        try {
            return jwtService.verify((String) userAuthentication.getCredentials());
        } catch (Exception e) {
            log.error("getCurrentUserweb : ", e);
            return null;
        }
    }
}
