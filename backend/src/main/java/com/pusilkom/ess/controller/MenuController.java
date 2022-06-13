package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.MenuCmd;
import com.pusilkom.ess.dto.form.search.MenuSearchForm;
import com.pusilkom.ess.dto.menu.MenuItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.Menu;
import com.pusilkom.ess.service.MenuService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import com.pusilkom.ess.validator.form.cmd.MenuValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by taufan on 02/12/19.
 */

@RestController
public class MenuController extends BaseRestController{
    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";
    final static String DELETE_MSG = "Tidak bisa delete";


    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    MenuService menuService;
    @Autowired
    MenuValidator menuValidator;

    @InitBinder("menuCmd")
    protected void registerMenuValidator(WebDataBinder binder){
        binder.setValidator(menuValidator);
    }

    @RequestMapping(value = "/menu/getAll", method = RequestMethod.GET)
    public ResponseEntity getAllMenu() {
        ResponseEntity responseEntity = null;
        try
        {
            List<Menu> menuList = menuService.getAllMenu();
            log.info("Cek isi Menu item : {}", d.toString(menuList));
            responseEntity = ResponseEntity.ok(menuList);
        }
        catch (Exception e)
        {
            log.error("GET ALL MENU", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/menu/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid MenuSearchForm organizationSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("OrganizationSearchForm : {}", d.toString(organizationSearchForm));
        log.info("Query : {}", d.toString(organizationSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(organizationSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<MenuItem> tablePagination = menuService.getTablePagination(organizationSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE ORGANIZATION : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public ResponseEntity postInstitusi(@Valid @RequestBody MenuCmd menuCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(menuCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            menuService.saveMenu(menuCmd);
            responseEntity = ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("POST MENU", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/menu-parent/{id}", method = RequestMethod.GET)
    public ResponseEntity getMenuParent(@PathVariable Long id) {
        ResponseEntity responseEntity = null;
        try {
            responseEntity = ResponseEntity.ok(menuService.getAllPossibleParents(id.longValue()));
        } catch (Exception e) {
            log.error("GET MENU", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/menu/{id}", method = RequestMethod.PUT)
    public ResponseEntity putOrganization(@PathVariable Integer id, @Valid @RequestBody MenuCmd menuCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(menuCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            menuService.saveMenu(menuCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
    public ResponseEntity getOrganization(@PathVariable Long id) {
        ResponseEntity responseEntity = null;

        try {
            Menu menu = menuService.getById(id);
            if (menu == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(menu);
        } catch (Exception e) {
            log.error("GET ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/menu/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMenu(@PathVariable Long id) {
        ResponseEntity responseEntity = null;

        try {
            if(menuService.canBeDeleted(id)){
                menuService.delete(id);
                responseEntity = ResponseEntity.ok().build();
            } else {
                Exception e = new Exception(DELETE_MSG);
                responseEntity = ResponseEntity.unprocessableEntity().body(rv.buildError(e, DELETE_MSG));
            }
        } catch (Exception e) {
            log.error("DELETE ORGANIZATION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));

        }

        return responseEntity;
    }

    @RequestMapping(value = "/menu/getMenuRoleHasMenu/{idRoleHasMenu}", method = RequestMethod.GET)
    public ResponseEntity getMenuRoleHasMenu(@PathVariable Long idRoleHasMenu) {
        ResponseEntity responseEntity = null;
        try
        {
            List<MenuItem> menuList = menuService.getMenuNotInIdMenuRoleHasMenu(idRoleHasMenu);
            log.info("Cek isi Menu item : {}", d.toString(menuList));
            responseEntity = ResponseEntity.ok(menuList);
        }
        catch (Exception e)
        {
            log.error("GET ALL MENU USE IN ROLEH HAS MENU UPDATE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

}
