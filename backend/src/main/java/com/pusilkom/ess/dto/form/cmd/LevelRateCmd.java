package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.LevelRate;
import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LevelRateCmd extends LevelRate {
    private String startDateString;
    private String endDateString;

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

    public LevelRate toLevelRate() throws ParseException {
        SimpleDateFormat ssdf = new SimpleDateFormat ("dd-MM-yyyy");
        LevelRate levelRate = new LevelRate();
        levelRate.setId(this.getId());
        levelRate.setRate(this.getRate());
        levelRate.setLevelId(this.getLevelId());

        if(!StringUtils.isEmpty(this.getStartDateString())) {
            levelRate.setStartDate(ssdf.parse(this.getStartDateString()));
        }

        if(!StringUtils.isEmpty(this.getEndDateString())) {
            levelRate.setEndDate(ssdf.parse(this.getEndDateString()));
        }

        return levelRate;
    }
}
