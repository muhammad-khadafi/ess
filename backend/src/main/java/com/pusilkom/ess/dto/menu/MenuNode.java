package com.pusilkom.ess.dto.menu;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by fahri on 3/26/17.
 */
public class MenuNode extends MenuItem {

    boolean isSelected;
    boolean isExpanded;
    List<MenuNode> children = Lists.newArrayList();

    public MenuNode() {
    }

    public MenuNode(MenuItem menuItem) {
        this.setId(menuItem.getId());
        this.setIdParent(menuItem.getIdParent());
        this.setKode(menuItem.getKode());
        this.setLabel(menuItem.getLabel());
        this.setLevel(menuItem.getLevel());
        this.setMenuOrder(menuItem.getMenuOrder());
        this.setIsDisplayed(menuItem.getIsDisplayed());
    }

    public void addChild(MenuNode menuNode) {
        children.add(menuNode);
    }


    public static MenuNode createRootNode() {
        MenuNode root = new MenuNode();
        root.setId(-1L);
        root.setKode("ROOT");
        root.setLabel("");
        root.setLevel(-1L);
        root.setIsDisplayed(true);
        root.setIsExpanded(true);

        return root;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }

    public void setIsExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public boolean isIsExpanded() {
        return isExpanded;
    }

    public List<MenuNode> getChildren() {
        return children;
    }

}
