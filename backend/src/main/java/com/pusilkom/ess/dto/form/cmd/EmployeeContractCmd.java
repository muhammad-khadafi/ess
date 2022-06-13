package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.EmployeeContract;

import java.util.Date;

public class EmployeeContractCmd {
    private Integer id;
    private Integer employeeId;
    private String contractNumber;
    private Boolean isActive;
    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;
    private Date createdDate;
    private Date updatedDate;
    private Integer createdBy;
    private Integer updatedBy;
    private String signee;

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

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
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

    public String getSignee() {
        return signee;
    }

    public void setSignee(String signee) {
        this.signee = signee;
    }

    public EmployeeContract toEmployeeContract() {
        EmployeeContract emp = new EmployeeContract();
        emp.setId(this.id);
        emp.setEmployeeId(this.employeeId);
        emp.setContractNumber(this.contractNumber);
        emp.setSignee(this.signee);
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

        if(this.isActive == true){
            emp.setIsActive(true);
        } else {
            emp.setIsActive(false);
        }
        return emp;
    }
}
