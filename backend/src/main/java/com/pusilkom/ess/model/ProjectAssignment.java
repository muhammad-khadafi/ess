package com.pusilkom.ess.model;

import java.io.Serializable;
import java.util.Date;

public class ProjectAssignment implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.employee_id
     *
     * @mbg.generated
     */
    private Integer employeeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.project_id
     *
     * @mbg.generated
     */
    private Integer projectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.role_id
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.is_lead
     *
     * @mbg.generated
     */
    private Boolean isLead;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.load
     *
     * @mbg.generated
     */
    private Integer load;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.start_date
     *
     * @mbg.generated
     */
    private Date startDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.end_date
     *
     * @mbg.generated
     */
    private Date endDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.created_date
     *
     * @mbg.generated
     */
    private Date createdDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.created_by
     *
     * @mbg.generated
     */
    private Integer createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.updated_date
     *
     * @mbg.generated
     */
    private Date updatedDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.updated_by
     *
     * @mbg.generated
     */
    private Integer updatedBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.level_id
     *
     * @mbg.generated
     */
    private Integer levelId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_assignment.flag_active
     *
     * @mbg.generated
     */
    private Boolean flagActive;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table project_assignment
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.id
     *
     * @return the value of project_assignment.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.id
     *
     * @param id the value for project_assignment.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.employee_id
     *
     * @return the value of project_assignment.employee_id
     *
     * @mbg.generated
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.employee_id
     *
     * @param employeeId the value for project_assignment.employee_id
     *
     * @mbg.generated
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.project_id
     *
     * @return the value of project_assignment.project_id
     *
     * @mbg.generated
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.project_id
     *
     * @param projectId the value for project_assignment.project_id
     *
     * @mbg.generated
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.role_id
     *
     * @return the value of project_assignment.role_id
     *
     * @mbg.generated
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.role_id
     *
     * @param roleId the value for project_assignment.role_id
     *
     * @mbg.generated
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.is_lead
     *
     * @return the value of project_assignment.is_lead
     *
     * @mbg.generated
     */
    public Boolean getIsLead() {
        return isLead;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.is_lead
     *
     * @param isLead the value for project_assignment.is_lead
     *
     * @mbg.generated
     */
    public void setIsLead(Boolean isLead) {
        this.isLead = isLead;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.load
     *
     * @return the value of project_assignment.load
     *
     * @mbg.generated
     */
    public Integer getLoad() {
        return load;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.load
     *
     * @param load the value for project_assignment.load
     *
     * @mbg.generated
     */
    public void setLoad(Integer load) {
        this.load = load;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.start_date
     *
     * @return the value of project_assignment.start_date
     *
     * @mbg.generated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.start_date
     *
     * @param startDate the value for project_assignment.start_date
     *
     * @mbg.generated
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.end_date
     *
     * @return the value of project_assignment.end_date
     *
     * @mbg.generated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.end_date
     *
     * @param endDate the value for project_assignment.end_date
     *
     * @mbg.generated
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.created_date
     *
     * @return the value of project_assignment.created_date
     *
     * @mbg.generated
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.created_date
     *
     * @param createdDate the value for project_assignment.created_date
     *
     * @mbg.generated
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.created_by
     *
     * @return the value of project_assignment.created_by
     *
     * @mbg.generated
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.created_by
     *
     * @param createdBy the value for project_assignment.created_by
     *
     * @mbg.generated
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.updated_date
     *
     * @return the value of project_assignment.updated_date
     *
     * @mbg.generated
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.updated_date
     *
     * @param updatedDate the value for project_assignment.updated_date
     *
     * @mbg.generated
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.updated_by
     *
     * @return the value of project_assignment.updated_by
     *
     * @mbg.generated
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.updated_by
     *
     * @param updatedBy the value for project_assignment.updated_by
     *
     * @mbg.generated
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.level_id
     *
     * @return the value of project_assignment.level_id
     *
     * @mbg.generated
     */
    public Integer getLevelId() {
        return levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.level_id
     *
     * @param levelId the value for project_assignment.level_id
     *
     * @mbg.generated
     */
    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_assignment.flag_active
     *
     * @return the value of project_assignment.flag_active
     *
     * @mbg.generated
     */
    public Boolean getFlagActive() {
        return flagActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_assignment.flag_active
     *
     * @param flagActive the value for project_assignment.flag_active
     *
     * @mbg.generated
     */
    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_assignment
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ProjectAssignment other = (ProjectAssignment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getIsLead() == null ? other.getIsLead() == null : this.getIsLead().equals(other.getIsLead()))
            && (this.getLoad() == null ? other.getLoad() == null : this.getLoad().equals(other.getLoad()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getUpdatedDate() == null ? other.getUpdatedDate() == null : this.getUpdatedDate().equals(other.getUpdatedDate()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getLevelId() == null ? other.getLevelId() == null : this.getLevelId().equals(other.getLevelId()))
            && (this.getFlagActive() == null ? other.getFlagActive() == null : this.getFlagActive().equals(other.getFlagActive()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_assignment
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getIsLead() == null) ? 0 : getIsLead().hashCode());
        result = prime * result + ((getLoad() == null) ? 0 : getLoad().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getUpdatedDate() == null) ? 0 : getUpdatedDate().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getLevelId() == null) ? 0 : getLevelId().hashCode());
        result = prime * result + ((getFlagActive() == null) ? 0 : getFlagActive().hashCode());
        return result;
    }
}