package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.cmd.EmployeeBenefitCmd;
import com.pusilkom.ess.dto.form.search.EmployeeBenefitSearchForm;
import com.pusilkom.ess.dto.table.EmployeeBenefitItem;
import com.pusilkom.ess.model.EmployeeBenefit;
import com.pusilkom.ess.model.EmployeeBenefitExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeBenefitMapper extends BaseMapper<EmployeeBenefit, Integer, EmployeeBenefitExample> {
    List<EmployeeBenefitItem> getListEmployeeBenefitItemBySearchForm(@Param("searchForm") EmployeeBenefitSearchForm searchForm);
    Long getTotalEmployeeBenefitItemBySearchForm(@Param("searchForm") EmployeeBenefitSearchForm searchForm);
    Long checkDuplicate(@Param("searchForm") EmployeeBenefitCmd searchForm);
}
