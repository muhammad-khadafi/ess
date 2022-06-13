package com.pusilkom.ess.dto.form.search;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ITF on 11/2/2018.
 */
public class PettyCashSearchForm extends BaseTableSearchForm {
    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;
    private String category;
    private String roleName;
    private Integer loginAs;
    private Integer idRole;
    private String pettyCashStatus;
    private Boolean isVueTable;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void convertToDate(){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        try {
            if(this.startDateString != null && this.startDateString != "") {
                Date date = format.parse(this.startDateString);
                this.setStartDate(date);
            }
            if(this.endDateString != null && this.endDateString != "") {
                Date date = format.parse(this.endDateString);
                this.setEndDate(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Integer getLoginAs() {
        return loginAs;
    }

    public void setLoginAs(Integer loginAs) {
        this.loginAs = loginAs;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getPettyCashStatus() {
        return pettyCashStatus;
    }

    public void setPettyCashStatus(String pettyCashStatus) {
        this.pettyCashStatus = pettyCashStatus;
    }

    public Boolean getIsVueTable() {
        return isVueTable;
    }

    public void setIsVueTable(Boolean isVueTable) {
        this.isVueTable = isVueTable;
    }
}
