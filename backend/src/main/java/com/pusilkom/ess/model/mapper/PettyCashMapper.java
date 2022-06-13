package com.pusilkom.ess.model.mapper;


import com.pusilkom.ess.dto.form.search.PettyCashSearchForm;
import com.pusilkom.ess.dto.table.PettyCashItem;
import com.pusilkom.ess.model.PettyCash;
import com.pusilkom.ess.model.PettyCashExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Created by arief on 10/31/18.
 */
public interface PettyCashMapper extends BaseMapper<PettyCash, Integer, PettyCashExample> {
    List<PettyCashItem> getListPettyCashBySearchForm(@Param("searchForm") PettyCashSearchForm searchForm);
    Long getTotalPettyCashItemBySearchForm(@Param("searchForm") PettyCashSearchForm searchForm);
    @Select("select id as id, counter as counter, req_date as reqDate, created_date as createdDate " +
            "from petty_cash " +
            "order by id desc limit 1")
    PettyCashItem getLastRowPettyCash();
    List<PettyCashItem> getListPettyCashByIdUser(@Param("idUser") Integer idUser);
}
