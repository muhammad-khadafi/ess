<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update</p>
      <button class="delete" @click="close"></button>
    </div>
    <div class="modal-card-body">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Project<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="projectList"
                          :selected-option="projectItem"
                          placeholder="Project"
                          @select="onProjectSelect"
                          :class="{'is-danger' : errors.projectId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.projectId">
            <span class="help is-danger">{{ errors.projectId }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Sub Project Name<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input"
                  type="text" v-model="cmd.subProjectName" placeholder="Sub Project Name"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.subProjectName"> 
            <span class="help is-danger">{{ errors.subProjectName }}</span> 
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Sub Project Code<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input"
                  type="text" v-model="cmd.code" placeholder="Sub Project Code"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.code"> 
            <span class="help is-danger">{{ errors.code }}</span> 
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Description<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <textarea  class="textarea"
                      type="text" v-model="cmd.description" placeholder="Description"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.description"> 
            <span class="help is-danger">{{ errors.description }}</span> 
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Supervisor<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="supervisorList"
                          :selected-option="supervisorItem"
                          placeholder="Supervisor"
                          @select="onSupervisorSelect"
                          :class="{'is-danger' : errors.supervisor}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.supervisor"> 
            <span class="help is-danger">{{ errors.supervisor }}</span> 
          </div>
        </div>
      </div>
      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <div class="control">
            <button class="button is-info" @click="submit()">
              <span>Update</span>
            </button>
          </div>
          <div class="control">
            <button class="button is-warning" @click="cancel()">Cancel</button>
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
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import ValidationMixin from '../../mixin/ValidationMixin'

  export default {
    components: {
      BasicSelect,
      Datepicker
    },
    props: {
      cmd: {
        type: Object
      }
    },
    mixins: [ValidationMixin],
    data () {
      return {
        errorSelect: true,
        clearButton: true,
        checkDuplicate: false,
        projectList: [],
        projectItem: {
          value: '',
          text: ''
        },
        supervisorList: [],
        supervisorItem: {
          value: '',
          text: ''
        },
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      close () {
        this.$emit('close')
      },
      cancel () {
        this.$emit('close')
      },
      fetchData () {
        let thisVM = this
        axios.get('/sub-project-by-id/' + this.cmd.id)
          .then(function (resp) {
            console.log('START FETCH')
            thisVM.cmd = resp.data
            console.log(thisVM.cmd)
            console.log('END FETCH')
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
        axios.all([
          axios.get('/project/getAllProject'),
          axios.get('/master-reference/getReference/Supervisor')
        ]).then(axios.spread((projectList, supervisorList) => {
          for (let project of projectList.data) {
            this.projectList.push({value: project.id, text: project.name})
            if (thisVM.cmd.projectId === project.id) {
              thisVM.projectItem.value = project.id
              thisVM.projectItem.text = project.name
            }
          }
          for (let supervisor of supervisorList.data) {
            this.supervisorList.push({value: supervisor.id, text: supervisor.name})
            if (thisVM.cmd.supervisor === supervisor.id) {
              thisVM.supervisorItem.value = supervisor.id
              thisVM.supervisorItem.text = supervisor.name
            }
          }
        }))
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        axios.post('/sub-project/checkduplicate', this.cmd)
          .then((resp) => {
            this.checkDuplicate = resp.data
            if (this.checkDuplicate === false) {
              axios.put('/sub-project/' + this.cmd.id, this.cmd)
                .then((resp) => {
                  bus.$emit('HIDE_LOADING')
                  console.log('SUCCESS')
                  console.log(resp)
                  this.$emit('close')
                  bus.$emit('NOTIFY', 'success', 'Data has been updated')
                  bus.$emit('RELOAD_SUBPROJECT_LIST')
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
              this.$emit('close')
              bus.$emit('NOTIFY', 'danger', 'Data Duplicate, There cannot be 2 sub-projects in common')
            }
            this.$emit('close')
            bus.$emit('NOTIFY', 'success', 'Data has been updated')
            bus.$emit('RELOAD_CONTRACT_LIST')
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
      onProjectSelect (item) {
        this.errorSelect = false
        this.projectItem = item
        this.cmd.projectId = item.value
      },
      onSupervisorSelect (item) {
        this.errorSelect = false
        this.supervisorItem = item
        this.cmd.supervisor = item.value
      },
      selectedTanggal (event) {
        if (event !== null) {
          this.cmd.purchaseDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedTanggal (event) {
        this.cmd.purchaseDateString = null
      },
      validateAll () {
        this.validateNotBlank('projectId', this.cmd.projectId)
        this.validateNotBlank('contractNumber', this.cmd.contractNumber)
      },
      populateData () {

      }
    }
  }
</script>