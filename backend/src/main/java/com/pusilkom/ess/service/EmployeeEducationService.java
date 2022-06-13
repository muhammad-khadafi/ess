package com.pusilkom.ess.service;


import com.pusilkom.ess.dto.form.cmd.EducationCmd;
import com.pusilkom.ess.dto.form.search.EmployeeEducationSearchForm;
import com.pusilkom.ess.dto.table.EmployeeEducationItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeEducation;
import com.pusilkom.ess.model.mapper.EmployeeEducationMapper;
import com.pusilkom.ess.model.mapper.EmployeeMapper;
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
public class EmployeeEducationService {
    @Autowired
    EmployeeEducationMapper eduMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = false)
    public void saveCmd(EducationCmd education) {
        boolean isNew = (education.getId() == null);
        if(isNew) {
            education.setCreatedBy(CommonUtil.getUserId());
            education.setCreatedDate(CommonUtil.getCurrentDate());
            eduMapper.insertSelective(education.toEmployeeEducation());
        } else {
            EmployeeEducation old = eduMapper.selectByPrimaryKey(education.getId());
            education.setCreatedBy(old.getCreatedBy());
            education.setCreatedDate(old.getCreatedDate());
            education.setUpdatedBy(CommonUtil.getUserId());
            education.setUpdatedDate(CommonUtil.getCurrentDate());
            eduMapper.updateByPrimaryKey(education.toEmployeeEducation());
        }
    }

    public TablePagination<EmployeeEducationItem> getTablePagination(EmployeeEducationSearchForm searchForm) {
        List<EmployeeEducationItem> listItem = eduMapper.getListEmployeeEducationItemBySearchForm(searchForm);
        Long totalItemFiltered = eduMapper.getTotalEmployeeEducationItemBySearchForm(searchForm);

        return new TablePagination<EmployeeEducationItem>(searchForm, listItem, totalItemFiltered);
    }

    public EmployeeEducation getEmployeeEducationById(Integer id) {
        //log.info("*** POST education by ID : {}", d.toString(eduMapper.getEducationById(id)));
//        EmployeeEducationExample ex = new EmployeeEducationExample();
//        ex.createCriteria().andEmployeeIdEqualTo(id);
//        List<EmployeeEducation> emp = eduMapper.selectByExample(ex);
       // eduMapper.selectByPrimaryKey(id);
        log.info("*** POST education by ID : {}", d.toString(eduMapper.selectByPrimaryKey(id)));
        return eduMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        eduMapper.deleteByPrimaryKey(id);
    }
}

