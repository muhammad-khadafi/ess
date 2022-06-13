package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeeCmd;
import com.pusilkom.ess.dto.form.cmd.EmployeeContractCmd;
import com.pusilkom.ess.dto.form.cmd.LetterCmd;
import com.pusilkom.ess.dto.form.search.EmployeeContractSearchForm;
import com.pusilkom.ess.dto.form.search.LevelSearchForm;
import com.pusilkom.ess.dto.form.search.ListSuratForm;
import com.pusilkom.ess.dto.table.*;
import com.pusilkom.ess.model.EmployeeContract;
import com.pusilkom.ess.model.Letter;
import com.pusilkom.ess.model.PettyCash;
import com.pusilkom.ess.model.Role;
import com.pusilkom.ess.service.EmployeeContractService;
import com.pusilkom.ess.service.LetterService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class LetterController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    LetterService LetterService;

    @RequestMapping(value = "/letter-dropdown", method = RequestMethod.GET)
    public ResponseEntity getListInformation() {
        ResponseEntity responseEntity = null;
        try {
            List<LetterItem> keteranganlist = LetterService.getKeteranganLetter();
            log.info("Cek isi role item : {}", d.toString(keteranganlist));
            responseEntity = ResponseEntity.ok(keteranganlist);
        } catch (Exception e) {
            log.error("GET ALL ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/letter-category", method = RequestMethod.GET)
    public ResponseEntity getListCategorySearchForm() {
        ResponseEntity responseEntity = null;
        try {
            List<LetterItem> keteranganlist = LetterService.getCategoryLetter();
            log.info("Cek isi role item : {}", d.toString(keteranganlist));
            responseEntity = ResponseEntity.ok(keteranganlist);
        } catch (Exception e) {
            log.error("GET ALL ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/letter-id", method = RequestMethod.GET)
    public ResponseEntity getListId() {
        ResponseEntity responseEntity = null;
        try {
            LetterItem keteranganlist = LetterService.getKeteranganId();
            log.info("Cek isi role item : {}", d.toString(keteranganlist));
            responseEntity = ResponseEntity.ok(keteranganlist);
        } catch (Exception e) {
            log.error("GET ALL ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/letter-submit", method = RequestMethod.POST)
    public ResponseEntity postLetter(@Valid @RequestBody LetterCmd letterCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("*** SEBELUM POST CMD : {}", d.toString(letterCmd));
        try {
            log.info("*** POST CMD : {}", d.toString(letterCmd));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            LetterService.saveCmd(letterCmd);
        } catch (Exception e) {
            log.error("POST EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
            return  responseEntity;
    }

    @RequestMapping(value = "/letter/lastrow", method = RequestMethod.GET)
    public ResponseEntity getLastRowLetter() {
        ResponseEntity responseEntity = null;
        try {
            LetterItem letter = LetterService.getLastRowLetter();
            log.info("cek last petty cash item : {}", d.toString(letter));
            responseEntity = ResponseEntity.ok(letter);
        }
        catch (Exception e) {
            log.error("GET ERR PETTY CASH", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/letter-list", method = RequestMethod.GET)
    public ResponseEntity getTableLetter
            (@Valid ListSuratForm listSuratForm, BindingResult result, HttpServletRequest httpServletRequest)
    {

        log.info("levelSearchForm : {}", d.toString(listSuratForm));
        log.info("Query : {}", d.toString(listSuratForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(listSuratForm));
            TablePagination<LetterItem> tablePagination = LetterService.getTablePagination(listSuratForm);
            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE project assignment : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

}



