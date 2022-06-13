package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.common.DownloadResponse;
import com.pusilkom.ess.dto.form.cmd.EmployeeDocumentCmd;
import com.pusilkom.ess.dto.form.search.EmployeeDocumentSearchForm;
import com.pusilkom.ess.dto.table.EmployeeDocumentItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeDocument;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.service.EmployeeDocumentService;
import com.pusilkom.ess.service.MasterReferenceService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.DownloadUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import com.pusilkom.ess.util.UploadUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeDocumentController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";
    final static String TICKET_NOTVALID_MSG = "Ticket is not valid";
    @Value("${dir.upload.employee}") String DIR_UPLOAD_EMPLOYEE;

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    EmployeeDocumentService employeeDocumentService;
    @Autowired
    MasterReferenceService masterReferenceService;
    @Autowired
    UploadUtil uploadUtil;
    @Autowired
    DownloadUtil downloadUtil;


    @RequestMapping(value = "/employee-document/upload", method = RequestMethod.POST)
    public ResponseEntity uploadEmployeeDocument(@RequestParam(value = "fileDoc", required = false)MultipartFile[] fileUpload) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** EMPLOYEE DOCUMENT CMD UP {}", d.toString(fileUpload));

            Map<String, String> response = null ;

            if(fileUpload.length > 0) {
                response = uploadUtil.saveEmployeeDocument(fileUpload);
            }
            responseEntity = ResponseEntity.ok(response);
        }
        catch (Exception e) {
            log.error("POST EMPLOYEE DOCUMENT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-document/add", method = RequestMethod.POST)
    public ResponseEntity postEmployeeDocument(@Valid @RequestBody EmployeeDocumentCmd emp,
                                               BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** EMPLOYEE DOCUMENT CMD {}", d.toString(emp));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            employeeDocumentService.saveEmployeeDocumentCmd(emp);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error("POST EMPLOYEE DOCUMENT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-document/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid EmployeeDocumentSearchForm employeeDocumentSearchForm, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("employeeDocumentSearchForm : {}", d.toString(employeeDocumentSearchForm));
        log.info("Query : {}", d.toString(employeeDocumentSearchForm.getOrderQuery()));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeeDocumentItem> tablePagination = employeeDocumentService.getTablePagination(employeeDocumentSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE EMPLOYEE DOCUMENT : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-document/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeDocumentById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        log.info(">>> CHECK DOCUMENT ID : {}", d.toString(id));

        try {
            EmployeeDocument document = employeeDocumentService.getEmployeeDocumentById(id);
            log.info(">>> FETCH EMPLOYEE DOCUMENT : {}", d.toString(document));
            if (document == null) {
                return ResponseEntity.notFound().build();
            }
           responseEntity = ResponseEntity.ok(document);
           log.info(">>> CHECK RESP ENT EMPLOYEE DOCUMENT : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("GET EMPLOYEE DOCUMENT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/employee-document/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployeeDocument(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            employeeDocumentService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE DOCUMENT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/document/all", method = RequestMethod.GET)
    public ResponseEntity getAllMasterReferenceDocument() {
        ResponseEntity responseEntity = null;
        try
        {
            List<MasterReference> dropdown = masterReferenceService.getMasterReferenceByReferenceType("Document");
            log.info("Check all fetched document : {}", d.toString(dropdown));
            responseEntity = ResponseEntity.ok(dropdown);
        }
        catch (Exception e)
        {
            log.error("GET ALL DOCUMENT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-document/checkduplicate", method = RequestMethod.POST)
    public ResponseEntity checkDuplicateEmployeeDocument(@Valid @RequestBody EmployeeDocumentCmd employeeDocumentCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            log.info("*** PUT CMD : {}", d.toString(employeeDocumentCmd));
            Boolean pa  = employeeDocumentService.checkDuplicate(employeeDocumentCmd);
            responseEntity = ResponseEntity.ok(pa);
        } catch (Exception e) {
            log.error("CHECK DUPLICATE EMPLOYEE DOCUMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value="/employee-document/file/{id}", method = RequestMethod.POST)
    public ResponseEntity getFileEmployeeDocById(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;

        try {
            String ticket = downloadUtil.generateTicket("/api/download/employee-document/" + id);
            log.info("REQUEST uri : {}", request.getRequestURI());
            log.info("TICKET : {}", ticket);

            responseEntity = ResponseEntity.ok(new DownloadResponse(ticket));
        } catch (Exception e) {
            log.error("GET TICKET", e);
            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value="/download/employee-document/{id}", method = RequestMethod.GET)
    public void getDownloadEmployeeDocById(@PathVariable Integer id, @RequestParam("ticket") String ticket, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            boolean isVerified = downloadUtil.verifyTicket(request, ticket);

            log.info("REQUEST uri : {}", request.getRequestURI());
            log.info("TICKET : {}", ticket);
            log.info("isVerified : {}", isVerified);

            if (!isVerified) {
                response.getOutputStream().write(TICKET_NOTVALID_MSG.getBytes());
                return;
            }
            EmployeeDocument doc = employeeDocumentService.getEmployeeDocumentById(id);
            String ext1 = FilenameUtils.getExtension(DIR_UPLOAD_EMPLOYEE + "/" + doc.getFileName());
            String tipeKonten = "image/" + ext1;
            InputStream is = new FileInputStream(DIR_UPLOAD_EMPLOYEE + "/" + doc.getFileName());
            byte[] fileBytes = IOUtils.toByteArray(is);

            response.setContentType(tipeKonten);
            response.setHeader("Content-Disposition", "attachment;filename="+ doc.getFileName());

            response.getOutputStream().write(fileBytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error("DOWNLOAD FILE", e);
            response.getOutputStream().write(TICKET_NOTVALID_MSG.getBytes());
            return;
        }
    }
}

