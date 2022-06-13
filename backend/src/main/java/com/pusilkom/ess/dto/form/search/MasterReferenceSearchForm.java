package com.pusilkom.ess.dto.form.search;

/**
 * Created by fahri on 1/16/17.
 */
public class MasterReferenceSearchForm extends BaseTableSearchForm {
    private Integer id;
    private String name;
    private String category;
    private String referenceType;

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
