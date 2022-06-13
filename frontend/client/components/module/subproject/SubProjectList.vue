<template>
  <div class="card-content">
    <div class="columns">
      <div class="column">
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Project</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <basic-select :options="projectList"
                      :selected-option="projectItem"
                      placeholder="-- Please Select --"
                      @select="onProjectSelect">
              </basic-select>
            </div>
          </div>
        </div>
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Sub Project Name</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <input class="input"
               type="text" v-model="form.subProjectName" placeholder="Sub Project Name"/>
            </div>
          </div>
        </div>
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Sub Project Code</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <input class="input"
               type="text" v-model="form.code" placeholder="Sub Project Code"/>
            </div>
          </div>
        </div>
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Supervisor</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <input class="input"
               type="text" v-model="form.supervisor" placeholder="Supervisor"/>
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
                api-url="/sub-project/table"
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
            <button class="button is-warning" @click="viewSubPro(props.rowData)">
              <i class="fa fa-eye"/>
            </button>
            <button class="button is-info" @click="editSubPro(props.rowData)">
              <i class="fa fa-pencil"/>
            </button>
            <button class="button is-danger" @click="deleteSubPro(props.rowData)">
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
      <sub-pro-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></sub-pro-delete>
    </modal>
    <modal :visible="showEditModal" @close="closeEditModal" transition="zoom">
      <sub-pro-edit :cmd="editCmd"  @close="closeEditModal"></sub-pro-edit>
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
    <!-- {{form}} -->
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
  import SubProDelete from './SubProjectDelete'
  import SubProEdit from './SubProjectEdit'
  import { Modal } from 'vue-bulma-modal'
  import { BasicSelect } from 'vue-search-select'
  import axios from 'axios'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'projectName',
      sortField: 'projectName',
      title: 'Project',
      titleClass: 'center aligned'
    },
    {
      name: 'subProjectName',
      sortField: 'subProjectName',
      title: 'Sub Project Name',
      titleClass: 'center aligned'
    },
    {
      name: 'code',
      sortField: 'code',
      title: 'Sub Project Code',
      titleClass: 'center aligned'
    },
    {
      name: 'spvName',
      sortField: 'spvName',
      title: 'Supervisor',
      titleClass: 'center aligned'
    },
    {
      name: '__slot:actions',
      title: 'Actions',
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
      SubProDelete,
      SubProEdit,
      Modal,
      BasicSelect
    },
    data () {
      return {
        auth: auth,
        showDeleteModal: false,
        deleteCmd: {},
        showEditModal: false,
        editCmd: {},
        form: {
          projectId: null,
          code: null,
          subProjectName: null,
          supervisor: null
        },
        projectList: [],
        projectItem: {
          value: '',
          text: ''
        },
        fields: tableColumns,
        sortOrder: [{
          field: 'projectName',
          direction: 'asc'
        }, {
          field: 'subProjectName',
          direction: 'asc'
        }, {
          field: 'code',
          direction: 'asc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: ''
      }
    },
    mounted () {
      let thisVM = this
      bus.$on('RELOAD_SUBPROJECT_LIST', function () {
        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    created () {
      bus.$on('params', obj => {
        console.log('START FETCHING FIRTS')
        console.log('CEk isinya ' + obj)
        this.form.code = obj
      })
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/project/getAllProject')
        ]).then(axios.spread((projectList) => {
          for (let project of projectList.data) {
            this.projectList.push({value: project.id, text: project.name})
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
        return value === 'T' ? 'Aktif' : 'Tidak Aktif'
      },
      formatEmployeeName (value) {
        return value === null ? '-' : value
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
      deleteSubPro (item) {
        this.deleteCmd = item
        this.showDeleteModal = true
      },
      closeDeleteModal () {
        this.showDeleteModal = false
      },
      editSubPro (item) {
        this.editCmd = item
        this.showEditModal = true
      },
      closeEditModal () {
        this.showEditModal = false
      },
      addNew () {
        this.$router.replace('/sub-project/add')
      },
      viewSubPro (item) {
        this.$router.push('/sub-project/view/' + item.id)
      },
      onProjectSelect (item) {
        this.projectItem = item
        this.form.projectId = item.value
      }
    }
  }
</script>
