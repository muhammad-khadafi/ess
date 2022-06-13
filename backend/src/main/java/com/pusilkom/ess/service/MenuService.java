package com.pusilkom.ess.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pusilkom.ess.dto.form.cmd.MenuCmd;
import com.pusilkom.ess.dto.form.search.MenuSearchForm;
import com.pusilkom.ess.dto.menu.MenuItem;
import com.pusilkom.ess.dto.menu.MenuNode;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.*;
import com.pusilkom.ess.model.mapper.MenuMapper;
import com.pusilkom.ess.model.mapper.RoleHasMenuMapper;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;

import java.util.*;

/**
 * Created by fahri on 3/8/17.
 */

@Service
@Transactional(readOnly = true)
public class MenuService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired DebugUtil d;
    @Autowired RoleHasMenuService roleHasMenuService;
    @Autowired MenuMapper menuMapper;
    @Autowired
    RoleHasMenuMapper roleHasMenuMapper;

    public List<Menu> getListMenuByIdRole(Long idRole) {
        List<Menu> listMenu = Lists.newArrayList();

        for (RoleHasMenu roleHasMenu : roleHasMenuService.getListRoleHasMenuByIdRole(idRole)) {
            Menu menu = this.getMenuById(roleHasMenu.getIdMenu());

            listMenu.add(menu);
        }

        return listMenu;
    }

    public Menu getMenuById(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public Set<String> getSetKodeMenuByListIdRole(List<Long> listIdRole) {
        Set<String> setKodePermission = Sets.newHashSet();

        for (Long idRole : listIdRole) {
            for (Menu menu : this.getListMenuByIdRole(idRole)) {
//                System.out.println("menunya : " + menu.getKode());
                setKodePermission.add(menu.getKode());
            }
        }

        return setKodePermission;
    }

    public MenuNode constructMenuNodeByIdUserweb(Long idUserweb) {
        List<MenuItem> listMenuItem = menuMapper.getListMenuItemByIdUserweb(idUserweb);

        MenuNode rootNode = MenuNode.createRootNode();

        Map<Long, MenuNode> mapMenuItem = new HashMap<Long, MenuNode>();

        for (MenuItem menuItem : listMenuItem) {

            Long idMenu = menuItem.getId();

            MenuNode menuNode = mapMenuItem.get(idMenu);
            if (menuNode == null) {
                menuNode = new MenuNode(menuItem);
                mapMenuItem.put(idMenu, menuNode);
            }

            Long idParent = menuItem.getIdParent();
            if (idParent != null) {
                MenuNode parentNode = mapMenuItem.get(idParent);
                parentNode.addChild(menuNode);

                mapMenuItem.put(idParent, parentNode);
            }

            if (menuItem.getLevel() == 1L) {
                rootNode.addChild(menuNode);
            }
        }

        return rootNode;
    }

    public List<Menu> getAllMenu() {
        MenuExample ex = new MenuExample();
        List<Menu> listMenu = menuMapper.selectByExample(ex);
        if(!listMenu.isEmpty()){
            return listMenu;
        } else {
            return null;
        }
    }

    public TablePagination<MenuItem> getTablePagination(MenuSearchForm menuSearchForm) {
        List<MenuItem> listItem = menuMapper.getMenuItemBySearchForm(menuSearchForm);
        Long totalItemFiltered = menuMapper.getTotalMenuItemBySearchForm(menuSearchForm);

        return new TablePagination<MenuItem>(menuSearchForm, listItem, totalItemFiltered);
    }

    public Boolean isCodeDuplicate(MenuCmd menuCmd) {
        List<Menu> menus = getAllMenu();
        if(menuCmd.getId() != null){
            Menu toremove = null;
            for(Menu menu : menus){
                if(menu.getId() == menuCmd.getId()){
                    toremove = menu;
                }
            }
            if(toremove != null){
                menus.remove(toremove);
            }
        }
        for(Menu menu : menus) {
            if (menu.getKode().equals(menuCmd.getKode())) {
                return false;
            }
        }
        return true;
    }

    public Boolean isNameDuplicate(MenuCmd menuCmd) {
        List<Menu> menus = getAllMenu();
        if(menuCmd.getId() != null){
            Menu toremove = null;
            for(Menu menu : menus){
                if(menu.getId() == menuCmd.getId()){
                    toremove = menu;
                }
            }
            if(toremove != null){
                menus.remove(toremove);
            }
        }
        for(Menu menu : menus) {
            if (menu.getLabel().equals(menuCmd.getLabel())) {
                return false;
            }
        }
        return true;
    }

    public Boolean isParentValid(MenuCmd menuCmd) {
        if(menuCmd.getIdParent() == null){
            return true;
        }

        Menu menu = menuMapper.selectByPrimaryKey(menuCmd.getIdParent());
        if (menuCmd.getId() != null && (menu.getIdParent() == (menuCmd.getId()))) {
            return false;
        }

        return true;
    }

    public Boolean canBeDeleted(Long id) {
        MenuExample ex = new MenuExample();
        ex.createCriteria().andIdParentEqualTo(id);
        List<Menu> menus = menuMapper.selectByExample(ex);
        return menus.isEmpty();
    }

    public List<FieldError> fieldErrorsCheck(MenuCmd menuCmd){
        List<FieldError> fieldErrors = new ArrayList<FieldError>();

        if(isNameDuplicate(menuCmd)){
            FieldError fieldError = new FieldError("menuCmd", "label", "Nama Menu sudah ada");
            fieldErrors.add(fieldError);
        }

        if(isCodeDuplicate(menuCmd)){
            FieldError fieldError = new FieldError("menuCmd", "kode", "Kode Menu sudah ada");
            fieldErrors.add(fieldError);
        }

        if(isParentValid(menuCmd)){
            FieldError fieldError = new FieldError("menuCmd", "id_parent", "Menu Parent tidak valid");
            fieldErrors.add(fieldError);
        }

        return fieldErrors;
    }

    @Transactional(readOnly = false)
    public void saveMenu(MenuCmd menuCmd) {
        if (menuCmd.getId() == null) {
            menuMapper.insertSelective(menuCmd.toMenu());
        } else {
            menuMapper.updateByPrimaryKey(menuCmd.toMenu());
        }
    }

    @Transactional(readOnly = false)
    public List<Menu> getAllPossibleParents(Long id) {
        MenuExample ex = new MenuExample();
        ex.createCriteria().andIdNotEqualTo(id.longValue());
        List<Menu> menus = menuMapper.selectByExample(ex);

        if(id == null){
            return menus;
        }

        List<Menu> possibleParents = new ArrayList<Menu>();
        for(Menu menuEx : menus){
            if(!isParent(id, menuEx)){
                possibleParents.add(menuEx);
            }
        }

        return possibleParents;
    }

    private Boolean isParent(Long id, Menu menu){
        if(menu.getIdParent() == null) {
            return false;
        }
        if(menu.getIdParent().longValue() == id.longValue()){
            return true;
        } else {
            return isParent(id, menuMapper.selectByPrimaryKey(menu.getIdParent()));
        }
    }

    public Menu getById(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    public List<MenuItem> getMenuNotInIdMenuRoleHasMenu(Long idRoleHasMenu){
        RoleHasMenu item = roleHasMenuMapper.selectByPrimaryKey(idRoleHasMenu);
        return menuMapper.getMenuNotInIdMenuRoleHasMenu(item.getIdRole());
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    public MenuNode constructRootMenuNodeByIdUserwebAndRoleId(Long idUserweb, Long idRole) {
        List<MenuItem> listMenuItem = menuMapper.getListMenuItemByIdUserwebAndRoleId(idUserweb, idRole);
        return this.constructRootMenuNodeByListMenuItem(listMenuItem);
    }

    public MenuNode constructRootMenuNodeByListMenuItem(List<MenuItem> listMenuItem) {
        MenuNode rootNode = MenuNode.createRootNode();

        for (MenuNode menuNode : this.constructChildrenMenuNodeByListMenuItem(listMenuItem)) {
            rootNode.addChild(menuNode);
        }

        return rootNode;
    }

    public List<MenuNode> constructChildrenMenuNodeByListMenuItem(List<MenuItem> listMenuItem) {
        List<MenuNode> listMenuNode = Lists.newArrayList();
        Map<Long, MenuNode> mapMenuItem = new HashMap<Long, MenuNode>();

        for (MenuItem menuItem : listMenuItem) {

            Long idMenu = menuItem.getId();

            MenuNode menuNode = mapMenuItem.get(idMenu);
            if (menuNode == null) {
                menuNode = new MenuNode(menuItem);
                mapMenuItem.put(idMenu, menuNode);
            }

            Long idParent = menuItem.getIdParent();
            if (idParent != null) {
                MenuNode parentNode = mapMenuItem.get(idParent);
                                     parentNode.addChild(menuNode);

                mapMenuItem.put(idParent, parentNode);
            }

            if (menuItem.getLevel() == 1L) {
                listMenuNode.add(menuNode);
            }
        }

        return listMenuNode;
    }
}
