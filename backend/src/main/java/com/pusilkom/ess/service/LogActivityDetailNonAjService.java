package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.LogActivityDetailCmd;
import com.pusilkom.ess.dto.form.search.LogActivityDetailSearchForm;
import com.pusilkom.ess.dto.form.search.LogActivityViewSearchForm;
import com.pusilkom.ess.dto.table.EmployeeItem;
import com.pusilkom.ess.dto.table.LogActivityDetailItem;
import com.pusilkom.ess.dto.form.search.LogActivityViewSearchForm;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.EmployeeSkillset;
import com.pusilkom.ess.model.LogActivityDetail;
import com.pusilkom.ess.model.LogActivityDetailNonAj;
import com.pusilkom.ess.model.Project;
import com.pusilkom.ess.model.mapper.LogActivityDetailNonAjMapper;
import com.pusilkom.ess.model.mapper.ProjectMapper;
import com.pusilkom.ess.util.DebugUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
public class LogActivityDetailNonAjService {
    @Autowired
    LogActivityDetailNonAjMapper logActivityDetailNonAjMapper;
    @Autowired
    ProjectMapper projectMapper;
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;

    public void saveCmd(LogActivityDetailCmd logActivityDetailCmd){
        this.save(logActivityDetailCmd.toLogActivityDetail());
    }

    @Transactional(readOnly = false)
    public void save(LogActivityDetail logActivityDetail){
        if(logActivityDetail.getId() == null){
            logActivityDetailNonAjMapper.insert(toLogActivityNonAj(logActivityDetail));
        } else {
            logActivityDetailNonAjMapper.updateByPrimaryKey(toLogActivityNonAj(logActivityDetail));
        }
    }

    public LogActivityDetailNonAj toLogActivityNonAj(LogActivityDetail logActivityDetail){
        LogActivityDetailNonAj logActivityDetailNonAj = new LogActivityDetailNonAj();
        logActivityDetailNonAj.setId(logActivityDetail.getId());
        logActivityDetailNonAj.setDate(logActivityDetail.getDate());
        logActivityDetailNonAj.setActivityDetail(logActivityDetail.getActivityDetail());
        logActivityDetailNonAj.setThirdParty(logActivityDetail.getThirdParty());
        logActivityDetailNonAj.setDuration(logActivityDetail.getDuration());
        logActivityDetailNonAj.setOvertime(logActivityDetail.getOvertime());
        logActivityDetailNonAj.setSpvAssigned(logActivityDetail.getSpvAssigned());
        logActivityDetailNonAj.setProjectAssignId(logActivityDetail.getProjectAssignId());
        logActivityDetailNonAj.setSubProId(logActivityDetail.getSubProId());
        return logActivityDetailNonAj;
    }

    public TablePagination<LogActivityDetailItem>  getAllLogActivityDetail(LogActivityDetailSearchForm searchForm){
        List<LogActivityDetailItem> listItem = logActivityDetailNonAjMapper.getAllLogActivityDetail(searchForm);
        Long totalItem = logActivityDetailNonAjMapper.getTotalLogActivityDetail(searchForm);

        return new TablePagination<LogActivityDetailItem>(searchForm, listItem, totalItem);
    }

    public void delete(Integer id) {
        logActivityDetailNonAjMapper.deleteByPrimaryKey(id);
    }

    public List<String[]> getAllLogActivityView(LogActivityViewSearchForm searchForm){
        List<String[]> listActivities = new ArrayList<>();
        LocalDate startDate = searchForm.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = searchForm.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<EmployeeItem> listEmployees = logActivityDetailNonAjMapper.getAllAssignedEmployees(searchForm);
        if (listEmployees.size() > 0) {
            String[] headerRow = new String[listEmployees.size() + 1];
            headerRow[0] = "Date";
            int colIndex = 1;
            for (EmployeeItem emp : listEmployees) {
                headerRow[colIndex] = emp.getName();
                colIndex++;
            }
            listActivities.add(headerRow);

            List<LogActivityDetailItem> listLogs = logActivityDetailNonAjMapper.getAllLogActivityView(searchForm);
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                String[] row = new String[listEmployees.size() + 1];
                LocalDate comparedDate = date;
                row[0] = comparedDate.format(formatters);
                colIndex = 1;
                for (EmployeeItem emp : listEmployees) {
                    List<LogActivityDetailItem> filteredData = listLogs.stream()
                            .filter(obj -> obj.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(comparedDate))
                            .filter(obj -> obj.getEmployeeId().equals(emp.getId().toString()))
                            .collect(Collectors.toList());
                    if (filteredData.size() > 0) {
                        row[colIndex] = "";
                        for (LogActivityDetailItem logs : filteredData) {
                            row[colIndex] = row[colIndex] + logs.getActivityDetail() + "\r\n";
                        }
                    } else {
                        row[colIndex] = "-";
                    }

                    colIndex++;
                }

                listActivities.add(row);
            }
        }

        return listActivities;
    }
}
