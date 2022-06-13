package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.diagram.OrganizationStructureDiagram;
import com.pusilkom.ess.dto.form.search.OrganizationStructureSearchForm;
import com.pusilkom.ess.dto.table.OrganizationStructureItem;
import com.pusilkom.ess.dto.view.OrganizationStructureDetail;
import com.pusilkom.ess.model.OrganizationStructure;
import com.pusilkom.ess.model.OrganizationStructureExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ITF on 11/8/2018.
 */
public interface OrganizationStructureMapper extends BaseMapper<OrganizationStructure, Integer, OrganizationStructureExample> {
    @Select("select * from organization_structure where is_active = true order by name asc")
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="name", column="name"),
    })
    public List<OrganizationStructureDetail> selectAllOrganizationStructure();

    List<OrganizationStructureItem> getListOrganizationStructureItemBySearchForm(@Param("searchForm") OrganizationStructureSearchForm searchForm);

    Long getTotalOrganizationStructureItemBySearchForm(@Param("searchForm") OrganizationStructureSearchForm searchForm);

    OrganizationStructureItem getOrganizationStructureByIdProject(@Param("idOrganization") Integer idOrganization);

    List<OrganizationStructureDiagram> getOrganizationStructureDiagram();

    List<OrganizationStructureDiagram> getOrganizationStructureNotes();


}
