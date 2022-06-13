package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.EmployeeContractCmd;
import com.pusilkom.ess.dto.form.search.EmployeeContractSearchForm;
import com.pusilkom.ess.dto.table.EmployeeContractItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeContract;
import com.pusilkom.ess.model.mapper.EmployeeContractMapper;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeContractService {
    @Autowired
    EmployeeContractMapper employeeContractMapper;

    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = false)
    public void saveEmployeeContractCmd(EmployeeContractCmd emp) {
        boolean isNew = (emp.getId() == null);
        if(isNew) {
            emp.setCreatedBy(CommonUtil.getUserId());
            emp.setCreatedDate(CommonUtil.getCurrentDate());
            employeeContractMapper.insertSelective(emp.toEmployeeContract());
        } else {
            emp.setUpdatedBy(CommonUtil.getUserId());
            emp.setUpdatedDate(CommonUtil.getCurrentDate());
            employeeContractMapper.updateByPrimaryKey(emp.toEmployeeContract());
        }
    }

    public TablePagination<EmployeeContractItem> getTablePagination(EmployeeContractSearchForm searchForm) {
        List<EmployeeContractItem> listItem = employeeContractMapper.getListEmployeeContractItemBySearchForm(searchForm);
        Long totalItemFiltered = employeeContractMapper.getTotalEmployeeContractItemBySearchForm(searchForm);
        for (EmployeeContractItem emp : listItem) {
            emp.convDateToString();
        }
        return new TablePagination<EmployeeContractItem>(searchForm, listItem, totalItemFiltered);
    }

    public EmployeeContract getEmployeeContractById(Integer id) {
        return employeeContractMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        employeeContractMapper.deleteByPrimaryKey(id);
    }
}

