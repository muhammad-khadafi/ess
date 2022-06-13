package com.pusilkom.ess.service;


import com.pusilkom.ess.dto.form.cmd.FundingSourceCmd;
import com.pusilkom.ess.dto.form.search.FundingSourceSearchForm;
import com.pusilkom.ess.dto.table.FundingSourceItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.FundingSource;
import com.pusilkom.ess.model.mapper.FundingSourceMapper;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FundingSourceService {
    @Autowired
    FundingSourceMapper fundingSourceMapper;

    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = false)
    public void saveCmd(FundingSourceCmd fundingSourceCmd) {
        boolean isNew = (fundingSourceCmd.getId() == null);
        if(isNew) {
            fundingSourceMapper.insertSelective(fundingSourceCmd.toFundingSource());
        } else {
            fundingSourceMapper.updateByPrimaryKey(fundingSourceCmd.toFundingSource());
        }
    }

    public TablePagination<FundingSourceItem> getTablePagination(FundingSourceSearchForm searchForm) {
        List<FundingSourceItem> listItem = fundingSourceMapper.getListFundingSourceItemBySearchForm(searchForm);
        Long totalItemFiltered = fundingSourceMapper.getTotalFundingSourceItemBySearchForm(searchForm);

        return new TablePagination<FundingSourceItem>(searchForm, listItem, totalItemFiltered);
    }

    public FundingSource getFundingSourceById(Integer id) {
        log.info("*** POST funding source by ID : {}", d.toString(fundingSourceMapper.selectByPrimaryKey(id)));
        return fundingSourceMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        fundingSourceMapper.deleteByPrimaryKey(id);
    }

    public Integer countPettyCashHasFundingSource(FundingSourceCmd fundingSourceCmd) {
        Integer totalRole = fundingSourceMapper.countUserwebHasRoleByUserWebId(fundingSourceCmd.getId());
        return totalRole;
    }
}

