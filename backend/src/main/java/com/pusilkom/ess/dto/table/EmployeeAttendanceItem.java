package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.dto.common.WorkingHour;
import com.pusilkom.ess.model.EmployeeAttendance;

import java.util.List;

public class EmployeeAttendanceItem extends EmployeeAttendance {
    private String dateString;
    private String checkInString;
    private String checkOutString;
    private Integer totalWork;
    private float totalDuration;
    private String workPerWeek;
    private String durationString;
    private String workingMonthString;
    private float averageDailyWorkingHour;
    private String averageDailyWorkingHourString;
    private WorkingHour[] weeklyWorkingHour;

    public WorkingHour[] getWeeklyWorkingHour() {
        return weeklyWorkingHour;
    }

    public void setWeeklyWorkingHour(WorkingHour[] weeklyWorkingHour) {
        this.weeklyWorkingHour = weeklyWorkingHour;
    }

    public String getAverageDailyWorkingHourString() {
        return averageDailyWorkingHourString;
    }

    public void setAverageDailyWorkingHourString(String averageDailyWorkingHourString) {
        this.averageDailyWorkingHourString = averageDailyWorkingHourString;
    }

    public float getAverageDailyWorkingHour() {
        return averageDailyWorkingHour;
    }

    public void setAverageDailyWorkingHour(float averageDailyWorkingHour) {
        this.averageDailyWorkingHour = averageDailyWorkingHour;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getCheckInString() {
        return checkInString;
    }

    public void setCheckInString(String checkInString) {
        this.checkInString = checkInString;
    }

    public String getCheckOutString() {
        return checkOutString;
    }

    public void setCheckOutString(String checkOutString) {
        this.checkOutString = checkOutString;
    }

    public Integer getTotalWork() {
        return totalWork;
    }

    public void setTotalWork(Integer totalWork) {
        this.totalWork = totalWork;
    }

    public float getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(float totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getWorkPerWeek() {
        return workPerWeek;
    }

    public void setWorkPerWeek(String workPerWeek) {
        this.workPerWeek = workPerWeek;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public String getWorkingMonthString() {
        return workingMonthString;
    }

    public void setWorkingMonthString(String workingMonthString) {
        this.workingMonthString = workingMonthString;
    }
}
