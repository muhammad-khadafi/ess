<template>
  <div class="card-content">
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Project Contract Number</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="contractList"
                      :selected-option="contractItem"
                      placeholder="-- Please Select --"
                      @select="onContractSelect"
                      :class="{'is-danger' : errors.contractId}">
        </basic-select>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Term</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input"
               type="text" v-model="form.term" placeholder="Term"/>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Percentage</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input"
               type="text" v-model="form.percentage" placeholder="Percentage"/>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Value</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input"
               type="text" v-on:keyup="onBlurNumber" v-model="pricewithSeparator" placeholder="Value"/>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Due Date</label>
        </div>
      </div>
      <div class="column is-5">
        <datepicker v-model="form.purchaseDate" input-class="input" format="dd-MM-yyyy"
                    placeholder="Due Date"
                    @selected="selectedTanggal($event)"
                    @cleared="clearedTanggal($event)"
                    :clear-button="clearButton"></datepicker>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Is Paid ?</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="paidList"
                      :selected-option="paidItem"
                      placeholder="-- Please Select --"
                      @select="onPaidSelect">
        </basic-select>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Notes</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input"
               type="text" v-model="form.notes" placeholder="Notes"/>
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
                api-url="/projectPayment/table"
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
import Vue from 'vue'
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import ProjectPaymentOperation from './ProjectPaymentOperation.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'
import axios from 'axios'
import { BasicSelect } from 'vue-search-select'
import Datepicker from 'vuejs-datepicker'

Vue.component('ProjectPaymentOperation', ProjectPaymentOperation)

let tableColumns = [
  {
    name: '__sequence',
    title: 'No.',
    titleClass: 'center aligned',
    dataClass: 'right aligned'
  },
  {
    name: 'projectContractName',
    sortField: 'projectContractName',
    title: 'Peoject Contract Number',
    titleClass: 'center aligned'
  },
  {
    name: 'term',
    sortField: 'term',
    title: 'Term',
    titleClass: 'center aligned'
  },
  {
    name: 'percentage',
    sortField: 'percentage',
    title: 'Percentage',
    titleClass: 'center aligned'
  },
  {
    name: 'value',
    sortField: 'value',
    title: 'value',
    titleClass: 'center aligned',
    callback: 'thousandSeparator'
  },
  {
    name: 'dueDateString',
    sortField: 'dueDateString',
    title: 'Due Date',
    titleClass: 'center aligned'
  },
  {
    name: 'isPaidString',
    sortField: 'isPaidString',
    title: 'Is Paid',
    titleClass: 'center aligned'
  },
  {
    name: 'notes',
    sortField: 'notes',
    title: 'Notes',
    titleClass: 'center aligned'
  },
  {
    title: 'Action',
    titleClass: 'center aligned',
    name: '__component:project-payment-operation',
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
      clearButton: true,
      form: {
        contractId: null,
        term: null,
        percentage: null,
        value: null,
        payment: null,
        notes: null,
        dueDateString: null
      },
      contractList: [],
      contractItem: {
        value: '',
        text: ''
      },
      paidList: [{value: 'true', text: 'paid'},
        {value: 'false', text: 'unpaid'}],
      paidItem: {
        value: '',
        text: ''
      },
      pricewithSeparator: null,
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'name',
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
  methods: {
    fetchData () {
      console.log('START FETCH')
      axios.all([
        axios.get('/projectContract/getAll')
      ]).then(axios.spread((contractList) => {
        for (let contract of contractList.data) {
          this.contractList.push({value: contract.id, text: contract.contractNumber})
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
    onProjectSelect (item) {
      this.errorSelect = false
      this.projectItem = item
      this.form.projectId = item.value
    },
    onContractSelect (item) {
      this.errorSelect = false
      this.contractItem = item
      this.form.contractId = item.value
    },
    onPaidSelect (item) {
      this.errorSelect = false
      this.paidItem = item
      this.form.payment = item.value
    },
    selectedTanggal (event) {
      if (event !== null) {
        this.form.dueDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    addNew () {
      this.$router.replace('/projectPayment/add')
    },
    onBlurNumber () {
      this.pricewithSeparator = this.pricewithSeparator.replace(/\D/g, '')
      this.pricewithSeparator = this.thousandSeparator(this.pricewithSeparator)
    },
    thousandSeparator (amount) {
      if (this.form.value != null && this.form.value != null) {
        this.form.value = this.form.value.replace(/\D/g, '')
      }
      if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      } else {
        return amount
      }
    }
  }
}
</script>
