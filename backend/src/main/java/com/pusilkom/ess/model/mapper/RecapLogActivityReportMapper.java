package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.LogActivityReportSearchForm;
import com.pusilkom.ess.dto.form.search.RecapLogActivityReportSearchForm;
import com.pusilkom.ess.dto.table.LogActivityReportItem;
import com.pusilkom.ess.dto.table.RecapLogActivityReportItem;
import com.pusilkom.ess.model.LogActivityReport;
import com.pusilkom.ess.model.LogActivityReportExample;
import com.pusilkom.ess.model.RecapLogActivityReport;
import com.pusilkom.ess.model.RecapLogActivityReportExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface RecapLogActivityReportMapper extends BaseMapper<RecapLogActivityReport, Integer, RecapLogActivityReportExample> {
    List<RecapLogActivityReportItem> getListRecapLogActivitytemBySearchForm(@Param("searchForm") RecapLogActivityReportSearchForm searchForm);

    Long getTotalReportLogActivityReportItemBySearchForm(@Param("searchForm") RecapLogActivityReportSearchForm searchForm);
}
