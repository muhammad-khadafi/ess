package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeeSalaryCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSalarySearchForm;
import com.pusilkom.ess.dto.table.EmployeeSalaryItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeSalary;
import com.pusilkom.ess.service.EmployeeSalaryService;
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
public class EmployeeSalaryController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    EmployeeSalaryService employeeSalaryService;

    @RequestMapping(value = "/employee-salary/add", method = RequestMethod.POST)
    public ResponseEntity postEmployeeBenefit(@Valid @RequestBody EmployeeSalaryCmd emp, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** EMPLOYEE SALARY CMD {}", d.toString(emp));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            employeeSalaryService.saveEmployeeSalaryCmd(emp);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error("POST EMPLOYEE SALARY ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-salary/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid EmployeeSalarySearchForm employeeSalarySearchForm, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("employeeSalarySearchForm : {}", d.toString(employeeSalarySearchForm));
        log.info("Query : {}", d.toString(employeeSalarySearchForm.getOrderQuery()));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeeSalaryItem> tablePagination = employeeSalaryService.getTablePagination(employeeSalarySearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE EMPLOYEE SALARY : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-salary/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeSalaryById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        log.info(">>> CHECK SALARY ID : {}", d.toString(id));

        try {
            EmployeeSalary salary = employeeSalaryService.getEmployeeSalaryById(id);
            log.info(">>> FETCH EMPLOYEE SALARY : {}", d.toString(salary));
            if (salary == null) {
                return ResponseEntity.notFound().build();
            }
           responseEntity = ResponseEntity.ok(salary);
           log.info(">>> CHECK RESP ENT EMPLOYEE SALARY : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("GET EMPLOYEE SALARY ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/employee-salary/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployeeSalary(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            employeeSalaryService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE SALARY ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}

