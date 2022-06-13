package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.InstitusiCmd;
import com.pusilkom.ess.dto.form.search.InstitusiSearchForm;
import com.pusilkom.ess.dto.table.InstitusiItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.InstitusiDetail;
import com.pusilkom.ess.model.Institusi;
import com.pusilkom.ess.service.InstitusiService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import com.pusilkom.ess.validator.form.cmd.InstitusiCmdValidator;
import com.pusilkom.ess.validator.form.search.InstitusiSearchFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by fahri on 1/7/17.
 */
@RestController
public class InstitusiController extends BaseRestController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";

    @Autowired InstitusiService institusiService;
    @Autowired InstitusiCmdValidator institusiCmdValidator;
    @Autowired InstitusiSearchFormValidator institusiSearchFormValidator;
    @Autowired RestValidatorUtil rv;
    @Autowired DebugUtil d;

    @InitBinder("institusiCmd")
    protected void registerInstitusiCmdValidator(WebDataBinder binder) {
        binder.setValidator(institusiCmdValidator);
    }

    @InitBinder("institusiSearchForm")
    protected void registerInstitusiSearchFormValidator(WebDataBinder binder) {
        binder.setValidator(institusiSearchFormValidator);
    }

    @RequestMapping(value = "/institusi/{id}", method = RequestMethod.GET)
    public ResponseEntity getInstitusi(@PathVariable Long id) {
        ResponseEntity responseEntity = null;

        // Contoh query 3 cara :
        InstitusiDetail institusiDetail1 = institusiService.getInstitusiDetailByKodeInstitusiCara1("KODE06");
        log.info("institusiDetail1 : " + d.toString(institusiDetail1));
        InstitusiDetail institusiDetail2 = institusiService.getInstitusiDetailByKodeInstitusiCara2("KODE06");
        log.info("institusiDetail2 : " + d.toString(institusiDetail2));
        InstitusiDetail institusiDetail3 = institusiService.getInstitusiDetailByKodeInstitusiCara3("KODE06");
        log.info("institusiDetail3 : " + d.toString(institusiDetail3));
        // SELESAI CONTOH

        try {
            Institusi institusi = institusiService.getById(id);
            if (institusi == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(new InstitusiDetail(institusi));
        } catch (Exception e) {
            log.error("GET INSTITUSI", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/institusi", method = RequestMethod.POST)
    public ResponseEntity postInstitusi(@Valid @RequestBody InstitusiCmd institusiCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** POST CMD : {}", d.toString(institusiCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            institusiService.saveCmd(institusiCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST INSTITUSI", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/institusi/{id}", method = RequestMethod.PUT)
    public ResponseEntity putInstitusi(@PathVariable Long id, @Valid @RequestBody InstitusiCmd institusiCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(institusiCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            institusiService.saveCmd(institusiCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT INSTITUSI", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/institusi/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteInstitusi(@PathVariable Long id) {
        ResponseEntity responseEntity = null;

        try {
            institusiService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE INSTITUSI", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    // Contoh prototype grid dgn DataTable
//    @RequestMapping(value = "/institusi/table", method = RequestMethod.POST)
//    public ResponseEntity postTableSearch(@Valid @RequestBody InstitusiSearchForm institusiSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {
//
////        InstitusiSearchForm institusiSearchForm = new InstitusiSearchForm();
//        ResponseEntity responseEntity = null;
//        try {
//            log.info("*** POST TABLE : {}", d.toString(institusiSearchForm));
//
////            if (result.hasErrors()) {
//            if (false) {
//                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
//            }
//
//            DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(httpServletRequest);
//            log.info(">>> criterias : ", d.toString(criterias));
//
////            institusiSearchForm.setCriterias(criterias);
//
//            DataSet<InstitusiItem> dataSet = institusiService.getDataSet(institusiSearchForm);
//
//            log.info(">>> FORM : ", d.toString(institusiSearchForm));
//
//            responseEntity = ResponseEntity.ok(dataSet);
//
//        } catch (Exception e) {
//            log.error("TABLE INSTITUSI : ", e);
//
//            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
//        }
//
//        return responseEntity;
//    }
    // END

    @RequestMapping(value = "/institusi/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid InstitusiSearchForm institusiSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("InstitusiSearchForm : {}", d.toString(institusiSearchForm));
        log.info("Query : {}", d.toString(institusiSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(institusiSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<InstitusiItem> tablePagination = institusiService.getTablePagination(institusiSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE INSTITUSI : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }


}
