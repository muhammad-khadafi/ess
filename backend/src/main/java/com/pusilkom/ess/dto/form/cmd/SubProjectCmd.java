package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.SubProject;

/**
 * Created by ITF on 1/8/2019.
 */
public class SubProjectCmd extends SubProject {
    public SubProject toSubProject(){
        SubProject subProject = new SubProject();
        subProject.setId(this.getId());
        subProject.setProjectId(this.getProjectId());
        subProject.setSubProjectName(this.getSubProjectName());
        subProject.setCode(this.getCode());
        subProject.setDescription(this.getDescription());
        subProject.setSupervisor(this.getSupervisor());

        return subProject;
    }
}
