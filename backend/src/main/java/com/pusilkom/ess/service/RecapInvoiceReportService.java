package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.search.InvoiceReportSearchForm;
import com.pusilkom.ess.dto.form.search.RecapInvoiceReportSearchForm;
import com.pusilkom.ess.dto.table.InvoiceReportItem;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.table.RecapInvoiceReportItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.InvoiceReport;
import com.pusilkom.ess.model.RecapInvoiceReport;
import com.pusilkom.ess.model.mapper.InvoiceReportMapper;
import com.pusilkom.ess.model.mapper.RecapInvoiceReportMapper;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecapInvoiceReportService {
    @Autowired
    DebugUtil d;
    @Autowired
    RecapInvoiceReportMapper recapInvoiceReportMapper;

    Logger log = LoggerFactory.getLogger(this.getClass());

    public void save(RecapInvoiceReport recapInvoiceReport){
        recapInvoiceReportMapper.insert(recapInvoiceReport);
    }

    public RecapInvoiceReport getById(Integer id){
        return recapInvoiceReportMapper.selectByPrimaryKey(id);
    }

    public TablePagination<RecapInvoiceReportItem> getTablePagination(RecapInvoiceReportSearchForm searchForm){
        List<RecapInvoiceReportItem> listItem = recapInvoiceReportMapper.getListRecapInvoicetemBySearchForm(searchForm);
//        System.out.println(listItem);
        Long totalItemFiletered = recapInvoiceReportMapper.getTotalReportInvoiceReportItemBySearchForm(searchForm);
//        System.out.println(""+ totalItemFiletered);

        return new TablePagination<RecapInvoiceReportItem>(searchForm, listItem, totalItemFiletered);
    }

    public void delete(Integer id){
        recapInvoiceReportMapper.deleteByPrimaryKey(id);
    }
}
