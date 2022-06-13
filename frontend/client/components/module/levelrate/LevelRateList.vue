<template>


  <div class="card-content">
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Level</label>
        </div>
      </div>
      <div class="column is-5">
        <span class="select">
          <select v-model="form.levelId" :class="{'is-danger' : errors.level}">
            <option v-for="option in levelList" :value="option.value">
              {{ option.text }}
            </option>
          </select>
        </span>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Rate</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input" v-on:keyup="onBlurNumber" type="text" v-model="pricewithSeparator"/>
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
                    @selected="selectedTanggalStartDate($event)"
                    @cleared="clearedTanggalStartDate($event)"
                    :clear-button="clearButton"></datepicker>
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
                    @selected="selectedTanggalEndDate($event)"
                    @cleared="clearedTanggalEndDate($event)"
                    :clear-button="clearButton"></datepicker>
      </div>
    </div>

    <div class="columns">
      <div class="column is-half is-offset-one-quarter has-text-centered">
        <button class="button is-info" @click="search()">
                  <span class="icon is-small">
                    <i class="fa fa-search"></i>
                  </span>
          <span>Serach</span>
        </button>
      </div>
    </div>
<!--    {{form}}-->
    <div>
      <vuetable ref="vuetable"
                api-url="/level-rate/table"
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
  import LevelRateOperation from './LevelRateOperation.vue'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import axios from 'axios'
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'

  Vue.component('levelRateOperation', LevelRateOperation)

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'levelName',
      sortField: 'levelName',
      title: 'Level Name',
      titleClass: 'center aligned'
    },
    {
      name: 'rate',
      sortField: 'rate',
      title: 'Rate',
      titleClass: 'center aligned',
      callback: 'thousandSeparator'
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
      name: '__component:level-rate-operation',
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
      console.log('Level Rate List MOUNTED')
      let thisVM = this
      bus.$on('RELOAD_LEVEL_RATE_LIST', function () {
        console.log('LEVEL RATE LIST', thisVM.$refs)
        console.log('LEVEL RATE LIST is kosong : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    data () {
      return {
        clearButton: true,
        form: {
          levelId: null,
          rate: null,
          startDate: null,
          endDate: null,
          startDateString: '',
          endDateString: ''
        },
        pricewithSeparator: null,
        errors: {},
        fields: tableColumns,
        sortOrder: [{
          field: 'levelName',
          direction: 'asc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
        levelList: []
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/level/all')
        ]).then(axios.spread((levelList) => {
          this.levelList.push({value: null, text: '-- Please Select --'})
          for (let level of levelList.data) {
            this.levelList.push({value: level.id, text: level.name})
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
      addNew () {
        this.$router.replace('/level-rate/add')
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
        this.form.startDateString = null
      },
      clearedTanggalEndDate (event) {
        this.form.endDateString = null
      },
      onBlurNumber () {
        this.form.rate = this.pricewithSeparator.replace(/\D/g, '')
        this.pricewithSeparator = this.thousandSeparator(this.pricewithSeparator)
      },
      thousandSeparator (amount) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      }
    }
  }
</script>
