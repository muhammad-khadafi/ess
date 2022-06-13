package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.search.UserWebHasRoleSearchForm;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.table.UserWebHasRoleItem;
import com.pusilkom.ess.model.UserwebHasRole;
import com.pusilkom.ess.model.UserwebHasRoleExample;
import com.pusilkom.ess.model.mapper.UserwebHasRoleMapper;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserHasRoleService {
    @Autowired
    DebugUtil d;
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserwebHasRoleMapper userwebHasRoleMapper;

    @Transactional(readOnly = false)
    public void save(UserwebHasRole[] userwebHasRoles) {
        for (UserwebHasRole userRole : userwebHasRoles){
            userwebHasRoleMapper.insert(userRole);
        }
    }

    public TablePagination<UserWebHasRoleItem> getTablePagination(UserWebHasRoleSearchForm searchForm) {
        List<UserWebHasRoleItem> listItem = userwebHasRoleMapper.getListUserwebHasRoleBySearchForm(searchForm);
        List<UserWebHasRoleItem> listItem2 = new ArrayList<UserWebHasRoleItem>();

        for(int i=0; i<listItem.size(); i++){
            List<UserWebHasRoleItem> getDetail = userwebHasRoleMapper.getListUserWebHasRoleByIdUserweb(listItem.get(i).getIdUserweb());
            UserWebHasRoleItem objectToAdd = getDetail.get(0);
            for(int j=1; j<getDetail.size(); j++){
                objectToAdd.setRoleString(objectToAdd.getRoleString() +" | "+ getDetail.get(j).getRoleString());
            }
            listItem2.add(objectToAdd);
        }

        Long totalItemFiltered = userwebHasRoleMapper.getTotalUserwebHasRoleItemBySearchForm(searchForm);
        return new TablePagination<UserWebHasRoleItem>(searchForm, listItem2, totalItemFiltered);
    }

    public UserwebHasRole getUserWebHasRoleById (Long id){
        return userwebHasRoleMapper.selectByPrimaryKey(id);
    }

    public List<UserWebHasRoleItem> getListUserWebHasRoleByIdUserweb(Long id){
        UserwebHasRole item = this.getUserWebHasRoleById(id);
        List<UserWebHasRoleItem> listItem = userwebHasRoleMapper.getListUserWebHasRoleByIdUserweb(item.getIdUserweb());

        if(!listItem.isEmpty()){
            return listItem;
        } else {
            return null;
        }
    }

    @Transactional(readOnly = false)
    public void delete(Long id){
        UserwebHasRole item = this.getUserWebHasRoleById(id);
        UserwebHasRoleExample ex = new UserwebHasRoleExample();
        ex.createCriteria().andIdUserwebEqualTo(item.getIdUserweb());
        userwebHasRoleMapper.deleteByExample(ex);
    }
}
