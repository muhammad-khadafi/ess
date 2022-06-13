package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.ProjectSearchForm;
import com.pusilkom.ess.dto.table.ProjectItem;
import com.pusilkom.ess.model.Project;
import com.pusilkom.ess.model.ProjectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface ProjectMapper extends BaseMapper<Project, Integer, ProjectExample> {

    List<ProjectItem> getListProjectItemBySearchForm(@Param("searchForm") ProjectSearchForm searchForm);

    Long getTotalProjectItemBySearchForm(@Param("searchForm") ProjectSearchForm searchForm);

    ProjectItem getProjectItemByIdProject(@Param("idProject") Integer idProject);

    List<Project> getAllProjectNonAj();
}
