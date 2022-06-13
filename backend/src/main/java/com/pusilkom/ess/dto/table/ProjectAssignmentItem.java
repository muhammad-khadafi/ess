package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.ProjectAssignment;

public class ProjectAssignmentItem extends ProjectAssignment {
    private String employeeName;
    private String projectName;
    private String roleName;
    private String levelName;
    private String isLeadString;
    private String startDateString;
    private String endDateString;
    private String isActiveString;
    private String loadString;
    private String projectStatus;
    private Integer months;
    private Integer years;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getIsLeadString() {
        return isLeadString;
    }

    public void setIsLeadString(String isLeadString) {
        this.isLeadString = isLeadString;
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

    public String getIsActiveString() {
        return isActiveString;
    }

    public void setIsActiveString(String isActiveString) {
        this.isActiveString = isActiveString;
    }

    public String getLoadString() {
        return loadString;
    }

    public void setLoadString(String loadString) {
        this.loadString = loadString;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
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
