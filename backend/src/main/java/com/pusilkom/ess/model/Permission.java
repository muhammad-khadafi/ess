package com.pusilkom.ess.model;

import java.io.Serializable;

public class Permission implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.permission.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.permission.kode
     *
     * @mbg.generated
     */
    private String kode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.permission.label
     *
     * @mbg.generated
     */
    private String label;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table public.permission
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.permission.id
     *
     * @return the value of public.permission.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.permission.id
     *
     * @param id the value for public.permission.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.permission.kode
     *
     * @return the value of public.permission.kode
     *
     * @mbg.generated
     */
    public String getKode() {
        return kode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.permission.kode
     *
     * @param kode the value for public.permission.kode
     *
     * @mbg.generated
     */
    public void setKode(String kode) {
        this.kode = kode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.permission.label
     *
     * @return the value of public.permission.label
     *
     * @mbg.generated
     */
    public String getLabel() {
        return label;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.permission.label
     *
     * @param label the value for public.permission.label
     *
     * @mbg.generated
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.permission
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
        Permission other = (Permission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getKode() == null ? other.getKode() == null : this.getKode().equals(other.getKode()))
            && (this.getLabel() == null ? other.getLabel() == null : this.getLabel().equals(other.getLabel()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.permission
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getKode() == null) ? 0 : getKode().hashCode());
        result = prime * result + ((getLabel() == null) ? 0 : getLabel().hashCode());
        return result;
    }
}