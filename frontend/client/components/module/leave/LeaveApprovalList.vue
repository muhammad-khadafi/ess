<template>


  <div class="card-content">

    <div class="columns">
      <div class="column">

          <div class="control is-horizontal">
            <div class="control-label">
              <div class="column is-full">
                  <label class="label">Employee Name</label>
              </div>
              </div>
            <div class="control is-grouped">
              <div class="control is-expanded">
                <div class="column is-full">
                <select class="select" v-model="form.employeeId">
                  <option v-for="option in listOfEmployee" :value="option.value">
                    {{ option.text }}
                </option>
                </select>
                </div>
              </div>
            </div>
          </div>

          <div class="control is-horizontal">
            <div class="control-label">
              <div class="column is-full">
                <label class="label">Leave Type</label>
              </div>
            </div>
            <div class="control is-grouped">
              <div class="control is-expanded">
                <div class="column is-half">
                  <select class="select" v-model="form.leaveTypeId">
                  <option v-for="option in listOfLeaveType" :value="option.value">
                    {{ option.text }}
                </option>
                </select>
                </div>
              </div>
            </div>
          </div>

          <div class="control is-horizontal">
            <div class="control-label">
              <div class="column is-full">
                <label class="label">Start Date</label>
              </div>
            </div>
            <div class="control is-grouped">
              <div class="control is-expanded">
                <div class="column is-2">
                  <datepicker input-class="input" format="dd-MM-yyyy" v-model="form.startDateStr"
                          placeholder="Start Date"
                          :clear-button="true">
              </datepicker>
                </div>
              </div>
            </div>
          </div>

          <div class="control is-horizontal">
            <div class="control-label">
              <div class="column is-full">
                <label class="label">End Date</label>
              </div>
            </div>
            <div class="control is-grouped">
              <div class="control is-expanded">
                <div class="column is-2">
                  <datepicker input-class="input" format="dd-MM-yyyy" v-model="form.endDateStr"
                          placeholder="End Date"
                          :clear-button="true">
              </datepicker>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

      <div class="columns">
        <div class="column is-half is-offset-one-quarter has-text-centered">
            <button class="button is-info" @click="search()">
                  <span class="icon is-small">
                    <i class="fa fa-search"></i>
                  </span>
              <span>Search</span>
            </button>
        </div>
      </div>


    <div>
      <vuetable ref="vuetable"
                api-url="/leave-approval/table"
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
                @vuetable:loaded="hideLoader"
      />
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
  </div><!-- content -->

    <div class="columns">
      <div class="column is-half is-offset-one-quarter has-text-centered">
        <button class="button is-info" @click="addNew()">
                  <span class="icon is-small">
                    <i class="fa fa-plus"></i>
                  </span>
          <span>Add New</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Vue from 'vue'
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import LeaveApprovalOperation from './LeaveApprovalOperation.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'
import Datepicker from 'vuejs-datepicker'

Vue.component('leaveApprovalOperation', LeaveApprovalOperation)

let tableColumns = [
  {
    name: '__sequence',
    title: 'No.',
    titleClass: 'center aligned',
    dataClass: 'right aligned'
  },
  {
    name: 'employeeName',
    sortField: 'employeeName',
    title: 'Name',
    titleClass: 'center aligned'
  },
  {
    name: 'leaveTypeName',
    sortField: 'leaveTypeName',
    title: 'Leave Type',
    titleClass: 'center aligned'
  },
  {
    name: 'startDateStr',
    sortField: 'startDateStr',
    title: 'Start Date',
    titleClass: 'center aligned'
  },
  {
    name: 'endDateStr',
    sortField: 'endDateStr',
    title: 'End Date',
    titleClass: 'center aligned'
  },
  {
    name: 'numOfDays',
    sortField: 'numOfDays',
    title: 'Number of Days',
    titleClass: 'center aligned'
  },
  {
    name: 'reason',
    sortField: 'reason',
    title: 'Reason',
    titleClass: 'center aligned'
  },
  {
    name: 'status',
    sortField: 'status',
    title: 'Status',
    titleClass: 'center aligned'
  },
  {
    title: 'Action',
    titleClass: 'center aligned',
    name: '__component:leave-approval-operation',
    dataClass: 'center aligned'
  }
]

export default {
  components: {
    Vuetable,
    VuetablePagination,
    VuetablePaginationDropdown,
    VuetablePaginationInfo,
    Datepicker
  },
  created () {
    this.fetchData()
  },
  mounted () {
    let thisVM = this
    bus.$on('RELOAD_APPROVAL_LIST', function () {
      if (thisVM.$refs.vuetable !== undefined) {
        thisVM.$refs.vuetable.reload()
      }
    })
  },
  data () {
    return {
      form: {
        employeeId: null,
        leaveTypeId: null,
        startDateStr: null,
        endDateStr: null
      },
      listReferenceType: [
        { value: null, text: '- Select Reference Type -' },
        { value: 'Benefit', text: 'Benefit' },
        { value: 'Skillset', text: 'Skillset' },
        { value: 'Supervisor', text: 'Supervisor' },
        { value: 'Document', text: 'Document' },
        { value: 'Project Status', text: 'Project Status' },
        { value: 'Project Role', text: 'Project Role' },
        { value: 'Inventory Type', text: 'Inventory Type' }
      ],
      listOfLeaveType: [],
      listOfEmployee: [],
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'employee_name',
        direction: 'asc'
      }],
      multiSort: true,
      perPage: 10,
      paginationInfoTemplate: '{from} - {to} dari {total} data',
      paginationInfoNoDataTemplate: ''
    }
  },
  methods: {
    fetchData () {
      console.log('START FETCH')
      axios.all([
        axios.get('/leave-type/all'),
        axios.get('/employee/all')
      ]).then(axios.spread((leaveTypeList, employeeList) => {
        this.listOfLeaveType.push({value: null, text: '-- Select Leave Type--'})
        this.listOfEmployee.push({value: null, text: '-- Select Employee--'})
        for (let leaveType of leaveTypeList.data) {
          this.listOfLeaveType.push({value: leaveType.id, text: leaveType.leaveTypeName})
        }
        for (let employee of employeeList.data) {
          this.listOfEmployee.push({value: employee.id, text: employee.name})
        }
      }))
    },
    search () {
      this.errors = {}
      this.$refs.vuetable.refresh()
    },
    showLoader () {
      console.log('on LOADING')
      bus.$emit('SHOW_LOADING')
      console.log('DEBUG!')
    },
    hideLoader () {
      console.log('on LOADED')
      bus.$emit('HIDE_LOADING')
      console.log('DEBUG!')
    },
    formatFlagAktif (value) {
      return value === true ? 'Aktif' : 'Tidak Aktif'
    },
    onLoadSuccess (response) {
      console.log('on LOAD SUCCESS')
      console.log(response)
      // set pagination data to pagination-info component
      this.$refs.paginationInfo.setPaginationData(response.data)
      console.log('DEBUG!')
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
      this.$router.replace('/master-reference/add')
    },
    refreshTable () {
      this.$refs.vuetable.refresh()
    }
  }
}
</script>
