package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.common.WorkingHour;
import com.pusilkom.ess.dto.form.search.EmployeeAttendanceSearchForm;
import com.pusilkom.ess.dto.table.EmployeeAttendanceItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeAttendance;
import com.pusilkom.ess.model.EmployeeAttendanceExample;
import com.pusilkom.ess.model.mapper.EmployeeAttendanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeAttendanceService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeAttendanceMapper employeeAttendanceMapper;

    public TablePagination<EmployeeAttendanceItem> getTablePagination(EmployeeAttendanceSearchForm searchForm) {
        searchForm.setValid(true);
        List<EmployeeAttendanceItem> listItem = employeeAttendanceMapper.getListEmployeeAttendance(searchForm);
        Integer i = 0;
        for (EmployeeAttendanceItem item : listItem){
            listItem.get(i).setDurationString(hourInStringFormatter(item.getDuration()));
            i++;
        }
        Long totalItemFiltered = employeeAttendanceMapper.getTotalEmployeeAttendance(searchForm);

        return new TablePagination<EmployeeAttendanceItem>(searchForm, listItem, totalItemFiltered);
    }

    @Transactional(readOnly = false)
    public void updateActivityDetail (Long idEmployeeAttendace, String activityDetail) {
        EmployeeAttendanceExample ex = new EmployeeAttendanceExample();
        ex.createCriteria().andIdEqualTo(idEmployeeAttendace);

        List<EmployeeAttendance> attendanceList = employeeAttendanceMapper.selectByExample(ex);
        EmployeeAttendance employeeAttendance = attendanceList.get(0);
        employeeAttendance.setActivityDetail(activityDetail);
        employeeAttendanceMapper.updateByPrimaryKeySelective(employeeAttendance);
    }

    public EmployeeAttendanceItem getSummary (EmployeeAttendanceSearchForm searchForm, Integer weeksofMonth) {
        EmployeeAttendanceItem item = new EmployeeAttendanceItem();
        List<EmployeeAttendanceItem> listItem = employeeAttendanceMapper.getAllListEmployeeAttendance(searchForm);
        Integer workingDays = 0;

        // set total working hour
        for (EmployeeAttendanceItem eItems : listItem) {
            if (eItems.getDuration() != null) {
                item.setTotalDuration(item.getTotalDuration() + eItems.getDuration());
                workingDays++;
            }
        }
        item.setWorkingMonthString(hourInStringFormatter(item.getTotalDuration()));

        //set total working days
        item.setTotalWork(workingDays);


        //set average working days
        if(item.getTotalWork() != 0.0)
            item.setAverageDailyWorkingHour(item.getTotalDuration()/item.getTotalWork());
        item.setAverageDailyWorkingHourString(hourInStringFormatter(item.getAverageDailyWorkingHour()));

        //set weekly working hour
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, searchForm.getYears());
        cal.set(Calendar.MONTH, searchForm.getMonths()-1);
        int maxWeek = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

        WorkingHour[] arrayOfWeeklyWorkingHour = new WorkingHour[maxWeek];
        for(int i = 0; i < maxWeek; i++){
            WorkingHour workingHour = new WorkingHour();
            workingHour.setWeekNumber(i+1);
            arrayOfWeeklyWorkingHour[i] = workingHour;
        }

        for (EmployeeAttendanceItem eItems : listItem) {
            if(eItems.getDuration() != null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(eItems.getDate());
                int numOfWeek = calendar.get(Calendar.WEEK_OF_MONTH);
                arrayOfWeeklyWorkingHour[numOfWeek-1].setDurationMin(arrayOfWeeklyWorkingHour[numOfWeek-1].getDurationMin() + eItems.getDuration());
            }
         }

        for (WorkingHour workingHour:
             arrayOfWeeklyWorkingHour) {
            workingHour.setDuratinStr(hourInStringFormatter(workingHour.getDurationMin()));
        }

         item.setWeeklyWorkingHour(arrayOfWeeklyWorkingHour);

        return item;
    }

    public String hourInStringFormatter(Float minutes) {
        if(minutes == null){
            return "";
        }
        if (minutes == 0.0){
            return "0 hour";
        }
        if(minutes.intValue() < 60){
            return minutes.intValue() + " minutes";
        }
        int getHours = minutes.intValue() / 60;
        int getMinutes = minutes.intValue() % 60;

        return getHours + " hours " + getMinutes + " minutes";
    }


}
