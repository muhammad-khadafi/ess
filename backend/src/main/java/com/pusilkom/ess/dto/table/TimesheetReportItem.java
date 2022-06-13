package com.pusilkom.ess.dto.table;

public class TimesheetReportItem {

    private String employeeName;
    private Integer employeeId;
    private Integer totalDay;
    private String startDate;
    private String endDate;
    private Boolean isAJ;

    public Boolean getAJ() {
        return isAJ;
    }

    public void setAJ(Boolean AJ) {
        isAJ = AJ;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }
}
