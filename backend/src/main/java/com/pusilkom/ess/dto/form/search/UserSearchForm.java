package com.pusilkom.ess.dto.form.search;

/**
 * Created by ITF on 2/11/2019.
 */
public class UserSearchForm extends BaseTableSearchForm {

    private String username;
    private String employeeName;
    private String flagAktif;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getFlagAktif() {
        return flagAktif;
    }

    public void setFlagAktif(String flagAktif) {
        this.flagAktif = flagAktif;
    }
}
