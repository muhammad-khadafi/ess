package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.LogActivityDetail;

public class LogActivityDetailCmd extends LogActivityDetail {
    private Integer idEmployee;
    private Integer projectId;

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public LogActivityDetail toLogActivityDetail(){
        LogActivityDetail lad = new LogActivityDetailCmd();
        lad.setId(this.getId());
        lad.setDate(this.getDate());
        lad.setActivityDetail(this.getActivityDetail());
        lad.setThirdParty(this.getThirdParty());
        lad.setDuration(this.getDuration());
        lad.setOvertime(this.getOvertime());
        lad.setSpvAssigned(this.getSpvAssigned());
        lad.setProjectAssignId(this.getProjectAssignId());
        lad.setSubProId(this.getSubProId());

        return lad;
    }
}
