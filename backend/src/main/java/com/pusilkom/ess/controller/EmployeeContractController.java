package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeeContractCmd;
import com.pusilkom.ess.dto.form.search.EmployeeContractSearchForm;
import com.pusilkom.ess.dto.table.EmployeeContractItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeContract;
import com.pusilkom.ess.service.EmployeeContractService;
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
public class EmployeeContractController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    EmployeeContractService employeeContractService;

    @RequestMapping(value = "/employee-contract/add", method = RequestMethod.POST)
    public ResponseEntity postEmployeeContract(@Valid @RequestBody EmployeeContractCmd emp, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** EMPLOYEE CONTRACT CMD {}", d.toString(emp));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            employeeContractService.saveEmployeeContractCmd(emp);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error("POST EMPLOYEE CONTRACT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-contract/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid EmployeeContractSearchForm employeeContractSearchForm, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("employeeContractSearchForm : {}", d.toString(employeeContractSearchForm));
        log.info("Query : {}", d.toString(employeeContractSearchForm.getOrderQuery()));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeeContractItem> tablePagination = employeeContractService.getTablePagination(employeeContractSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE EMPLOYEE CONTRACT : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-contract/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeContractById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        log.info(">>> CHECK CONTRACT ID : {}", d.toString(id));

        try {
            EmployeeContract contract = employeeContractService.getEmployeeContractById(id);
            log.info(">>> FETCH EMPLOYEE CONTRACT : {}", d.toString(contract));
            if (contract == null) {
                return ResponseEntity.notFound().build();
            }
           responseEntity = ResponseEntity.ok(contract);
           log.info(">>> CHECK RESP ENT EMPLOYEE CONTRACT : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("GET EMPLOYEE CONTRACT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/employee-contract/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployeeContract(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            employeeContractService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE CONTRACT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}

