package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.ProjectPayment;

public class ProjectPaymentCmd extends ProjectPayment {
    private String contractNumber;

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public ProjectPayment toProjectPayment(){
        ProjectPayment pp = new ProjectPayment();
        pp.setId(this.getId());
        pp.setContractId(this.getContractId());
        pp.setTerm(this.getTerm());
        pp.setPercentage(this.getPercentage());
        pp.setValue(this.getValue());
        pp.setDueDate(this.getDueDate());
        pp.setIsPaid(this.getIsPaid());
        pp.setNotes(this.getNotes());
        pp.setCreatedBy(this.getCreatedBy());
        pp.setCreatedDate(this.getCreatedDate());

        return pp;
    }
}
