package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.EmployeeEducationSearchForm;
import com.pusilkom.ess.dto.table.EmployeeEducationItem;
import com.pusilkom.ess.model.EmployeeEducation;
import com.pusilkom.ess.model.EmployeeEducationExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeEducationMapper extends BaseMapper<EmployeeEducation, Integer, EmployeeEducationExample> {
    List<EmployeeEducationItem> getListEmployeeEducationItemBySearchForm(@Param("searchForm") EmployeeEducationSearchForm searchForm);
    Long getTotalEmployeeEducationItemBySearchForm(@Param("searchForm") EmployeeEducationSearchForm searchForm);
}
