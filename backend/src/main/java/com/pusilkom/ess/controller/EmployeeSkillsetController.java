package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeeSkillsetCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSkillsetSearchForm;
import com.pusilkom.ess.dto.table.EmployeeSkillsetItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeSkillset;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.service.EmployeeSkillsetService;
import com.pusilkom.ess.service.MasterReferenceService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeSkillsetController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    EmployeeSkillsetService employeeSkillsetService;
    @Autowired
    MasterReferenceService masterReferenceService;

    @RequestMapping(value = "/employee-skillset/add", method = RequestMethod.POST)
    public ResponseEntity postEmployeeSkillset(@Valid @RequestBody EmployeeSkillsetCmd emp, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** EMPLOYEE SKILLSET CMD {}", d.toString(emp));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            employeeSkillsetService.saveCmd(emp);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error("POST EMPLOYEE SKILLSET ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-skillset/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid EmployeeSkillsetSearchForm employeeSkillsetSearchForm, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("employeeSkillsetSearchForm : {}", d.toString(employeeSkillsetSearchForm));
        log.info("Query : {}", d.toString(employeeSkillsetSearchForm.getOrderQuery()));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeeSkillsetItem> tablePagination = employeeSkillsetService.getTablePagination(employeeSkillsetSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE EMPLOYEE SKILLSET : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-skillset/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeSkillsetById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        log.info(">>> CHECK SKILLSET ID : {}", d.toString(id));

        try {
            EmployeeSkillset emp = employeeSkillsetService.getById(id);
            log.info(">>> FETCH EMPLOYEE SKILLSET : {}", d.toString(emp));
            if (emp == null) {
                return ResponseEntity.notFound().build();
            }
           responseEntity = ResponseEntity.ok(emp);
           log.info(">>> CHECK RESP ENT EMPLOYEE SKILLSET : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("GET EMPLOYEE SKILLSET ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/employee-skillset/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            employeeSkillsetService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE SKILLSET ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/skillset/all", method = RequestMethod.GET)
    public ResponseEntity getAllMasterReferenceSkillset() {
        ResponseEntity responseEntity = null;
        try
        {
            List<MasterReference> benefitList = masterReferenceService.getMasterReferenceByReferenceType("Skillset");
            log.info("Check all fetched skillset : {}", d.toString(benefitList));
            responseEntity = ResponseEntity.ok(benefitList);
        }
        catch (Exception e)
        {
            log.error("GET ALL SKILLSET ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-skillset/checkduplicate", method = RequestMethod.POST)
    public ResponseEntity checkDuplicate(@Valid @RequestBody EmployeeSkillsetCmd employeeSkillsetCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            log.info("*** PUT CMD : {}", d.toString(employeeSkillsetCmd));
            Boolean pa  = employeeSkillsetService.checkDuplicate(employeeSkillsetCmd);
            responseEntity = ResponseEntity.ok(pa);
        } catch (Exception e) {
            log.error("CHECK DUPLICATE EMPLOYEE SKILLSET", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}

