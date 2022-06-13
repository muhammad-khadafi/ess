<template>
  <div class="box">
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Employee Name</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options= "listPegawai"
                      :selected-option= "pegawai"
                      placeholder= "Please Select"
                      @select= "onPegawaiSelect"
                      :class="{'is-danger' : errors.employeeId}">
        </basic-select>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Inventory Name</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options= "listInventory"
                      :selected-option= "inventory"
                      placeholder= "Please Select"
                      @select= "onInventorySelect"
                      :class="{'is-danger' : errors.inventoryId}">
        </basic-select>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Start Date</label>
        </div>
      </div>
      <div class="column is-5">
        <datepicker v-model="form.startDate" input-class="input" format="dd-MM-yyyy"
                    placeholder="Start Date"
                    @selected="selectedStartDate($event)"
                    @cleared="clearedStartDate($event)">
        </datepicker>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">End Date</label>
        </div>
      </div>
      <div class="column is-5">
        <datepicker v-model="form.endDate" input-class="input" format="dd-MM-yyyy"
                    placeholder="End Date"
                    @selected="selectedEndDate($event)"
                    @cleared="clearedEndDate($event)">
        </datepicker>
      </div>
    </div>

    <div class="columns">
       <div class="column is-half is-offset-one-quarter has-text-centered">
        <button class="button is-info" @click="search()">
          <span class="icon is-small"><i class="fa fa-search"></i></span>
          <span>Search</span>
        </button>
      </div>
    </div>

  <div>
    <vuetable ref="vuetable"
                api-url="/inventory-assignment/table"
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
          <div class="custom-actions">
            <tooltip label="view" placement="top">
              <div class="is-clearfix">
                <button class="button is-warning" @click="viewInventoryAssignment(props.rowData)">
              <i class="fa fa-eye"/>
              </button>
              </div>
            </tooltip>
            <tooltip label="edit" placement="top">
              <div class="is-clearfix">
                <button class="button is-info" @click="editInventoryAssignment(props.rowData)">
              <i class="fa fa-pencil"/>
            </button>
              </div>
            </tooltip>
            <tooltip label="delete" placement="top">
              <div class="is-clearfix">
                <button class="button is-danger" @click="deleteInventoryAssignment(props.rowData)">
              <i class="fa fa-trash"/>
            </button>
              </div>
            </tooltip>
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
  <modal :visible="showDeleteModal" @close="onClose" transition="zoom">
      <inventory-assignment-delete :deleteForm="deleteCmd" @close="onClose"></inventory-assignment-delete>
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
<script>
import { BasicSelect } from 'vue-search-select'
import Datepicker from 'vuejs-datepicker'
import axios from 'axios'
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'
import Tooltip from 'vue-bulma-tooltip'
import InventoryAssignmentDelete from './Delete'
import { Modal } from 'vue-bulma-modal'

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
    title: 'Employee',
    titleClass: 'center aligned'
  },
  {
    name: 'inventoryName',
    sortField: 'inventoryName',
    title: 'Inventory',
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
    name: '__slot:actions',
    title: 'Action',
    titleClass: 'center aligned',
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
    Tooltip,
    InventoryAssignmentDelete,
    Modal
  },
  data () {
    return {
      showDeleteModal: false,
      deleteCmd: {},
      clearButton: true,
      listPegawai: [],
      listInventory: [],
      errors: {},
      form: {
        id: null,
        employeeId: null,
        employeeName: null,
        inventoryId: null,
        inventoryName: null,
        startDate: null,
        endDate: null,
        startDateString: null,
        endDateString: null
      },
      pegawai: {
        value: '',
        text: ''
      },
      inventory: {
        value: '',
        text: ''
      },
      fields: tableColumns,
      sortOrder: [{
        field: 'id',
        direction: 'asc'
      }],
      multiSort: true,
      perPage: 10,
      paginationInfoTemplate: '{from} - {to} dari {total} data',
      paginationInfoNoDataTemplate: ''
    }
  },
  created () {
    this.fetchData()
  },
  mounted () {
    let thisVM = this
    bus.$on('RELOAD_INVENTORY_ASSIGNMENT_LIST', function () {
      if (thisVM.$refs.vuetable !== undefined) {
        thisVM.$refs.vuetable.reload()
      }
    })
  },
  methods: {
    fetchData () {
      axios.all([
        axios.get('/inventory/all'),
        axios.get('/employee/all')
      ]).then(axios.spread((objekInven, objekEmploy) => {
        for (let inv of objekInven.data) {
          this.listInventory.push({value: inv.id, text: inv.name + ' - ' + inv.type})
        }
        for (let emp of objekEmploy.data) {
          this.listPegawai.push({value: emp.id, text: emp.name})
        }
        console.log('isi dari inven', this.listInventory)
        console.log('isi dari empl', this.listPegawai)
      }))
    },
    onPegawaiSelect (item) {
      this.pegawai = item
      this.form.employeeId = this.pegawai.value
    },
    onInventorySelect (item) {
      this.inventory = item
      this.form.inventoryId = this.inventory.value
    },
    selectedStartDate (event) {
      if (event !== null) {
        this.form.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedStartDate (event) {
      this.form.startDate = null
    },
    selectedEndDate (event) {
      if (event !== null) {
        this.form.endDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedEndDate (event) {
      this.form.endDate = null
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
    formatRow (value, param) {
      return value + ' - ' + param
    },
    onClose () {
      this.showDeleteModal = false
    },
    viewInventoryAssignment (item) {
      this.$router.push('/inventory-assignment/view/' + item.id)
    },
    editInventoryAssignment (item) {
      this.$router.push('/inventory-assignment/edit/' + item.id)
    },
    deleteInventoryAssignment (item) {
      this.showDeleteModal = true
      this.deleteCmd = item
    },
    addNew () {
      this.$router.replace('/inventory-assignment/add')
    }
  }
}
</script>
