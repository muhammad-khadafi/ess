package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.table.SubProAssignmentItem;
import com.pusilkom.ess.service.SubProAssignmentService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubProAssignmentController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    SubProAssignmentService subProAssignmentService;

    @RequestMapping(value = "/sub-pro-assignment/{id}", method = RequestMethod.GET)
    public ResponseEntity getProjectById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            List<SubProAssignmentItem> subProAssignment = subProAssignmentService.getByEmployeeId(id);
            log.info("Cek isi subProAssignment Item : {}", d.toString(subProAssignment));
            responseEntity = ResponseEntity.ok(subProAssignment);
        }
        catch (Exception e)
        {
            log.error("GET SUB PROJECT ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
}
