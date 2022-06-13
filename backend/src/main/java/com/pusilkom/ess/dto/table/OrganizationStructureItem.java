package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.OrganizationStructure;

public class OrganizationStructureItem extends OrganizationStructure {
    private String parentName;
    private String organizationName;
    private String jobPositionName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getJobPositionName() {
        return jobPositionName;
    }

    public void setJobPositionName(String jobPositionName) {
        this.jobPositionName = jobPositionName;
    }
}
