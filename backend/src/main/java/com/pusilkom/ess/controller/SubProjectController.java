package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.SubProjectCmd;
import com.pusilkom.ess.dto.form.search.SubProjectSearchForm;
import com.pusilkom.ess.dto.table.SubProjectItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.service.SubProjectService;
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

@RestController
public class SubProjectController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    SubProjectService subProjectService;

    @RequestMapping(value = "/sub-project/{id}", method = RequestMethod.GET)
    public ResponseEntity getSubProjectByIdEmployee(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            List<SubProjectItem> listSubPro = subProjectService.getSubProjectByIdEmployee(id);
            log.info("Cek isi Sub Project Item : {}", d.toString(listSubPro));
            responseEntity = ResponseEntity.ok(listSubPro);
        }
        catch (Exception e)
        {
            log.error("GET SUB PROJECT ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/sub-project-by-project/{id}", method = RequestMethod.GET)
    public ResponseEntity getSubProjectByIdProject(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            List<SubProjectItem> listSubPro = subProjectService.getListSubProjectItemByIdProject(id);
            log.info("Cek isi Sub Project Item : {}", d.toString(listSubPro));
            responseEntity = ResponseEntity.ok(listSubPro);
        }
        catch (Exception e)
        {
            log.error("GET SUB PROJECT ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/sub-project-non-aj", method = RequestMethod.GET)
    public ResponseEntity getSubProjectNonAj(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            List<SubProjectItem> listSubPro = subProjectService.getListSubProjectNonAj();
            log.info("Cek isi Sub Project Item : {}", d.toString(listSubPro));
            responseEntity = ResponseEntity.ok(listSubPro);
        }
        catch (Exception e)
        {
            log.error("GET SUB PROJECT ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/sub-project/table", method = RequestMethod.GET)
    public ResponseEntity postProject(SubProjectSearchForm searchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("Project Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(searchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<SubProjectItem> tablePagination = subProjectService.getTablePagination(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE SUB PROJECT ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/sub-project", method = RequestMethod.POST)
    public ResponseEntity postSubProject(@Valid @RequestBody SubProjectCmd subProjectCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(subProjectCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            subProjectService.saveCmd(subProjectCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST SUB PROJECT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/sub-project-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity getSubProjectById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            SubProjectItem subProjectItem = subProjectService.getSubProjectItemById(id);
            log.info("Cek isi Sub Project Item : {}", d.toString(subProjectItem));
            responseEntity = ResponseEntity.ok(subProjectItem);
        }
        catch (Exception e)
        {
            log.error("GET SUB PROJECT ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/sub-project-by-id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteSubProject(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            subProjectService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE SUB PROJECT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/sub-project/{id}", method = RequestMethod.PUT)
    public ResponseEntity putSubProject(@PathVariable Integer id, @Valid @RequestBody SubProjectCmd subProjectCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(subProjectCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            subProjectService.saveCmd(subProjectCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT SUBPROJECT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/sub-project/checkduplicate", method = RequestMethod.POST)
    public ResponseEntity checkDuplicate(@Valid @RequestBody SubProjectCmd subProjectCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(subProjectCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Boolean check = subProjectService.checkDuplicate(subProjectCmd);
            responseEntity = ResponseEntity.ok(check);
        } catch (Exception e) {
            log.error("POST SUB PROJECT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
