package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.Role;

/**
 * Created by ITF on 2/15/2019.
 */
public class RoleCmd extends Role {
    public Role toRole(){
        Role role = new Role();
        role.setId(this.getId());
        role.setKode(this.getKode());
        role.setLabel(this.getLabel());
        role.setKeterangan(this.getKeterangan());

        return role;
    }
}
