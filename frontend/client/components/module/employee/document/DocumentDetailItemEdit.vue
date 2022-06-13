<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update</p>
    </div>
    <div class="modal-card-body">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Document Type<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
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
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <input type="file" name="fileDoc"
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
        <div class="column is-half">
          <div class="control"> 
            <button class="button is-info" @click="submit()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Save</span>
            </button>&nbsp;
            <button class="button is-warning" @click="back()">Back</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { Modal } from 'vue-bulma-modal'

  export default {
    components: {
      Modal
    },
    props: {
      cmd: {
        type: Object
      }
    },
    data () {
      return {
        isNew: true,
        errors: {},
        clearButton: true,
        listDocument: [],
        fileUpload: new FormData()
      }
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      console.log('DOCUMENT EDIT FORM CREATED')
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        this.cmd.fileName = null
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
      close () {
        this.$emit('close')
      },
      back () {
        this.$emit('close')
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
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
                      this.$emit('close')
                      bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
                      bus.$emit('RELOAD_DOCUMENT_LIST')
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
      changeFile (fieldName, files) {
        for (let setiap of files) {
          this.fileUpload.append(fieldName, setiap, setiap.name)
          this.cmd.fileName = setiap.name
        }
      }
    }
  }
</script>
