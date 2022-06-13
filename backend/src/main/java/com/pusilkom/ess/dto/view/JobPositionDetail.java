package com.pusilkom.ess.dto.view;

import com.pusilkom.ess.model.JobPosition;
import java.util.Date;

/**
 * Created by ITF on 11/2/2018.
 */
public class JobPositionDetail {
    private Integer id;
    private String name;
    private String level;
    private Boolean isActive;
    private Date createdDate;
    private Integer createdBy;
    private Date updateDate;
    private Integer updatedBy;

    public JobPositionDetail() {}

    public JobPositionDetail(JobPosition jobPosition) {
        this.id = jobPosition.getId();
        this.name = jobPosition.getName();
        this.level = jobPosition.getLevel();
        this.isActive = jobPosition.getIsActive();
        this.createdBy = jobPosition.getCreatedBy();
        this.createdDate =jobPosition.getCreatedDate();
        this.updateDate = jobPosition.getUpdatedDate();
        this.updatedBy = jobPosition.getUpdatedBy();
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}
