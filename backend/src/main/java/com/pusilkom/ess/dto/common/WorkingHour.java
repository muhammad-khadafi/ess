package com.pusilkom.ess.dto.common;

/**
 * Created by muhammad.khadafi on 07/02/2020.
 */
public class WorkingHour {

    private Integer weekNumber;
    private float durationMin;
    private String duratinStr;

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public float getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(float durationMin) {
        this.durationMin = durationMin;
    }

    public String getDuratinStr() {
        return duratinStr;
    }

    public void setDuratinStr(String duratinStr) {
        this.duratinStr = duratinStr;
    }
}
