package com.pusilkom.ess.model.mapper;

import com.pusilkom.ess.dto.form.search.CertificateNumberSearchForm;
import com.pusilkom.ess.dto.table.CertificateNumberItem;
import com.pusilkom.ess.model.CertificateNumber;
import com.pusilkom.ess.model.CertificateNumberExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by muhammad.khadafi on 1/7/20.
 */
public interface CertificateNumberMapper extends BaseMapper<CertificateNumber, Long, CertificateNumberExample> {

    Long getRegulerSequence();

    Long getShortCourseSequence();

    Long getIHTSequence();

    List<CertificateNumberItem> getListCertificateNumberItemBySearchForm(@Param("searchForm") CertificateNumberSearchForm certificateNumberSearchForm);

    Long getTotalListCertificateNumberItemBySearchForm(@Param("searchForm") CertificateNumberSearchForm certificateNumberSearchForm);
}
