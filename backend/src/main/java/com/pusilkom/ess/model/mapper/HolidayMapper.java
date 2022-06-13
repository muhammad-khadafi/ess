package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.LeaveApprovalSearchForm;
import com.pusilkom.ess.dto.form.search.LeaveRequestSearchForm;
import com.pusilkom.ess.dto.table.LeaveApprovalItem;
import com.pusilkom.ess.dto.table.LeaveRequestItem;
import com.pusilkom.ess.model.Holiday;
import com.pusilkom.ess.model.HolidayExample;
import com.pusilkom.ess.model.LeaveRequest;
import com.pusilkom.ess.model.LeaveRequestExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by muhammad.khadafi on 1/7/17.
 */
public interface HolidayMapper extends BaseMapper<Holiday, Long, HolidayExample> {

}
