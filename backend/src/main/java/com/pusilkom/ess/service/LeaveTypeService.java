package com.pusilkom.ess.service;

import com.pusilkom.ess.model.LeaveRequestExample;
import com.pusilkom.ess.model.LeaveType;
import com.pusilkom.ess.model.LeaveTypeExample;
import com.pusilkom.ess.model.mapper.LeaveTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class LeaveTypeService {
    @Autowired
    LeaveTypeMapper leaveTypeMapper;

    public List<LeaveType> getListLeaveType(){
        LeaveTypeExample ex = new LeaveTypeExample();
        List<LeaveType> getAllLeaveType = leaveTypeMapper.selectByExample(ex);
        if(!getAllLeaveType.isEmpty()){
            return getAllLeaveType;
        } else {
            return null;
        }
    }

    public LeaveType getListLeaveTypeByCode(String codes){
        LeaveTypeExample ex = new LeaveTypeExample();
        ex.createCriteria().andLeaveTypeCodeEqualTo(codes);
        List<LeaveType> getAllLeaveType = leaveTypeMapper.selectByExample(ex);
        if(!getAllLeaveType.isEmpty()){
            return getAllLeaveType.get(0);
        } else {
            return null;
        }
    }

    public LeaveType getListLeaveTypeById(Long id){
        LeaveTypeExample ex = new LeaveTypeExample();
        ex.createCriteria().andIdEqualTo(id);
        List<LeaveType> getAllLeaveType = leaveTypeMapper.selectByExample(ex);
        if(!getAllLeaveType.isEmpty()){
            return getAllLeaveType.get(0);
        } else {
            return null;
        }
    }
}
