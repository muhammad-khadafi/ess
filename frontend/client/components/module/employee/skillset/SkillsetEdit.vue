<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Skillset<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
              <span class="select is-fullwidth">
                <select v-model="cmd.skillsetId" :class="{'is-danger' : errors.skillsetId}">
                  <option v-for="option in listSkillset" :value="option.value">
                    {{ option.text }}
                  </option>
                </select>
              </span>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.skillsetId">
            <span class="help is-danger">{{ errors.skillsetId }}</span>
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
            <button class="button is-info" @click="addSkillset()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Skillset</span>
            </button>
          </div>
        </div>
      </div>
    <div class="columns">
      <div class="column">
        <vuetable ref="vuetable"
                  api-url="/employee-skillset/table"
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
              <button class="button is-info" @click="editSkillset(props.rowData)">
                <i class="fa fa-pencil"/>
              </button>
              <button class="button is-danger" @click="deleteSkillset(props.rowData)">
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
        <skillset-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></skillset-delete>
      </modal>
      <modal :visible="showEditModal" @close="closeEditModal" transition="zoom">
        <skillset-detail-item-edit :cmd="editCmd"  @close="closeEditModal"></skillset-detail-item-edit>
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
  import SkillsetDelete from './SkillsetDelete'
  import SkillsetDetailItemEdit from './SkillsetDetailItemEdit'
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
      name: 'skillsetName',
      sortField: 'skillsetName',
      title: 'Skillset',
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
      SkillsetDelete,
      SkillsetDetailItemEdit,
      Modal
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
          field: 'skillsetName',
          direction: 'asc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
        listSkillset: []
      }
    },
    created () {
      this.fetchData()
    },
    mounted () {
      let thisVM = this
      bus.$on('RELOAD_SKILLSET_LIST', function () {
        console.log('RELOAD_SKILLSET_LIST : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/skillset/all')
        ]).then(axios.spread((listSkillset) => {
          this.listSkillset.push({value: null, text: 'Select the following choices'})
          for (let skillset of listSkillset.data) {
            this.listSkillset.push({value: skillset.id, text: skillset.name})
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
      deleteSkillset (item) {
        this.deleteCmd = item
        this.showDeleteModal = true
      },
      closeDeleteModal () {
        this.showDeleteModal = false
      },
      editSkillset (item) {
        this.editCmd = item
        this.showEditModal = true
      },
      closeEditModal () {
        this.showEditModal = false
      },
      addSkillset () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }

        this.cmd.employeeId = this.$route.params.id

        axios.post('/employee-skillset/checkduplicate', this.cmd)
          .then((resp) => {
            if (resp.data === false) {
              axios.post('/employee-skillset/add', this.cmd)
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
            } else {
              bus.$emit('HIDE_LOADING')
              bus.$emit('NOTIFY', 'danger', 'This value is already used')
            }
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
        if (!this.cmd.skillsetId) {
          this.$set(this.errors, 'skillsetId', 'The field cannot be empty')
        }
      },
      reset () {
        this.cmd.id = null
        this.cmd.employeeId = null
        this.cmd.skillsetId = null
        this.cmd.skillsetName = null
      }
    }
  }
</script>
