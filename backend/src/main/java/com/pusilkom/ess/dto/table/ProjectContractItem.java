package com.pusilkom.ess.dto.table;

import java.util.Date;

public class ProjectContractItem {
    private Integer id;
    private Integer projectId;
    private String projectName;
    private String contractNumber;
    private Date contractDate;
    private String contractDateString;
    private String signee1;
    private String signee2;
    private Date createdDate;
    private Integer createdBy;
    private Date updatedDate;
    private Integer updatedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getContractDateString() {
        return contractDateString;
    }

    public void setContractDateString(String contractDateString) {
        this.contractDateString = contractDateString;
    }

    public String getSignee1() {
        return signee1;
    }

    public void setSignee1(String signee1) {
        this.signee1 = signee1;
    }

    public String getSignee2() {
        return signee2;
    }

    public void setSignee2(String signee2) {
        this.signee2 = signee2;
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}
