package com.pusilkom.ess.model;

import java.io.Serializable;
import java.util.Date;

public class InventoryAssignment implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inventory_assignment.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inventory_assignment.employee_id
     *
     * @mbg.generated
     */
    private Integer employeeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inventory_assignment.inventory_id
     *
     * @mbg.generated
     */
    private Integer inventoryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inventory_assignment.start_date
     *
     * @mbg.generated
     */
    private Date startDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inventory_assignment.end_date
     *
     * @mbg.generated
     */
    private Date endDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column inventory_assignment.is_assigned
     *
     * @mbg.generated
     */
    private Boolean isAssigned;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table inventory_assignment
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inventory_assignment.id
     *
     * @return the value of inventory_assignment.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inventory_assignment.id
     *
     * @param id the value for inventory_assignment.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inventory_assignment.employee_id
     *
     * @return the value of inventory_assignment.employee_id
     *
     * @mbg.generated
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inventory_assignment.employee_id
     *
     * @param employeeId the value for inventory_assignment.employee_id
     *
     * @mbg.generated
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inventory_assignment.inventory_id
     *
     * @return the value of inventory_assignment.inventory_id
     *
     * @mbg.generated
     */
    public Integer getInventoryId() {
        return inventoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inventory_assignment.inventory_id
     *
     * @param inventoryId the value for inventory_assignment.inventory_id
     *
     * @mbg.generated
     */
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inventory_assignment.start_date
     *
     * @return the value of inventory_assignment.start_date
     *
     * @mbg.generated
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inventory_assignment.start_date
     *
     * @param startDate the value for inventory_assignment.start_date
     *
     * @mbg.generated
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inventory_assignment.end_date
     *
     * @return the value of inventory_assignment.end_date
     *
     * @mbg.generated
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inventory_assignment.end_date
     *
     * @param endDate the value for inventory_assignment.end_date
     *
     * @mbg.generated
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column inventory_assignment.is_assigned
     *
     * @return the value of inventory_assignment.is_assigned
     *
     * @mbg.generated
     */
    public Boolean getIsAssigned() {
        return isAssigned;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column inventory_assignment.is_assigned
     *
     * @param isAssigned the value for inventory_assignment.is_assigned
     *
     * @mbg.generated
     */
    public void setIsAssigned(Boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory_assignment
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
        InventoryAssignment other = (InventoryAssignment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
            && (this.getInventoryId() == null ? other.getInventoryId() == null : this.getInventoryId().equals(other.getInventoryId()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getIsAssigned() == null ? other.getIsAssigned() == null : this.getIsAssigned().equals(other.getIsAssigned()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table inventory_assignment
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
        result = prime * result + ((getInventoryId() == null) ? 0 : getInventoryId().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getIsAssigned() == null) ? 0 : getIsAssigned().hashCode());
        return result;
    }
}