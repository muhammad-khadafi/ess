package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.JobPosition;

import java.util.Date;

/**
 * Created by ITF on 11/1/2018.
 */
public class JobPositionCmd {

    private Integer id;
    private String name;
    private String level;
    private Boolean isActive;
    private Date createdDate;
    private Integer createdBy;
    private Date updateDate;
    private Integer updatedBy;

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

    public JobPosition toJobPosition() {
        JobPosition jobPosition = new JobPosition();
        jobPosition.setId(this.id);
        jobPosition.setName(this.name);
        jobPosition.setLevel(this.level);
        jobPosition.setIsActive(this.isActive);
        jobPosition.setCreatedBy(this.createdBy);
        jobPosition.setCreatedDate(this.createdDate);
        jobPosition.setUpdatedDate(this.updateDate);
        jobPosition.setUpdatedBy(this.updatedBy);
        return jobPosition;
    }
}
