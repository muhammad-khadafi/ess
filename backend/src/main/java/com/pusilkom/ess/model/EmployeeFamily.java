package com.pusilkom.ess.model;

import java.io.Serializable;
import java.util.Date;

public class EmployeeFamily implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.employee_id
     *
     * @mbg.generated
     */
    private Integer employeeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.nik
     *
     * @mbg.generated
     */
    private String nik;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.relation
     *
     * @mbg.generated
     */
    private String relation;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.birth_place
     *
     * @mbg.generated
     */
    private String birthPlace;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.birth_date
     *
     * @mbg.generated
     */
    private Date birthDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.phone_number
     *
     * @mbg.generated
     */
    private String phoneNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.created_date
     *
     * @mbg.generated
     */
    private Date createdDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.created_by
     *
     * @mbg.generated
     */
    private Integer createdBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.updated_date
     *
     * @mbg.generated
     */
    private Date updatedDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee_family.updated_by
     *
     * @mbg.generated
     */
    private Integer updatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table employee_family
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.id
     *
     * @return the value of employee_family.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.id
     *
     * @param id the value for employee_family.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.employee_id
     *
     * @return the value of employee_family.employee_id
     *
     * @mbg.generated
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.employee_id
     *
     * @param employeeId the value for employee_family.employee_id
     *
     * @mbg.generated
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.name
     *
     * @return the value of employee_family.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.name
     *
     * @param name the value for employee_family.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.nik
     *
     * @return the value of employee_family.nik
     *
     * @mbg.generated
     */
    public String getNik() {
        return nik;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.nik
     *
     * @param nik the value for employee_family.nik
     *
     * @mbg.generated
     */
    public void setNik(String nik) {
        this.nik = nik;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.relation
     *
     * @return the value of employee_family.relation
     *
     * @mbg.generated
     */
    public String getRelation() {
        return relation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.relation
     *
     * @param relation the value for employee_family.relation
     *
     * @mbg.generated
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.birth_place
     *
     * @return the value of employee_family.birth_place
     *
     * @mbg.generated
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.birth_place
     *
     * @param birthPlace the value for employee_family.birth_place
     *
     * @mbg.generated
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.birth_date
     *
     * @return the value of employee_family.birth_date
     *
     * @mbg.generated
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.birth_date
     *
     * @param birthDate the value for employee_family.birth_date
     *
     * @mbg.generated
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.phone_number
     *
     * @return the value of employee_family.phone_number
     *
     * @mbg.generated
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.phone_number
     *
     * @param phoneNumber the value for employee_family.phone_number
     *
     * @mbg.generated
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.created_date
     *
     * @return the value of employee_family.created_date
     *
     * @mbg.generated
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.created_date
     *
     * @param createdDate the value for employee_family.created_date
     *
     * @mbg.generated
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.created_by
     *
     * @return the value of employee_family.created_by
     *
     * @mbg.generated
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.created_by
     *
     * @param createdBy the value for employee_family.created_by
     *
     * @mbg.generated
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.updated_date
     *
     * @return the value of employee_family.updated_date
     *
     * @mbg.generated
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.updated_date
     *
     * @param updatedDate the value for employee_family.updated_date
     *
     * @mbg.generated
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee_family.updated_by
     *
     * @return the value of employee_family.updated_by
     *
     * @mbg.generated
     */
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee_family.updated_by
     *
     * @param updatedBy the value for employee_family.updated_by
     *
     * @mbg.generated
     */
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_family
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
        EmployeeFamily other = (EmployeeFamily) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getNik() == null ? other.getNik() == null : this.getNik().equals(other.getNik()))
            && (this.getRelation() == null ? other.getRelation() == null : this.getRelation().equals(other.getRelation()))
            && (this.getBirthPlace() == null ? other.getBirthPlace() == null : this.getBirthPlace().equals(other.getBirthPlace()))
            && (this.getBirthDate() == null ? other.getBirthDate() == null : this.getBirthDate().equals(other.getBirthDate()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getUpdatedDate() == null ? other.getUpdatedDate() == null : this.getUpdatedDate().equals(other.getUpdatedDate()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table employee_family
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getNik() == null) ? 0 : getNik().hashCode());
        result = prime * result + ((getRelation() == null) ? 0 : getRelation().hashCode());
        result = prime * result + ((getBirthPlace() == null) ? 0 : getBirthPlace().hashCode());
        result = prime * result + ((getBirthDate() == null) ? 0 : getBirthDate().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getUpdatedDate() == null) ? 0 : getUpdatedDate().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        return result;
    }
}