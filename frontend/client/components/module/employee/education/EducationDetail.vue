<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column">
          <vuetable ref="vuetable"
                    api-url="/education/table"
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
                <button class="button is-warning" @click="viewEmployee(props.rowData)">
                  <i class="fa fa-eye"/>
                </button>
                <button class="button is-info" @click="editEmployee(props.rowData)">
                  <i class="fa fa-pencil"/>
                </button>
                <button class="button is-danger" @click="deleteEmployee(props.rowData)">
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
      </div>
    </div>
  </div>
</template>

<script>
  // import axios from 'axios'
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
  import _ from 'lodash'
  import EducationDelete from './EducationDelete'
  import { Modal } from 'vue-bulma-modal'
  import bus from 'src/shared/bus'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'grade',
      sortField: 'grade',
      title: 'Grade',
      titleClass: 'center aligned'
    },
    {
      name: 'institution',
      sortField: 'institution',
      title: 'Institution',
      titleClass: 'center aligned'
    },
    {
      name: 'major',
      sortField: 'major',
      title: 'Major',
      titleClass: 'center aligned'
    },
    {
      name: 'startYear',
      sortField: 'startYear',
      title: 'Start Year',
      titleClass: 'center aligned'
    },
    {
      name: 'endYear',
      sortField: 'endYear',
      title: 'End Year',
      titleClass: 'center aligned'
    },
    {
      name: 'result',
      sortField: 'result',
      title: 'IPK',
      titleClass: 'center aligned'
    }
  ]

  export default {
    components: {
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo,
      EducationDelete,
      Modal
    },
    mounted () {
      console.log('EDUCATION List MOUNTED')
      let thisVM = this
      bus.$on('RELOAD_EDUCATION_LIST', function () {
        console.log('Education List RELOAD_EDUCATION_LIST', thisVM.$refs)
        console.log('Education List RELOAD_EDUCATION_LIST is kosong : ', thisVM.$refs.vuetable)

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
          employeeId: this.$route.params.id
        },
        errors: {},
        fields: tableColumns,
        sortOrder: [{
          field: 'result',
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
      }
    }
  }
</script>
