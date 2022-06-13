package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.auth.UserInfo;
import com.pusilkom.ess.dto.form.cmd.UserChangePasswordCmd;
import com.pusilkom.ess.dto.form.cmd.UserCmd;
import com.pusilkom.ess.dto.form.search.UserSearchForm;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.table.UserItem;
import com.pusilkom.ess.model.Userweb;
import com.pusilkom.ess.model.UserwebExample;
import com.pusilkom.ess.model.UserwebHasRole;
import com.pusilkom.ess.model.mapper.UserwebHasRoleMapper;
import com.pusilkom.ess.model.mapper.UserwebMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fahri on 3/8/17.
 */

@Service
@Transactional(readOnly = true)
public class UserwebService {

    @Autowired
    UserwebMapper userwebMapper;
    @Autowired
    UserwebHasRoleMapper userwebHasRoleMapper;

    public Userweb getUserwebByUsername(String username) {
        UserwebExample example = new UserwebExample();
        example.createCriteria()
                .andUsernameEqualTo(username);

        List<Userweb> list = userwebMapper.selectByExample(example);

        return list.isEmpty() ? null : list.get(0);
    }

    public UserInfo getUserInfoByUsername(String username) {
        UserInfo userInfo = userwebMapper.getUserInfoByUsername(username);
        return userInfo;
    }

    public TablePagination<UserItem> getTablePaginationUser(UserSearchForm searchForm){
        // cek flag
        if(searchForm.getFlagAktif().equalsIgnoreCase("true")){
            searchForm.setFlagAktif("T");
        }
        if(searchForm.getFlagAktif().equalsIgnoreCase("false")){
            searchForm.setFlagAktif("F");
        }

        if(searchForm.getEmployeeName() != null){
            if(searchForm.getEmployeeName().equalsIgnoreCase("-")){
                searchForm.setEmployeeName(null);
            }
        }

        List<UserItem> listItem = userwebMapper.getListUserItemBySearchForm(searchForm);
        Long totalItemFiltered = userwebMapper.getTotalSubUserItemBySearchForm(searchForm);

        return new TablePagination<UserItem>(searchForm, listItem, totalItemFiltered);
    }

    public UserItem getUserItemById(Integer idUserweb){
        UserItem userItem = userwebMapper.getUserItemById(idUserweb);
        return userItem;
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        userwebMapper.deleteByPrimaryKey(id);
    }

    public Boolean checkDuplicate(UserCmd userCmd) {
        Long duplicate = userwebMapper.checkDuplicate(userCmd.toUserWeb());
        if(duplicate > 0){
            return true;
        } else {
            return false;
        }
    }

    @Transactional(readOnly = false)
    public void saveCmd(UserCmd userCmd) {
        //business logic di sini

        this.save(userCmd.toUserWeb());
    }

    @Transactional(readOnly = false)
    public void save(Userweb userweb) {
        if (userweb.getId() == null) {
            if(userweb.getFlagAktif().equalsIgnoreCase("true")){
                userweb.setFlagAktif("T");
            }
            if(userweb.getFlagAktif().equalsIgnoreCase("false")){
                userweb.setFlagAktif("F");
            }

            String newSalt = BCrypt.gensalt();
            userweb.setPassword(BCrypt.hashpw("Pusilkom123", newSalt));
            userweb.setSalt(newSalt);
            userwebMapper.insertSelective(userweb);
        } else {
            if(userweb.getFlagAktif().equalsIgnoreCase("true")){
                userweb.setFlagAktif("T");
            }
            if(userweb.getFlagAktif().equalsIgnoreCase("false")){
                userweb.setFlagAktif("F");
            }
            userwebMapper.updateByPrimaryKeySelective(userweb);
        }
    }
    
     public List<Userweb> getListAllUserWeb(){
        UserwebExample ex = new UserwebExample();
        List<Userweb> userwebList = userwebMapper.selectByExample(ex);

        if(!userwebList.isEmpty()){
            return userwebList;
        } else {
            return null;
        }

     }

    public boolean isPasswordValid(UserChangePasswordCmd cmd) {
        UserInfo info = this.getUserInfoByUsername(cmd.getUsername());
        return BCrypt.checkpw(cmd.getPassword(), info.getPassword());
    }

    @Transactional(readOnly = false)
    public void saveChangePassword(UserChangePasswordCmd userCmd) {
        //business logic di sini
        userwebMapper.updateByPrimaryKeySelective(userCmd.forSave());
    }

    @Transactional(readOnly = false)
    public void addUserAndUserHasRole(Integer employeeId, Long roleId, String username, String password){
        Userweb userweb = new Userweb();
        userweb.setEmployeeId(employeeId);
        userweb.setUsername(username);
        String newSalt = BCrypt.gensalt();
        userweb.setPassword(BCrypt.hashpw(password, newSalt));
        userweb.setSalt(newSalt);
        userweb.setFlagAktif("T");
        userwebMapper.insertSelective(userweb);

        Long idUserWeb = userweb.getId();
        System.out.println("ID USER WEBNYA : " + idUserWeb);
        UserwebHasRole userwebHasRole = new UserwebHasRole();
        userwebHasRole.setIdRole(roleId);
        userwebHasRole.setIdUserweb(idUserWeb);
        userwebHasRoleMapper.insert(userwebHasRole);
    }
}
