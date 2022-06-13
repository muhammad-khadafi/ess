package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.common.DownloadResponse;
import com.pusilkom.ess.dto.form.cmd.LogActivityDetailCmd;
import com.pusilkom.ess.dto.form.search.LogActivityDetailSearchForm;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.service.LogActivityDetailService;
import com.pusilkom.ess.service.LogActivityReportService;
import com.pusilkom.ess.service.ProjectAssignmentService;
import com.pusilkom.ess.service.ReportService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.DownloadUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import com.pusilkom.ess.validator.form.cmd.LogActivityDetailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

@RestController
public class LogActivityDetailController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    LogActivityDetailService logActivityDetailService;
    @Autowired
    LogActivityDetailValidator logActivityDetailValidator;
    @Autowired
    DownloadUtil downloadUtil;
    @Autowired
    ReportService reportService;
    @Autowired
    LogActivityReportService logActivityReportService;
    @Autowired
    ProjectAssignmentService projectAssignmentService;

    @Value("${dir.report.temp}")
    String DIR_REPORT_TEMP;

    @InitBinder("logActivityDetailCmd")
    protected void registerLogActivityDetailValidator(WebDataBinder binder){
        binder.setValidator(logActivityDetailValidator);
    }

    @RequestMapping(value = "/log-act-detail", method = RequestMethod.POST)
    public ResponseEntity postLogActDetail(@Valid @RequestBody LogActivityDetailCmd logActivityDetailCmd, BindingResult result){
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(logActivityDetailCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            logActivityDetailService.saveCmd(logActivityDetailCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST LOG ACT DETAIL ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/log-act-detail/{id}", method = RequestMethod.GET)
    public ResponseEntity getLogActDetailById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            LogActivityDetailItem logActDetail= logActivityDetailService.getById(id);
            log.info("Cek isi LOG ACT DETAIL : {}", d.toString(logActDetail));
            responseEntity = ResponseEntity.ok(logActDetail);
        }
        catch (Exception e)
        {
            log.error("GET LOG ACT DETAIL", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/log-act-detail/{id}", method = RequestMethod.PUT)
    public ResponseEntity putProject(@PathVariable("id") Integer id,@Valid @RequestBody LogActivityDetailCmd logActivityDetailCmd, BindingResult result)
    {
        ResponseEntity responseEntity = null;
        try
        {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            logActivityDetailService.saveCmd(logActivityDetailCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT LOG ACT DETAIL", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/log-act-detail/table", method = RequestMethod.GET)
    public ResponseEntity getAllLogActivityDetail(LogActivityDetailSearchForm searchForm, HttpServletRequest httpServletRequest){
        log.info("Project Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
//        LogActivityDetailSearchForm searchForm = new LogActivityDetailSearchForm();
        try {
            TablePagination<LogActivityDetailItem> tablePagination = logActivityDetailService.getAllLogActivityDetail(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE LOG ACT DETAIL ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/log-act-detail/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLogActivityDetail(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            logActivityDetailService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE LOG ACT DETAIL ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/report/generateticket/logActivity", method = RequestMethod.POST)
    public ResponseEntity generateTicket(@RequestBody LogActivityDetailSearchForm logActivityDetailSearchForm,
                                         HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;
        try {

            String ticket = downloadUtil.generateTicket("/api/download/sample/cetakreport/logActivity/"
                    + logActivityDetailSearchForm.getIdEmployee()
                    + logActivityDetailSearchForm.getMonths()
                    + logActivityDetailSearchForm.getYears()
                    + logActivityDetailSearchForm.getDateAfter()
                    + logActivityDetailSearchForm.getDateBefore());
            log.info("REQUEST uri : {}", request.getRequestURI());
            log.info("TICKET : {}", ticket);

            responseEntity = ResponseEntity.ok(new DownloadResponse(ticket));
        } catch (Exception e) {
            log.error("GET TICKET", e);
            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    //Message for error
    final static String TICKET_NOTVALID_MSG = "Ticket is not valid";

    @RequestMapping(value = "/download/sample/cetakreport/logActivity/{employeeName}/{idEmployee}/{days}/{months}/{years}/{dateBefore}/{dateAfter}", method = RequestMethod.GET)
    public ResponseEntity getReport(@PathVariable String employeeName, @PathVariable Integer idEmployee, @PathVariable Integer days,
                                    @PathVariable Integer months, @PathVariable Integer years, @PathVariable Date dateBefore,
                                    @PathVariable Date dateAfter, @RequestParam("ticket") String ticket,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception  {
        ResponseEntity responseEntity = null;

        try {
//            System.out.println("+++++ get date : " + dateBefore + " " + dateAfter);
            byte[] pdfBytes = null;
            pdfBytes = reportService.generateLogActivityReport(idEmployee, months, years, dateBefore, dateAfter);

            response.reset();
            response.resetBuffer();
            String filenames = "Log-Activity-" + years +"-"+ (months.toString().length() < 2 ? "0" + months : months)
                    +"-"+ (days.toString().length() < 2 ? "0" + days : days) +"-"+ employeeName.replace(' ', '-') +".pdf";

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

    @RequestMapping(value = "/log-act-detail/checkProjectEmployee", method = RequestMethod.POST)
    public ResponseEntity checkProjectEmployee( @RequestBody LogActivityDetailCmd logActivityDetailCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(logActivityDetailCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Integer projectAssignmentId = projectAssignmentService.checkProjectEmployee(logActivityDetailCmd);
            responseEntity = ResponseEntity.ok(projectAssignmentId);
        } catch (Exception e) {
            log.error("POST PROJECT ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
