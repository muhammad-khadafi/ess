<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update</p>
      <button class="delete" @click="close"></button>
    </div>
    <div class="modal-card-body">
    <div class="columns">
      <div class="column">
        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Employee Name<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <div class="column is-one-third">
              <basic-select :options= "listPegawai"
              :selected-option= "pegawai"
              placeholder= "Employee Name"
              @select= "onPegawaiSelect"
              :class="{'is-danger' : errors.employeeId}">
              </basic-select>
              </div>
            </div>
            <div class="control" v-if="errors.employeeId">
              <span class="help is-danger">{{errors.employeeId}}</span>
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
                              @selected="selectedStartDate($event)"
                              @cleared="clearedStartDate($event)"
                              :clear-button="clearButton">
                  </datepicker>
                </div>
            </div>
            <div class="control" v-if="errors.startDate">
              <span class="help is-danger">{{errors.startDate}}</span>
            </div>
          </div>
        </div>
        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">End Date</label>
          </div>
          <div class="control is-grouped">
              <div class="control is-expanded">
                <div class="column is-one-third">
                  <datepicker v-model="cmd.endDate" input-class="input" format="dd-MM-yyyy"
                              placeholder="End Date"
                              @selected="selectedEndDate($event)"
                              @cleared="clearedEndDate($event)"
                              :clear-button="clearButton">
                  </datepicker>
                </div>
            </div>
            <div class="control" v-if="errors.endDate">
              <span class="help is-danger">{{errors.endDate}}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
<!--    {{cmd}}-->
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
import { BasicSelect } from 'vue-search-select'
import Datepicker from 'vuejs-datepicker'
import axios from 'axios'
import ValidationMixin from '../../mixin/ValidationMixin'
import bus from 'src/shared/bus'
import _ from 'lodash'

export default {
  mixins: [ValidationMixin],
  components: {
    BasicSelect,
    Datepicker
  },
  props: {
    cmd: {
      type: Object
    }
  },
  data () {
    return {
      clearButton: true,
      listPegawai: [],
      listInventory: [],
      form: {
        employeeId: null,
        startDate: null
      },
      pegawai: {
        text: '',
        value: ''
      },
      inventory: {
        text: '',
        value: ''
      },
      errors: {}
    }
  },
  created () {
    this.pegawai.text = this.cmd.employeeName
    this.fetchData()
  },
  methods: {
    close () {
      this.$emit('close')
    },
    cancel () {
      this.$emit('close')
    },
    fetchData () {
      axios.all([
        axios.get('/employee/all')
      ]).then(axios.spread((objekEmploy) => {
        for (let emp of objekEmploy.data) {
          this.listPegawai.push({value: emp.id, text: emp.name})
        }
      }))
    },
    onPegawaiSelect (item) {
      this.pegawai = item
      this.cmd.employeeId = this.pegawai.value
    },
    onInventorySelect (item) {
      this.inventory = item
      this.cmd.inventoryId = this.inventory.value
    },
    selectedStartDate (event) {
      if (event !== null) {
        this.cmd.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedStartDate (event) {
      this.cmd.startDate = null
    },
    selectedEndDate (event) {
      if (event !== null) {
        this.cmd.endDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedEndDate (event) {
      this.cmd.endDate = null
    },
    submit () {
      this.validateAll()
      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }
      axios.put('/inventory-assignment/' + this.$route.params.id, this.cmd)
        .then((resp) => {
          this.$emit('close')
          bus.$emit('NOTIFY', 'success', 'Data has been updated')
          bus.$emit('RELOAD_INVENTORY_ASSIGN_LIST')
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
    validateEmployeeNotNull () {
      this.validateNotBlank('employeeId', this.cmd.employeeId)
    },
    validateInventoryNotNull () {
      this.validateNotBlank('inventoryId', this.cmd.inventoryId)
    },
    validateAll () {
      this.validateNotBlank('employeeId', this.cmd.employeeId)
      this.validateNotBlank('startDate', this.cmd.startDate)
    },
    validateStartDateNotNull () {
      this.validateNotBlank('startDate', this.cmd.startDate)
    }
  }
}
</script>
