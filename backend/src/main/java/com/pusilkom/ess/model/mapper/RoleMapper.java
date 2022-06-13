package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.RoleSearchForm;
import com.pusilkom.ess.dto.table.RoleItem;
import com.pusilkom.ess.model.Role;
import com.pusilkom.ess.model.RoleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fahri on 1/7/17.
 */
public interface RoleMapper extends BaseMapper<Role, Long, RoleExample> {

    List<RoleItem> getListRoleItemBySearchForm(@Param("searchForm") RoleSearchForm searchForm);
    Long getTotalRoleItemBySearchForm(@Param("searchForm") RoleSearchForm searchForm);

    Long checkDuplicateKode(@Param("role") Role role);
    Long checkDuplicateLabel(@Param("role") Role role);

    @Select("select id, kode, label, keterangan " +
            "from role " +
            "where id = #{idRole}")
    RoleItem getRoleItemById(@Param("idRole") Integer idRole);

    List<RoleItem> getListRoleNotInUserwebHasRoleByIdUserweb(@Param("idUserweb") Long idUserweb);
}
