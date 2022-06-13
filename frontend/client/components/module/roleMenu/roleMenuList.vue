<template>
  <div class="box">
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Role</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="projectList"
                      :selected-option="projectItem"
                      placeholder="Please Select Role"
                      @select="onProjectSelect"
                      :class="{'is-danger' : errors.organizationId}">
        </basic-select>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Menu</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input"
               type="text" v-model="form.menuString" placeholder="Menu"/>
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
                api-url="/role-menu/table"
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
import RoleMenuOperation from './RoleMenuOperation.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'
import axios from 'axios'
import { BasicSelect } from 'vue-search-select'

Vue.component('RoleMenuOperation', RoleMenuOperation)

let tableColumns = [
  {
    name: '__sequence',
    title: 'No.',
    titleClass: 'center aligned',
    dataClass: 'right aligned'
  },
  {
    name: 'roleString',
    sortField: 'id_role',
    title: 'Role',
    titleClass: 'center aligned'
  },
  {
    name: 'menuString',
    sortField: 'id_Menu',
    title: 'Menu',
    titleClass: 'center aligned'
  },
  {
    title: 'Action',
    titleClass: 'center aligned',
    name: '__component:role-menu-operation',
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
        menuString: null,
        idRole: null
      },
      projectList: [],
      projectItem: {
        value: '',
        text: ''
      },
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'id_role',
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
        axios.get('/role/getAll')
      ]).then(axios.spread((projectList) => {
        for (let organization of projectList.data) {
          this.projectList.push({value: organization.id, text: organization.label})
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
      this.form.idRole = item.value
    },
    addNew () {
      this.$router.replace('/role-menu/add')
    }
  }
}
</script>
