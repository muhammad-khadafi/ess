/**
 * Created by fahri on 3/27/17.
 */

import Vue from 'vue'
import Home from 'components/Home.vue'
import Login from 'components/Login.vue'
import Forbidden from 'components/Forbidden.vue'
import {InstitusiList, InstitusiForm, InstitusiDetail} from 'components/module/institusi/modal'
import {JobPositionList, JobPositionForm, JobPositionDetail, JobPositionEdit} from 'components/module/jobposition'
import {DropdownDinamis, WrapperJqueryPlugin, GridInlineEditing, Report} from 'components/module/sample'
import {OrganizationForm, OrganizationList, OrganizationEdit, OrganizationDetail} from 'components/module/organisasi/modal'
import VueRouter from 'vue-router'
import auth from 'src/shared/auth'
import {EmployeeFormParent, EmployeeList, EmployeeDetailParent, EmployeeEditParent} from 'components/module/employee'
import {EducationDetailItemEdit} from 'components/module/employee/education'
import {InventoryList, InventoryParent} from 'components/module/inventory'
import {MasterReferenceForm, MasterReferenceList, MasterReferenceView, MasterReferenceUpdate} from 'components/module/masterreference'
import {OrganizationStructureForm, OrganizationStructureList, OrganizationStructureView, OrganizationStructureUpdate, OrganizationStructure} from 'components/module/organizationstructure'
import {ProjectList, ProjectParent, ProjectParentEdit} from 'components/module/project'
import {InventoryAssignmentList, InventoryAssignmentForm, InventoryAssignmentEdit, InventoryAssignmentView} from 'components/module/inventoryAssignment'
import {ProjectContractForm, ProjectContractList, ProjectContractView, ProjectContractUpdate} from 'components/module/projectContract'
import {ProjectPaymentForm, ProjectPaymentList, ProjectPaymentView, ProjectPaymentUpdate} from 'components/module/projectPayment'
import {EmployeeLoggingForm, EmployeeLoggingList, EmployeeLoggingView, EmployeeLoggingUpdate} from 'components/module/employeeLogging'
import {LogActivityDetailsReport, InvoiceReport, RecapReport, LogActivityDetailsNonAjReport, AssignmentNonAjReport, TimesheetSummaryReport} from 'components/module/reports'
import {ProjectAssignmentList, ProjectAssignmentView, ProjectAssignmentUpdate, ProjectAssignmentForm} from 'components/module/projectassignment'
import {SubProjectList, SubProjectForm, SubProjectView, SubProjectEdit} from 'components/module/subproject'
import {LevelRateList, LevelRateForm, LevelRateUpdate, LevelRateView} from 'components/module/levelrate'
import {LogActivityReportList} from 'components/module/logActivityReport'
import {RecapLogActivityReportList} from 'components/module/recapLogActivityReport'
import {RecapInvoiceReportList} from 'components/module/recapInvoiceReport'
import {InvoiceReportList} from 'components/module/invoiceReport'
import {UserList, UserForm, UserEdit} from 'components/module/user'
import {UserRoleForm, UserRoleList, UserRoleView, UserRoleUpdate} from 'components/module/userRole'
import {RoleList, RoleForm, RoleEdit} from 'components/module/role'
import {RoleMenuForm, RoleMenuList, RoleMenuView, RoleMenuUpdate} from 'components/module/roleMenu'
import {MenuList, MenuForm, MenuEdit} from 'components/module/menu'
import ChangePassword from 'components/ChangePassword.vue'
import {EmployeeAttendanceList} from 'components/module/employeeAttendance'
import {SuratAwal, ViewSurat} from 'components/module/surat'
import {PettyCashList, PettyCashView, PettyCashForm, PettyCashVerify, PettyCashConfirm, PettyCashGuestForm} from 'components/module/pettycash'
import {LeaveApprovalList} from 'components/module/leave'
import {LeaveRequestList, LeaveRequestForm} from 'components/module/leaveRequest'
import {CertificateList, GenerateCertificateNumber} from 'components/module/training'
import {EmployeeLoggingNonAjList} from 'components/module/employeeLoggingNonAj'
import {LogActivityView, ProjectAssignmentReport} from 'components/module/generalReport'
import {FundingSourceList, FundingSourceForm, FundingSourceEdit} from 'components/module/fundingSource'

Vue.use(VueRouter)

let mapRouter = [
  {
    name: 'HOME',
    path: '/home',
    component: Home,
    meta: {auth: true, title: 'Home'}
  },
  {
    path: '/login',
    component: Login,
    meta: {title: ''}
  },
  { name: 'ORGANIZATION',
    path: '/organisasi',
    component: OrganizationList,
    meta: {auth: true, title: 'Manage Organization'}
  },
  { name: 'ORGANIZATION_ADD',
    path: '/organisasi/add',
    component: OrganizationForm,
    meta: {auth: true, title: 'Add Organization'}
  },
  { name: 'ORGANIZATION_EDIT',
    path: '/organization/edit/:id',
    component: OrganizationEdit,
    meta: {auth: true, title: 'Edit Organization'}
  },
  { name: 'ORGANIZATION',
    path: '/organization/view/:id',
    component: OrganizationDetail,
    meta: {auth: true, title: 'View Organization'}
  },
  {
    name: 'INSTITUSI',
    path: '/institusi',
    component: InstitusiList,
    meta: {auth: true, title: 'Kelola Institusi'}
  },
  { path: '/institusi/add',
    component: InstitusiForm,
    meta: {auth: true, title: 'Tambah Institusi'}
  },
  { path: '/institusi/edit/:id',
    component: InstitusiForm,
    meta: {auth: true, title: 'Ubah Institusi'}
  },
  { path: '/institusi/view/:id',
    component: InstitusiDetail,
    meta: {auth: true, title: 'Lihat Institusi'}
  },
  { path: '/dropdown-dinamis',
    component: DropdownDinamis,
    meta: {auth: true, title: 'Dropdown Dinamis'}
  },
  { path: '/wrapper-jquery-plugin',
    component: WrapperJqueryPlugin,
    meta: {auth: true, title: 'Wrapper Jquery Plugin'}
  },
  { path: '/grid-inline-editing',
    component: GridInlineEditing,
    meta: {auth: true, title: 'Grid Inline Editing'}
  },
  { name: 'EMPLOYEE',
    path: '/employee',
    component: EmployeeList,
    meta: {auth: true, title: 'Manage Employee'}
  },
  { name: 'EMPLOYEE_ADD',
    path: '/employee/add',
    component: EmployeeFormParent,
    meta: {auth: true, title: 'Add Employee'}
  },
  { name: 'EMPLOYEE',
    path: '/employee/view/:id',
    component: EmployeeDetailParent,
    meta: {auth: true, title: 'View Employee'}
  },
  { name: 'EMPLOYEE_EDIT',
    path: '/employee/edit/:id',
    component: EmployeeEditParent,
    meta: {auth: true, title: 'Edit Employee'}
  },
  { name: 'EMPLOYEE_EDIT',
    path: '/education/edit/:id',
    component: EducationDetailItemEdit,
    meta: {auth: true, title: 'Edit Education'}
  },
  // { path: '/education/edit/:id',
  //   component: EducationEdit,
  //   meta: {auth: true, title: 'Edit Education'}
  // },
  { name: 'JOB_POSITION',
    path: '/job-position',
    component: JobPositionList,
    meta: {auth: true, title: 'Manage Job Position'}
  },
  { name: 'JOB_POSITION_ADD',
    path: '/job-position/add',
    component: JobPositionForm,
    meta: {auth: true, title: 'Add Job Position'}
  },
  { name: 'JOB_POSITION',
    path: '/job-position/view/:id',
    component: JobPositionDetail,
    meta: {auth: true, title: 'View Job Position'}
  },
  { name: 'JOB_POSITION_EDIT',
    path: '/job-position/edit/:id',
    component: JobPositionEdit,
    meta: {auth: true, title: 'Edit Job Position'}
  },
  { name: 'MASTER_REFERENCE',
    path: '/master-reference',
    component: MasterReferenceList,
    meta: {auth: true, title: 'Manage Master Reference'}
  },
  { name: 'MASTER_REFERENCE_ADD',
    path: '/master-reference/add',
    component: MasterReferenceForm,
    meta: {auth: true, title: 'Add Master Reference'}
  },
  { name: 'MASTER_REFERENCE',
    path: '/master-reference/view/:id',
    component: MasterReferenceView,
    meta: {auth: true, title: 'View Master Reference'}
  },
  { name: 'MASTER_REFERENCE_EDIT',
    path: '/master-reference/edit/:id',
    component: MasterReferenceUpdate,
    meta: {auth: true, title: 'Edit Master Reference'}
  },
  { name: 'INVENTORY',
    path: '/inventory',
    component: InventoryList,
    meta: {auth: true, title: 'Manage Inventory'}
  },
  { name: 'INVENTORY_ADD',
    path: '/inventory/:menu',
    component: InventoryParent,
    meta: {auth: true, title: 'Add Inventory'}
  },
  { name: 'INVENTORY',
    path: '/inventory/:menu/:id',
    component: InventoryParent,
    meta: {auth: true, title: 'View Inventory'}
  },
  { name: 'INVENTORY_EDIT',
    path: '/inventory/do/:menu/:id',
    component: InventoryParent,
    meta: {auth: true, title: 'Edit Inventory'}
  },
  { name: 'VIEW_ORGANIZATION_STRUCTURE',
    path: '/organization-structure/view-all',
    component: OrganizationStructure,
    meta: {auth: true, title: 'View All Organization Structure'}
  },
  { name: 'ORGANIZATION_STRUCTURE',
    path: '/organization-structure',
    component: OrganizationStructureList,
    meta: {auth: true, title: 'Organization Structure'}
  },
  { name: 'ORGANIZATION_STRUCTURE_ADD',
    path: '/organization-structure/add',
    component: OrganizationStructureForm,
    meta: {auth: true, title: 'Add Organization Structure'}
  },
  { name: 'ORGANIZATION_STRUCTURE',
    path: '/organization-structure/view/:id',
    component: OrganizationStructureView,
    meta: {auth: true, title: 'View Organization Structure'}
  },
  { name: 'ORGANIZATION_STRUCTURE_EDIT',
    path: '/organization-structure/edit/:id',
    component: OrganizationStructureUpdate,
    meta: {auth: true, title: 'Edit Organization Structure'}
  },
  { name: 'PROJECT',
    path: '/project',
    component: ProjectList,
    meta: {auth: true, title: 'Manage Project'}
  },
  { name: 'PROJECT_ADD',
    path: '/project/add',
    component: ProjectParent,
    meta: {auth: true, title: 'Add Project'}
  },
  { name: 'PROJECT',
    path: '/project/v/:menu/:id',
    component: ProjectParentEdit,
    meta: {auth: true, title: 'View Project'}
  }, /*
  { name: 'PROJECT_EDIT',
    path: '/project/edit/:id',
    component: ProjectParentEdit,
    meta: {auth: true, title: 'Edit Project'}
  }, */
  { name: 'PROJECT_EDIT',
    path: '/projects/:menu/:id',
    component: ProjectParentEdit,
    meta: {auth: true, title: 'Edit Project'}
  },
  { name: 'INVENTORY_ASSIGNMENT',
    path: '/inventory-assignment',
    component: InventoryAssignmentList,
    meta: {auth: true, title: 'Manage Inventory Assignment'}
  },
  { name: 'INVENTORY_ASSIGNMENT_ADD',
    path: '/inventory-assignment/add',
    component: InventoryAssignmentForm,
    meta: {auth: true, title: 'Add Inventory Assignment'}
  },
  { name: 'INVENTORY_ASSIGNMENT_EDIT',
    path: '/inventory-assignment/edit/:id',
    component: InventoryAssignmentEdit,
    meta: {auth: true, title: 'Edit Inventory Assignment'}
  },
  { name: 'INVENTORY_ASSIGNMENT',
    path: '/inventory-assignment/view/:id',
    component: InventoryAssignmentView,
    meta: {auth: true, title: 'View Inventory Assignment'}
  },
  { name: 'PROJECT_CONTRACT',
    path: '/projectContract',
    component: ProjectContractList,
    meta: {auth: true, title: 'Manage Project Contract'}
  },
  { name: 'PROJECT_CONTRACT_ADD',
    path: '/projectContract/add',
    component: ProjectContractForm,
    meta: {auth: true, title: 'Add Project Contract'}
  },
  { name: 'PROJECT_CONTRACT',
    path: '/projectContract/view/:id',
    component: ProjectContractView,
    meta: {auth: true, title: 'View Project Contract'}
  },
  { name: 'PROJECT_CONTRACT_EDIT',
    path: '/projectContract/edit/:id',
    component: ProjectContractUpdate,
    meta: {auth: true, title: 'Edit Project Contract'}
  },
  { name: 'PROJECT_PAYMENT',
    path: '/projectPayment',
    component: ProjectPaymentList,
    meta: {auth: true, title: 'Manage Project Payment'}
  },
  { name: 'PROJECT_PAYMENT_ADD',
    path: '/projectPayment/add',
    component: ProjectPaymentForm,
    meta: {auth: true, title: 'Add Project Payment'}
  },
  { name: 'PROJECT_PAYMENT',
    path: '/projectPayment/view/:id',
    component: ProjectPaymentView,
    meta: {auth: true, title: 'View Project Payment'}
  },
  { name: 'PROJECT_PAYMENT_EDIT',
    path: '/projectPayment/edit/:id',
    component: ProjectPaymentUpdate,
    meta: {auth: true, title: 'Edit Project Payment'}
  },
  { path: '/sample/report',
    component: Report,
    meta: {auth: true, title: 'Sample Report'}
  },
  { name: 'EMPLOYEE_LOGGING',
    path: '/employee-logging',
    component: EmployeeLoggingList,
    meta: {auth: true, title: 'Manage Employee Logging'}
  },
  { name: 'EMPLOYEE_LOGGING',
    path: '/employee-logging/add',
    component: EmployeeLoggingForm,
    meta: {auth: true, title: 'Add Employee Logging'}
  },
  { name: 'EMPLOYEE_LOGGING',
    path: '/employee-logging/view/:id',
    component: EmployeeLoggingView,
    meta: {auth: true, title: 'View Employee Logging'}
  },
  { name: 'EMPLOYEE_LOGGING',
    path: '/employee-logging/edit/:id',
    component: EmployeeLoggingUpdate,
    meta: {auth: true, title: 'Edit Employee Logging'}
  },
  { name: 'EMPLOYEE_LOGGING_NON_AJ',
    path: '/employee-logging-non-aj',
    component: EmployeeLoggingNonAjList,
    meta: {auth: true, title: 'Manage Employee Logging Non AJ'}
  },
  { name: 'LOG_ACTIVITY_REPORTS',
    path: '/log-activity-details-report',
    component: LogActivityDetailsReport,
    meta: {auth: true, title: 'Log Activity Details Report'}
  },
  {
    name: 'TIMESHEETS_PER_EMPLOYEE',
    path: '/timesheets-per-employee-report',
    component: LogActivityDetailsNonAjReport,
    meta: {auth: true, title: 'Timesheets Report'}
  },
  {
    name: 'ASSIGNMENT_REPORT',
    path: '/assignment-report',
    component: AssignmentNonAjReport,
    meta: {auth: true, title: 'Assignment Report'}
  },
  { name: 'SUB_PROJECT',
    path: '/sub-project',
    component: SubProjectList,
    meta: {auth: true, title: 'Manage Sub Project'}
  },
  { name: 'SUB_PROJECT_ADD',
    path: '/sub-project/add',
    component: SubProjectForm,
    meta: {auth: true, title: 'Add Sub Project'}
  },
  { name: 'SUB_PROJECT',
    path: '/sub-project/view/:id',
    component: SubProjectView,
    meta: {auth: true, title: 'View Sub Project'}
  },
  { name: 'SUB_PROJECT_EDIT',
    path: '/sub-project/edit/:id',
    component: SubProjectEdit,
    meta: {auth: true, title: 'Edit Sub Project'}
  },
  { name: 'PROJECT_ASSIGNMENT',
    path: '/project-assignment',
    component: ProjectAssignmentList,
    meta: {auth: true, title: 'Manage Project Assignment'}
  },
  { name: 'PROJECT_ASSIGNMENT',
    path: '/project-assignment/view/:id',
    component: ProjectAssignmentView,
    meta: {auth: true, title: 'View Project Assignment'}
  },
  { name: 'PROJECT_ASSIGNMENT_EDIT',
    path: '/project-assignment/edit/:id',
    component: ProjectAssignmentUpdate,
    meta: {auth: true, title: 'Update Project Assignment'}
  },
  { name: 'PROJECT_ASSIGNMENT_ADD',
    path: '/project-assignment/add/:months/:years',
    component: ProjectAssignmentForm,
    meta: {auth: true, title: 'Add Project Assignment'}
  },
  { name: 'PROJECT_ASSIGNMENT_ADD',
    path: '/project-assignment/add/:employeeId/:employeeName/:months/:years',
    component: ProjectAssignmentForm,
    meta: {auth: true, title: 'Add Project Assignment'}
  },
  { name: 'LEVEL_RATE',
    path: '/level-rate',
    component: LevelRateList,
    meta: {auth: true, title: 'Manage Level Rate'}
  },
  { name: 'LEVEL_RATE_ADD',
    path: '/level-rate/add',
    component: LevelRateForm,
    meta: {auth: true, title: 'Add Level Rate'}
  },
  { name: 'LEVEL_RATE_EDIT',
    path: '/level-rate/edit/:id',
    component: LevelRateUpdate,
    meta: {auth: true, title: 'Update Level Rate'}
  },
  { name: 'LEVEL_RATE',
    path: '/level-rate/view/:id',
    component: LevelRateView,
    meta: {auth: true, title: 'View Level Rate'}
  },
  { name: 'LIST_REPORT_EMPLOYEE_LOGGING',
    path: '/log-activity-report',
    component: LogActivityReportList,
    meta: {auth: true, title: 'List of Report Employee Logging'}
  },
  { name: 'LIST_RECAP_REPORT_EMPLOYEE_LOGGING',
    path: '/recap-log-activity-report',
    component: RecapLogActivityReportList,
    meta: {auth: true, title: 'List of Recap Report Employee Logging'}
  },
  { name: 'LIST_RECAP_REPORT_INVOICE',
    path: '/recap-invoice-report',
    component: RecapInvoiceReportList,
    meta: {auth: true, title: 'List of Recap Report Invoice'}
  },
  { name: 'INVOICE_REPORTS',
    path: '/invoice-report',
    component: InvoiceReport,
    meta: {auth: true, title: 'Invoice Report'}
  },
  { name: 'RECAP_REPORTS',
    path: '/recap-report',
    component: RecapReport,
    meta: {auth: true, title: 'Recap Report'}
  },
  { name: 'LIST_INVOICE_REPORT',
    path: '/invoice-report-list',
    component: InvoiceReportList,
    meta: {auth: true, title: 'List of Invoice Reports'}
  },
  { name: 'USER',
    path: '/user',
    component: UserList,
    meta: {auth: true, title: 'Manage User'}
  },
  { name: 'USER_ADD',
    path: '/user/add',
    component: UserForm,
    meta: {auth: true, title: 'Add User'}
  },
  { name: 'USER_EDIT',
    path: '/user/edit/:id',
    component: UserEdit,
    meta: {auth: true, title: 'Edit User'}
  },
  { name: 'USER_ROLE',
    path: '/user-role',
    component: UserRoleList,
    meta: {auth: true, title: 'Manage User Role'}
  },
  { name: 'USER_ROLE_ADD',
    path: '/user-role/add',
    component: UserRoleForm,
    meta: {auth: true, title: 'Add User Role'}
  },
  { name: 'USER_ROLE',
    path: '/user-role/view/:id',
    component: UserRoleView,
    meta: {auth: true, title: 'View User Role'}
  },
  { name: 'USER_ROLE_EDIT',
    path: '/user-role/edit/:id',
    component: UserRoleUpdate,
    meta: {auth: true, title: 'Edit User Role'}
  },
  { name: 'ROLE',
    path: '/role',
    component: RoleList,
    meta: {auth: true, title: 'Manage Role'}
  },
  { name: 'ROLE_ADD',
    path: '/role/add',
    component: RoleForm,
    meta: {auth: true, title: 'Add Role'}
  },
  { name: 'ROLE_EDIT',
    path: '/role/edit/:id',
    component: RoleEdit,
    meta: {auth: true, title: 'Edit Role'}
  },
  { name: 'ROLE_MENU',
    path: '/role-menu',
    component: RoleMenuList,
    meta: {auth: true, title: 'Manage Role Menu'}
  },
  { name: 'ROLE_MENU_ADD',
    path: '/role-menu/add',
    component: RoleMenuForm,
    meta: {auth: true, title: 'Add Role Menu'}
  },
  { name: 'ROLE_MENU',
    path: '/role-menu/view/:id',
    component: RoleMenuView,
    meta: {auth: true, title: 'View Role Menu'}
  },
  { name: 'ROLE_MENU_EDIT',
    path: '/role-menu/edit/:id',
    component: RoleMenuUpdate,
    meta: {auth: true, title: 'Edit Role Menu'}
  },
  { name: 'ADD_MENU',
    path: '/menu',
    component: MenuList,
    meta: {auth: true, title: 'Manage Menu'}
  },
  { name: 'ADD_MENU_ADD',
    path: '/menu/add',
    component: MenuForm,
    meta: {auth: true, title: 'Add Menu'}
  },
  { name: 'ADD_MENU_EDIT',
    path: '/menu/edit/:id',
    component: MenuEdit,
    meta: {auth: true, title: 'Edit Menu'}
  },
  { name: 'CHANGE_PASSWORD',
    path: '/change-password',
    component: ChangePassword,
    meta: {auth: true, title: 'Change Password'}
  },
  { name: 'FORBIDDEN',
    path: '/forbidden',
    component: Forbidden,
    meta: {auth: true, title: 'Forbidden Access'}
  },
  { name: 'ATTENDANCE_LIST',
    path: '/attendance-list',
    component: EmployeeAttendanceList,
    meta: {auth: true, title: 'Manage Employee Attendance'}
  },
  { name: 'PETTY_CASH',
    path: '/petty-cash',
    component: PettyCashList,
    meta: {auth: true, title: 'Manage Petty Cash'}
  },
  { name: 'PETTY_CASH_ADD',
    path: '/petty-cash/add',
    component: PettyCashForm,
    meta: {auth: true, title: 'Add Petty Cash'}
  },
  { name: 'PETTY_CASH',
    path: '/petty-cash/view/:id',
    component: PettyCashView,
    meta: {auth: true, title: 'View Petty Cash'}
  },
  { name: 'PETTY_CASH_VERIFY',
    path: '/petty-cash/verify/:id',
    component: PettyCashVerify,
    meta: {auth: true, title: 'Verify Petty Cash'}
  },
  { name: 'PETTY_CASH_CONFIRM',
    path: '/petty-cash/confirm/:id',
    component: PettyCashConfirm,
    meta: {auth: true, title: 'Confirm Petty Cash'}
  },
  { name: 'PETTY_CASH_GUEST_ADD',
    path: '/petty-cash/guest/add',
    component: PettyCashGuestForm,
    meta: {title: 'Add Petty Cash'}
  },
  {
    name: 'BUAT_SURAT',
    path: '/surat',
    component: SuratAwal,
    meta: {auth: true, title: 'Create Letter Number'}
  },
  {
    name: 'LIST_SURAT',
    path: '/view-surat',
    component: ViewSurat,
    meta: {auth: true, title: 'List Of Letter Number'}
  },
  {
    name: 'MOBILE_APP',
    path: '/mobile',
    beforeEnter () { location.href = 'http://essmobile.pusilkom.com' }
  },
  {
    name: 'LEAVE_APPROVAL',
    path: '/leave-approval',
    component: LeaveApprovalList,
    meta: {auth: true, title: 'List of Leave Approval'}
  },
  { name: 'LEAVE_REQUEST',
    path: '/leave-request',
    component: LeaveRequestList,
    meta: {auth: true, title: 'Leave Request'}
  },
  { name: 'LEAVE_REQUEST_ADD',
    path: '/leave-request/add',
    component: LeaveRequestForm,
    meta: {auth: true, title: 'Add Leave Request'}
  },
  { name: 'LEAVE_REQUEST_EDIT',
    path: '/leave-request/edit/:id',
    component: LeaveRequestForm,
    meta: {auth: true, title: 'Edit Leave Request'}
  },
  { name: 'CERTIFICATE_LIST',
    path: '/certificate',
    component: CertificateList,
    meta: {auth: true, title: 'Certificate List'}
  },
  { name: 'GENERATE_CERTIFICATE_NUMBER',
    path: '/certificate-generator',
    component: GenerateCertificateNumber,
    meta: {auth: true, title: 'Generate Certificate Number'}
  },
  { name: 'LOG_ACTIVITY_VIEW',
    path: '/log-activity-view',
    component: LogActivityView,
    meta: {auth: true, title: 'View Log Activity'}
  },
  { name: 'FUNDING_SOURCE',
    path: '/funding-source',
    component: FundingSourceList,
    meta: {auth: true, title: 'Manage Funding Source'}
  },
  { name: 'FUNDING_SOURCE_ADD',
    path: '/funding-source/add',
    component: FundingSourceForm,
    meta: {auth: true, title: 'Add Funding Source'}
  },
  { name: 'FUNDING_SOURCE_EDIT',
    path: '/funding-source/edit/:id',
    component: FundingSourceEdit,
    meta: {auth: true, title: 'Edit Funding Source'}
  },
  { name: 'TIMESHEET_SUMMARY_REPORT',
    path: '/timesheet-summary-report',
    component: TimesheetSummaryReport,
    meta: {auth: true, title: 'Timesheet Summary Report'}
  },
  { name: 'PROJECT_ASSIGNMENT_REPORT',
    path: '/project-assignment-report',
    component: ProjectAssignmentReport,
    meta: {auth: true, title: 'Project Assignment Report'}
  },
  {path: '*', redirect: '/home'}
]

let router = new VueRouter(
  { routes: mapRouter
  }
)

router.beforeEach((to, from, next) => {
  console.log('Router BEFORE EACH : To ', to)
  document.body.scrollTop = 0
  // let auth = injector.get('auth')

  // let jwt = auth.getJwt()
  // console.log('$$$ JWT : ', jwt)

  // bus.$emit('CLOSE_NOTIFICATION')
  if (to.matched.some(record => record.meta.auth)) {
    // this route requires auth, check if logged in
    // if not, redirect to login page.
    auth.checkAuth()

    if (!auth.isAuthenticated) {
      next('/login')
    } else if (!auth.hasAccess(to.name)) {
      next({name: 'FORBIDDEN'})
    } else {
      next()
    }
  } else {
    next() // make sure to always call next()!
  }
})

// router.afterEach(route => {
//   document.title = 'EUIS-NG ' + (route.meta.title ? ' - ' + route.meta.title : '')
// })

router.afterEach(route => {
  console.log('Router AFTER EACH')
})

// module.exports = router

export { router as default, mapRouter }
