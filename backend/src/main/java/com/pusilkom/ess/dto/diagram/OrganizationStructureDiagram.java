package com.pusilkom.ess.dto.diagram;

import com.pusilkom.ess.model.OrganizationStructure;

public class OrganizationStructureDiagram extends OrganizationStructure{
    private String organizationName;
    private String employeeName;

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
        return organizationStructure;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
