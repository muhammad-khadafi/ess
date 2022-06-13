<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Employee Name<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <basic-select :options="employeeList"
                          :selected-option="employeeItem"
                          placeholder="-- Please Select --"
                          @select="onEmployeeSelect"
                          :class="{'is-danger' : errors.employeeId}">
            </basic-select>
            </div>
          </div>
          <div class="control" v-if="errors.employeeId">
            <span class="help is-danger">{{ errors.employeeId }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Project Name<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <basic-select :options="projectList"
                          :selected-option="projectItem"
                          placeholder="-- Please Select --"
                          @select="onProjectSelect"
                          :class="{'is-danger' : errors.projectId}">
            </basic-select>
            </div>
          </div>
          <div class="control" v-if="errors.projectId">
            <span class="help is-danger">{{ errors.projectId }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Role<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
              <span class="select">
                <select v-model="cmd.roleId" :class="{'is-danger' : errors.roleId}">
                <option v-for="option in roleList" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
              </span>
            </div>
          </div>
          <div class="control" v-if="errors.roleId">
            <span class="help is-danger">{{ errors.roleId }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Level<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
              <span class="select">
                <select v-model="cmd.levelId" :class="{'is-danger' : errors.levelId}">
                <option v-for="option in levelList" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
              </span>
            </div>
          </div>
          <div class="control" v-if="errors.levelId">
            <span class="help is-danger">{{ errors.levelId }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Is Lead</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input class="checkbox" :class="{'is-danger' : errors.isLead}"
                   type="checkbox" v-model="cmd.isLead"/>
            </div>
          </div>
          <div class="control"> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Load<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="column is-one-third">
            <input class="input" type="number" v-model="cmd.load"/>
          </div>
          <div class="column is-one-third">%</div>
          <div class="control" v-if="errors.load"> 
            <span class="help is-danger">{{ errors.load }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Start Date<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <datepicker v-model="cmd.startDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedTanggalStartDate($event)"
                        @cleared="clearedTanggalStartDate($event)"
                        :clear-button="clearButton"></datepicker>
            </div>
            <div class="control" v-if="errors.startDateString"> 
              <span class="help is-danger">{{ errors.startDateString }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">End Date<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
              <datepicker v-model="cmd.endDate" input-class="input" format="dd-MM-yyyy"
                          placeholder="End Date"
                          @selected="selectedTanggalEndDate($event)"
                          @cleared="clearedTanggalEndDate($event)"
                          :clear-button="clearButton"></datepicker>
            </div>
            <div class="control" v-if="errors.endDateString"> 
              <span class="help is-danger">{{ errors.endDateString }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Is Active</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
              <input class="checkbox" :class="{'is-danger' : errors.flagActive}"
                     type="checkbox" v-model="cmd.flagActive"/>
            </div>
          </div>
          <div class="control"> 
          </div>
        </div>
      </div>
<!--      {{cmd}}-->
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
  </div>
</template>

<script>
  import axios from 'axios'
  import _ from 'lodash'
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import bus from 'src/shared/bus'

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
    data () {
      return {
        clearButton: true,
        cmd: {
          employeeId: null,
          projectId: null,
          roleId: null,
          levelId: null,
          isLead: null,
          load: null,
          startDate: new Date(),
          startDateString: '',
          endDate: null,
          endDateString: '',
          flagActive: true,
          months: null,
          years: null
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
        errors: {},
        checkError: false
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      submit () {
        this.validateAll()
        bus.$emit('SHOW_LOADING')
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }

        axios.post('/project-assignment/checkduplicate', this.cmd)
          .then((resp) => {
            if (resp.data === false) {
              axios.post('/project-assignment/add', this.cmd)
                .then((resp) => {
                  bus.$emit('HIDE_LOADING')
                  console.log('SUCCESS')
                  console.log(resp)
                  this.$router.replace('/project-assignment')
                  bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
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
              bus.$emit('NOTIFY', 'danger', 'This employee already has same role within project')
            }
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

      validateAll () {
        this.errors = {}
        this.checkError = false
        if (this.cmd.load === null || this.cmd.load === '') {
          this.$set(this.errors, 'load', 'The field cannot be empty')
        }
        if (this.cmd.load !== null && (this.cmd.load > 100 || this.cmd.load < 1)) {
          this.$set(this.errors, 'load', 'The number must be between 1 and 100')
        }
        if (this.cmd.employeeId === null || this.cmd.employeeId === '') {
          this.$set(this.errors, 'employeeId', 'The field cannot be empty')
        }
        if (this.cmd.projectId === null || this.cmd.projectId === '') {
          this.$set(this.errors, 'projectId', 'The field cannot be empty')
        }
        if (this.cmd.startDate === null || this.cmd.startDate === '') {
          this.$set(this.errors, 'startDateString', 'The field cannot be empty')
        }
        if (this.cmd.endDate === null || this.cmd.endDate === '') {
          this.$set(this.errors, 'endDateString', 'The field cannot be empty')
        }
        if (this.cmd.roleId === null || this.cmd.roleId === '') {
          this.$set(this.errors, 'roleId', 'The field cannot be empty')
        }
        if (this.cmd.levelId === null || this.cmd.levelId === '') {
          this.$set(this.errors, 'levelId', 'The field cannot be empty')
        }

        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            this.checkError = true
          }
        }

        if (this.checkError === false) {
          if (this.cmd.startDate != null && this.cmd.endDate != null) {
            if (this.cmd.startDate > this.cmd.endDate) {
              this.$set(this.errors, 'startDateString', 'Start date must be earlier than end date')
              this.$set(this.errors, 'endDateString', 'Start date must be earlier than end date')
            }
          }
        }
      },

      fetchData () {
        console.log('START FETCH')
        var date = new Date(this.$route.params.years + '/' + this.$route.params.months + '/' + '01')
        this.cmd.startDate = new Date(date.getFullYear(), date.getMonth(), 2)
        this.cmd.startDate = new Date(this.cmd.startDate.getTime() - this.cmd.startDate.getTimezoneOffset() * -60000)
        this.cmd.endDate = new Date(date.getFullYear(), date.getMonth() + 1, 0, 23, 59, 59)
        this.cmd.months = this.$route.params.months
        this.cmd.years = this.$route.params.years
        axios.all([
          axios.get('/project/getAllProject'),
          axios.get('/employee/all'),
          axios.get('/master-reference/getReference/Project Role'),
          axios.get('/level/all')
        ]).then(axios.spread((projectList, employeeList, roleList, levelList) => {
          this.roleList.push({value: null, text: '-- Please Select --'})
          this.levelList.push({value: null, text: '-- Please Select --'})
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

          this.employeeItem.value = this.cmd.employeeId = this.$route.params.employeeId
          this.employeeItem.text = this.$route.params.employeeName
        }))
      },
      cancel () {
        this.$router.replace('/project-assignment')
      },
      onProjectSelect (item) {
        this.errorSelect = false
        this.projectItem = item
        this.cmd.projectId = item.value
      },
      onEmployeeSelect (item) {
        this.errorSelect = false
        this.employeeItem = item
        this.cmd.employeeId = item.value
      },
      selectedTanggalStartDate (event) {
        if (event !== null) {
          this.cmd.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      selectedTanggalEndDate (event) {
        if (event !== null) {
          this.cmd.endDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedTanggalStartDate (event) {
        this.cmd.startDateString = null
      },
      clearedTanggalEndDate (event) {
        this.cmd.endDateString = null
      }
    }
  }
</script>
