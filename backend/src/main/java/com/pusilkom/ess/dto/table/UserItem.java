package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.Userweb;

/**
 * Created by ITF on 2/11/2019.
 */
public class UserItem extends Userweb{

    private String employeeName;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
