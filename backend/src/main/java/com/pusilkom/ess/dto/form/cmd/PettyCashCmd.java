package com.pusilkom.ess.dto.form.cmd;

import com.pusilkom.ess.model.PettyCash;

/**
 * Created by arief on 12/06/19.
 */
public class PettyCashCmd extends PettyCash {
    public PettyCash toPettyCash(){
        PettyCash pettyCash = new PettyCash();
        pettyCash.setId(this.getId());
        pettyCash.setReqNumber(this.getReqNumber());
        pettyCash.setReqDate(this.getReqDate());
        pettyCash.setPaidTo(this.getPaidTo());
        pettyCash.setReqAmount(this.getReqAmount());
        pettyCash.setAmountInWord(this.getAmountInWord());
        pettyCash.setCategory(this.getCategory());
        pettyCash.setDescription(this.getDescription());
        pettyCash.setFundingSourceId(this.getFundingSourceId());
        pettyCash.setNeedApproval(this.getNeedApproval());
        pettyCash.setIsApproved(this.getIsApproved());
        pettyCash.setReasonOfRejection(this.getReasonOfRejection());
        pettyCash.setApprovalDate(this.getApprovalDate());
        pettyCash.setCounter(this.getCounter());
        pettyCash.setUploadPath(this.getUploadPath());
        pettyCash.setCreatedBy(this.getCreatedBy());
        pettyCash.setIsDone(this.getIsDone());
        pettyCash.setApprovedBy(this.getApprovedBy());
        pettyCash.setPaidBy(this.getPaidBy());
        pettyCash.setPaidDate(this.getPaidDate());
        pettyCash.setCreatedDate(this.getCreatedDate());

        return pettyCash;
    }
}
