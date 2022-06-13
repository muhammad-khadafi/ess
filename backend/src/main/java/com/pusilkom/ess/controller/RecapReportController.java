package com.pusilkom.ess.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pusilkom.ess.service.ReportService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecapReportController extends  BaseRestController {
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/download/sample/cetakRecapReport/logActivity/{employeeName}/{idEmployee}/{days}/{months}/{monthsString}/{years}/{dateBefore}/{dateAfter}/{subProjectId}/{recapOption}/{pusilkomPersonnel}/{pusilkomRole}/{clientPersonnel}/{clientRole}", method = RequestMethod.GET)
    public ResponseEntity getReport(@PathVariable String employeeName, @PathVariable Integer idEmployee, @PathVariable Integer days,
                                    @PathVariable Integer months, @PathVariable String monthsString, @PathVariable Integer years, @PathVariable Date dateBefore,
                                    @PathVariable Date dateAfter, @PathVariable Integer subProjectId, @PathVariable String recapOption,
                                    @PathVariable String pusilkomPersonnel, @PathVariable String pusilkomRole, @PathVariable String clientPersonnel, @PathVariable String clientRole,
                                    @RequestParam("ticket") String ticket, HttpServletRequest request, HttpServletResponse response) throws Exception  {
        ResponseEntity responseEntity = null;

        try {
//            System.out.println("+++++ get date : " + dateBefore + " " + dateAfter);
            byte[] pdfBytes = null;
            pdfBytes = reportService.generateRecapLogActivityReport((idEmployee == 0 ? null : idEmployee), dateBefore, dateAfter, recapOption, monthsString, years, (subProjectId == 0 ? null : subProjectId), pusilkomPersonnel, pusilkomRole, clientPersonnel, clientRole);

            response.reset();
            response.resetBuffer();
            String filenames = "Recap-" + recapOption + "-" + years +"-"+ (months.toString().length() < 2 ? "0" + months : months)
                    +"-"+ (days.toString().length() < 2 ? "0" + days : days) + (idEmployee == 0 ? "" : "-"+ employeeName.replace(' ', '-')) +".pdf";

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
