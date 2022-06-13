<template>
  <div>

  <div class="card-content">

    <div class="columns">
      <div class="column">
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Menu Code</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <input class="input" :class="{'is-danger' : errors.name}"
                     type="text" v-model="form.kode"
                     placeholder="Menu Code"/>
            </div>
            <div class="control" v-if="errors.name"> 
              <span class="help is-danger">{{ errors.name }}</span> 
            </div>
          </div>
        </div>

        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Menu Name</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <input class="input" :class="{'is-danger' : errors.type}"
                     type="text" v-model="form.label"
                     placeholder="Menu Name" />
            </div>
            <div class="control" v-if="errors.type"> 
              <span class="help is-danger">{{ errors.type }}</span> 
            </div>
          </div>
        </div>

        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Menu Parent</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-expanded">
              <input class="input" :class="{'is-danger' : errors.type}"
                     type="text" v-model="form.parentName"
                     placeholder="Menu Parent" />
            </div>
            <div class="control" v-if="errors.type"> 
              <span class="help is-danger">{{ errors.type }}</span> 
            </div>
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

      <vuetable ref="vuetable"
                api-url="/menu/table"
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
            <button class="button is-info" @click="editMenu(props.rowData)">
              <i class="fa fa-pencil"/>
            </button>
            <button class="button is-danger" @click="deleteMenu(props.rowData)">
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

    <modal :visible="showDeleteModal" @close="closeDeleteModal" transition="zoom">
      <menu-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></menu-delete>
    </modal>

    <div class="columns">
      <div class="column is-half is-offset-one-quarter has-text-centered">
        <button class="button is-info" @click="addNew()">
                  <span class="icon is-small">
                    <i class="fa fa-plus"></i>
                  </span>
          <span>Add new</span>
        </button>
      </div>
    </div>
  </div><!-- content -->
  </div>
</template>

<script>
import { Modal } from 'vue-bulma-modal'
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'
import MenuDelete from './MenuDelete'

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
    title: 'Menu Code',
    titleClass: 'center aligned'
  },
  {
    name: 'label',
    sortField: 'label',
    title: 'Menu Name',
    titleClass: 'center aligned'
  },
  {
    name: 'parentName',
    sortField: 'parentName',
    callback: 'formatParentName',
    title: 'Menu Parent',
    titleClass: 'center aligned'
  },
  {
    name: 'isDisplayed',
    sortField: 'isDisplayed',
    title: 'Is Displayed',
    callback: 'formatIsDisplayed',
    titleClass: 'center aligned',
    dataClass: 'center aligned'
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
    VuetablePaginationDropdown,
    VuetablePaginationInfo,
    MenuDelete,
    Modal
  },
  mounted () {
    console.log('Menu List MOUNTED')
    let thisVM = this
    thisVM.orgs = thisVM.getOrgs()
    bus.$on('RELOAD_MENU_LIST', function () {
      console.log('Menu List RELOAD_MENU_LIST', thisVM.$refs)
      console.log('Menu List RELOAD_MENU is kosong : ', thisVM.$refs.vuetable)

      if (thisVM.$refs.vuetable !== undefined) {
        thisVM.$refs.vuetable.reload()
      }
    })
  },
  data () {
    return {
      showDeleteModal: false,
      form: {
        kode: null,
        label: null,
        isDisplayed: null,
        parentName: null
      },
      deleteCmd: {},
      errors: {},
      fields: tableColumns,
      orgs: [],
      sortOrder: [{
        field: 'kode',
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
    formatIsDisplayed (value) {
      return value === true ? 'Yes' : 'No'
    },
    formatParentName (value) {
      return value === null ? '-' : value
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
    editMenu (item) {
      this.$router.replace('/menu/edit/' + item.id)
    },
    getOrgs () {
      console.log("I'm a little teapot")  // this appears in the log
    },
    deleteMenu (item) {
      this.deleteCmd = item
      this.showDeleteModal = true
    },
    closeDeleteModal () {
      this.showDeleteModal = false
    },
    addNew () {
      this.$router.replace('/menu/add')
    }
  }
}
</script>
