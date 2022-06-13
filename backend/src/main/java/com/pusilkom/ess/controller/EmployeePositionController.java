package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeePositionCmd;
import com.pusilkom.ess.dto.form.search.EmployeePositionSearchForm;
import com.pusilkom.ess.dto.table.EmployeePositionItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.service.EmployeePositionService;
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

@RestController
public class EmployeePositionController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    EmployeePositionService employeePositionService;

    @RequestMapping(value = "/employee-position", method = RequestMethod.POST)
    public ResponseEntity postEmployeePosition(@RequestBody EmployeePositionCmd employeePositionCmd, BindingResult result){
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(employeePositionCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            employeePositionService.saveCmd(employeePositionCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST EMPLOYEE POSITION ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-position/table", method = RequestMethod.GET)
    public ResponseEntity postEmployeePosition(EmployeePositionSearchForm searchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("employee-position Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(searchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeePositionItem> tablePagination = employeePositionService.getTablePagination(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE PROJECT ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-position/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployeePositionById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            EmployeePositionItem item = employeePositionService.getById(id);
            log.info("Cek isi employee-position Item : {}", d.toString(item));
            responseEntity = ResponseEntity.ok(item);
        }
        catch (Exception e)
        {
            log.error("GET employee-position", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-position/{id}", method = RequestMethod.PUT)
    public ResponseEntity putEmployeePosition(@PathVariable("id") Integer id, @RequestBody EmployeePositionCmd form, BindingResult result)
    {
        ResponseEntity responseEntity = null;
        log.info("*******Cek isi employee-position Item : {}", d.toString(form));
        try
        {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            employeePositionService.saveCmd(form);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT EMPLOYEE POSITION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-position/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployeePosition(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            employeePositionService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE EMPLOYEE POSITION ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-position/checkduplicate", method = RequestMethod.POST)
    public ResponseEntity checkDuplicate(@Valid @RequestBody EmployeePositionCmd employeePositionCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            log.info("*** PUT CMD : {}", d.toString(employeePositionCmd));
            Boolean pa  = employeePositionService.checkDuplicate(employeePositionCmd);
            responseEntity = ResponseEntity.ok(pa);
        } catch (Exception e) {
            log.error("CHECK DUPLICATE EMPLOYEE POSITION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
