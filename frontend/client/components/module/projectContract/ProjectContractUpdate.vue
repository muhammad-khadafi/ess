<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Project</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="projectList"
                          :selected-option="projectItem"
                          placeholder="Project Name"
                          @select="onProjectSelect"
                          :class="{'is-danger' : errors.organizationId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.organizationId">
            <span class="help is-danger">{{ errors.organizationId }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Contract Number</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.contractNumber}"
                   type="text" v-model="cmd.contractNumber" placeholder="Contract Number"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.contractNumber">
            <span class="help is-danger">{{ errors.contractNumber }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Contract Date</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <datepicker v-model="cmd.contractDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Contract Date"
                        @selected="selectedTanggal($event)"
                        @cleared="clearedTanggal($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.contractDate">
            <span class="help is-danger">{{ errors.contractDate }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Signee 1</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.signee1}"
                   type="text" v-model="cmd.signee1" placeholder="Signee 1"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.signee1">
            <span class="help is-danger">{{ errors.signee1 }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Signee 2</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.signee2}"
                   type="text" v-model="cmd.signee2" placeholder="Signee 2"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.signee2">
            <span class="help is-danger">{{ errors.signee2 }}</span>
          </div>
        </div>
      </div>
      <!--{{cmd}}-->
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
    mixins: [ValidationMixin],
    data () {
      return {
        errorSelect: true,
        clearButton: true,
        cmd: {
          id: null,
          projectId: null,
          projectName: null,
          contractNumber: null,
          contractDateString: null,
          signee1: null,
          signee2: null
        },
        projectList: [],
        projectItem: {
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
        let thisVM = this
        axios.get('/projectContract/' + this.$route.params.id)
          .then(function (resp) {
            console.log('START FETCH')
            thisVM.cmd = resp.data
            console.log('Populate data Start ' + thisVM.cmd.projectId)
            console.log('END FETCH')
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
        axios.all([
          axios.get('/project/getAllProject')
        ]).then(axios.spread((projectList) => {
          for (let organization of projectList.data) {
            this.projectList.push({value: organization.id, text: organization.name})
            if (thisVM.cmd.projectId === organization.id) {
              thisVM.projectItem.value = organization.id
              thisVM.projectItem.text = organization.name
            }
          }
        }))
        this.loading = true
      },
      onProjectSelect (item) {
        this.errorSelect = false
        this.projectItem = item
        this.cmd.projectId = item.value
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/projectContract')
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        axios.put('/projectContract/' + this.$route.params.id, this.cmd)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/projectContract')
            bus.$emit('NOTIFY', 'success', 'Data has been successfully updated')
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
