package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeeAttendanceCmd;
import com.pusilkom.ess.dto.form.cmd.MasterReferenceCmd;
import com.pusilkom.ess.dto.table.EmployeeAttendanceItem;
import com.pusilkom.ess.model.EmployeeAttendance;
import com.pusilkom.ess.service.MobileService;
import com.pusilkom.ess.service.ReportService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by muhammad.khadafi on 07/08/19.
 */
@RestController
public class MobileController extends BaseRestController {

    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;

    @Autowired
    MobileService mobileService;


    @RequestMapping(value = "/clockin", method = RequestMethod.POST)
    public ResponseEntity clockIn(@Valid @RequestBody EmployeeAttendanceCmd employeeAttendanceCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** CLOCK IN PROCESS.. : {}", d.toString(employeeAttendanceCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            mobileService.clockIn(employeeAttendanceCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("CLOCK IN ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/clockout", method = RequestMethod.POST)
    public ResponseEntity clockOut(@Valid @RequestBody EmployeeAttendanceCmd employeeAttendanceCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** CLOCK OUT PROCESS.. : {}", d.toString(employeeAttendanceCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            mobileService.clockOut(employeeAttendanceCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("CLOCK OUT ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


    @RequestMapping(value = "/clock-in-out-status/{id}", method = RequestMethod.GET)
    public ResponseEntity clockInOutStatus(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** Get Clock In Clock Out Status .. : {}", d.toString(id));

            String resultStatus = mobileService.getClockInOutStatus(id);
            responseEntity = ResponseEntity.ok(resultStatus);
        } catch (Exception e) {
            log.error("Get Status ERROR ..", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/clock-in-out-status/get-object/{id}", method = RequestMethod.GET)
    public ResponseEntity clockInOutStatusObject(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** Get Clock In Clock Out Status .. : {}", d.toString(id));

            EmployeeAttendance resultStatus = mobileService.getClockInOutStatusReturnObject(id);
            responseEntity = ResponseEntity.ok(resultStatus);
        } catch (Exception e) {
            log.error("Get Status ERROR ..", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/view-log/{id}/{dateOfLog}", method = RequestMethod.GET)
    public ResponseEntity viewLog(@PathVariable Integer id, @PathVariable String dateOfLog) {
        ResponseEntity responseEntity = null;
        String month = dateOfLog.substring(0, 2);
        String year = dateOfLog.substring(3, 7);
        try {
            log.info("*** Get Attendance List .. : {}", d.toString(id));

            ArrayList<EmployeeAttendanceItem> resultStatus = mobileService.viewLog(id, month, year);
            responseEntity = ResponseEntity.ok(resultStatus);
        } catch (Exception e) {
            log.error("Get Status ERROR ..", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/view-log/get-month-year/", method = RequestMethod.GET)
    public ResponseEntity getMonthYear() {
        ResponseEntity responseEntity = null;
        try {
            Date currentDate = new Date();
            log.info("*** Get Month Year .. : {}", d.toString(currentDate));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String strDate = sdf.format(currentDate);

            responseEntity = ResponseEntity.ok(strDate);
        } catch (Exception e) {
            log.error("Get Month Year ERROR ..", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


}
