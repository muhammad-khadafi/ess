package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.LeaveRequest;

public class LeaveRequestCmd extends LeaveRequest {
    public LeaveRequest toLeaveRequest() {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(this.getId());
        leaveRequest.setEmployeeId(this.getEmployeeId());
        leaveRequest.setLeaveTypeId(this.getLeaveTypeId());
        leaveRequest.setStartDate(this.getStartDate());
        leaveRequest.setEndDate(this.getEndDate());
        leaveRequest.setNumOfDays(this.getNumOfDays());
        leaveRequest.setReason(this.getReason());
        leaveRequest.setStatus(this.getStatus());
        leaveRequest.setManagerId(this.getManagerId());
        leaveRequest.setDeputyDirectorId(this.getDeputyDirectorId());
        leaveRequest.setCreatedBy(this.getCreatedBy());
        leaveRequest.setCreatedDate(this.getCreatedDate());
        leaveRequest.setLastUpdatedBy(this.getLastUpdatedBy());
        leaveRequest.setLastUpdatedDate(this.getLastUpdatedDate());

        return leaveRequest;
    }
}
