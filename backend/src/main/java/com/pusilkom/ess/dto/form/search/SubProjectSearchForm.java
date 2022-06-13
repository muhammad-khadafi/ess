package com.pusilkom.ess.dto.form.search;

/**
 * Created by ITF on 1/7/2019.
 */
public class SubProjectSearchForm extends BaseTableSearchForm {

    private Integer projectId;
    private String code;
    private String subProjectName;
    private String supervisor;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
}
