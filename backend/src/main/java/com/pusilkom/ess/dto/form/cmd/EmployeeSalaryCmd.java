package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.EmployeeSalary;

import java.util.Date;

public class EmployeeSalaryCmd {
    private Integer id;
    private Integer employeeId;
    private Integer salary;
    private Boolean isActive;
    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;
    private Date createdDate;
    private Date updatedDate;
    private Integer createdBy;
    private Integer updatedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public EmployeeSalary toEmployeeSalary() {
        EmployeeSalary emp = new EmployeeSalary();
        emp.setId(this.id);
        emp.setEmployeeId(this.employeeId);
        emp.setSalary(this.salary);
        emp.setStartDate(this.startDate);
        emp.setEndDate(this.endDate);

        if(this.id == null){
            emp.setCreatedBy(this.createdBy);
            emp.setCreatedDate(this.createdDate);
        } else {
            emp.setCreatedBy(this.createdBy);
            emp.setCreatedDate(this.createdDate);
            emp.setUpdatedBy(this.updatedBy);
            emp.setUpdatedDate(this.updatedDate);
        }

        if(this.isActive == null || this.isActive == false){
            emp.setIsActive(false);
        } else {
            emp.setIsActive(true);
        }
        return emp;
    }
}
