package com.pusilkom.ess.dto.view;

import com.pusilkom.ess.model.Organization;

/**
 * Created by ITF on 11/8/2018.
 */
public class OrganizationDetail {
    private Integer id;
    private String name;
    private String type;
    private Integer parentId;
    private Boolean isActive;

    public OrganizationDetail(Organization organization) {
        this.id = organization.getId();
        this.name = organization.getName();
        this.type = organization.getType();
        this.isActive = organization.getIsActive();
        this.parentId = organization.getParentId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
