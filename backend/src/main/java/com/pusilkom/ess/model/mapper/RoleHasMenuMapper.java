package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.RoleHasMenuSearchForm;
import com.pusilkom.ess.dto.table.RoleHasMenuItem;
import com.pusilkom.ess.model.RoleHasMenu;
import com.pusilkom.ess.model.RoleHasMenuExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * Created by fahri on 1/7/17.
 */
public interface RoleHasMenuMapper extends BaseMapper<RoleHasMenu, Long, RoleHasMenuExample> {
    List<RoleHasMenuItem> getListRoleHasMenuBySearchForm(@Param("searchForm") RoleHasMenuSearchForm searchForm);
    List<RoleHasMenuItem> getListRoleHasMenuByIdRole(@Param("idRole") Long idRole);
    Long getTotalRoleHasMenuBySearchForm(@Param("searchForm") RoleHasMenuSearchForm searchForm);

    @Select("select count(*) "+
            "from role_has_menu " +
            "where id_role = #{roleId}")
    Integer countRoleHasMenuByRoleId(@Param("roleId") Long roleId);
}
