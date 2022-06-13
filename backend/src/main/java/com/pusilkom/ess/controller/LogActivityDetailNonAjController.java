package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.common.DownloadResponse;
import com.pusilkom.ess.dto.form.cmd.LogActivityDetailCmd;
import com.pusilkom.ess.dto.form.search.LogActivityDetailSearchForm;
import com.pusilkom.ess.dto.form.search.LogActivityViewSearchForm;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.service.LogActivityDetailNonAjService;
import com.pusilkom.ess.service.ReportService;
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
public class LogActivityDetailNonAjController extends BaseRestController {
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    LogActivityDetailNonAjService logActivityDetailNonAjService;
    @Autowired
    DownloadUtil downloadUtil;
    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/log-act-detail-non-aj", method = RequestMethod.POST)
    public ResponseEntity postLogActDetail(@Valid @RequestBody LogActivityDetailCmd logActivityDetailCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(logActivityDetailCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            logActivityDetailNonAjService.saveCmd(logActivityDetailCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST LOG ACT DETAIL ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/log-act-detail-non-aj/table", method = RequestMethod.GET)
    public ResponseEntity getAllLogActivityDetail(LogActivityDetailSearchForm searchForm, HttpServletRequest httpServletRequest){
        log.info("Project Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;

        try {
            TablePagination<LogActivityDetailItem> tablePagination = logActivityDetailNonAjService.getAllLogActivityDetail(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE LOG ACT DETAIL ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/log-act-detail-non-aj/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLogActivityDetail(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            logActivityDetailNonAjService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE LOG ACT DETAIL ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/report/generateticket/logActivityNonAj", method = RequestMethod.POST)
    public ResponseEntity generateTicket(@RequestBody LogActivityDetailSearchForm logActivityDetailSearchForm,
                                         HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;
        try {

            String ticket = downloadUtil.generateTicket("/api/download/sample/cetakreport/logActivityNonAj/"
                    + logActivityDetailSearchForm.getIdEmployee()
                    + logActivityDetailSearchForm.getMonths()
                    + logActivityDetailSearchForm.getYears()
                    + logActivityDetailSearchForm.getDateAfter()
                    + logActivityDetailSearchForm.getDateBefore()
                    + logActivityDetailSearchForm.getProjectId()
                    + logActivityDetailSearchForm.getSubProjectId());
            log.info("REQUEST uri : {}", request.getRequestURI());
            log.info("TICKET : {}", ticket);

            responseEntity = ResponseEntity.ok(new DownloadResponse(ticket));
        } catch (Exception e) {
            log.error("GET TICKET", e);
            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/download/sample/cetakreport/logActivityNonAj/{employeeName}/{idEmployee}" +
            "/{days}/{months}/{years}/{dateBefore}/{dateAfter}/{projectId}/{subProjectId}",
            method = RequestMethod.GET)
    public ResponseEntity getReport(@PathVariable String employeeName, @PathVariable Integer idEmployee, @PathVariable Integer days,
                                    @PathVariable Integer months, @PathVariable Integer years, @PathVariable Date dateBefore,
                                    @PathVariable Date dateAfter, @PathVariable(required = false) Integer projectId, @PathVariable(required = false) Integer subProjectId,
                                    @RequestParam("ticket") String ticket, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception  {
        ResponseEntity responseEntity = null;

        try {
            byte[] pdfBytes = null;
            pdfBytes = reportService.generateLogActivityNonAjReport(idEmployee, months, years, dateBefore, dateAfter, projectId, subProjectId);

            response.reset();
            response.resetBuffer();

            String typeName = "per-employee-";
            if (subProjectId != 0) {
                typeName = "per-project-";
            }

            String filenames = "timesheet-" + typeName + years +"-"+ (months.toString().length() < 2 ? "0" + months : months)
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

    @RequestMapping(value = "/report/generateticket/assignment-report", method = RequestMethod.POST)
    public ResponseEntity generateTicketAssignmentReport(@RequestBody LogActivityDetailSearchForm logActivityDetailSearchForm,
                                         HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;
        try {

            String ticket = downloadUtil.generateTicket("/api/download/cetakreport/assignment-report/"
                    + logActivityDetailSearchForm.getDateBefore()
                    + logActivityDetailSearchForm.getDateAfter()
                    + logActivityDetailSearchForm.getProjectType());
            responseEntity = ResponseEntity.ok(new DownloadResponse(ticket));
        } catch (Exception e) {
            log.error("GET TICKET", e);
            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/download/cetakreport/assignment-report/{dateBefore}/{dateAfter}/{projectType}",
            method = RequestMethod.GET)
    public ResponseEntity getAssignmentReport(@PathVariable Date dateBefore, @PathVariable Date dateAfter, @PathVariable String projectType,
                                    @RequestParam("ticket") String ticket, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception  {
        ResponseEntity responseEntity = null;

        try {
            byte[] pdfBytes = null;
            pdfBytes = reportService.generateAssignmentReport(dateBefore, dateAfter, projectType);

            response.reset();
            response.resetBuffer();

            String filenames = "assignment-report.pdf";

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

    @RequestMapping(value = "/log-act-view-non-aj/table", method = RequestMethod.GET)
    public ResponseEntity getAllLogActivityView(LogActivityViewSearchForm searchForm, HttpServletRequest httpServletRequest){
        ResponseEntity responseEntity = null;
        searchForm.convertToDate();
        log.info("Log Activity View Search Form : {}", d.toString(searchForm));
        try {
            List<String[]> logActivities = logActivityDetailNonAjService.getAllLogActivityView(searchForm);
            responseEntity = ResponseEntity.ok(logActivities);

        } catch (Exception e) {
            log.error("TABLE LOG ACT VIEW ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

}
