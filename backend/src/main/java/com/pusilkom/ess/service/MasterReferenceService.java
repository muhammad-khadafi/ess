package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.MasterReferenceCmd;
import com.pusilkom.ess.dto.form.search.MasterReferenceSearchForm;
import com.pusilkom.ess.dto.table.MasterReferenceItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.model.MasterReferenceExample;
import com.pusilkom.ess.model.mapper.MasterReferenceMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by muhammad.khadafi on 1/7/17.
 */


@Service
@Transactional
public class MasterReferenceService {

    @Autowired
    MasterReferenceMapper masterReferenceMapper;

    public void saveCmd(MasterReferenceCmd masterReferenceCmd) throws Exception {
//        throw new Exception();
        MasterReference masterReference = toMasterReference(masterReferenceCmd);
        masterReference.setCreatedDate(CommonUtil.getCurrentDate());
        masterReference.setCreatedBy(CommonUtil.getUserId());
        masterReferenceMapper.insert(masterReference);
    }

    private MasterReference toMasterReference(MasterReferenceCmd masterReferenceCmd) {
        MasterReference masterReference = new MasterReference();
        masterReference.setName(masterReferenceCmd.getName());
        masterReference.setCategory(masterReferenceCmd.getCategory());
        masterReference.setReferenceType(masterReferenceCmd.getReferenceType());
        return masterReference;
    }

    public TablePagination<MasterReferenceItem> getTablePagination(MasterReferenceSearchForm searchForm) {
        List<MasterReferenceItem> listItem = masterReferenceMapper.getListMasterReferenceItemBySearchForm(searchForm);
        Long totalItemFiltered = masterReferenceMapper.getTotalMasterReferenceItemBySearchForm(searchForm);

        return new TablePagination<MasterReferenceItem>(searchForm, listItem, totalItemFiltered);
    }

    public MasterReference getById(Integer id) {

        return masterReferenceMapper.selectByPrimaryKey(id);

    }

    public void delete(Integer id) {
        masterReferenceMapper.deleteByPrimaryKey(id);
    }

    public void updateCmd(MasterReferenceCmd masterReferenceCmd, Integer id) {
        MasterReference masterReference = toMasterReference(masterReferenceCmd);
        masterReference.setId(id);
        masterReference.setUpdatedBy(CommonUtil.getUserId());
        masterReference.setCreatedDate(CommonUtil.getCurrentDate());
        masterReferenceMapper.updateByPrimaryKeySelective(masterReference);
    }

    public List<MasterReference> getMasterReferenceByReferenceType(String refType){
        MasterReferenceExample ex = new MasterReferenceExample();
        if(refType.equalsIgnoreCase("supervisor")){
            ex.createCriteria().andReferenceTypeEqualTo(refType);
            ex.setOrderByClause("name asc");
        } else {
            ex.createCriteria().andReferenceTypeEqualTo(refType);
            ex.setOrderByClause("name asc");
        }
        List<MasterReference> listMasterRef = masterReferenceMapper.selectByExample(ex);

        if(!listMasterRef.isEmpty()){
            return listMasterRef;
        } else {
            return null;
        }
    }
}

