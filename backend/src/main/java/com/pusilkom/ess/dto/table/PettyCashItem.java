package com.pusilkom.ess.dto.table;

import com.pusilkom.ess.model.PettyCash;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by ITF on 11/2/2018.
 */
public class PettyCashItem extends PettyCash {
    private String approvalDateString;
    private String reqDateString;
    private String paidToName;
    private String fundingSourceName;
    private String roleName;
    private String needApprovalStr;
    private String reqAmountStr;
    private String statusStr;

    public String getApprovalDateString() {
        return approvalDateString;
    }

    public void setApprovalDateString(String approvalDateString) {
        this.approvalDateString = approvalDateString;
    }

    public String getReqDateString() {
        return reqDateString;
    }

    public void setReqDateString(String reqDateString) {
        this.reqDateString = reqDateString;
    }

    public String getPaidToName() {
        return paidToName;
    }

    public void setPaidToName(String paidToName) {
        this.paidToName = paidToName;
    }

    public String getFundingSourceName() {
        return fundingSourceName;
    }

    public void setFundingSourceName(String fundingSourceName) {
        this.fundingSourceName = fundingSourceName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNeedApprovalStr() {
        return needApprovalStr;
    }

    public void setNeedApprovalStr(String needApprovalStr) {
        this.needApprovalStr = needApprovalStr;
    }

    public String getReqAmountStr() {
        return reqAmountStr;
    }

    public void setReqAmountStr(String reqAmountStr) {
        this.reqAmountStr = reqAmountStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public PettyCashItem toPettyCashItem(PettyCash pettyCash){
        this.setId(pettyCash.getId());
        this.setReqNumber(pettyCash.getReqNumber());
        this.setReqDate(pettyCash.getReqDate());
        this.setPaidTo(pettyCash.getPaidTo());
        this.setReqAmount(pettyCash.getReqAmount());
        this.setAmountInWord(pettyCash.getAmountInWord());
        this.setCategory(pettyCash.getCategory());
        this.setDescription(pettyCash.getDescription());
        this.setFundingSourceId(pettyCash.getFundingSourceId());
        this.setNeedApproval(pettyCash.getNeedApproval());
        this.setIsApproved(pettyCash.getIsApproved());
        this.setReasonOfRejection(pettyCash.getReasonOfRejection());
        this.setApprovalDate(pettyCash.getApprovalDate());
        this.setCreatedBy(pettyCash.getCreatedBy());
        this.setIsDone(pettyCash.getIsDone());
        this.setApprovedBy(pettyCash.getApprovedBy());
        this.setPaidBy(pettyCash.getPaidBy());
        this.setPaidDate(pettyCash.getPaidDate());
        this.setReasonOfCancellation(pettyCash.getReasonOfCancellation());
        this.setCreatedDate(pettyCash.getCreatedDate());

        return this;
    }

    public void convDateToString () {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        if(this.getReqDate()!=null){
            this.setReqDateString(df.format(this.getReqDate()));
        }
        if(this.getApprovalDate()!=null){
            this.setApprovalDateString(df.format(this.getApprovalDate()));
        }
    }
}
