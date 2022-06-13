package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.EmployeeAttendanceCmd;
import com.pusilkom.ess.dto.form.search.EmployeeAttendanceSearchForm;
import com.pusilkom.ess.dto.table.EmployeeAttendanceItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.service.EmployeeAttendanceService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EmployeeAttendanceController extends BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    EmployeeAttendanceService employeeAttendanceService;

    @RequestMapping(value = "/employee-attendance/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(EmployeeAttendanceSearchForm searchForm, BindingResult result, HttpServletRequest httpServletRequest) {
        log.info("EmployeeAttendanceSearchForm : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(searchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<EmployeeAttendanceItem> tablePagination = employeeAttendanceService.getTablePagination(searchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE EMPLOYEE ATTENDANCE : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-attendance/{id}/{activityDetail}", method = RequestMethod.PUT)
    public ResponseEntity putEmployeeAttendance(@PathVariable("id") Long id, @PathVariable("activityDetail") String activityDetail) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD INSERT ACT EMPLOYEE ATTENDANCE: {}", activityDetail);
            employeeAttendanceService.updateActivityDetail(id, activityDetail);
            responseEntity = ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("PUT EMPLOYEE ATTENDANCE ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    
    @RequestMapping(value = "/employee-attendance/{id}", method = RequestMethod.PUT)
    public ResponseEntity putEmployeeAttendance2(@PathVariable("id") Long id, @RequestBody EmployeeAttendanceCmd employeeAttendanceCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD INSERT ACT EMPLOYEE ATTENDANCE: {}", d.toString(employeeAttendanceCmd));
            employeeAttendanceService.updateActivityDetail(id, employeeAttendanceCmd.getActivityDetail());
            responseEntity = ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error("PUT EMPLOYEE ATTENDANCE ERROR", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/employee-attendance/getsummary/{id}/{dates}/{months}/{years}/{weeksofMonth}", method = RequestMethod.GET)
    public ResponseEntity getSummary(@PathVariable("id") Integer id, @PathVariable("dates") Integer dates, @PathVariable("months") Integer months,
                                     @PathVariable("years") Integer years, @PathVariable("weeksofMonth") Integer weeksofMonth) {
        EmployeeAttendanceSearchForm searchForm = new EmployeeAttendanceSearchForm();
        searchForm.setEmployeeId(id);
        searchForm.setDates(dates);
        searchForm.setMonths(months);
        searchForm.setYears(years);
        searchForm.setValid(true);
        log.info("EmployeeAttendanceSearchForm : {}", d.toString(searchForm));
        log.info("Query : {}", d.toString(searchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(searchForm));

            EmployeeAttendanceItem attendanceItem = employeeAttendanceService.getSummary(searchForm, weeksofMonth);

            log.info("Cek isi project Item : {}", d.toString(attendanceItem));

            responseEntity = ResponseEntity.ok(attendanceItem);

        } catch (Exception e) {
            log.error("Get EMPLOYEE ATTENDANCE : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
