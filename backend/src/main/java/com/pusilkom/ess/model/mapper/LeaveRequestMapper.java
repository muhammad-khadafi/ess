package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.LeaveApprovalSearchForm;
import com.pusilkom.ess.dto.form.search.LeaveRequestSearchForm;
import com.pusilkom.ess.dto.form.search.MasterReferenceSearchForm;
import com.pusilkom.ess.dto.table.LeaveApprovalItem;
import com.pusilkom.ess.dto.table.LeaveRequestItem;
import com.pusilkom.ess.dto.table.MasterReferenceItem;
import com.pusilkom.ess.model.LeaveRequest;
import com.pusilkom.ess.model.LeaveRequestExample;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.model.MasterReferenceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by muhammad.khadafi on 1/7/17.
 */
public interface LeaveRequestMapper extends BaseMapper<LeaveRequest, Long, LeaveRequestExample> {

    List<LeaveApprovalItem> getApprovalList(@Param("searchForm") LeaveApprovalSearchForm searchForm);

    Long countApprovalList(@Param("searchForm") LeaveApprovalSearchForm leaveApprovalSearchForm);

    String getRoleByUserId(@Param("userId") Integer userId);

    List<LeaveRequestItem> getListLeaveRequestItemBySearchForm(@Param("searchForm") LeaveRequestSearchForm searchForm);
    Long getTotalLeaveRequestItemBySearchForm(@Param("searchForm") LeaveRequestSearchForm searchForm);

    List<LeaveRequestItem> getListLeaveRequestItemByEmployeeIdandCode(@Param("employeeId") Integer employeeId,
                                                                      @Param("years") Integer years,
                                                                      @Param("codes") String codes);
}
