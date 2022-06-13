package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.FundingSourceCmd;
import com.pusilkom.ess.dto.form.search.FundingSourceSearchForm;
import com.pusilkom.ess.dto.table.FundingSourceItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.FundingSource;
import com.pusilkom.ess.service.FundingSourceService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class FundingSourceController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    FundingSourceService fundingSourceService;

    @RequestMapping(value = "/funding-source/add", method = RequestMethod.POST)
    public ResponseEntity postFundingSource(@Valid @RequestBody FundingSourceCmd fundingSourceCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST FUNDING SOURCE CMD {}", d.toString(fundingSourceCmd));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            fundingSourceService.saveCmd(fundingSourceCmd);
            responseEntity = ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error("POST FUNDING SOURCE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
    @RequestMapping(value = "/funding-source/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid FundingSourceSearchForm fundingSourceSearchForm, BindingResult result) {

        log.info("FundingSourceSearchForm : {}", d.toString(fundingSourceSearchForm));
        log.info("Query : {}", d.toString(fundingSourceSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(fundingSourceSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<FundingSourceItem> tablePagination = fundingSourceService.getTablePagination(fundingSourceSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE FUNDING SOURCE : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/funding-source/{id}", method = RequestMethod.GET)
    public ResponseEntity getFundingSourceById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            FundingSource fundingSource = fundingSourceService.getFundingSourceById(id);
            log.info(">>> get funding source by id : {}", d.toString(fundingSource));
            if (fundingSource == null) {
                return ResponseEntity.notFound().build();
            }
           responseEntity = ResponseEntity.ok(fundingSource);
        } catch (Exception e) {
            log.error("GET FUNDING SOURCE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/funding-source/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteFundingSource(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            fundingSourceService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE FUNDING SOURCE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/funding-source/checkPettyCashHasFundingSource", method = RequestMethod.POST)
    public ResponseEntity checkUserwebHasRole(@RequestBody FundingSourceCmd fundingSourceCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(fundingSourceCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            Integer totalCount = fundingSourceService.countPettyCashHasFundingSource(fundingSourceCmd);
            responseEntity = ResponseEntity.ok(totalCount);
        } catch (Exception e) {
            log.error("POST FUNDING SOURCE", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }
}

