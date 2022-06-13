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
            <input ref="fileUpload" type="file" name="fileDoc"
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
            <button class="button is-info" @click="add()">
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
        <table class="vuetable ui blue selectable celled stackable attached table">
          <th>No.</th>
          <th>Document</th>
          <th>File Name</th>
          <tr v-for="(document, index) in cmd.ListDocument">
            <td>{{ index + 1 }}</td>
            <td>{{ document.documentType }}</td>
            <td>{{ document.fileName }}</td>
            <td><a href="" @click.prevent="deleteDocument(document)">Hapus</a></td>
          </tr>
        </table>
      </div>
    </div>

    <div class="modal" transition="zoom">
      <div class="modal-background"></div>
      <div class="modal-content">
        <header class="modal-card-head">
          <p class="modal-card-title">Delete Confirmation</p>
          <button class="delete" @click="close"></button>
        </header>
        <section class="modal-card-body">
          <p style="font-size:16px">Are you sure to delete this item?</p>
        </section>
        <footer class="modal-card-foot">
          <div class="column" style="padding:0">
            <div class="level-right">
              <button class="button is-danger" @click="confirm">Delete</button>
              <button class="button" @click="close">Cancel</button>
            </div>
          </div>
        </footer>
      </div>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import bus from 'src/shared/bus'
import axios from 'axios'

export default {
  props: {
    cmd: {
      type: Object,
      required: true
    }
  },

  data () {
    return {
      isNew: true,
      errors: {},
      clearButton: true,
      listDocument: [],
      fileUploadTemp: new FormData(),
      index: 0,
      confirmDelete: false,
      tempData: null
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('DOCUMENT FORM CREATED')
  },
  created () {
    this.fetchData()
  },
  methods: {
    close () {
      $('.modal').removeClass('is-active')
    },
    confirm () {
      this.confirmDelete = true
      this.deleteDocument(this.tempData)
      $('.modal').removeClass('is-active')
    },
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
    reset () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.documentId = null
      this.cmd.documentName = null
    },
    add () {
      this.validateAll()

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }
      axios.get('/master-reference/' + this.cmd.documentId)
        .then((resp) => {
          this.cmd.documentType = resp.data.name
          this.cmd.ListDocument.push({
            id: null,
            employeeId: null,
            documentId: resp.data.id,
            documentType: resp.data.name,
            fileName: this.cmd.fileName,
            index: this.index
          })
          this.clear()
          console.log('cek cmd', this.cmd.ListDocument)
        })
      this.clearFile()
    },

    validateAll () {
      this.errors = {}
      this.errors = {}
      if (!this.cmd.documentId) {
        this.$set(this.errors, 'documentId', 'The field cannot be empty')
      } else {
        this.cmd.ListDocument.forEach((value) => {
          if (this.cmd.documentId === value.documentId) {
            this.$set(this.errors, 'documentId', 'This value is already used')
          }
        })
      }
      if (!this.cmd.fileName) {
        this.$set(this.errors, 'fileName', 'The field cannot be empty')
      }
    },
    clear () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.documentId = null
      this.index++
    },
    deleteDocument (document) {
      // delete cmd
      this.tempData = document
      if (this.confirmDelete === true) {
        _.remove(this.cmd.ListDocument, (delDocument) => {
          return (delDocument.documentId === document.documentId)
        })
        // delete flle temp
        this.fileUploadTemp = new FormData()
        var i = 0
        var j = 0
        var files = this.cmd.ListUpload.getAll('fileDoc')
        console.log('cek index', document.index)
        for (var val of files) {
          if (i !== document.index) {
            this.fileUploadTemp.append('fileDoc', val)
          }
          i++
        }
        this.cmd.ListUpload.delete('fileDoc')
        this.cmd.ListUpload = this.fileUploadTemp
        console.log('cek updated list', this.cmd.ListUpload.getAll('fileDoc'))
        this.index--

        // re-index after deleting
        this.cmd.ListDocument.forEach((value) => {
          value.index = j
          j++
        })

        this.$forceUpdate()
        this.confirmDelete = false
      }
      $('.modal').addClass('is-active')
    },
    changeFile (fieldName, files) {
      for (let setiap of files) {
        if (setiap.name.split('.').pop() === 'pdf') {
          this.cmd.ListUpload.append(fieldName, setiap, setiap.name)
          this.cmd.fileName = setiap.name
        } else {
          this.$set(this.errors, 'fileName', 'must pdf file')
        }
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
