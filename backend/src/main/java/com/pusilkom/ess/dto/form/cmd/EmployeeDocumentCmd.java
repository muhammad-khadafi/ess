package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.EmployeeDocument;

import java.util.Date;

public class EmployeeDocumentCmd {
    private Integer id;
    private Integer employeeId;
    private Integer documentId;
    private String fileName;
    private Date uploadTime;
    private Integer createdBy;
    private Integer updatedBy;
    private Date createdDate;
    private Date updatedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
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

    public EmployeeDocument toEmployeeDocument() {
        EmployeeDocument emp = new EmployeeDocument();
        emp.setId(this.id);
        emp.setDocumentId(this.documentId);
        emp.setEmployeeId(this.employeeId);
        emp.setFileName(this.fileName);
        emp.setUploadTime(this.uploadTime);
        if(this.id == null){
            emp.setCreatedBy(this.createdBy);
            emp.setCreatedDate(this.createdDate);
        } else {
            emp.setCreatedBy(this.createdBy);
            emp.setCreatedDate(this.createdDate);
            emp.setUpdatedBy(this.updatedBy);
            emp.setUpdatedDate(this.updatedDate);
        }
        return emp;
    }
}
