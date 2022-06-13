package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.LevelRateCmd;
import com.pusilkom.ess.dto.form.search.LevelSearchForm;
import com.pusilkom.ess.dto.table.LevelRateItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.*;
import com.pusilkom.ess.model.mapper.LevelRateMapper;
import com.pusilkom.ess.model.mapper.MasterReferenceMapper;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by arief on 10/31/18.
 */


@Service
@Transactional(readOnly = true)
public class LevelService {
    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LevelRateMapper levelMapper;

    @Autowired
    MasterReferenceMapper masterReferenceMapper;

    public List<MasterReference> getAllLevelForDropDown() {
        MasterReferenceExample ex = new MasterReferenceExample();
        ex.createCriteria().andReferenceTypeEqualTo("Level");
        List<MasterReference> listLevelRate = masterReferenceMapper.selectByExample(ex);
        return listLevelRate;
    }

    public TablePagination<LevelRateItem> getTablePagination(LevelSearchForm searchForm) {
        List<LevelRateItem> listItem = levelMapper.getListLevelRateBySearchForm(searchForm);
        Long totalItemFiltered = levelMapper.getTotalLevelRateItemBySearchForm(searchForm);
        return new TablePagination<LevelRateItem>(searchForm, listItem, totalItemFiltered);
    }

    @Transactional(readOnly = false)
    public void saveCmd(LevelRateCmd levelRate) throws ParseException {
        boolean isNew = (levelRate.getId() == null);
        if(isNew) {
            levelRate.setCreatedBy(CommonUtil.getUserId());
            levelRate.setCreatedDate(CommonUtil.getCurrentDate());
            LevelRate pa = levelRate.toLevelRate();
            levelMapper.insertSelective(pa);
        } else {
            levelRate.setUpdatedBy(CommonUtil.getUserId());
            levelRate.setUpdatedDate(CommonUtil.getCurrentDate());
            levelMapper.updateByPrimaryKey(levelRate.toLevelRate());
        }
    }

    public LevelRateItem getLevelRateById(Integer id) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        LevelRateItem levelRateItem = levelMapper.getLevelRateById(id);

        if(levelRateItem.getStartDate() != null){
            levelRateItem.setStartDateString(df.format(levelRateItem.getStartDate()));
        }
        if(levelRateItem.getEndDate() != null){
            levelRateItem.setEndDateString(df.format(levelRateItem.getEndDate()));
        }

        return levelRateItem;
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        levelMapper.deleteByPrimaryKey(id);
    }
}
