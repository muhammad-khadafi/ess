package com.pusilkom.ess.service;

import com.pusilkom.ess.dto.form.cmd.EmployeeAssignmentReportCmd;
import com.pusilkom.ess.model.*;
import com.pusilkom.ess.model.mapper.*;
import com.pusilkom.ess.util.DebugUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by pusilkom on 4/20/2017.
 */
@Service
public class ReportService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DebugUtil d;

    @Value("${dir.report.jasper}")
    String DIR_REPORT_JASPER;
    @Value("${dir.report.temp}")
    String DIR_REPORT_TEMP;
    @Value("${file.report.logo}")
    String FILE_REPORT_LOGO;

    @Autowired
    DataSource dataSource;
    @Autowired
    LogActivityReportService logActivityReportService;
    @Autowired
    MasterReferenceService masterReferenceService;
    @Autowired
    SubProjectMapper subProjectMapper;
    @Autowired
    InvoiceReportService invoiceReportService;
    @Autowired
    RecapLogActivityReportService recapLogActivityReportService;
    @Autowired
    RecapInvoiceReportService recapInvoiceReportService;
    @Autowired
    PettyCashMapper pettyCashMapper;
    @Autowired
    UserwebMapper userwebMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    FundingSourceMapper fundingSourceMapper;

    public static void getDownloadFile() {
        String url = "data/report/temp";
        String localFilename = "20181210-160928375-sampleReport.pdf";
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream()); FileOutputStream fileOutputStream = new FileOutputStream(localFilename)) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File generateTempPdfFile(String postFix) {
        return new File(DIR_REPORT_TEMP, new DateTime().toString("yyyyMMdd-HHmmssSSS") + "-" + postFix + ".pdf");
    }

    public byte[] generateSampleReport(String nama, String nik) {
        File pdfFile = this.generateTempPdfFile("SampleReport");
        byte[] result = null;
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                    DIR_REPORT_JASPER, "SampleReport.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);
//            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
//                    DIR_REPORT_JASPER, "LogActivityDetails.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);

            Map<String, Object> params = new HashMap();
            params.put("nama", nama);
            params.put("nik", nik);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

            result = IOUtils.toByteArray(new FileInputStream(pdfFile));
        } catch (Exception e) {
            log.error("CETAK SAMPLE REPORT : ", e);
        }
        return result;
    }

    public byte[] generateLogActivityReport(Integer idEmployee, Integer months, Integer years, Date dateBefore, Date dateAfter) {
        File pdfFile = this.generateTempPdfFile("log_activity_report");
        String pdfFilePath = pdfFile.getPath();
        byte[] result = null;
        try {
            LogActivityReport lar = new LogActivityReport();
            lar.setEmployeeId(idEmployee);
            lar.setStartDate(dateBefore);
            lar.setEndDate(dateAfter);
            lar.setPath(pdfFilePath);
            logActivityReportService.save(lar);
//            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
//                    DIR_REPORT_JASPER, "SampleReport.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                    DIR_REPORT_JASPER, "log_activity_details_report.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);

            Map<String, Object> params = new HashMap();
            params.put("idEmployee", idEmployee);
            params.put("months", months);
            params.put("years", years);
            params.put("date_before", new java.sql.Date(dateBefore.getTime()));
            params.put("date_after", new java.sql.Date(dateAfter.getTime()));
            params.put("subreport_location", DIR_REPORT_JASPER + "/sub_report_logActivityDetails_rev.jasper");
            params.put("sub_report_spv_location", DIR_REPORT_JASPER + "/sub_report_get_all_spv_rev.jasper");
            params.put("logo_arta_jasa", DIR_REPORT_JASPER + "/logoArtaJasa.jpg");
            params.put("logo_pusilkom", DIR_REPORT_JASPER + "/logoPusilkom.png");

            log.info("SASAS" + params.toString());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

            result = IOUtils.toByteArray(new FileInputStream(pdfFile));
        } catch (Exception e) {
            log.error("CETAK SAMPLE REPORT : ", e);
        }
        return result;
    }

    public byte[] generateLogActivityNonAjReport(Integer idEmployee, Integer months, Integer years,
                                                 Date dateBefore, Date dateAfter, Integer projectId,
                                                 Integer subProjectId) {
        String reportType = "REPORT_PER_EMPLOYEE";
        if (subProjectId != 0) {
            reportType = "REPORT_PER_SUB_PROJECT";
        }

        File pdfFile = this.generateTempPdfFile("log_activity_non_aj_report");
        String pdfFilePath = pdfFile.getPath();
        byte[] result = null;
        try {
//            LogActivityReport lar = new LogActivityReport();
//            lar.setEmployeeId(idEmployee);
//            lar.setStartDate(dateBefore);
//            lar.setEndDate(dateAfter);
//            lar.setPath(pdfFilePath);
//            logActivityReportService.save(lar);

            JasperReport jasperReport;

            Map<String, Object> params = new HashMap();
            params.put("idEmployee", idEmployee);
            params.put("months", months);
            params.put("years", years);
            params.put("date_before", new java.sql.Date(dateBefore.getTime()));
            params.put("date_after", new java.sql.Date(dateAfter.getTime()));
            params.put("logo_arta_jasa", DIR_REPORT_JASPER + "/logoArtaJasa.jpg");
            params.put("logo_pusilkom", DIR_REPORT_JASPER + "/logoPusilkom.png");

            if (reportType.equals("REPORT_PER_EMPLOYEE")) {
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                        DIR_REPORT_JASPER, "log_activity_details_non_aj_per_employee.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);
                params.put("subreport_location", DIR_REPORT_JASPER + "/sub_report_log_activity_details_non_aj_per_employee.jasper");
            } else {
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                        DIR_REPORT_JASPER, "log_activity_details_non_aj_per_sub_project.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);
                params.put("subProjectId", subProjectId);
                params.put("subreport_location", DIR_REPORT_JASPER + "/sub_report_log_activity_details_non_aj_per_sub_project.jasper");
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

            result = IOUtils.toByteArray(new FileInputStream(pdfFile));
        } catch (Exception e) {
            log.error("CETAK TIMESHEET REPORT : ", e);
        }
        return result;
    }


    public byte[] generateInvoiceReport(Date startDate, Date endDate, String subProjectId, String spvName, String clientPersonel,
                                        String clientRole, String clientRole2, String pusilkomPersonel, String pusilkomRole, String pusilkomRole2) {
        File pdfFile = this.generateTempPdfFile("invoice_report");
        String pdfFilePath = pdfFile.getPath();
        byte[] result = null;
        try {
            InvoiceReport invoice = new InvoiceReport();
            invoice.setStartDate(startDate);
            invoice.setEndDate(endDate);
            invoice.setSubProjectId(subProjectId.equals("null") || subProjectId.equals("undefined") ? null : Integer.parseInt(subProjectId));
            invoice.setSupervisorName(spvName.equals("null") || spvName.equals("undefined") ? null : spvName);
            invoice.setPath(pdfFilePath);
            invoiceReportService.save(invoice);

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                    DIR_REPORT_JASPER, "invoice_report.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);
            LocalDate periode = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

//            SubProject subProject = subProjectMapper.selectByPrimaryKey(Integer.parseInt(subProjectId));
//            String spvSubProject = masterReferenceService.getById(subProject.getSupervisor()).getName();

            Map<String, Object> params = new HashMap();
            params.put("PusilkomPersonel", pusilkomPersonel);
            params.put("ClientPersonel", clientPersonel);
            params.put("PusilkomRole", pusilkomRole);
            params.put("PusilkomRole2", pusilkomRole2);
            params.put("ClientRole", clientRole);
            params.put("ClientRole2", clientRole2);
            params.put("SubProId", subProjectId.equals("null") || subProjectId.equals("undefined") ? null : Integer.parseInt(subProjectId));
            params.put("SpvName", spvName.equals("null") || spvName.equals("undefined") ? null : spvName);
            params.put("StartDate", startDate);
            params.put("EndDate", endDate);
            params.put("Periode", periode.getMonth().toString() + " " + periode.getYear());
//            params.put("Spv", spvSubProject);
            params.put("subReportLocation", DIR_REPORT_JASPER + "/invoice_sub_report.jasper");
            params.put("subReportLocation2", DIR_REPORT_JASPER + "/invoice_sub_report2.jasper");
            params.put("logo_arta_jasa", DIR_REPORT_JASPER + "/logoArtaJasa.jpg");
            params.put("logo_pusilkom", DIR_REPORT_JASPER + "/logoPusilkom.png");

            log.info("SASAS" + params.toString());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

            result = IOUtils.toByteArray(new FileInputStream(pdfFile));
        } catch (Exception e) {
            log.error("Invoice Report Error : ", e);
        }
        return result;
    }

    public byte[] generateRecapLogActivityReport(Integer idEmployee, Date dateBefore, Date dateAfter, String recapOption,
                                                 String monthString, Integer years, Integer subProjectId, String pusilkomPersonnel,
                                                 String pusilkomRole, String clientPersonnel, String clientRole) {
        File pdfFile = this.generateTempPdfFile("recap log_activity_report");
        String pdfFilePath = pdfFile.getPath();
        JasperReport jasperReport;
        byte[] result = null;

        String pusilkomRole2 = "", clientRole2 = "";
        String[] listPusilkomRole, listClientRole;

        listPusilkomRole = pusilkomRole.split("\\|");
        if (listPusilkomRole.length > 1) {
            pusilkomRole = listPusilkomRole[0];
            pusilkomRole2 = listPusilkomRole[1];
        }
        listClientRole = clientRole.split("\\|");
        if (listClientRole.length > 1) {
            clientRole = listClientRole[0];
            clientRole2 = listClientRole[1];
        }

        try {
            if (recapOption.equalsIgnoreCase("Invoice")) {
                RecapInvoiceReport rir = new RecapInvoiceReport();
                rir.setEmployeeId(idEmployee);
                rir.setStartDate(dateBefore);
                rir.setEndDate(dateAfter);
                rir.setPath(pdfFilePath);
                rir.setSubProjectId(subProjectId);
                recapInvoiceReportService.save(rir);
            } else {
                RecapLogActivityReport rlar = new RecapLogActivityReport();
                rlar.setEmployeeId(idEmployee);
                rlar.setStartDate(dateBefore);
                rlar.setEndDate(dateAfter);
                rlar.setPath(pdfFilePath);
                rlar.setSubProjectId(subProjectId);
                recapLogActivityReportService.save(rlar);
            }
//            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
//                    DIR_REPORT_JASPER, "SampleReport.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);

            if (idEmployee == null && recapOption.equalsIgnoreCase("Invoice")) {
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                        DIR_REPORT_JASPER, "recap_report_no_name.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);
            } else {
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                        DIR_REPORT_JASPER, "recap_report_log_activity_no_name.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);
            }

            Map<String, Object> params = new HashMap();
            params.put("idEmployee", idEmployee);
            params.put("date_before", new java.sql.Date(dateBefore.getTime()));
            params.put("date_after", new java.sql.Date(dateAfter.getTime()));
            params.put("sub_project", subProjectId);
            params.put("monthString", monthString);
            params.put("years", years);
            if (recapOption.equalsIgnoreCase("Invoice")) {
                params.put("subreport_location", DIR_REPORT_JASPER + "/recap_invoice_sub_report.jasper");
            } else {
                params.put("subreport_location", DIR_REPORT_JASPER + "/recap_sub_report.jasper");
            }
            params.put("logo_arta_jasa", DIR_REPORT_JASPER + "/logoArtaJasa.jpg");
            params.put("logo_pusilkom", DIR_REPORT_JASPER + "/logoPusilkom.png");
            params.put("pusilkomPersonnel", pusilkomPersonnel);
            params.put("pusilkomRole", pusilkomRole);
            params.put("pusilkomRole2", pusilkomRole2);
            params.put("clientPersonnel", clientPersonnel);
            params.put("clientRole", clientRole);
            params.put("clientRole2", clientRole2);

            log.info("SASAS" + params.toString());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

            result = IOUtils.toByteArray(new FileInputStream(pdfFile));
        } catch (Exception e) {
            log.error("CETAK SAMPLE REPORT : ", e);
        }
        return result;
    }

    public byte[] generatePettyCashReport(String reqDateString, String reqNumber, String category, String paidToName, String reqAmount, String amountInWord) {
        File pdfFile = this.generateTempPdfFile("petty_cash_report");
        String pdfFilePath = pdfFile.getPath();
        byte[] result = null;
        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                    DIR_REPORT_JASPER, "petty_cash_report.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);

            String tahun = "";
            if (reqDateString != null && !reqDateString.isEmpty()) {
                tahun = reqDateString.substring(reqDateString.length() - 4);
            }

            PettyCashExample ex = new PettyCashExample();
            ex.createCriteria().andReqNumberEqualTo(reqNumber);
            PettyCash pet = pettyCashMapper.selectByExample(ex).get(0);
            String approverName = "Ardhi Tomiarfi, MTI";
            String paidBy = "Kasir Kas Kecil";
            String digitallyApproved = "";
            if (pet.getApprovedBy() != null) {
                Userweb usApprover = userwebMapper.selectByPrimaryKey(pet.getApprovedBy().longValue());
                if (usApprover != null && usApprover.getEmployeeId() != null) {
                    Employee emp = employeeMapper.selectByPrimaryKey(usApprover.getEmployeeId());
                    approverName = emp.getName();
                    digitallyApproved = "Digitally Approved by ";
                }
            }

            if (pet.getPaidBy() != null) {
                Userweb usApprover = userwebMapper.selectByPrimaryKey(pet.getPaidBy().longValue());
                if (usApprover != null && usApprover.getEmployeeId() != null) {
                    Employee emp = employeeMapper.selectByPrimaryKey(usApprover.getEmployeeId());
                    paidBy = paidBy + " - " + emp.getName();
                }
            }
            String description = "";
            String fundingSourceName = "";
            if (pet.getDescription() != null) {
                description = pet.getDescription();
            }

            FundingSource funding = fundingSourceMapper.selectByPrimaryKey(pet.getFundingSourceId());
            fundingSourceName = funding.getName();


            Map<String, Object> params = new HashMap();
            params.put("reqDateString", reqDateString);
            params.put("category", category);
            params.put("paidToName", paidToName);
            params.put("reqAmount", reqAmount);
            params.put("amountInWord", amountInWord);
            params.put("description", description);
            params.put("reqNumber", reqNumber);
            params.put("fundingSourceName", fundingSourceName);
            params.put("tahun", tahun);
            params.put("approverName", approverName);
            params.put("paidBy", paidBy);
            params.put("digitallyApproved", digitallyApproved);

            log.info("SASAS" + params.toString());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

            result = IOUtils.toByteArray(new FileInputStream(pdfFile));
        } catch (Exception e) {
            log.error("Petty Cash Report Error : ", e);
        }
        return result;
    }

    public byte[] generateAssignmentReport(Date dateBefore, Date dateAfter, String projectType) {

        File pdfFile = this.generateTempPdfFile("assignment_report");
        byte[] result = null;
        try {
            Map<String, Object> params = new HashMap();
            params.put("date_before", new java.sql.Date(dateBefore.getTime()));
            params.put("date_after", new java.sql.Date(dateAfter.getTime()));
            params.put("logo_pusilkom", DIR_REPORT_JASPER + "/logoPusilkom.png");
            if (projectType.equals("AJ")) {
                params.put("filter_project", "and pa.project_id = 3");
                params.put("project_type", "Artajasa");
            } else if (projectType.equals("NON_AJ")) {
                params.put("filter_project", "and pa.project_id != 3");
                params.put("project_type", "Non Artajasa");
            } else {
                params.put("project_type", "Semua Proyek (Artajasa & Non Artajasa)");
                params.put("filter_project", "");
            }

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                    DIR_REPORT_JASPER, "assignment_report.jasper").getAbsolutePath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

            result = IOUtils.toByteArray(new FileInputStream(pdfFile));
        } catch (Exception e) {
            log.error("CETAK ASSIGNMENT REPORT : ", e);
        }
        return result;
    }

    public byte[] generateTimesheetDetailReport(Integer employeeId, String employeeName,
                                                String startDate, String endDate, Boolean isAJ) {

        File pdfFile = this.generateTempPdfFile("timesheet_detail_report");

        byte[] result = null;
        try {
            JasperReport jasperReport;

            Map<String, Object> params = new HashMap();
            params.put("idEmployee", employeeId);
            params.put("date_before", new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'").parse(startDate).getTime()));
            params.put("date_after", new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'").parse(endDate).getTime()));
            params.put("logo_pusilkom", DIR_REPORT_JASPER + "/logoPusilkom.png");

            if (isAJ) {
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                        DIR_REPORT_JASPER, "timesheet_report_AJ.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);
                params.put("subreport_location", DIR_REPORT_JASPER + "/sub_report_timesheet_AJ.jasper");
            } else {
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(new File(
                        DIR_REPORT_JASPER, "timesheet_report_non_AJ.jasper").getAbsolutePath());// JasperCompileManager.compileReport(path);
                params.put("subreport_location", DIR_REPORT_JASPER + "/sub_report_timesheet_non_AJ.jasper");
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile.getAbsolutePath());

            result = IOUtils.toByteArray(new FileInputStream(pdfFile));
        } catch (Exception e) {
            log.error("CETAK TIMESHEET REPORT : ", e);
        }
        return result;
    }

    public XSSFWorkbook getProjectAssignmentReport(String startDateString, String endDateString, String selectedProjectListStr)
            throws ParseException {

        List<String> selectedProjectListSplitted = Arrays.asList(selectedProjectListStr.split("\\s*,\\s*"));
        List<Integer> selectedProjectList = new ArrayList<>();
        for (String item: selectedProjectListSplitted) {
            selectedProjectList.add(Integer.parseInt(item));
        }

        DateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat formater1 = new SimpleDateFormat("MMM-yyyy");

        List<String> listOfMonth = new ArrayList<>();
        List<Employee> listOfEmployee = employeeMapper.getEmployeeProjectAssignmentReport(formater.parse(startDateString),
                formater.parse(endDateString), selectedProjectList);

        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        try {
            startCalendar.setTime(formater.parse(startDateString));
            endCalendar.setTime(formater.parse(endDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while (startCalendar.before(endCalendar)) {
            // add one month to date per loop
            String date = formater1.format(startCalendar.getTime()).toUpperCase();
            listOfMonth.add(date);
            startCalendar.add(Calendar.MONTH, 1);
        }

        XSSFWorkbook workbook = new XSSFWorkbook();

        try {

            Sheet sheet = workbook.createSheet("Project Assignment Report");

            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 11);
            font.setBold(true);
            headerStyle.setFont(font);

            Cell headerCell;

            for (int i = 0; i < listOfMonth.size() + 1; i++) {
                sheet.setColumnWidth(i, 6000);
                headerCell = header.createCell(i);
                if (i == 0) {
                    headerCell.setCellValue("NAME");
                    headerCell.setCellStyle(headerStyle);
                } else {
                    headerCell.setCellValue(listOfMonth.get(i - 1));
                    headerCell.setCellStyle(headerStyle);
                }
            }

            CellStyle style = workbook.createCellStyle();
            style.setWrapText(true);

            int rowNumber = 0;

            for (int i = 0; i < listOfEmployee.size(); i++) {
                Row row = sheet.createRow(++rowNumber);
                Cell cellName = row.createCell(0);

                cellName.setCellValue(listOfEmployee.get(i).getName());
                cellName.setCellStyle(style);

                List<EmployeeAssignmentReportCmd> listOfEmployeeAssignmentReportCmd = employeeMapper
                        .getEmployeeProjectAssignmentReport2(listOfEmployee.get(i).getId(), formater.parse(startDateString),
                                formater.parse(endDateString), selectedProjectList);

                for (EmployeeAssignmentReportCmd item: listOfEmployeeAssignmentReportCmd) {
                    item.setMonthYear(formater1.format(item.getStartDate()).toUpperCase());
                    item.setDisplay(false);
                }

                boolean allProjectWritten = false;
                int writenRow = 1;

                while (!allProjectWritten) {
                    if (writenRow > 1) {
                        row = sheet.createRow(++rowNumber);
                    }
                    for (int k = 0; k < listOfMonth.size(); k++) {
                        for (EmployeeAssignmentReportCmd employeeData : listOfEmployeeAssignmentReportCmd) {
                            if (listOfMonth.get(k).equals(employeeData.getMonthYear()) && !employeeData.getDisplay()) {
                                Cell cell = row.createCell(k + 1);
                                cell.setCellValue(employeeData.getProjectName() + " " + employeeData.getLoad() + "%");
                                cell.setCellStyle(style);
                                employeeData.setDisplay(true);
                                break;
                            }
                        }
                    }

                    for (EmployeeAssignmentReportCmd employeeData : listOfEmployeeAssignmentReportCmd) {
                        if (employeeData.getDisplay()) {
                            allProjectWritten = true;
                        } else {
                            allProjectWritten = false;
                            writenRow++;
                            break;
                        }
                    }

                }
                rowNumber++; // create break space from one employee to others
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

}