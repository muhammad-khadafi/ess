<template>


  <div class="card-content">
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Category</label>
        </div>
      </div>
      <div class="column is-5">
        <span class="select">
          <select v-model="form.category" :class="{'is-danger' : errors.category}">
            <option v-for="option in categoryList" :value="option.value">
              {{ option.text }}
            </option>
          </select>
        </span>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Start Request Date</label>
        </div>
      </div>
      <div class="column is-3">
        <datepicker v-model="form.startDate" input-class="input" format="dd-MM-yyyy"
                    placeholder="Start Date"
                    @selected="selectedTanggalStartDate($event)"
                    @cleared="clearedTanggalStartDate($event)"
                    :clear-button="clearButton"></datepicker>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">End Request Date</label>
        </div>
      </div>
      <div class="column is-3">
        <datepicker v-model="form.endDate" input-class="input" format="dd-MM-yyyy"
                    placeholder="End Date"
                    @selected="selectedTanggalEndDate($event)"
                    @cleared="clearedTanggalEndDate($event)"
                    :clear-button="clearButton"></datepicker>
      </div>
    </div>

    <div class="columns is-mobile"  v-if="form.roleName == 'DIRECTOR' || form.roleName == 'DEPUTY_DIRECTOR'">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Status</label>
        </div>
      </div>
      <div class="column is-5">
        <span class="select">
          <select v-model="form.pettyCashStatus" :class="{'is-danger' : errors.pettyCashStatus}">
            <option v-for="option in pettyCashStatusList" :value="option.value">
              {{ option.text }}
            </option>
          </select>
        </span>
      </div>
    </div>

    <div class="columns is-mobile"  v-if="form.roleName === 'ACCOUNTING_STAFF'">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Status</label>
        </div>
      </div>
      <div class="column is-5">
        <span class="select">
          <select v-model="form.pettyCashStatus" :class="{'is-danger' : errors.pettyCashStatus}">
            <option v-for="option in pettyCashStatusListAccounting" :value="option.value">
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
    <!--    {{form}}-->

    <div>
      <vuetable ref="vuetable"
                api-url="/petty-cash/table"
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
                @vuetable:loadeddata="thousandSeparator"
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
  import PettyCashOperation from './PettyCashOperation.vue'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import axios from 'axios'
  import {BasicSelect} from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import auth from 'src/shared/auth'

  Vue.component('pettyCashOperation', PettyCashOperation)

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'reqNumber',
      sortField: 'reqNumber',
      title: 'Request Number',
      titleClass: 'center aligned'
    },
    {
      name: 'paidToName',
      sortField: 'paidToName',
      title: 'Paid To',
      titleClass: 'center aligned'
    },
    {
      name: 'reqAmountStr',
      sortField: 'reqAmount',
      title: 'Request Amount',
      titleClass: 'center aligned'
    },
    {
      name: 'category',
      sortField: 'category',
      title: 'Category',
      titleClass: 'center aligned'
    },
    {
      name: 'reqDateString',
      sortField: 'reqDate',
      title: 'Request Date',
      titleClass: 'center aligned'
    },
    {
      name: 'statusStr',
      sortField: 'statusStr',
      title: 'Status',
      titleClass: 'center aligned'
    },
    {
      title: 'Action',
      titleClass: 'center aligned',
      name: '__component:petty-cash-operation',
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
      this.getRoleUser()
      console.log('Level Rate List MOUNTED')
      let thisVM = this
      bus.$on('RELOAD_PETTY_CASH_LIST', function () {
        console.log('RELOAD_PETTY_CASH_LIST', thisVM.$refs)
        console.log('RELOAD_PETTY_CASH_LIST is kosong : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    data () {
      return {
        clearButton: true,
        form: {
          category: null,
          startDate: null,
          endDate: null,
          startDateString: '',
          endDateString: '',
          idRole: localStorage.getItem('id_role'),
          loginAs: auth.getUserInfo().id,
          pettyCashStatus: 'In Process',
          roleName: ''
        },
        pricewithSeparator: null,
        errors: {},
        fields: tableColumns,
        sortOrder: [{
          field: 'id',
          direction: 'desc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
        categoryList: [
          {value: null, text: '--- Choose Category ---'},
          {value: 'Transport', text: 'Transport'},
          {value: 'Non Transport', text: 'Non Transport'}
        ],
        pettyCashStatusList: [
          {value: 'Waiting for Approval', text: 'Waiting for Approval'},
          {value: 'Paid', text: 'In Process/Paid/Rejected'}
        ],
        pettyCashStatusListAccounting: [
          {value: 'Waiting for Approval', text: 'Waiting for Approval'},
          {value: 'In Process', text: 'In Process'},
          {value: 'Paid', text: 'Paid'},
          {value: 'Rejected', text: 'Rejected'}
        ]
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
      },
      getRoleUser () {
        let thisVM = this
        axios.get('/role-by-id/' + this.form.idRole)
          .then(function (resp) {
            thisVM.form.roleName = resp.data.kode
            if (thisVM.form.roleName === 'DIRECTOR' || thisVM.form.roleName === 'DEPUTY_DIRECTOR') {
              thisVM.form.pettyCashStatus = 'Waiting for Approval'
            }
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
        this.loading = true
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
      addNew () {
        this.$router.replace('/petty-cash/add')
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
      },
      clearedTanggalStartDate (event) {
        this.form.startDate = null
        this.form.startDateString = null
      },
      clearedTanggalEndDate (event) {
        this.form.endDate = null
        this.form.endDateString = null
      },
      onBlurNumber () {
        this.form.rate = this.pricewithSeparator.replace(/\D/g, '')
        this.pricewithSeparator = this.thousandSeparator(this.pricewithSeparator)
      },
      thousandSeparator (amount) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      },
      customPredictor (date) {
        if (date.getDate() % 5 === 0) {
          return true
        }
      }
    }
  }
</script>
