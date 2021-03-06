package com.pusilkom.ess.model;

import java.io.Serializable;

public class TluLetter implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tlu_letter.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tlu_letter.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tlu_letter.counter
     *
     * @mbg.generated
     */
    private Integer counter;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tlu_letter.value
     *
     * @mbg.generated
     */
    private String value;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tlu_letter
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tlu_letter.id
     *
     * @return the value of tlu_letter.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tlu_letter.id
     *
     * @param id the value for tlu_letter.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tlu_letter.name
     *
     * @return the value of tlu_letter.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tlu_letter.name
     *
     * @param name the value for tlu_letter.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tlu_letter.counter
     *
     * @return the value of tlu_letter.counter
     *
     * @mbg.generated
     */
    public Integer getCounter() {
        return counter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tlu_letter.counter
     *
     * @param counter the value for tlu_letter.counter
     *
     * @mbg.generated
     */
    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tlu_letter.value
     *
     * @return the value of tlu_letter.value
     *
     * @mbg.generated
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tlu_letter.value
     *
     * @param value the value for tlu_letter.value
     *
     * @mbg.generated
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tlu_letter
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
        TluLetter other = (TluLetter) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCounter() == null ? other.getCounter() == null : this.getCounter().equals(other.getCounter()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tlu_letter
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCounter() == null) ? 0 : getCounter().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        return result;
    }
}