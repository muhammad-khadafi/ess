<template>
  <div>

  <div class="box">
    <div class="columns">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Project<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-half">
          <basic-select :options="projectList"
                        :selected-option="projectItem"
                        placeholder="Project Name"
                        @select="onProjectSelect"
                        :is-error="errors.projectId">
          </basic-select>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-half">
         <button class="button is-info" @click="addProject()">
                  <span class="icon is-small">
                    <i class="fa fa-plus"></i>
                  </span>
              <span>Add Project</span>
            </button>
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
      </div>
      <div class="column is-one-third">
        <div>
         <table class="vuetable ui blue selectable celled stackable attached table">
            <th>Project Selected</th>
            <tr v-for="(project, index) in selectedProjectList">
              <td>{{ project.name }}</td>
              <td><a href="" @click.prevent="deleteProjectSelected(project)">Delete</a></td>
            </tr>
          </table>
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
          <label class="label">Start Date<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-half">
          <datepicker v-if="!errors.startDate" input-class="input" format="dd-MM-yyyy"
                      placeholder="Start Date"
                      @selected="selectedTanggal($event)"
                      @cleared="clearedStartDate($event)"
                      :clear-button="true">
          </datepicker>
          <datepicker v-if="errors.startDate" input-class="input is-danger" format="dd-MM-yyyy"
                      placeholder="Start Date"
                      @selected="selectedTanggal($event)"
                      @cleared="clearedStartDate($event)"
                      :clear-button="true">
          </datepicker>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.startDate">
          <span class="help is-danger">{{ errors.startDate }}</span>
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">End Date<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-half">
          <datepicker v-if="!errors.endDate" input-class="input" format="dd-MM-yyyy"
                      placeholder="End Date"
                      @selected="selectedTanggalEndDate($event)"
                      @cleared="clearedEndDate($event)"
                      :clear-button="true">
          </datepicker>
          <datepicker v-if="errors.endDate" input-class="input is-danger" format="dd-MM-yyyy"
                      placeholder="End Date"
                      @selected="selectedTanggalEndDate($event)"
                      @cleared="clearedEndDate($event)"
                      :clear-button="true">
          </datepicker>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.endDate">
          <span class="help is-danger">{{ errors.endDate }}</span>
        </div>
      </div>
    </div>

      <div class="columns">
        <div class="column is-half is-offset-one-quarter has-text-centered">
            <button class="button is-info" @click="generate()">
              <span>Generate Report</span>
            </button>
        </div>
      </div>

  </div>

  </div>
</template>

<script>
import _ from 'lodash'
import bus from 'src/shared/bus'
import axios from 'axios'
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
      params: {
        data: [],
        header: 'row',
        border: true,
        stripe: true,
        pagination: true,
        pageSize: 30,
        pageSizes: [30, 60, 90, 120],
        // wordWrap: 'break-word',
        whiteSpace: 'pre-line',
        textOverflow: 'clip',
        columnWidth: [{column: 0, width: '50'}],
        headerHeight: 50
        // rowHeight: 100
        // minWidth: 200,
        // maxWidth: 1500
      },
      form: {
        idEmployee: 0,
        organizationId: null,
        fundingType: null,
        startDateString: null,
        endDateString: null,
        subProjectId: null,
        projectId: null,
        selectedProjectList: [],
        selectedProjectListStr: null
      },
      employeeList: [],
      employeeItem: {
        value: '',
        text: ''
      },
      subProjectList: [],
      subProjectItem: {
        value: '',
        text: ''
      },
      projectList: [],
      selectedProjectList: [],
      projectItem: {
        value: '',
        text: ''
      },
      projectDto: {
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
      axios.get('/project/getAllProject')
        .then((resp) => {
          console.log('PROJECT FETCH DATA : ', resp.data)
          for (let project of resp.data) {
            this.projectList.push({value: project.id, text: project.name})
          }
        })
        .catch((error) => {
          console.log('ERROR : ', error)
          console.log('isi dari error', error.response)
          _.each(error.response.data.errors, function (value) {
            this.$set(this.errors, value.field, value.message)
          })
        })
    },
    search () {
    },
    addProject () {
      console.log('item: ', this.projectDto)
      this.selectedProjectList.push({
        id: this.projectDto.id,
        name: this.projectDto.name
      })
      _.remove(this.projectList, (project) => {
        return (project.value === this.projectDto.id)
      })
      this.clear()
      this.$forceUpdate()
    },
    deleteProjectSelected (project) {
      console.log('delete : ', this.selectedProjectList)
      console.log('delete : ', project)
      _.remove(this.selectedProjectList, (selectedProjectItem) => {
        return (selectedProjectItem.id === project.id)
      })
      this.projectList.push({value: project.id, text: project.name})
      this.$forceUpdate()
    },
    generate () {
      this.form.selectedProjectList = this.selectedProjectList
      this.form.selectedProjectListStr = ''
      for (let i = 0; i < this.selectedProjectList.length; i++) {
        if (i !== 0) {
          this.form.selectedProjectListStr = this.form.selectedProjectListStr.concat(',')
        }
        this.form.selectedProjectListStr = this.form.selectedProjectListStr.concat(this.selectedProjectList[i].id)
      }
      console.log('obj ', this.form)
      axios.post('/report/generateticket/project-assignment-report', this.form)
          .then((resp) => {
            console.log('BERHASIL : ', resp)
            let ticket = resp.data.ticket
            let root = window.location.protocol + '//' + window.location.host
            console.log('Ready to generate')
            window.open(root + '/api/download/cetakreport/project-assignment-report/' +
              this.form.startDateString + '/' + this.form.endDateString + '/' + this.form.selectedProjectListStr +
              '?ticket=' + ticket)
          })
          .catch((error) => {
            console.log('GAGAL : ', error)
          })
    },
    clear () {
      this.projectItem.text = ''
      this.projectItem.value = ''
    },
    onSubProjectSelect (item) {
      this.errorSelect = false
      this.subProjectItem = item
      this.form.subProjectId = item.value
    },
    onProjectSelect (item) {
      this.errorSelect = false
      this.form.projectId = item.value
      this.projectItem = item
      this.projectDto.id = item.value
      this.projectDto.name = item.text
    },
    onLoadSuccess (response) {
      console.log('on LOAD SUCCESS')
      console.log(response)
      // set pagination data to pagination-info component
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
    },
    selectedTanggal (event) {
      if (event !== null) {
        this.form.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    selectedTanggalEndDate (event) {
      if (event !== null) {
        this.form.endDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    validateAll () {
      this.validateNotBlank('startDate', this.form.startDateString)
      this.validateNotBlank('endDate', this.form.endDateString)
      this.validateNotBlank('projectId', this.form.projectId)
    },
    clearedStartDate (event) {
      this.form.startDateString = null
    },
    clearedEndDate (event) {
      this.form.endDateString = null
    }
  }
}
</script>
<style>

</style>
