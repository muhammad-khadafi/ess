package com.pusilkom.ess.dto.form.search;

public class UserWebHasRoleSearchForm extends BaseTableSearchForm {
    Long idUserweb;
    Long idRole;
    String usernameString;

    public Long getIdUserweb() {
        return idUserweb;
    }

    public void setIdUserweb(Long idUserweb) {
        this.idUserweb = idUserweb;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getUsernameString() {
        return usernameString;
    }

    public void setUsernameString(String usernameString) {
        this.usernameString = usernameString;
    }
}
