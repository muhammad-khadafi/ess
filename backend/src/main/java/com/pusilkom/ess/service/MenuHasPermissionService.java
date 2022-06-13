package com.pusilkom.ess.service;

import com.pusilkom.ess.model.MenuHasPermission;
import com.pusilkom.ess.model.MenuHasPermissionExample;
import com.pusilkom.ess.model.mapper.MenuHasPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fahri on 3/8/17.
 */

@Service
@Transactional(readOnly = true)
public class MenuHasPermissionService {

    @Autowired MenuHasPermissionMapper menuHasPermissionMapper;

    public List<MenuHasPermission> getListMenuHasPermissionByIdMenu(Long idMenu) {
        MenuHasPermissionExample example = new MenuHasPermissionExample();
        example.createCriteria()
                .andIdMenuEqualTo(idMenu);

        return menuHasPermissionMapper.selectByExample(example);
    }

}
