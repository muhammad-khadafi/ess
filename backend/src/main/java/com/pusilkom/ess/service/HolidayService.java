package com.pusilkom.ess.service;

import com.pusilkom.ess.model.Holiday;
import com.pusilkom.ess.model.HolidayExample;
import com.pusilkom.ess.model.mapper.HolidayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HolidayService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HolidayMapper holidayMapper;

    public boolean isHolidayByDate (Date dates) {
        HolidayExample ex = new HolidayExample();
        ex.createCriteria().andDateEqualTo(dates);

        List<Holiday> holidayList = holidayMapper.selectByExample(ex);

        if (holidayList.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    public Integer getHolidayRange (Date date1, Date date2) {
        HolidayExample ex = new HolidayExample();
        ex.createCriteria().andDateBetween(date1, date2);

        List<Holiday> holidayList = holidayMapper.selectByExample(ex);

        if (holidayList.isEmpty()) {
            return 0;
        } else {
            return holidayList.size();
        }

    }
}
