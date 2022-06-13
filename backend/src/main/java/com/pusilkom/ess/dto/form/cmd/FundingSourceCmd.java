package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.FundingSource;

public class FundingSourceCmd {
    private Integer id;
    private String name;
    private Boolean isActive;

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public FundingSource toFundingSource() {
        FundingSource fundingSource = new FundingSource();
        fundingSource.setId(this.id);
        fundingSource.setName(this.name);
        fundingSource.setIsActive(this.isActive);

        return fundingSource;
    }

}
