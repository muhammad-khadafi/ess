<template>
  <div class="card-content">
    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Organization Name</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="organizationList"
                      :selected-option="organizationItem"
                      placeholder="-- Please Select --"
                      @select="onOrganizationSelect"
                      :class="{'is-danger' : errors.organizationId}">
        </basic-select>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Employee Name</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="employeeList"
                      :selected-option="employeeItem"
                      placeholder="Employee Name"
                      @select="onEmployeeSelect"
                      :class="{'is-danger' : errors.fundingType}">
        </basic-select>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Project Name</label>
        </div>
      </div>
      <div class="column is-5">
        <basic-select :options="projectList"
                      :selected-option="projectItem"
                      placeholder="Project Name"
                      @select="onProjectSelect"
                      :class="{'is-danger' : errors.organizationId}">
        </basic-select>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Role</label>
        </div>
      </div>
      <div class="column is-5">
        <span class="select">
          <select v-model="form.roleId" :class="{'is-danger' : errors.role}">
          <option v-for="option in roleList" :value="option.value">
            {{ option.text }}
          </option>
          </select>
        </span>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Level</label>
        </div>
      </div>
      <div class="column is-5">
        <span class="select">
          <select v-model="form.levelId" :class="{'is-danger' : errors.level}">
            <option v-for="option in levelList" :value="option.value">
              {{ option.text }}
            </option>
          </select>
        </span>
      </div>
    </div>

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
    <div>
      <vuetable ref="vuetable"
                api-url="/project-assignment/table"
                :fields="fields"
                pagination-path="pagination"
                :sort-order="sortOrder"
                :multi-sort="multiSort"
                :per-page="perPage"
                :append-params="form"
                @vuetable:pagination-data="onPaginationData"
                @vuetable:load-success="onLoadSuccess"
                @vuetable:load-error="onLoadError"
                @vuetable:loading="showLoader"
                @vuetable:loaded="hideLoader"
      />
      <nav class="columns">
        <div class="column">
          <vuetable-pagination-info ref="paginationInfo"
                                    :pagination-info-template="paginationInfoTemplate"
                                    :pagination-info-no-data-template="paginationInfoNoDataTemplate"/>
        </div>
        <div class="column">
          <vuetable-pagination ref="pagination" @vuetable-pagination:change-page="onChangePage"/>
        </div>
      </nav>
    </div><!-- content -->

    <div class="columns">
      <div class="column is-half is-offset-one-quarter has-text-centered">
        <button class="button is-info" @click="addNew()">
                  <span class="icon is-small">
                    <i class="fa fa-plus"></i>
                  </span>
          <span>Add New</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
  import ProjectAssignmentOperation from './ProjectAssignmentOperation.vue'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import axios from 'axios'
  import { BasicSelect } from 'vue-search-select'

  Vue.component('projectAssignmentOperation', ProjectAssignmentOperation)

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'employeeName',
      sortField: 'name',
      title: 'Employee',
      titleClass: 'center aligned'
    },
    {
      name: 'projectName',
      sortField: 'projectName',
      title: 'Project',
      titleClass: 'center aligned'
    },
    {
      name: 'roleName',
      sortField: 'roleName',
      title: 'Role',
      titleClass: 'center aligned'
    },
    {
      name: 'levelName',
      sortField: 'levelName',
      title: 'Level',
      titleClass: 'center aligned'
    },
    {
      name: 'isLeadString',
      sortField: 'isLeadString',
      title: 'Is Lead',
      titleClass: 'center aligned'
    },
    {
      name: 'loadString',
      sortField: 'load',
      title: 'Load',
      titleClass: 'center aligned'
    },
    {
      name: 'projectStatus',
      sortField: 'projectStatus',
      title: 'Project Status',
      titleClass: 'center aligned'
    },
    {
      title: 'Action',
      titleClass: 'center aligned',
      name: '__component:project-assignment-operation',
      dataClass: 'center aligned'
    }
  ]

  export default {
    components: {
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo,
      BasicSelect
    },
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
        form: {
          organizationId: null,
          employeeId: null,
          projectId: null,
          roleId: null,
          levelId: null,
          months: null,
          years: null
        },
        errors: {},
        fields: tableColumns,
        sortOrder: [{
          field: 'employeeName',
          direction: 'asc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
        organizationList: [],
        organizationItem: {
          value: '',
          text: ''
        },
        projectList: [],
        projectItem: {
          value: '',
          text: ''
        },
        employeeList: [],
        employeeItem: {
          value: '',
          text: ''
        },
        roleList: [],
        levelList: [],
        tempDate: new Date(),
        currentMonth: null
      }
    },
    created () {
      this.fetchData()
      this.setTodayDate()
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/organization/organizationlist'),
          axios.get('/project/getAllProject'),
          axios.get('/employee/all'),
          axios.get('/master-reference/getReference/Project Role'),
          axios.get('/level/all')
        ]).then(axios.spread((organizationList, projectList, employeeList, roleList, levelList) => {
          this.roleList.push({value: null, text: 'Please Select'})
          this.levelList.push({value: null, text: 'Please Select'})
          for (let organization of organizationList.data) {
            this.organizationList.push({value: organization.id, text: organization.name})
          }
          for (let organization of projectList.data) {
            this.projectList.push({value: organization.id, text: organization.name})
          }
          for (let employee of employeeList.data) {
            this.employeeList.push({value: employee.id, text: employee.name})
          }
          for (let role of roleList.data) {
            this.roleList.push({value: role.id, text: role.name})
          }
          for (let level of levelList.data) {
            this.levelList.push({value: level.id, text: level.name})
          }
        }))
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
        this.$refs.paginationInfo.setPaginationData(response.data)
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
      addNew () {
        this.$router.replace('/project-assignment/add/' + this.form.months + '/' + this.form.years)
      },
      onProjectSelect (item) {
        this.errorSelect = false
        this.projectItem = item
        this.form.projectId = item.value
      },
      onEmployeeSelect (item) {
        this.errorSelect = false
        this.employeeItem = item
        this.form.employeeId = item.value
      },
      onOrganizationSelect (item) {
        this.errorSelect = false
        this.organizationItem = item
        this.form.organizationId = item.value
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
      },
      prevMonth () {
        var d = this.tempDate
        this.tempDate.setDate(1)
        this.tempDate.setMonth(d.getMonth() - 1)
        this.form.months = this.tempDate.getMonth() + 1
        this.form.years = this.tempDate.getFullYear()
        this.setTodayDate()
        this.$refs.vuetable.refresh()
      }
    }
  }
</script>
