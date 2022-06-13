package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.InventoryAssignmentCmd;
import com.pusilkom.ess.dto.form.search.BaseTableSearchForm;
import com.pusilkom.ess.dto.form.search.InventoryAssignmentSearchForm;
import com.pusilkom.ess.dto.table.InventoryAssignmentItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.Inventory;
import com.pusilkom.ess.model.InventoryAssignment;
import com.pusilkom.ess.model.InventoryAssignmentExample;
import com.pusilkom.ess.model.mapper.InventoryAssignmentMapper;
import com.pusilkom.ess.model.mapper.InventoryMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class InventoryAssignmentService extends BaseTableSearchForm {
    @Autowired
    InventoryAssignmentMapper inventoryAssignmentMapper;
    @Autowired
    InventoryMapper inventoryMapper;
    public void saveForm(InventoryAssignmentCmd form) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(form.getId() == null)
        {
            InventoryAssignment aNew = new InventoryAssignment();
            PropertyUtils.copyProperties(aNew, form);
            inventoryAssignmentMapper.insert(aNew);

            /*Inventory invent = inventoryMapper.selectByPrimaryKey(form.getInventoryId());
            invent.setIsAssigned(true);
            invent.setUpdatedBy(CommonUtil.getUserId());
            invent.setUpdatedDate(new Date());
            inventoryMapper.updateByPrimaryKey(invent);*/
        }
        else
        {
            InventoryAssignment old = inventoryAssignmentMapper.selectByPrimaryKey(form.getId());
            PropertyUtils.copyProperties(old, form);
            inventoryAssignmentMapper.updateByPrimaryKey(old);
        }
    }

    public TablePagination<InventoryAssignmentItem> getTablePagination(InventoryAssignmentSearchForm form)
    {
        List<InventoryAssignmentItem> listItem = inventoryAssignmentMapper.getListInventoryAssignmentItemBySearchForm(form);
        Long totalItemFiltered = inventoryAssignmentMapper.getTotalInventoryAssignmentItemBySearchForm(form);

        return new TablePagination<InventoryAssignmentItem>(form, listItem, totalItemFiltered);
    }

    public void update (InventoryAssignmentCmd form) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        /*InventoryAssignment old = inventoryAssignmentMapper.selectByPrimaryKey(form.getId());
        //update old inven jika inven tidak sama
        if(!(form.getInventoryId().intValue() == old.getInventoryId().intValue()))
        {
            Inventory oldInvent = inventoryMapper.selectByPrimaryKey(old.getInventoryId());
            oldInvent.setIsAssigned(false);
            oldInvent.setUpdatedBy(CommonUtil.getUserId());
            oldInvent.setUpdatedDate(new Date());
            inventoryMapper.updateByPrimaryKey(oldInvent);

            Inventory newInvent = inventoryMapper.selectByPrimaryKey(form.getInventoryId());
            newInvent.setIsAssigned(true);
            newInvent.setUpdatedBy(CommonUtil.getUserId());
            newInvent.setUpdatedDate(new Date());
            inventoryMapper.updateByPrimaryKey(newInvent);
        }*/
        saveForm(form);
    }
    public void delete(Integer id) {
        InventoryAssignment assignment = inventoryAssignmentMapper.selectByPrimaryKey(id);
        //update is assigned barang
        Inventory invent = inventoryMapper.selectByPrimaryKey(assignment.getInventoryId());
        invent.setIsAssigned(false);
        invent.setUpdatedBy(CommonUtil.getUserId());
        invent.setUpdatedDate(new Date());
        inventoryMapper.updateByPrimaryKey(invent);
        //delete inventoryassignment
        inventoryAssignmentMapper.deleteByPrimaryKey(id);
    }

    public InventoryAssignmentItem getInventoryAssignmentItemById(Integer id)
    {
        return inventoryAssignmentMapper.getInventoryAssignmentItemById(id);
    }

    public String getInventoryAssignmentByIdInventory(Integer id) {
        String employeeName = "";
        Integer counts = 0;
        InventoryAssignmentExample ex = new InventoryAssignmentExample();
        ex.createCriteria().andInventoryIdEqualTo(id);
        List<InventoryAssignmentItem> list = inventoryAssignmentMapper.getInventoryAssignmentItemByIdInventory(id);
        if(!list.isEmpty()) {
            for(InventoryAssignmentItem items : list) {
                employeeName += (counts > 0 ? " | " : "") + items.getEmployeeName();
                counts++;
            }
        }

        return employeeName;
    }

    public void removeAssignment(Integer id) {
        InventoryAssignment assignment = inventoryAssignmentMapper.selectByPrimaryKey(id);
        assignment.setIsAssigned(false);
        assignment.setEndDate(new Date());
        inventoryAssignmentMapper.updateByPrimaryKey(assignment);
    }
}
