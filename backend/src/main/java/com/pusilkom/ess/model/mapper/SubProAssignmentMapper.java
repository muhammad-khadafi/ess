package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.ProjectSearchForm;
import com.pusilkom.ess.dto.table.ProjectItem;
import com.pusilkom.ess.dto.table.SubProAssignmentItem;
import com.pusilkom.ess.model.SubProAssignment;
import com.pusilkom.ess.model.SubProAssignmentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface SubProAssignmentMapper extends BaseMapper<SubProAssignment, Integer, SubProAssignmentExample> {

    List<SubProAssignmentItem> getListSubProAssignItemByEmployeeId(@Param("idEmployee") Integer idEmployee);

    List<ProjectItem> getListProjectItemBySearchForm(@Param("searchForm") ProjectSearchForm searchForm);

    Long getTotalProjectItemBySearchForm(@Param("searchForm") ProjectSearchForm searchForm);

    ProjectItem getProjectItemByIdProject(@Param("idProject") Integer idProject);
}
