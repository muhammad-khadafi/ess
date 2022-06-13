package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.LogActivityDetailCmd;
import com.pusilkom.ess.dto.form.search.LogActivityDetailSearchForm;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.LogActivityDetail;
import com.pusilkom.ess.model.LogActivityDetailExample;
import com.pusilkom.ess.model.mapper.LogActivityDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LogActivityDetailService {
    @Autowired
    LogActivityDetailMapper logActivityDetailMapper;

    public void saveCmd(LogActivityDetailCmd logActivityDetailCmd){
        this.save(logActivityDetailCmd.toLogActivityDetail());
    }

    @Transactional(readOnly = false)
    public void save(LogActivityDetail logActivityDetail){
        if(logActivityDetail.getId() == null){
            logActivityDetailMapper.insert(logActivityDetail);
        } else {
            logActivityDetailMapper.updateByPrimaryKey(logActivityDetail);
        }
    }

    public LogActivityDetailItem getById(Integer id) {
        return logActivityDetailMapper.getLogActivityDetailAndSubProNameAndEmployeeNameByIdLogActDetail(id);
    }

    public TablePagination<LogActivityDetailItem>  getAllLogActivityDetail(LogActivityDetailSearchForm searchForm){
        List<LogActivityDetailItem> listItem = logActivityDetailMapper.getAllLogActivityDetail(searchForm);
        Long totalItem = logActivityDetailMapper.getTotalLogActivityDetail(searchForm);

        return new TablePagination<LogActivityDetailItem>(searchForm, listItem, totalItem);
    }

    public void delete(Integer id) {
        logActivityDetailMapper.deleteByPrimaryKey(id);
    }

    public Boolean validateAbsent(Integer id, Integer projectAssignId, Integer subProId, Date absentDate){
        System.out.println("====masuk disini!!!====");
        LogActivityDetailExample ex = new LogActivityDetailExample();
        if(id == null){
            ex.createCriteria().andProjectAssignIdEqualTo(projectAssignId).andSubProIdEqualTo(subProId).andDateEqualTo(absentDate);
        } else {
            ex.createCriteria().andIdNotEqualTo(id).andProjectAssignIdEqualTo(projectAssignId).andSubProIdEqualTo(subProId).andDateEqualTo(absentDate);
        }


        List<LogActivityDetail> list = logActivityDetailMapper.selectByExample(ex);

        if(!list.isEmpty()){
            System.out.println("====validatenyaaa false====");
            return false;
        } else {
            System.out.println("====validatenyaaa true====");
            return true;
        }
    }
}
