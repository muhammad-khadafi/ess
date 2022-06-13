<template>
  <div class="card-content">
    <div class="columns">
      <div class="column">
        <!--<p class="bd-notification is-info">First column</p>-->
        <div class="columns is-mobile">
          <div class="column is-3">
            <div class="level-right">
              <label class="label">Employee Name<font color="red">*</font></label>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="control is-grouped level-left" :class="{'is-disabled' : disabledEmployee}">
              <basic-select :options="employeeList"
                            :selected-option="employeeItem"
                            placeholder="Employee Name"
                            @select="onEmployeeSelect"
                            :class="{'is-danger' : errors.employeeId}">
              </basic-select>
            </div>
          </div>
          <div class="column is-3">
            <div class="control" v-if="errors.employeeId">
              <span class="help is-danger">{{ errors.employeeId }}</span>
            </div>
          </div>
        </div>

        <div class="columns is-mobile">
          <div class="column is-3">
            <div class="level-right">
              <label class="label">Date<font color="red">*</font></label>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="control is-grouped level-left">
              <datepicker v-model="form.date" input-class="input" format="dd-MM-yyyy"
                          placeholder="Date"
                          @selected="selectedTanggal($event)"
                          @cleared="clearedTanggal($event)"></datepicker>
            </div>
          </div>
          <div class="column">
            <div class="control" v-if="errors.date">
              <span class="help is-danger">{{ errors.date }}</span>
            </div>
          </div>
        </div>

        <div class="columns is-mobile">
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
          <div class="column is-3">
            <div class="control" v-if="errors.projectId">
              <span class="help is-danger">{{ errors.projectId }}</span>
            </div>
          </div>
        </div>

        <div class="columns is-mobile">
          <div class="column is-3">
            <div class="level-right">
              <label class="label">Sub Project</label>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="control is-grouped level-left">
              <basic-select :options="parentList"
                            :selected-option="parentItem"
                            placeholder="Sub Project"
                            @select="onParentSelect"
                            :class="{'is-danger' : errors.subProId}">
              </basic-select>
            </div>
          </div>
          <div class="column is-3">
            <div class="control" v-if="errors.subProId">
              <span class="help is-danger">{{ errors.subProId }}</span>
            </div>
          </div>
        </div>

        <div class="columns is-mobile">
          <div class="column is-3">
            <div class="level-right">
              <label class="label">SPV Assigned</label>
            </div>
          </div>
          <div class="column is-3">
            <div class="control is-grouped level-left">
              <input class="input" :class="{'is-danger' : errors.spvAssigned}"
                     type="text" v-model="form.spvAssigned" :disabled="disabled == 1 ? false : true"
                     placeholder="SPV Assigned"/>
            </div>
          </div>
          <div class="column is-3" v-if="spvButton">
            <button @click="disabled = (disabled + 1) % 2">Click to Modify</button>
          </div>
          <div class="column">
            <div class="control" v-if="errors.spvAssigned">
              <span class="help is-danger">{{ errors.spvAssigned }}</span>
            </div>
          </div>
        </div>

        <div class="columns is-mobile">
          <div class="column is-3">
            <div class="level-right">
              <label class="label">Activity Detail<font color="red">*</font></label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
              <textarea class="textarea" :class="{'is-danger' : errors.activityDetail}"
                    type="text" v-model="form.activityDetail" placeholder="Activity Detail"/>
            </div>
          </div>
          <div class="column">
            <div class="control" v-if="errors.activityDetail">
              <span class="help is-danger">{{ errors.activityDetail }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="column">
        <!--<p class="bd-notification is-danger">Second column</p>-->
        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Collaboration Withs</label>
            </div>
          </div>
          <div class="column is-half">
            <div class="control is-grouped level-left">
          <textarea class="textarea" :class="{'is-danger' : errors.thirdParty}"
                    type="text" v-model="form.thirdParty" placeholder="Collaboration With"/>
            </div>
          </div>
          <div class="column">
            <div class="control" v-if="errors.thirdParty">
              <span class="help is-danger">{{ errors.thirdParty }}</span>
            </div>
          </div>
        </div>

        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Duration<font color="red">*</font></label>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="control is-grouped level-left">
              <input class="input" min="0" :class="{'is-danger' : errors.duration}"
                     type="number" onkeypress="return (event.charCode == 8 || event.charCode == 0 || event.charCode == 13) ? null : event.charCode >= 48 && event.charCode <= 57"
                     v-model="form.duration" placeholder="Duration"/>
            </div>
          </div>
          <div class="column">
            <div class="control" v-if="errors.duration">
              <span class="help is-danger">{{ errors.duration }}</span>
            </div>
          </div>
        </div>

        <div class="columns is-mobile">
          <div class="column is-2">
            <div class="level-right">
              <label class="label">Overtime</label>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="control is-grouped level-left">
              <input class="input" min="0" :class="{'is-danger' : errors.overtime}"
                     type="number" onkeypress="return (event.charCode == 8 || event.charCode == 0 || event.charCode == 13) ? null : event.charCode >= 48 && event.charCode <= 57"
                     v-model="form.overtime" placeholder="Overtime"/>
            </div>
          </div>
          <div class="column">
            <div class="control" v-if="errors.overtime">
              <span class="help is-danger">{{ errors.overtime }}</span>
            </div>
          </div>
        </div>

      </div>
    </div>

    <div class="columns">
      <div class="column is-half is-offset-one-quarter has-text-centered">
        <button class="button is-info" @click="submit()">
          <span>Save</span>
        </button>
      </div>
    </div>
    <div class="columns">
      <div class="column is-fifth">
      </div>
      <div class="column is-fifth">
        <button class="button is-info btn-nav" @click="prevMonth()">
          <span>Previous Month</span>
        </button>
      </div>
      <div class="column is-fifth">
        <h1 class="has-text-centered"><b>{{currentMonth}}</b></h1>
      </div>
      <div class="column is-fifth has-text-right">
        <button class="button is-info btn-nav" @click="nextMonth()">
          <span>Next Month</span>
        </button>
      </div>
      <div class="column is-fifth">
      </div>
    </div>
  <!--</div>-->
  <div>
      <vuetable ref="vuetable"
                api-url="/log-act-detail-non-aj/table"
                :fields="fields"
                :sort-order="sortOrder"
                :multi-sort="multiSort"
                :per-page="perPage"
                :append-params="form"
                @vuetable:load-success="onLoadSuccess"
                @vuetable:load-error="onLoadError"
                @vuetable:loading="showLoader"
                @vuetable:loaded="hideLoader"
      />

  </div>
  </div>
</template>

<style>
  .btn-nav{
    width: 120px;
  }
</style>

<script>
import auth from 'src/shared/auth'
import Vue from 'vue'
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import EmployeeLoggingNonAjOperation from './EmployeeLoggingNonAjOperation.vue'
import _ from 'lodash'
import bus from 'src/shared/bus'
import axios from 'axios'
import { BasicSelect } from 'vue-search-select'
import Datepicker from 'vuejs-datepicker'
import ValidationMixin from '../../mixin/ValidationMixin'

Vue.component('EmployeeLoggingNonAjOperation', EmployeeLoggingNonAjOperation)

let tableColumns = [
  {
    name: 'dateString',
    title: 'Date',
    titleClass: 'center aligned',
    dataClass: 'right aligned',
    width: '100px'
  },
  {
    name: 'projectName',
    sortField: 'projectName',
    title: 'Project',
    titleClass: 'center aligned',
    width: '150px'
  },
  {
    name: 'subProjectName',
    sortField: 'subProjectName',
    title: 'Sub Project',
    titleClass: 'center aligned',
    width: '150px'
  },
  {
    name: 'activityDetail',
    sortField: 'activityDetail',
    title: 'Activity Detail',
    titleClass: 'center aligned'
  },
  {
    name: 'thirdParty',
    sortField: 'thirdParty',
    title: 'Collaboration',
    titleClass: 'center aligned',
    width: '150px'
  },
  {
    name: 'duration',
    sortField: 'duration',
    title: 'Duration',
    titleClass: 'center aligned',
    width: '80px',
    dataClass: 'center aligned'
  },
  {
    name: 'overtime',
    sortField: 'overtime',
    title: 'Overtime',
    titleClass: 'center aligned',
    width: '80px',
    dataClass: 'center aligned'
  },
  {
    name: 'spvAssigned',
    sortField: 'spvAssigned',
    title: 'SPV',
    titleClass: 'center aligned'
  },
  {
    title: 'Delete',
    titleClass: 'center aligned',
    name: '__component:employee-logging-non-aj-operation',
    dataClass: 'center aligned',
    width: '80px'
  }
]

export default {
  components: {
    Vuetable,
    VuetablePagination,
    VuetablePaginationDropdown,
    VuetablePaginationInfo,
    BasicSelect,
    Datepicker
  },
  mixins: [ValidationMixin],
  mounted () {
    console.log('Institusi List MOUNTED')
    let thisVM = this
    bus.$on('RELOAD_INSTITUSI_LIST', function () {
      console.log('Institusi List RELOAD_INSTITUSI_LIST', thisVM.$refs)
      console.log('Institusi List RELOAD_INSTITUSI_LIST is kosong : ', thisVM.$refs.vuetable)

      if (thisVM.$refs.vuetable !== undefined) {
        thisVM.$refs.vuetable.reload()
      }
    })
  },
  data () {
    return {
      auth: auth,
      disabledEmployee: true,
      form: {
        idEmployee: null,
        subProAssignmentId: null,
        date: new Date(),
        activityDetail: null,
        thirdParty: null,
        duration: 8,
        overtime: 0,
        spvAssigned: null,
        projectAssignId: null,
        projectId: null,
        subProId: null,
        months: null,
        years: null
      },
      tempDate: new Date(),
      currentMonth: null,
      projectList: [],
      parentList: [],
      employeeList: [],
      parentItem: {
        value: '',
        text: '',
        spv: ''
      },
      employeeItem: {
        value: '',
        text: ''
      },
      projectItem: {
        value: '',
        text: ''
      },
      activList: [{value: 'true', text: 'true'},
        {value: 'false', text: 'false'}],
      activItem: {
        value: '',
        text: ''
      },
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'l.date',
        direction: 'asc'
      }],
      disabled: 0,
      spvButton: false,
      multiSort: true,
      perPage: 10,
      paginationInfoTemplate: '{from} - {to} dari {total} data',
      paginationInfoNoDataTemplate: '',
      subMenuName: 'employee-logging'
    }
  },
  created () {
    this.setSelectedTanggal()
    this.setTodayDate()
    this.fetchData()
    this.checkRole()
    this.form.idEmployee = auth.getUserInfo().employeeId
  },
  methods: {
    fetchData () {
      console.log('START FETCH')
      this.employeeList = []
      this.projectList = []
      axios.all([
        axios.get('/employee/non-aj'),
        axios.get('/project/getAllProjectNonAj')
      ]).then(axios.spread((employeeList, projectList) => {
        for (let employee of employeeList.data) {
          this.employeeList.push({value: employee.id, text: employee.name})
          if (auth.getUserInfo().employeeId === employee.id) {
            this.employeeItem.value = employee.id
            this.employeeItem.text = employee.name
          }
        }
        for (let project of projectList.data) {
          this.projectList.push({value: project.id, text: project.name})
        }
      }))
    },
    reset () {
      this.form.subProId = null
      this.form.date = new Date()
      this.form.activityDetail = null
      this.form.thirdParty = null
      this.form.duration = 8
      this.form.overtime = 0
      this.form.spvAssigned = null
      this.parentItem = ''
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
      axios.post('/log-act-detail/checkProjectEmployee', this.form)
        .then((resp) => {
          console.log('Debug Response : ' + resp.data)
          this.form.projectAssignId = resp.data
          if (this.form.projectAssignId !== '') {
            axios.post('/log-act-detail-non-aj', this.form)
              .then((resp) => {
                bus.$emit('HIDE_LOADING')
                console.log('SUCCESS')
                console.log(resp)
                bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')
                this.reset()
                this.search()
                this.spvButton = false
              })
              .catch((error) => {
                bus.$emit('HIDE_LOADING')
                console.log('ERROR : ', error)
                console.log('isi dari error', error.response)
                if (error.response.status === 422) {
                  bus.$emit('NOTIFY', 'danger', 'Anda sudah menginputkan log dengan tanggal dan project yang sama')
                }
              })
          } else {
            bus.$emit('HIDE_LOADING')
            bus.$emit('NOTIFY', 'danger', 'Tidak memiliki assignment pada project terpilih')
          }
        })
      .catch((error) => {
        _.each(error.response.data.errors, function (value) {
          this.$set(this.errors, value.field, value.message)
        })
      })
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
    formatFlagAktif (value) {
      return value === true ? 'Aktif' : 'Tidak Aktif'
    },
    onLoadSuccess (response) {
      console.log('on LOAD SUCCESS')
      console.log(response)
      // set pagination data to pagination-info component
      // this.$refs.paginationInfo.setPaginationData(response.data)
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
    onEmployeeSelect (item) {
      this.errorSelect = false
      this.employeeItem = item
      this.form.idEmployee = item.value
      this.search()
    },
    onParentSelect (item) {
      this.errorSelect = false
      this.parentItem = item
      this.form.subProId = item.value
      this.form.spvAssigned = item.spv
      this.spvButton = true
    },
    onProjectSelect (item) {
      this.form.projectId = item.value
      this.errorSelect = false
      this.projectItem = item
      this.parentList = []
      axios.all([
        axios.get('/sub-project-by-project/' + item.value)
      ]).then(axios.spread((parentList) => {
        for (let parent of parentList.data) {
          this.parentList.push({value: parent.id, text: parent.subProjectName, spv: parent.spvName})
        }
      }))
    },
    onActiveSelect (item) {
      this.errorSelect = false
      this.activItem = item
      this.form.active = item.value
    },
    selectedTanggal (event) {
      if (event !== null) {
        this.form.purchaseDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedTanggal (event) {
      this.form.purchaseDateString = null
    },
    validateEmployeeId () {
      if (this.form.idEmployee === '' || (typeof this.form.idEmployee === 'undefined' || this.form.idEmployee === null)) {
        this.$set(this.errors, 'employeeId', 'Tidak boleh kosong')
      } else {
        this.errors.idEmployee = null
      }
    },
    validateSubProId () {
      if (this.form.subProId === '' || (typeof this.form.subProId === 'undefined' || this.form.subProId === null)) {
        this.$set(this.errors, 'subProId', 'field cannot be empty')
      } else {
        this.errors.subProId = null
      }
    },
    validateAll () {
      this.validateEmployeeId()
      this.validateNotBlank('date', this.form.date)
      // this.validateNotBlank('spvAssigned', this.form.spvAssigned)
      this.validateNotBlank('activityDetail', this.form.activityDetail)
      // this.validateNotBlank('thirdParty', this.form.thirdParty)
      this.validatePositifValueandGreaterThanZero('duration', this.form.duration)
      this.validatePositifValue('overtime', this.form.overtime)
      // this.validateSubProId()
    },
    addNew () {
      this.$router.replace('/employeePosition/add')
    },
    setSelectedTanggal () {
      this.form.purchaseDateString = (this.form.date.getDate() < 10 ? '0' + this.form.date.getDate() : this.form.date.getDate()) + '-' + (this.form.date.getMonth() + 1 < 10 ? ('0' + (this.form.date.getMonth() + 1)) : (this.form.date.getMonth() + 1)) + '-' + this.form.date.getFullYear()
    },
    setTodayDate () {
      var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
      this.currentMonth = months[this.tempDate.getMonth()] + ' ' + this.tempDate.getFullYear()
      this.form.months = this.tempDate.getMonth() + 1
      this.form.years = this.tempDate.getFullYear()
    },
    nextMonth () {
      var d = this.tempDate
      this.tempDate.setDate(1)
      this.tempDate.setMonth(d.getMonth() + 1)
      this.form.months = this.tempDate.getMonth() + 1
      this.form.years = this.tempDate.getFullYear()
      this.setTodayDate()
      this.$refs.vuetable.refresh()
      this.fetchData()
    },
    prevMonth () {
      var d = this.tempDate
      this.tempDate.setDate(1)
      this.tempDate.setMonth(d.getMonth() - 1)
      this.form.months = this.tempDate.getMonth() + 1
      this.form.years = this.tempDate.getFullYear()
      this.setTodayDate()
      this.$refs.vuetable.refresh()
      this.fetchData()
    },
    checkRole () {
      if (auth.getUserInfo().kode === 'ADMIN' || auth.getUserInfo().kode === 'SUPER_ADMIN' || auth.getUserInfo().kode === 'PROJECT_LEAD' || auth.getUserInfo().kode === 'PMO') {
        this.disabledEmployee = false
      }
    }
  }
}
</script>
