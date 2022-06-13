package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.search.LogActivityReportSearchForm;
import com.pusilkom.ess.dto.form.search.RecapLogActivityReportSearchForm;
import com.pusilkom.ess.dto.table.LogActivityReportItem;
import com.pusilkom.ess.dto.table.RecapLogActivityReportItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.LogActivityReport;
import com.pusilkom.ess.model.RecapLogActivityReport;
import com.pusilkom.ess.model.mapper.LogActivityReportMapper;
import com.pusilkom.ess.model.mapper.RecapLogActivityReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecapLogActivityReportService {
    @Autowired
    RecapLogActivityReportMapper recapLogActivityReportMapper;

    public void save(RecapLogActivityReport recapLogActivityReport){
        recapLogActivityReportMapper.insert(recapLogActivityReport);
    }

    public RecapLogActivityReport getById(Integer id){
        return recapLogActivityReportMapper.selectByPrimaryKey(id);
    }

    public TablePagination<RecapLogActivityReportItem> getTablePagination(RecapLogActivityReportSearchForm recapLogActivityReportSearchForm){
        List<RecapLogActivityReportItem> listItem = recapLogActivityReportMapper.getListRecapLogActivitytemBySearchForm(recapLogActivityReportSearchForm);
//        System.out.println(listItem);
        Long totalItemFiletered = recapLogActivityReportMapper.getTotalReportLogActivityReportItemBySearchForm(recapLogActivityReportSearchForm);
//        System.out.println(""+ totalItemFiletered);

        return new TablePagination<RecapLogActivityReportItem>(recapLogActivityReportSearchForm, listItem, totalItemFiletered);
    }

    public void delete(Integer id){
        recapLogActivityReportMapper.deleteByPrimaryKey(id);
    }
}
