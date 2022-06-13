package com.pusilkom.ess.model;

import java.io.Serializable;
import java.util.Date;

public class ProjectPayment implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.contract_id
     *
     * @mbg.generated
     */
    private Integer contractId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.term
     *
     * @mbg.generated
     */
    private Integer term;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.percentage
     *
     * @mbg.generated
     */
    private Integer percentage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.value
     *
     * @mbg.generated
     */
    private Integer value;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.due_date
     *
     * @mbg.generated
     */
    private Date dueDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.is_paid
     *
     * @mbg.generated
     */
    private Boolean isPaid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.notes
     *
     * @mbg.generated
     */
    private String notes;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.created_date
     *
     * @mbg.generated
     */
    private Date createdDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.created_by
     *
     * @mbg.generated
     */
    private Integer createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.updated_date
     *
     * @mbg.generated
     */
    private Date updatedDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_payment.updated_by
     *
     * @mbg.generated
     */
    private Integer updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table project_payment
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.id
     *
     * @return the value of project_payment.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.id
     *
     * @param id the value for project_payment.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.contract_id
     *
     * @return the value of project_payment.contract_id
     *
     * @mbg.generated
     */
    public Integer getContractId() {
        return contractId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.contract_id
     *
     * @param contractId the value for project_payment.contract_id
     *
     * @mbg.generated
     */
    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.term
     *
     * @return the value of project_payment.term
     *
     * @mbg.generated
     */
    public Integer getTerm() {
        return term;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.term
     *
     * @param term the value for project_payment.term
     *
     * @mbg.generated
     */
    public void setTerm(Integer term) {
        this.term = term;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.percentage
     *
     * @return the value of project_payment.percentage
     *
     * @mbg.generated
     */
    public Integer getPercentage() {
        return percentage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.percentage
     *
     * @param percentage the value for project_payment.percentage
     *
     * @mbg.generated
     */
    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.value
     *
     * @return the value of project_payment.value
     *
     * @mbg.generated
     */
    public Integer getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.value
     *
     * @param value the value for project_payment.value
     *
     * @mbg.generated
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.due_date
     *
     * @return the value of project_payment.due_date
     *
     * @mbg.generated
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.due_date
     *
     * @param dueDate the value for project_payment.due_date
     *
     * @mbg.generated
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.is_paid
     *
     * @return the value of project_payment.is_paid
     *
     * @mbg.generated
     */
    public Boolean getIsPaid() {
        return isPaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.is_paid
     *
     * @param isPaid the value for project_payment.is_paid
     *
     * @mbg.generated
     */
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.notes
     *
     * @return the value of project_payment.notes
     *
     * @mbg.generated
     */
    public String getNotes() {
        return notes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.notes
     *
     * @param notes the value for project_payment.notes
     *
     * @mbg.generated
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.created_date
     *
     * @return the value of project_payment.created_date
     *
     * @mbg.generated
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.created_date
     *
     * @param createdDate the value for project_payment.created_date
     *
     * @mbg.generated
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.created_by
     *
     * @return the value of project_payment.created_by
     *
     * @mbg.generated
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.created_by
     *
     * @param createdBy the value for project_payment.created_by
     *
     * @mbg.generated
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.updated_date
     *
     * @return the value of project_payment.updated_date
     *
     * @mbg.generated
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.updated_date
     *
     * @param updatedDate the value for project_payment.updated_date
     *
     * @mbg.generated
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_payment.updated_by
     *
     * @return the value of project_payment.updated_by
     *
     * @mbg.generated
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_payment.updated_by
     *
     * @param updatedBy the value for project_payment.updated_by
     *
     * @mbg.generated
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_payment
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
        ProjectPayment other = (ProjectPayment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
            && (this.getTerm() == null ? other.getTerm() == null : this.getTerm().equals(other.getTerm()))
            && (this.getPercentage() == null ? other.getPercentage() == null : this.getPercentage().equals(other.getPercentage()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
            && (this.getDueDate() == null ? other.getDueDate() == null : this.getDueDate().equals(other.getDueDate()))
            && (this.getIsPaid() == null ? other.getIsPaid() == null : this.getIsPaid().equals(other.getIsPaid()))
            && (this.getNotes() == null ? other.getNotes() == null : this.getNotes().equals(other.getNotes()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getUpdatedDate() == null ? other.getUpdatedDate() == null : this.getUpdatedDate().equals(other.getUpdatedDate()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_payment
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getTerm() == null) ? 0 : getTerm().hashCode());
        result = prime * result + ((getPercentage() == null) ? 0 : getPercentage().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getDueDate() == null) ? 0 : getDueDate().hashCode());
        result = prime * result + ((getIsPaid() == null) ? 0 : getIsPaid().hashCode());
        result = prime * result + ((getNotes() == null) ? 0 : getNotes().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getUpdatedDate() == null) ? 0 : getUpdatedDate().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        return result;
    }
}