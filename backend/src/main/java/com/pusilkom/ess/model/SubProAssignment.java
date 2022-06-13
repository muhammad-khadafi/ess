package com.pusilkom.ess.model;

import java.io.Serializable;

public class SubProAssignment implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_pro_assignment.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_pro_assignment.sub_project_id
     *
     * @mbg.generated
     */
    private Integer subProjectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_pro_assignment.flag_aktif
     *
     * @mbg.generated
     */
    private Boolean flagAktif;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sub_pro_assignment.project_assign_id
     *
     * @mbg.generated
     */
    private Integer projectAssignId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sub_pro_assignment
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_pro_assignment.id
     *
     * @return the value of sub_pro_assignment.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_pro_assignment.id
     *
     * @param id the value for sub_pro_assignment.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_pro_assignment.sub_project_id
     *
     * @return the value of sub_pro_assignment.sub_project_id
     *
     * @mbg.generated
     */
    public Integer getSubProjectId() {
        return subProjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_pro_assignment.sub_project_id
     *
     * @param subProjectId the value for sub_pro_assignment.sub_project_id
     *
     * @mbg.generated
     */
    public void setSubProjectId(Integer subProjectId) {
        this.subProjectId = subProjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_pro_assignment.flag_aktif
     *
     * @return the value of sub_pro_assignment.flag_aktif
     *
     * @mbg.generated
     */
    public Boolean getFlagAktif() {
        return flagAktif;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_pro_assignment.flag_aktif
     *
     * @param flagAktif the value for sub_pro_assignment.flag_aktif
     *
     * @mbg.generated
     */
    public void setFlagAktif(Boolean flagAktif) {
        this.flagAktif = flagAktif;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sub_pro_assignment.project_assign_id
     *
     * @return the value of sub_pro_assignment.project_assign_id
     *
     * @mbg.generated
     */
    public Integer getProjectAssignId() {
        return projectAssignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sub_pro_assignment.project_assign_id
     *
     * @param projectAssignId the value for sub_pro_assignment.project_assign_id
     *
     * @mbg.generated
     */
    public void setProjectAssignId(Integer projectAssignId) {
        this.projectAssignId = projectAssignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_pro_assignment
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
        SubProAssignment other = (SubProAssignment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSubProjectId() == null ? other.getSubProjectId() == null : this.getSubProjectId().equals(other.getSubProjectId()))
            && (this.getFlagAktif() == null ? other.getFlagAktif() == null : this.getFlagAktif().equals(other.getFlagAktif()))
            && (this.getProjectAssignId() == null ? other.getProjectAssignId() == null : this.getProjectAssignId().equals(other.getProjectAssignId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_pro_assignment
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSubProjectId() == null) ? 0 : getSubProjectId().hashCode());
        result = prime * result + ((getFlagAktif() == null) ? 0 : getFlagAktif().hashCode());
        result = prime * result + ((getProjectAssignId() == null) ? 0 : getProjectAssignId().hashCode());
        return result;
    }
}