package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.EmployeeAttendanceCmd;
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

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class MobileService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeAttendanceMapper employeeAttendanceMapper;

    public TablePagination<EmployeeAttendanceItem> getTablePagination(EmployeeAttendanceSearchForm searchForm) {
        List<EmployeeAttendanceItem> listItem = employeeAttendanceMapper.getListEmployeeAttendance(searchForm);
        Long totalItemFiltered = employeeAttendanceMapper.getTotalEmployeeAttendance(searchForm);

        return new TablePagination<EmployeeAttendanceItem>(searchForm, listItem, totalItemFiltered);
    }

    public void clockIn(EmployeeAttendanceCmd employeeAttendanceCmd) {
        employeeAttendanceCmd.setDate(new Date());
        employeeAttendanceCmd.setClockIn(new Date());
//        if (validateClockIn(employeeAttendanceCmd)) {
            EmployeeAttendance employeeAttendance = toEmployeeAttendance(employeeAttendanceCmd);
            employeeAttendanceMapper.insert(employeeAttendance);
//        }
    }

    private boolean validateClockIn(EmployeeAttendanceCmd employeeAttendanceCmd) {
        boolean isExist= false;

        EmployeeAttendanceExample employeeAttendanceExample = new EmployeeAttendanceExample();
        employeeAttendanceExample.createCriteria().andEmployeeIdEqualTo(employeeAttendanceCmd.getEmployeeId())
                .andDateEqualTo(employeeAttendanceCmd.getDate()).andClockInIsNotNull();
        List<EmployeeAttendance> employeeAttendanceList= employeeAttendanceMapper.selectByExample(employeeAttendanceExample);

        if(employeeAttendanceList.size() != 0)
            isExist = true;

        return !isExist;
    }

    private EmployeeAttendance toEmployeeAttendance(EmployeeAttendanceCmd employeeAttendanceCmd) {
        EmployeeAttendance employeeAttendance = new EmployeeAttendance();
        employeeAttendance.setDate(employeeAttendanceCmd.getDate());
        employeeAttendance.setClockIn(employeeAttendanceCmd.getClockIn());
        employeeAttendance.setClockInLocation(employeeAttendanceCmd.getClockInLocation());
        employeeAttendance.setEmployeeId(employeeAttendanceCmd.getEmployeeId());
        employeeAttendance.setIsValid(true);
        return employeeAttendance;
    }

    public void clockOut(EmployeeAttendanceCmd employeeAttendanceCmd) {
        EmployeeAttendance employeeAttendance = getClockInData(employeeAttendanceCmd.getEmployeeId());
        employeeAttendance.setClockOutLocation(employeeAttendanceCmd.getClockOutLocation());
        employeeAttendance.setClockOut(new Date());

        Instant instant1 = Instant.ofEpochMilli(employeeAttendance.getClockIn().getTime());
        LocalTime timeClockIn = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault()).toLocalTime();

        Instant instant2 = Instant.ofEpochMilli(employeeAttendance.getClockOut().getTime());
        LocalTime timeClockOut = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault()).toLocalTime();

        Long duration = Duration.between(timeClockIn, timeClockOut).toMinutes();
        employeeAttendance.setDuration(duration.floatValue());

        employeeAttendanceMapper.updateByPrimaryKeySelective(employeeAttendance);

    }

    private EmployeeAttendance getClockInData(Integer employeeId) {
        EmployeeAttendanceExample employeeAttendanceExample = new EmployeeAttendanceExample();
        employeeAttendanceExample.createCriteria()
                .andEmployeeIdEqualTo(employeeId)
                .andDateEqualTo(new Date());
        List<EmployeeAttendance> employeeAttendanceList = employeeAttendanceMapper.selectByExample(employeeAttendanceExample);
        return employeeAttendanceList.get(0);
    }

    public String getClockInOutStatus(Integer employeeId) {
        EmployeeAttendanceExample example = new EmployeeAttendanceExample();
        example.createCriteria()
                .andEmployeeIdEqualTo(employeeId)
                .andDateEqualTo(new Date());
        List<EmployeeAttendance> employeeAttendanceList = employeeAttendanceMapper.selectByExample(example);
        String result = null;
        if (!employeeAttendanceList.isEmpty()) {
            result = (employeeAttendanceList.get(0).getClockIn() != null ? employeeAttendanceList.get(0).getClockIn().toString() : null)
                    + "|" + (employeeAttendanceList.get(0).getClockOut() != null ? employeeAttendanceList.get(0).getClockOut().toString() : null);

        }
        return result;
    }

    public EmployeeAttendance getClockInOutStatusReturnObject(Integer employeeId) {
        EmployeeAttendanceExample example = new EmployeeAttendanceExample();
        example.createCriteria()
                .andEmployeeIdEqualTo(employeeId)
                .andDateEqualTo(new Date());
        List<EmployeeAttendance> employeeAttendanceList = employeeAttendanceMapper.selectByExample(example);
        String result = null;
        if (!employeeAttendanceList.isEmpty()) {
            return employeeAttendanceList.get(0);

        }

        return new EmployeeAttendance();
    }

    public ArrayList<EmployeeAttendanceItem> viewLog(Integer id, String monthStr, String yearStr) {
//        SimpleDateFormat sdfMonth = new SimpleDateFormat("M");
//        Integer month = Integer.parseInt(sdfMonth.format(new Date()).toString());

        Integer month = Integer.parseInt(monthStr);
        Integer year = Integer.parseInt(yearStr);
        return employeeAttendanceMapper.viewLog(id, month, year);
    }

    public List<EmployeeAttendance> getAutoClockOutData() {
        return null;
    }

    public void processAutoClockOut() {
        System.out.println("auto clockout process starting..");
        EmployeeAttendanceExample employeeAttendanceExample = new EmployeeAttendanceExample();
        employeeAttendanceExample.createCriteria().andClockOutIsNull();
        List<EmployeeAttendance> employeeAttendanceList = employeeAttendanceMapper.selectByExample(employeeAttendanceExample);
        for (EmployeeAttendance employeeAttendance : employeeAttendanceList) {
            employeeAttendance.setIsValid(false);
            employeeAttendanceMapper.updateByPrimaryKeySelective(employeeAttendance);
        }
        System.out.println("auto clockout process done..");
    }
}
