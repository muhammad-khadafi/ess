package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.diagram.OrganizationStructureDiagramTree;
import com.pusilkom.ess.dto.form.cmd.OrganizationStructureCmd;
import com.pusilkom.ess.dto.form.search.OrganizationStructureSearchForm;
import com.pusilkom.ess.dto.table.OrganizationStructureItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.OrganizationStructureDetail;
import com.pusilkom.ess.service.JobPositionService;
import com.pusilkom.ess.service.OrganizationService;
import com.pusilkom.ess.service.OrganizationStructureService;
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
 * Created by ITF on 11/8/2018.
 */
@RestController
public class OrganizationStructureController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    JobPositionService jobPositionService;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    OrganizationStructureService organizationStructureService;

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;

    @RequestMapping(value = "/organization-structure", method = RequestMethod.POST)
    public ResponseEntity postProject(@RequestBody OrganizationStructureCmd organizationStructureCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD INSERT Organization Structure: {}", d.toString(organizationStructureCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            organizationStructureService.saveCmd(organizationStructureCmd);
            responseEntity = ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("POST Organization Structure ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/organization-structure/structurelist", method = RequestMethod.GET)
    public ResponseEntity getAllOrganizationStructure() {
        ResponseEntity responseEntity = null;

        try {
            List<OrganizationStructureDetail> organizationStructure = organizationStructureService.getAllOrganizationStructure();
            responseEntity = ResponseEntity.ok(organizationStructure);
        } catch (Exception e) {
            log.error("GET JOB POSITION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/organization-structure/table", method = RequestMethod.GET)
    public ResponseEntity postProject(OrganizationStructureSearchForm searchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("Project Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(searchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<OrganizationStructureItem> tablePagination = organizationStructureService.getTablePagination(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE PROJECT ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/organization-structure/{id}", method = RequestMethod.GET)
    public ResponseEntity getOrganizationStructureById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            OrganizationStructureItem item = organizationStructureService.getById(id);
            log.info("Cek isi organization-structure Item : {}", d.toString(item));
            responseEntity = ResponseEntity.ok(item);
        }
        catch (Exception e)
        {
            log.error("GET organization-structure", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }



    @RequestMapping(value = "/organization-structure/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            organizationStructureService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE ORGANIZATION STRUCTURE ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/organization-structure/diagram", method = RequestMethod.GET)
    public ResponseEntity getOrganizationStructureDiagram() {
        ResponseEntity responseEntity = null;

        try {
            List<OrganizationStructureDiagramTree> organizationStructure = organizationStructureService.getOrganizationStructureDiagram();
            responseEntity = ResponseEntity.ok(organizationStructure);
        } catch (Exception e) {
            log.error("GET ORGANIZATION STRUCTURE DIAGRAM", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
}
