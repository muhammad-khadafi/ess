package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.EmployeePosition;

public class EmployeePositionCmd extends EmployeePosition {

    private Boolean active;

    public EmployeePosition toEmployeePosition(){
        EmployeePosition ep = new EmployeePosition();
        ep.setId(this.getId());
        ep.setEmployeeId(this.getEmployeeId());
        ep.setStructureId(this.getStructureId());
        ep.setStartDate(this.getStartDate());
        ep.setEndDate(this.getEndDate());
        ep.setBirthDate(this.getBirthDate());
        ep.setIsActive(this.getIsActive());
        ep.setNotes(this.getNotes());
        ep.setCreatedBy(this.getCreatedBy());
        ep.setCreatedDate(this.getCreatedDate());

        return ep;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
