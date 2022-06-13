package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.RoleCmd;
import com.pusilkom.ess.dto.form.search.RoleSearchForm;
import com.pusilkom.ess.dto.table.RoleItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.Role;
import com.pusilkom.ess.service.RoleService;
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
 * Created by taufan on 8/11/17.
 */

@RestController
public class RoleController extends  BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    RoleService roleService;

    @CrossOrigin
    @RequestMapping(value = {"/role/getAll", "/userGenerator/role/getAll"}, method = RequestMethod.GET)
    public ResponseEntity getAllRole() {
        ResponseEntity responseEntity = null;
        try
        {
            List<Role> roleList = roleService.getListAllRole();
            log.info("Cek isi role item : {}", d.toString(roleList));
            responseEntity = ResponseEntity.ok(roleList);
        }
        catch (Exception e)
        {
            log.error("GET ALL ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/role/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(RoleSearchForm searchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("Role Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(searchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<RoleItem> tablePagination = roleService.getTablePaginationRole(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE ROLE ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role/checkduplicatekode", method = RequestMethod.POST)
    public ResponseEntity checkDuplicateCode(@Valid @RequestBody RoleCmd roleCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(roleCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Boolean check = roleService.checkDuplicateKode(roleCmd);
            responseEntity = ResponseEntity.ok(check);
        } catch (Exception e) {
            log.error("POST ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role/checkduplicatelabel", method = RequestMethod.POST)
    public ResponseEntity checkDuplicateLabel(@Valid @RequestBody RoleCmd roleCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(roleCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Boolean check = roleService.checkDuplicateLabel(roleCmd);
            responseEntity = ResponseEntity.ok(check);
        } catch (Exception e) {
            log.error("POST ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public ResponseEntity postUser(@Valid @RequestBody RoleCmd roleCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(roleCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            roleService.saveCmd(roleCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity getRoleById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            RoleItem roleItem = roleService.getRoleItemById(id);
            log.info("Cek isi Role Item : {}", d.toString(roleItem));
            responseEntity = ResponseEntity.ok(roleItem);
        }
        catch (Exception e)
        {
            log.error("GET ROLE ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/role/checkUserwebHasRole", method = RequestMethod.POST)
    public ResponseEntity checkRefUserwebHasRole(@RequestBody RoleCmd roleCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(roleCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Integer totalUserWebHasRole = roleService.countUserwebHasRoleByRoleId(roleCmd);
            responseEntity = ResponseEntity.ok(totalUserWebHasRole);
        } catch (Exception e) {
            log.error("POST ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role/checkRoleHasMenu", method = RequestMethod.POST)
    public ResponseEntity checkRefRoleHasMenu(@RequestBody RoleCmd roleCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(roleCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Integer totalRoleHasMenu = roleService.countRoleHasMenuByRoleId(roleCmd);
            responseEntity = ResponseEntity.ok(totalRoleHasMenu);
        } catch (Exception e) {
            log.error("POST ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRole(@PathVariable Long id) {
        ResponseEntity responseEntity = null;

        try {
            roleService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/role/getUserHasRole/{idUserwebHasRole}", method = RequestMethod.GET)
    public ResponseEntity getUserHasRole(@PathVariable Long idUserwebHasRole) {
        ResponseEntity responseEntity = null;
        try
        {
            List<RoleItem> roleList = roleService.getListRoleNotInUserwebHasRoleByIdUserweb(idUserwebHasRole);
            log.info("Cek isi role item : {}", d.toString(roleList));
            responseEntity = ResponseEntity.ok(roleList);
        }
        catch (Exception e)
        {
            log.error("GET ALL ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
}
