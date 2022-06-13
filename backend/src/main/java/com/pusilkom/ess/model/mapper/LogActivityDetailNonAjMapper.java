package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.LogActivityDetailSearchForm;
import com.pusilkom.ess.dto.form.search.LogActivityViewSearchForm;
import com.pusilkom.ess.dto.form.search.TimeSheetSummarySearchForm;
import com.pusilkom.ess.dto.table.EmployeeItem;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.table.TimesheetReportItem;
import com.pusilkom.ess.model.LogActivityDetail;
import com.pusilkom.ess.model.LogActivityDetailExample;
import com.pusilkom.ess.model.LogActivityDetailNonAj;
import com.pusilkom.ess.model.LogActivityDetailNonAjExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by muhammad.khadafi on 1/7/17.
 */
public interface LogActivityDetailNonAjMapper extends BaseMapper<LogActivityDetailNonAj, Integer, LogActivityDetailNonAjExample> {

    List<LogActivityDetailItem> getAllLogActivityDetail(@Param("searchForm") LogActivityDetailSearchForm searchForm);

    Long getTotalLogActivityDetail(@Param("searchForm") LogActivityDetailSearchForm searchForm);

    List<LogActivityDetailItem> getAllLogActivityView(@Param("searchForm") LogActivityViewSearchForm searchForm);

    List<EmployeeItem> getAllAssignedEmployees(@Param("searchForm") LogActivityViewSearchForm searchForm);

    List<TimesheetReportItem> getTimesheetSummaryData(@Param("searchForm") TimeSheetSummarySearchForm searchForm);
}
