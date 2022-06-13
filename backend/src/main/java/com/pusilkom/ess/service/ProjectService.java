package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.ProjectCmd;
import com.pusilkom.ess.dto.form.cmd.ProjectContractCmd;
import com.pusilkom.ess.dto.form.cmd.ProjectPaymentCmd;
import com.pusilkom.ess.dto.form.search.ProjectSearchForm;
import com.pusilkom.ess.dto.table.ProjectItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.*;
import com.pusilkom.ess.model.mapper.ProjectContractMapper;
import com.pusilkom.ess.model.mapper.ProjectMapper;
import com.pusilkom.ess.model.mapper.ProjectPaymentMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by taufan on 8/11/17.
 */

@Service
@Transactional
public class ProjectService {
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectContractMapper projectContractMapper;
    @Autowired
    ProjectPaymentMapper projectPaymentMapper;

    public void saveCmd(ProjectCmd projectCmd) throws Exception {
        this.save(projectCmd.toProject(), projectCmd.getListProjectContract(), projectCmd.getListProjectPayment());
    }

    @Transactional(readOnly = false)
    public void save(Project project, List<ProjectContractCmd> dataProjectContract,
                     List<ProjectPaymentCmd> dataProjectPaymentCmd){
        if(project.getId() == null){
            project.setCreatedDate(CommonUtil.getCurrentDate());
            project.setCreatedBy(CommonUtil.getUserId());
            projectMapper.insert(project);

            Integer idProject = project.getId();
            for(ProjectContractCmd pc : dataProjectContract){
                pc.setProjectId(idProject);
                pc.setCreatedBy(CommonUtil.getUserId());
                pc.setCreatedDate(CommonUtil.getCurrentDate());
                ProjectContract projectContract = pc.toProjectContract();
                projectContractMapper.insert(projectContract);

                Integer idProjContract = projectContract.getId();
                for(ProjectPaymentCmd pp : dataProjectPaymentCmd){
                    if(pc.getContractNumber().equalsIgnoreCase(pp.getContractNumber())){
                        pp.setContractId(idProjContract);
                        pp.setCreatedBy(CommonUtil.getUserId());
                        pp.setCreatedDate(CommonUtil.getCurrentDate());
                        projectPaymentMapper.insert(pp.toProjectPayment());
                    }
                }
            }
        }
        else {
            project.setUpdatedDate(CommonUtil.getCurrentDate());
            project.setUpdatedBy(CommonUtil.getUserId());
            projectMapper.updateByPrimaryKey(project);
        }
    }

    public TablePagination<ProjectItem> getTablePagination(ProjectSearchForm projectSearchForm){
        List<ProjectItem> listItem = projectMapper.getListProjectItemBySearchForm(projectSearchForm);
        Long totalItemFiltered = projectMapper.getTotalProjectItemBySearchForm(projectSearchForm);

        return new TablePagination<ProjectItem>(projectSearchForm, listItem, totalItemFiltered);
    }

    public ProjectItem getById(Integer id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        /*Project project = projectMapper.selectByPrimaryKey(id);
        ProjectCmd projectCmd = new ProjectCmd();
        PropertyUtils.copyProperties(projectCmd, project);*/

        ProjectItem projectItem = projectMapper.getProjectItemByIdProject(id);

        return projectItem;
    }

    public List<Project> getAllProject(){
        ProjectExample projectExample = new ProjectExample();
        projectExample.setOrderByClause("name asc");
        List<Project> listProject = projectMapper.selectByExample(projectExample);

        if(!listProject.isEmpty()){
            return listProject;
        } else {
            return null;
        }
    }

    public void delete(Integer id){
        ProjectContractExample exPc = new ProjectContractExample();
        exPc.createCriteria().andProjectIdEqualTo(id);
        List<ProjectContract> listProjectContracts = projectContractMapper.selectByExample(exPc);
        if(!listProjectContracts.isEmpty()){
            for (ProjectContract pc : listProjectContracts) {
                ProjectPaymentExample exPp = new ProjectPaymentExample();
                exPp.createCriteria().andContractIdEqualTo(pc.getId());
                projectPaymentMapper.deleteByExample(exPp);
            }
        }
        projectContractMapper.deleteByExample(exPc);
        projectMapper.deleteByPrimaryKey(id);
    }

    public List<Project> getAllProjectNonAj() {
        return projectMapper.getAllProjectNonAj();
    }
}
