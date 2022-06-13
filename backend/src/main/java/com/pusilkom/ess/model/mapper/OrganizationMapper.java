package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.OrganizationSearchForm;
import com.pusilkom.ess.dto.table.OrganizationItem;
import com.pusilkom.ess.model.Organization;
import com.pusilkom.ess.model.OrganizationExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by fahri on 1/7/17.
 */
public interface OrganizationMapper extends BaseMapper<Organization, Integer, OrganizationExample> {

    List<OrganizationItem> getListOrganizationItemBySearchForm(@Param("organizationSearchForm") OrganizationSearchForm organizationSearchForm);

    Long getTotalOrganizationItemBySearchForm(@Param("organizationSearchForm") OrganizationSearchForm organizationSearchForm);

    @Select("select * from organization")
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="name", column="name"),
            @Result(property="type", column="type"),
            @Result(property="parentId", column="parent_id")
    })
    public List<Organization> selectAllOrganization();
}
