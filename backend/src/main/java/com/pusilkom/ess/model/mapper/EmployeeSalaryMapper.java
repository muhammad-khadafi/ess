package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.EmployeeSalarySearchForm;
import com.pusilkom.ess.dto.table.EmployeeSalaryItem;
import com.pusilkom.ess.model.EmployeeSalary;
import com.pusilkom.ess.model.EmployeeSalaryExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeSalaryMapper extends BaseMapper<EmployeeSalary, Integer, EmployeeSalaryExample> {
    List<EmployeeSalaryItem> getListEmployeeSalaryItemBySearchForm(@Param("searchForm") EmployeeSalarySearchForm searchForm);
    Long getTotalEmployeeSalaryItemBySearchForm(@Param("searchForm") EmployeeSalarySearchForm searchForm);
}
