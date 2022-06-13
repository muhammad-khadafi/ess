package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.EmployeeFamily;

import java.util.Date;

public class EmployeeFamilyCmd {
    private Integer id;
    private Integer employeeId;
    private String name;
    private String relation;
    private String birthPlace;
    private Date birthDate;
    private String birthDateString;
    private String phoneNumber;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdDate;
    private Date updatedDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDateString() {
        return birthDateString;
    }

    public void setBirthDateString(String birthDateString) {
        this.birthDateString = birthDateString;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public EmployeeFamily toEmployeeFamily() {
        EmployeeFamily emp = new EmployeeFamily();
        emp.setId(this.id);
        emp.setEmployeeId(this.employeeId);
        emp.setName(this.name);
        emp.setRelation(this.relation);
        emp.setBirthDate(this.birthDate);
        emp.setBirthPlace(this.birthPlace);
        emp.setPhoneNumber(this.phoneNumber);
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
