package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.search.LeaveApprovalSearchForm;
import com.pusilkom.ess.dto.table.LeaveApprovalItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.LeaveType;
import com.pusilkom.ess.service.LeaveApprovalService;
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
 * Created by muhammad.khadafi on 27/02/2020.
 */

@RestController
public class LeaveApprovalController extends BaseRestController {

    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    LeaveApprovalService leaveApprovalService;


    @RequestMapping(value = "/leave-approval/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid LeaveApprovalSearchForm leaveApprovalSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("Leave Approval Search Form : {}", d.toString(leaveApprovalSearchForm));
        log.info("Query : {}", d.toString(leaveApprovalSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(leaveApprovalSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<LeaveApprovalItem> tableData = leaveApprovalService.getTableData(leaveApprovalSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tableData));

            responseEntity = ResponseEntity.ok(tableData);

        } catch (Exception e) {
            log.error("APPROVAL LIST TABLE ERROR : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/leave-type/all", method = RequestMethod.GET)
    public ResponseEntity getLeaveType() {

        ResponseEntity responseEntity = null;
        try {


            List<LeaveType> leaveTypes = leaveApprovalService.getLeaveType();

            responseEntity = ResponseEntity.ok(leaveTypes);

        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/leave-approval/{id}/{operation}/{role}", method = RequestMethod.PUT)
    public ResponseEntity putLeaveApproval(@PathVariable Long id, @PathVariable String operation, @PathVariable String role) {
        ResponseEntity responseEntity = null;

        try {
            leaveApprovalService.updateStatus(id, operation, role);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("APPROVAL OPERATION ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

}
