package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeeFamilyCmd;
import com.pusilkom.ess.dto.form.search.EmployeeFamilySearchForm;
import com.pusilkom.ess.dto.table.EmployeeFamilyItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeFamily;
import com.pusilkom.ess.service.EmployeeFamilyService;
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
public class EmployeeFamilyController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    EmployeeFamilyService employeeFamilyService;

    @RequestMapping(value = "/employee-family/add", method = RequestMethod.POST)
    public ResponseEntity postEmployeeFamily(@Valid @RequestBody EmployeeFamilyCmd emp, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** EMPLOYEE FAMILY CMD {}", d.toString(emp));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            employeeFamilyService.saveEmployeeFamilyCmd(emp);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error("POST EMPLOYEE FAMILY ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-family/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid EmployeeFamilySearchForm employeeFamilySearchForm, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("EmployeeSearchForm : {}", d.toString(employeeFamilySearchForm));
        log.info("Query : {}", d.toString(employeeFamilySearchForm.getOrderQuery()));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeeFamilyItem> tablePagination = employeeFamilyService.getTablePagination(employeeFamilySearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE EMPLOYEE FAMILY : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-family/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeFamilyById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        log.info(">>> CHECK FAMILY ID : {}", d.toString(id));

        try {
            EmployeeFamily family = employeeFamilyService.getEmployeeFamilyById(id);
            log.info(">>> FETCH EMPLOYEE FAMILY : {}", d.toString(family));
            if (family == null) {
                return ResponseEntity.notFound().build();
            }
           responseEntity = ResponseEntity.ok(family);
           log.info(">>> CHECK RESP ENT EMPLOYEE FAMILY : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("GET EMPLOYEE FAMILY ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/employee-family/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployeeFamily(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            employeeFamilyService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE FAMILY ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}

