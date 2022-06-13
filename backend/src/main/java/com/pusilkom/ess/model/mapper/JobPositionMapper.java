package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.JobPositionSearchForm;
import com.pusilkom.ess.dto.table.JobPositionItem;
import com.pusilkom.ess.dto.view.JobPositionDetail;
import com.pusilkom.ess.model.JobPosition;
import com.pusilkom.ess.model.JobPositionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ITF on 11/1/2018.
 */
public interface JobPositionMapper extends BaseMapper<JobPosition, Integer, JobPositionExample> {

    List<JobPositionItem> getListJobPositionItemBySearchForm(@Param("searchForm") JobPositionSearchForm searchForm);
    Long getTotalJobPositionItemBySearchForm(@Param("searchForm") JobPositionSearchForm searchForm);

    @Select("select * from job_position")
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="name", column="name"),
            @Result(property="level", column="level"),
            @Result(property="isActive", column="is_active"),
    })
    public List<JobPositionDetail> selectAllJobPosition();
}
