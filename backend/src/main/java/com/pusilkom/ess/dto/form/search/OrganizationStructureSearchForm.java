package com.pusilkom.ess.dto.form.search;

public class OrganizationStructureSearchForm extends BaseTableSearchForm {

    private Integer id;
    private Integer parentId;
    private Boolean stringAktif;
    private String notes;
    private Integer organizationId;
    private Integer positionId;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getStringAktif() {
        return stringAktif;
    }

    public void setStringAktif(Boolean stringAktif) {
        this.stringAktif = stringAktif;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
