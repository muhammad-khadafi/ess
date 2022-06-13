package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.auth.UserInfo;
import com.pusilkom.ess.dto.form.search.UserSearchForm;
import com.pusilkom.ess.dto.table.UserItem;
import com.pusilkom.ess.model.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fahri on 1/7/17.
 */
public interface UserwebMapper extends BaseMapper<Userweb, Long, UserwebExample> {
    @Select("select employee.name as employeeName, userweb.id as id, username, password, role.id as roleId,role.kode, role.label, employee_id as employeeId " +
            "from userweb join employee ON userweb.employee_id = employee.id\n" +
            "join userweb_has_role on userweb.id = userweb_has_role.id_userweb\n" +
            "join role on userweb_has_role.id_role = role.id\n" +
            "where username = #{username}")
    UserInfo getUserInfoByUsername(@Param("username") String username);

    List<UserItem> getListUserItemBySearchForm(@Param("searchForm") UserSearchForm searchForm);
    Long getTotalSubUserItemBySearchForm(@Param("searchForm") UserSearchForm searchForm);

    @Select("select id, username, flag_aktif as flagAktif,employee_id as employeeId " +
            "from userweb " +
            "where id = #{idUserweb}")
    UserItem getUserItemById(@Param("idUserweb") Integer idUserweb);

    Long checkDuplicate(@Param("userweb") Userweb userweb);
}
