package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.LeaveRequestCmd;
import com.pusilkom.ess.dto.form.cmd.RoleCmd;
import com.pusilkom.ess.dto.form.search.LeaveRequestSearchForm;
import com.pusilkom.ess.dto.form.search.RoleSearchForm;
import com.pusilkom.ess.dto.table.LeaveRequestItem;
import com.pusilkom.ess.dto.table.RoleItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.Employee;
import com.pusilkom.ess.model.LeaveRequest;
import com.pusilkom.ess.service.LeaveRequestService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
public class LeaveRequestController extends BaseRestController {
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    LeaveRequestService leaveRequestService;

    @RequestMapping(value = "/leave-request/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(LeaveRequestSearchForm searchForm, BindingResult result, HttpServletRequest httpServletRequest){
        log.info("Leave request Search Form : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(searchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<LeaveRequestItem> tablePagination = leaveRequestService.getTablePaginationRole(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE Leave Request ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value="/leave-request/diff/{startDate}/{endDate}/{employeeId}/{years}/{leaveTypeId}", method = RequestMethod.GET)
    public ResponseEntity getNumberOfDaysLeave(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate,
                                               @PathVariable("employeeId") Integer employeeId, @PathVariable("years") Integer years,
                                               @PathVariable("leaveTypeId") Integer leaveTypeId)
    {
        ResponseEntity responseEntity = null;
        try
        {
            responseEntity = ResponseEntity.ok(leaveRequestService.getNumberOfDays(startDate, endDate, employeeId, years, leaveTypeId));
        }
        catch (Exception e)
        {
            log.error("GET NAME AND NIK EMPLOYEE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/leave-request", method = RequestMethod.POST)
    public ResponseEntity postLeaveRequest(@RequestBody LeaveRequestCmd leaveRequestCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(leaveRequestCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            leaveRequestService.saveCmd(leaveRequestCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST LEAVE REQUEST", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/leave-request/get-by-id/{id}", method = RequestMethod.GET)
    public ResponseEntity getLeaveRequestById(@PathVariable("id") Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            LeaveRequest leaveRequest = leaveRequestService.getById(new Long(id));
            log.info("Cek isi leaveRequest Item from get by id : {}", d.toString(leaveRequest));
            responseEntity = ResponseEntity.ok(leaveRequest);
        }
        catch (Exception e)
        {
            log.error("GET ROLE ", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/leave-request/{id}", method = RequestMethod.PUT)
    public ResponseEntity putLeaveRequest(@RequestBody LeaveRequestCmd leaveRequestCmd, @PathVariable("id") Integer id, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(leaveRequestCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            leaveRequestService.saveCmd(leaveRequestCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT LEAVE REQUEST", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/leave-request/delete", method = RequestMethod.POST)
    public ResponseEntity deleteLeaveRequest(@RequestBody LeaveRequestCmd leaveRequestCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** DELETE CMD : {}", d.toString(leaveRequestCmd));

            leaveRequestService.deleteLeave(leaveRequestCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE LEAVE REQUEST", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
