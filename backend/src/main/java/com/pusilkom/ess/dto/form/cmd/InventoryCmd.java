package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.Inventory;
import com.pusilkom.ess.model.InventoryAssignment;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InventoryCmd {
    Integer id;
    boolean isNew;
    Integer type;
    String typeString;
    String name;
    String specification;
    String location;
    String purchaseDateString;
    Integer purchasePrice;
    String condition;
    String inventoryCode;
    boolean isAssigned;
    String notes;
    Date purchaseDate;
    String pricewithSeparator;
    List<InventoryAssignment> listInventoryAssignment;


    public String getPurchaseDateString() {
        return purchaseDateString;
    }

    public void setPurchaseDateString(String purchaseDateString) {
        this.purchaseDateString = purchaseDateString;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

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

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String spesification) {
        this.specification = spesification;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public List<InventoryAssignment> getListInventoryAssignment() {
        return listInventoryAssignment;
    }

    public void setListInventoryAssignment(List<InventoryAssignment> listInventoryAssignment) {
        this.listInventoryAssignment = listInventoryAssignment;
    }

    public String getPricewithSeparator() {
        return pricewithSeparator;
    }

    public void setPricewithSeparator(String pricewithSeparator) {
        this.pricewithSeparator = pricewithSeparator;
    }

    public Inventory toInventory() throws ParseException {
        SimpleDateFormat ssdf = new SimpleDateFormat ("dd-MM-yyyy");
        Inventory aNew = new Inventory();
        aNew.setType(this.getType());
        aNew.setName(this.getName());
        aNew.setSpecification(this.getSpecification());
        aNew.setLocation(this.getLocation());
        if(!StringUtils.isEmpty(this.getPurchaseDateString())) {
            aNew.setPurchaseDate(ssdf.parse(this.getPurchaseDateString()));
        }
        aNew.setPurchasePrice(this.getPurchasePrice());
        aNew.setCondition(this.getCondition());
        aNew.setIsAssigned(this.getIsAssigned());
        aNew.setInventoryCode(this.getInventoryCode());

        return aNew;
    }
}
