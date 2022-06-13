package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.UserwebHasRole;

public class UserWebHasRoleItem extends UserwebHasRole {
    private String usernameString;
    private String roleString;

    public String getUsernameString() {
        return usernameString;
    }

    public void setUsernameString(String usernameString) {
        this.usernameString = usernameString;
    }

    public String getRoleString() {
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }
}
