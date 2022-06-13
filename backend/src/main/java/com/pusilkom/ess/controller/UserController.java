package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.UserChangePasswordCmd;
import com.pusilkom.ess.dto.form.cmd.UserCmd;
import com.pusilkom.ess.dto.form.search.UserSearchForm;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.table.UserItem;
import com.pusilkom.ess.model.Userweb;
import com.pusilkom.ess.service.UserwebHasRoleService;
import com.pusilkom.ess.service.UserwebService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import com.pusilkom.ess.validator.form.cmd.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import javax.validation.Valid;

/**
 * Created by fahri on 1/7/17.
 */
@RestController
public class UserController extends BaseRestController {

    final static String ERROR_MSG = "Failed to authenticate";

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    UserwebService userwebService;
    @Autowired
    UserwebHasRoleService userwebHasRoleService;

    @Autowired
    UserValidator userValidator;

    @InitBinder("userChangePasswordCmd")
    protected void registerMenuValidator(WebDataBinder binder){
        binder.setValidator(userValidator);
    }

    @RequestMapping(value="/user/{id}", method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable Long id) {


        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "/user/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(UserSearchForm searchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("User Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(searchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<UserItem> tablePagination = userwebService.getTablePaginationUser(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE SUB PROJECT ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/userWeb/getAll", method = RequestMethod.GET)
    public ResponseEntity getAllUserWeb() {
        ResponseEntity responseEntity = null;
        try
        {
            List<Userweb> userwebList = userwebService.getListAllUserWeb();
            log.info("Cek isi User Web List : {}", d.toString(userwebList));
            responseEntity = ResponseEntity.ok(userwebList);
        }
        catch (Exception e)
        {
            log.error("GET ALL USER WEB LIST", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/user-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            UserItem userItem = userwebService.getUserItemById(id);
            log.info("Cek isi User Item : {}", d.toString(userItem));
            responseEntity = ResponseEntity.ok(userItem);
        }
        catch (Exception e)
        {
            log.error("GET USER ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/user/checkUserwebHasRole", method = RequestMethod.POST)
    public ResponseEntity checkUserwebHasRole(@RequestBody UserCmd userCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(userCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Integer totalRole = userwebHasRoleService.countUserwebHasRoleByUserWebId(userCmd);
            responseEntity = ResponseEntity.ok(totalRole);
        } catch (Exception e) {
            log.error("POST USERT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserweb(@PathVariable Long id) {
        ResponseEntity responseEntity = null;

        try {
            userwebService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE USER", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/user/checkduplicate", method = RequestMethod.POST)
    public ResponseEntity checkDuplicate(@Valid @RequestBody UserCmd userCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(userCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Boolean check = userwebService.checkDuplicate(userCmd);
            responseEntity = ResponseEntity.ok(check);
        } catch (Exception e) {
            log.error("POST USER", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity postUser(@Valid @RequestBody UserCmd userCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(userCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            userwebService.saveCmd(userCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST USER", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity putUser(@PathVariable Integer id, @Valid @RequestBody UserCmd userCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(userCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            userwebService.saveCmd(userCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT USER", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/user/change-password", method = RequestMethod.PUT)
    public ResponseEntity changePassword(@Valid @RequestBody UserChangePasswordCmd userChangePasswordCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(userChangePasswordCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            userwebService.saveChangePassword(userChangePasswordCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT USER", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @CrossOrigin
    @RequestMapping(value = "/userGenerator/addUser/{employeeId}/{roleId}/{username}/{password}", method = RequestMethod.POST)
    public ResponseEntity postUserFromOtherResource(@PathVariable Integer employeeId, @PathVariable Long roleId,@PathVariable String username,
                                                    @PathVariable String password) {
        ResponseEntity responseEntity = null;
        try {
            userwebService.addUserAndUserHasRole(employeeId, roleId, username, password);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT USER", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;

    }

    @CrossOrigin
    @RequestMapping(value = "/userGenerator/user/checkduplicate/{userName}", method = RequestMethod.POST)
    public ResponseEntity checkDuplicate(@PathVariable String userName) {
        ResponseEntity responseEntity = null;

        try {
            UserCmd userCmd = new UserCmd();
            userCmd.setUsername(userName);

            Boolean check = userwebService.checkDuplicate(userCmd);
            responseEntity = ResponseEntity.ok(check);
        } catch (Exception e) {
            log.error("POST USER", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
