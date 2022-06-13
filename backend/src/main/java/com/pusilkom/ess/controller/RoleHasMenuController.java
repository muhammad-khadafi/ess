package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.RoleHasMenuCmd;
import com.pusilkom.ess.dto.form.search.RoleHasMenuSearchForm;
import com.pusilkom.ess.dto.table.RoleHasMenuItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.service.RoleHasMenuService;
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
public class RoleHasMenuController extends BaseRestController{
    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    RoleHasMenuService roleHasMenuService;

    @RequestMapping(value = "/role-menu/add", method = RequestMethod.POST)
    public ResponseEntity postRoleMenu(@RequestBody RoleHasMenuCmd[] roleHasMenuCmds, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("*** SEBELUM POST CMD : {}", d.toString(roleHasMenuCmds.length));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            roleHasMenuService.save(roleHasMenuCmds);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST ROLE MENU", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role-menu/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid RoleHasMenuSearchForm roleMenuSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("userRole SearchForm : {}", d.toString(roleMenuSearchForm));
        log.info("Query : {}", d.toString(roleMenuSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(roleMenuSearchForm));
            TablePagination<RoleHasMenuItem> tablePagination = roleHasMenuService.getTablePagination(roleMenuSearchForm);
            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE ROLE HAS MENU : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role-menu/update/{id}", method = RequestMethod.GET)
    public ResponseEntity getRoleHasMenuById(@PathVariable Long id) {
        ResponseEntity responseEntity = null;

        try {
            List<RoleHasMenuItem> listItem = roleHasMenuService.getListRoleHasMenuByIdRoleUpdate(id);
            log.info(">>> get Role Has Menu : {}", d.toString(listItem));
            if (listItem.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(listItem);
            log.info(">>> get Role Has Menu by id : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("get Role Has Menu", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role-menu/{id}", method = RequestMethod.PUT)
    public ResponseEntity putUserRole(@PathVariable Long id, @RequestBody RoleHasMenuCmd[] roleHasMenuCmds, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD UPDATE : {}", d.toString(roleHasMenuCmds.length));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            roleHasMenuService.delete(id);
            roleHasMenuService.save(roleHasMenuCmds);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT ROLE HAS MENU", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role-menu/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRoleMenu(@PathVariable Long id) {
        ResponseEntity responseEntity = null;
        try {
            roleHasMenuService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE ROLE HAS MENU : ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
