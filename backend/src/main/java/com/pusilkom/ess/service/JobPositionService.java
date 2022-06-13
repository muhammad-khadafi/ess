package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.JobPositionCmd;
import com.pusilkom.ess.dto.form.search.JobPositionSearchForm;
import com.pusilkom.ess.dto.table.JobPositionItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.dto.view.JobPositionDetail;
import com.pusilkom.ess.model.JobPosition;
import com.pusilkom.ess.model.mapper.JobPositionMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ITF on 11/1/2018.
 */
@Service
@Transactional(readOnly = true)
public class JobPositionService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JobPositionMapper jobPositionMapper;

    public JobPosition getById(Integer id) {
        return jobPositionMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void saveCmd(JobPositionCmd jobPositionCmd) {

        boolean isNew = (jobPositionCmd.getId() == null);

        this.save(jobPositionCmd.toJobPosition());
    }

    @Transactional(readOnly = false)
    public void save(JobPosition jobPosition) {
        if (jobPosition.getId() == null) {
            jobPosition.setCreatedBy(CommonUtil.getUserId());
            jobPosition.setCreatedDate(CommonUtil.getCurrentDate());
            jobPositionMapper.insertSelective(jobPosition);
        } else {
            jobPosition.setUpdatedBy(CommonUtil.getUserId());
            jobPosition.setUpdatedDate(CommonUtil.getCurrentDate());
            jobPositionMapper.updateByPrimaryKey(jobPosition);
        }
    }

    public TablePagination<JobPositionItem> getTablePagination(JobPositionSearchForm searchForm) {
        List<JobPositionItem> listItem = jobPositionMapper.getListJobPositionItemBySearchForm(searchForm);
        Long totalItemFiltered = jobPositionMapper.getTotalJobPositionItemBySearchForm(searchForm);

        return new TablePagination<JobPositionItem>(searchForm, listItem, totalItemFiltered);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        jobPositionMapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public List<JobPositionDetail> getAllJobPosition() {
        return jobPositionMapper.selectAllJobPosition();
    }
}
