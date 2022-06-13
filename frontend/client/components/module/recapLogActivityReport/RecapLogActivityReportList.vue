<template>
  <div>

  <div class="box">

    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Employee Name</label>
        </div>
      </div>
      <div class="column is-one-fifth">
        <div class="control is-grouped level-left">
          <basic-select :options="employeeList"
                        :selected-option="employeeItem"
                        placeholder="Employee Name"
                        @select="onEmployeeSelect"
                        :class="{'is-danger' : errors.employeeId}">
          </basic-select>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.employeeId">
          <span class="help is-danger">{{ errors.employeeId }}</span>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Sub Project</label>
        </div>
      </div>
      <div class="column is-one-fifth">
        <div class="control is-grouped level-left">
          <basic-select :options="subProjectList"
                        :selected-option="subProjectItem"
                        placeholder="Sub Project Name"
                        @select="onSubProjectSelect"
                        :class="{'is-danger' : errors.subProjectId}">
          </basic-select>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.employeeId">
          <span class="help is-danger">{{ errors.employeeId }}</span>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column">
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Periode<font color="red">*</label>
            </div>
          </div>
          <div class="column is-2">
            <datepicker input-class="input" format="dd-MM-yyyy"
                        placeholder="select date"
                        @selected="selectedTanggal($event)">
            </datepicker>
          </div>
          <div class="column is-2">
            <datepicker input-class="input" format="dd-MM-yyyy"
                        placeholder="select date"
                        @selected="selectedTanggalEndDate($event)">
            </datepicker>
          </div>
        </div>
      </div>
    </div>
    <!--{{form}}-->

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
                api-url="/recap-log-activity-report/table"
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
  </div>
  </div>
</template>

<script>
import Vue from 'vue'
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import RecapLogActivityReportOperation from './RecapLogActivityReportOperation.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'
import axios from 'axios'
import { BasicSelect } from 'vue-search-select'
import Datepicker from 'vuejs-datepicker'

Vue.component('RecapLogActivityReportOperation', RecapLogActivityReportOperation)

let tableColumns = [
  {
    name: '__sequence',
    title: 'No.',
    titleClass: 'center aligned',
    dataClass: 'right aligned'
  },
  {
    name: 'name',
    sortField: 'name',
    title: 'Employee Name',
    titleClass: 'center aligned'
  },
  {
    name: 'subProjectName',
    sortField: 'subProjectName',
    title: 'Sub Project Code',
    titleClass: 'center aligned'
  },
  {
    name: 'startDateString',
    sortField: 'startDate',
    title: 'Start Date',
    titleClass: 'center aligned'
  },
  {
    name: 'endDateString',
    sortField: 'endDate',
    title: 'End Date',
    titleClass: 'center aligned'
  },
  {
    title: 'Action',
    titleClass: 'center aligned',
    name: '__component:recap-log-activity-report-operation',
    dataClass: 'center aligned'
  }
]

export default {
  components: {
    Vuetable,
    VuetablePagination,
    VuetablePaginationDropdown,
    VuetablePaginationInfo,
    BasicSelect,
    Datepicker
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
        idEmployee: 0,
        organizationId: null,
        fundingType: null,
        startDateString: null,
        endDateString: null,
        subProjectId: null
      },
      employeeList: [],
      employeeItem: {
        value: '',
        text: ''
      },
      subProjectList: [],
      subProjectItem: {
        value: '',
        text: ''
      },
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'rlar.id',
        direction: 'desc'
      }],
      multiSort: true,
      perPage: 10,
      paginationInfoTemplate: '{from} - {to} dari {total} data',
      paginationInfoNoDataTemplate: '',
      subMenuName: 'log-activity-report'
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      console.log('START FETCH')
      axios.all([
        axios.get('/project-assignment/getAll/3/' + this.subMenuName),
        axios.get('/invoice-report/getSubProject')
      ]).then(axios.spread((employeeList, subProjectList) => {
        for (let employee of employeeList.data) {
          this.employeeList.push({value: employee.id, text: employee.employeeName, idEmployee: employee.employeeId})
        }
        for (let subProject of subProjectList.data) {
          this.subProjectList.push({value: subProject.id, text: subProject.subProjectName})
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
    },
    hideLoader () {
      console.log('on LOADED')
      bus.$emit('HIDE_LOADING')
    },
    formatFlagAktif (value) {
      return value === true ? 'Aktif' : 'Tidak Aktif'
    },
    onSubProjectSelect (item) {
      this.errorSelect = false
      this.subProjectItem = item
      this.form.subProjectId = item.value
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
    onEmployeeSelect (item) {
      this.errorSelect = false
      this.employeeItem = item
      this.form.idEmployee = item.idEmployee
    },
    selectedTanggal (event) {
      if (event !== null) {
        this.form.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    selectedTanggalEndDate (event) {
      if (event !== null) {
        this.form.endDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    }
  }
}
</script>
