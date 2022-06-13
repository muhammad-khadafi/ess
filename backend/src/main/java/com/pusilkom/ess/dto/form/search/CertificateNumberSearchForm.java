package com.pusilkom.ess.dto.form.search;

import java.util.Date;

public class CertificateNumberSearchForm extends BaseTableSearchForm {

    private String category;
    private Date courseDate;
    private String courseName;
    private String certificateNumber;

    private Date courseDateStr;

    public Date getCourseDateStr() {
        return courseDateStr;
    }

    public void setCourseDateStr(Date courseDateStr) {
        this.courseDateStr = courseDateStr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(Date courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }
}
