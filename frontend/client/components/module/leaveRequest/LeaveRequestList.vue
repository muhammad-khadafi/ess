<template>
  <div class="card-content">
    <div class="columns">
      <div class="column">
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Leave Code</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <span class="select">
                <select v-model="form.leaveTypeId">
                  <option v-for="option in leaveTypeList" :value="option.value">
                    {{ option.text }}
                  </option>
                </select>
              </span>
            </div>
          </div>
        </div>
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Status</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <span class="select">
                <select v-model="form.status">
                  <option v-for="option in statusList" :value="option.value">
                    {{ option.text }}
                  </option>
                </select>
              </span>
            </div>
          </div>
        </div>
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Start Date</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <datepicker input-class="input" format="dd-MM-yyyy"
                      placeholder="Start Date"
                      @selected="selectedTanggalStartDate($event)"
                      @cleared="clearedTanggalStartDate($event)"
                      :clear-button="clearButton"></datepicker>
            </div>
          </div>
        </div>
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">End Date</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <datepicker input-class="input" format="dd-MM-yyyy"
                      placeholder="End Date"
                      @selected="selectedTanggalEndDate($event)"
                      @cleared="clearedTanggalEndDate($event)"
                      :clear-button="clearButton"></datepicker>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div>
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
      <vuetable ref="vuetable"
                api-url="/leave-request/table"
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
          <div class="custom-actions" v-if="props.rowData.status != 'Canceled'">
            <button class="button is-info" @click="editRole(props.rowData)">
              <i class="fa fa-pencil"/>
            </button>
            <button class="button is-danger" @click="deleteRole(props.rowData)">
              <i class="fa fa-trash"/>
            </button>
            <br>
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
    </div>
    <modal :visible="showDeleteModal" @close="closeDeleteModal" transition="zoom">
      <leave-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></leave-delete>
    </modal>
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

<style>
  .btn-nav{
    width: 120px;
  }
</style>

<script>
  import auth from 'src/shared/auth'
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import LeaveDelete from './LeaveRequestDelete'
  import { Modal } from 'vue-bulma-modal'
  import axios from 'axios'
  import Datepicker from 'vuejs-datepicker'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'center aligned'
    },
    {
      name: 'leaveTypeIdString',
      sortField: 'leaveTypeIdString',
      title: 'Leave Type',
      titleClass: 'center aligned'
    },
    {
      name: 'startDateString',
      sortField: 'startDateString',
      title: 'Start Date',
      titleClass: 'center aligned'
    },
    {
      name: 'endDateString',
      sortField: 'endDateString',
      title: 'End Date',
      titleClass: 'center aligned'
    },
    {
      name: 'numOfDays',
      sortField: 'numOfDays',
      title: 'Num Of Days',
      titleClass: 'center aligned',
      dataClass: 'center aligned'
    },
    {
      name: 'status',
      sortField: 'status',
      title: 'Status',
      titleClass: 'center aligned'
    },
    {
      name: '__slot:actions',
      title: 'Action',
      titleClass: 'center aligned',
      dataClass: 'center aligned'
    }
  ]

  export default {
    components: {
      Vuetable,
      VuetablePagination,
      VuetablePaginationInfo,
      VuetablePaginationDropdown,
      LeaveDelete,
      Modal,
      Datepicker
    },
    mounted () {
      console.log('Role List MOUNTED')
      let thisVM = this
      bus.$on('RELOAD_ROLE_LIST', function () {
        console.log('Role List RELOAD_ROLE_LIST', thisVM.$refs)
        console.log('Role List RELOAD_ROLE_LIST is kosong : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    data () {
      return {
        clearButton: true,
        auth: auth,
        showDeleteModal: false,
        deleteCmd: {},
        form: {
          leaveTypeId: null,
          startDateString: null,
          endDateString: null,
          employeeId: null,
          status: null
        },
        leaveTypeList: [],
        statusList: [],
        fields: tableColumns,
        sortOrder: [{
          field: 'lr.id',
          direction: 'desc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: ''
      }
    },
    created () {
      this.fetchData()
      this.form.employeeId = auth.getUserInfo().employeeId
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/leave-type/getAll')
        ]).then(axios.spread((leaveList) => {
          this.leaveTypeList.push({value: null, text: 'Please Select'})
          for (let leave of leaveList.data) {
            this.leaveTypeList.push({value: leave.id, text: leave.leaveTypeName})
          }
        }))
        this.statusList.push({value: null, text: 'Please Select'})
        this.statusList.push({value: 'Waiting Manager Approva', text: 'Waiting Manager Approva'})
        this.statusList.push({value: 'Waiting Deputy Director Approval', text: 'Waiting Deputy Director Approval'})
        this.statusList.push({value: 'Approved', text: 'Approved'})
        this.statusList.push({value: 'Rejected by Manager', text: 'Rejected by Manager'})
        this.statusList.push({value: 'Rejected by Deputy Director', text: 'Rejected by Deputy Director'})
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
      onLoadSuccess (response) {
        console.log('on LOAD SUCCESS')
        console.log(response)
        // set pagination data to pagination-info component
        // this.$refs.paginationInfo.setPaginationData(response.data)
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
      deleteRole (item) {
        this.deleteCmd = item
        this.showDeleteModal = true
      },
      closeDeleteModal () {
        this.showDeleteModal = false
      },
      addNew () {
        this.$router.replace('/leave-request/add')
      },
      selectedTanggalStartDate (event) {
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
