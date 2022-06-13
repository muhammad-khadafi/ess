package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.common.DownloadResponse;
import com.pusilkom.ess.dto.form.cmd.InvoiceReportCmd;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.model.SubProject;
import com.pusilkom.ess.service.MasterReferenceService;
import com.pusilkom.ess.service.ReportService;
import com.pusilkom.ess.service.SubProjectService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.DownloadUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by muhammad.khadafi on 31/10/18.
 */
@RestController
public class InvoiceReportController extends BaseRestController {

    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    SubProjectService subProjectService;
    @Autowired
    MasterReferenceService masterReferenceService;
    @Autowired
    DownloadUtil downloadUtil;
    @Autowired
    ReportService reportService;


    @RequestMapping(value = "/invoice-report/getSubProject", method = RequestMethod.GET)
    public ResponseEntity getAllSubProject() {
        ResponseEntity responseEntity = null;

        try {
            List<SubProject> subProjectList = subProjectService.getAllSubProject();

            if (subProjectList == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(subProjectList);
        } catch (Exception e) {
            log.error("GET SUB PROJECT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/invoice-report/getSpv", method = RequestMethod.GET)
    public ResponseEntity getAllSpv() {
        ResponseEntity responseEntity = null;

        try {
            List<MasterReference> spvList = masterReferenceService.getMasterReferenceByReferenceType("Supervisor");

            if (spvList == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(spvList);
        } catch (Exception e) {
            log.error("GET SUB PROJECT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/report/generateticket/invoicereport", method = RequestMethod.POST)
    public ResponseEntity generateTicketInvoiceReport(@Valid @RequestBody InvoiceReportCmd invoiceReportCmd,
                                         HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;
        try {

            String ticket = downloadUtil.generateTicket("/api/download/generatereport/invoicereport/"
                    + invoiceReportCmd.getStartDate() + invoiceReportCmd.getEndDate()
                    + invoiceReportCmd.getSubProjectId() + invoiceReportCmd.getSpvName()
                    + invoiceReportCmd.getClientPersonel() + invoiceReportCmd.getClientRole()
                    + invoiceReportCmd.getPusilkomPersonel() + invoiceReportCmd.getPusilkomRole());

            log.info("REQUEST uri : {}", request.getRequestURI());
            log.info("TICKET : {}", ticket);

            responseEntity = ResponseEntity.ok(new DownloadResponse(ticket));
        } catch (Exception e) {
            log.error("GET TICKET", e);
            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/download/generatereport/invoicereport/{startDate}/{endDate}/{dateString}/{subProjectId}/{subProjectString}/{spvName}/{clientPersonel}/{clientRole}/{pusilkomPersonel}/{pusilkomRole}", method = RequestMethod.GET)
    public ResponseEntity generateReportInvoiceReport(@PathVariable Date startDate, @PathVariable Date endDate, @PathVariable String dateString, @PathVariable String subProjectId,
                                                      @PathVariable String subProjectString, @PathVariable String spvName, @PathVariable String clientPersonel, @PathVariable String clientRole,
                                                      @PathVariable String pusilkomPersonel, @PathVariable String pusilkomRole, @RequestParam("ticket") String ticket,
                                                      HttpServletRequest request, HttpServletResponse response) throws Exception  {

        ResponseEntity responseEntity = null;
        String pusilkomRole2 = "", clientRole2 = "";
        String[] listPusilkomRole, listClientRole;

        try {
            byte[] pdfBytes = null;
            listPusilkomRole = pusilkomRole.split("\\|");
            if(listPusilkomRole.length > 1) {
                pusilkomRole = listPusilkomRole[0];
                pusilkomRole2 = listPusilkomRole[1];
            }
            listClientRole = clientRole.split("\\|");
            if(listClientRole.length > 1) {
                clientRole = listClientRole[0];
                clientRole2 = listClientRole[1];
            }
            pdfBytes = reportService.generateInvoiceReport(startDate, endDate, subProjectId, spvName, clientPersonel, clientRole, clientRole2, pusilkomPersonel, pusilkomRole, pusilkomRole2);

            response.reset();
            response.resetBuffer();

            String fileName = dateString;
            if(!subProjectString.contains("null")){
                fileName = fileName + "-" + subProjectString.replace(" ", "-");
            }
            if(!spvName.contains("null")){
                fileName = fileName + "-" + spvName.replace(" ", "-");
            }
            //this line to make browser download the file
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=Invoice-Report-" +fileName+ ".pdf");

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
