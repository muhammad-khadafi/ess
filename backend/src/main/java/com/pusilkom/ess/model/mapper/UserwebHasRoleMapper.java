package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.UserWebHasRoleSearchForm;
import com.pusilkom.ess.dto.table.UserWebHasRoleItem;
import com.pusilkom.ess.model.UserwebHasRole;
import com.pusilkom.ess.model.UserwebHasRoleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * Created by fahri on 1/7/17.
 */
public interface UserwebHasRoleMapper extends BaseMapper<UserwebHasRole, Long, UserwebHasRoleExample> {
    List<UserWebHasRoleItem> getListUserWebHasRoleByIdUserweb(@Param("idUserweb") Long idUserweb);
    List<UserWebHasRoleItem> getListUserwebHasRoleBySearchForm(@Param("searchForm") UserWebHasRoleSearchForm searchForm);
    Long getTotalUserwebHasRoleItemBySearchForm(@Param("searchForm") UserWebHasRoleSearchForm searchForm);
    @Select("select count(*) "+
            "from userweb_has_role " +
            "where id_userweb = #{userwebId}")
    Integer countUserwebHasRoleByUserWebId(@Param("userwebId") Long userwebId);

    @Select("select count(*) "+
            "from userweb_has_role " +
            "where id_role = #{roleId}")
    Integer countUserwebHasRoleByRoleId(@Param("roleId") Long roleId);
}
