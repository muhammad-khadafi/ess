package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.ProjectContract;

public class ProjectContractCmd extends ProjectContract {

    public ProjectContract toProjectContract() {
        ProjectContract projectContract = new ProjectContract();
        projectContract.setId(this.getId());
        projectContract.setProjectId(this.getProjectId());
        projectContract.setContractNumber(this.getContractNumber());
        projectContract.setContractDate(this.getContractDate());
        projectContract.setSignee1(this.getSignee1());
        projectContract.setSignee2(this.getSignee2());
        projectContract.setCreatedBy(this.getCreatedBy());
        projectContract.setCreatedDate(this.getCreatedDate());

        return projectContract;
    }
}
