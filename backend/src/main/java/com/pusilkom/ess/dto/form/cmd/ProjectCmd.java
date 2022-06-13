package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.Project;

import java.util.List;

public class ProjectCmd extends Project {
    private List<ProjectContractCmd> listProjectContract;
    private List<ProjectPaymentCmd> listProjectPayment;

    public List<ProjectContractCmd> getListProjectContract() {
        return listProjectContract;
    }

    public void setListProjectContract(List<ProjectContractCmd> listProjectContract) {
        this.listProjectContract = listProjectContract;
    }

    public List<ProjectPaymentCmd> getListProjectPayment() {
        return listProjectPayment;
    }

    public void setListProjectPayment(List<ProjectPaymentCmd> listProjectPayment) {
        this.listProjectPayment = listProjectPayment;
    }

    public Project toProject(){
        Project project = new Project();
        project.setId(this.getId());
        project.setOrganizationId(this.getOrganizationId());
        project.setFundingType(this.getFundingType());
        project.setClient(this.getClient());
        project.setStartDate(this.getStartDate());
        project.setEndDate(this.getEndDate());
        project.setStatusId(this.getStatusId());
        project.setName(this.getName());

        return project;
    }
}
