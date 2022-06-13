<template>
  <div>
    <div class="box">

      <div class="columns">
        <div class="column">
          <div class="columns is-mobile">
            <div class="column is-2">
              <div class="level-right">
                <label class="label">Periode<font color="red">*</font></label>
              </div>
            </div>
            <div class="column is-2">
              <datepicker v-model="form.startDateStr" input-class="input" format="dd-MM-yyyy"
                          placeholder="select date"
                          :clear-button="clearButton">
              </datepicker>
            </div>
            <div class="column is-2">
              <datepicker v-model="form.endDateStr" input-class="input" format="dd-MM-yyyy"
                          placeholder="select date"
                          :clear-button="clearButton">
              </datepicker>
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
         <div class="control"> 
            <button class="button is-info" @click="generate()">
              <span class="icon is-small">
                <i class="fa fa-check"></i>
              </span>
              <span>View Report</span>
            </button>
          </div>

        </div>
      </div>

      <hr>
      <div>
        <vuetable ref="vuetable"
                  api-url="/timesheet-summary/table"
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
    </div>


    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import ValidationMixin from '../../mixin/ValidationMixin'
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
  import TimesheetSummaryReportAction from './TimesheetSummaryReportAction.vue'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  
  Vue.component('timesheetSummaryReportAction', TimesheetSummaryReportAction)

  let tableColumns = [
    {
      name: 'employeeName',
      sortField: 'employeeName',
      title: 'Employee Name',
      titleClass: 'left aligned'
    },
    {
      name: 'totalDay',
      sortField: 'totalDay',
      title: 'Total Day',
      titleClass: 'left aligned'
    },
    {
      title: 'Detail',
      titleClass: 'center aligned',
      name: '__component:timesheet-summary-report-action',
      dataClass: 'center aligned'
    }
  ]
  export default {
    components: {
      BasicSelect,
      Datepicker,
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo,
      TimesheetSummaryReportAction
    },
    props: {
      id: {
        type: Number
      }
    },
    mixins: [ValidationMixin],
    data () {
      return {
        clearButton: true,
        form: {
          startDate: null,
          endDate: null
        },
        errors: {},
        subMenuName: 'log-activity-details-report',
        fields: tableColumns,
        sortOrder: [{
          field: 'employeeName',
          direction: 'asc'
        }],
        multiSort: true,
        perPage: 1000,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: ''
      }
    },
    methods: {
      generate () {
        this.$refs.vuetable.refresh()
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
      onLoadSuccess (response) {
        console.log('on LOAD SUCCESS')
        console.log(response)
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
      showLoader () {
        console.log('on LOADING')
        bus.$emit('SHOW_LOADING')
      },
      hideLoader () {
        console.log('on LOADED')
        bus.$emit('HIDE_LOADING')
      }
    }
  }
</script>
