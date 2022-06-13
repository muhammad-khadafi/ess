package com.pusilkom.ess.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.pusilkom.ess.dto.form.cmd.InventoryCmd;
import com.pusilkom.ess.dto.form.search.InventorySearchForm;
import com.pusilkom.ess.dto.table.InventoryItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.Inventory;
import com.pusilkom.ess.model.InventoryAssignment;
import com.pusilkom.ess.model.InventoryAssignmentExample;
import com.pusilkom.ess.model.mapper.InventoryAssignmentMapper;
import com.pusilkom.ess.model.mapper.InventoryMapper;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InventoryService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    InventoryMapper inventoryMapper;
    @Autowired
    InventoryAssignmentMapper inventoryAssignmentMapper;
    @Autowired
    InventoryAssignmentService inventoryAssignmentService;
    @Autowired
    DebugUtil d;
    @Value("${dir.qrCode}")
    String DIR_QRCODE;
    @Value("${url.to.encode}")
    String URL_ENCODE;
    static final SimpleDateFormat ssdf = new SimpleDateFormat ("dd-MM-yyyy");

    @Transactional
    public void saveForm(InventoryCmd form) throws ParseException, Exception{
        log.info("isi dari data inventorycmd : {}", d.toString(form));
        boolean isNew = (form.getId() == null);

        if(isNew)
        {
            Inventory aNew = form.toInventory();
            Integer codeNumber = 1;
            InventoryItem inventoryItem = inventoryMapper.getLastInventoryCode(aNew.getInventoryCode());
            if(inventoryItem != null){
                codeNumber = Integer.parseInt(inventoryItem.getInventoryCode().substring(2));
                codeNumber++;
            }
            aNew.setInventoryCode(aNew.getInventoryCode() + "" + String.format("%04d", codeNumber));
            aNew.setCreatedBy(CommonUtil.getUserId());
            aNew.setCreatedDate(new Date());
            inventoryMapper.insert(aNew);

            Integer idInventory = aNew.getId();
            for (InventoryAssignment ia : form.getListInventoryAssignment()) {
                ia.setInventoryId(idInventory);
                inventoryAssignmentMapper.insert(ia);
            }

            try {
//                generateQRCodeImage("This is my first QR Code", 350, 350, QR_CODE_IMAGE_PATH);
                //URL DEV PROD Belong Here
                generateQRCodeImages(URL_ENCODE + "/#/inventory/view/" + idInventory, 350, 350, idInventory);
            } catch (WriterException e) {
                System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
            }
        }
        else
        {
            SimpleDateFormat ssdf = new SimpleDateFormat ("dd-MM-yyyy");
            Inventory oldInventory = inventoryMapper.selectByPrimaryKey(form.getId());
            oldInventory.setType(form.getType());
            oldInventory.setName(form.getName());
            oldInventory.setSpecification(form.getSpecification());
            oldInventory.setLocation(form.getLocation());
            if(!StringUtils.isEmpty(form.getPurchaseDateString())) {
                oldInventory.setPurchaseDate(ssdf.parse(form.getPurchaseDateString()));
            }
            oldInventory.setPurchasePrice(form.getPurchasePrice());
            oldInventory.setCondition(form.getCondition());
            oldInventory.setIsAssigned(form.getIsAssigned());
            oldInventory.setUpdatedBy(CommonUtil.getUserId());
            oldInventory.setUpdatedDate(new Date());

            inventoryMapper.updateByPrimaryKey(oldInventory);
        }
    }

    public TablePagination<InventoryItem> getTablePagination(InventorySearchForm form)
    {
        List<InventoryItem> listItem = inventoryMapper.getListInventoryItemBySearchForm(form);
        Integer counts = 0;
        String getInventoryAssignmentEmployeeName = null;
        for(InventoryItem item : listItem) {
            getInventoryAssignmentEmployeeName = inventoryAssignmentService.getInventoryAssignmentByIdInventory(item.getId());
            if(getInventoryAssignmentEmployeeName.equalsIgnoreCase("")){
                listItem.get(counts).setEmployeeName("No");
            } else {
                listItem.get(counts).setEmployeeName(getInventoryAssignmentEmployeeName);
            }
            counts++;
        }
        Long totalItemFiltered = inventoryMapper.getTotalInventoryItemBySearchForm(form);

        return new TablePagination<InventoryItem>(form, listItem, totalItemFiltered);
    }

    public InventoryCmd getDetailInventorytoCmd(Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Inventory inven = inventoryMapper.selectByPrimaryKey(id);
        InventoryItem inven = inventoryMapper.getDetailInventoryItemByIdInventory(id);
        InventoryCmd invenCmd = new InventoryCmd();
        PropertyUtils.copyProperties(invenCmd, inven);
        if(inven.getPurchaseDate() != null) {
            invenCmd.setPurchaseDateString(ssdf.format(inven.getPurchaseDate()));
        }
        if(inven.getId() != null) {
            invenCmd.setIsNew(false);
        }

        if(invenCmd.getPurchasePrice() != null ){
            invenCmd.setPricewithSeparator(NumberFormat.getNumberInstance(Locale.ITALIAN).format(invenCmd.getPurchasePrice().longValue()));
        }

        return invenCmd;
    }

    public void delete(Integer id) {
        InventoryAssignmentExample ex = new InventoryAssignmentExample();
        ex.createCriteria().andInventoryIdEqualTo(id);
        inventoryAssignmentMapper.deleteByExample(ex);
        inventoryMapper.deleteByPrimaryKey(id);
    }

    public List<InventoryItem> getForDropDownInventory() {
        return inventoryMapper.getListInventoryItemAll();
    }

    public static void generateQRCodeImages(String texts, int width, int height, int idInventory) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(texts, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath("./data/qrCode/" + idInventory + ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
