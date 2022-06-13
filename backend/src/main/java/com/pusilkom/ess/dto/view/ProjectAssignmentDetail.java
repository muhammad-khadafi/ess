package com.pusilkom.ess.dto.view;
import com.pusilkom.ess.model.ProjectAssignment;

public class ProjectAssignmentDetail extends ProjectAssignment {
    private String employeeName;
    private String projectName;
    private String roleName;
    private String levelName;
    private String isLeadString;

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
}
