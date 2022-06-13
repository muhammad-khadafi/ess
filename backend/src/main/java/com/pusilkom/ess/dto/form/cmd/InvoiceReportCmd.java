package com.pusilkom.ess.dto.form.cmd;

import java.util.Date;

/**
 * Created by muhammad.khadafi on 10/25/2018.
 */
public class InvoiceReportCmd {

    private Date startDate;
    private Date endDate;
    private Integer subProjectId;
    private Integer spvId;
    private String clientPersonel;
    private String clientRole;
    private String pusilkomPersonel;
    private String pusilkomRole;
    private String spvName;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(Integer subProjectId) {
        this.subProjectId = subProjectId;
    }

    public Integer getSpvId() {
        return spvId;
    }

    public void setSpvId(Integer spvId) {
        this.spvId = spvId;
    }

    public String getClientPersonel() {
        return clientPersonel;
    }

    public void setClientPersonel(String clientPersonel) {
        this.clientPersonel = clientPersonel;
    }

    public String getClientRole() {
        return clientRole;
    }

    public void setClientRole(String clientRole) {
        this.clientRole = clientRole;
    }

    public String getPusilkomPersonel() {
        return pusilkomPersonel;
    }

    public void setPusilkomPersonel(String pusilkomPersonel) {
        this.pusilkomPersonel = pusilkomPersonel;
    }

    public String getPusilkomRole() {
        return pusilkomRole;
    }

    public void setPusilkomRole(String pusilkomRole) {
        this.pusilkomRole = pusilkomRole;
    }

    public String getSpvName() {
        return spvName;
    }

    public void setSpvName(String spvName) {
        this.spvName = spvName;
    }
}
