package com.pusilkom.ess.service;

import com.pusilkom.ess.model.AppHasRole;
import com.pusilkom.ess.model.AppHasRoleExample;
import com.pusilkom.ess.model.mapper.AppHasRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fahri on 3/8/17.
 */

@Service
@Transactional(readOnly = true)
public class AppHasRoleService {

    @Autowired AppHasRoleMapper appHasRoleMapper;

    public List<AppHasRole> getListAppHasRoleByIdApp(Long idApp) {
        AppHasRoleExample example = new AppHasRoleExample();
        example.createCriteria()
                .andIdAppEqualTo(idApp);

        return appHasRoleMapper.selectByExample(example);
    }

}
