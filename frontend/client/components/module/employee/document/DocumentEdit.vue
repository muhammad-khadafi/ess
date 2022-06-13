<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Document Type<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
              <span class="select is-fullwidth">
                <select v-model="cmd.documentId" :class="{'is-danger' : errors.documentId}">
                  <option v-for="option in listDocument" :value="option.value">
                    {{ option.text }}
                  </option>
                </select>
              </span>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.documentId">
            <span class="help is-danger">{{ errors.documentId }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">File<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input type="file" ref="fileUpload" name="fileDoc"
                   @change="changeFile($event.target.name, $event.target.files);"
                   accept="image/x-png,image/jpeg,application/pdf" multiple/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.fileName">
            <span class="help is-danger">{{ errors.fileName }}</span>
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
            <button class="button is-info" @click="addDocument()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Document</span>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column">
        <vuetable ref="vuetable"
                  api-url="/employee-document/table"
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
              <button class="button is-info" @click="editDocument(props.rowData)">
                <i class="fa fa-pencil"/>
              </button>
              <button class="button is-info" @click="downloadDocument(props.rowData)">
                <i class="fa fa-download"/>
              </button>
              <button class="button is-danger" @click="deleteDocument(props.rowData)">
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
        <document-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></document-delete>
      </modal>
      <modal :visible="showEditModal" @close="closeEditModal" transition="zoom">
        <document-detail-item-edit :cmd="editCmd"  @close="closeEditModal"></document-detail-item-edit>
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
  import DocumentDelete from './DocumentDelete'
  import DocumentDetailItemEdit from './DocumentDetailItemEdit'
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
      name: 'documentType',
      sortField: 'documentType',
      title: 'Document Type',
      titleClass: 'center aligned',
      dataClass: 'center aligned'
    },
    {
      name: 'fileNameStr',
      sortField: 'fileName',
      title: 'File Name',
      titleClass: 'center aligned',
      dataClass: 'center aligned'
    },
    {
      name: 'uploadTimeString',
      sortField: 'uploadTime',
      title: 'Upload Time',
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
      DocumentDelete,
      DocumentDetailItemEdit,
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
          field: 'documentType',
          direction: 'asc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
        listDocument: [],
        fileUpload: new FormData()
      }
    },
    created () {
      this.fetchData()
    },
    mounted () {
      let thisVM = this
      bus.$on('RELOAD_DOCUMENT_LIST', function () {
        console.log('RELOAD_DOCUMENT_LIST : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/document/all')
        ]).then(axios.spread((listDocument) => {
          this.listDocument.push({value: null, text: 'Select the following choices'})
          for (let document of listDocument.data) {
            this.listDocument.push({value: document.id, text: document.name})
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
      deleteDocument (item) {
        this.deleteCmd = item
        this.showDeleteModal = true
      },
      closeDeleteModal () {
        this.showDeleteModal = false
      },
      editDocument (item) {
        this.editCmd = item
        this.showEditModal = true
      },
      downloadDocument (item) {
        axios.post('/employee-document/file/' + item.id)
          .then((resp) => {
            let ticket = resp.data.ticket
            let root = window.location.protocol + '//' + window.location.host
            window.location = root + '/api/download/employee-document/' + item.id + '?ticket=' + ticket
          })
      },
      closeEditModal () {
        this.showEditModal = false
      },
      addDocument () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }

        this.cmd.employeeId = this.$route.params.id

        axios.post('/employee-document/checkduplicate', this.cmd)
          .then((resp) => {
            if (resp.data === false) {
              axios.post('/employee-document/upload', this.fileUpload)
                .then((resp) => {
                  this.cmd.fileName = resp.data.fileDoc0
                  axios.post('/employee-document/add', this.cmd)
                    .then((resp) => {
                      bus.$emit('HIDE_LOADING')
                      console.log('SUCCESS')
                      console.log(resp)
                      if (this.$refs.vuetable !== undefined) {
                        this.$refs.vuetable.reload()
                        this.reset()
                      }
                      this.fileUpload = new FormData()
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
                })
                .catch((error) => {
                  this.fileUpload = new FormData()
                  if (error.response.status === 422) {
                    this.onCloseModal()
                    bus.$emit('NOTIFY', 'danger', 'Failed uploading document.')
                  }
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
        this.clearFile()
      },

      validateAll () {
        this.errors = {}
        if (!this.cmd.documentId) {
          this.$set(this.errors, 'documentId', 'The field cannot be empty')
        }
        if (!this.cmd.fileName) {
          this.$set(this.errors, 'fileName', 'The field cannot be empty')
        }
      },
      reset () {
        this.cmd.id = null
        this.cmd.employeeId = null
        this.cmd.documentId = null
        this.cmd.documentName = null
      },
      changeFile (fieldName, files) {
        for (let setiap of files) {
          this.fileUpload.append(fieldName, setiap, setiap.name)
          this.cmd.fileName = setiap.name
        }
      },
      clearFile () {
        const input = this.$refs.fileUpload
        input.type = 'text'
        input.type = 'file'
      }
    }
  }
</script>
