package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.EmployeeSkillsetCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSkillsetSearchForm;
import com.pusilkom.ess.dto.table.EmployeeSkillsetItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeSkillset;
import com.pusilkom.ess.model.mapper.EmployeeSkillsetMapper;
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
public class EmployeeSkillsetService {
    @Autowired
    EmployeeSkillsetMapper employeeSkillsetMapper;

    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = false)
    public void saveCmd(EmployeeSkillsetCmd emp) {
        boolean isNew = (emp.getId() == null);
        if(isNew) {
            emp.setCreatedBy(CommonUtil.getUserId());
            emp.setCreatedDate(CommonUtil.getCurrentDate());
            employeeSkillsetMapper.insertSelective(emp.toEmployeeSkillset());
        } else {
            EmployeeSkillset oldBen = employeeSkillsetMapper.selectByPrimaryKey(emp.getId());
            emp.setCreatedBy(oldBen.getCreatedBy());
            emp.setCreatedDate(oldBen.getCreatedDate());
            emp.setUpdatedBy(CommonUtil.getUserId());
            emp.setUpdatedDate(CommonUtil.getCurrentDate());
            employeeSkillsetMapper.updateByPrimaryKey(emp.toEmployeeSkillset());
        }
    }

    public TablePagination<EmployeeSkillsetItem> getTablePagination(EmployeeSkillsetSearchForm searchForm) {
        List<EmployeeSkillsetItem> listItem = employeeSkillsetMapper.getListEmployeeSkillsetItemBySearchForm(searchForm);
        Long totalItemFiltered = employeeSkillsetMapper.getTotalEmployeeSkillsetItemBySearchForm(searchForm);

        return new TablePagination<EmployeeSkillsetItem>(searchForm, listItem, totalItemFiltered);
    }

    public EmployeeSkillset getById(Integer id) {
        return employeeSkillsetMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        employeeSkillsetMapper.deleteByPrimaryKey(id);
    }

    public Boolean checkDuplicate(EmployeeSkillsetCmd employeeSkillsetCmd) {
        Long pa = employeeSkillsetMapper.checkDuplicate(employeeSkillsetCmd);
        if(pa > 0){
            return true;
        } else {
            return false;
        }
    }
}

