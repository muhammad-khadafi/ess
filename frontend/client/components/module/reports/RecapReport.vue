<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Report Type<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <select class="select" v-model="form.reportType">
              <option v-for="option in listReportType" :value="option.value" placeholder="- Select Report Type -">
                {{ option.text }}
              </option>
            </select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.reportType">
            <span class="help is-danger">{{ errors.reportType }}</span>
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
                          placeholder="select start date"
                          @selected="selectedTanggalBefore($event)"
                          :clear-button="clearButton">
              </datepicker>
              <!--<basic-select :options="monthsList"-->
                            <!--:selected-option="monthsItem"-->
                            <!--placeholder="Select Month"-->
                            <!--@select="onMonthSelect"-->
                            <!--:class="{'is-danger' : errors.employeeId}">-->
              <!--</basic-select>-->
            </div>
            <div class="column is-2">
              <datepicker v-model="form.dateAfter" input-class="input" format="dd-MM-yyyy"
                          placeholder="select end date"
                          @selected="selectedTanggal($event)"
                          :clear-button="clearButton">
              </datepicker>
              <!--<basic-select :options="yearsList"-->
                            <!--:selected-option="yearsItem"-->
                            <!--placeholder="Select Year"-->
                            <!--@select="onYearSelect"-->
                            <!--:class="{'is-danger' : errors.employeeId}">-->
              <!--</basic-select>-->
            </div>
            <div class="column is 3">
              <div class="control" v-if="errors.dateAfter">
                <span class="help is-danger">{{ errors.dateAfter }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Sub Project</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="subProjectList"
                          :selected-option="subProjectItem"
                          placeholder="Sub Project Name"
                          @select="onSubProjectSelect"
                          :class="{'is-danger' : errors.subProjectId}">
            </basic-select>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Employee Name</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="employeeList"
                          :selected-option="employeeItem"
                          placeholder="Employee Name"
                          @select="onEmployeeSelect"
                          :class="{'is-danger' : errors.employeeId}">
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
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Pusilkom Signature Name</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" type="text" v-model="form.pusilkomPersonnel" />
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Pusilkom Signature Role</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <textarea class="textarea" type="text" v-model="form.pusilkomRole" />
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Client Signature Name</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" type="text" v-model="form.clientPersonnel" />
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Client Signature Role</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <textarea class="textarea" type="text" v-model="form.clientRole" />
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
          employeeId: 0,
          days: null,
          months: null,
          monthsString: null,
          years: null,
          dateAfter: null,
          dateBefore: null,
          reportType: null,
          subProjectId: 0,
          pusilkomPersonnel: null,
          pusilkomRole: null,
          clientPersonnel: null,
          clientRole: null,
          clientRoleReplace: null,
          pusilkomRoleReplace: null
        },
        listReportType: [
          { value: null, text: '- Select Report Type -' },
          { value: 'Invoice', text: 'Recap Invoice' },
          { value: 'Log-Activity', text: 'Recap Log Activity' }
        ],
        employeeList: [],
        employeeItem: {
          value: '',
          text: ''
        },
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
        subProjectList: [],
        subProjectItem: {
          value: '',
          text: ''
        },
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
          axios.get('/project-assignment/getAll/3/' + this.subMenuName),
          axios.get('/invoice-report/getSubProject')
        ]).then(axios.spread((employeeList, subProjectList) => {
          this.employeeList.push({value: 0, text: ' - Please Select - '})
          for (let employee of employeeList.data) {
            this.employeeList.push({value: employee.employeeId, text: employee.employeeName})
          }
          for (let subProject of subProjectList.data) {
            this.subProjectList.push({value: subProject.id, text: subProject.code})
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
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            return
          }
        }
        this.form.pusilkomRoleReplace = this.form.pusilkomRole.replace(/\n/g, '|')
        this.form.clientRoleReplace = this.form.clientRole.replace(/\n/g, '|')
        axios.post('/report/generateticket/logActivity', this.form)
          .then((resp) => {
            console.log('BERHASIL : ', resp)
            let ticket = resp.data.ticket
            let root = window.location.protocol + '//' + window.location.host
            console.log('Ready to generate')
            window.open(root + '/api/download/sample/cetakRecapReport/logActivity/' +
              this.form.employeeName + '/' + this.form.employeeId + '/' + this.form.days + '/' + this.form.months + '/' + this.form.monthsString + '/' + this.form.years + '/' +
              this.form.dateBefore + '/' + this.form.dateAfter + '/' + this.form.subProjectId + '/' + this.form.reportType + '/' +
              this.form.pusilkomPersonnel + '/' + this.form.pusilkomRoleReplace + '/' + this.form.clientPersonnel + '/' + this.form.clientRoleReplace + '?ticket=' + ticket)
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
        this.validateNotBlank('reportType', this.form.reportType)
        this.validateNotBlank('dateAfter', this.form.dateAfter)
        this.validateNotBlank('dateAfter', this.form.dateBefore)
      },
      onEmployeeSelect (item) {
        this.errorSelect = false
        this.employeeItem = item
        this.form.employeeId = item.value
        this.form.employeeName = item.text
      },
      selectedTanggal (event) {
        if (event !== null) {
          var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
          this.form.months = ((event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)))
          this.form.monthsString = months[event.getMonth()]
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
      onSubProjectSelect (item) {
        this.errorSelect = false
        this.subProjectItem = item
        this.form.subProjectId = item.value
        this.form.subProjectString = item.text
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
      }
    }
  }
</script>
