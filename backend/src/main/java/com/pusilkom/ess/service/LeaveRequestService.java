package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.LeaveRequestCmd;
import com.pusilkom.ess.dto.form.search.LeaveRequestSearchForm;
import com.pusilkom.ess.dto.form.search.RoleSearchForm;
import com.pusilkom.ess.dto.table.LeaveRequestItem;
import com.pusilkom.ess.dto.table.RoleItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.LeaveRequest;
import com.pusilkom.ess.model.LeaveRequestExample;
import com.pusilkom.ess.model.LeaveType;
import com.pusilkom.ess.model.mapper.LeaveRequestMapper;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LeaveRequestService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LeaveRequestMapper leaveRequestMapper;
    @Autowired
    LeaveTypeService leaveTypeService;
    @Autowired
    HolidayService holidayService;
    @Autowired
    DebugUtil d;

    public TablePagination<LeaveRequestItem> getTablePaginationRole(LeaveRequestSearchForm searchForm){

        List<LeaveRequestItem> listItem = leaveRequestMapper.getListLeaveRequestItemBySearchForm(searchForm);
        Long totalItemFiltered = leaveRequestMapper.getTotalLeaveRequestItemBySearchForm(searchForm);

        return new TablePagination<LeaveRequestItem>(searchForm, listItem, totalItemFiltered);
    }

    public LeaveRequest getNumberOfDays(String startDateString, String endDateString, Integer employeeId, Integer years, Integer leaveTypeId) throws ParseException {
        LeaveRequest leaveRequest = new LeaveRequest();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = myFormat.parse(startDateString);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        Integer numberOfDays = 0;
        LeaveType lt = leaveTypeService.getListLeaveTypeById(new Long(leaveTypeId));

        if (lt.getLeaveTypeCode().equalsIgnoreCase("ANNUAL_LEAVE")) {
            Date date2 = myFormat.parse(endDateString);
            cal2.setTime(date2);
            while (cal1.before(cal2)) {
                if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
                        && (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))
                        && holidayService.isHolidayByDate(cal1.getTime())) {
                    numberOfDays++;
                }
                cal1.add(Calendar.DATE, 1);
            }

            Integer totalLeave = this.getTotalLeaveRequestByIdEmployeeAndYear(employeeId, years, "ANNUAL_LEAVE") + numberOfDays;

            LeaveType leaveType = leaveTypeService.getListLeaveTypeByCode(lt.getLeaveTypeCode());
            System.out.println("Cek kondisi : " + totalLeave + " || " +leaveType.getAmount());
            if (totalLeave > leaveType.getAmount()) {
                numberOfDays = -1;
            }
        } else {
            numberOfDays = lt.getAmount() - 1;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            SimpleDateFormat s;
            s=new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("check this out " + s.format(date1));
            System.out.println("check this out jumlah libur " + lt.getAmount());

            for(int i=0 ; i < lt.getAmount() ;)
            {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                //here even sat and sun are added
                //but at the end it goes to the correct week day.
                //because i is only increased if it is week day
                if(calendar.get(Calendar.DAY_OF_WEEK)<=5 && holidayService.isHolidayByDate(calendar.getTime()))
                {
                    i++;
                }

            }
            leaveRequest.setEndDate(calendar.getTime());
            System.out.println("check this out " + s.format(leaveRequest.getEndDate()));
        }

        leaveRequest.setNumOfDays(numberOfDays);

        return leaveRequest;
    }

    @Transactional(readOnly = false)
    public void saveCmd(LeaveRequestCmd leaveRequestCmd) {
        leaveRequestCmd.setDeputyDirectorId(new Long(124));
        this.save(leaveRequestCmd.toLeaveRequest());
    }

    @Transactional(readOnly = false)
    public void save(LeaveRequest leaveRequest) {
        if (leaveRequest.getId() == null){
            leaveRequest.setCreatedBy(CommonUtil.getUserId());
            leaveRequest.setCreatedDate(CommonUtil.getCurrentDate());
            leaveRequest.setStatus("Waiting Manager Approval");
            leaveRequestMapper.insertSelective(leaveRequest);
        } else {
            leaveRequest.setLastUpdatedBy(CommonUtil.getUserId());
            leaveRequest.setLastUpdatedDate(CommonUtil.getCurrentDate());
            leaveRequestMapper.updateByPrimaryKey(leaveRequest);
        }
    }

    public Integer getTotalLeaveRequestByIdEmployeeAndYear(Integer employeeId, Integer years, String codes) {
        List<LeaveRequestItem> listItem = leaveRequestMapper.getListLeaveRequestItemByEmployeeIdandCode(employeeId, years, codes);
        log.info("Query : {}", d.toString(listItem));
        Integer totalLeaveByYear = 0;
        for (int i = 0; i < listItem.size(); i++){
            totalLeaveByYear += listItem.get(i).getNumOfDays();
        }

        return totalLeaveByYear;
    }

    public LeaveRequest getById (Long id) {
        return leaveRequestMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void deleteLeave (LeaveRequestCmd leaveRequestCmd) {
        LeaveRequest leaveRequest = leaveRequestCmd.toLeaveRequest();
        leaveRequest.setIsCancel(true);
        leaveRequestMapper.updateByPrimaryKey(leaveRequest);
    }
}
