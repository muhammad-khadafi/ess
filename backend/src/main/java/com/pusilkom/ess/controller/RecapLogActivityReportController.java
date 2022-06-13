package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.search.LogActivityReportSearchForm;
import com.pusilkom.ess.dto.form.search.RecapLogActivityReportSearchForm;
import com.pusilkom.ess.dto.table.LogActivityReportItem;
import com.pusilkom.ess.dto.table.RecapLogActivityReportItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.LogActivityReport;
import com.pusilkom.ess.model.RecapLogActivityReport;
import com.pusilkom.ess.service.LogActivityReportService;
import com.pusilkom.ess.service.RecapLogActivityReportService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

/**
 * Created by taufan on 8/1/19.
 */

@RestController
public class RecapLogActivityReportController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    RecapLogActivityReportService recapLogActivityReportService;

    @RequestMapping(value = "/recap-log-activity-report/table", method = RequestMethod.GET)
    public ResponseEntity getTableRecapLogActivity(RecapLogActivityReportSearchForm recapLogActivityReportSearchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("logActivityReport Search Form : {}", d.toString(recapLogActivityReportSearchForm));
        log.info("Query : {}", d.toString(recapLogActivityReportSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(recapLogActivityReportSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<RecapLogActivityReportItem> tablePagination = recapLogActivityReportService.getTablePagination(recapLogActivityReportSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE logActivityReport ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/download/recap-log-activity-report/get-report/{id}/{employeeName}", method = RequestMethod.GET)
    public ResponseEntity getReportPoc(@PathVariable Integer id, @PathVariable String employeeName,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception  {
        RecapLogActivityReport recapLogActivityReport = recapLogActivityReportService.getById(id);

        ResponseEntity responseEntity = null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formats = formatter.format(recapLogActivityReport.getEndDate());
        String filenames = "Recap-Log-Activity-" + formats + "-" + employeeName.replace(' ', '-') +".pdf";

        try {
            File files = new File(recapLogActivityReport.getPath());
            byte[] pdfBytes = null;
            pdfBytes = IOUtils.toByteArray(new FileInputStream(files));

            response.reset();
            response.resetBuffer();

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

    @RequestMapping(value = "/recap-log-activity-report/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLogActivityReport(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        RecapLogActivityReport recapLogActivityReport = recapLogActivityReportService.getById(id);
        try {
            File files = new File(recapLogActivityReport.getPath());
            if(!files.isDirectory()) files.delete();

            recapLogActivityReportService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE LOG ACTIVITY REPORT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
