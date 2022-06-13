<template>


  <div class="card-content">

    <div class="columns">
      <div class="column">

          <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Category</label>
          </div>
        </div>
        <div class="column is-5">
        <span class="select">
          <select v-model="form.category" :class="{'is-danger' : errors.category}">
            <option v-for="option in listCategory" :value="option.value">
              {{ option.text }}
            </option>
          </select>
        </span>
          <div class="control is-grouped" v-if="errors.category">
            <span class="help is-danger">{{ errors.category }}</span>
          </div>
        </div>
      </div>


      <!-- <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Month/Year</label>

          </div>
        </div>
        <div class="column is-2">
            <vue-monthly-picker 
                v-model="courseDate"
                :monthLabels=monthLabels 
                dateFormat="MMM-YYYY"
                @selected="onDateSelect()">
                </vue-monthly-picker>
          <div class="control is-grouped" v-if="errors.courseDate">
            <span class="help is-danger">{{ errors.courseDate }}</span>
          </div>
        </div>
      </div> -->

      <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Course Name</label>
          </div>
        </div>
        <div class="column is-4">
          <div class="control is-grouped level-left">
          <input class="input"
                     type="text" v-model="form.courseName"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.courseName">
            <span class="help is-danger">{{ errors.courseName }}</span>
          </div>
        </div>
      </div>

      <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Certificate Number</label>
          </div>
        </div>
        <div class="column is-4">
          <div class="control is-grouped level-left">
          <input class="input"
                     type="text" v-model="form.certificateNumber"/>
          </div>
        </div>
        <div class="column">
          
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
                api-url="/certificate/table"
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
          <span>Generate New Certificate Number</span>
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
import CertificateNumberOperation from './CertificateNumberOperation.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'

import VueMonthlyPicker from 'vue-monthly-picker'

// import moment from 'moment'

Vue.component('certificateNumberOperation', CertificateNumberOperation)

let tableColumns = [
  {
    name: '__sequence',
    title: 'No.',
    titleClass: 'center aligned',
    dataClass: 'right aligned'
  },
  {
    name: 'category',
    sortField: 'category',
    title: 'Category',
    titleClass: 'center aligned'
  },
  {
    name: 'courseName',
    sortField: 'courseName',
    title: 'Course Name',
    titleClass: 'center aligned'
  },
  {
    name: 'monthYear',
    sortField: 'monthYear',
    title: 'Month/Year',
    titleClass: 'center aligned'
  },
  {
    name: 'certificateNumber',
    sortField: 'certificateNumber',
    title: 'Certificate Number',
    titleClass: 'center aligned'
  },
  {
    title: 'Action',
    titleClass: 'center aligned',
    name: '__component:certificate-number-operation',
    dataClass: 'center aligned'
  }
]

export default {
  components: {
    Vuetable,
    VuetablePagination,
    VuetablePaginationDropdown,
    VuetablePaginationInfo,
    VueMonthlyPicker
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
        category: null,
        courseName: null,
        certificateNumber: null
        // courseDateStr: ''
      },
      courseDate: null,
      listCategory: [
          { value: null, text: '--- Choose Category ---' },
          { value: 'REGULER', text: 'Reguler' },
          { value: 'SHORT_COURSE', text: 'Short Course' },
          { value: 'IHT', text: 'IHT' }
      ],
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'category',
        direction: 'asc'
      }],
      multiSort: true,
      perPage: 10,
      paginationInfoTemplate: '{from} - {to} dari {total} data',
      paginationInfoNoDataTemplate: '',
      monthLabels: ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC']
    }
  },
  methods: {
    onDateSelect () {
      // this.form.courseDateStr = (this.courseDate.getDate() < 10 ? '0' + this.courseDate.getDate() : this.courseDate.getDate()) + '-' + (this.courseDate.getMonth() + 1 < 10 ? ('0' + (this.courseDate.getMonth() + 1)) : (this.courseDate.getMonth() + 1)) + '-' + this.courseDate.getFullYear()
    },
    search () {
      this.errors = {}
      // this.form.courseDateStr = moment(this.courseDate).format('DD/MM/YYYY')
      // console.log('DEBUG: ' + this.form.courseDateStr)
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
      this.$router.replace('/certificate-generator')
    }
  }
}
</script>
