package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.EmployeeAttendanceSearchForm;
import com.pusilkom.ess.dto.table.EmployeeAttendanceItem;
import com.pusilkom.ess.model.EmployeeAttendance;
import com.pusilkom.ess.model.EmployeeAttendanceExample;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ITF on 11/1/2018.
 */
public interface EmployeeAttendanceMapper extends BaseMapper<EmployeeAttendance, Long, EmployeeAttendanceExample> {

    List<EmployeeAttendanceItem> getListEmployeeAttendance(@Param("searchForm") EmployeeAttendanceSearchForm searchForm);
    List<EmployeeAttendanceItem> getAllListEmployeeAttendance(@Param("searchForm") EmployeeAttendanceSearchForm searchForm);
    Long getTotalEmployeeAttendance(@Param("searchForm") EmployeeAttendanceSearchForm searchForm);

    EmployeeAttendance getIdClockInData(@Param("employeeId") Integer employeeId, @Param("todayDate") LocalDate todayDate);

    ArrayList<EmployeeAttendanceItem> viewLog(@Param("id") Integer id, @Param("month") Integer month, @Param("year") Integer year);
}
