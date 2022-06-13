package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.SubProjectCmd;
import com.pusilkom.ess.dto.form.search.SubProjectSearchForm;
import com.pusilkom.ess.dto.table.SubProjectItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.SubProject;
import com.pusilkom.ess.model.SubProjectExample;
import com.pusilkom.ess.model.mapper.SubProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SubProjectService {
    @Autowired
    SubProjectMapper subProjectMapper;

    public List<SubProjectItem> getSubProjectByIdEmployee(Integer idEmployee){
        List<SubProjectItem> list = subProjectMapper.getListSubProjectItemBySearchForm(idEmployee);

        if(!list.isEmpty()){
            return list;
        } else {
            return null;
        }
    }

    public List<SubProjectItem> getListSubProjectItemByIdProject(Integer idProject){
        List<SubProjectItem> list = subProjectMapper.getListSubProjectItemByIdProject(idProject);

        if(!list.isEmpty()){
            return list;
        } else {
            return null;
        }
    }

    public TablePagination<SubProjectItem> getTablePagination(SubProjectSearchForm searchForm){
        List<SubProjectItem> listItem = subProjectMapper.getListSubProjectItemBySearchForm2(searchForm);
        Long totalItemFiltered = subProjectMapper.getTotalSubProjectItemBySearchForm2(searchForm);

        return new TablePagination<SubProjectItem>(searchForm, listItem, totalItemFiltered);
    }

    @Transactional(readOnly = false)
    public void saveCmd(SubProjectCmd subProjectCmd) {
        //business logic di sini

        this.save(subProjectCmd.toSubProject());
    }

    @Transactional(readOnly = false)
    public void save(SubProject subProject) {
        if (subProject.getId() == null) {
            subProjectMapper.insertSelective(subProject);
        } else {
            subProjectMapper.updateByPrimaryKey(subProject);
        }
    }

    public SubProjectItem getSubProjectItemById(Integer idProject){
        SubProjectItem subProjectItem = subProjectMapper.getSubProjectItemById(idProject);

        return subProjectItem;
    }

    public void delete(Integer id) {
        subProjectMapper.deleteByPrimaryKey(id);
    }

    public Boolean checkDuplicate(SubProjectCmd subProjectCmd) {
       Long duplicate = subProjectMapper.checkDuplicate(subProjectCmd.toSubProject());
       System.out.println("cek duplikat " + duplicate);
       if(duplicate > 0){
           return true;
       } else {
           return false;
       }
    }

    public List<SubProject> getAllSubProject() {
        return subProjectMapper.selectByExample(new SubProjectExample());
    }


    public List<SubProjectItem> getListSubProjectNonAj() {
        return subProjectMapper.getListSubProjectNonAj();
    }
}
