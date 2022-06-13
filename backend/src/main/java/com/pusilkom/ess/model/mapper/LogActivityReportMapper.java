package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.LogActivityReportSearchForm;
import com.pusilkom.ess.dto.table.LogActivityReportItem;
import com.pusilkom.ess.model.LogActivityReport;
import com.pusilkom.ess.model.LogActivityReportExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface LogActivityReportMapper extends BaseMapper<LogActivityReport, Integer, LogActivityReportExample> {
    List<LogActivityReportItem> getListLogActivityReportItemBySearchForm(@Param("searchForm") LogActivityReportSearchForm searchForm);

    Long getTotalLogActivityReportItemBySearchForm(@Param("searchForm") LogActivityReportSearchForm searchForm);
}
