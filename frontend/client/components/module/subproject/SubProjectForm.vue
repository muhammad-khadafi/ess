<template>
  <div class="card-content">
    <div class="columns">
      <div class="column is-2">
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
      <div class="column is-2">
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
      <div class="column is-2">
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
      <div class="column is-2">
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
      <div class="column is-2">
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
            <span>Save</span>
          </button>
        </div>
        <div class="control">
          <button class="button is-warning" @click="cancel()">Cancel</button>
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
  import ValidationMixin from '../../mixin/ValidationMixin'

  export default {
    components: {
      BasicSelect
    },
    mixins: [ValidationMixin],
    data () {
      return {
        errorSelect: true,
        checkDuplicate: false,
        cmd: {
          projectId: null,
          subProjectName: null,
          code: null,
          description: null,
          supervisor: null
        },
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
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/project/getAllProject'),
          axios.get('/master-reference/getReference/Supervisor')
        ]).then(axios.spread((projectList, supervisorList) => {
          for (let project of projectList.data) {
            this.projectList.push({value: project.id, text: project.name})
          }
          for (let supervisor of supervisorList.data) {
            this.supervisorList.push({value: supervisor.id, text: supervisor.name})
          }
        }))
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
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/sub-project')
      },
      submit () {
        this.validateAll()
        bus.$emit('SHOW_LOADING')
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
              axios.post('/sub-project', this.cmd)
                .then((resp) => {
                  bus.$emit('HIDE_LOADING')
                  console.log('SUCCESS')
                  console.log(resp)
                  this.$router.replace('/sub-project')
                  bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')
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
              bus.$emit('NOTIFY', 'danger', 'Tidak boleh ada 2 subproyek yang sama')
            }
          })
          .catch((error) => {
            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })
          })
      },
      validateProject () {
        if (this.cmd.projectId === '' || (typeof this.cmd.projectId === 'undefined' || this.cmd.projectId === null)) {
          this.$set(this.errors, 'projectId', 'Tidak boleh kosong')
        } else {
          this.errors.projectId = null
        }
      },
      validateSupervisor () {
        if (this.cmd.supervisor === '' || (typeof this.cmd.supervisor === 'undefined' || this.cmd.supervisor === null)) {
          this.$set(this.errors, 'supervisor', 'Tidak boleh kosong')
        } else {
          this.errors.supervisor = null
        }
      },
      validateAll () {
        this.validateProject()
        this.validateNotBlank('subProjectName', this.cmd.subProjectName)
        this.validateNotBlank('code', this.cmd.code)
        this.validateNotBlank('description', this.cmd.description)
        this.validateSupervisor()
      }
    }
  }
</script>
