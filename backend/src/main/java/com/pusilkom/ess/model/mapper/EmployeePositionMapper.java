package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.EmployeePositionSearchForm;
import com.pusilkom.ess.dto.table.EmployeePositionItem;
import com.pusilkom.ess.model.EmployeePosition;
import com.pusilkom.ess.model.EmployeePositionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface EmployeePositionMapper extends BaseMapper<EmployeePosition, Integer, EmployeePositionExample> {

    List<EmployeePositionItem> getListEmployeePositionItemBySearchForm(@Param("searchForm") EmployeePositionSearchForm searchForm);

    Long getTotalEmployeePositionItemBySearchForm(@Param("searchForm") EmployeePositionSearchForm searchForm);

    EmployeePositionItem getEmployeePositionByIdProject(@Param("id") Integer id);

    Long checkDuplicate(@Param("searchForm") EmployeePosition employeePosition);
}
