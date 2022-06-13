package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.InvoiceReportSearchForm;
import com.pusilkom.ess.dto.table.InvoiceReportItem;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.model.InvoiceReport;
import com.pusilkom.ess.model.InvoiceReportExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface InvoiceReportMapper extends BaseMapper<InvoiceReport, Integer, InvoiceReportExample> {
    List<InvoiceReportItem> getListInvoiceReportItemBySearchForm(@Param("searchForm") InvoiceReportSearchForm searchForm);

    Long getTotalInvoiceReportItemBySearchForm(@Param("searchForm") InvoiceReportSearchForm searchForm);

    Long getTotalInvoice(@Param("invoiceReport") InvoiceReport invoiceReport, @Param("code") String code,
                         @Param("subProjectName") String subProjectName);
    List<LogActivityDetailItem> getSubProjectCodeAndName(@Param("invoiceReport") InvoiceReport invoiceReport);
}
