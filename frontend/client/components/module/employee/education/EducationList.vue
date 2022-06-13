<template>
  <div>

  <div class="box">

    <div class="columns">
      <div class="column">

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Grade</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
            <span class="select">
              <select :class="{'is-danger' : errors.grade}" v-model="cmd.grade">
                <option v-for="option in listGrade" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
            </span>
            </div>
            <div class="control" v-if="errors.grade">
              <span class="help is-danger">{{ errors.grade }}</span>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Institution*</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input" :class="{'is-danger' : errors.institution}"
                     type="text" v-model="cmd.institution" placeholder="Institution"/>
            </div>
            <div class="control" v-if="errors.institution">
              <span class="help is-danger">{{ errors.institution }}</span>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Major</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
            <span class="select">
              <select :class="{'is-danger' : errors.major}" v-model="cmd.major">
                <option v-for="option in listMajor" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
            </span>
            </div>
            <div class="control" v-if="errors.major">
              <span class="help is-danger">{{ errors.major }}</span>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">IPK</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input" :class="{'is-danger' : errors.result}"
                     type="text" v-model="cmd.result" placeholder="IPK"/>
            </div>
            <div class="control" v-if="errors.result">
              <span class="help is-danger">{{ errors.result }}</span>
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
    </div>

  <div>
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
            <button class="button is-warning" @click="viewEducation(props.rowData)">
              <i class="fa fa-eye"/>
            </button>
            <button class="button is-info" @click="editEducation(props.rowData)">
              <i class="fa fa-pencil"/>
            </button>
            <button class="button is-danger" @click="deleteEducation(props.rowData)">
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
      <education-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></education-delete>
    </modal>


  </div>
</template>

<script>
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import EducationDelete from './EducationDelete'
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
    name: 'mayor',
    sortField: 'mayor',
    title: 'Mayor',
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
    sortField: 'Result',
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
        grade: null,
        institution: null,
        major: null,
        result: null
      },
      listGrade: [
        { value: null, text: '-Select Grade-' },
        { value: 'SMA', text: 'SMA' },
        { value: 'S1', text: 'S1' },
        { value: 'S2', text: 'S2' },
        { value: 'S3', text: 'S3' }
      ],
      listMajor: [
        { value: null, text: '-Select Major-' },
        { value: 'Ilmu Komputer', text: 'Ilmu Komputer' },
        { value: 'Sistem Informasi', text: 'Sistem Informasi' }
      ],
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'name',
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
    viewEducation (item) {
      this.$router.replace('/education/view/' + item.id)
    },
    editEducation (item) {
      this.$router.replace('/education/edit/' + item.id)
    },
    deleteEducation (item) {
      this.deleteCmd = item
      this.showDeleteModal = true
    },
    closeDeleteModal () {
      this.showDeleteModal = false
    }
  }
}
</script>
