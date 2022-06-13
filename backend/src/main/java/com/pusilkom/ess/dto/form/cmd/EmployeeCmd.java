package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.Employee;

import java.util.Date;
import java.util.List;

/**
 * Created by arief on 10/31/18.
 */
public class EmployeeCmd {

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
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdDate;
    private Date updatedDate;
    private List<EducationCmd> listPendidikan;
    private List<EducationCmd> listPendidikanDelete;
    private String birthDateString;
    private String entryDateString;
    private List<EmployeeFamilyCmd> listFamily;
    private List<EmployeeBenefitCmd> listBenefit;
    private List<EmployeeSalaryCmd> listSalary;
    private List<EmployeeSkillsetCmd> listSkillset;
    private List<EmployeeDocumentCmd> listDocument;
    private List<EmployeeContractCmd> listContract;
    private List<EmployeePositionCmd> listPosition;

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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(this.id);
        employee.setName(this.name);
        employee.setGender(this.gender);
        employee.setNik(this.nik);
        employee.setBirthPlace(this.birthPlace);
        employee.setBirthDate(this.birthDate);
        employee.setReligion(this.religion);
        employee.setBloodType(this.bloodType);
        employee.setMainPhoneNumber(this.mainPhoneNumber);
        employee.setSecondaryPhoneNumber(this.secondaryPhoneNumber);
        employee.setEmail(this.email);
        employee.setEmergencyContact(this.emergencyContact);
        employee.setEmergencyPhone(this.emergencyPhone);
        employee.setAddress(this.address);
        employee.setPostcode(this.postcode);
        employee.setCity(this.city);
        employee.setEntryDate(this.entryDate);
        employee.setCreatedBy(this.createdBy);
        employee.setCreatedDate(this.createdDate);
        employee.setUpdatedBy(this.updatedBy);
        employee.setUpdatedDate(this.updatedDate);
        return employee;
    }

    public List<EducationCmd> getListPendidikan() {
        return listPendidikan;
    }

    public void setListPendidikan(List<EducationCmd> listPendidikan) {
        this.listPendidikan = listPendidikan;
    }

    public List<EducationCmd> getListPendidikanDelete() {
        return listPendidikanDelete;
    }

    public void setListPendidikanDelete(List<EducationCmd> listPendidikanDelete) {
        this.listPendidikanDelete = listPendidikanDelete;
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

    public List<EmployeeFamilyCmd> getListFamily() {
        return listFamily;
    }

    public void setListFamily(List<EmployeeFamilyCmd> listFamily) {
        this.listFamily = listFamily;
    }

    public List<EmployeeBenefitCmd> getListBenefit() {
        return listBenefit;
    }

    public void setListBenefit(List<EmployeeBenefitCmd> listBenefit) {
        this.listBenefit = listBenefit;
    }

    public List<EmployeeSalaryCmd> getListSalary() {
        return listSalary;
    }

    public void setListSalary(List<EmployeeSalaryCmd> listSalary) {
        this.listSalary = listSalary;
    }

    public List<EmployeeSkillsetCmd> getListSkillset() {
        return listSkillset;
    }

    public void setListSkillset(List<EmployeeSkillsetCmd> listSkillset) {
        this.listSkillset = listSkillset;
    }

    public List<EmployeeDocumentCmd> getListDocument() {
        return listDocument;
    }

    public void setListDocument(List<EmployeeDocumentCmd> listDocument) {
        this.listDocument = listDocument;
    }

    public List<EmployeeContractCmd> getListContract() {
        return listContract;
    }

    public void setListContract(List<EmployeeContractCmd> listContract) {
        this.listContract = listContract;
    }

    public List<EmployeePositionCmd> getListPosition() {
        return listPosition;
    }

    public void setListPosition(List<EmployeePositionCmd> listPosition) {
        this.listPosition = listPosition;
    }
}
