package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.SubProjectSearchForm;
import com.pusilkom.ess.dto.table.SubProjectItem;
import com.pusilkom.ess.model.SubProject;
import com.pusilkom.ess.model.SubProjectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface SubProjectMapper extends BaseMapper<SubProject, Integer, SubProjectExample> {

    List<SubProjectItem> getListSubProjectItemBySearchForm(@Param("idEmployee") Integer idEmployee);

    List<SubProjectItem> getListSubProjectItemByIdProject(@Param("idProject") Integer idProject);

    List<SubProjectItem> getListSubProjectItemBySearchForm2(@Param("searchForm") SubProjectSearchForm searchForm);
    Long getTotalSubProjectItemBySearchForm2(@Param("searchForm") SubProjectSearchForm searchForm);

    SubProjectItem getSubProjectItemById(@Param("idProject") Integer idProject);

    Long checkDuplicate(@Param("subProject") SubProject subProject);

    List<SubProjectItem> getListSubProjectNonAj();
}
