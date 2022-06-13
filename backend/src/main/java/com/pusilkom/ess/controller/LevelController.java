package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.LevelRateCmd;
import com.pusilkom.ess.dto.form.search.LevelSearchForm;
import com.pusilkom.ess.dto.table.LevelRateItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.service.LevelService;
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
 * Created by taufan on 8/11/17.
 */

@RestController
public class LevelController extends  BaseRestController{
    final static String ERROR_MSG = "Failed to process";
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    LevelService levelService;

    @RequestMapping(value = "/level/all", method = RequestMethod.GET)
    public ResponseEntity getAllLevel() {
        ResponseEntity responseEntity = null;
        try
        {
            List<MasterReference> levelList = levelService.getAllLevelForDropDown();
            log.info("Cek isi level item : {}", d.toString(levelList));
            responseEntity = ResponseEntity.ok(levelList);
        }
        catch (Exception e)
        {
            log.error("GET ALL LEVEL", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/level-rate/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid LevelSearchForm levelSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("levelSearchForm : {}", d.toString(levelSearchForm));
        log.info("Query : {}", d.toString(levelSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(levelSearchForm));
            TablePagination<LevelRateItem> tablePagination = levelService.getTablePagination(levelSearchForm);
            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE project assignment : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/level-rate/add", method = RequestMethod.POST)
    public ResponseEntity postLevelRate(@Valid @RequestBody LevelRateCmd levelRateCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("*** SEBELUM POST CMD : {}", d.toString(levelRateCmd));
        try {
            log.info("*** POST CMD : {}", d.toString(levelRateCmd));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            levelService.saveCmd(levelRateCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST level rate", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/level-rate/view/{id}", method = RequestMethod.GET)
    public ResponseEntity getProjectAssignmentById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            LevelRateItem pa = levelService.getLevelRateById(id);
            log.info(">>> get level rate : {}", d.toString(pa));
            if (pa == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(pa);
            log.info(">>> get level rate by id : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("get level rate", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/level-rate/{id}", method = RequestMethod.PUT)
    public ResponseEntity putLevelRate(@PathVariable Integer id, @Valid @RequestBody LevelRateCmd levelRateCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(levelRateCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            levelService.saveCmd(levelRateCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT LEVEL RATE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/level-rate/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLevelRate(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            levelService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE LEVEL RATE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}
