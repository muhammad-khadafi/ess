package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.ProjectPaymentSearchForm;
import com.pusilkom.ess.dto.table.ProjectPaymentItem;
import com.pusilkom.ess.model.ProjectPayment;
import com.pusilkom.ess.model.ProjectPaymentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by taufan on 1/7/17.
 */
public interface ProjectPaymentMapper extends BaseMapper<ProjectPayment, Integer, ProjectPaymentExample> {

    List<ProjectPaymentItem> getListProjectPaymentItemBySearchForm(@Param("searchForm") ProjectPaymentSearchForm searchForm);

    Long getTotalProjectPaymentItemBySearchForm(@Param("searchForm") ProjectPaymentSearchForm searchForm);

    ProjectPaymentItem getDetailProjectPaymentItemBySearchForm(@Param("id") Integer id);
}
