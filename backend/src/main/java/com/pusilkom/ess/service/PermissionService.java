package com.pusilkom.ess.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pusilkom.ess.model.*;
import com.pusilkom.ess.model.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by fahri on 3/8/17.
 */

@Service
@Transactional(readOnly = true)
public class PermissionService {

    @Autowired RoleHasMenuService roleHasMenuService;
    @Autowired RoleHasPermissionService roleHasPermissionService;
    @Autowired MenuHasPermissionService menuHasPermissionService;

    @Autowired PermissionMapper permissionMapper;

    public List<Permission> getListPermissionByIdRole(Long idRole) {
        List<Permission> listPermission = Lists.newArrayList();

        /*for (RoleHasMenu roleHasMenu : roleHasMenuService.getListRoleHasMenuByIdRole(idRole)) {
            for (MenuHasPermission menuHasPermission : menuHasPermissionService.getListMenuHasPermissionByIdMenu(roleHasMenu.getIdMenu())) {
                Permission permission = this.getPermissionById(menuHasPermission.getIdPermission());

                listPermission.add(permission);
            }
        }*/

        /*for (RoleHasPermission roleHasPermission : roleHasPermissionService.getListRoleHasPermissionByIdRole(idRole)) {
            Permission permission = this.getPermissionById(roleHasPermission.getIdPermission());

            listPermission.add(permission);
        }*/

        return listPermission;
    }

    public Permission getPermissionById(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }


    public Set<String> getSetKodePermissionByListIdRole(List<Long> listIdRole) {
        Set<String> setKodePermission = Sets.newHashSet();

        for (Long idRole : listIdRole) {
            for (Permission permission : this.getListPermissionByIdRole(idRole)) {
                setKodePermission.add(permission.getKode());
            }
        }

        return setKodePermission;
    }

}
