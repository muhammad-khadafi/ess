package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.common.DownloadResponse;
import com.pusilkom.ess.dto.form.cmd.MasterReferenceCmd;
import com.pusilkom.ess.dto.form.search.CertificateNumberSearchForm;
import com.pusilkom.ess.dto.form.search.EmployeeSearchForm;
import com.pusilkom.ess.dto.form.search.MasterReferenceSearchForm;
import com.pusilkom.ess.dto.table.CertificateNumberItem;
import com.pusilkom.ess.dto.table.MasterReferenceItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.MasterReferenceDetail;
import com.pusilkom.ess.model.CertificateNumber;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.service.CertificateNumberService;
import com.pusilkom.ess.service.MasterReferenceService;
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
import java.util.List;

/**
 * Created by muhammad.khadafi on 31/10/18.
 */
@RestController
public class CertificateNumberController extends BaseRestController {

    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    CertificateNumberService certificateNumberService;


    @RequestMapping(value = "/certificate-generator", method = RequestMethod.POST)
    public ResponseEntity certificateGenerator(@Valid @RequestBody CertificateNumber certificateNumber, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(certificateNumber));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            String generatedCertificateNumber = certificateNumberService.certificateNumberGenerator(certificateNumber);
            responseEntity = ResponseEntity.ok(generatedCertificateNumber);
        } catch (Exception e) {
            log.error("GENERATING CERTIFICATE NUMBER ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/certificate/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid CertificateNumberSearchForm certificateNumberSearchForm, BindingResult result) {

       ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(certificateNumberSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<CertificateNumberItem> tablePagination = certificateNumberService.getTablePagination(certificateNumberSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/certificate-number/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCertificateNumber(@PathVariable Long id) {
        ResponseEntity responseEntity = null;

        try {
            certificateNumberService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE CERTIFICATE NUMBER ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG + " : " + e));
        }

        return responseEntity;
    }

}
