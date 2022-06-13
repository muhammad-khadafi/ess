package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.Letter;

import java.util.Date;

public class LetterCmd {
    private  Integer id;
    private  String category;
    private  Date createdDate;
    private  String description;
    private String purpose;
    private String information;
    private Integer idUser;
    private String serialNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Letter toLetter(){
        Letter letter = new Letter();
        letter.setCategory(this.getCategory());
        letter.setCreatedDate(this.getCreatedDate());
        letter.setDescription(this.getDescription());
        letter.setPurpose(this.getPurpose());
        letter.setInformation(this.getInformation());
        letter.setSerialNumber(this.getSerialNumber());
        letter.setIdUser(this.getIdUser());
        return letter;
    }
}
