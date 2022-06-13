package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.EmployeeFamilySearchForm;
import com.pusilkom.ess.dto.table.EmployeeFamilyItem;
import com.pusilkom.ess.model.EmployeeFamily;
import com.pusilkom.ess.model.EmployeeFamilyExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeFamilyMapper extends BaseMapper<EmployeeFamily, Integer, EmployeeFamilyExample> {
    List<EmployeeFamilyItem> getListEmployeeFamilyItemBySearchForm(@Param("searchForm") EmployeeFamilySearchForm searchForm);
    Long getTotalEmployeeFamilyItemBySearchForm(@Param("searchForm") EmployeeFamilySearchForm searchForm);
}
