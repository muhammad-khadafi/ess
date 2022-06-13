package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.InvoiceReportSearchForm;
import com.pusilkom.ess.dto.form.search.RecapInvoiceReportSearchForm;
import com.pusilkom.ess.dto.table.InvoiceReportItem;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.table.RecapInvoiceReportItem;
import com.pusilkom.ess.model.InvoiceReport;
import com.pusilkom.ess.model.InvoiceReportExample;
import com.pusilkom.ess.model.RecapInvoiceReport;
import com.pusilkom.ess.model.RecapInvoiceReportExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface RecapInvoiceReportMapper extends BaseMapper<RecapInvoiceReport, Integer, RecapInvoiceReportExample> {
    List<RecapInvoiceReportItem> getListRecapInvoicetemBySearchForm(@Param("searchForm") RecapInvoiceReportSearchForm searchForm);
    Long getTotalReportInvoiceReportItemBySearchForm(@Param("searchForm") RecapInvoiceReportSearchForm searchForm);
}
