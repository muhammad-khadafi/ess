package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.EmployeeEducation;

import java.util.Date;

public class EducationCmd {
    private Integer id;
    private Integer employeeId;
    private String grade;
    private String institution;
    private String major;
    private String startYear;
    private String endYear;
    private String result;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public EmployeeEducation toEmployeeEducation() {
        EmployeeEducation edu = new EmployeeEducation();
        edu.setId(this.id);
        edu.setEmployeeId(this.employeeId);
        edu.setGrade(this.grade);
        edu.setInstitution(this.institution);
        edu.setMajor(this.major);
        edu.setStartYear(this.startYear);
        edu.setEndYear(this.endYear);
        edu.setResult(this.result);

        if(this.id == null){
            edu.setCreatedBy(this.createdBy);
            edu.setCreatedDate(this.createdDate);
        } else {
            edu.setCreatedBy(this.createdBy);
            edu.setCreatedDate(this.createdDate);
            edu.setUpdatedBy(this.updatedBy);
            edu.setUpdatedDate(this.updatedDate);
        }
        return edu;
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
}
