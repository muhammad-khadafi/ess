package com.pusilkom.ess.dto.form.search;

import java.io.Serializable;

/**
 * Created by fahri on 1/16/17.
 */
public class ColumnDef implements Serializable {

    private String name;
    private SortDirection sortDirection;

    public ColumnDef(String name, SortDirection sortDirection) {
        this.name = name;
        this.sortDirection = sortDirection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }
}
