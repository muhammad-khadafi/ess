package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.Employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ITF on 11/2/2018.
 */
public class EmployeeItem {
    private Integer id;
    private String name;
    private String nik;
    private String gender;
    private String email;
    private String birthPlace;
    private Date birthDate;
    private String mainPhoneNumber;
    private String secondaryPhoneNumber;
    private String birthDateString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBirthDateString() {
        return birthDateString;
    }

    public void setBirthDateString(String birthDateString) {
        this.birthDateString = birthDateString;
    }

    public void convBirthDateToString () {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.setBirthDateString(df.format(this.birthDate));
    }

    public EmployeeItem (Employee employee)
    {
        this.setId(employee.getId());
        this.setName(employee.getName());
        this.setNik(employee.getNik());
    }

    public EmployeeItem ()
    {

    }
}
