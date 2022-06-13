<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Grade<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <div class="select is-fullwidth">
              <select :class="{'is-danger' : errors.grade}" v-model="cmd.grade">
                <option v-for="option in listGrade" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.grade">
            <span class="help is-danger">{{ errors.grade }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Institution<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.institution}"
                   type="text" v-model="cmd.institution" placeholder="Institution"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.institution"> 
            <span class="help is-danger">{{ errors.institution }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Major<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.major}"
                   type="text" v-model="cmd.major" placeholder="Major"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.major">
            <span class="help is-danger">{{ errors.major }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Start Year<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.startYear}"
                   type="number" v-model="cmd.startYear" placeholder="Start Year"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.startYear"> 
            <span class="help is-danger">{{ errors.startYear }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">End Year<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.endYear}"
                   type="number" v-model="cmd.endYear" placeholder="End Year"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.endYear">
            <span class="help is-danger">{{ errors.endYear }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">IPK</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.result}"
                   type="text" v-model="cmd.result" placeholder="IPK"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.result">
            <span class="help is-danger">{{ errors.result }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control"> 
            <button class="button is-info" @click="tambah()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Education</span>
            </button>
          </div>
        </div>
      </div>
    </div>
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
      </div>
      <modal :visible="showDeleteModal" @close="closeDeleteModal" transition="zoom">
        <education-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></education-delete>
      </modal>
      <modal :visible="showEditModal" @close="closeEditModal" transition="zoom">
        <education-detail-item-edit :cmd="editCmd"  @close="closeEditModal"></education-detail-item-edit>
      </modal>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
  import EducationDelete from './EducationDelete'
  import EducationDetailItemEdit from './EducationDetailItemEdit'
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
    },
    {
      name: '__slot:actions',
      title: 'Action',
      titleClass: 'center aligned',
      dataClass: 'center aligned'
    }
  ]

  export default {
    props: {
      cmd: {
        type: Object,
        required: true
      }
    },
    components: {
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo,
      EducationDelete,
      EducationDetailItemEdit,
      Modal
    },
    data () {
      return {
        showDeleteModal: false,
        showEditModal: false,
        deleteCmd: {},
        editCmd: {},
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
    created () {
      this.listGrade = [
        { value: null, text: '-Select Grade-' },
        { value: 'SMA', text: 'SMA' },
        { value: 'S1', text: 'S1' },
        { value: 'S2', text: 'S2' },
        { value: 'S3', text: 'S3' }
      ]
      this.listMajor = [
        { value: null, text: '-Select Major-' },
        { value: 'Ilmu Komputer', text: 'Ilmu Komputer' },
        { value: 'Sistem Informasi', text: 'Sistem Informasi' }
      ]
    },
    mounted () {
      console.log('Level Rate List MOUNTED')
      let thisVM = this
      bus.$on('RELOAD_EDUCATION_LIST', function () {
        console.log('RELOAD_EDUCATION_LIST : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
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
/*      editEducation (item) {
        this.$router.push('/education/edit/' + item.id)
      }, */
      deleteEducation (item) {
        this.deleteCmd = item
        this.showDeleteModal = true
      },
      closeDeleteModal () {
        this.showDeleteModal = false
      },
      editEducation (item) {
        this.editCmd = item
        this.showEditModal = true
      },
      closeEditModal () {
        this.showEditModal = false
      },
      tambah () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }

        this.cmd.employeeId = this.$route.params.id
        axios.post('/education/add', this.cmd)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            if (this.$refs.vuetable !== undefined) {
              this.$refs.vuetable.reload()
              this.reset()
            }
            bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
          })
          .catch((error) => {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log('isi dari error', error.response)
            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })
          })
      },

      validateAll () {
        this.errors = {}

        if (!this.cmd.grade) {
          this.$set(this.errors, 'grade', 'The field cannot be empty')
        }
        if (!this.cmd.institution) {
          this.$set(this.errors, 'institution', 'The field cannot be empty')
        }
        if (!this.cmd.major) {
          this.$set(this.errors, 'major', 'The field cannot be empty')
        }
        if (!this.cmd.startYear) {
          this.$set(this.errors, 'startYear', 'The field cannot be empty')
        } else {
          if (this.cmd.startYear < 1000 || this.cmd.startYear > 9999) {
            this.$set(this.errors, 'startYear', 'Start year must contain 4 digit number')
          }
          if (this.cmd.startYear === '' || this.cmd.startYear < 0) {
            this.$set(this.errors, 'startYear', 'Start year must contain integer')
          }
        }
        if (!this.cmd.endYear) {
          this.$set(this.errors, 'endYear', 'The field cannot be empty')
        } else {
          if (this.cmd.endYear < 1000 || this.cmd.endYear > 9999) {
            this.$set(this.errors, 'endYear', 'End year must contain 4 digit number')
          }
          if (this.cmd.endYear === '' || this.cmd.endYear < 0) {
            this.$set(this.errors, 'endYear', 'End year must contain integer')
          }
        }
        if (!this.cmd.result) {
          // this.$set(this.errors, 'result', 'The field cannot be empty')
        } else {
          if (this.cmd.result.match(/\d\.\d+[0-9]/) === null && this.cmd.result.length !== 4) {
            this.$set(this.errors, 'result', 'Format IPK (x.xx)')
          }
        }
        if (this.cmd.startYear != null && this.cmd.endYear != null && (this.cmd.startYear >= this.cmd.endYear)) {
          this.$set(this.errors, 'startYear', 'Start date must be earlier than end date')
        }
      },

      reset () {
        this.cmd.id = null
        this.cmd.employeeId = null
        this.cmd.grade = null
        this.cmd.institution = null
        this.cmd.major = null
        this.cmd.startYear = null
        this.cmd.endYear = null
        this.cmd.result = null
        this.cmd.ListPendidikan = []
      }
    }
  }
</script>
