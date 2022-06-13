package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.OrganizationStructure;

public class OrganizationStructureCmd extends OrganizationStructure {

    public OrganizationStructure toOrganizationStructure(){
        OrganizationStructure organizationStructure = new OrganizationStructure();
        organizationStructure.setId(this.getId());
        organizationStructure.setOrganizationId(this.getOrganizationId());
        organizationStructure.setPositionId(this.getPositionId());
        organizationStructure.setParentId(this.getParentId());
        organizationStructure.setIsActive(this.getIsActive());
        organizationStructure.setNotes(this.getNotes());
        organizationStructure.setName(this.getName());
        organizationStructure.setCreatedBy(this.getCreatedBy());
        organizationStructure.setCreatedDate(this.getCreatedDate());
        organizationStructure.setCreatedBy(this.getCreatedBy());
        organizationStructure.setCreatedDate(this.getCreatedDate());

        if(this.getId() == null){
            organizationStructure.setCreatedBy(this.getCreatedBy());
            organizationStructure.setCreatedDate(this.getCreatedDate());
        } else {
            organizationStructure.setCreatedBy(this.getCreatedBy());
            organizationStructure.setCreatedDate(this.getCreatedDate());
            organizationStructure.setUpdatedBy(this.getUpdatedBy());
            organizationStructure.setUpdatedDate(this.getUpdatedDate());
        }
        return organizationStructure;
    }
}
