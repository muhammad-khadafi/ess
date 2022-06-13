package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.LevelSearchForm;
import com.pusilkom.ess.dto.table.LevelRateItem;
import com.pusilkom.ess.model.LevelRate;
import com.pusilkom.ess.model.LevelRateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by arief on 10/31/18.
 */
public interface LevelRateMapper extends BaseMapper<LevelRate, Integer, LevelRateExample> {
    List<LevelRateItem> getListLevelRateBySearchForm(@Param("searchForm") LevelSearchForm searchForm);
    Long getTotalLevelRateItemBySearchForm(@Param("searchForm") LevelSearchForm searchForm);
    LevelRateItem getLevelRateById(@Param("levelRateId") Integer levelRateId);
}
