<template>
  <div>
    <div class="box">
      <div class="columns">

        <div class="column is-2">
          <div class="level-right">
            <label class="label">Project Type<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-fifth">
          <div class="control is-grouped level-left">
            <basic-select :options="projectTypeList"
                          :selected-option="projectTypeItem"
                          placeholder="Project Type"
                          @select="onProjectTypeSelect">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.employeeId">
            <span class="help is-danger">{{ errors.employeeId }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column">
          <div class="columns is-mobile">
            <div class="column is-2">
              <div class="level-right">
                <label class="label">Periode<font color="red">*</font></label>
              </div>
            </div>
            <div class="column is-2">
              <datepicker v-model="form.dateBefore" input-class="input" format="dd-MM-yyyy"
                          placeholder="select date"
                          @selected="selectedTanggalBefore($event)"
                          :clear-button="clearButton">
              </datepicker>
            </div>
            <div class="column is-2">
              <datepicker v-model="form.dateAfter" input-class="input" format="dd-MM-yyyy"
                          placeholder="select date"
                          @selected="selectedTanggal($event)"
                          :clear-button="clearButton">
              </datepicker>
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
         <div class="control"> 
            <button class="button is-info" @click="generate()">
              <span class="icon is-small">
                <i class="fa fa-check"></i>
              </span>
              <span>Generate</span>
            </button>
          </div>

        </div>
<!--        {{form}}-->
      </div>

    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import ValidationMixin from '../../mixin/ValidationMixin'
//  import _ from 'lodash'
//  import bus from 'src/shared/bus'

  export default {
    components: {
      BasicSelect,
      Datepicker
    },
    props: {
      id: {
        type: Number
      }
    },
    mixins: [ValidationMixin],
    data () {
      return {
        clearButton: true,
        form: {
          employeeName: null,
          employeeId: null,
          days: null,
          months: null,
          years: null,
          dateAfter: null,
          dateBefore: null,
          projectId: null,
          subProjectId: null,
          projectType: null
        },
        projectList: [],
        subProjectList: [],
        employeeList: [],
        employeeItem: {
          value: '',
          text: ''
        },
        projectItem: {
          value: '',
          text: ''
        },
        subProjectItem: {
          value: '',
          text: ''
        },
        projectTypeItem: {
          value: '',
          text: ''
        },
        projectTypeList: [
          { value: 'AJ', text: 'Artajasa' },
          { value: 'NON_AJ', text: 'Non Artajasa' },
          { value: 'SEMUA', text: 'Semua Proyek' }
        ],
        monthsList: [
          { value: '1', text: 'January' },
          { value: '2', text: 'February' },
          { value: '3', text: 'March' },
          { value: '4', text: 'April' },
          { value: '5', text: 'May' },
          { value: '6', text: 'June' },
          { value: '7', text: 'July' },
          { value: '8', text: 'August' },
          { value: '9', text: 'September' },
          { value: '10', text: 'October' },
          { value: '11', text: 'November' },
          { value: '12', text: 'December' }
        ],
        monthsItem: {
          value: '',
          text: ''
        },
        yearsList: [
          { value: '2018', text: '2018' },
          { value: '2019', text: '2019' }
        ],
        yearsItem: {
          value: '',
          text: ''
        },
        errors: {},
        subMenuName: 'log-activity-details-report'
      }
    },
    created () {
      this.fetchData()
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/employee/non-aj')
        ]).then(axios.spread((employeeList, projectList) => {
          for (let employee of employeeList.data) {
            this.employeeList.push({value: employee.id, text: employee.name})
          }
        }))
      },
      reset () {
        this.cmd.id = null
        this.cmd.name = null
        this.cmd.category = null
        this.cmd.referenceType = null
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/master-reference')
      },
      generate () {
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            return
          }
        }
        if (this.form.projectType == null) {
          this.form.projectType = 'SEMUA'
        }
        axios.post('/report/generateticket/assignment-report', this.form)
            .then((resp) => {
              console.log('BERHASIL : ', resp)
              let ticket = resp.data.ticket
              let root = window.location.protocol + '//' + window.location.host
              console.log('Ready to generate')
              window.open(root + '/api/download/cetakreport/assignment-report/' +
                this.form.dateBefore + '/' + this.form.dateAfter + '/' + this.form.projectType +
                '/' + '?ticket=' + ticket)
            })
            .catch((error) => {
              console.log('GAGAL : ', error)
            })
      },
      download () {
        let root = window.location.protocol + '//' + window.location.host
        console.log('Ready to generate')
        window.open(root + '/api/log-activity-report/get-report/1')
      },
      validateAll () {
        this.validateNotBlank('employeeId', this.form.employeeId)
      },
      onEmployeeSelect (item) {
        this.errorSelect = false
        this.employeeItem = item
        this.form.employeeId = item.value
        this.form.employeeName = item.text
      },
      selectedTanggal (event) {
        if (event !== null) {
          this.form.months = ((event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)))
          this.form.years = event.getFullYear()
          this.form.days = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate())
          // this.form.dateAfter = (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + event.getFullYear()
        }
      },
      selectedTanggalBefore (event) {
        if (event !== null) {
          // this.form.dateBefore = (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + event.getFullYear()
        }
      },
      onMonthSelect (item) {
        this.errorSelect = false
        this.monthsItem = item
        this.form.months = item.value
      },
      onYearSelect (item) {
        this.errorSelect = false
        this.yearsItem = item
        this.form.years = item.value
      },
      onProjectSelect (item) {
        this.projectItem = item
        this.form.projectId = item.value
        this.subProjectList = []
        this.subProjectItem = ''
        this.form.subProjectId = null
        axios.all([
          axios.get('/sub-project-by-project/' + item.value)
        ]).then(axios.spread((subProjectList) => {
          for (let subProject of subProjectList.data) {
            this.subProjectList.push({value: subProject.id, text: subProject.subProjectName})
            console.log('debug sub project: ', subProject.subProjectName)
          }
        }))
      },
      onSubprojectSelect (item) {
        this.subProjectItem = item
        this.form.subProjectId = item.value
      },
      onProjectTypeSelect (item) {
        this.projectTypeItem = item
        this.form.projectType = item.value
      }
    }
  }
</script>
