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
      <div class="column">
        <div class="control" v-if="errors.projectId">
          <span class="help is-danger">{{ errors.projectId }}</span>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Sub Project</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-half">
          <basic-select :options="subProjectList"
                        :selected-option="subProjectItem"
                        placeholder="Sub Project Name"
                        @select="onSubProjectSelect"
                        :class="{'is-danger' : errors.subProjectId}">
          </basic-select>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.subProjectId">
          <span class="help is-danger">{{ errors.subProjectId }}</span>
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
    <!--{{form}}-->

      <div class="columns">
        <div class="column is-half is-offset-one-quarter has-text-centered">
            <button class="button is-info" @click="search()">
                  <span class="icon is-small">
                    <i class="fa fa-search"></i>
                  </span>
              <span>Search</span>
            </button>
        </div>
      </div>

  </div>
    <div class="box">
      <div class="columns">
        <vue-table-dynamic :params="params"></vue-table-dynamic>
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
import VueTableDynamic from 'vue-table-dynamic'
import ValidationMixin from '../../mixin/ValidationMixin'

export default {
  components: {
    BasicSelect,
    Datepicker,
    VueTableDynamic
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
        projectId: null
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
      this.validateAll()
      bus.$emit('SHOW_LOADING')
      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }
      this.params.data = []
      this.errors = {}
      console.log('sendForm : ', this.form)
      axios.get('/log-act-view-non-aj/table', {
        params: this.form
      })
        .then((resp) => {
          console.log('LOG FETCH DATA : ', resp.data)
          this.params.data = resp.data
          bus.$emit('HIDE_LOADING')
        })
        .catch((error) => {
          console.log('ERROR : ', error)
          console.log('isi dari error', error.response)
          _.each(error.response.data.errors, function (value) {
            this.$set(this.errors, value.field, value.message)
          })
        })
    },
    onSubProjectSelect (item) {
      this.errorSelect = false
      this.subProjectItem = item
      this.form.subProjectId = item.value
    },
    onProjectSelect (item) {
      this.subProjectList = []
      this.errorSelect = false
      this.projectItem = item
      this.form.projectId = item.value

      axios.get('/sub-project-by-project/' + this.form.projectId)
        .then((resp) => {
          console.log('SUBPROJECT FETCH DATA : ', resp.data)
          for (let subProject of resp.data) {
            this.subProjectList.push({value: subProject.id, text: subProject.subProjectName})
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
