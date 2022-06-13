package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeeCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSearchForm;
import com.pusilkom.ess.dto.table.EmployeeItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.EmployeeDetail;
import com.pusilkom.ess.model.Employee;
import com.pusilkom.ess.service.EmployeeService;
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

/**
 * Created by arief on 10/31/18.
 */
@RestController
public class EmployeeController extends BaseRestController {

    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    EmployeeService employeeService;

//    @RequestMapping(value = "/employee/add", method = RequestMethod.POST)
//    public ResponseEntity postPegawai(@Valid @RequestBody EmployeeCmd employeeCmd, BindingResult result) {
//        ResponseEntity responseEntity = null;
//        try {
//            log.info("*** POST CMD : {}", d.toString(employeeCmd));
//            if (result.hasErrors()) {
//                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
//            }
//
//            employeeService.saveCmd(employeeCmd);
//            responseEntity = ResponseEntity.ok().build();
//        } catch (Exception e) {
//            log.error("POST EMPLOYEE", e);
//            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
//        }
//
//        return responseEntity;
//    }

    @RequestMapping(value = "/employee/add", method = RequestMethod.POST)
    public ResponseEntity postPegawai(@Valid @RequestBody EmployeeCmd employeeCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("*** SEBELUM POST CMD : {}", d.toString(employeeCmd));
        try {
            log.info("*** POST CMD : {}", d.toString(employeeCmd));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Integer idEmployee = employeeService.saveCmd(employeeCmd);
            responseEntity = ResponseEntity.ok(idEmployee);
        } catch (Exception e) {
            log.error("POST EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid EmployeeSearchForm employeeSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("EmployeeSearchForm : {}", d.toString(employeeSearchForm));
        log.info("Query : {}", d.toString(employeeSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(employeeSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeeItem> tablePagination = employeeService.getTablePagination(employeeSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE EMPLOYEE : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            Employee employee = employeeService.getEmployeeById(id);
            log.info(">>> get employee by coba : {}", d.toString(employee));
            if (employee == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(new EmployeeDetail(employee));
            log.info(">>> get employee by id : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("GET EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity putJobPosition(@PathVariable Integer id, @Valid @RequestBody EmployeeCmd employeeCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(employeeCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            employeeService.saveCmd(employeeCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEmployee(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            employeeService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee/all", method = RequestMethod.GET)
    public ResponseEntity getAllEmployee() {
        ResponseEntity responseEntity = null;
        try {
            List<EmployeeItem> listAll = employeeService.getAllEmployeeForDropDown();
            responseEntity = ResponseEntity.ok(listAll);
        } catch (Exception e) {
            log.error("LIST ALL EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee/nameAndNik/{id}", method = RequestMethod.GET)
    public ResponseEntity getNameAndNikEmployee(@PathVariable("id") Integer id) {
        ResponseEntity responseEntity = null;
        try {
            Employee employee = employeeService.getEmployeeById(id);
            responseEntity = ResponseEntity.ok(employee.getName() + " - " + employee.getNik());
        } catch (Exception e) {
            log.error("GET NAME AND NIK EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @CrossOrigin
    @RequestMapping(value = "/userGenerator/employee/notAssign", method = RequestMethod.GET)
    public ResponseEntity getListEmployeeNotAssign() {
        ResponseEntity responseEntity = null;
        try {
            List<EmployeeItem> listAll = employeeService.getListEmployeeNotAssign();
            responseEntity = ResponseEntity.ok(listAll);
        } catch (Exception e) {
            log.error("LIST ALL EMPLOYEE NOT ASSIGN", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee/get/projectId/{employeeId}/{months}/{years}", method = RequestMethod.GET)
    public ResponseEntity getListEmployeeByProjectId(@PathVariable Integer employeeId, @PathVariable Integer months, @PathVariable Integer years) {
        ResponseEntity responseEntity = null;
        try {
            List<EmployeeItem> listAll = employeeService.getListEmployeeByProjectId(employeeId, months, years);
            responseEntity = ResponseEntity.ok(listAll);
        } catch (Exception e) {
            log.error("LIST ALL EMPLOYEE NOT ASSIGN", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee/get/roleManager", method = RequestMethod.GET)
    public ResponseEntity getListEmployeeByRoleManager() {
        ResponseEntity responseEntity = null;
        try {
            List<EmployeeItem> listAll = employeeService.getListEmployeeByRoleManager();
            log.info("Cek isi manager item : {}", d.toString(listAll));
            responseEntity = ResponseEntity.ok(listAll);
        } catch (Exception e) {
            log.error("LIST ALL EMPLOYEE NOT ASSIGN", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee/aj", method = RequestMethod.GET)
    public ResponseEntity getListEmployeeAJ() {
        ResponseEntity responseEntity = null;
        try {
            List<EmployeeItem> listAll = employeeService.getListEmployeeAJ();
            responseEntity = ResponseEntity.ok(listAll);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/employee/non-aj", method = RequestMethod.GET)
    public ResponseEntity getListEmployeeNonAJ() {
        ResponseEntity responseEntity = null;
        try {
            List<EmployeeItem> listAll = employeeService.getListEmployeeNonAJ();
            responseEntity = ResponseEntity.ok(listAll);
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
}
