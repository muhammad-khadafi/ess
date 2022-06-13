package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.table.SubProAssignmentItem;
import com.pusilkom.ess.model.mapper.SubProAssignmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubProAssignmentService {
    @Autowired
    SubProAssignmentMapper subProAssignmentMapper;

    public List<SubProAssignmentItem> getByEmployeeId(Integer id){
        /*SubProAssignmentExample ex = new SubProAssignmentExample();
        ex.createCriteria().andEmployeeIdEqualTo(id);*/

        List<SubProAssignmentItem> list =  subProAssignmentMapper.getListSubProAssignItemByEmployeeId(id);

        if(!list.isEmpty()){
            return list;
        } else {
            return null;
        }
    }

}
