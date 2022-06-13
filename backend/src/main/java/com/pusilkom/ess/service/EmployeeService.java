package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.*;
import com.pusilkom.ess.dto.form.search.EmployeeSearchForm;
import com.pusilkom.ess.dto.table.EmployeeItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.*;
import com.pusilkom.ess.model.mapper.*;
import com.pusilkom.ess.util.CommonUtil;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arief on 10/31/18.
 */


@Service
@Transactional(readOnly = true)
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    DebugUtil d;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EmployeeEducationMapper educationMapper;

    @Autowired
    EmployeeFamilyMapper employeeFamilyMapper;

    @Autowired
    EmployeeBenefitMapper employeeBenefitMapper;

    @Autowired
    EmployeeSalaryMapper employeeSalaryMapper;

    @Autowired
    EmployeeSkillsetMapper employeeSkillsetMapper;

    @Autowired
    EmployeeDocumentMapper employeeDocumentMapper;

    @Autowired
    EmployeeContractMapper employeeContractMapper;

    @Autowired
    EmployeePositionMapper employeePositionMapper;

    @Autowired
    UploadUtil uploadUtil;

    @Transactional(readOnly = false)
    public Integer saveCmd(EmployeeCmd employee) {
        Integer idEmployee = null;
        boolean isNew = (employee.getId() == null);
        if(isNew) {
            employee.setCreatedBy(CommonUtil.getUserId());
            employee.setCreatedDate(CommonUtil.getCurrentDate());
            Employee pegawai = employee.toEmployee();
            employeeMapper.insertSelective(pegawai);

            idEmployee = pegawai.getId();
            log.info("*** POST EMPLOYEE ID : {}", d.toString(pegawai));

            List<EducationCmd> dataEducation = employee.getListPendidikan();
            List<EmployeeFamilyCmd> dataFamily = employee.getListFamily();
            List<EmployeeBenefitCmd> dataBenefit = employee.getListBenefit();
            List<EmployeeSalaryCmd> dataSalary = employee.getListSalary();
            List<EmployeeSkillsetCmd> dataSkillset = employee.getListSkillset();
            List<EmployeeDocumentCmd> dataDoc = employee.getListDocument();
            List<EmployeeContractCmd> dataCon = employee.getListContract();
            List<EmployeePositionCmd> dataPos = employee.getListPosition();
            log.info("*** POST EMPLOYEE : {}", d.toString(employee));

            for(EducationCmd education : dataEducation) {
                //insert education
                education.setEmployeeId(idEmployee);
                education.setCreatedBy(CommonUtil.getUserId());
                education.setCreatedDate(CommonUtil.getCurrentDate());
                educationMapper.insertSelective(education.toEmployeeEducation());
            }

            for(EmployeeFamilyCmd family : dataFamily) {
                //insert family
                family.setEmployeeId(idEmployee);
                family.setCreatedBy(CommonUtil.getUserId());
                family.setCreatedDate(CommonUtil.getCurrentDate());
                employeeFamilyMapper.insertSelective(family.toEmployeeFamily());
            }

            for(EmployeeBenefitCmd benefit : dataBenefit) {
                //insert benefit
                benefit.setEmployeeId(idEmployee);
                benefit.setCreatedBy(CommonUtil.getUserId());
                benefit.setCreatedDate(CommonUtil.getCurrentDate());
                employeeBenefitMapper.insertSelective(benefit.toEmployeeBenefit());
            }

            for(EmployeeSalaryCmd salary : dataSalary) {
                //insert benefit
                salary.setEmployeeId(idEmployee);
                salary.setCreatedBy(CommonUtil.getUserId());
                salary.setCreatedDate(CommonUtil.getCurrentDate());
                employeeSalaryMapper.insertSelective(salary.toEmployeeSalary());
            }

            for(EmployeeSkillsetCmd skillset : dataSkillset) {
                //insert benefit
                skillset.setEmployeeId(idEmployee);
                skillset.setCreatedBy(CommonUtil.getUserId());
                skillset.setCreatedDate(CommonUtil.getCurrentDate());
                employeeSkillsetMapper.insertSelective(skillset.toEmployeeSkillset());
            }

            for(EmployeeDocumentCmd doc : dataDoc) {
                //insert doc
                doc.setEmployeeId(idEmployee);
                doc.setUploadTime(new Date());
                doc.setCreatedBy(CommonUtil.getUserId());
                doc.setCreatedDate(CommonUtil.getCurrentDate());
                employeeDocumentMapper.insertSelective(doc.toEmployeeDocument());
            }

            for(EmployeeContractCmd contract : dataCon) {
                //insert doc
                contract.setEmployeeId(idEmployee);
                contract.setCreatedBy(CommonUtil.getUserId());
                contract.setCreatedDate(CommonUtil.getCurrentDate());
                employeeContractMapper.insertSelective(contract.toEmployeeContract());
            }

            for(EmployeePositionCmd position : dataPos) {
                //insert doc
                position.setEmployeeId(idEmployee);
                position.setCreatedBy(CommonUtil.getUserId());
                position.setCreatedDate(CommonUtil.getCurrentDate());
                employeePositionMapper.insertSelective(position.toEmployeePosition());
            }
        } else {
            employee.setUpdatedBy(CommonUtil.getUserId());
            employee.setUpdatedDate(CommonUtil.getCurrentDate());
            idEmployee = employee.getId();
            employeeMapper.updateByPrimaryKey(employee.toEmployee());
        }
        return idEmployee;
    }

    public Integer idEmployee(EmployeeCmd employee){
        employeeMapper.insertSelective(employee.toEmployee());
        return employee.getId();
    }


    public TablePagination<EmployeeItem> getTablePagination(EmployeeSearchForm searchForm) {
        List<EmployeeItem> listItem = employeeMapper.getListEmployeeItemBySearchForm(searchForm);
        Long totalItemFiltered = employeeMapper.getTotalEmployeeItemBySearchForm(searchForm);
        for (EmployeeItem emp : listItem) {
            if(emp.getBirthDate() != null){
                emp.convBirthDateToString();
            }
        }

        return new TablePagination<EmployeeItem>(searchForm, listItem, totalItemFiltered);
    }

    public Employee getEmployeeById(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        EmployeeEducationExample exEd = new EmployeeEducationExample();
        EmployeeSalaryExample exSal = new EmployeeSalaryExample();
        EmployeeBenefitExample exBen = new EmployeeBenefitExample();
        EmployeeDocumentExample exDoc = new EmployeeDocumentExample();
        EmployeeFamilyExample exFam = new EmployeeFamilyExample();
        EmployeeSkillsetExample exSkill = new EmployeeSkillsetExample();
        EmployeeContractExample exCont = new EmployeeContractExample();
        EmployeePositionExample exPos = new EmployeePositionExample();

        exEd.createCriteria().andEmployeeIdEqualTo(id);
        exSal.createCriteria().andEmployeeIdEqualTo(id);
        exBen.createCriteria().andEmployeeIdEqualTo(id);
        exDoc.createCriteria().andEmployeeIdEqualTo(id);
        exFam.createCriteria().andEmployeeIdEqualTo(id);
        exPos.createCriteria().andEmployeeIdEqualTo(id);
        exSkill.createCriteria().andEmployeeIdEqualTo(id);
        exCont.createCriteria().andEmployeeIdEqualTo(id);

        educationMapper.deleteByExample(exEd);
        employeeSalaryMapper.deleteByExample(exSal);
        employeeBenefitMapper.deleteByExample(exBen);
        employeeFamilyMapper.deleteByExample(exFam);
        employeeSkillsetMapper.deleteByExample(exSkill);
        employeeContractMapper.deleteByExample(exCont);
        employeePositionMapper.deleteByExample(exPos);

        List<EmployeeDocument> doc = employeeDocumentMapper.selectByExample(exDoc);
        for(EmployeeDocument setiap : doc) {
            uploadUtil.deleteFileEmployeeDocument(setiap.getFileName());
        }

        employeeDocumentMapper.deleteByExample(exDoc);

        employeeMapper.deleteByPrimaryKey(id);
    }

    public List<EmployeeItem> getAllEmployeeForDropDown()
    {
        List<EmployeeItem> listEmployeeItem = new ArrayList<>();
        EmployeeExample ee = new EmployeeExample();
        ee.createCriteria();
        ee.setOrderByClause("name asc");
        List<Employee> list = employeeMapper.selectByExample(ee);
        for(Employee setiap : list)
        {
            listEmployeeItem.add(new EmployeeItem(setiap));
        }
        return listEmployeeItem;
    }

    public List<EmployeeItem> getListEmployeeNotAssign() {
        List<EmployeeItem> listEmployeeItem = employeeMapper.getListEmployeeNotAssign();

        if(!listEmployeeItem.isEmpty()){
            return listEmployeeItem;
        } else {
            return null;
        }
    }

    public List<EmployeeItem> getListEmployeeByProjectId(Integer employeeId, Integer months, Integer years) {
        List<EmployeeItem> listEmployeeItem = employeeMapper.getListEmployeeByProjectId(employeeId, months, years);

        if(!listEmployeeItem.isEmpty()){
            return listEmployeeItem;
        } else {
            return null;
        }
    }

    public List<EmployeeItem> getListEmployeeByRoleManager() {
        List<EmployeeItem> listEmployeeItem = employeeMapper.getListEmployeeByRoleManager();

        if(!listEmployeeItem.isEmpty()){
            return listEmployeeItem;
        } else {
            return null;
        }
    }

    public List<EmployeeItem> getListEmployeeAJ() {
        List<EmployeeItem> listEmployeeItem = employeeMapper.getListEmployeeAJ();
        if(!listEmployeeItem.isEmpty()){
            return listEmployeeItem;
        } else {
            return null;
        }
    }

    public List<EmployeeItem> getListEmployeeNonAJ() {
        List<EmployeeItem> listEmployeeItem = employeeMapper.getListEmployeeNonAJ();
        if(!listEmployeeItem.isEmpty()){
            return listEmployeeItem;
        } else {
            return null;
        }
    }
}
