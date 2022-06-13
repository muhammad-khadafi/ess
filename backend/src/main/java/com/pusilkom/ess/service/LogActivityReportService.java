package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.search.LogActivityReportSearchForm;
import com.pusilkom.ess.dto.table.LogActivityReportItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.LogActivityReport;
import com.pusilkom.ess.model.mapper.LogActivityReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogActivityReportService {
    @Autowired
    LogActivityReportMapper logActivityReportMapper;

    public void save(LogActivityReport logActivityReport){
        logActivityReportMapper.insert(logActivityReport);
    }

    public LogActivityReport getById(Integer id){
        return logActivityReportMapper.selectByPrimaryKey(id);
    }

    public TablePagination<LogActivityReportItem> getTablePagination(LogActivityReportSearchForm logActivityReportSearchForm){
        List<LogActivityReportItem> listItem = logActivityReportMapper.getListLogActivityReportItemBySearchForm(logActivityReportSearchForm);
//        System.out.println(listItem);
        Long totalItemFiletered = logActivityReportMapper.getTotalLogActivityReportItemBySearchForm(logActivityReportSearchForm);
//        System.out.println(""+ totalItemFiletered);

        return new TablePagination<LogActivityReportItem>(logActivityReportSearchForm, listItem, totalItemFiletered);
    }

    public void delete(Integer id){
        logActivityReportMapper.deleteByPrimaryKey(id);
    }
}
