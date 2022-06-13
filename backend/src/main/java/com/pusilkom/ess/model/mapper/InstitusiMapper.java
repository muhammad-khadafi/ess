package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.InstitusiSearchForm;
import com.pusilkom.ess.dto.table.InstitusiItem;
import com.pusilkom.ess.dto.view.InstitusiDetail;
import com.pusilkom.ess.model.Institusi;
import com.pusilkom.ess.model.InstitusiExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by fahri on 1/7/17.
 */
public interface InstitusiMapper extends BaseMapper<Institusi, Long, InstitusiExample> {

    @Select("SELECT * FROM institusi WHERE kode_institusi = #{kodeInstitusi} LIMIT 1")
    @ResultMap("InstitusiDetailResultMap")
    InstitusiDetail getInstitusiDetailByKodeInstitusiCara1(@Param("kodeInstitusi")String kodeInstitusi);


    InstitusiDetail getInstitusiDetailByKodeInstitusiCara3(@Param("kodeInstitusi")String kodeInstitusi);

    List<InstitusiItem> getListInstitusiItemBySearchForm(@Param("searchForm") InstitusiSearchForm searchForm);
    Long getTotalInstitusiItemBySearchForm(@Param("searchForm") InstitusiSearchForm searchForm);
}
