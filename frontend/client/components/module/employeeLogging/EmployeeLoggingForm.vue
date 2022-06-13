<template>
  <div>
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Employee name</label>
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
          <label class="label">Organization Structure</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <basic-select :options="parentList"
                        :selected-option="parentItem"
                        placeholder="Organization Structure"
                        @select="onParentSelect"
                        :class="{'is-danger' : errors.structureId}">
          </basic-select>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.structureId">
          <span class="help is-danger">{{ errors.structureId }}</span>
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Start Date</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <datepicker v-model="cmd.startDate" input-class="input" format="dd-MM-yyyy"
                      placeholder="Start Date"
                      @selected="selectedTanggal($event)"
                      @cleared="clearedTanggal($event)"
                      :clear-button="clearButton"></datepicker>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.startDate">
          <span class="help is-danger">{{ errors.startDate }}</span>
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">End Date</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <datepicker v-model="cmd.endDate" input-class="input" format="dd-MM-yyyy"
                      placeholder="End Date"
                      @selected="selectedTanggal($event)"
                      @cleared="clearedTanggal($event)"
                      :clear-button="clearButton"></datepicker>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.endDate">
          <span class="help is-danger">{{ errors.endDate }}</span>
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Is Active ?</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <input class="checkbox" :class="{'is-danger' : errors.active}"
                 type="checkbox" v-model="cmd.active"/>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.active">
          <span class="help is-danger">{{ errors.active }}</span>
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Notes</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <input class="input" :class="{'is-danger' : errors.notes}"
                 type="text" v-model="cmd.notes" placeholder="Notes"/>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.notes">
          <span class="help is-danger">{{ errors.notes }}</span>
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
              <span class="icon is-small">
                <i class="fa fa-check"></i>
              </span>
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
        cmd: {
          employeeId: null,
          structureId: null,
          startDate: null,
          endDate: null,
          active: true,
          notes: null
        },
        parentList: [],
        organizationList: [],
        positionList: [],
        employeeList: [],
        parentItem: {
          value: '',
          text: ''
        },
        organizationItem: {
          value: '',
          text: ''
        },
        positionItem: {
          value: '',
          text: ''
        },
        employeeItem: {
          value: '',
          text: ''
        },
        jobPosName: null,
        orgName: null,
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        axios.all([
          axios.get('/organization-structure/structurelist'),
          axios.get('/employee/all')
        ]).then(axios.spread((parentList, employeeList) => {
          for (let parent of parentList.data) {
            this.parentList.push({value: parent.id, text: parent.name})
          }
          for (let employee of employeeList.data) {
            this.employeeList.push({value: employee.id, text: employee.name})
          }
        }))
      },
      onEmployeeSelect (item) {
        this.errorSelect = false
        this.employeeItem = item
        this.cmd.employeeId = item.value
      },
      onParentSelect (item) {
        this.errorSelect = false
        this.parentItem = item
        this.cmd.structureId = item.value
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/employeePosition')
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        let req
        req = axios.post('/employee-position', this.cmd)
        req.then(function (resp) {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCES')
          console.log(resp)
          bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')

          console.log('$$$ isNEW', this.isNew)
          this.$router.replace('/employeePosition/add')
        })
          .catch(function (error) {
            this.bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log(error.response)

            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })

            console.log('SELESAI')
            if (this.errors.exception) {
              this.bus.$emit('NOTIFY', 'danger', 'Gagal menyimpan data')

              if (this.isNew) {
                this.$router.replace('/employeePosition')
              } else {
                this.$emit('close')
              }
            }
          })
      },
      validateAll () {
        this.validateNotBlank('employeeId', this.cmd.employeeId)
        this.validateNotBlank('structureId', this.cmd.structureId)
        this.validateNotBlank('startDate', this.cmd.startDate)
      },
      selectedTanggal (event) {
        if (event !== null) {
          this.cmd.check = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedTanggal (event) {
        this.cmd.check = null
      }
    }
  }
</script>
