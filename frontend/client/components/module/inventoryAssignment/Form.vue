<template>
<div>
  <div class="box">
    <div v-if="$route.params.menu != 'view'" class="columns">
      <div class="column">
        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Employee Name<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <div class="column is-one-third">
              <basic-select :options= "listPegawai"
              :selected-option= "pegawai"
              placeholder= "Employee Name"
              @select= "onPegawaiSelect"
              :class="{'is-danger' : errors.employeeId}">
              </basic-select>
              </div>
            </div>
            <div class="control" v-if="errors.employeeId">
              <span class="help is-danger">{{errors.employeeId}}</span>
            </div>
          </div>
        </div>
        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Start Date<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
              <div class="control is-expanded">
                <div class="column is-one-third">
                  <datepicker v-model="cmd.startDate" input-class="input" format="dd-MM-yyyy"
                              placeholder="Start Date"
                              @selected="selectedStartDate($event)"
                              @cleared="clearedStartDate($event)"
                              :clear-button="clearButton">
                  </datepicker>
                </div>
            </div>
            <div class="control" v-if="errors.startDate">
              <span class="help is-danger">{{errors.startDate}}</span>
            </div>
          </div>
        </div>
        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">End Date</label>
          </div>
          <div class="control is-grouped">
              <div class="control is-expanded">
                <div class="column is-one-third">
                  <datepicker v-model="cmd.endDate" input-class="input" format="dd-MM-yyyy"
                              placeholder="End Date"
                              @selected="selectedEndDate($event)"
                              @cleared="clearedEndDate($event)"
                              :clear-button="clearButton">
                  </datepicker>
                </div>
            </div>
            <div class="control" v-if="errors.endDate">
              <span class="help is-danger">{{errors.endDate}}</span>
            </div>
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
          <button v-if="$route.params.menu == 'add'" class="button is-info" @click="add()">
            <span>Add</span>
          </button>
          <button v-if="$route.params.menu == 'edit'" class="button is-info" @click="submit()">
            <span>Add</span>
          </button>
        </div>
        <!--<div class="control">
          <button class="button is-warning" @click="cancel()">Cancel</button>
        </div>-->
      </div>
    </div>

<!--    {{cmd}}-->

    <br>
    <br>

    <div v-if="$route.params.menu == 'add'" class="columns">
      <div class="column">
        <table class="vuetable ui blue selectable celled stackable attached table">
          <th>No.</th>
          <th>Employee Name</th>
          <th>Start Date</th>
          <th>End Date</th>
          <tr v-for="(inventAssign, index) in cmd.listInventoryAssignment">
            <td>{{ index + 1 }}</td>
            <td>{{ inventAssign.employeeName }}</td>
            <td>{{ inventAssign.startDateString}}</td>
            <td>{{ inventAssign.endDateString}}</td>
            <td><a href="" @click.prevent="deleteInvenAssignment(inventAssign)">Delete</a></td>
          </tr>
        </table>
      </div>
    </div>

    <!-- Start View -->
    <div v-if="$route.params.menu != 'add'">
      <vuetable ref="vuetable"
                api-url="/inventory-assignment/table"
                :fields="($route.params.menu == 'edit') ? fields : fields2"
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
            <tooltip label="edit" placement="top">
              <div class="is-clearfix">
                <button v-if="$route.params.menu != 'view'" class="button is-info" @click="editInventoryAssignment(props.rowData)">
                  <i class="fa fa-pencil"/>
                </button>
              </div>
            </tooltip>
            <tooltip label="delete" placement="top">
              <div class="is-clearfix">
                <button v-if="$route.params.menu != 'view'" class="button is-danger" @click="deleteInventoryAssignment(props.rowData)">
                  <i class="fa fa-trash"/>
                </button>
              </div>
            </tooltip>
            <tooltip label="remove assignment" placement="top">
              <div v-if="props.rowData.assigned" class="is-clearfix">
                <button v-if="$route.params.menu != 'view'" class="button is-warning" @click="removeInventoryAssignment(props.rowData)">
                  <i class="fa fa-user-times"/>
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
    <modal :visible="showEditModal"
           @close="closeEditModal" transition="zoom">
      <inventory-assignment-edit :cmd="editCmd"  @close="closeEditModal"></inventory-assignment-edit>
    </modal>
    <modal :visible="showDeleteModal" @close="closeDeleteModal" transition="zoom">
      <inventory-assignment-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></inventory-assignment-delete>
    </modal>
    <modal :visible="showRemoveModal" @close="closeRemoveModal" transition="zoom">
      <inventory-assignment-remove :deleteForm="removeCmd"  @close="closeRemoveModal"></inventory-assignment-remove>
    </modal>
    <!-- End of View -->

    <div id="deleteModal" class="modal" transition="zoom">
      <div class="modal-background"></div>
      <div class="modal-content">
        <header class="modal-card-head">
          <p class="modal-card-title">Delete Confirmation</p>
          <button class="delete" @click="close"></button>
        </header>
        <section class="modal-card-body">
          <p style="font-size:16px">Are you sure to delete this item?</p>
        </section>
        <footer class="modal-card-foot">
          <div class="column" style="padding:0">
            <div class="level-right">
              <button class="button is-danger" @click="confirm">Delete</button>
              <button class="button" @click="close">Cancel</button>
            </div>
          </div>
        </footer>
      </div>
    </div>

  </div>
</div>
</template>
<script>
import { BasicSelect } from 'vue-search-select'
import Datepicker from 'vuejs-datepicker'
import axios from 'axios'
import ValidationMixin from '../../mixin/ValidationMixin'
import bus from 'src/shared/bus'
import _ from 'lodash'
import { Modal } from 'vue-bulma-modal'
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import Tooltip from 'vue-bulma-tooltip'
import InventoryAssignmentEdit from '../inventoryAssignment/Edit'
import InventoryAssignmentDelete from '../inventoryAssignment/Delete'
import InventoryAssignmentRemove from '../inventoryAssignment/RemoveAssignment'

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

let tableColumns2 = [
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
  }
]

export default {
  mixins: [ValidationMixin],
  components: {
    BasicSelect,
    Datepicker,
    Modal,
    InventoryAssignmentEdit,
    InventoryAssignmentDelete,
    InventoryAssignmentRemove,
    Vuetable,
    VuetablePagination,
    VuetablePaginationDropdown,
    VuetablePaginationInfo,
    Tooltip
  },
  props: {
    cmd: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      clearButton: true,
      listPegawai: [],
      listInventory: [],
      pegawai: {
        text: '',
        value: ''
      },
      inventory: {
        text: '',
        value: ''
      },
      form: {
        inventoryId: this.$route.params.id
      },
      showRemoveModal: false,
      showDeleteModal: false,
      showEditModal: false,
      removeCmd: {},
      deleteCmd: {},
      editCmd: {},
      fields: tableColumns,
      fields2: tableColumns2,
      sortOrder: [{
        field: 'id',
        direction: 'desc'
      }],
      multiSort: true,
      perPage: 10,
      paginationInfoTemplate: '{from} - {to} dari {total} data',
      paginationInfoNoDataTemplate: '',
      errors: {}
    }
  },
  created () {
    this.fetchData()
  },
  mounted () {
    let thisVM = this
    bus.$on('RELOAD_INVENTORY_ASSIGN_LIST', function () {
      console.log('RELOAD_INVENTORY_ASSIGN_LIST : ', thisVM.$refs.vuetable)

      if (thisVM.$refs.vuetable !== undefined) {
        thisVM.$refs.vuetable.reload()
      }
    })
  },
  methods: {
    close () {
      $('#deleteModal').removeClass('is-active')
    },
    confirm () {
      this.confirmDelete = true
      this.deleteInvenAssignment(this.tempData)
      $('#deleteModal').removeClass('is-active')
    },
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
      this.cmd.employeeId = this.pegawai.value
      this.cmd.employeeName = this.pegawai.text
    },
    onInventorySelect (item) {
      this.inventory = item
      this.cmd.inventoryId = this.inventory.value
    },
    selectedStartDate (event) {
      if (event !== null) {
        this.cmd.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedStartDate (event) {
      this.cmd.startDate = null
    },
    selectedEndDate (event) {
      if (event !== null) {
        this.cmd.endDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedEndDate (event) {
      this.cmd.endDate = null
    },
    /*
    Start edit inventory assignment
    */
    submit () {
      this.validateAll()
      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }
      this.cmd.inventoryId = this.$route.params.id
      axios.post('/inventory-assignment', this.cmd)
        .then((resp) => {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCESS')
          console.log(resp)
          if (this.$refs.vuetable !== undefined) {
            this.$refs.vuetable.reload()
            this.clear()
          }
          bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
        })
        .catch((error) => {
          bus.$emit('HIDE_LOADING')
          console.log('ERROR : ', error)
          console.log('isi dari error', error.response)
          _.each(error.response.data.errors, function (value) {
            this.$set(this.errors, value.field, value.message)
          })
        })
    },
    closeEditModal () {
      this.showEditModal = false
    },
    editInventoryAssignment (item) {
      this.editCmd = item
      this.showEditModal = true
    },
    closeDeleteModal () {
      this.showDeleteModal = false
    },
    closeRemoveModal () {
      this.showRemoveModal = false
    },
    removeInventoryAssignment (item) {
      this.removeCmd = item
      this.showRemoveModal = true
    },
    deleteInventoryAssignment (item) {
      this.deleteCmd = item
      this.showDeleteModal = true
    },
    /*
    End edit inventory assignment
    */
    cancel () {
      this.$router.push('/inventory-assignment')
      console.log('isi dari cmd', this.cmd)
    },
    add () {
      this.validateAll()

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }

      this.cmd.listInventoryAssignment.push({
        id: null,
        employeeName: this.cmd.employeeName,
        employeeId: this.cmd.employeeId,
        startDate: this.cmd.startDate,
        startDateString: this.cmd.startDateString,
        endDate: this.cmd.endDate,
        endDateString: this.cmd.endDateString
      })

      this.clear()
    },
    clear () {
      this.cmd.employeeName = null
      this.cmd.employeeId = null
      this.cmd.startDate = null
      this.cmd.startDateString = null
      this.cmd.endDate = null
      this.cmd.endDateString = null
    },
    deleteInvenAssignment (InvenAssign) {
      this.tempData = InvenAssign
      if (this.confirmDelete === true) {
        _.remove(this.cmd.listInventoryAssignment, (delInvenAssign) => {
          return (delInvenAssign.employeeId === InvenAssign.employeeId)
        })
        this.$forceUpdate()
        this.confirmDelete = false
      }
      $('#deleteModal').addClass('is-active')
    },
    validateEmployeeNotNull () {
      this.validateNotBlank('employeeId', this.cmd.employeeId)
    },
    validateInventoryNotNull () {
      this.validateNotBlank('inventoryId', this.cmd.inventoryId)
    },
    validateAll () {
      this.validateNotBlank('employeeId', this.cmd.employeeId)
      this.validateNotBlank('startDate', this.cmd.startDate)
    },
    /*
    Start vue table
     */
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
    }
    /*
    End Vue table
     */
  }
}
</script>
