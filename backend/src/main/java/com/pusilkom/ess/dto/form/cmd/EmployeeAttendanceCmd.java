package com.pusilkom.ess.dto.form.cmd;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class EmployeeAttendanceCmd implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date date;
    private Date clockIn;
    private String clockInLocation;
    private LocalDateTime clockOut;
    private String clockOutLocation;
    private String activityDetail;
    private Integer employeeId;
    private Float duration;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getClockIn() {
        return clockIn;
    }

    public void setClockIn(Date clockIn) {
        this.clockIn = clockIn;
    }

    public String getClockInLocation() {
        return clockInLocation;
    }

    public void setClockInLocation(String clockInLocation) {
        this.clockInLocation = clockInLocation;
    }

    public LocalDateTime getClockOut() {
        return clockOut;
    }

    public void setClockOut(LocalDateTime clockOut) {
        this.clockOut = clockOut;
    }

    public String getClockOutLocation() {
        return clockOutLocation;
    }

    public void setClockOutLocation(String clockOutLocation) {
        this.clockOutLocation = clockOutLocation;
    }

    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }
}