package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.search.InvoiceReportSearchForm;
import com.pusilkom.ess.dto.table.InvoiceReportItem;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.InvoiceReport;
import com.pusilkom.ess.model.mapper.InvoiceReportMapper;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceReportService {
    @Autowired
    DebugUtil d;
    @Autowired
    InvoiceReportMapper invoiceReportMapper;

    Logger log = LoggerFactory.getLogger(this.getClass());

    public void save(InvoiceReport invoiceReport){
        List<LogActivityDetailItem> subProject =  invoiceReportMapper.getSubProjectCodeAndName(invoiceReport);
        long totalInvoice = 0;
        for (LogActivityDetailItem sub : subProject) {
            Long jml = invoiceReportMapper.getTotalInvoice(invoiceReport, sub.getCode(), sub.getSubProjectName());
            totalInvoice = totalInvoice + jml;
        }
        invoiceReport.setTotal(totalInvoice);
        invoiceReportMapper.insert(invoiceReport);
    }

    public InvoiceReport getById(Integer id){
        return invoiceReportMapper.selectByPrimaryKey(id);
    }

    public TablePagination<InvoiceReportItem> getTablePagination(InvoiceReportSearchForm searchForm){
        List<InvoiceReportItem> listItem = invoiceReportMapper.getListInvoiceReportItemBySearchForm(searchForm);
//        System.out.println(listItem);
        Long totalItemFiletered = invoiceReportMapper.getTotalInvoiceReportItemBySearchForm(searchForm);
//        System.out.println(""+ totalItemFiletered);

        return new TablePagination<InvoiceReportItem>(searchForm, listItem, totalItemFiletered);
    }

    public void delete(Integer id){
        invoiceReportMapper.deleteByPrimaryKey(id);
    }
}
