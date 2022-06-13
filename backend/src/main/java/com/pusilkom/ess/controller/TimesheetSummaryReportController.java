package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.common.DownloadResponse;
import com.pusilkom.ess.dto.form.cmd.LogActivityDetailCmd;
import com.pusilkom.ess.dto.form.search.LogActivityDetailSearchForm;
import com.pusilkom.ess.dto.form.search.LogActivityViewSearchForm;
import com.pusilkom.ess.dto.form.search.TimeSheetSummarySearchForm;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.table.TimesheetReportItem;
import com.pusilkom.ess.service.LogActivityDetailNonAjService;
import com.pusilkom.ess.service.ReportService;
import com.pusilkom.ess.service.TimesheetReportService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.DownloadUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class TimesheetSummaryReportController extends BaseRestController {
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    TimesheetReportService timesheetReportService;
    @Autowired
    DownloadUtil downloadUtil;
    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/timesheet-summary/table", method = RequestMethod.GET)
    public ResponseEntity getSummaryData(TimeSheetSummarySearchForm searchForm, HttpServletRequest httpServletRequest){
        log.info("Project Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;

        try {
            TablePagination<TimesheetReportItem> tablePagination;

            if(searchForm.getStartDateStr() == null || searchForm.getEndDateStr() == null){
                tablePagination = null;
            } else {
                tablePagination = timesheetReportService.getTimesheetSummaryData(searchForm);
            }

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE LOG ACT DETAIL ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/report/generateticket/timesheet-detail", method = RequestMethod.POST)
    public ResponseEntity generateTicket(@RequestBody TimesheetReportItem timesheetReportItem,
                                         HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;
        try {

            String ticket = downloadUtil.generateTicket("/api/download/sample/cetakreport/logActivityNonAj/"
                    + timesheetReportItem.getEmployeeId()
                    + timesheetReportItem.getEmployeeName()
                    + timesheetReportItem.getStartDate()
                    + timesheetReportItem.getEndDate()
                    + timesheetReportItem.getAJ());
            log.info("REQUEST uri : {}", request.getRequestURI());
            log.info("TICKET : {}", ticket);

            responseEntity = ResponseEntity.ok(new DownloadResponse(ticket));
        } catch (Exception e) {
            log.error("GET TICKET", e);
            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/download/cetakreport/timesheet-detail/{employeeName}/{employeeId}" +
            "/{startDate}/{endDate}/{aj}",
            method = RequestMethod.GET)
    public ResponseEntity getReport(@PathVariable String employeeName, @PathVariable Integer employeeId, @PathVariable String startDate,
                                    @PathVariable String endDate, @PathVariable Boolean aj,
                                    @RequestParam("ticket") String ticket, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception  {
        ResponseEntity responseEntity = null;

        try {
            byte[] pdfBytes = null;
            pdfBytes = reportService.generateTimesheetDetailReport(employeeId, employeeName, startDate, endDate, aj);

            response.reset();
            response.resetBuffer();

            String filenames = "timesheet_detail"
                    +"-"+ employeeName.replace(' ', '-') +".pdf";

            //this line to make browser download the file
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + filenames);

            try {
                response.getOutputStream().write(pdfBytes);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                response.flushBuffer();
            }
            catch (Exception e) {
                log.error("ERROR JASPER : "+e.getMessage(), e.getCause());
                e.printStackTrace();
            }
            log.info("RESPON : " + pdfBytes.toString());
            responseEntity = ResponseEntity.ok(pdfBytes);
        } catch (Exception ex) {
            log.error("ERROR JASPER : "+ex.getMessage(), ex.getCause());
            ex.printStackTrace();
        }

        return responseEntity;
    }

}
