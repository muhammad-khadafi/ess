package com.pusilkom.ess.dto.form.search;

/**
 * Created by ITF on 11/2/2018.
 */
public class JobPositionSearchForm extends BaseTableSearchForm {
    private String name;
    private String level;
    private Boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }
}
