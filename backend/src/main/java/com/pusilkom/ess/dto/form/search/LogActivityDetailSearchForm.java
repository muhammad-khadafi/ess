package com.pusilkom.ess.dto.form.search;

import java.util.Date;

public class LogActivityDetailSearchForm extends BaseTableSearchForm {
    private Integer projectAssignId;
    private Integer idEmployee;
    private Integer months;
    private Integer years;
    private Date dateBefore;
    private Date dateAfter;
    private Integer projectId;
    private Integer subProjectId;
    private String projectType;

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
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

    public Integer getProjectAssignId() {
        return projectAssignId;
    }

    public void setProjectAssignId(Integer projectAssignId) {
        this.projectAssignId = projectAssignId;
    }

    public Date getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(Date dateBefore) {
        this.dateBefore = dateBefore;
    }

    public Date getDateAfter() {
        return dateAfter;
    }

    public void setDateAfter(Date dateAfter) {
        this.dateAfter = dateAfter;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(Integer subProjectId) {
        this.subProjectId = subProjectId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
