package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.auth.UserInfo;
import com.pusilkom.ess.dto.form.cmd.PettyCashCmd;
import com.pusilkom.ess.dto.form.search.PettyCashSearchForm;
import com.pusilkom.ess.dto.table.PettyCashItem;
import com.pusilkom.ess.dto.table.RoleItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.Employee;
import com.pusilkom.ess.model.FundingSource;
import com.pusilkom.ess.model.PettyCash;
import com.pusilkom.ess.model.mapper.EmployeeMapper;
import com.pusilkom.ess.model.mapper.FundingSourceMapper;
import com.pusilkom.ess.model.mapper.PettyCashMapper;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arief on 10/31/18.
 */


@Service
@Transactional(readOnly = true)
public class PettyCashService {
    @Autowired
    PettyCashMapper pettyCashMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    FundingSourceMapper fundingSourceMapper;
    @Autowired
    UserwebService userwebService;
    @Autowired
    RoleService roleService;
    @Autowired
    ReportService reportService;
    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = false)
    public Integer saveCmd(PettyCashCmd pettyCashCmd) {

        Integer pettyCashId = null;
        boolean isNew = (pettyCashCmd.getId() == null);
        log.info("*** POST PETTYCASH ID : {}", d.toString(pettyCashCmd));
        if (isNew) {
            PettyCash lastRow = this.getLastRowPettyCash();
            log.info("*** LASTROW : {}", d.toString(lastRow));
            Integer counter = 1;
            if (lastRow == null) {

            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int currentYear = calendar.get(Calendar.YEAR);
                calendar.setTime(lastRow.getCreatedDate());
                int lastRowYear = calendar.get(Calendar.YEAR);
                if (currentYear - lastRowYear <= 0) {
                    counter = lastRow.getCounter() + 1;
                }
            }

            pettyCashCmd.setCounter(counter);
            pettyCashCmd.setReqNumber(this.generateReqNumber(counter));
            pettyCashCmd.setCreatedDate(new Date());
            PettyCash pettyCash = pettyCashCmd.toPettyCash();
            if((pettyCash.getCategory().equalsIgnoreCase("Transport") && pettyCash.getReqAmount() >= 100000)
                    || pettyCash.getCategory().equalsIgnoreCase("Non Transport") && pettyCash.getReqAmount() >= 500000) {
                pettyCash.setNeedApproval(true);
            } else {
                pettyCash.setNeedApproval(false);
            }
            pettyCashMapper.insertSelective(pettyCash);
            pettyCashId = pettyCash.getId();
        } else {
            PettyCash oldPetty = pettyCashMapper.selectByPrimaryKey(pettyCashCmd.getId());
            if(pettyCashCmd.getIsApproved() != null) {
                oldPetty.setIsApproved(pettyCashCmd.getIsApproved());
                if(pettyCashCmd.getApprovedBy() == null) {
                    oldPetty.setApprovedBy(CommonUtil.getUserId());
                } else {
                    oldPetty.setApprovedBy(pettyCashCmd.getApprovedBy());
                }
                oldPetty.setApprovalDate(new Date());
                if(oldPetty.getIsApproved() == false){
                    oldPetty.setReasonOfRejection(pettyCashCmd.getReasonOfRejection());
                }
            }
            pettyCashMapper.updateByPrimaryKey(oldPetty);
        }
        return pettyCashId;
    }

    public TablePagination<PettyCashItem> getTablePagination(PettyCashSearchForm searchForm) {
        RoleItem roleItem = roleService.getRoleItemById(searchForm.getIdRole());
        searchForm.setRoleName(roleItem.getKode());
        searchForm.setIsVueTable(true);
        log.info("*** POST searchForm : {}", d.toString(searchForm));
        List<PettyCashItem> listItem = pettyCashMapper.getListPettyCashBySearchForm(searchForm);
        for(PettyCashItem item : listItem){
            if(item.getIsDone() != null && item.getIsDone() == true) {
                item.setStatusStr("Paid");
            } else if (item.getNeedApproval() == true) {
                if(item.getIsApproved() == null){
                    item.setStatusStr("Waiting for Approval");
                }  else if(item.getIsApproved() == false){
                    item.setStatusStr("Rejected");
                } else if(item.getReasonOfCancellation() != null){
                    item.setStatusStr("Cancelled");
                } else {
                    item.setStatusStr("In Process");
                }
            } else {
                if(item.getReasonOfCancellation() != null){
                    item.setStatusStr("Cancelled");
                } else {
                    item.setStatusStr("In Process");
                }
            }
            item.setReqAmountStr(this.thousandSeparator(item.getReqAmount()));
            item.convDateToString();
            // item.setRoleName(this.getRoleName());
            if(item.getNeedApproval()==null){
                item.setNeedApprovalStr("-");
            } else {
                if(item.getNeedApproval()==true){
                    item.setNeedApprovalStr("Yes");
                } else {
                    item.setNeedApprovalStr("No");
                }
            }
        }
        Long totalItemFiltered = pettyCashMapper.getTotalPettyCashItemBySearchForm(searchForm);
        return new TablePagination<PettyCashItem>(searchForm, listItem, totalItemFiltered);
    }

    public String getRoleName(){
        UserInfo userInfo = userwebService.getUserInfoByUsername(CommonUtil.getUserName());
        RoleItem roleItem = roleService.getRoleItemById(userInfo.getRoleId().intValue());
        return roleItem.getKode();
    }

    public PettyCashItem getPettyCashItemById(Integer id) {
        PettyCash pettyTemp = pettyCashMapper.selectByPrimaryKey(id);
        Employee emp = employeeMapper.selectByPrimaryKey(pettyTemp.getPaidTo());
        FundingSource fundingSource = fundingSourceMapper.selectByPrimaryKey(pettyTemp.getFundingSourceId());
        PettyCashItem petty = new PettyCashItem();
        petty.setPaidToName(emp.getName());
        petty.setFundingSourceName(fundingSource.getName());
        petty.toPettyCashItem(pettyTemp);
        petty.convDateToString();
        // petty.setRoleName(this.getRoleName());
        return petty;
    }

    public List<FundingSource> getAllFundingSource() {
        List<FundingSource> listFundingSource = fundingSourceMapper.getAllActiveFundingSource();
        return listFundingSource;
    }

    public PettyCash getPettyCashById(Integer id) {
        PettyCash petty = pettyCashMapper.selectByPrimaryKey(id);
        return petty;
    }

    @Transactional(readOnly = false)
    public void update(PettyCash pettyCash) {
        pettyCashMapper.updateByPrimaryKey(pettyCash);
    }


    public PettyCashItem getLastRowPettyCash(){
        PettyCashItem lastRow = pettyCashMapper.getLastRowPettyCash();
        if(lastRow != null) {
            lastRow.convDateToString();
        }
        return lastRow;
    }

    public String generateReqNumber(Integer counter){
        String s = "0000";
        String x = "" + counter;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        DateTime datetime = new DateTime(new Date());
        int month = Integer.parseInt(datetime.toString("MM"));
        int year = Integer.parseInt(datetime.toString("yyyy"));

        return s.substring(0, (s.length() - (x.length())) + 1) + x + "/" + month + "/" + year;
    }

    public String thousandSeparator(int num){
        String str = String.format("%,d", num).replace(',', '.');
        return str;
    }

    public List<PettyCashItem> getListPettyCashUnapproved() {
        PettyCashSearchForm searchForm = new PettyCashSearchForm();
        searchForm.setPettyCashStatus("Unapproved");
        log.info("*** POST searchForm : {}", d.toString(searchForm));
        List<PettyCashItem> listItem = pettyCashMapper.getListPettyCashBySearchForm(searchForm);
        for(PettyCashItem item : listItem){
            if(item.getFundingSourceId() != null){
                FundingSource fundingSource = fundingSourceMapper.selectByPrimaryKey(item.getFundingSourceId());
                item.setFundingSourceName(fundingSource.getName());
            }
            if(item.getIsDone() != null && item.getIsDone() == true) {
                item.setStatusStr("Paid");
            } else if (item.getNeedApproval() == true) {
                if(item.getIsApproved() == null){
                    item.setStatusStr("Waiting for Approval");
                } else if(item.getIsApproved() == false){
                    item.setStatusStr("Rejected");
                } else if(item.getReasonOfCancellation() != null){
                    item.setStatusStr("Cancelled");
                } else {
                    item.setStatusStr("In Process");
                }
            } else {
                if(item.getReasonOfCancellation() != null){
                    item.setStatusStr("Cancelled");
                } else {
                    item.setStatusStr("In Process");
                }
            }
            item.setReqAmountStr(this.thousandSeparator(item.getReqAmount()));
            item.convDateToString();
            // item.setRoleName(this.getRoleName());
            if(item.getNeedApproval()==null){
                item.setNeedApprovalStr("-");
            } else {
                if(item.getNeedApproval()==true){
                    item.setNeedApprovalStr("Yes");
                } else {
                    item.setNeedApprovalStr("No");
                }
            }
        }
        return listItem;
    }

    public List<PettyCashItem> getListPettyCashByIdUser(Integer idUser) {
        List<PettyCashItem> listItem = pettyCashMapper.getListPettyCashByIdUser(idUser);
        for(PettyCashItem item : listItem){
            if(item.getIsDone() != null && item.getIsDone() == true) {
                item.setStatusStr("Paid");
            } else if (item.getNeedApproval() == true) {
                if(item.getIsApproved() == null){
                    item.setStatusStr("Waiting for Approval");
                }  else if(item.getIsApproved() == false){
                    item.setStatusStr("Rejected");
                } else if(item.getReasonOfCancellation() != null){
                    item.setStatusStr("Cancelled");
                } else {
                    item.setStatusStr("In Process");
                }
            } else {
                if(item.getReasonOfCancellation() != null){
                    item.setStatusStr("Cancelled");
                } else {
                    item.setStatusStr("In Process");
                }
            }
            item.setReqAmountStr(this.thousandSeparator(item.getReqAmount()));
            item.convDateToString();
            // item.setRoleName(this.getRoleName());
            if(item.getNeedApproval()==null){
                item.setNeedApprovalStr("-");
            } else {
                if(item.getNeedApproval()==true){
                    item.setNeedApprovalStr("Yes");
                } else {
                    item.setNeedApprovalStr("No");
                }
            }
        }
        return listItem;
    }
}
