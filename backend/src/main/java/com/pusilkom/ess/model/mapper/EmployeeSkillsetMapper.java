package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.cmd.EmployeeSkillsetCmd;
import com.pusilkom.ess.dto.form.search.EmployeeSkillsetSearchForm;
import com.pusilkom.ess.dto.table.EmployeeSkillsetItem;
import com.pusilkom.ess.model.EmployeeSkillset;
import com.pusilkom.ess.model.EmployeeSkillsetExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeSkillsetMapper extends BaseMapper<EmployeeSkillset, Integer, EmployeeSkillsetExample> {
    List<EmployeeSkillsetItem> getListEmployeeSkillsetItemBySearchForm(@Param("searchForm") EmployeeSkillsetSearchForm searchForm);
    Long getTotalEmployeeSkillsetItemBySearchForm(@Param("searchForm") EmployeeSkillsetSearchForm searchForm);
    Long checkDuplicate(@Param("searchForm") EmployeeSkillsetCmd searchForm);
}
