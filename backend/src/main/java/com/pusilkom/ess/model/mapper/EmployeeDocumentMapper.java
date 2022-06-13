package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.cmd.EmployeeDocumentCmd;
import com.pusilkom.ess.dto.form.search.EmployeeDocumentSearchForm;
import com.pusilkom.ess.dto.table.EmployeeDocumentItem;
import com.pusilkom.ess.model.EmployeeDocument;
import com.pusilkom.ess.model.EmployeeDocumentExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeDocumentMapper extends BaseMapper<EmployeeDocument, Integer, EmployeeDocumentExample> {
    List<EmployeeDocumentItem> getListEmployeeDocumentItemBySearchForm(@Param("searchForm") EmployeeDocumentSearchForm searchForm);
    Long getTotalEmployeeDocumentItemBySearchForm(@Param("searchForm") EmployeeDocumentSearchForm searchForm);
    Long checkDuplicate(@Param("searchForm") EmployeeDocumentCmd searchForm);
}
