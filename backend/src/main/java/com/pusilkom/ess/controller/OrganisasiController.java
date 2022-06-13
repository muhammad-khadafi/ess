package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.OrganizationCmd;
import com.pusilkom.ess.dto.form.search.OrganizationSearchForm;
import com.pusilkom.ess.dto.table.OrganizationItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.OrganizationDetail;
import com.pusilkom.ess.model.Organization;
import com.pusilkom.ess.service.OrganizationService;
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
 * Created by fahri on 1/7/17.
 */
@RestController
public class OrganisasiController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    OrganizationService organizationService;

    @Autowired RestValidatorUtil rv;
    @Autowired DebugUtil d;


    @RequestMapping(value = "/organisasi", method = RequestMethod.POST)
    public ResponseEntity postInstitusi(@Valid @RequestBody OrganizationCmd organizationCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(organizationCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            organizationService.saveCmd(organizationCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/organisasi", method = RequestMethod.GET)
    public ResponseEntity getOrganisasi() {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = ResponseEntity.ok(organizationService.getAll());
        } catch (Exception e) {
            log.error("POST ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/organization-parent/{id}", method = RequestMethod.GET)
    public ResponseEntity getOrganisasi(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = ResponseEntity.ok(organizationService.getAllPossibleParents(id.longValue()));
        } catch (Exception e) {
            log.error("POST ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/organisasi/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid OrganizationSearchForm organizationSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("OrganizationSearchForm : {}", d.toString(organizationSearchForm));
        log.info("Query : {}", d.toString(organizationSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(organizationSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<OrganizationItem> tablePagination = organizationService.getTablePagination(organizationSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE ORGANIZATION : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/organization/organizationlist", method = RequestMethod.GET)
    public ResponseEntity getAllOrganization() {
        ResponseEntity responseEntity = null;

        try {
            List<Organization> organization = organizationService.getAllOrganization();
            responseEntity = ResponseEntity.ok(organization);
        } catch (Exception e) {
            log.error("GET ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/organization/{id}", method = RequestMethod.PUT)
    public ResponseEntity putOrganization(@PathVariable Integer id, @Valid @RequestBody OrganizationCmd organizationCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(organizationCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            organizationService.saveCmd(organizationCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/organization/{id}", method = RequestMethod.GET)
    public ResponseEntity getOrganization(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            Organization organization = organizationService.getById(id);
            if (organization == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(new OrganizationDetail(organization));
        } catch (Exception e) {
            log.error("GET ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/organization/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteJobPosition(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            organizationService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


}
