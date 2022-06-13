package com.pusilkom.ess.dto.table;

import java.util.Date;

public class LeaveRequestItem {
    private Long id;
    private Long employeeId;
    private Long leaveTypeId;
    private String leaveTypeIdString;
    private Date startDate;
    private String startDateString;
    private Date endDate;
    private String endDateString;
    private Integer numOfDays;
    private String numOfDaysString;
    private String reason;
    private String status;
    private Integer createdBy;
    private Date createdDate;
    private Integer lastUpdatedBy;
    private Date lastUpdatedDate;
    private Long managerId;
    private Long deputyDirectorId;
    private String leaveDateDetail;
    private Boolean isCancel;
    private String docPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

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

    public Integer getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(Integer numOfDays) {
        this.numOfDays = numOfDays;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getDeputyDirectorId() {
        return deputyDirectorId;
    }

    public void setDeputyDirectorId(Long deputyDirectorId) {
        this.deputyDirectorId = deputyDirectorId;
    }

    public String getLeaveDateDetail() {
        return leaveDateDetail;
    }

    public void setLeaveDateDetail(String leaveDateDetail) {
        this.leaveDateDetail = leaveDateDetail;
    }

    public Boolean getCancel() {
        return isCancel;
    }

    public void setCancel(Boolean cancel) {
        isCancel = cancel;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
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

    public String getNumOfDaysString() {
        return numOfDaysString;
    }

    public void setNumOfDaysString(String numOfDaysString) {
        this.numOfDaysString = numOfDaysString;
    }

    public String getLeaveTypeIdString() {
        return leaveTypeIdString;
    }

    public void setLeaveTypeIdString(String leaveTypeIdString) {
        this.leaveTypeIdString = leaveTypeIdString;
    }
}
