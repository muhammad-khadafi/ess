package com.pusilkom.ess.dto.form.search;

import java.util.Date;

public class InventorySearchForm extends BaseTableSearchForm {
    Integer id;
    boolean isNew;
    Integer type;
    String name;
    String spesification;
    String location;
    String purchaseDateString;
    Integer purchasePrice;
    String condition;
    String isAssigned;
    String notes;
    Date purchaseDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean aNew) {
        isNew = aNew;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpesification() {
        return spesification;
    }

    public void setSpesification(String spesification) {
        this.spesification = spesification;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPurchaseDateString() {
        return purchaseDateString;
    }

    public void setPurchaseDateString(String purchaseDateString) {
        this.purchaseDateString = purchaseDateString;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(String assigned) {
        isAssigned = assigned;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
