package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.UserRoleCmd;
import com.pusilkom.ess.dto.form.search.UserWebHasRoleSearchForm;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.table.UserWebHasRoleItem;
import com.pusilkom.ess.service.UserHasRoleService;
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
 * Created by taufan on 02/12/19.
 */

@RestController
public class UserWebHasRoleController extends BaseRestController{
    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    UserHasRoleService userHasRoleService;

    @RequestMapping(value = "/user-role/add", method = RequestMethod.POST)
    public ResponseEntity postUserRole(@RequestBody UserRoleCmd[] userRoleCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("*** SEBELUM POST CMD : {}", d.toString(userRoleCmd.length));
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            userHasRoleService.save(userRoleCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/user-role/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid UserWebHasRoleSearchForm levelSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("userRole SearchForm : {}", d.toString(levelSearchForm));
        log.info("Query : {}", d.toString(levelSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(levelSearchForm));
            TablePagination<UserWebHasRoleItem> tablePagination = userHasRoleService.getTablePagination(levelSearchForm);
            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE USER WEB HAS ROLE : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/user-role/update/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserRoleById(@PathVariable Long id) {
        ResponseEntity responseEntity = null;

        try {
            List<UserWebHasRoleItem> listItem = userHasRoleService.getListUserWebHasRoleByIdUserweb(id);
            log.info(">>> get user role : {}", d.toString(listItem));
            if (listItem.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(listItem);
            log.info(">>> get user role by id : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("get user role", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/user-role/{id}", method = RequestMethod.PUT)
    public ResponseEntity putUserRole(@PathVariable Long id, @RequestBody UserRoleCmd[] userRoleCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(userRoleCmd.length));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            userHasRoleService.delete(id);
            userHasRoleService.save(userRoleCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT USER WEB HAS MENU", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/user-role/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserRole(@PathVariable Long id) {
        ResponseEntity responseEntity = null;
        try {
            userHasRoleService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE USER WEB HAS ROLE : ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

}
