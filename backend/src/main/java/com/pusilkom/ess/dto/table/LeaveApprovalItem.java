package com.pusilkom.ess.dto.table;

import java.util.Date;

/**
 * Created by muhammad.khadafi on 1/9/17.
 */
public class LeaveApprovalItem {

    private Long id;
    private String employeeName;
    private String leaveTypeName;
    private Date startDate;
    private Date endDate;
    private Integer numOfDays;
    private String reason;
    private String startDateStr;
    private String endDateStr;
    private String status;
    private String leaveDateDetail;
    private String managerName;
    private String deputyDirectorName;

    public LeaveApprovalItem() {
    }

    public LeaveApprovalItem(LeaveApprovalItem leaveApprovalItemCopy) {
        this.id = leaveApprovalItemCopy.id;
        this.employeeName = leaveApprovalItemCopy.employeeName;
        this.leaveTypeName = leaveApprovalItemCopy.leaveTypeName;
        this.startDate = leaveApprovalItemCopy.startDate;
        this.endDate = leaveApprovalItemCopy.endDate;
        this.numOfDays = leaveApprovalItemCopy.numOfDays;
        this.reason = leaveApprovalItemCopy.reason;
        this.startDateStr = leaveApprovalItemCopy.startDateStr;
        this.endDateStr = leaveApprovalItemCopy.endDateStr;
        this.status = leaveApprovalItemCopy.status;
        this.leaveDateDetail = leaveApprovalItemCopy.leaveDateDetail;
        this.managerName = leaveApprovalItemCopy.managerName;
        this.deputyDirectorName = leaveApprovalItemCopy.deputyDirectorName;
    }


    public String getLeaveDateDetail() {
        return leaveDateDetail;
    }

    public void setLeaveDateDetail(String leaveDateDetail) {
        this.leaveDateDetail = leaveDateDetail;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getDeputyDirectorName() {
        return deputyDirectorName;
    }

    public void setDeputyDirectorName(String deputyDirectorName) {
        this.deputyDirectorName = deputyDirectorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
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
}
