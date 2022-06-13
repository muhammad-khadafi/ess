package com.pusilkom.ess.dto.form.search;

import com.pusilkom.ess.model.Project;

import java.util.List;

public class ProjectAssignmentReportForm extends BaseTableSearchForm {

    private String startDateString;
    private String endDateString;
    private String selectedProjectListStr;
    private List<Project> selectedProjectList;

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

    public List<Project> getSelectedProjectList() {
        return selectedProjectList;
    }

    public void setSelectedProjectList(List<Project> selectedProjectList) {
        this.selectedProjectList = selectedProjectList;
    }

    public String getSelectedProjectListStr() {
        return selectedProjectListStr;
    }

    public void setSelectedProjectListStr(String selectedProjectListStr) {
        this.selectedProjectListStr = selectedProjectListStr;
    }
}
