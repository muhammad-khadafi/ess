package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.EmployeeSalaryCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSalarySearchForm;
import com.pusilkom.ess.dto.table.EmployeeSalaryItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeSalary;
import com.pusilkom.ess.model.mapper.EmployeeSalaryMapper;
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
public class EmployeeSalaryService {
    @Autowired
    EmployeeSalaryMapper employeeSalaryMapper;

    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = false)
    public void saveEmployeeSalaryCmd(EmployeeSalaryCmd emp) {
        boolean isNew = (emp.getId() == null);
        if(isNew) {
            emp.setCreatedBy(CommonUtil.getUserId());
            emp.setCreatedDate(CommonUtil.getCurrentDate());
            employeeSalaryMapper.insertSelective(emp.toEmployeeSalary());
        } else {
            emp.setUpdatedBy(CommonUtil.getUserId());
            emp.setUpdatedDate(CommonUtil.getCurrentDate());
            employeeSalaryMapper.updateByPrimaryKey(emp.toEmployeeSalary());
        }
    }

    public TablePagination<EmployeeSalaryItem> getTablePagination(EmployeeSalarySearchForm searchForm) {
        List<EmployeeSalaryItem> listItem = employeeSalaryMapper.getListEmployeeSalaryItemBySearchForm(searchForm);
        Long totalItemFiltered = employeeSalaryMapper.getTotalEmployeeSalaryItemBySearchForm(searchForm);
        for (EmployeeSalaryItem emp : listItem) {
            emp.convDateToString();
        }
        return new TablePagination<EmployeeSalaryItem>(searchForm, listItem, totalItemFiltered);
    }

    public EmployeeSalary getEmployeeSalaryById(Integer id) {
        return employeeSalaryMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        employeeSalaryMapper.deleteByPrimaryKey(id);
    }
}

