package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.MasterReferenceSearchForm;
import com.pusilkom.ess.dto.table.MasterReferenceItem;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.model.MasterReferenceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by muhammad.khadafi on 1/7/17.
 */
public interface MasterReferenceMapper extends BaseMapper<MasterReference, Integer, MasterReferenceExample> {

    List<MasterReferenceItem> getListMasterReferenceItemBySearchForm(@Param("searchForm") MasterReferenceSearchForm searchForm);

    Long getTotalMasterReferenceItemBySearchForm(@Param("searchForm") MasterReferenceSearchForm searchForm);
}
