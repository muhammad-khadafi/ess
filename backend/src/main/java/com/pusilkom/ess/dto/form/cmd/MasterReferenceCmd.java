package com.pusilkom.ess.dto.form.cmd;

/**
 * Created by muhammad.khadafi on 10/25/2018.
 */
public class MasterReferenceCmd {

    private String name;

    private String category;

    private String referenceType;

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
