package com.pusilkom.ess.dto.view;

import com.pusilkom.ess.model.EmployeeEducation;

public class EmployeeEducationDetail {
    private Integer id;
    private Integer employeeId;
    private String grade;
    private String institution;
    private String major;
    private String startYear;
    private String endYear;
    private String result;

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


    public EmployeeEducationDetail(EmployeeEducation employeeEducation){
        this.id = employeeEducation.getId();
        this.employeeId = employeeEducation.getEmployeeId();
        this.major = employeeEducation.getMajor();
        this.grade = employeeEducation.getGrade();
        this.institution = employeeEducation.getInstitution();
        this.startYear = employeeEducation.getStartYear();
        this.endYear = employeeEducation.getEndYear();
        this.result = employeeEducation.getResult();
    }
}
