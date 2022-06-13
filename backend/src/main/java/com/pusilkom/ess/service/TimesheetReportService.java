package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.search.TimeSheetSummarySearchForm;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.table.TimesheetReportItem;
import com.pusilkom.ess.model.mapper.EmployeeMapper;
import com.pusilkom.ess.model.mapper.LogActivityDetailMapper;
import com.pusilkom.ess.model.mapper.LogActivityDetailNonAjMapper;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional
public class TimesheetReportService {
    @Autowired
    LogActivityDetailNonAjMapper logActivityDetailNonAjMapper;

    @Autowired
    LogActivityDetailMapper logActivityDetailMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;

    public TablePagination<TimesheetReportItem> getTimesheetSummaryData(TimeSheetSummarySearchForm searchForm) {
        try {
            searchForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'").parse(searchForm.getStartDateStr()));
            searchForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'").parse(searchForm.getEndDateStr()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<TimesheetReportItem> nonAJItems = logActivityDetailNonAjMapper.getTimesheetSummaryData(searchForm);
        List<TimesheetReportItem> AJItems = logActivityDetailMapper.getTimesheetSummaryData(searchForm);

        List<TimesheetReportItem> allEmployee = employeeMapper.getAllEmployeeActive();

        for (TimesheetReportItem employee : allEmployee) {
            employee.setStartDate(searchForm.getStartDateStr());
            employee.setEndDate(searchForm.getEndDateStr());
            boolean isFind = false;
            employee.setAJ(false);

            for (TimesheetReportItem nonAJ : nonAJItems) {
                if (employee.getEmployeeId().equals(nonAJ.getEmployeeId())) {
                    employee.setTotalDay(nonAJ.getTotalDay());
                    isFind = true;
                    break;
                }
            }

            if (!isFind) {
                for (TimesheetReportItem AJ : AJItems) {
                    if (employee.getEmployeeId().equals(AJ.getEmployeeId())) {
                        employee.setTotalDay(AJ.getTotalDay());
                        employee.setAJ(true);
                        break;
                    }
                }
            }

        }

        Integer totalItem = allEmployee.size();

        return new TablePagination<TimesheetReportItem>(searchForm, allEmployee, totalItem.longValue());
    }


}
