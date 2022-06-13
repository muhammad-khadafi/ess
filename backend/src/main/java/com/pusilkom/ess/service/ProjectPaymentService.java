package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.ProjectPaymentCmd;
import com.pusilkom.ess.dto.form.search.ProjectPaymentSearchForm;
import com.pusilkom.ess.dto.table.ProjectPaymentItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.ProjectPayment;
import com.pusilkom.ess.model.mapper.ProjectPaymentMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@Transactional
public class ProjectPaymentService {
    @Autowired
    ProjectPaymentMapper projectPaymentMapper;

    public void saveCmd(ProjectPaymentCmd projectPaymentCmd) throws Exception {
        this.save(projectPaymentCmd.toProjectPayment());
    }

    @Transactional(readOnly = false)
    public void save(ProjectPayment projectPayment) {
        if(projectPayment.getId() == null){
            projectPayment.setCreatedBy(CommonUtil.getUserId());
            projectPayment.setCreatedDate(CommonUtil.getCurrentDate());
            projectPaymentMapper.insert(projectPayment);
        } else {
            projectPayment.setUpdatedBy(CommonUtil.getUserId());
            projectPayment.setUpdatedDate(CommonUtil.getCurrentDate());
            projectPaymentMapper.updateByPrimaryKey(projectPayment);
        }
    }

    public TablePagination<ProjectPaymentItem> getTablePagination(ProjectPaymentSearchForm searchForm){
        List<ProjectPaymentItem> listItem = projectPaymentMapper.getListProjectPaymentItemBySearchForm(searchForm);
        Long totalItemFiletered = projectPaymentMapper.getTotalProjectPaymentItemBySearchForm(searchForm);

        return new TablePagination<ProjectPaymentItem>(searchForm, listItem, totalItemFiletered);
    }

    public ProjectPaymentCmd getProjectPaymentById(Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ProjectPayment projectPayment = projectPaymentMapper.selectByPrimaryKey(id);
        ProjectPaymentCmd projectPaymentCmd = new ProjectPaymentCmd();
        PropertyUtils.copyProperties(projectPaymentCmd, projectPayment);

        return projectPaymentCmd;
    }

    public ProjectPaymentItem getDetailProjectPayment(Integer id){
        return projectPaymentMapper.getDetailProjectPaymentItemBySearchForm(id);
    }

    public void delete (Integer id){
        projectPaymentMapper.deleteByPrimaryKey(id);
    }
}
