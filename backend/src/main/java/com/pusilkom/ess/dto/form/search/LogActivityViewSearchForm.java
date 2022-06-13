package com.pusilkom.ess.dto.form.search;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogActivityViewSearchForm {
    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;
    private Integer subProjectId;
    private Integer projectId;

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

    public Integer getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(Integer subProjectId) {
        this.subProjectId = subProjectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
}
