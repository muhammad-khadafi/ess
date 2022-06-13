package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.search.LeaveApprovalSearchForm;
import com.pusilkom.ess.dto.table.LeaveApprovalItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.LeaveRequest;
import com.pusilkom.ess.model.LeaveRequestExample;
import com.pusilkom.ess.model.LeaveType;
import com.pusilkom.ess.model.LeaveTypeExample;
import com.pusilkom.ess.model.mapper.LeaveRequestMapper;
import com.pusilkom.ess.model.mapper.LeaveTypeMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by muhammad.khadafi on 1/7/17.
 */


@Service
@Transactional
public class LeaveApprovalService {


    @Autowired
    LeaveRequestMapper leaveRequestMapper;

    @Autowired
    LeaveTypeMapper leaveTypeMapper;




    public TablePagination<LeaveApprovalItem> getTableData(LeaveApprovalSearchForm leaveApprovalSearchForm) {
        dateFormatting(leaveApprovalSearchForm);

        List<LeaveApprovalItem> listItem = leaveRequestMapper.getApprovalList(leaveApprovalSearchForm)
                .stream().map(obj -> {
            LeaveApprovalItem leaveApprovalItem = new LeaveApprovalItem(obj);
            //formatting date
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            leaveApprovalItem.setStartDateStr(dateFormat.format(leaveApprovalItem.getStartDate()));
            leaveApprovalItem.setEndDateStr(dateFormat.format(leaveApprovalItem.getEndDate()));

            //reformat leave date detail
            leaveApprovalItem.setLeaveDateDetail(
                    leaveApprovalItem.getLeaveDateDetail() != null ? leaveApprovalItem.getLeaveDateDetail().replace('|',',') : "-"
            );

            return leaveApprovalItem;
        }).collect(Collectors.toList());

        Long totalItemFiltered = leaveRequestMapper.countApprovalList(leaveApprovalSearchForm);

        return new TablePagination<LeaveApprovalItem>(leaveApprovalSearchForm, listItem, totalItemFiltered);
    }

    private void dateFormatting(LeaveApprovalSearchForm leaveApprovalSearchForm) {
        if (leaveApprovalSearchForm.getStartDateStr() != null){
            leaveApprovalSearchForm.setStartDateStr(leaveApprovalSearchForm.getStartDateStr().substring(0,10));
            try {
                leaveApprovalSearchForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(leaveApprovalSearchForm.getStartDateStr()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (leaveApprovalSearchForm.getEndDateStr() != null) {
            leaveApprovalSearchForm.setEndDateStr(leaveApprovalSearchForm.getEndDateStr().substring(0,10));
            try {
                leaveApprovalSearchForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(leaveApprovalSearchForm.getEndDateStr()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public List<LeaveType> getLeaveType() {
        List<LeaveType> leaveTypeList = leaveTypeMapper.selectByExample(new LeaveTypeExample());
        return leaveTypeList;
    }

    public void updateStatus(Long id, String operation, String role) {
        String status = "";

        if (operation.equalsIgnoreCase("REJECT")){
            if (role.equalsIgnoreCase("MANAGER")) {
                status = "Rejected by Manager";
            } else if (role.equalsIgnoreCase("DEPUTY DIRECTOR")){
                status = "Rejected by Deputy Director";
            }
        }
        else if (operation.equalsIgnoreCase("APPROVE")){
            if (role.equalsIgnoreCase("MANAGER")) {
                status = "Waiting Deputy Director Approval";
            } else if (role.equalsIgnoreCase("DEPUTY DIRECTOR")){
                status = "Approved";
            }
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(id);
        leaveRequest.setStatus(status);
        leaveRequestMapper.updateByPrimaryKeySelective(leaveRequest);

    }
}

