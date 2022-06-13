package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.search.RecapInvoiceReportSearchForm;
import com.pusilkom.ess.dto.form.search.RecapLogActivityReportSearchForm;
import com.pusilkom.ess.dto.table.RecapInvoiceReportItem;
import com.pusilkom.ess.dto.table.RecapLogActivityReportItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.RecapInvoiceReport;
import com.pusilkom.ess.model.RecapLogActivityReport;
import com.pusilkom.ess.service.RecapInvoiceReportService;
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
public class RecapInvoiceReportController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    RecapInvoiceReportService recapInvoiceReportService;

    @RequestMapping(value = "/recap-invoice-report/table", method = RequestMethod.GET)
    public ResponseEntity getTableRecapLogActivity(RecapInvoiceReportSearchForm recapInvoiceReportSearchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("logActivityReport Search Form : {}", d.toString(recapInvoiceReportSearchForm));
        log.info("Query : {}", d.toString(recapInvoiceReportSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(recapInvoiceReportSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<RecapInvoiceReportItem> tablePagination = recapInvoiceReportService.getTablePagination(recapInvoiceReportSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE logActivityReport ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/download/recap-invoice-report/get-report/{id}/{employeeName}", method = RequestMethod.GET)
    public ResponseEntity getReportPoc(@PathVariable Integer id, @PathVariable String employeeName,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception  {
        RecapInvoiceReport recapInvoiceReport = recapInvoiceReportService.getById(id);

        ResponseEntity responseEntity = null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formats = formatter.format(recapInvoiceReport.getEndDate());
        String filenames = "Recap-Invoice-" + formats + "-" + employeeName.replace(' ', '-') +".pdf";

        try {
            File files = new File(recapInvoiceReport.getPath());
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

    @RequestMapping(value = "/recap-invoice-report/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLogActivityReport(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        RecapInvoiceReport recapInvoiceReport = recapInvoiceReportService.getById(id);
        try {
            File files = new File(recapInvoiceReport.getPath());
            if(!files.isDirectory()) files.delete();

            recapInvoiceReportService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE LOG ACTIVITY REPORT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
