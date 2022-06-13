package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.EmployeeFamilyCmd;
import com.pusilkom.ess.dto.form.search.EmployeeFamilySearchForm;
import com.pusilkom.ess.dto.table.EmployeeFamilyItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeFamily;
import com.pusilkom.ess.model.mapper.EmployeeFamilyMapper;
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
public class EmployeeFamilyService {
    @Autowired
    EmployeeFamilyMapper employeeFamilyMapper;

    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = false)
    public void saveEmployeeFamilyCmd(EmployeeFamilyCmd emp) {
        boolean isNew = (emp.getId() == null);
        if(isNew) {
            emp.setCreatedBy(CommonUtil.getUserId());
            emp.setCreatedDate(CommonUtil.getCurrentDate());
            employeeFamilyMapper.insertSelective(emp.toEmployeeFamily());
        } else {
            EmployeeFamily old = employeeFamilyMapper.selectByPrimaryKey(emp.getId());
            emp.setCreatedBy(old.getCreatedBy());
            emp.setCreatedDate(old.getCreatedDate());
            emp.setUpdatedBy(CommonUtil.getUserId());
            emp.setUpdatedDate(CommonUtil.getCurrentDate());
            employeeFamilyMapper.updateByPrimaryKey(emp.toEmployeeFamily());
        }
    }

    public TablePagination<EmployeeFamilyItem> getTablePagination(EmployeeFamilySearchForm searchForm) {
        List<EmployeeFamilyItem> listItem = employeeFamilyMapper.getListEmployeeFamilyItemBySearchForm(searchForm);
        Long totalItemFiltered = employeeFamilyMapper.getTotalEmployeeFamilyItemBySearchForm(searchForm);
        for (EmployeeFamilyItem emp : listItem) {
            if(emp.getBirthDate() != null){
                emp.convBirthDateToString();
            }
        }
        return new TablePagination<EmployeeFamilyItem>(searchForm, listItem, totalItemFiltered);
    }

    public EmployeeFamily getEmployeeFamilyById(Integer id) {
        return employeeFamilyMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        employeeFamilyMapper.deleteByPrimaryKey(id);
    }
}

