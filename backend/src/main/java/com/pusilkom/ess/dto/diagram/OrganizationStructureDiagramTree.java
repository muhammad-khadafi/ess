package com.pusilkom.ess.dto.diagram;

import java.util.List;

public class OrganizationStructureDiagramTree {
    private Integer id;
    private String name;
    private String title;
    private List<OrganizationStructureDiagramTree> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrganizationStructureDiagramTree> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationStructureDiagramTree> children) {
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
