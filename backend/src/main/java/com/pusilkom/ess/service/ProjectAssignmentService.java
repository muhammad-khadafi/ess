package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.LogActivityDetailCmd;
import com.pusilkom.ess.dto.form.cmd.ProjectAssignmentCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSearchForm;
import com.pusilkom.ess.dto.form.search.ProjectAssignmentSearchForm;
import com.pusilkom.ess.dto.table.EmployeeItem;
import com.pusilkom.ess.dto.table.ProjectAssignmentItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.ProjectAssignment;
import com.pusilkom.ess.model.mapper.EmployeeMapper;
import com.pusilkom.ess.model.mapper.ProjectAssignmentMapper;
import com.pusilkom.ess.model.mapper.UserwebHasRoleMapper;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class ProjectAssignmentService {
    @Autowired
    ProjectAssignmentMapper projectAssignmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    UserwebHasRoleMapper userwebHasRoleMapper;
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;

    public List<ProjectAssignmentItem> getAllProjectAssignment(Integer idProject, Integer months, Integer years,String subMenuName){
        List<ProjectAssignmentItem> list = projectAssignmentMapper.getListProjectAssignment(idProject, months, years, subMenuName);

        if(!list.isEmpty()){
            return list;
        } else {
            return null;
        }
    }

    public TablePagination<ProjectAssignmentItem> getTablePaginationGetAllEmployee(ProjectAssignmentSearchForm searchForm) {
        EmployeeSearchForm employeeSearchForm = new EmployeeSearchForm();
        employeeSearchForm.setPage(searchForm.getPage());
        employeeSearchForm.setPer_page(searchForm.getPerPage());
        employeeSearchForm.setId(searchForm.getEmployeeId());
        if(searchForm.getSort().contains("employeeName")) {
            employeeSearchForm.setSort("name|asc");
            System.out.println("masuk 1");
        }else if(searchForm.getSort().contains("name")) {
            employeeSearchForm.setSort(searchForm.getSort());
            searchForm.setSort(null);
            System.out.println("masuk 2");
        } else if(searchForm.getSort().contains("projectName")){
            searchForm.setSort(searchForm.getSort() + ",employeeName|asc");
        }
        List<ProjectAssignmentItem> listItem = projectAssignmentMapper.getListProjectAssignmentBySearchForm(searchForm);
        List<ProjectAssignmentItem> listItemtoSubmit = new ArrayList<ProjectAssignmentItem>();
        Long totalItemFiltered = employeeMapper.getTotalEmployeeItemBySearchForm(employeeSearchForm);
        Integer flagUnAssign = 0;
        //Get All Employee by Employee Mapper
        List<EmployeeItem> newListEmployee = employeeMapper.getListEmployeeItemBySearchFormForProjectAssignment(employeeSearchForm);
        List<EmployeeItem> listofEmployee = employeeMapper.getListEmployeeNotAssigninPorjectAssignment(employeeSearchForm, searchForm.getMonths(), searchForm.getYears());

        if(employeeSearchForm.getSort().contains("name") && searchForm.getEmployeeId() == null){
            System.out.println("check point 2 & tot " + totalItemFiltered);
            for (EmployeeItem e : newListEmployee) {
                searchForm.setEmployeeId(e.getId());
                List<ProjectAssignmentItem> listItem2 = projectAssignmentMapper.getProjectAssignmentByIdEmployee(e.getId(), searchForm);
                if(!listItem2.isEmpty()){
                    for(int i=0; i<listItem2.size(); i++){
                        listItemtoSubmit.add(listItem2.get(i));
                    }
                } else {
                    listItemtoSubmit.add(this.setItemNotHaveProjectAssign(e.getId(), e.getName(), searchForm.getMonths(), searchForm.getYears()));
                }
            }

            totalItemFiltered = employeeMapper.getTotalEmployeeItemBySearchFormForProjectAssignment(employeeSearchForm);
            return new TablePagination<ProjectAssignmentItem>(searchForm, listItemtoSubmit, totalItemFiltered);
        }
        else if(!listItem.isEmpty()) {
            System.out.println("check point 1 : " + listItem.size() + " | employee : " + newListEmployee.size());
            for (EmployeeItem e : listofEmployee) {
                flagUnAssign = 0;
                for (int i = 0; i < listItem.size(); i++) {
                    if(e.getId() == listItem.get(i).getEmployeeId()){
                        flagUnAssign = 1;
                    }
                }
                if(flagUnAssign == 0 && listItem.size() < 10 && searchForm.getEmployeeId() == null){
                    listItem.add(this.setItemNotHaveProjectAssign(e.getId(), e.getName(), searchForm.getMonths(), searchForm.getYears()));
                }
            }
        } else if(!newListEmployee.isEmpty()){
            for (EmployeeItem e : newListEmployee) {
                listItem.add(this.setItemNotHaveProjectAssign(e.getId(), e.getName(), searchForm.getMonths(), searchForm.getYears()));
            }
        }

        System.out.println("check this--- : " + listItem.size());

        return new TablePagination<ProjectAssignmentItem>(searchForm, listItem, totalItemFiltered);
    }

    public TablePagination<ProjectAssignmentItem> getTablePaginationGetAllEmployeeByOrganizationId(ProjectAssignmentSearchForm searchForm) {
        EmployeeSearchForm employeeSearchForm = new EmployeeSearchForm();
        employeeSearchForm.setOrganizationId(searchForm.getOrganizationId());
        employeeSearchForm.setPage(searchForm.getPage());
        employeeSearchForm.setPer_page(searchForm.getPerPage());
        employeeSearchForm.setId(searchForm.getEmployeeId());
        List<ProjectAssignmentItem> listItemToTable = new ArrayList<ProjectAssignmentItem>();
        Long totalItemFiltered = employeeMapper.getTotalEmployeeItemBySearchFormByOrganizationId(employeeSearchForm);
        //Get All Employee by Employee Mapper
        List<EmployeeItem> newListEmployee = employeeMapper.getListEmployeeItemBySearchFormByOrganizationId(employeeSearchForm);

        for (EmployeeItem e : newListEmployee) {
            searchForm.setEmployeeId(e.getId());
            List<ProjectAssignmentItem> listItem = projectAssignmentMapper.getProjectAssignmentByIdEmployee(e.getId(), searchForm);
            if(!listItem.isEmpty()){
                for(int i=0; i<listItem.size(); i++){
                    listItemToTable.add(listItem.get(i));
                }
            } else {
                listItemToTable.add(this.setItemNotHaveProjectAssign(e.getId(), e.getName(), searchForm.getMonths(), searchForm.getYears()));
            }
        }

        return new TablePagination<ProjectAssignmentItem>(searchForm, listItemToTable, totalItemFiltered);
    }

    /*public TablePagination<ProjectAssignmentItem> getTablePaginationByIdEmployeeNotHaveAssign(ProjectAssignmentSearchForm searchForm){
        Employee employee = employeeMapper.selectByPrimaryKey(searchForm.getEmployeeId());

        EmployeeSearchForm employeeSearchForm = new EmployeeSearchForm();
        List<ProjectAssignmentItem> listItemToTable = new ArrayList<ProjectAssignmentItem>();
        Long totalItemFiltered = employeeMapper.getTotalEmployeeItemBySearchForm(employeeSearchForm);

        listItemToTable.add(this.setItemNotHaveProjectAssign(employee.getId(), employee.getName()));
        return new TablePagination<ProjectAssignmentItem>(searchForm, listItemToTable, totalItemFiltered);
    }*/

    public ProjectAssignmentItem setItemNotHaveProjectAssign(Integer employeeId, String employeeName, Integer months, Integer years){
        ProjectAssignmentItem item = new ProjectAssignmentItem();
        item.setId(-1);
        item.setEmployeeId(employeeId);
        item.setEmployeeName(employeeName);
        item.setProjectName("-");
        item.setRoleName("-");
        item.setLevelName("-");
        item.setIsLeadString("-");
        item.setLoadString("-");
        item.setProjectStatus("-");
        item.setMonths(months);
        item.setYears(years);

        return item;
    }

    public TablePagination<ProjectAssignmentItem> getTablePagination(ProjectAssignmentSearchForm searchForm) {
        List<ProjectAssignmentItem> listItem = projectAssignmentMapper.getListProjectAssignmentBySearchForm(searchForm);
        Long totalItemFiltered = projectAssignmentMapper.getTotalProjectAssignmentItemBySearchForm(searchForm);

        return new TablePagination<ProjectAssignmentItem>(searchForm, listItem, totalItemFiltered);
    }

    public TablePagination<ProjectAssignmentItem> getTablePaginationForHome(ProjectAssignmentSearchForm searchForm) {
        List<ProjectAssignmentItem> listItem = projectAssignmentMapper.getTablePaginationForHome(searchForm);
        Long totalItemFiltered = projectAssignmentMapper.getTotalProjectAssignmentItemBySearchForm(searchForm);

        return new TablePagination<ProjectAssignmentItem>(searchForm, listItem, totalItemFiltered);
    }

    public TablePagination<ProjectAssignmentItem> getTablePaginationForHomePrev(ProjectAssignmentSearchForm searchForm) {
        List<ProjectAssignmentItem> listItem = projectAssignmentMapper.getTablePaginationForHomePrev(searchForm);
        Long totalItemFiltered = projectAssignmentMapper.getTotalProjectAssignmentItemBySearchForm(searchForm);

        return new TablePagination<ProjectAssignmentItem>(searchForm, listItem, totalItemFiltered);
    }

    public ProjectAssignmentItem getProjectAssignmentById(Integer id) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        ProjectAssignmentItem project = projectAssignmentMapper.getProjectAssignmentById(id);

        if(project.getStartDate() != null){
            project.setStartDateString(df.format(project.getStartDate()));
        }
        if(project.getEndDateString() != null){
            project.setEndDateString(df.format(project.getEndDate()));
        }

        return project;
    }

    @Transactional(readOnly = false)
    public void saveCmd(ProjectAssignmentCmd projectAssignment) throws ParseException {
        boolean isNew = (projectAssignment.getId() == null);
        if(isNew) {
            Calendar beginCalendar = Calendar.getInstance();
            Calendar finishCalendar = Calendar.getInstance();
            beginCalendar.setTime(projectAssignment.getStartDate());
            finishCalendar.setTime(projectAssignment.getEndDate());
            int beginMonth = beginCalendar.get(Calendar.MONTH);
            int endMonth = finishCalendar.get(Calendar.MONTH);

            projectAssignment.setCreatedBy(CommonUtil.getUserId());
            projectAssignment.setCreatedDate(CommonUtil.getCurrentDate());
            ProjectAssignment pa = projectAssignment.toProjectAssignment();

            if (beginMonth == endMonth) { //apabila start dan end date di bulan sama
                projectAssignmentMapper.insertSelective(pa);
            } else { //apabila lintas bulan, di loop
                Calendar comparedCalendar = Calendar.getInstance();
                comparedCalendar.setTime(projectAssignment.getStartDate());
                comparedCalendar.set(Calendar.DAY_OF_MONTH, 1);
                while (comparedCalendar.before(finishCalendar)) {
                    int loopMonth = comparedCalendar.get(Calendar.MONTH);
                    Calendar loopCalendar = Calendar.getInstance();
                    loopCalendar.setTime(comparedCalendar.getTime());
                    if (loopMonth - beginMonth == 0) { //bulan pertama
                        loopCalendar.add(Calendar.MONTH, 1);
                        loopCalendar.set(Calendar.DAY_OF_MONTH, 1);
                        loopCalendar.add(Calendar.DATE, -1);
                        pa.setEndDate(loopCalendar.getTime());
                    } else if (loopMonth - endMonth == 0) { //bulan akhir
                        loopCalendar.set(Calendar.DAY_OF_MONTH, 1);
                        pa.setStartDate(loopCalendar.getTime());
                        pa.setEndDate(projectAssignment.getEndDate());
                    } else { //selain bulan awal dan akhir
                        loopCalendar.set(Calendar.DAY_OF_MONTH, 1);
                        pa.setStartDate(loopCalendar.getTime());
                        loopCalendar.add(Calendar.MONTH, 1);
                        loopCalendar.add(Calendar.DATE, -1);
                        pa.setEndDate(loopCalendar.getTime());
                    }
                    projectAssignmentMapper.insertSelective(pa);
                    // add one month to date per loop
                    comparedCalendar.add(Calendar.MONTH, 1);
                }
            }
        } else {
            projectAssignment.setUpdatedBy(CommonUtil.getUserId());
            projectAssignment.setUpdatedDate(CommonUtil.getCurrentDate());
            projectAssignmentMapper.updateByPrimaryKey(projectAssignment.toProjectAssignment());
        }
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        projectAssignmentMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void deactiveEmployeeProjectAssignment(Integer id) {
        ProjectAssignment pa = projectAssignmentMapper.getProjectAssignmentById(id);
        if(pa.getFlagActive() == true) {
            pa.setFlagActive(false);
        } else {
            pa.setFlagActive(true);
        }
        projectAssignmentMapper.updateByPrimaryKey(pa);
    }

    public Boolean checkDuplicate(ProjectAssignmentCmd projectAssignmentSearchForm) {
        Long pa = projectAssignmentMapper.checkDuplicate(projectAssignmentSearchForm);
        if(pa > 0){
            return true;
        } else {
            return false;
        }
    }

    public Integer checkProjectEmployee(LogActivityDetailCmd logActivityDetailCmd) {
        Integer projectAssignmentId = projectAssignmentMapper.checkProjectEmployee(logActivityDetailCmd.getIdEmployee(),
                logActivityDetailCmd.getProjectId(), logActivityDetailCmd.getDate());
        return projectAssignmentId;
    }
}
