<template>
  <div class="card-content">
    <br/>
    <br/>
    <br/>
    <div v-if="roleId == 1" class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Employee Name</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="employeeList"
                      :selected-option="employeeItem"
                      placeholder="Please Select"
                      @select="onEmployeeSelect">
        </basic-select>
      </div>
    </div>

    <br/>
    <br/>
    <br/>

<!--    {{form}}-->

    <div class="columns">
      <div class="column is-fifth">
      </div>
      <div class="column is-fifth">
        <button class="button is-info btn-nav" @click="prevMonth()">
          <span>Previous Month</span>
        </button>
      </div>
      <div class="column is-fifth">
        <h1 class="has-text-centered"><b>{{currentMonth}}</b></h1>
      </div>
      <div class="column is-fifth has-text-right">
        <button class="button is-info btn-nav" @click="nextMonth()">
          <span>Next Month</span>
        </button>
      </div>
      <div class="column is-fifth">
      </div>
    </div>

    <div>

      <div class="columns">
        <!-- <div class="column is-1"></div> -->
        <div class="column is-6">
            <nav class="panel">
              <p class="panel-heading">
                <label class="label">Weekly Working Hours</label>
              </p>
              <label class="panel-block" v-for="workHour in weeklyWorkingHour" :key="workHour.durationMin">
                Week {{workHour.weekNumber}} : {{workHour.duratinStr}}
              </label>
              <!-- <label class="panel-block">
                Week 1 : 0 Hours
              </label>
              <label class="panel-block">
                Week 2 : 38 Hours 48 Minutes
              </label>
              <label class="panel-block">
                Week 3 : 0 Hours
              </label>
              <label class="panel-block">
                Week 4 : 0 Hours
              </label>
              <label class="panel-block">
                Week 5 : 0 Hours
              </label> -->
            </nav>
        </div>
        <div class="column is-6">
            <nav class="panel">
              <p class="panel-heading">
                <label class="label">Summary</label>
              </p>
              <label class="panel-block">
                Total Working Day : {{totalWork}}
              </label>
              <label class="panel-block">
                Total Working Hours : {{totalDuration}}
              </label>
              <label class="panel-block">
                Average Daily Working Hour : {{avgWorkHour}}
              </label>
            </nav>
        </div>
      </div>
     
      <vuetable ref="vuetable"
                api-url="/employee-attendance/table"
                :fields="fields"
                pagination-path="pagination"
                :sort-order="sortOrder"
                :multi-sort="multiSort"
                :per-page="perPage"
                :append-params="form"
                @vuetable:pagination-data="onPaginationData"
                @vuetable:load-success="onLoadSuccess"
                @vuetable:load-error="onLoadError"
                @vuetable:loading="showLoader"
                @vuetable:loaded="hideLoader">
        <template slot="actions" scope="props">
          <div class="columns is-gapless">
            <div class="column is-9">
              <div class="level-right">
                <textarea class="textarea" type="text"  style="width:50px;"
                v-model="props.rowData.activityDetail" />
                <!-- <button class="button is-info" @click="editAttendance(props.rowData)">
                  <i class="fa fa-pencil"/>
                </button> -->
              </div>
            </div>
            <div class="column is-4">
              <button style="margin:25px 0 0 10px;" class="button is-info" @click="editAttendance(props.rowData)">
                <span>
                  <i class="fa fa-pencil"></i>
                </span>
                <span>Edit</span>                
              </button>
            </div>
          </div>
        </template>
      </vuetable>
      <nav class="columns">
        <div class="column">
          <vuetable-pagination-info ref="paginationInfo"
                                    :pagination-info-template="paginationInfoTemplate"
                                    :pagination-info-no-data-template="paginationInfoNoDataTemplate"/>
        </div>
        <div class="column">
          <vuetable-pagination ref="pagination" @vuetable-pagination:change-page="onChangePage"/>
        </div>
      </nav>
      <modal :visible="showEditModal" @close="closeEditModal" transition="zoom">
        <employee-attendance-edit :cmd="editCmd"  @close="closeEditModal"></employee-attendance-edit>
      </modal>
    </div><!-- content -->


    <!--<div class="columns">
      <div class="column is-half is-offset-one-quarter has-text-centered">
        <button class="button is-info" @click="addNew()">
                  <span class="icon is-small">
                    <i class="fa fa-plus"></i>
                  </span>
          <span>Add New</span>
        </button>
      </div>
    </div>-->
  </div>
</template>

<script>
  import axios from 'axios'
  import auth from 'src/shared/auth'
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { BasicSelect } from 'vue-search-select'
  import ProjectContractUpdate from '../project/projectContract/ProjectContractUpdate'
  import { Modal } from 'vue-bulma-modal'
  import EmployeeAttendanceEdit from './employeeAttendanceEdit'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'dateString',
      sortField: 'date',
      title: 'Date',
      titleClass: 'center aligned',
      width: '10%'
    },
    {
      name: 'checkInString',
      sortField: 'projectName',
      title: 'Clock In',
      titleClass: 'center aligned',
      width: '26%'
    },
    {
      name: 'checkOutString',
      sortField: 'roleName',
      title: 'Clock Out',
      titleClass: 'center aligned',
      width: '26%'
    },
    {
      name: 'durationString',
      sortField: 'duration',
      title: 'Duration',
      titleClass: 'center aligned',
      dataClass: 'center aligned',
      width: '8%'
    },
    /* {
      name: 'activityDetail',
      callback: 'blankText',
      sortField: 'activityDetail',
      title: 'Activity Detail',
      titleClass: 'center aligned'
    }, */
    {
      title: 'Activity Detail',
      titleClass: 'center aligned',
      name: '__slot:actions',
      width: '30%'
    }
  ]

  export default {
    components: {
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo,
      BasicSelect,
      ProjectContractUpdate,
      Modal,
      EmployeeAttendanceEdit
    },
    mounted () {
      console.log('Institusi List MOUNTED')
      let thisVM = this
      bus.$on('RELOAD_INSTITUSI_LIST', function () {
        console.log('Institusi List RELOAD_INSTITUSI_LIST', thisVM.$refs)
        console.log('Institusi List RELOAD_INSTITUSI_LIST is kosong : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    data () {
      return {
        form: {
          employeeId: null,
          projectId: null,
          roleId: null,
          levelId: null,
          dates: null,
          months: null,
          years: null,
          weeksofMonth: 0
        },
        cmd: {
          id: null,
          activityDetail: null
        },
        roleId: null,
        totalDuration: null,
        totalWork: null,
        avgWorkHour: null,
        auth: auth,
        errors: {},
        fields: tableColumns,
        sortOrder: [{
          field: 'date',
          direction: 'desc'
        }],
        multiSort: true,
        perPage: 40,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
        currDates: null,
        currMonths: null,
        currYears: null,
        employeeList: [],
        employeeItem: {
          value: '',
          text: ''
        },
        roleList: [],
        levelList: [],
        tempDate: new Date(),
        currentMonth: null,
        showEditModal: false,
        editCmd: {},
        weeklyWorkingHour: []
      }
    },
    created () {
      this.setTodayDate()
      this.setParamsDate()
      this.roleId = localStorage.getItem('id_role')
      this.form.employeeId = this.roleId === '1' ? 0 : auth.getUserInfo().employeeId
      this.fetchData()
      this.getSummary()
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/employee/all')
        ]).then(axios.spread((employeeList, resp) => {
          this.employeeList.push({value: 0, text: '-- Please Select --'})
          for (let employee of employeeList.data) {
            this.employeeList.push({value: employee.id, text: employee.name})
          }
        }))
      },
      getSummary () {
        axios.get('/employee-attendance/getsummary/' + this.form.employeeId + '/' + this.form.dates + '/' + this.form.months +
            '/' + this.form.years + '/' + this.form.weeksofMonth)
          .then((resp) => {
            this.totalDuration = resp.data.workingMonthString
            this.totalWork = resp.data.totalWork
            this.avgWorkHour = resp.data.averageDailyWorkingHourString
            this.weeklyWorkingHour = resp.data.weeklyWorkingHour
          })
      },
      search () {
        this.errors = {}
        this.$refs.vuetable.refresh()
      },
      showLoader () {
        console.log('on LOADING')
        bus.$emit('SHOW_LOADING')
      },
      hideLoader () {
        console.log('on LOADED')
        bus.$emit('HIDE_LOADING')
      },
      formatFlagAktif (value) {
        return value === true ? 'Aktif' : 'Tidak Aktif'
      },
      closeEditModal () {
        this.showEditModal = false
      },
      onLoadSuccess (response) {
        console.log('on LOAD SUCCESS')
        console.log(response)
        // set pagination data to pagination-info component
        this.$refs.paginationInfo.setPaginationData(response.data)
      },
      onLoadError (response) {
        let thisVM = this

        console.log('on LOAD ERROR : ', response)
        if (response.status === 400) {
          bus.$emit('NOTIFY', 'danger', 'Gagal load data')
        } else if (response.status === 422) {
          console.log(response)

          _.each(response.data.errors, function (value) {
            thisVM.$set(thisVM.errors, value.field, value.message)
          })
        }

        this.$refs.vuetable.tableData = null
        this.$refs.paginationInfo.setPaginationData(null)
        this.$refs.pagination.setPaginationData(null)
      },
      onPaginationData (tablePagination) {
        console.log('on PAGINATION DATA')
        console.log(tablePagination)
        this.$refs.paginationInfo.setPaginationData(tablePagination)
        this.$refs.pagination.setPaginationData(tablePagination)
      },
      onChangePage (page) {
        console.log('on CHANGE PAGE')
        this.$refs.vuetable.changePage(page)
      },
      onClick (action, data) {
        console.log('actions: on-click PARENT', data.name)
      },
      addNew () {
        this.$router.replace('/project-assignment/add/' + this.form.months + '/' + this.form.years)
      },
      onProjectSelect (item) {
        this.errorSelect = false
        this.projectItem = item
        this.form.projectId = item.value
      },
      onEmployeeSelect (item) {
        this.errorSelect = false
        this.employeeItem = item
        this.form.employeeId = item.value
        this.$refs.vuetable.refresh()
        this.getSummary()
      },
      onOrganizationSelect (item) {
        this.errorSelect = false
        this.organizationItem = item
        this.form.organizationId = item.value
      },
      setTodayDate () {
        var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
        this.currentMonth = months[this.tempDate.getMonth()] + ' ' + this.tempDate.getFullYear()
      },
      setParamsDate () {
        this.currDates = this.form.dates = this.tempDate.getDate()
        this.currMonths = this.form.months = this.tempDate.getMonth() + 1
        this.currYears = this.form.years = this.tempDate.getFullYear()
      },
      nextMonth () {
        var d = this.tempDate
        this.tempDate.setMonth(d.getMonth() + 1)
        this.form.months = this.tempDate.getMonth() + 1
        this.form.years = this.tempDate.getFullYear()
        if (this.currMonths === this.form.months && this.currYears === this.form.years) {
          this.form.dates = this.currDates
          this.form.weeksofMonth = 0
        } else {
          this.form.dates = 28
          this.form.weeksofMonth = 4
        }
        this.setTodayDate()
        this.$refs.vuetable.refresh()
        this.getSummary()
      },
      prevMonth () {
        var d = this.tempDate
        this.tempDate.setMonth(d.getMonth() - 1)
        this.form.months = this.tempDate.getMonth() + 1
        this.form.years = this.tempDate.getFullYear()
        if (this.currMonths === this.form.months && this.currYears === this.form.years) {
          this.form.dates = this.currDates
          this.form.weeksofMonth = 0
        } else {
          this.form.dates = 28
          this.form.weeksofMonth = 4
        }
        this.setTodayDate()
        this.$refs.vuetable.refresh()
        this.getSummary()
      },
      blankText (value) {
        if (value === null || value === '') {
          return '<i class="red">not assign yet</i>'
        }
        return value
      },
      editAttendance (item) {
        console.log('CEK ISI ITEM : ', item)
        this.cmd.id = item.id
        this.cmd.activityDetail = item.activityDetail
        axios.put('/employee-attendance/' + item.id, this.cmd)
          .then((resp) => {
            this.$emit('close')
            bus.$emit('NOTIFY', 'success', 'Data has been updated')
            bus.$emit('RELOAD_CONTRACT_LIST')
          })
          .catch((error) => {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log('isi dari error', error.response)
            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })
          })
      }
    }
  }
</script>
