package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.MenuSearchForm;
import com.pusilkom.ess.dto.menu.MenuItem;
import com.pusilkom.ess.model.Menu;
import com.pusilkom.ess.model.MenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fahri on 1/7/17.
 */
public interface MenuMapper extends BaseMapper<Menu, Long, MenuExample> {

    List<MenuItem> getListMenuItemByIdUserweb(@Param("idUserweb") Long idUserweb);
    List<MenuItem> getListMenuItemByIdUserwebAndRoleId(@Param("idUserweb") Long idUserweb, @Param("idRole") Long idRole);

    List<MenuItem> getMenuItemBySearchForm(@Param("menuSearchForm") MenuSearchForm menuSearchForm);
    Long getTotalMenuItemBySearchForm(@Param("menuSearchForm") MenuSearchForm menuSearchForm);

    List<MenuItem> getMenuNotInIdMenuRoleHasMenu(@Param("idRole") Long idRole);
}
