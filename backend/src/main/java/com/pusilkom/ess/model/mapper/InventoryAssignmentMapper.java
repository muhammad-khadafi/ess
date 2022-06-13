package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.InventoryAssignmentSearchForm;
import com.pusilkom.ess.dto.table.InventoryAssignmentItem;
import com.pusilkom.ess.model.InventoryAssignment;
import com.pusilkom.ess.model.InventoryAssignmentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InventoryAssignmentMapper extends BaseMapper<InventoryAssignment, Integer, InventoryAssignmentExample> {
    List<InventoryAssignmentItem> getListInventoryAssignmentItemBySearchForm(@Param("searchForm") InventoryAssignmentSearchForm form);
    Long getTotalInventoryAssignmentItemBySearchForm(@Param("searchForm") InventoryAssignmentSearchForm form);
    InventoryAssignmentItem getInventoryAssignmentItemById(@Param("id") Integer id);
    List<InventoryAssignmentItem> getInventoryAssignmentItemByIdInventory(@Param("id") Integer id);
}
