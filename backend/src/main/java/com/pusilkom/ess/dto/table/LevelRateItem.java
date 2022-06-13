package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.LevelRate;

public class LevelRateItem extends LevelRate {
    private String levelName;
    private String startDateString;
    private String endDateString;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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
}
