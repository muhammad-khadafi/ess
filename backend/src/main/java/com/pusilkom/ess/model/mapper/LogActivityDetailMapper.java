package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.LogActivityDetailSearchForm;
import com.pusilkom.ess.dto.form.search.TimeSheetSummarySearchForm;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.table.TimesheetReportItem;
import com.pusilkom.ess.model.LogActivityDetail;
import com.pusilkom.ess.model.LogActivityDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface LogActivityDetailMapper extends BaseMapper<LogActivityDetail, Integer, LogActivityDetailExample> {

    List<LogActivityDetailItem> getAllLogActivityDetail(@Param("searchForm") LogActivityDetailSearchForm searchForm);

    LogActivityDetailItem getLogActivityDetailAndSubProNameAndEmployeeNameByIdLogActDetail(@Param("id") Integer id);

    Long getTotalLogActivityDetail(@Param("searchForm") LogActivityDetailSearchForm searchForm);

    List<TimesheetReportItem> getTimesheetSummaryData(@Param("searchForm") TimeSheetSummarySearchForm searchForm);
}
