package com.pusilkom.ess.dto.view;

import com.pusilkom.ess.model.Employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arief on 10/31/18.
 */
public class EmployeeDetail {

    private Integer id;
    private String name;
    private String gender;
    private String nik;
    private String birthPlace;
    private Date birthDate;
    private String religion;
    private String bloodType;
    private String mainPhoneNumber;
    private String secondaryPhoneNumber;
    private String email;
    private String emergencyContact;
    private String emergencyPhone;
    private String address;
    private String postcode;
    private String city;
    private Date entryDate;
    private String birthDateString;
    private String entryDateString;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getMainPhoneNumber() {
        return mainPhoneNumber;
    }

    public void setMainPhoneNumber(String mainPhoneNumber) {
        this.mainPhoneNumber = mainPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getBirthDateString() {
        return birthDateString;
    }

    public void setBirthDateString(String birthDateString) {
        this.birthDateString = birthDateString;
    }

    public String getEntryDateString() {
        return entryDateString;
    }

    public void setEntryDateString(String entryDateString) {
        this.entryDateString = entryDateString;
    }

    public EmployeeDetail(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.gender = employee.getGender();
        this.nik = employee.getNik();
        this.birthPlace = employee.getBirthPlace();
        this.birthDate = employee.getBirthDate();
        this.religion = employee.getReligion();
        this.bloodType = employee.getBloodType();
        this.mainPhoneNumber = employee.getMainPhoneNumber();
        this.secondaryPhoneNumber = employee.getSecondaryPhoneNumber();
        this.email = employee.getEmail();
        this.emergencyContact = employee.getEmergencyContact();
        this.emergencyPhone = employee.getEmergencyPhone();
        this.address = employee.getAddress();
        this.postcode = employee.getPostcode();
        this.city = employee.getCity();
        this.entryDate = employee.getEntryDate();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(employee.getEntryDate() != null){
            this.entryDateString = df.format(this.entryDate);
        }
        if(employee.getBirthDate() != null){
            this.birthDateString = df.format(this.birthDate);
        }
    }
}
