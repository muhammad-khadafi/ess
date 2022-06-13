<template>
  <div class="card-content">
    <div class="columns">
      <div class="column">
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Role Code</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <input class="input"
                     type="text" v-model="form.kode" placeholder="Role Code"/>
            </div>
          </div>
        </div>
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Role Name</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <input class="input"
                     type="text" v-model="form.label" placeholder="Role Name"/>
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
                api-url="/role/table"
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
      <role-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></role-delete>
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
  import RoleDelete from './RoleDelete'
  import { Modal } from 'vue-bulma-modal'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'kode',
      sortField: 'kode',
      title: 'Role Code',
      titleClass: 'center aligned'
    },
    {
      name: 'label',
      sortField: 'label',
      title: 'Role Name',
      titleClass: 'center aligned'
    },
    {
      name: 'keterangan',
      sortField: 'keterangan',
      title: 'Note',
      titleClass: 'center aligned',
      callback: 'formatKeterangan'
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
      RoleDelete,
      Modal
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
        auth: auth,
        showDeleteModal: false,
        deleteCmd: {},
        form: {
          kode: null,
          label: null,
          keterangan: null
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
    methods: {
      fetchData () {
        console.log('START FETCH')
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
        this.$router.replace('/role/add')
      },
      editRole (item) {
        this.$router.replace('/role/edit/' + item.id)
      },
      formatKeterangan (value) {
        return value === null ? '-' : value
      }
    }
  }
</script>
