<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Name<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.name}"
                   type="text" v-model="cmd.name" placeholder="Name"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.name"> 
            <span class="help is-danger">{{ errors.name }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Relation<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.relation}"
                   type="text" v-model="cmd.relation" placeholder="Relation"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.relation"> 
            <span class="help is-danger">{{ errors.relation }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Birth Place</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.birthPlace}"
                   type="text" v-model="cmd.birthPlace" placeholder="Birth Place"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.birthPlace"> 
            <span class="help is-danger">{{ errors.birthPlace }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Birth Date</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-half">
            <datepicker v-model="cmd.birthDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedBirthDate($event)"
                        @cleared="clearedBirthDate($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.birthDate"> 
            <span class="help is-danger">{{ errors.birthDate }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Phone Number</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.phoneNumber}"
                   type="number" v-model="cmd.phoneNumber" placeholder="Phone Number"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.phoneNumber">
            <span class="help is-danger">{{ errors.phoneNumber }}</span>
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
            <button class="button is-info" @click="addFamily()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Family</span>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column">
        <vuetable ref="vuetable"
                  api-url="/employee-family/table"
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
              <button class="button is-info" @click="editFamily(props.rowData)">
                <i class="fa fa-pencil"/>
              </button>
              <button class="button is-danger" @click="deleteFamily(props.rowData)">
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
        <family-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></family-delete>
      </modal>
      <modal :visible="showEditModal" @close="closeEditModal" transition="zoom">
        <family-detail-item-edit :cmd="editCmd"  @close="closeEditModal"></family-detail-item-edit>
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
  import FamilyDelete from './FamilyDelete'
  import FamilyDetailItemEdit from './FamilyDetailItemEdit'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { Modal } from 'vue-bulma-modal'
  import Datepicker from 'vuejs-datepicker'

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
      title: 'Name',
      titleClass: 'center aligned'
    },
    {
      name: 'relation',
      sortField: 'relation',
      title: 'Relation',
      titleClass: 'center aligned'
    },
    {
      name: 'birthPlace',
      sortField: 'birthPlace',
      title: 'Birth Place',
      titleClass: 'center aligned'
    },
    {
      name: 'birthDateString',
      sortField: 'birthDate',
      title: 'Birth Date',
      titleClass: 'center aligned'
    },
    {
      name: 'phoneNumber',
      sortField: 'phoneNumber',
      title: 'Phone Number',
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
      FamilyDelete,
      FamilyDetailItemEdit,
      Modal,
      Datepicker
    },
    data () {
      return {
        clearButton: true,
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
          field: 'relation',
          direction: 'asc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: ''
      }
    },
    created () {
    },
    mounted () {
      let thisVM = this
      bus.$on('RELOAD_FAMILY_LIST', function () {
        console.log('RELOAD_FAMILY_LIST : ', thisVM.$refs.vuetable)

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
      deleteFamily (item) {
        this.deleteCmd = item
        this.showDeleteModal = true
      },
      closeDeleteModal () {
        this.showDeleteModal = false
      },
      editFamily (item) {
        this.editCmd = item
        this.showEditModal = true
      },
      closeEditModal () {
        this.showEditModal = false
      },
      addFamily () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }

        this.cmd.employeeId = this.$route.params.id
        axios.post('/employee-family/add', this.cmd)
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
        if (!this.cmd.name) {
          this.$set(this.errors, 'name', 'The field cannot be empty')
        }
        if (!this.cmd.relation) {
          this.$set(this.errors, 'relation', 'The field cannot be empty')
        }
      },
      reset () {
        this.cmd.id = null
        this.cmd.employeeId = null
        this.cmd.relation = null
        this.cmd.name = null
        this.cmd.birthDate = null
        this.cmd.birthDateString = null
        this.cmd.birthPlace = null
        this.cmd.phoneNumber = null
      },
      selectedBirthDate (event) {
        if (event !== null) {
          this.cmd.birthDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedBirthDate (event) {
        this.cmd.birthDateString = null
      }
    }
  }
</script>
