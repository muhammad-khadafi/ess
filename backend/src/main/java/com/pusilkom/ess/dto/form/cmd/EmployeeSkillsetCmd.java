package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.EmployeeSkillset;

import java.util.Date;

public class EmployeeSkillsetCmd {
    private Integer id;
    private Integer employeeId;
    private Integer skillsetId;
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

    public Integer getSkillsetId() {
        return skillsetId;
    }

    public void setSkillsetId(Integer skillsetId) {
        this.skillsetId = skillsetId;
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

    public EmployeeSkillset toEmployeeSkillset() {
        EmployeeSkillset emp = new EmployeeSkillset();
        emp.setId(this.id);
        emp.setSkillsetId(this.skillsetId);
        emp.setEmployeeId(this.employeeId);
        if(this.id == null){
            emp.setCreatedBy(this.createdBy);
            emp.setCreatedDate(this.createdDate);
        } else {
            emp.setCreatedBy(this.createdBy);
            emp.setCreatedDate(this.createdDate);
            emp.setUpdatedBy(this.updatedBy);
            emp.setUpdatedDate(this.updatedDate);
        }
        return emp;
    }
}
