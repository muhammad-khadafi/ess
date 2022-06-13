package com.pusilkom.ess.dto.form.search;

/**
 * Created by ITF on 11/2/2018.
 */
public class EmployeeSalarySearchForm extends BaseTableSearchForm {
    private Integer employeeId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
