package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.ProjectContractCmd;
import com.pusilkom.ess.dto.form.search.ProjectContractSearchForm;
import com.pusilkom.ess.dto.table.ProjectContractItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.ProjectContract;
import com.pusilkom.ess.model.ProjectContractExample;
import com.pusilkom.ess.model.mapper.ProjectContractMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectContractService {
    @Autowired
    ProjectContractMapper projectContractMapper;

    public void saveCmd(ProjectContractCmd projectContractCmd) throws Exception{
        this.save(projectContractCmd.toProjectContract());
    }

    @Transactional(readOnly = false)
    public void save(ProjectContract projectContract){
        if(projectContract.getId() == null){
            projectContract.setCreatedBy(CommonUtil.getUserId());
            projectContract.setCreatedDate(CommonUtil.getCurrentDate());
            projectContractMapper.insert(projectContract);
        } else {
            projectContract.setUpdatedBy(CommonUtil.getUserId());
            projectContract.setCreatedDate(CommonUtil.getCurrentDate());
            projectContractMapper.updateByPrimaryKey(projectContract);
        }
    }

    public TablePagination<ProjectContractItem> getTablePagination(ProjectContractSearchForm projectContractSearchForm){
        List<ProjectContractItem> listItem = projectContractMapper.getListProjectContractItemBySearchForm(projectContractSearchForm);
        Long totalItemFiletered = projectContractMapper.getTotalProjectContractItemBySearchForm(projectContractSearchForm);

        return new TablePagination<ProjectContractItem>(projectContractSearchForm, listItem, totalItemFiletered);
    }

    public ProjectContractItem getById(Integer id){
        ProjectContractItem projectContractItem = projectContractMapper.getProjectContractItemById(id);

        return projectContractItem;
    }

    public List<ProjectContract> getAllProjectContract (){
        ProjectContractExample ex = new ProjectContractExample();

        List<ProjectContract> listContract = projectContractMapper.selectByExample(ex);
        if (!listContract.isEmpty()){
            return listContract;
        } else {
            return listContract;
        }
    }

    public void delete(Integer id) {
        projectContractMapper.deleteByPrimaryKey(id);
    }
}
