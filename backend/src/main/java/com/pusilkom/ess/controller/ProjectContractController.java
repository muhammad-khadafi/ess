package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.ProjectContractCmd;
import com.pusilkom.ess.dto.form.search.ProjectContractSearchForm;
import com.pusilkom.ess.dto.table.ProjectContractItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.ProjectContract;
import com.pusilkom.ess.service.ProjectContractService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by taufan on 8/11/17.
 */

@RestController
public class ProjectContractController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    ProjectContractService projectContractService;

    @RequestMapping(value = "/projectContract", method = RequestMethod.POST)
    public ResponseEntity postProject(@RequestBody ProjectContractCmd projectContractCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD INSERT PROJECT CONTRACT: {}", d.toString(projectContractCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            projectContractService.saveCmd(projectContractCmd);
            responseEntity = ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("POST PROJECT CONTRACT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectContract/table", method = RequestMethod.GET)
    public ResponseEntity postProject(ProjectContractSearchForm projectContractSearchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("Project Contract Search Form : {}", d.toString(projectContractSearchForm));
        log.info("Query : {}", d.toString(projectContractSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(projectContractSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<ProjectContractItem> tablePagination = projectContractService.getTablePagination(projectContractSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE PROJECT CONTRACT ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectContract/{id}", method = RequestMethod.GET)
    public ResponseEntity getProjectContractById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            ProjectContractItem projectContractItem = projectContractService.getById(id);
            log.info("Cek isi Item : {}", d.toString(projectContractItem));
            responseEntity = ResponseEntity.ok(projectContractItem);
        }
        catch (Exception e){
            log.error("GET PROJECT CONTRACT ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectContract/getAll", method = RequestMethod.GET)
    public ResponseEntity getAllProjectContract() {
        ResponseEntity responseEntity = null;
        try
        {
            List<ProjectContract> listProjectContract = projectContractService.getAllProjectContract();
            log.info("Cek isi Item : {}", d.toString(listProjectContract));
            responseEntity = ResponseEntity.ok(listProjectContract);
        }
        catch (Exception e){
            log.error("GET PROJECT CONTRACT ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectContract/{id}", method = RequestMethod.PUT)
    public ResponseEntity putProject(@PathVariable("id") Integer id, @RequestBody ProjectContractCmd form, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD INSERT PROJECT CONTRACT: {}", d.toString(form));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            projectContractService.saveCmd(form);
            responseEntity = ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("PUT PROJECT CONTRACT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/projectContract/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectContract(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            projectContractService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE PROJECT CONTRACT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
