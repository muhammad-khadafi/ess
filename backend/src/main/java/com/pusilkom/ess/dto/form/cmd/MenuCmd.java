package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.Menu;

public class MenuCmd extends Menu {


    public Menu toMenu() {
        Menu menu = new Menu();
        menu.setId(this.getId());
        menu.setIdParent(this.getIdParent());
        menu.setIsDisplayed(this.getIsDisplayed());
        menu.setKode(this.getKode());
        menu.setLabel(this.getLabel());
        menu.setMenuOrder(this.getMenuOrder());
        return menu;
    }
}