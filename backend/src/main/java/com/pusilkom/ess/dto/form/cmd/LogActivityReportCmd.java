package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.LogActivityReport;

import java.util.Date;

public class LogActivityReportCmd extends LogActivityReport {
    public LogActivityReport toLogActivityReport(Integer idEmployee, Date startDate, Date endDate, String path){
        LogActivityReport logActivityReport = new LogActivityReport();
        logActivityReport.setEmployeeId(idEmployee);
        logActivityReport.setStartDate(startDate);
        logActivityReport.setEndDate(endDate);
        logActivityReport.setPath(path);

        return logActivityReport;
    }
}
