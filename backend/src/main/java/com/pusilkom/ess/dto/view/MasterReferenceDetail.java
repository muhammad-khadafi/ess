package com.pusilkom.ess.dto.view;

import com.pusilkom.ess.model.MasterReference;

/**
 * Created by fahri on 1/8/17.
 */
public class MasterReferenceDetail {
    private Integer id;
    private String name;
    private String category;
    private String referenceType;

    public MasterReferenceDetail(MasterReference masterReference) {
        this.id = masterReference.getId();
        this.name = masterReference.getName();
        this.category = masterReference.getCategory();
        this.referenceType = masterReference.getReferenceType();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }
}
