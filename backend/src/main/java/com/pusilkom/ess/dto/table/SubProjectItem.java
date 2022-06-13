package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.SubProject;

public class SubProjectItem extends SubProject {
    private String spvName;
    private String projectName;

    public String getSpvName() {
        return spvName;
    }

    public void setSpvName(String spvName) {
        this.spvName = spvName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
