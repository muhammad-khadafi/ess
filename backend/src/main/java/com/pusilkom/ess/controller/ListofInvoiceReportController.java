package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.search.InvoiceReportSearchForm;
import com.pusilkom.ess.dto.table.InvoiceReportItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.InvoiceReport;
import com.pusilkom.ess.service.InvoiceReportService;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by taufan on 22/1/19.
 */

@RestController
public class ListofInvoiceReportController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    InvoiceReportService invoiceReportService;

    @RequestMapping(value = "/invoice-report/table", method = RequestMethod.GET)
    public ResponseEntity getTableLogActivity(InvoiceReportSearchForm invoiceReportSearchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("InvoiceReport Search Form : {}", d.toString(invoiceReportSearchForm));
        log.info("Query : {}", d.toString(invoiceReportSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        DateFormat formater = new SimpleDateFormat("MM-dd-yyyy");
        try {
            if(invoiceReportSearchForm.getStartDateString() != null && invoiceReportSearchForm.getStartDateString() != ""){
                invoiceReportSearchForm.setStartDate(formater.parse(invoiceReportSearchForm.getStartDateString()));
            }
            log.info("*** POST TABLE : {}", d.toString(invoiceReportSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<InvoiceReportItem> tablePagination = invoiceReportService.getTablePagination(invoiceReportSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE InvoiceReport ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/download/invoice-report/get-report/{id}", method = RequestMethod.GET)
    public ResponseEntity getReportPoc(@PathVariable Integer id,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception  {
        InvoiceReport invoiceReport = invoiceReportService.getById(id);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String formats = formatter.format(invoiceReport.getEndDate());
        String filenames = "Invoice-Report-" + formats +".pdf";

        ResponseEntity responseEntity = null;

        try {
            File files = new File(invoiceReport.getPath());
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
            log.info("RESPON GET Invoice Reports : " + pdfBytes.toString());
            responseEntity = ResponseEntity.ok(pdfBytes);
        } catch (Exception ex) {
            log.error("ERROR JASPER : "+ex.getMessage(), ex.getCause());
            ex.printStackTrace();
        }

        return responseEntity;
    }

    @RequestMapping(value = "/invoice-report/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLogActivityReport(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        InvoiceReport invoiceReport = invoiceReportService.getById(id);

        try {
            File files = new File(invoiceReport.getPath());
            if(!files.isDirectory()) files.delete();
            else System.out.println("============ Direktori tidak ditemeukan =============");


            invoiceReportService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE INVOICE REPORT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
