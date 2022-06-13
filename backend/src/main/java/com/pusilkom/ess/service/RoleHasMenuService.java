package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.search.RoleHasMenuSearchForm;
import com.pusilkom.ess.dto.table.RoleHasMenuItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.RoleHasMenu;
import com.pusilkom.ess.model.RoleHasMenuExample;
import com.pusilkom.ess.model.mapper.RoleHasMenuMapper;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fahri on 3/8/17.
 */

@Service
@Transactional(readOnly = true)
public class RoleHasMenuService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired RoleHasMenuMapper roleHasMenuMapper;

    public List<RoleHasMenu> getListRoleHasMenuByIdRole(Long idRole) {
        RoleHasMenuExample example = new RoleHasMenuExample();
        example.createCriteria()
                .andIdRoleEqualTo(idRole);

        return roleHasMenuMapper.selectByExample(example);
    }

    @Transactional(readOnly = false)
    public void save(RoleHasMenu[] roleHasMenus) {
        for (RoleHasMenu roleMenu : roleHasMenus){
            roleHasMenuMapper.insert(roleMenu);
        }
    }

    public TablePagination<RoleHasMenuItem> getTablePagination(RoleHasMenuSearchForm searchForm) {
        List<RoleHasMenuItem> listItem = roleHasMenuMapper.getListRoleHasMenuBySearchForm(searchForm);
        List<RoleHasMenuItem> listItem2 = new ArrayList<RoleHasMenuItem>();

        for(int i=0; i<listItem.size(); i++){
            List<RoleHasMenuItem> getDetail = roleHasMenuMapper.getListRoleHasMenuByIdRole(listItem.get(i).getIdRole());
            RoleHasMenuItem objectToAdd = getDetail.get(0);
            for(int k=1; k<getDetail.size(); k++){
                objectToAdd.setMenuString(objectToAdd.getMenuString() +" | "+ getDetail.get(k).getMenuString());
            }
            listItem2.add(objectToAdd);
        }

        Long totalItemFiltered = roleHasMenuMapper.getTotalRoleHasMenuBySearchForm(searchForm);
        return new TablePagination<RoleHasMenuItem>(searchForm, listItem2, totalItemFiltered);
    }

    public List<RoleHasMenuItem> getListRoleHasMenuByIdRoleUpdate(Long id){
        RoleHasMenu item = roleHasMenuMapper.selectByPrimaryKey(id);
        List<RoleHasMenuItem> listItem = roleHasMenuMapper.getListRoleHasMenuByIdRole(item.getIdRole());

        if(!listItem.isEmpty()){
            return listItem;
        } else {
            return null;
        }
    }

    @Transactional(readOnly = false)
    public void delete(Long id){
        RoleHasMenu item = roleHasMenuMapper.selectByPrimaryKey(id);
        RoleHasMenuExample ex = new RoleHasMenuExample();
        ex.createCriteria().andIdRoleEqualTo(item.getIdRole());
        roleHasMenuMapper.deleteByExample(ex);

    }

}
