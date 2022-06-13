package com.pusilkom.ess.controller;

import com.pusilkom.ess.dto.common.DownloadResponse;
import com.pusilkom.ess.dto.form.cmd.ProjectAssignmentCmd;
import com.pusilkom.ess.dto.form.search.LogActivityDetailSearchForm;
import com.pusilkom.ess.dto.form.search.ProjectAssignmentReportForm;
import com.pusilkom.ess.dto.form.search.ProjectAssignmentSearchForm;
import com.pusilkom.ess.dto.table.ProjectAssignmentItem;
import com.pusilkom.ess.dto.table.TablePagination;
import com.pusilkom.ess.model.Project;
import com.pusilkom.ess.service.ProjectAssignmentService;
import com.pusilkom.ess.service.ReportService;
import com.pusilkom.ess.util.DebugUtil;
import com.pusilkom.ess.util.DownloadUtil;
import com.pusilkom.ess.util.RestValidatorUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
public class ProjectAssignmentController extends BaseRestController{
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DebugUtil d;
    @Autowired
    RestValidatorUtil rv;
    @Autowired
    ProjectAssignmentService projectAssignmentService;
    @Autowired
    DownloadUtil downloadUtil;
    @Autowired
    ReportService reportService;

    final static String ERROR_MSG = "Failed to process";
    final static String TICKET_NOTVALID_MSG = "Ticket is not valid";

    @RequestMapping(value = "/project-assignment/getAll/{idProject}/{subMenuName}", method = RequestMethod.GET)
    public ResponseEntity getAllProjectAssignment(@PathVariable Integer idProject, @PathVariable String subMenuName) {
        ResponseEntity responseEntity = null;
        try
        {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Integer months = localDate.getMonthValue();
            Integer years = localDate.getYear();
            List<ProjectAssignmentItem> list = projectAssignmentService.getAllProjectAssignment(idProject, months, years, subMenuName);
            log.info("Cek isi PROJECT ASSIGNMENT : {}", d.toString(list));
            responseEntity = ResponseEntity.ok(list);
        }
        catch (Exception e)
        {
            log.error("GET ALL PROJECT ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }
        return responseEntity;
    }

    @RequestMapping(value = "/project-assignment/table", method = RequestMethod.GET)
    public ResponseEntity getTableSearch(@Valid ProjectAssignmentSearchForm projectAssignmentSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("ProjectAssignmentForm : {}", d.toString(projectAssignmentSearchForm));
        log.info("Query : {}", d.toString(projectAssignmentSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(projectAssignmentSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            if(projectAssignmentSearchForm.getProjectId() != null || projectAssignmentSearchForm.getOrganizationId() != null
                    || projectAssignmentSearchForm.getRoleId() != null || projectAssignmentSearchForm.getLevelId() != null
                    || projectAssignmentSearchForm.getEmployeeId() != null){
                TablePagination<ProjectAssignmentItem> tablePagination = projectAssignmentService.getTablePagination(projectAssignmentSearchForm);
                log.info(">>> tablePagination 2 : {}", d.toString(tablePagination));
                responseEntity = ResponseEntity.ok(tablePagination);
            } else {
                log.info("masuk disini");
                TablePagination<ProjectAssignmentItem> tablePagination = projectAssignmentService.getTablePaginationGetAllEmployee(projectAssignmentSearchForm);
                log.info(">>> tablePagination 1 : {}", d.toString(tablePagination));
                responseEntity = ResponseEntity.ok(tablePagination);
            }

        } catch (Exception e) {
            log.error("TABLE project assignment : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project-assignment/table-for-home/", method = RequestMethod.GET)
    public ResponseEntity getTableForHome(@Valid ProjectAssignmentSearchForm projectAssignmentSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("ProjectAssignmentFormForHome : {}", d.toString(projectAssignmentSearchForm));
        log.info("Query : {}", d.toString(projectAssignmentSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(projectAssignmentSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<ProjectAssignmentItem> tablePagination = projectAssignmentService.getTablePaginationForHome(projectAssignmentSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE project assignment : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project-assignment/table-for-home-prev/", method = RequestMethod.GET)
    public ResponseEntity getTableForHomePrev(@Valid ProjectAssignmentSearchForm projectAssignmentSearchForm, BindingResult result, HttpServletRequest httpServletRequest) {

        log.info("ProjectAssignmentFormForHome : {}", d.toString(projectAssignmentSearchForm));
        log.info("Query : {}", d.toString(projectAssignmentSearchForm.getOrderQuery()));

        ResponseEntity responseEntity = null;
        try {
            log.info("*** POST TABLE : {}", d.toString(projectAssignmentSearchForm));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            TablePagination<ProjectAssignmentItem> tablePagination = projectAssignmentService.getTablePaginationForHomePrev(projectAssignmentSearchForm);

            log.info(">>> tablePagination : {}", d.toString(tablePagination));

            responseEntity = ResponseEntity.ok(tablePagination);

        } catch (Exception e) {
            log.error("TABLE project assignment : ", e);

            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project-assignment/view/{id}", method = RequestMethod.GET)
    public ResponseEntity getProjectAssignmentById(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;

        try {
            ProjectAssignmentItem pa = projectAssignmentService.getProjectAssignmentById(id);
            log.info(">>> get project assignment : {}", d.toString(pa));
            if (pa == null) {
                return ResponseEntity.notFound().build();
            }

            responseEntity = ResponseEntity.ok(pa);
            log.info(">>> get project assignment by id : {}", d.toString(responseEntity));
        } catch (Exception e) {
            log.error("GET project assignment", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project-assignment/add", method = RequestMethod.POST)
    public ResponseEntity postProjectAssignment(@Valid @RequestBody ProjectAssignmentCmd projectAssignmentCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        log.info("*** SEBELUM POST CMD : {}", d.toString(projectAssignmentCmd));
        try {
            log.info("*** POST CMD : {}", d.toString(projectAssignmentCmd));
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            projectAssignmentService.saveCmd(projectAssignmentCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("POST project assignment", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project-assignment/{id}", method = RequestMethod.PUT)
    public ResponseEntity putProjectAssignment(@PathVariable Integer id, @Valid @RequestBody ProjectAssignmentCmd projectAssignmentCmd, BindingResult result) {
        ResponseEntity responseEntity = null;

        try {
            log.info("*** PUT CMD : {}", d.toString(projectAssignmentCmd));

            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }

            projectAssignmentService.saveCmd(projectAssignmentCmd);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("PUT PROJECT ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project-assignment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectAssignment(@PathVariable Integer id) {
        ResponseEntity responseEntity = null;
        try {
            projectAssignmentService.deactiveEmployeeProjectAssignment(id);
            responseEntity = ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("DELETE PROJECT ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/project-assignment/checkduplicate", method = RequestMethod.POST)
    public ResponseEntity checkDuplicateProjectAssignment(@Valid @RequestBody ProjectAssignmentCmd projectAssignmentCmd, BindingResult result) {
        ResponseEntity responseEntity = null;
        try {
            if (result.hasErrors()) {
                return ResponseEntity.unprocessableEntity().body(rv.buildError(result.getFieldErrors()));
            }
            log.info("*** PUT CMD : {}", d.toString(projectAssignmentCmd));
            Boolean pa  = projectAssignmentService.checkDuplicate(projectAssignmentCmd);
            responseEntity = ResponseEntity.ok(pa);
        } catch (Exception e) {
            log.error("CHECK DUPLICATE PROJECT ASSIGNMENT", e);
            responseEntity = ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/report/generateticket/project-assignment-report", method = RequestMethod.POST)
    public ResponseEntity generateTicketProjectAssignmentReport(@RequestBody ProjectAssignmentReportForm projectAssignmentReportForm,
                                                         HttpServletRequest request, HttpServletResponse response)
    {
        ResponseEntity  responseEntity = null;
        try {

            String ticket = downloadUtil.generateTicket("/api/download/cetakreport/project-assignment-report/"
                    + projectAssignmentReportForm.getStartDateString() + "/"
                    + projectAssignmentReportForm.getEndDateString() + "/"
                    + projectAssignmentReportForm.getSelectedProjectListStr());
            responseEntity = ResponseEntity.ok(new DownloadResponse(ticket));
        } catch (Exception e) {
            log.error("GET TICKET", e);
            responseEntity =  ResponseEntity.badRequest().body(rv.buildError(e, ERROR_MSG));
        }

        return responseEntity;
    }

    @RequestMapping(value = "/download/cetakreport/project-assignment-report/{startDateString}/{endDateString}/{selectedProjectListStr}",
            method = RequestMethod.GET)
    public ResponseEntity getAssignmentReport(@PathVariable String startDateString, @PathVariable String endDateString, @PathVariable String selectedProjectListStr,
                                              @RequestParam("ticket") String ticket, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception  {
        ResponseEntity responseEntity = null;

        log.info("REQUEST uri : {}", request.getRequestURI());
        log.info("TICKET : {}", ticket);

        try {
            boolean isVerified = downloadUtil.verifyTicket(request, ticket);
            log.info("isVerified : {}", isVerified);
            if (!isVerified) {
                return ResponseEntity.unprocessableEntity().body(TICKET_NOTVALID_MSG);
            }

            XSSFWorkbook workbook = reportService.getProjectAssignmentReport(startDateString, endDateString, selectedProjectListStr);

            try {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=project_assignment_report.xlsx");
                workbook.write(response.getOutputStream()); // Write workbook to response.
                workbook.close();
            }
            catch (Exception e) {
                log.error("ERROR XLS REPORT : "+e.getMessage(), e.getCause());
            }
        } catch (Exception ex) {
            log.error("ERROR XLS REPORT : "+ex.getMessage(), ex.getCause());
        }
        return responseEntity;
    }
}
