package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.EmployeeBenefitCmd;
import com.pusilkom.ess.dto.form.search.EmployeeBenefitSearchForm;
import com.pusilkom.ess.dto.table.EmployeeBenefitItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeBenefit;
import com.pusilkom.ess.model.mapper.EmployeeBenefitMapper;
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
public class EmployeeBenefitService {
    @Autowired
    EmployeeBenefitMapper employeeBenefitMapper;

    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = false)
    public void saveEmployeeBenefitCmd(EmployeeBenefitCmd emp) {
        boolean isNew = (emp.getId() == null);
        if(isNew) {
            emp.setCreatedBy(CommonUtil.getUserId());
            emp.setCreatedDate(CommonUtil.getCurrentDate());
            employeeBenefitMapper.insertSelective(emp.toEmployeeBenefit());
        } else {
            EmployeeBenefit oldBen = employeeBenefitMapper.selectByPrimaryKey(emp.getId());
            emp.setCreatedBy(oldBen.getCreatedBy());
            emp.setCreatedDate(oldBen.getCreatedDate());
            emp.setUpdatedBy(CommonUtil.getUserId());
            emp.setUpdatedDate(CommonUtil.getCurrentDate());
            employeeBenefitMapper.updateByPrimaryKey(emp.toEmployeeBenefit());
        }
    }

    public TablePagination<EmployeeBenefitItem> getTablePagination(EmployeeBenefitSearchForm searchForm) {
        List<EmployeeBenefitItem> listItem = employeeBenefitMapper.getListEmployeeBenefitItemBySearchForm(searchForm);
        Long totalItemFiltered = employeeBenefitMapper.getTotalEmployeeBenefitItemBySearchForm(searchForm);

        return new TablePagination<EmployeeBenefitItem>(searchForm, listItem, totalItemFiltered);
    }

    public EmployeeBenefit getEmployeeBenefitById(Integer id) {
        return employeeBenefitMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        employeeBenefitMapper.deleteByPrimaryKey(id);
    }

    public Boolean checkDuplicate(EmployeeBenefitCmd employeeBenefitCmd) {
        Long pa = employeeBenefitMapper.checkDuplicate(employeeBenefitCmd);
        if(pa > 0){
            return true;
        } else {
            return false;
        }
    }
}

