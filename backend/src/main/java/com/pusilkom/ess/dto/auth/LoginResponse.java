package com.pusilkom.ess.dto.auth;

import java.util.List;

/**
 * Created by fahri on 3/21/17.
 */
public class LoginResponse {
    String idToken;
    UserInfo userInfo;
    Long roleId;
    List<String> listMenu;

    public LoginResponse(String idToken, UserInfo userInfo, Long roleId, List<String> listMenu) {
        this.idToken = idToken;
        this.userInfo = userInfo;
        this.roleId = roleId;
        this.listMenu = listMenu;
    }

    public LoginResponse(String idToken, UserInfo userInfo) {
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<String> getListMenu() {
        return listMenu;
    }

    public void setListMenu(List<String> listMenu) {
        this.listMenu = listMenu;
    }
}
