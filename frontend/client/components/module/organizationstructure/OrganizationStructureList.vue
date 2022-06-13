<template>
  <div class="box">

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Organization Structure Name</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input"
               type="text" v-model="form.name" placeholder="Organization Structure Name"/>
      </div>
    </div>
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Parent Organization</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="parentList"
                      :selected-option="parentItem"
                      placeholder="-- Please Select --"
                      @select="onParentSelect"
                      :class="{'is-danger' : errors.fundingType}">
        </basic-select>
      </div>
    </div>
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Is Active ?</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="activList"
                      :selected-option="activItem"
                      placeholder="-- Please Select --"
                      @select="onActiveSelect">
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
                api-url="/organization-structure/table"
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
import OrganizationStructureOperation from './OrganizationStructureOperation.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'
import axios from 'axios'
import { BasicSelect } from 'vue-search-select'

Vue.component('OrganizationStructureOperation', OrganizationStructureOperation)

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
    title: 'Organization Structure Name',
    titleClass: 'center aligned'
  },
  {
    name: 'parentName',
    sortField: 'parentName',
    title: 'Parent Name',
    titleClass: 'center aligned'
  },
  {
    name: 'isActive',
    sortField: 'isActive',
    title: 'Is Active',
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
    name: '__component:organization-structure-operation',
    dataClass: 'center aligned'
  }
]

export default {
  components: {
    Vuetable,
    VuetablePagination,
    VuetablePaginationDropdown,
    VuetablePaginationInfo,
    BasicSelect
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
        organizationId: null,
        fundingType: null,
        client: null,
        statusId: null,
        notes: null,
        stringAktif: null,
        tempCheck: null,
        name: null
      },
      parentList: [],
      parentItem: {
        value: '',
        text: ''
      },
      activList: [{value: 'true', text: 'true'},
        {value: 'false', text: 'false'}],
      activItem: {
        value: '',
        text: ''
      },
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'nama',
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
        axios.get('/organization-structure/structurelist')
      ]).then(axios.spread((parentList, organizationList, statusList) => {
        for (let parent of parentList.data) {
          this.parentList.push({value: parent.id, text: parent.name})
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
    onParentSelect (item) {
      this.errorSelect = false
      this.parentItem = item
      this.form.parentId = item.value
    },
    onActiveSelect (item) {
      this.errorSelect = false
      this.activItem = item
      this.form.stringAktif = item.value
    },
    addNew () {
      this.$router.replace('/organization-structure/add')
    }
  }
}
</script>
