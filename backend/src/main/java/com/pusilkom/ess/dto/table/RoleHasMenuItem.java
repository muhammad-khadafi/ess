package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.RoleHasMenu;

public class RoleHasMenuItem extends RoleHasMenu {
    private String roleString;
    private String menuString;

    public String getRoleString() {
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }

    public String getMenuString() {
        return menuString;
    }

    public void setMenuString(String menuString) {
        this.menuString = menuString;
    }
}
