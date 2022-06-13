package com.pusilkom.ess.controller;

import com.google.common.collect.Lists;
import com.pusilkom.ess.auth.AuthService;
import com.pusilkom.ess.auth.JwtService;
import com.pusilkom.ess.dto.auth.LoginRequest;
import com.pusilkom.ess.dto.auth.LoginResponse;
import com.pusilkom.ess.dto.auth.UserInfo;
import com.pusilkom.ess.model.Userweb;
import com.pusilkom.ess.service.InstitusiService;
import com.pusilkom.ess.service.MenuService;
import com.pusilkom.ess.service.RoleService;
import com.pusilkom.ess.service.UserwebService;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by fahri on 1/7/17.
 */
@RestController
public class AuthController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to authenticate";
    final static String ERROR_GET_MENU_MSG = "Failed to load menu";

    @Autowired JwtService jwtService;
    @Autowired AuthService authService;
    @Autowired UserwebService userwebService;
    @Autowired MenuService menuService;
    @Autowired InstitusiService institusiService;
    @Autowired RestValidatorUtil rv;
    @Autowired DebugUtil d;
    @Autowired
    RoleService roleService;

    @RequestMapping(value="/auth/login", method = RequestMethod.POST)
    public ResponseEntity getLogin(@RequestBody final LoginRequest loginRequest) throws Exception {
        log.info("*** LoginRequest : {}", d.toString(loginRequest));
        String salt = BCrypt.gensalt();
        String hashpwd = BCrypt.hashpw(loginRequest.getPassword(), salt);

        log.info("*** hashpwd : {} salt : {}", hashpwd, salt);

        ResponseEntity responseEntity = null;
        try {
            if (!authService.checkLogin(loginRequest)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(rv.buildError(new RuntimeException(), ERROR_MSG));
            }

            String idToken = jwtService.generateToken(loginRequest);

            UserInfo userInfo = userwebService.getUserInfoByUsername(loginRequest.getUsername());
            Long roleId = userInfo.getRoleId();
            List<Long> listIdRole = roleService.getListIdRoleByIdUser(userInfo.getId().longValue());
            Set<String> setKodeMenu = menuService.getSetKodeMenuByListIdRole(listIdRole);
            CommonUtil.setUserName(userInfo.getUsername());
            CommonUtil.setUserId(userInfo.getId());
            responseEntity =  ResponseEntity.ok(new LoginResponse(idToken, userInfo, roleId, Lists.newArrayList(setKodeMenu)));
        } catch (Exception e) {
            log.error("AUTH LOGIN", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value="/auth/mlogin", method = RequestMethod.POST)
    public ResponseEntity getLoginMobile(@RequestBody final LoginRequest loginRequest) throws Exception {
        log.info("*** LoginRequest from mobile : {}", d.toString(loginRequest));
        String salt = BCrypt.gensalt();
        String hashpwd = BCrypt.hashpw(loginRequest.getPassword(), salt);

        log.info("*** from mobile hashpwd : {} salt : {}", hashpwd, salt);

        ResponseEntity responseEntity = null;
        try {
            if (!authService.checkLogin(loginRequest)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(rv.buildError(new RuntimeException(), ERROR_MSG));
            }

            String idToken = jwtService.generateMToken(loginRequest);

            UserInfo userInfo = userwebService.getUserInfoByUsername(loginRequest.getUsername());

            String IdTokenAndEmployeeId = "Bearer  " + idToken +"|"+ userInfo.getEmployeeId() + "|" + userInfo.getEmployeeName();

            responseEntity =  ResponseEntity.ok(IdTokenAndEmployeeId);
        } catch (Exception e) {
            log.error("AUTH LOGIN FROM MOBILE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value="/auth/menu", method = RequestMethod.GET)
    public ResponseEntity getAuthMenu() throws Exception {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = ResponseEntity.ok().body(menuService.constructMenuNodeByIdUserweb(2L));
        } catch (Exception e) {
            log.error("AUTH MENU", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_GET_MENU_MSG));
        }

        return responseEntity;
    }

    //WARN: This is PUBLIC API. Don't give @PreAuthorize
    @RequestMapping(value="/auth/menu/{id}", method = RequestMethod.GET)
    public ResponseEntity getAuthMenuWithRole(@PathVariable Long id) throws Exception {
        ResponseEntity responseEntity = null;
        try {
            Userweb userweb = authService.getCurrentUserweb();
            responseEntity = ResponseEntity.ok().body(menuService.constructRootMenuNodeByIdUserwebAndRoleId(userweb.getId(), id));
        } catch (Exception e) {
            log.error("AUTH MENU", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_GET_MENU_MSG));
        }

        return responseEntity;
    }

}
