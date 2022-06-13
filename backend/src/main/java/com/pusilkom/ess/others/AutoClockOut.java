package com.pusilkom.ess.others;

import com.pusilkom.ess.model.EmployeeAttendance;
import com.pusilkom.ess.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muhammad.khadafi on 27/01/2020.
 */

@Component
public class AutoClockOut {

    /** Author : muhammad.khadafi
    * Add springboot native scheduling
    * cron param list =>  @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
    */

    @Autowired
    MobileService mobileService;

    @Scheduled(cron = "0 0 23 * * ?")
    public void process(){
        try{
            mobileService.processAutoClockOut();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
