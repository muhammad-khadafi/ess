package com.pusilkom.ess.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.pusilkom.ess.dto.form.cmd.RoleCmd;
import com.pusilkom.ess.dto.form.search.RoleSearchForm;
import com.pusilkom.ess.dto.table.RoleItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.AppHasRole;
import com.pusilkom.ess.model.Role;
import com.pusilkom.ess.model.RoleExample;
import com.pusilkom.ess.model.UserwebHasRole;
import com.pusilkom.ess.model.mapper.RoleHasMenuMapper;
import com.pusilkom.ess.model.mapper.RoleMapper;
import com.pusilkom.ess.model.mapper.UserwebHasRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fahri on 3/8/17.
 */

@Service
@Transactional(readOnly = true)
public class RoleService {

    @Autowired AppHasRoleService appHasRoleService;
    @Autowired
    UserwebHasRoleService userwebHasRoleService;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleHasMenuMapper roleHasMenuMapper;
    @Autowired
    UserwebHasRoleMapper userwebHasRoleMapper;

    public List<Long> getListIdRoleByIdApp(Long idApp) {
        List<Long> listIdRole = Lists.transform(appHasRoleService.getListAppHasRoleByIdApp(idApp), new Function<AppHasRole,Long>() {
            public Long apply(AppHasRole i) { return i.getId(); }
        });

        return listIdRole;
    }

    public List<Long> getListIdRoleByIdUser(Long idUser) {
        List<Long> listIdRole = Lists.transform(userwebHasRoleService.getListUserwebHasRoleByIdUser(idUser), new Function<UserwebHasRole,Long>() {
            public Long apply(UserwebHasRole i) { return i.getIdRole(); }
        });

        return listIdRole;
    }

    public TablePagination<RoleItem> getTablePaginationRole(RoleSearchForm searchForm){

        List<RoleItem> listItem = roleMapper.getListRoleItemBySearchForm(searchForm);
        Long totalItemFiltered = roleMapper.getTotalRoleItemBySearchForm(searchForm);

        return new TablePagination<RoleItem>(searchForm, listItem, totalItemFiltered);
    }

    public Boolean checkDuplicateKode(RoleCmd roleCmd) {
        Long duplicate = roleMapper.checkDuplicateKode(roleCmd.toRole());
        if(duplicate > 0){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkDuplicateLabel(RoleCmd roleCmd) {
        Long duplicate = roleMapper.checkDuplicateLabel(roleCmd.toRole());
        if(duplicate > 0){
            return true;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = false)
    public void saveCmd(RoleCmd roleCmd) {
        //business logic di sini

        this.save(roleCmd.toRole());
    }

    @Transactional(readOnly = false)
    public void save(Role role) {
        if (role.getId() == null) {
            roleMapper.insertSelective(role);
        } else {
            roleMapper.updateByPrimaryKey(role);
        }
    }

    public RoleItem getRoleItemById(Integer idRole){
        RoleItem roleItem = roleMapper.getRoleItemById(idRole);

        return roleItem;
    }

    public List<Role> getListAllRole(){
        RoleExample ex = new RoleExample();
        List<Role> getAllRole = roleMapper.selectByExample(ex);
        if(!getAllRole.isEmpty()){
            return getAllRole;
        } else {
            return null;
        }
    }

    public List<RoleItem> getListRoleNotInUserwebHasRoleByIdUserweb(Long idUserwebHasRole){
        UserwebHasRole userweb = userwebHasRoleMapper.selectByPrimaryKey(idUserwebHasRole);
        return roleMapper.getListRoleNotInUserwebHasRoleByIdUserweb(userweb.getIdUserweb());
    }

    public Integer countUserwebHasRoleByRoleId(RoleCmd roleCmd) {
        Integer totalUserWebHasRole = userwebHasRoleMapper.countUserwebHasRoleByRoleId(roleCmd.getId());
        return totalUserWebHasRole;
    }

    public Integer countRoleHasMenuByRoleId(RoleCmd roleCmd) {
        Integer totalRoleHasMenu = roleHasMenuMapper.countRoleHasMenuByRoleId(roleCmd.getId());
        return totalRoleHasMenu;
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
    }
}
