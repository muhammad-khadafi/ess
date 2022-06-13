package com.pusilkom.ess.model.mapper;


import com.pusilkom.ess.dto.form.cmd.EmployeeAssignmentReportCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSearchForm;
import com.pusilkom.ess.dto.table.EmployeeItem;
import com.pusilkom.ess.dto.table.TimesheetReportItem;
import com.pusilkom.ess.model.Employee;
import com.pusilkom.ess.model.EmployeeExample;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by arief on 10/31/18.
 */
public interface EmployeeMapper extends BaseMapper<Employee, Integer, EmployeeExample> {
    List<EmployeeItem> getListEmployeeItemBySearchForm(@Param("searchForm") EmployeeSearchForm searchForm);
    Long getTotalEmployeeItemBySearchForm(@Param("searchForm") EmployeeSearchForm searchForm);
    List<EmployeeItem> getListEmployeeItemBySearchFormByOrganizationId(@Param("searchForm") EmployeeSearchForm searchForm);
    Long getTotalEmployeeItemBySearchFormByOrganizationId(@Param("searchForm") EmployeeSearchForm searchForm);
    List<EmployeeItem> getListEmployeeItemBySearchFormForProjectAssignment(@Param("searchForm") EmployeeSearchForm searchForm);
    Long getTotalEmployeeItemBySearchFormForProjectAssignment(@Param("searchForm") EmployeeSearchForm searchForm);
    List<EmployeeItem> getListEmployeeNotAssigninPorjectAssignment(@Param("searchForm") EmployeeSearchForm searchForm, @Param("months") Integer months, @Param("years") Integer years);
    List<EmployeeItem> getListEmployeeNotAssign();
    List<EmployeeItem> getListEmployeeByRoleManager();
    List<EmployeeItem> getListEmployeeByProjectId(@Param("employeeId") Integer employeeId, @Param("months") Integer months, @Param("years") Integer years);

    List<EmployeeItem> getListEmployeeAJ();

    List<EmployeeItem> getListEmployeeNonAJ();

    List<TimesheetReportItem> getAllEmployeeActive();

    List<Employee> getEmployeeProjectAssignmentReport(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("selectedProjectList") List<Integer> selectedProjectList);

    List<EmployeeAssignmentReportCmd> getEmployeeProjectAssignmentReport2(@Param("id") Integer id, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("selectedProjectList") List<Integer> selectedProjectList);
}
