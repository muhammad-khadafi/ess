package com.pusilkom.ess.model;

import java.io.Serializable;
import java.util.Date;

public class EmployeeContract implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.employee_id
     *
     * @mbg.generated
     */
    private Integer employeeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.contract_number
     *
     * @mbg.generated
     */
    private String contractNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.is_active
     *
     * @mbg.generated
     */
    private Boolean isActive;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.start_date
     *
     * @mbg.generated
     */
    private Date startDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.end_date
     *
     * @mbg.generated
     */
    private Date endDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.signee
     *
     * @mbg.generated
     */
    private String signee;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.created_date
     *
     * @mbg.generated
     */
    private Date createdDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.created_by
     *
     * @mbg.generated
     */
    private Integer createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.updated_date
     *
     * @mbg.generated
     */
    private Date updatedDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_contract.updated_by
     *
     * @mbg.generated
     */
    private Integer updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table employee_contract
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.id
     *
     * @return the value of employee_contract.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.id
     *
     * @param id the value for employee_contract.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.employee_id
     *
     * @return the value of employee_contract.employee_id
     *
     * @mbg.generated
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.employee_id
     *
     * @param employeeId the value for employee_contract.employee_id
     *
     * @mbg.generated
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.contract_number
     *
     * @return the value of employee_contract.contract_number
     *
     * @mbg.generated
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.contract_number
     *
     * @param contractNumber the value for employee_contract.contract_number
     *
     * @mbg.generated
     */
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.is_active
     *
     * @return the value of employee_contract.is_active
     *
     * @mbg.generated
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.is_active
     *
     * @param isActive the value for employee_contract.is_active
     *
     * @mbg.generated
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.start_date
     *
     * @return the value of employee_contract.start_date
     *
     * @mbg.generated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.start_date
     *
     * @param startDate the value for employee_contract.start_date
     *
     * @mbg.generated
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.end_date
     *
     * @return the value of employee_contract.end_date
     *
     * @mbg.generated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.end_date
     *
     * @param endDate the value for employee_contract.end_date
     *
     * @mbg.generated
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.signee
     *
     * @return the value of employee_contract.signee
     *
     * @mbg.generated
     */
    public String getSignee() {
        return signee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.signee
     *
     * @param signee the value for employee_contract.signee
     *
     * @mbg.generated
     */
    public void setSignee(String signee) {
        this.signee = signee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.created_date
     *
     * @return the value of employee_contract.created_date
     *
     * @mbg.generated
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.created_date
     *
     * @param createdDate the value for employee_contract.created_date
     *
     * @mbg.generated
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.created_by
     *
     * @return the value of employee_contract.created_by
     *
     * @mbg.generated
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.created_by
     *
     * @param createdBy the value for employee_contract.created_by
     *
     * @mbg.generated
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.updated_date
     *
     * @return the value of employee_contract.updated_date
     *
     * @mbg.generated
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.updated_date
     *
     * @param updatedDate the value for employee_contract.updated_date
     *
     * @mbg.generated
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_contract.updated_by
     *
     * @return the value of employee_contract.updated_by
     *
     * @mbg.generated
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_contract.updated_by
     *
     * @param updatedBy the value for employee_contract.updated_by
     *
     * @mbg.generated
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_contract
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
        EmployeeContract other = (EmployeeContract) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
            && (this.getContractNumber() == null ? other.getContractNumber() == null : this.getContractNumber().equals(other.getContractNumber()))
            && (this.getIsActive() == null ? other.getIsActive() == null : this.getIsActive().equals(other.getIsActive()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getSignee() == null ? other.getSignee() == null : this.getSignee().equals(other.getSignee()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getUpdatedDate() == null ? other.getUpdatedDate() == null : this.getUpdatedDate().equals(other.getUpdatedDate()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_contract
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
        result = prime * result + ((getContractNumber() == null) ? 0 : getContractNumber().hashCode());
        result = prime * result + ((getIsActive() == null) ? 0 : getIsActive().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getSignee() == null) ? 0 : getSignee().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getUpdatedDate() == null) ? 0 : getUpdatedDate().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        return result;
    }
}