package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.ProjectPaymentCmd;
import com.pusilkom.ess.dto.form.search.ProjectPaymentSearchForm;
import com.pusilkom.ess.dto.table.ProjectPaymentItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.service.ProjectPaymentService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class    ProjectPaymentController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    ProjectPaymentService projectPaymentService;

    @RequestMapping(value = "/projectPayment", method = RequestMethod.POST)
    public ResponseEntity postProject(@RequestBody ProjectPaymentCmd projectPaymentCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD INSERT PROJECT PAYMENT: {}", d.toString(projectPaymentCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            projectPaymentService.saveCmd(projectPaymentCmd);
            responseEntity = ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("POST PROJECT PAYMENT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectPayment/table", method = RequestMethod.GET)
    public ResponseEntity getTableProjectPayment(ProjectPaymentSearchForm searchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("Project Contract Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(searchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<ProjectPaymentItem> tablePagination = projectPaymentService.getTablePagination(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE PROJECT PAYMENT ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectPayment/{id}", method = RequestMethod.GET)
    public ResponseEntity getProjectPaymentById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            ProjectPaymentCmd projectPaymentCmd = projectPaymentService.getProjectPaymentById(id);
            log.info("Cek isi Item : {}", d.toString(projectPaymentCmd));
            responseEntity = ResponseEntity.ok(projectPaymentCmd);
        }
        catch (Exception e){
            log.error("GET PROJECT PAYMENT ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectPayment/getDetail/{id}", method = RequestMethod.GET)
    public ResponseEntity getDetailProjectPaymentById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            ProjectPaymentItem projectPaymentItem = projectPaymentService.getDetailProjectPayment(id);
            log.info("Cek isi Item : {}", d.toString(projectPaymentItem));
            responseEntity = ResponseEntity.ok(projectPaymentItem);
        }
        catch (Exception e){
            log.error("GET PROJECT PAYMENT ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectPayment/{id}", method = RequestMethod.PUT)
    public ResponseEntity putProjectPayment(@PathVariable("id") Integer id, @RequestBody ProjectPaymentCmd form, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD INSERT PROJECT PAYMENT: {}", d.toString(form));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            projectPaymentService.saveCmd(form);
            responseEntity = ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("PUT PROJECT PAYMENT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectPayment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectPayment(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            projectPaymentService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE PROJECT PAYMENT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
