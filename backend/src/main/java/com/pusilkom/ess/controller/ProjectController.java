package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.ProjectCmd;
import com.pusilkom.ess.dto.form.search.ProjectSearchForm;
import com.pusilkom.ess.dto.table.ProjectItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.Project;
import com.pusilkom.ess.service.ProjectService;
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
public class ProjectController extends  BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity postProject(@RequestBody ProjectCmd projectCmd, BindingResult result){
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(projectCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            projectService.saveCmd(projectCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST PROJECT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project/table", method = RequestMethod.GET)
    public ResponseEntity postProject(ProjectSearchForm projectSearchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("Project Search Form : {}", d.toString(projectSearchForm));
        log.info("Query : {}", d.toString(projectSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(projectSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<ProjectItem> tablePagination = projectService.getTablePagination(projectSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE PROJECT ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public ResponseEntity getProjectById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            ProjectItem projectItem = projectService.getById(id);
            log.info("Cek isi project Item : {}", d.toString(projectItem));
            responseEntity = ResponseEntity.ok(projectItem);
        }
        catch (Exception e)
        {
            log.error("GET PROJECT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/project/getAllProject", method = RequestMethod.GET)
    public ResponseEntity getAllProject() {
        ResponseEntity responseEntity = null;
        try
        {
            List<Project> projectList = projectService.getAllProject();
            log.info("Cek isi project Item : {}", d.toString(projectList));
            responseEntity = ResponseEntity.ok(projectList);
        }
        catch (Exception e)
        {
            log.error("GET ALL PROJECT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.PUT)
    public ResponseEntity putProject(@PathVariable("id") Integer id, @RequestBody ProjectCmd form, BindingResult result)
    {
        ResponseEntity responseEntity = null;
        log.info("*** PUT CMD : {}", d.toString(form));
        try
        {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            projectService.saveCmd(form);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT PROJECT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            projectService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE MASTER REFERENCE ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project/getAllProjectNonAj", method = RequestMethod.GET)
    public ResponseEntity getAllProjectNonAj() {
        ResponseEntity responseEntity = null;
        try
        {
            List<Project> projectList = projectService.getAllProjectNonAj();
            log.info("Cek isi project Item : {}", d.toString(projectList));
            responseEntity = ResponseEntity.ok(projectList);
        }
        catch (Exception e)
        {
            log.error("GET ALL PROJECT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
}
