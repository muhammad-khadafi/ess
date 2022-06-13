package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.diagram.OrganizationStructureDiagram;
import com.pusilkom.ess.dto.diagram.OrganizationStructureDiagramTree;
import com.pusilkom.ess.dto.form.cmd.OrganizationStructureCmd;
import com.pusilkom.ess.dto.form.search.OrganizationStructureSearchForm;
import com.pusilkom.ess.dto.table.OrganizationStructureItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.OrganizationStructureDetail;
import com.pusilkom.ess.model.OrganizationStructure;
import com.pusilkom.ess.model.mapper.OrganizationStructureMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ITF on 11/8/2018.
 */
@Service
@Transactional
public class OrganizationStructureService {

    @Autowired
    OrganizationStructureMapper organizationStructureMapper;
    private List<OrganizationStructureDiagram> diagrams;

    @Transactional(readOnly = false)
    public List<OrganizationStructureDetail> getAllOrganizationStructure() {
        return organizationStructureMapper.selectAllOrganizationStructure();
    }

    public void saveCmd(OrganizationStructureCmd organizationStructureCmd){
        this.save(organizationStructureCmd.toOrganizationStructure());
    }

    @Transactional(readOnly = false)
    public void save (OrganizationStructure organizationStructure){
        if(organizationStructure.getId() == null){
            organizationStructure.setCreatedBy(CommonUtil.getUserId());
            organizationStructure.setCreatedDate(CommonUtil.getCurrentDate());
            organizationStructureMapper.insertSelective(organizationStructure);
        } else {
            organizationStructure.setUpdatedBy(CommonUtil.getUserId());
            organizationStructure.setUpdatedDate(CommonUtil.getCurrentDate());
            organizationStructureMapper.updateByPrimaryKey(organizationStructure);
        }
    }

    public TablePagination<OrganizationStructureItem> getTablePagination(OrganizationStructureSearchForm searchForm){
        List<OrganizationStructureItem> listItem = organizationStructureMapper.getListOrganizationStructureItemBySearchForm(searchForm);
        Long totalItemFiltered = organizationStructureMapper.getTotalOrganizationStructureItemBySearchForm(searchForm);

        return new TablePagination<OrganizationStructureItem>(searchForm, listItem, totalItemFiltered);
    }

    public OrganizationStructureItem getById(Integer id){
        return organizationStructureMapper.getOrganizationStructureByIdProject(id);
    }

    public void delete(Integer id) {
        organizationStructureMapper.deleteByPrimaryKey(id);
    }

    public List<OrganizationStructureDiagramTree> getOrganizationStructureDiagram() {
        return getOrganizationStructureDiagramTree(organizationStructureMapper.getOrganizationStructureDiagram());
    }

    public List<OrganizationStructureDiagramTree> getOrganizationStructureDiagramTree(List<OrganizationStructureDiagram> diagrams) {
        this.diagrams = diagrams;
        System.out.println(this.diagrams.size());

        List<OrganizationStructureDiagramTree> tree = new ArrayList<OrganizationStructureDiagramTree>();
        for(int ii = 0; ii < this.diagrams.size();){
            if(this.diagrams.get(ii).getParentId() == null){
                OrganizationStructureDiagram entry = this.diagrams.get(ii);
                OrganizationStructureDiagramTree node = new OrganizationStructureDiagramTree();
                node.setId(entry.getId());
                node.setTitle(entry.getEmployeeName());
                node.setName(entry.getName());
                tree.add(node);
                this.diagrams.remove(entry);
            } else {
                ii++;
            }
        }
        for(OrganizationStructureDiagramTree node : tree){
            node = fillTree(node);
        }
        System.out.println(this.diagrams.size());
        return tree;

    }

    private OrganizationStructureDiagramTree fillTree(OrganizationStructureDiagramTree tree){
        if(diagrams.isEmpty()){
            return tree;
        }
        for(int ii = 0; ii < diagrams.size();){
            if(diagrams.get(ii).getParentId() == tree.getId()){
                OrganizationStructureDiagram entry = diagrams.get(ii);
                OrganizationStructureDiagramTree node = new OrganizationStructureDiagramTree();
                node.setId(entry.getId());
                node.setTitle(entry.getEmployeeName());
                node.setName(entry.getName());
                diagrams.remove(entry);
                OrganizationStructureDiagramTree child = fillTree(node);
                if(tree.getChildren() == null){
                    List<OrganizationStructureDiagramTree> children = new ArrayList<OrganizationStructureDiagramTree>();
                    children.add(child);
                    tree.setChildren(children);
                } else {
                    List<OrganizationStructureDiagramTree> children = tree.getChildren();
                    children.add(child);
                    tree.setChildren(children);
                }
            } else {
                ii++;
            }
        }

        return tree;
    }

}
