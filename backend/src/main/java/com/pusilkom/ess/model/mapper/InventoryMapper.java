package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.InventorySearchForm;
import com.pusilkom.ess.dto.table.InventoryItem;
import com.pusilkom.ess.model.Inventory;
import com.pusilkom.ess.model.InventoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InventoryMapper extends BaseMapper<Inventory, Integer, InventoryExample> {
    List<InventoryItem> getListInventoryItemBySearchForm(@Param("searchForm") InventorySearchForm form);
    Long getTotalInventoryItemBySearchForm(@Param("searchForm") InventorySearchForm form);
    InventoryItem getDetailInventoryItemByIdInventory(@Param("inventoryId") Integer inventoryId);
    InventoryItem getLastInventoryCode(@Param("inventoryCode") String inventoryCode);
    List<InventoryItem> getListInventoryItemAll();
}
