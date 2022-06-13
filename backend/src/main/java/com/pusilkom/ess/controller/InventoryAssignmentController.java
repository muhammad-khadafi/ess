package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.InventoryAssignmentCmd;
import com.pusilkom.ess.dto.form.search.InventoryAssignmentSearchForm;
import com.pusilkom.ess.dto.table.InventoryAssignmentItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.service.InventoryAssignmentService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import com.pusilkom.ess.validator.form.cmd.InventoryAssignmentCmdValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class InventoryAssignmentController extends BaseRestController{
    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    InventoryAssignmentCmdValidator inventoryAssignmentCmdValidator;
    @Autowired
    InventoryAssignmentService inventoryAssignmentService;

    @InitBinder("form")
    protected void registerInventoryAssignmentCmdValidator(WebDataBinder binder) {
        binder.setValidator(inventoryAssignmentCmdValidator);
    }

    @RequestMapping(value="/inventory-assignment", method = RequestMethod.POST)
    public ResponseEntity postInventoryAssignment(@Valid @RequestBody InventoryAssignmentCmd form, BindingResult result)
    {
        ResponseEntity responseEntity = null;
        try
        {
            if(result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            inventoryAssignmentService.saveForm(form);
            responseEntity = ResponseEntity.ok().build();
        }
        catch(Exception e)
        {
            log.error("POST INVENTORY ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value="/inventory-assignment/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearchInventoryAssignment(InventoryAssignmentSearchForm form, BindingResult result, HttpServletRequest httpServletRequest)
    {
        ResponseEntity responseEntity = null;
        try
        {
            TablePagination<InventoryAssignmentItem> tablePagination = inventoryAssignmentService.getTablePagination(form);
            responseEntity = ResponseEntity.ok(tablePagination);
            log.info(">>> tablePagination : {}", d.toString(tablePagination));
        }
        catch (Exception e)
        {
            log.error("GET INVENTORY ASSIGNMENT TABLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/inventory-assignment/{id}", method = RequestMethod.PUT)
    public ResponseEntity putInventoryAssignment(@PathVariable("id") Integer id, @Valid @RequestBody InventoryAssignmentCmd form, BindingResult result)
    {
        ResponseEntity responseEntity = null;
        try
        {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            inventoryAssignmentService.update(form);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            log.error("PUT INVENTORY ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/inventory-assignment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteInventoryAssignment(@PathVariable("id") Integer id)
    {
        ResponseEntity responseEntity = null;
        try
        {
            inventoryAssignmentService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            log.error("DELETE INVENTORY ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value ="/inventory-assignment/{id}", method = RequestMethod.GET)
    public ResponseEntity getInventoryAssignment(@PathVariable("id") Integer id)
    {
        ResponseEntity responseEntity = null;
        try
        {
            InventoryAssignmentItem item = inventoryAssignmentService.getInventoryAssignmentItemById(id);
            responseEntity = ResponseEntity.ok(item);
        }
        catch (Exception e)
        {
            log.error("GET INVENTORY ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/inventory-assignment/remove-assignment/{id}", method = RequestMethod.PUT)
    public ResponseEntity removeInventoryAssignment(@PathVariable("id") Integer id)
    {
        ResponseEntity responseEntity = null;
        try
        {
            inventoryAssignmentService.removeAssignment(id);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e)
        {
            log.error("REMOVE INVENTORY ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
}
