package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.common.DownloadResponse;
import com.pusilkom.ess.dto.form.cmd.EmployeeCmd;
import com.pusilkom.ess.dto.form.cmd.InvoiceReportCmd;
import com.pusilkom.ess.dto.form.cmd.PettyCashCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSearchForm;
import com.pusilkom.ess.dto.form.search.LevelSearchForm;
import com.pusilkom.ess.dto.form.search.PettyCashSearchForm;
import com.pusilkom.ess.dto.table.EmployeeItem;
import com.pusilkom.ess.dto.table.LevelRateItem;
import com.pusilkom.ess.dto.table.PettyCashItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.EmployeeDetail;
import com.pusilkom.ess.model.*;
import com.pusilkom.ess.service.EmployeeService;
import com.pusilkom.ess.service.PettyCashService;
import com.pusilkom.ess.service.ReportService;
import com.pusilkom.ess.util.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by arief on 10/31/18.
 */
@RestController
public class PettyCashController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";
    final static String TICKET_NOTVALID_MSG = "Ticket is not valid";
    @Value("${dir.proof.of.payment.pettycash}") String DIR_UPLOAD_PETTY_CASH;

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    PettyCashService pettyCashService;
    @Autowired
    ReportService reportService;
    @Autowired
    DownloadUtil downloadUtil;
    @Autowired
    UploadUtil uploadUtil;


    @RequestMapping(value = "/petty-cash/add", method = RequestMethod.POST)
    public ResponseEntity postPettyCash(@Valid @RequestBody PettyCashCmd pettyCashCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("*** POST CMD : {}", d.toString(pettyCashCmd));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            Integer pettyCashId = pettyCashService.saveCmd(pettyCashCmd);
            responseEntity = ResponseEntity.ok(pettyCashId);
        } catch (Exception e) {
            log.error("POST CMD", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/petty-cash/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid PettyCashSearchForm pettyCashSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {
        pettyCashSearchForm.convertToDate();
        log.info("pettyCashSearchForm : {}", d.toString(pettyCashSearchForm));
        log.info("Query : {}", d.toString(pettyCashSearchForm.getOrderQuery()));
        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(pettyCashSearchForm));
            TablePagination<PettyCashItem> tablePagination = pettyCashService.getTablePagination(pettyCashSearchForm);
            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE Petty Cash : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/petty-cash/view/{id}", method = RequestMethod.GET)
    public ResponseEntity getPettyCashById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            PettyCashItem pa = pettyCashService.getPettyCashItemById(id);
            log.info(">>> get petty cash : {}", d.toString(pa));
            if (pa == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(pa);
            log.info(">>>  get petty cash : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("get petty cash", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/funding-source/all", method = RequestMethod.GET)
    public ResponseEntity getAllFundingSource() {
        ResponseEntity responseEntity = null;
        try {
            List<FundingSource> fundingList = pettyCashService.getAllFundingSource();
            log.info("cek fundingList item : {}", d.toString(fundingList));
            responseEntity = ResponseEntity.ok(fundingList);
        }
        catch (Exception e) {
            log.error("GET ALL FUNDING", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/petty-cash/lastrow", method = RequestMethod.GET)
    public ResponseEntity getPettyCashLastRow() {
        ResponseEntity responseEntity = null;
        try {
            PettyCash pettycash = pettyCashService.getLastRowPettyCash();
            log.info("cek last petty cash item : {}", d.toString(pettycash));
            responseEntity = ResponseEntity.ok(pettycash);
        }
        catch (Exception e) {
            log.error("GET ERR PETTY CASH", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/petty-cash/{id}", method = RequestMethod.PUT)
    public ResponseEntity putPettyCash(@PathVariable Integer id, @RequestBody PettyCashCmd pettyCashCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(pettyCashCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            pettyCashService.saveCmd(pettyCashCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT PETTY CASH", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value="/download/petty-cash/{id}", method = RequestMethod.GET)
    public void getDownloadPettyCashById(@PathVariable Integer id, @RequestParam("ticket") String ticket, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            boolean isVerified = downloadUtil.verifyTicket(request, ticket);

            log.info("REQUEST uri : {}", request.getRequestURI());
            log.info("TICKET : {}", ticket);
            log.info("isVerified : {}", isVerified);

            if (!isVerified) {
                response.getOutputStream().write(TICKET_NOTVALID_MSG.getBytes());
                return;
            }
            PettyCash pettyCash = pettyCashService.getPettyCashById(id);
            String ext1 = FilenameUtils.getExtension(DIR_UPLOAD_PETTY_CASH + "/" + pettyCash.getUploadPath());
            String tipeKonten = "image/" + ext1;
            InputStream is = new FileInputStream(DIR_UPLOAD_PETTY_CASH + "/" + pettyCash.getUploadPath());
            byte[] fileBytes = IOUtils.toByteArray(is);

            response.setContentType(tipeKonten);
            response.setHeader("Content-Disposition", "attachment;filename="+ pettyCash.getUploadPath());

            response.getOutputStream().write(fileBytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error("DOWNLOAD FILE", e);
            response.getOutputStream().write(TICKET_NOTVALID_MSG.getBytes());
            return;
        }
    }

    @RequestMapping(value="/petty-cash/file/{id}", method = RequestMethod.POST)
    public ResponseEntity getFilePettyCashById(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;

        try {
            String ticket = downloadUtil.generateTicket("/api/download/petty-cash/" + id);
            log.info("REQUEST uri : {}", request.getRequestURI());
            log.info("TICKET : {}", ticket);

            responseEntity = ResponseEntity.ok(new DownloadResponse(ticket));
        } catch (Exception e) {
            log.error("GET TICKET", e);
            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/petty-cash/upload/{pettyId}", method = RequestMethod.POST)
    public ResponseEntity uploadProofOfPayment(@RequestParam(value = "fileDoc", required = false)MultipartFile[] fileUpload, @PathVariable Integer pettyId) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** PETTY DOCUMENT CMD UP {}", d.toString(fileUpload));
            log.info("*** PETTY DOCUMENT CMD UP {}", d.toString(pettyId));
            Map<String, String> response = null ;

            if(fileUpload.length > 0) {
                response = uploadUtil.saveFileDoc(DIR_UPLOAD_PETTY_CASH, fileUpload);
                PettyCash petty = pettyCashService.getPettyCashById(pettyId);
                petty.setUploadPath(response.get("fileDoc"));
                pettyCashService.update(petty);
            }
            responseEntity = ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error("POST PETTY CASH ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/report/generateticket/pettycashreport", method = RequestMethod.POST)
    public ResponseEntity generateTicketPettyCashReport(@Valid @RequestBody PettyCashItem pettyCashItem,
                                                      HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;
        try {
            log.info("*** PettyCashItem {}", d.toString(pettyCashItem));
            String ticket = downloadUtil.generateTicket("/api/download/generatereport/pettycashreport/"
                    + pettyCashItem.getReqDateString() + pettyCashItem.getReqNumber()
                    + pettyCashItem.getCategory()
                    + pettyCashItem.getPaidToName() + pettyCashItem.getReqAmount()
                    + pettyCashItem.getAmountInWord());

            log.info("REQUEST uri : {}", request.getRequestURI());
            log.info("TICKET : {}", ticket);

            responseEntity = ResponseEntity.ok(new DownloadResponse(ticket));
        } catch (Exception e) {
            log.error("GET TICKET", e);
            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/download/generatereport/pettycashreport/{reqDateString}/{reqNumber}/{category}/{paidToName}/{reqAmount}/{amountInWord}", method = RequestMethod.GET)
    public ResponseEntity generatePettyCashReport(@PathVariable String reqDateString, @PathVariable String reqNumber, @PathVariable String category, @PathVariable String paidToName, @PathVariable String reqAmount,
                                                      @PathVariable String amountInWord, @RequestParam("ticket") String ticket,
                                                      HttpServletRequest request, HttpServletResponse response) throws Exception  {

        log.info("*** ticket {}", d.toString(ticket));
        ResponseEntity responseEntity = null;

        try {
            byte[] pdfBytes = null;
            String myInput = reqNumber;
            String myOutput = myInput.replaceAll("-", "/");
            reqNumber = myOutput;
            pdfBytes = reportService.generatePettyCashReport(reqDateString, reqNumber, category, paidToName, reqAmount, amountInWord);

            response.reset();
            response.resetBuffer();

            //this line to make browser download the file
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=Petty-Cash-Report-" +new DateTime().toString("yyyyMMddHHmmss") + ".pdf");

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

    @RequestMapping(value = "/petty-cash/confirmfinal/{id}", method = RequestMethod.PUT)
    public ResponseEntity putPettyCashFinalConfirmation(@PathVariable Integer id, @Valid @RequestBody PettyCashCmd pettyCashCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** GET ID : {}", d.toString(id));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            PettyCash pettyCash = pettyCashService.getPettyCashById(id);
            pettyCash.setIsDone(true);
            pettyCash.setPaidDate(new Date());
            pettyCash.setPaidBy(pettyCashCmd.getPaidBy());
            if(pettyCashCmd.getProcessedBy() != null) {
                pettyCash.setProcessedBy(pettyCashCmd.getProcessedBy());
            } else {
                pettyCash.setProcessedBy(CommonUtil.getUserId());
            }
            pettyCashService.update(pettyCash);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT PETTY CASH", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value="/image/petty-cash/{id}", method = RequestMethod.GET)
    public ResponseEntity getImagePettyCashById(@PathVariable Integer id) throws IOException {
        ResponseEntity responseEntity = null;
        try {
            PettyCash pettyCash = pettyCashService.getPettyCashById(id);
            String ext1 = FilenameUtils.getExtension(DIR_UPLOAD_PETTY_CASH + "/" + pettyCash.getUploadPath());
            String tipeKonten = "image/" + ext1;
            InputStream is = new FileInputStream(DIR_UPLOAD_PETTY_CASH + "/" + pettyCash.getUploadPath());
            byte[] fileBytes = IOUtils.toByteArray(is);
            String encoded = Base64.getEncoder().encodeToString(fileBytes);
            responseEntity = ResponseEntity.ok(encoded);
        } catch (Exception e) {
            log.error("PUT PETTY CASH", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/petty-cash/unapproved", method = RequestMethod.GET)
    public ResponseEntity getListPettyCashUnapproved() {
        ResponseEntity responseEntity = null;
        try {
            List<PettyCashItem> listPettyCash = pettyCashService.getListPettyCashUnapproved();
            log.info(">>> List of Petty Cash Item : {}", d.toString(listPettyCash));
            responseEntity = ResponseEntity.ok(listPettyCash);
        } catch (Exception e) {
            log.error("Petty Cash Item Error : ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/petty-cash/cancel/{id}", method = RequestMethod.PUT)
    public ResponseEntity putPettyCashCancellation(@PathVariable Integer id, @Valid @RequestBody PettyCashCmd pettyCashCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("data cmd : {}", d.toString(pettyCashCmd));
        try {
            log.info("*** GET ID : {}", d.toString(id));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            PettyCash pettyCash = pettyCashService.getPettyCashById(id);
            if(pettyCashCmd.getProcessedBy() != null) {
                pettyCash.setProcessedBy(pettyCashCmd.getProcessedBy());
            } else {
                pettyCash.setProcessedBy(CommonUtil.getUserId());
            }
            pettyCash.setReasonOfCancellation(pettyCashCmd.getReasonOfCancellation());
            pettyCashService.update(pettyCash);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT PETTY CASH", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/petty-cash/guest/add", method = RequestMethod.POST)
    public ResponseEntity postPettyCashGuest(@Valid @RequestBody PettyCashCmd pettyCashCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("*** POST CMD : {}", d.toString(pettyCashCmd));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            Integer pettyCashId = pettyCashService.saveCmd(pettyCashCmd);
            responseEntity = ResponseEntity.ok(pettyCashId);
        } catch (Exception e) {
            log.error("POST CMD", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/petty-cash/user/{idUser}", method = RequestMethod.GET)
    public ResponseEntity getPettyCashByIdUser(@PathVariable("idUser") Integer idUser) {
        ResponseEntity responseEntity = null;
        try {
            List<PettyCashItem> pettyCashList = pettyCashService.getListPettyCashByIdUser(idUser);
            log.info("pettyCashList : {}", d.toString(pettyCashList));
            responseEntity = ResponseEntity.ok(pettyCashList);
        }
        catch (Exception e) {
            log.error("GET ERR pettyCashList", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
}
