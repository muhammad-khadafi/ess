package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.Organization;

/**
 * Created by fahri on 1/8/17.
 */
public class OrganizationCmd extends Organization {

    public Organization toOrganization() {
        Organization organization = new Organization();
        organization.setId(this.getId());
        organization.setName(this.getName());
        organization.setParentId(this.getParentId());
        organization.setIsActive(this.getIsActive());
        organization.setType(this.getType());

        return organization;
    }

}
