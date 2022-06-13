package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.EmployeePositionCmd;
import com.pusilkom.ess.dto.form.search.EmployeePositionSearchForm;
import com.pusilkom.ess.dto.table.EmployeePositionItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeePosition;
import com.pusilkom.ess.model.mapper.EmployeePositionMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by taufan on 8/11/17.
 */

@Service
@Transactional
public class EmployeePositionService {
    @Autowired
    EmployeePositionMapper employeePositionMapper;

    public void saveCmd(EmployeePositionCmd employeePositionCmd){
        this.save(employeePositionCmd.toEmployeePosition());
    }

    @Transactional(readOnly = false)
    public void save(EmployeePosition employeePosition){
        if(employeePosition.getId() == null){
            employeePosition.setCreatedBy(CommonUtil.getUserId());
            employeePosition.setCreatedDate(CommonUtil.getCurrentDate());
            employeePositionMapper.insert(employeePosition);
        } else {
            System.out.println("###Cek isi is_active : " + employeePosition.getIsActive());
            employeePosition.setUpdatedBy(CommonUtil.getUserId());
            employeePosition.setUpdatedDate(CommonUtil.getCurrentDate());
            employeePositionMapper.updateByPrimaryKey(employeePosition);
        }
    }

    public TablePagination<EmployeePositionItem> getTablePagination(EmployeePositionSearchForm searchForm){
        List<EmployeePositionItem> listItem = employeePositionMapper.getListEmployeePositionItemBySearchForm(searchForm);
        for (EmployeePositionItem emp : listItem) {
            if(emp.getIsActive() != null){
                if(emp.getIsActive() == true){
                    emp.setIsActiveString("Active");
                } else {
                    emp.setIsActiveString("Not Active");
                }
            }
        }
        Long totalItemFiltered = employeePositionMapper.getTotalEmployeePositionItemBySearchForm(searchForm);

        return new TablePagination<EmployeePositionItem>(searchForm, listItem, totalItemFiltered);
    }

    public EmployeePositionItem getById(Integer id){
        return employeePositionMapper.getEmployeePositionByIdProject(id);
    }

    public void delete(Integer id){
        employeePositionMapper.deleteByPrimaryKey(id);
    }

    public Boolean checkDuplicate(EmployeePositionCmd employeePositionCmd) {
        Long pa = employeePositionMapper.checkDuplicate(employeePositionCmd);
        if(pa > 0){
            return true;
        } else {
            return false;
        }
    }
}
