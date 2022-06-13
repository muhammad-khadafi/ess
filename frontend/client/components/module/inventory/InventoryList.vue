<template>
  <div class="box">
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Type</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="inventoryTypeList"
                      :selected-option="inventoryTypeItem"
                      placeholder="-- Please Select Type --"
                      @select="onTypeSelect"
                      :class="{'is-danger' : errors.type}">
        </basic-select>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Name</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input" :class="{'is-danger' : errors.name}"
               type="text" v-model="form.name" placeholder="Name"/>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Location</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input" :class="{'is-danger' : errors.location}"
               type="text" v-model="form.location" placeholder="Location"/>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Condition</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input" :class="{'is-danger' : errors.condition}"
               type="text" v-model="form.condition" placeholder="Condition"/>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Purchase Date</label>
        </div>
      </div>
      <div class="column is-5">
        <datepicker v-model="form.purchaseDate" input-class="input" format="dd-MM-yyyy"
                    placeholder="Purchase Date"
                    @selected="selectedTanggal($event)"
                    @cleared="clearedTanggal($event)"
                    :clear-button="clearButton"></datepicker>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Is Assigned ?</label>
        </div>
      </div>
      <div class="column is-5">
        <span class="select">
                <select v-model="form.isAssigned" :class="{'is-danger' : errors.isAssigned}">
                <option v-for="option in listFlagAktif" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
              </span>
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
                api-url="/inventory/table"
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
                <button class="button is-warning" @click="viewInventory(props.rowData)">
              <i class="fa fa-eye"/>
              </button>
              </div>
            </tooltip>
            <tooltip label="edit" placement="top">
              <div class="is-clearfix">
                <button class="button is-info" @click="editInventory(props.rowData)">
              <i class="fa fa-pencil"/>
            </button>
              </div>
            </tooltip>
            <tooltip label="delete" placement="top">
              <div class="is-clearfix">
                <button class="button is-danger" @click="deleteInventory(props.rowData)">
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
  </div><!-- content -->
    <modal :visible="showDeleteModal" @close="onClose" transition="zoom">
      <inventory-delete :deleteForm="deleteCmd" @close="onClose"></inventory-delete>
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
import axios from 'axios'
import { Modal } from 'vue-bulma-modal'
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'
import InventoryDelete from './InventoryDelete'
import Datepicker from 'vuejs-datepicker'
import Tooltip from 'vue-bulma-tooltip'
import { BasicSelect } from 'vue-search-select'

let tableColumns = [
  {
    name: '__sequence',
    title: 'No.',
    titleClass: 'center aligned',
    dataClass: 'right aligned'
  },
  {
    name: 'typeString',
    sortField: 'type',
    title: 'Type',
    titleClass: 'center aligned'
  },
  {
    name: 'name',
    sortField: 'name',
    title: 'Name',
    titleClass: 'center aligned'
  },
  {
    name: 'location',
    sortField: 'location',
    title: 'Location',
    titleClass: 'center aligned'
  },
  {
    name: 'condition',
    sortField: 'condition',
    title: 'Condition',
    titleClass: 'center aligned'
  },
  {
    name: 'purchaseDateString',
    sortField: 'purchaseDate',
    title: 'Purchase Date',
    titleClass: 'center aligned'
  },
  {
    name: 'employeeName',
    sortField: 'isAssigned',
    title: 'Is Assigned',
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
    VuetablePaginationDropdown,
    VuetablePaginationInfo,
    InventoryDelete,
    Modal,
    Datepicker,
    Tooltip,
    BasicSelect
  },
  mounted () {
    let thisVM = this
    bus.$on('RELOAD_INVENTORY_LIST', function () {
      if (thisVM.$refs.vuetable !== undefined) {
        thisVM.$refs.vuetable.reload()
      }
    })
  },
  created () {
    this.fetchData()
  },
  data () {
    return {
      clearButton: true,
      showDeleteModal: false,
      deleteCmd: {},
      form: {
        type: null,
        name: null,
        location: null,
        condition: null,
        purchaseDateString: null,
        purchaseDate: null,
        isAssigned: null
      },
      listFlagAktif: [
        { value: '', text: '--- All Status ---' },
        { value: 'yes', text: 'Yes' },
        { value: 'no', text: 'No' }
      ],
      errors: {},
      inventoryTypeList: [],
      inventoryTypeItem: {
        value: '',
        text: ''
      },
      fields: tableColumns,
      sortOrder: [{
        field: 'id',
        direction: 'desc'
      }],
      multiSort: true,
      perPage: 10,
      paginationInfoTemplate: '{from} - {to} dari {total} data',
      paginationInfoNoDataTemplate: ''
    }
  },
  methods: {
    fetchData () {
      axios.all([
        axios.get('/master-reference/getReference/Inventory Type')
      ]).then(axios.spread((inventoryTypeList) => {
        for (let inventoryType of inventoryTypeList.data) {
          this.inventoryTypeList.push({value: inventoryType.id, text: inventoryType.name})
        }
      }))
    },
    search () {
      this.errors = {}
      this.$refs.vuetable.refresh()
    },
    onTypeSelect (item) {
      this.errorSelect = false
      this.inventoryTypeItem = item
      this.form.type = item.value
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
      return value === true ? 'Active' : 'Not Active'
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
    viewInventory (item) {
      this.$router.push('/inventory/view/' + item.id)
    },
    editInventory (item) {
      this.$router.push('/inventory/do/edit/' + item.id)
    },
    deleteInventory (item) {
      this.showDeleteModal = true
      this.deleteCmd = item
    },
    onClose () {
      this.showDeleteModal = false
    },
    selectedTanggal (event) {
      if (event !== null) {
        this.form.purchaseDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedTanggal (event) {
      this.form.purchaseDateString = null
    },
    isAssigned (value) {
      return value === true ? 'Yes' : 'No'
    },
    addNew () {
      this.$router.replace('/inventory/add')
    }
  }
}
</script>
