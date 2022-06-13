package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.EmployeeDocumentCmd;
import com.pusilkom.ess.dto.form.search.EmployeeDocumentSearchForm;
import com.pusilkom.ess.dto.table.EmployeeDocumentItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeDocument;
import com.pusilkom.ess.model.mapper.EmployeeDocumentMapper;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeDocumentService {
    @Autowired
    EmployeeDocumentMapper employeeDocumentMapper;

    @Autowired
    DebugUtil d;

    @Autowired
    UploadUtil uploadUtil;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = false)
    public void saveEmployeeDocumentCmd(EmployeeDocumentCmd emp) {
        boolean isNew = (emp.getId() == null);
        if(isNew) {
            emp.setUploadTime(new Date());
            emp.setCreatedBy(CommonUtil.getUserId());
            emp.setCreatedDate(CommonUtil.getCurrentDate());
            employeeDocumentMapper.insertSelective(emp.toEmployeeDocument());
        } else {
            EmployeeDocument oldBen = employeeDocumentMapper.selectByPrimaryKey(emp.getId());
            if(!oldBen.getFileName().equals(emp.getFileName())){
                uploadUtil.deleteFileEmployeeDocument(oldBen.getFileName());
                emp.setUploadTime(new Date());
            }
            emp.setCreatedBy(oldBen.getCreatedBy());
            emp.setCreatedDate(oldBen.getCreatedDate());
            emp.setUpdatedBy(CommonUtil.getUserId());
            emp.setUpdatedDate(CommonUtil.getCurrentDate());
            employeeDocumentMapper.updateByPrimaryKey(emp.toEmployeeDocument());
        }
    }

    public TablePagination<EmployeeDocumentItem> getTablePagination(EmployeeDocumentSearchForm searchForm) {
        List<EmployeeDocumentItem> listItem = employeeDocumentMapper.getListEmployeeDocumentItemBySearchForm(searchForm);
        Long totalItemFiltered = employeeDocumentMapper.getTotalEmployeeDocumentItemBySearchForm(searchForm);
        for (EmployeeDocumentItem emp : listItem) {
            emp.convDateToString();
        }

        return new TablePagination<EmployeeDocumentItem>(searchForm, listItem, totalItemFiltered);
    }

    public EmployeeDocument getEmployeeDocumentById(Integer id) {
        return employeeDocumentMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        EmployeeDocument doc = employeeDocumentMapper.selectByPrimaryKey(id);
        uploadUtil.deleteFileEmployeeDocument(doc.getFileName());
        employeeDocumentMapper.deleteByPrimaryKey(id);
    }

    public Boolean checkDuplicate(EmployeeDocumentCmd employeeDocumentCmd) {
        Long pa = employeeDocumentMapper.checkDuplicate(employeeDocumentCmd);
        if(pa > 0){
            return true;
        } else {
            return false;
        }
    }
}

