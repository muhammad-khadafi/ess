package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.ProjectContractSearchForm;
import com.pusilkom.ess.dto.table.ProjectContractItem;
import com.pusilkom.ess.model.ProjectContract;
import com.pusilkom.ess.model.ProjectContractExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface ProjectContractMapper extends BaseMapper<ProjectContract, Integer, ProjectContractExample> {

    List<ProjectContractItem> getListProjectContractItemBySearchForm(@Param("searchForm") ProjectContractSearchForm searchForm);

    Long getTotalProjectContractItemBySearchForm(@Param("searchForm") ProjectContractSearchForm searchForm);

    ProjectContractItem getProjectContractItemById(@Param("idProjectContract") Integer idProjectContract);
}
