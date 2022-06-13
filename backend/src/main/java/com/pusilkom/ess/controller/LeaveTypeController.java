package com.pusilkom.ess.controller;

import com.pusilkom.ess.model.LeaveType;
import com.pusilkom.ess.model.Role;
import com.pusilkom.ess.service.LeaveTypeService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LeaveTypeController extends BaseRestController {
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    LeaveTypeService leaveTypeService;

    @RequestMapping(value = {"/leave-type/getAll"}, method = RequestMethod.GET)
    public ResponseEntity getAllRole() {
        ResponseEntity responseEntity = null;
        try
        {
            List<LeaveType> leaveTypeList = leaveTypeService.getListLeaveType();
            log.info("Cek isi leavetype item : {}", d.toString(leaveTypeList));
            responseEntity = ResponseEntity.ok(leaveTypeList);
        }
        catch (Exception e)
        {
            log.error("GET ALL ROLE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
}
