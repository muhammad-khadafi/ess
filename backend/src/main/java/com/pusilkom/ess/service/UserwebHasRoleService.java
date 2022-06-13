package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.UserCmd;
import com.pusilkom.ess.model.UserwebHasRole;
import com.pusilkom.ess.model.UserwebHasRoleExample;
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
public class UserwebHasRoleService {

    @Autowired UserwebHasRoleMapper userwebHasRoleMapper;

    public List<UserwebHasRole> getListUserwebHasRoleByIdUser(Long idUser) {
        UserwebHasRoleExample example = new UserwebHasRoleExample();
        example.createCriteria()
                .andIdUserwebEqualTo(idUser);

        return userwebHasRoleMapper.selectByExample(example);
    }

    public Integer countUserwebHasRoleByUserWebId(UserCmd userCmd) {
        Integer totalRole = userwebHasRoleMapper.countUserwebHasRoleByUserWebId(userCmd.getId());
        return totalRole;
    }
}
