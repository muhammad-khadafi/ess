package com.pusilkom.ess.model;

import java.io.Serializable;
import java.util.Date;

public class InvoiceReport implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice_report.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice_report.start_date
     *
     * @mbg.generated
     */
    private Date startDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice_report.end_date
     *
     * @mbg.generated
     */
    private Date endDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice_report.sub_project_id
     *
     * @mbg.generated
     */
    private Integer subProjectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice_report.supervisor_name
     *
     * @mbg.generated
     */
    private String supervisorName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice_report.path
     *
     * @mbg.generated
     */
    private String path;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column invoice_report.total
     *
     * @mbg.generated
     */
    private Long total;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table invoice_report
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice_report.id
     *
     * @return the value of invoice_report.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice_report.id
     *
     * @param id the value for invoice_report.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice_report.start_date
     *
     * @return the value of invoice_report.start_date
     *
     * @mbg.generated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice_report.start_date
     *
     * @param startDate the value for invoice_report.start_date
     *
     * @mbg.generated
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice_report.end_date
     *
     * @return the value of invoice_report.end_date
     *
     * @mbg.generated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice_report.end_date
     *
     * @param endDate the value for invoice_report.end_date
     *
     * @mbg.generated
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice_report.sub_project_id
     *
     * @return the value of invoice_report.sub_project_id
     *
     * @mbg.generated
     */
    public Integer getSubProjectId() {
        return subProjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice_report.sub_project_id
     *
     * @param subProjectId the value for invoice_report.sub_project_id
     *
     * @mbg.generated
     */
    public void setSubProjectId(Integer subProjectId) {
        this.subProjectId = subProjectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice_report.supervisor_name
     *
     * @return the value of invoice_report.supervisor_name
     *
     * @mbg.generated
     */
    public String getSupervisorName() {
        return supervisorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice_report.supervisor_name
     *
     * @param supervisorName the value for invoice_report.supervisor_name
     *
     * @mbg.generated
     */
    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice_report.path
     *
     * @return the value of invoice_report.path
     *
     * @mbg.generated
     */
    public String getPath() {
        return path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice_report.path
     *
     * @param path the value for invoice_report.path
     *
     * @mbg.generated
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column invoice_report.total
     *
     * @return the value of invoice_report.total
     *
     * @mbg.generated
     */
    public Long getTotal() {
        return total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column invoice_report.total
     *
     * @param total the value for invoice_report.total
     *
     * @mbg.generated
     */
    public void setTotal(Long total) {
        this.total = total;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table invoice_report
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
        InvoiceReport other = (InvoiceReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getSubProjectId() == null ? other.getSubProjectId() == null : this.getSubProjectId().equals(other.getSubProjectId()))
            && (this.getSupervisorName() == null ? other.getSupervisorName() == null : this.getSupervisorName().equals(other.getSupervisorName()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getTotal() == null ? other.getTotal() == null : this.getTotal().equals(other.getTotal()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table invoice_report
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getSubProjectId() == null) ? 0 : getSubProjectId().hashCode());
        result = prime * result + ((getSupervisorName() == null) ? 0 : getSupervisorName().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getTotal() == null) ? 0 : getTotal().hashCode());
        return result;
    }
}