package com.pusilkom.ess.service;

import com.pusilkom.ess.model.RoleHasPermission;
import com.pusilkom.ess.model.RoleHasPermissionExample;
import com.pusilkom.ess.model.mapper.RoleHasPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fahri on 3/8/17.
 */

@Service
@Transactional(readOnly = true)
public class RoleHasPermissionService {

    @Autowired RoleHasPermissionMapper roleHasPermissionMapper;

    public List<RoleHasPermission> getListRoleHasPermissionByIdRole(Long idRole) {
        RoleHasPermissionExample example = new RoleHasPermissionExample();
        example.createCriteria()
                .andIdRoleEqualTo(idRole);

        return roleHasPermissionMapper.selectByExample(example);
    }

}
