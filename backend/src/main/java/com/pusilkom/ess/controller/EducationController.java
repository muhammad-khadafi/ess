package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EducationCmd;
import com.pusilkom.ess.dto.form.search.EmployeeEducationSearchForm;
import com.pusilkom.ess.dto.table.EmployeeEducationItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeEducation;
import com.pusilkom.ess.service.EmployeeEducationService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EducationController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    EmployeeEducationService educationService;

    @RequestMapping(value = "/education/add", method = RequestMethod.POST)
    public ResponseEntity postPendidikan(@Valid @RequestBody EducationCmd eduCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST PURCHASE ORDER CMD {}", d.toString(eduCmd));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            educationService.saveCmd(eduCmd);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error("POST PURCHASE ORDER", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
    @RequestMapping(value = "/education/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid EmployeeEducationSearchForm employeeEducationSearchForm, BindingResult result) {

        log.info("EmployeeEducationSearchForm : {}", d.toString(employeeEducationSearchForm));
        log.info("Query : {}", d.toString(employeeEducationSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(employeeEducationSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeeEducationItem> tablePagination = educationService.getTablePagination(employeeEducationSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE EDUCATION : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/education/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeEducationById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        log.info(">>> get education by coba : {}", d.toString("hahahahahha"));

        try {
            EmployeeEducation employeeEdu = educationService.getEmployeeEducationById(id);
            log.info(">>> get education by coba : {}", d.toString(employeeEdu));
            if (employeeEdu == null) {
                return ResponseEntity.notFound().build();
            }
           responseEntity = ResponseEntity.ok(employeeEdu);
            log.info(">>> get education by id : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("GET EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/education/{id}", method = RequestMethod.PUT)
    public ResponseEntity putEducation(@PathVariable Integer id, @Valid @RequestBody EducationCmd eduCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(eduCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            educationService.saveCmd(eduCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT EDUCARION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/education/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployeeEducation(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            educationService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE EDUCATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}

