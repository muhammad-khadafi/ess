package com.pusilkom.ess.dto.form.search;

public class EmployeeAttendanceSearchForm extends BaseTableSearchForm {
    private Integer employeeId;
    private Integer currDate;
    private Integer dates;
    private Integer months;
    private Integer years;
    private Boolean isValid;

    public Boolean getIsValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getCurrDate() {
        return currDate;
    }

    public void setCurrDate(Integer currDate) {
        this.currDate = currDate;
    }

    public Integer getDates() {
        return dates;
    }

    public void setDates(Integer dates) {
        this.dates = dates;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }
}
