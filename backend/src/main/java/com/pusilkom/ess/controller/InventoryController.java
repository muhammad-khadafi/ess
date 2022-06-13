package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.form.cmd.InventoryCmd;
import com.pusilkom.ess.dto.form.search.InventorySearchForm;
import com.pusilkom.ess.dto.table.InventoryItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.service.InventoryService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import com.pusilkom.ess.validator.form.cmd.InventoryCmdValidator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@RestController
public class InventoryController extends BaseRestController{
    Logger log = LoggerFactory.getLogger(this.getClass());

    final static String ERROR_MSG = "Failed to process";
    @Autowired
    InventoryCmdValidator inventoryCmdValidator;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    DebugUtil d;
    @Autowired
    InventoryService inventoryService;

    @InitBinder("form")
    protected void registerInventoryCmdValidator(WebDataBinder binder) {
        binder.setValidator(inventoryCmdValidator);
    }

    @RequestMapping(value="/inventory", method = RequestMethod.POST)
    public ResponseEntity postInventory(@Valid @RequestBody InventoryCmd form, BindingResult result)
    {
        ResponseEntity responseEntity = null;
        try
        {
            if(result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            inventoryService.saveForm(form);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST INVENTORY", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/inventory/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearchInventory(InventorySearchForm form, BindingResult result, HttpServletRequest httpServletRequest)
    {
        ResponseEntity responseEntity = null;
        try
        {
            TablePagination<InventoryItem> tablePagination = inventoryService.getTablePagination(form);
            responseEntity = ResponseEntity.ok(tablePagination);
            log.info(">>> tablePagination : {}", d.toString(tablePagination));
        }
        catch(Exception e)
        {
            log.error("TABLE INVENTORY", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.GET)
    public ResponseEntity getInventoryById(@PathVariable("id") Integer id)
    {
        ResponseEntity responseEntity = null;
        try
        {
            InventoryCmd inventoryCmd = inventoryService.getDetailInventorytoCmd(id);
            log.info("Cek isi Item : {}", d.toString(inventoryCmd));
            responseEntity = ResponseEntity.ok(inventoryCmd);
        }
        catch (Exception e)
        {
            log.error("GET INVENTORY", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.PUT)
    public ResponseEntity putInventory(@PathVariable("id") Integer id, @Valid @RequestBody InventoryCmd form, BindingResult result)
    {
        ResponseEntity responseEntity = null;
        try
        {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            inventoryService.saveForm(form);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT INVENTORY", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteInventory(@PathVariable("id") Integer id) {
        ResponseEntity responseEntity = null;
        try
        {
            inventoryService.delete(id);
            responseEntity = ResponseEntity.ok().build();
        }
        catch(Exception e)
        {
            log.error("DELETE INVENTORY", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }
    @RequestMapping(value="/inventory/all", method =RequestMethod.GET)
    public ResponseEntity getListAllInventory() {
        ResponseEntity responseEntity = null;
        try
        {
            List<InventoryItem> listAllInvenItem = inventoryService.getForDropDownInventory();
            responseEntity = ResponseEntity.ok(listAllInvenItem);
        }
        catch(Exception e)
        {
            log.error("LIST ALL INVENTORY", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value="/inventory/nameAndType/{id}", method = RequestMethod.GET)
    public ResponseEntity getNameAndTypeInventory(@PathVariable("id") Integer id)
    {
        ResponseEntity responseEntity = null;
        try
        {
            InventoryCmd inventoryCmd = inventoryService.getDetailInventorytoCmd(id);
            responseEntity = ResponseEntity.ok(inventoryCmd.getName() + " - " + inventoryCmd.getType());
        }
        catch (Exception e)
        {
            log.error("GET NAME AND TYPE INVENTORY", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value="/inventory/getQR/{id}", method = RequestMethod.GET)
    public ResponseEntity getQRCodeByIdInventory(@PathVariable("id") Integer id, HttpServletResponse response)
    {
        ResponseEntity responseEntity = null;
        try
        {
            File files = new File("./data/qrCode/" + id + ".png");
            if (!files.exists()){
                System.out.println("=== file tidak ditemukan ===");
                responseEntity = ResponseEntity.badRequest().body(responseEntity);
                return responseEntity;
            }
            byte[] imgBytes = null;
            imgBytes = IOUtils.toByteArray(new FileInputStream(files));

            response.reset();
            response.resetBuffer();

            //this line to make browser download the file
            response.setContentType("image/png");
            response.setHeader("Content-Disposition", "attachment;filename=qr_code.png");

            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(new File("./data/qrCode/" + id + ".png"));
                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
            } catch (Exception e) {
                log.error("hasil :", e);
            } finally{
                IOUtils.closeQuietly(inputStream);
            }
            responseEntity = ResponseEntity.ok(imgBytes);
        }
        catch (Exception e)
        {
            log.error("GET NAME AND TYPE INVENTORY", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/download/qrCode/{id}/{names}", method = RequestMethod.GET)
    public ResponseEntity getReportPoc(@PathVariable Integer id, @PathVariable String names,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception  {

        ResponseEntity responseEntity = null;

        try {
            File files = new File("./data/qrCode/" + id + ".png");
            byte[] pdfBytes = null;
            pdfBytes = IOUtils.toByteArray(new FileInputStream(files));

            response.reset();
            response.resetBuffer();

            //this line to make browser download the file
            response.setContentType("image/png");
            response.setHeader("Content-Disposition", "attachment;filename=QRCode-"+ names.replace(' ', '-') +".png");

            try {
                response.getOutputStream().write(pdfBytes);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                response.flushBuffer();
            }
            catch (Exception e) {
                log.error("ERROR JASPER : "+e.getMessage(), e.getCause());
                e.printStackTrace();
            }
            log.info("RESPON : " + pdfBytes.toString());
            responseEntity = ResponseEntity.ok(pdfBytes);
        } catch (Exception ex) {
            log.error("ERROR JASPER : "+ex.getMessage(), ex.getCause());
            ex.printStackTrace();
        }

        return responseEntity;
    }
}
