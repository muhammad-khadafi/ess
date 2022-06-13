package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.Userweb;

/**
 * Created by ITF on 2/12/2019.
 */
public class UserCmd extends Userweb {
    private String employeeName;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Userweb toUserWeb(){
        Userweb userweb = new Userweb();
        userweb.setId(this.getId());
        userweb.setUsername(this.getUsername());
        userweb.setPassword(this.getPassword());
        userweb.setSalt(this.getSalt());
        userweb.setFlagAktif(this.getFlagAktif());
        userweb.setEmployeeId(this.getEmployeeId());

        return userweb;
    }
}
