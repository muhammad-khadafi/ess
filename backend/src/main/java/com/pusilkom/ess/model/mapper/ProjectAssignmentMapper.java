package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.cmd.ProjectAssignmentCmd;
import com.pusilkom.ess.dto.form.search.ProjectAssignmentSearchForm;
import com.pusilkom.ess.dto.table.ProjectAssignmentItem;
import com.pusilkom.ess.model.ProjectAssignment;
import com.pusilkom.ess.model.ProjectAssignmentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface ProjectAssignmentMapper extends BaseMapper<ProjectAssignment, Integer, ProjectAssignmentExample> {


    List<ProjectAssignmentItem> getListProjectAssignment(@Param("idProject") Integer idProject, @Param("months") Integer months, @Param("years") Integer years, @Param("subMenuName") String subMenuName);
    List<ProjectAssignmentItem> getListProjectAssignmentBySearchForm(@Param("searchForm") ProjectAssignmentSearchForm searchForm);
    List<ProjectAssignmentItem> getTablePaginationForHome(@Param("searchForm") ProjectAssignmentSearchForm searchForm);
    List<ProjectAssignmentItem> getTablePaginationForHomePrev(@Param("searchForm") ProjectAssignmentSearchForm searchForm);
    List<ProjectAssignmentItem> getProjectAssignmentByIdEmployee(@Param("employeeId") Integer employeeId, @Param("searchForm") ProjectAssignmentSearchForm searchForm);
    Long getTotalProjectAssignmentItemBySearchForm(@Param("searchForm") ProjectAssignmentSearchForm searchForm);
    ProjectAssignmentItem getProjectAssignmentById(@Param("projectAssignmentId") Integer projectAssignmentId);
    Long checkDuplicate(@Param("searchForm") ProjectAssignmentCmd searchForm);

    @Select("select id " +
            "from project_assignment " +
            "where employee_id = #{employeeId} " +
            "and project_id = #{projectId} " +
            "and #{assignmentDate} between start_date and end_date")
    Integer checkProjectEmployee( @Param("employeeId") Integer employeeId, @Param("projectId") Integer projectId, @Param("assignmentDate") Date assignmentDate);
    /*Long getTotalProjectItemBySearchForm(@Param("searchForm") ProjectSearchForm searchForm);

    ProjectItem getProjectItemByIdProject(@Param("idProject") Integer idProject);*/
}
