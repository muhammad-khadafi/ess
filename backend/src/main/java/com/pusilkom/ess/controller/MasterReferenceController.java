package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.common.DownloadResponse;
import com.pusilkom.ess.dto.form.cmd.MasterReferenceCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSearchForm;
import com.pusilkom.ess.dto.form.search.MasterReferenceSearchForm;
import com.pusilkom.ess.dto.table.MasterReferenceItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.MasterReferenceDetail;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.service.MasterReferenceService;
import com.pusilkom.ess.service.ReportService;
import com.pusilkom.ess.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by muhammad.khadafi on 31/10/18.
 */
@RestController
public class MasterReferenceController extends BaseRestController {

    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    MasterReferenceService masterReferenceService;

    @Autowired
    DownloadUtil downloadUtil;

    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/master-reference", method = RequestMethod.POST)
    public ResponseEntity postMasterReference(@Valid @RequestBody MasterReferenceCmd masterReferenceCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(masterReferenceCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            masterReferenceService.saveCmd(masterReferenceCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST MASTER REFERENCE ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/master-reference/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid MasterReferenceSearchForm masterReferenceSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("Master Reference Search Form : {}", d.toString(masterReferenceSearchForm));
        log.info("Query : {}", d.toString(masterReferenceSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(masterReferenceSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<MasterReferenceItem> tablePagination = masterReferenceService.getTablePagination(masterReferenceSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE MASTER REFERENCE ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/master-reference/{id}", method = RequestMethod.GET)
    public ResponseEntity getMasterReference(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            MasterReference masterReference = masterReferenceService.getById(id);

            if (masterReference == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(new MasterReferenceDetail(masterReference));
        } catch (Exception e) {
            log.error("GET MASTER REFERENCE ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/master-reference/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMasterReference(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            masterReferenceService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE MASTER REFERENCE ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG + " : " + e));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/master-reference/{id}", method = RequestMethod.PUT)
    public ResponseEntity putMasterReference(@PathVariable Integer id, @Valid @RequestBody MasterReferenceCmd masterReferenceCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(masterReferenceCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            masterReferenceService.updateCmd(masterReferenceCmd, id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("UPDATE MASTER REFERENCE ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/master-reference/getReference/{ref}", method = RequestMethod.GET)
    public ResponseEntity getMasterReferenceByReferenceType(@PathVariable String ref) {
        ResponseEntity responseEntity = null;

        try {
            List<MasterReference> masterReference = masterReferenceService.getMasterReferenceByReferenceType(ref);

            if (masterReference == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(masterReference);
        } catch (Exception e) {
            log.error("GET MASTER REFERENCE ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

//    @PreAuthorize("hasAuthority('POST_TICKETCETAKREPORT_ID')")
    @RequestMapping(value = "/report/generateticket", method = RequestMethod.POST)
    public ResponseEntity generateTicket(@Valid @RequestBody EmployeeSearchForm employeeSearchForm,
                                               HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;
        try {

            String ticket = downloadUtil.generateTicket("/api/download/sample/cetakreport/"
                    + employeeSearchForm.getName() + employeeSearchForm.getNik());
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

    @RequestMapping(value = "/download/sample/cetakreport/{nama}/{nik}", method = RequestMethod.GET)
    public ResponseEntity getReport(@PathVariable String nama, @PathVariable String nik, @RequestParam("ticket") String ticket,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception  {
        ResponseEntity responseEntity = null;

        try {
            byte[] pdfBytes = null;
            pdfBytes = reportService.generateSampleReport(nama, nik);

            response.reset();
            response.resetBuffer();

            //this line to make browser download the file
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=SampleReport.pdf");

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

    @RequestMapping(value = "/report/download", method = RequestMethod.POST)
    public ResponseEntity downloadReport(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        ResponseEntity responseEntity = null;

        try {
            reportService.getDownloadFile();
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("ERROR JASPER : "+ex.getMessage(), ex.getCause());
        }

        return responseEntity;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseEntity testApi(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        ResponseEntity responseEntity = null;

        try {
            log.info("Done");
            responseEntity = ResponseEntity.ok("true");
        } catch (Exception ex) {
            log.error("ERROR : "+ex.getMessage(), ex.getCause());
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(new Exception(), ERROR_MSG));
        }

        return responseEntity;
    }
}
