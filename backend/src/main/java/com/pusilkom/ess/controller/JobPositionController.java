package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.JobPositionCmd;
import com.pusilkom.ess.dto.form.search.JobPositionSearchForm;
import com.pusilkom.ess.dto.table.JobPositionItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.JobPositionDetail;
import com.pusilkom.ess.model.JobPosition;
import com.pusilkom.ess.service.JobPositionService;
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
 * Created by ITF on 11/1/2018.
 */
@RestController
public class JobPositionController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    JobPositionService jobPositionService;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;

    @RequestMapping(value = "/job-position/{id}", method = RequestMethod.GET)
    public ResponseEntity getJobPosition(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            JobPosition jobPosition = jobPositionService.getById(id);
            if (jobPosition == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(new JobPositionDetail(jobPosition));
        } catch (Exception e) {
            log.error("GET JOB POSITION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/job-position", method = RequestMethod.POST)
    public ResponseEntity postJobPosition(@Valid @RequestBody JobPositionCmd jobPositionCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(jobPositionCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            jobPositionService.saveCmd(jobPositionCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST JOB POSITION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/job-position/{id}", method = RequestMethod.PUT)
    public ResponseEntity putJobPosition(@PathVariable Integer id, @Valid @RequestBody JobPositionCmd jobPositionCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(jobPositionCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            jobPositionService.saveCmd(jobPositionCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT INSTITUSI", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/job-position/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid JobPositionSearchForm jobPositionSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("JobPositionSearchForm : {}", d.toString(jobPositionSearchForm));
        log.info("Query : {}", d.toString(jobPositionSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(jobPositionSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<JobPositionItem> tablePagination = jobPositionService.getTablePagination(jobPositionSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE JOB POSITION : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/job-position/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteJobPosition(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            jobPositionService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE JOB POSITION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/job-position/positionlist", method = RequestMethod.GET)
    public ResponseEntity getAllJobPosition() {
        ResponseEntity responseEntity = null;

        try {
            List<JobPositionDetail> jobPosition = jobPositionService.getAllJobPosition();
            responseEntity = ResponseEntity.ok(jobPosition);
        } catch (Exception e) {
            log.error("GET JOB POSITION", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
}
