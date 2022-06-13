package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeeBenefitCmd;
import com.pusilkom.ess.dto.form.search.EmployeeBenefitSearchForm;
import com.pusilkom.ess.dto.table.EmployeeBenefitItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeBenefit;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.service.EmployeeBenefitService;
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
public class EmployeeBenefitController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    EmployeeBenefitService employeeBenefitService;
    @Autowired
    MasterReferenceService masterReferenceService;

    @RequestMapping(value = "/employee-benefit/add", method = RequestMethod.POST)
    public ResponseEntity postEmployeeBenefit(@Valid @RequestBody EmployeeBenefitCmd emp, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** EMPLOYEE BENEFIT CMD {}", d.toString(emp));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            employeeBenefitService.saveEmployeeBenefitCmd(emp);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error("POST EMPLOYEE BENEFIT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-benefit/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid EmployeeBenefitSearchForm employeeBenefitSearchForm, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("employeeBenefitSearchForm : {}", d.toString(employeeBenefitSearchForm));
        log.info("Query : {}", d.toString(employeeBenefitSearchForm.getOrderQuery()));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeeBenefitItem> tablePagination = employeeBenefitService.getTablePagination(employeeBenefitSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE EMPLOYEE BENEFIT : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-benefit/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeBenefitById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        log.info(">>> CHECK BENEFIT ID : {}", d.toString(id));

        try {
            EmployeeBenefit benefit = employeeBenefitService.getEmployeeBenefitById(id);
            log.info(">>> FETCH EMPLOYEE BENEFIT : {}", d.toString(benefit));
            if (benefit == null) {
                return ResponseEntity.notFound().build();
            }
           responseEntity = ResponseEntity.ok(benefit);
           log.info(">>> CHECK RESP ENT EMPLOYEE BENEFIT : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("GET EMPLOYEE BENEFIT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/employee-benefit/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployeeFamily(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            employeeBenefitService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE BENEFIT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/benefit/all", method = RequestMethod.GET)
    public ResponseEntity getAllMasterReferenceBenefit() {
        ResponseEntity responseEntity = null;
        try
        {
            List<MasterReference> benefitList = masterReferenceService.getMasterReferenceByReferenceType("Benefit");
            log.info("Check all fetched benefit : {}", d.toString(benefitList));
            responseEntity = ResponseEntity.ok(benefitList);
        }
        catch (Exception e)
        {
            log.error("GET ALL BENEFIT ERR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee-benefit/checkduplicate", method = RequestMethod.POST)
    public ResponseEntity checkDuplicateEmployeeBenefit(@Valid @RequestBody EmployeeBenefitCmd employeeBenefitCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            log.info("*** PUT CMD : {}", d.toString(employeeBenefitCmd));
            Boolean pa  = employeeBenefitService.checkDuplicate(employeeBenefitCmd);
            responseEntity = ResponseEntity.ok(pa);
        } catch (Exception e) {
            log.error("CHECK DUPLICATE EMPLOYEE BENEFIT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}

