package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.EmployeeContractSearchForm;
import com.pusilkom.ess.dto.table.EmployeeContractItem;
import com.pusilkom.ess.model.EmployeeContract;
import com.pusilkom.ess.model.EmployeeContractExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeContractMapper extends BaseMapper<EmployeeContract, Integer, EmployeeContractExample> {
    List<EmployeeContractItem> getListEmployeeContractItemBySearchForm(@Param("searchForm") EmployeeContractSearchForm searchForm);
    Long getTotalEmployeeContractItemBySearchForm(@Param("searchForm") EmployeeContractSearchForm searchForm);
}
