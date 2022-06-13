package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.MasterReferenceCmd;
import com.pusilkom.ess.dto.form.search.CertificateNumberSearchForm;
import com.pusilkom.ess.dto.form.search.MasterReferenceSearchForm;
import com.pusilkom.ess.dto.table.CertificateNumberItem;
import com.pusilkom.ess.dto.table.MasterReferenceItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.CertificateNumber;
import com.pusilkom.ess.model.MasterReference;
import com.pusilkom.ess.model.MasterReferenceExample;
import com.pusilkom.ess.model.mapper.CertificateNumberMapper;
import com.pusilkom.ess.model.mapper.MasterReferenceMapper;
import com.pusilkom.ess.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by muhammad.khadafi on 1/7/17.
 */


@Service
@Transactional
public class CertificateNumberService {

    @Autowired
    CertificateNumberMapper certificateNumberMapper;

    public String certificateNumberGenerator(CertificateNumber certificateNumber) {
       String monthYear;
       Long sequenceNumber;
       String result;

       SimpleDateFormat df = new SimpleDateFormat("MMYY");
       monthYear = df.format(certificateNumber.getCourseDate());

       if (certificateNumber.getCategory().equalsIgnoreCase("REGULER"))
           sequenceNumber = certificateNumberMapper.getRegulerSequence();
       else if (certificateNumber.getCategory().equalsIgnoreCase("SHORT_COURSE"))
           sequenceNumber = certificateNumberMapper.getShortCourseSequence();
       else
           sequenceNumber = certificateNumberMapper.getIHTSequence();

       result = certificateNumber.getCourseName() + "/PIK-UI/" + monthYear + "/" + sequenceNumber;

       certificateNumber.setCertificateNumber(result);
       certificateNumberMapper.insert(certificateNumber);

       return result;

    }

    public TablePagination<CertificateNumberItem> getTablePagination(CertificateNumberSearchForm certificateNumberSearchForm) {
        List<CertificateNumberItem> listItem = certificateNumberMapper.getListCertificateNumberItemBySearchForm(certificateNumberSearchForm);
        try{
//            listItem.stream().forEach(obj -> {
//                DateFormat dateFormat = new SimpleDateFormat("MMYY");
//                obj.setMonthYear(dateFormat.format(obj.getCourseDate()));
//            });
            DateFormat dateFormat = new SimpleDateFormat("MM/YY");
            for (CertificateNumberItem certificateNumberItem: listItem) {
                certificateNumberItem.setMonthYear(dateFormat.format(certificateNumberItem.getCourseDate()));
            }
        } catch (Exception e){
            e.printStackTrace();
        }



        Long totalData = certificateNumberMapper.getTotalListCertificateNumberItemBySearchForm(certificateNumberSearchForm);
        return new TablePagination<CertificateNumberItem>(certificateNumberSearchForm, listItem, totalData);

    }

    public void delete(Long id) {
        certificateNumberMapper.deleteByPrimaryKey(id);
    }
}

