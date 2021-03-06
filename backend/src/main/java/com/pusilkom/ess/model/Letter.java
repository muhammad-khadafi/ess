package com.pusilkom.ess.model;

import java.io.Serializable;
import java.util.Date;

public class Letter implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column letter.category
     *
     * @mbg.generated
     */
    private String category;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column letter.created_date
     *
     * @mbg.generated
     */
    private Date createdDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column letter.description
     *
     * @mbg.generated
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column letter.purpose
     *
     * @mbg.generated
     */
    private String purpose;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column letter.information
     *
     * @mbg.generated
     */
    private String information;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column letter.id_user
     *
     * @mbg.generated
     */
    private Integer idUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column letter.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column letter.serial_number
     *
     * @mbg.generated
     */
    private String serialNumber;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table letter
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column letter.category
     *
     * @return the value of letter.category
     *
     * @mbg.generated
     */
    public String getCategory() {
        return category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column letter.category
     *
     * @param category the value for letter.category
     *
     * @mbg.generated
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column letter.created_date
     *
     * @return the value of letter.created_date
     *
     * @mbg.generated
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column letter.created_date
     *
     * @param createdDate the value for letter.created_date
     *
     * @mbg.generated
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column letter.description
     *
     * @return the value of letter.description
     *
     * @mbg.generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column letter.description
     *
     * @param description the value for letter.description
     *
     * @mbg.generated
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column letter.purpose
     *
     * @return the value of letter.purpose
     *
     * @mbg.generated
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column letter.purpose
     *
     * @param purpose the value for letter.purpose
     *
     * @mbg.generated
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column letter.information
     *
     * @return the value of letter.information
     *
     * @mbg.generated
     */
    public String getInformation() {
        return information;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column letter.information
     *
     * @param information the value for letter.information
     *
     * @mbg.generated
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column letter.id_user
     *
     * @return the value of letter.id_user
     *
     * @mbg.generated
     */
    public Integer getIdUser() {
        return idUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column letter.id_user
     *
     * @param idUser the value for letter.id_user
     *
     * @mbg.generated
     */
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column letter.id
     *
     * @return the value of letter.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column letter.id
     *
     * @param id the value for letter.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column letter.serial_number
     *
     * @return the value of letter.serial_number
     *
     * @mbg.generated
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column letter.serial_number
     *
     * @param serialNumber the value for letter.serial_number
     *
     * @mbg.generated
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table letter
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
        Letter other = (Letter) that;
        return (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getPurpose() == null ? other.getPurpose() == null : this.getPurpose().equals(other.getPurpose()))
            && (this.getInformation() == null ? other.getInformation() == null : this.getInformation().equals(other.getInformation()))
            && (this.getIdUser() == null ? other.getIdUser() == null : this.getIdUser().equals(other.getIdUser()))
            && (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSerialNumber() == null ? other.getSerialNumber() == null : this.getSerialNumber().equals(other.getSerialNumber()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table letter
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getPurpose() == null) ? 0 : getPurpose().hashCode());
        result = prime * result + ((getInformation() == null) ? 0 : getInformation().hashCode());
        result = prime * result + ((getIdUser() == null) ? 0 : getIdUser().hashCode());
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSerialNumber() == null) ? 0 : getSerialNumber().hashCode());
        return result;
    }
}