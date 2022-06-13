package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.ProjectAssignment;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by arief on 10/31/18.
 */
public class ProjectAssignmentCmd extends ProjectAssignment {
    private String startDateString;
    private String endDateString;
    private Integer months;
    private Integer years;

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

    public ProjectAssignment toProjectAssignment() throws ParseException {
        SimpleDateFormat ssdf = new SimpleDateFormat ("dd-MM-yyyy");
        ProjectAssignment projectAssignment = new ProjectAssignment();
        projectAssignment.setId(this.getId());
        projectAssignment.setEmployeeId(this.getEmployeeId());
        projectAssignment.setProjectId(this.getProjectId());
        projectAssignment.setRoleId(this.getRoleId());
        projectAssignment.setIsLead(this.getIsLead());
        projectAssignment.setLoad(this.getLoad());
        projectAssignment.setStartDate(this.getStartDate());
        projectAssignment.setEndDate(this.getEndDate());
        projectAssignment.setLevelId(this.getLevelId());
        projectAssignment.setFlagActive(this.getFlagActive());
        projectAssignment.setCreatedBy(this.getCreatedBy());
        projectAssignment.setCreatedDate(this.getCreatedDate());
        projectAssignment.setUpdatedBy(this.getUpdatedBy());
        projectAssignment.setUpdatedDate(this.getUpdatedDate());

        if(!StringUtils.isEmpty(this.getStartDateString())) {
            projectAssignment.setStartDate(ssdf.parse(this.getStartDateString()));
        }

        if(!StringUtils.isEmpty(this.getEndDateString())) {
            projectAssignment.setEndDate(ssdf.parse(this.getEndDateString()));
        }

        return projectAssignment;
    }
}
