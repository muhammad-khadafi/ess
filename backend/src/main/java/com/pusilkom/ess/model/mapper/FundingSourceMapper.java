package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.FundingSourceSearchForm;
import com.pusilkom.ess.dto.table.FundingSourceItem;
import com.pusilkom.ess.model.FundingSource;
import com.pusilkom.ess.model.FundingSourceExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by arief on 10/31/18.
 */
public interface FundingSourceMapper extends BaseMapper<FundingSource, Integer, FundingSourceExample> {
    List<FundingSourceItem> getListFundingSourceItemBySearchForm(@Param("searchForm") FundingSourceSearchForm searchForm);
    Long getTotalFundingSourceItemBySearchForm(@Param("searchForm") FundingSourceSearchForm searchForm);
    @Select("select count(*) "+
            "from petty_cash " +
            "where funding_source_id = #{fundingSourceId}")
    Integer countUserwebHasRoleByUserWebId(@Param("fundingSourceId") Integer fundingSourceId);
    @Select("select * "+
            "from funding_source " +
            "where is_active is true or is_active is null")
    List<FundingSource> getAllActiveFundingSource();
}
