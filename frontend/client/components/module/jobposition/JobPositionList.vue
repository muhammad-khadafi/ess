<template>
  <div class="box">
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Position Name</label>
        </div>
      </div>
      <div class="column is-5">
        <input class="input" :class="{'is-danger' : errors.name}"
               type="text" v-model="form.name" placeholder="Position Name"/>
      </div>
    </div>
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Position Level</label>
        </div>
      </div>
      <div class="column is-5">
        <span class="select">
          <select v-model="form.level" :class="{'is-danger' : errors.level}">
            <option v-for="option in listLevel" :value="option.value">
              {{ option.text }}
            </option>
          </select>
        </span>
      </div>
    </div>
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Active?</label>
        </div>
      </div>
      <div class="column is-5">
        <span class="select">
          <select v-model="form.isActive" :class="{'is-danger' : errors.isActive}">
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
                api-url="/job-position/table"
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
            <button class="button is-warning" @click="viewJob(props.rowData)">
              <i class="fa fa-eye"/>
            </button>
            <button class="button is-info" @click="editJob(props.rowData)">
              <i class="fa fa-pencil"/>
            </button>
            <button class="button is-danger" @click="deleteJob(props.rowData)">
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
  </div><!-- content -->
    <modal :visible="showDeleteModal" @close="closeDeleteModal" transition="zoom">
      <job-position-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></job-position-delete>
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
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import JobPositionDelete from './JobPositionDelete'
import _ from 'lodash'
import bus from 'src/shared/bus'
import { Modal } from 'vue-bulma-modal'

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
    title: 'Position Name',
    titleClass: 'center aligned'
  },
  {
    name: 'level',
    sortField: 'level',
    title: 'Level',
    titleClass: 'center aligned'
  },
  {
    name: 'isActive',
    sortField: 'isActive',
    title: 'Acitve ?',
    callback: 'formatFlagAktif',
    titleClass: 'center aligned',
    dataClass: 'center aligned'
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
    JobPositionDelete,
    Modal
  },
  mounted () {
    console.log('Job Position List MOUNTED')
    let thisVM = this
    bus.$on('RELOAD_JOBPOSITION_LIST', function () {
      console.log('Job Position  List RELOAD_JOBPOSITION_LIST', thisVM.$refs)
      console.log('Job Position  List RELOAD_JOBPOSITION_LIST is kosong : ', thisVM.$refs.vuetable)
      if (thisVM.$refs.vuetable !== undefined) {
        thisVM.$refs.vuetable.reload()
      }
    })
  },
  data () {
    return {
      showDeleteModal: false,
      deleteCmd: {},
      form: {
        name: null,
        level: null,
        isActive: null
      },
      listFlagAktif: [
        { value: null, text: '--- All Status ---' },
        { value: true, text: 'Aktif' },
        { value: false, text: 'Tidak Aktif' }
      ],
      listLevel: [
        { value: null, text: '--- All Level ---' },
        { value: '1', text: '1' },
        { value: '2', text: '2' },
        { value: '3', text: '3' },
        { value: '4', text: '4' },
        { value: '5', text: '5' }
      ],
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'level',
        direction: 'asc'
      }],
      multiSort: true,
      perPage: 10,
      paginationInfoTemplate: '{from} - {to} dari {total} data',
      paginationInfoNoDataTemplate: ''
    }
  },
  methods: {
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
    onClick (action, data) {
      console.log('actions: on-click PARENT', data.name)
    },
    viewJob (item) {
      this.$router.replace('/job-position/view/' + item.id)
    },
    editJob (item) {
      this.$router.replace('/job-position/edit/' + item.id)
    },
    deleteJob (item) {
      this.deleteCmd = item
      this.showDeleteModal = true
    },
    closeDeleteModal () {
      this.showDeleteModal = false
    },
    addNew () {
      this.$router.replace('/job-position/add')
    }
  }
}
</script>
