<template>
  <div class="card-content">
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Leave Type<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <span class="select">
            <select v-model="cmd.leaveTypeId" :class="{'is-danger' : errors.leaveTypeId}" v-on:change="onLeaveTypeSelect()">
              <option v-for="option in levelList" :value="option.value">
                {{ option.text }}
              </option>
            </select>
          </span>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.leaveTypeId">
          <span class="help is-danger">{{ errors.leaveTypeId }}</span>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Start Date<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <datepicker v-model="cmd.startDate" input-class="input" format="dd-MM-yyyy"
                      placeholder="Start Date"
                      @selected="selectedTanggalStartDate($event)"
                      @cleared="clearedTanggalStartDate($event)"
                      :clear-button="clearButton"></datepicker>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.startDateString">
          <span class="help is-danger">{{ errors.startDateString }}</span>
        </div>
      </div>
    </div>

    <div class="columns" v-if="statusEndDate">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">End Date<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <datepicker v-model="cmd.endDate" input-class="input" format="dd-MM-yyyy"
                      placeholder="End Date"
                      :disabledDates= true
                      @selected="selectedTanggalEndDate($event)"
                      @cleared="clearedTanggalEndDate($event)"
                      :clear-button="clearButton"></datepicker>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.endDateString">
          <span class="help is-danger">{{ errors.endDateString }}</span>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Total Leave Days</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <label class="label">{{ cmd.numOfDays }} days</label>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.numOfDays">
          <span class="help is-danger">{{ errors.numOfDays }}</span>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Reason<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <textarea class="textarea" :class="{'is-danger' : errors.activityDetail}"
                    type="text" v-model="cmd.reason" placeholder="Reason"/>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.reason">
          <span class="help is-danger">{{ errors.reason }}</span>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Manager<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <span class="select">
            <select v-model="cmd.managerId" :class="{'is-danger' : errors.managerId}">
              <option v-for="option in managerList" :value="option.value">
                {{ option.text }}
              </option>
            </select>
          </span>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.managerId">
          <span class="help is-danger">{{ errors.managerId }}</span>
        </div>
      </div>
    </div>

    <div class="control is-horizontal">
      <div class="control-label">
        <label class="label"></label>
      </div>
      <div class="control is-grouped">
        <div class="control"> 
          <button v-if="isDisplayAdd" class="button is-info" @click="submit()">
            <span>Save</span>
          </button>
          <button v-else class="button is-info" @click="update()">
            <span>Update</span>
          </button>
        </div>
        <div class="control">
          <button class="button is-warning" @click="cancel()">Cancel</button>
        </div>
      </div>
    </div>
    <!-- {{ cmd }} -->
  </div>
</template>

<script>
import axios from 'axios'
import _ from 'lodash'
import bus from 'src/shared/bus'
import Datepicker from 'vuejs-datepicker'
import auth from 'src/shared/auth'

export default {
  components: {
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
        id: null,
        employeeId: null,
        reason: null,
        leaveTypeId: null,
        managerId: null,
        rate: null,
        startDate: null,
        endDate: null,
        startDateString: '',
        endDateString: '16-03-2020',
        numOfDays: 0,
        years: 0,
        pricewithSeparator: null
      },
      levelList: [],
      managerList: [],
      errors: {},
      checkError: false,
      statusEndDate: true,
      isDisplayAdd: true
    }
  },

  created () {
    this.fetchData()
    this.cmd.employeeId = auth.getUserInfo().employeeId
    if (this.$route.params.id) {
      let thisVM = this
      this.isDisplayAdd = false
      axios.get('/leave-request/get-by-id/' + this.$route.params.id)
        .then(function (resp) {
          console.log('START FETCH')
          thisVM.cmd = resp.data
          console.log(thisVM.cmd)
          console.log('END FETCH')
        })
        .catch(function (error) {
          console.log('GAGAL FETCH')
          console.log(error.response)
          thisVM.$emit('close')
        })
    }
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
      axios.post('/leave-request', this.cmd)
        .then((resp) => {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCESS')
          console.log(resp)
          this.$router.replace('/leave-request')
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
    },
    update () {
      this.validateAll()
      bus.$emit('SHOW_LOADING')
      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }
      axios.put('/leave-request/' + this.$route.params.id, this.cmd)
        .then((resp) => {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCESS')
          console.log(resp)
          this.$router.replace('/leave-request')
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
    reset () {
      this.cmd.leaveTypeId = null
      this.cmd.rate = null
      this.cmd.startDate = null
      this.cmd.endDate = null
      this.cmd.startDateString = ''
      this.cmd.endDateString = ''
      this.cmd.pricewithSeparator = null
    },
    fetchData () {
      console.log('START FETCH')
      axios.all([
        axios.get('/leave-type/getAll'),
        axios.get('/employee/get/roleManager')
      ]).then(axios.spread((levelList, managerList) => {
        this.levelList.push({value: null, text: 'Please Select'})
        for (let level of levelList.data) {
          this.levelList.push({value: level.id, text: level.leaveTypeName})
        }
        this.managerList.push({value: null, text: 'Please Select'})
        for (let manager of managerList.data) {
          this.managerList.push({value: manager.id, text: manager.name})
        }
      }))
    },
    cancel () {
      console.log('CANCEL')
      this.$router.replace('/leave-request')
    },
    validateAll () {
      this.errors = {}
      // if (this.cmd.rate === null) {
      //   this.$set(this.errors, 'rate', 'The field cannot be empty')
      // }
      // if (this.cmd.rate === '' || this.cmd.rate < 0) {
      //   this.$set(this.errors, 'rate', 'Rate must contain integer')
      // }
      // if (this.cmd.leaveTypeId === null || this.cmd.leaveTypeId === '') {
      //   this.$set(this.errors, 'leaveTypeId', 'The field cannot be empty')
      // }
      // for (var field in this.errors) {
      //   if (this.errors[field] !== null) {
      //     this.checkError = true
      //   }
      // }

      // if (this.checkError === false) {
      //   if (this.cmd.startDate != null && this.cmd.endDate != null) {
      //     if (this.cmd.startDate > this.cmd.endDate) {
      //       this.$set(this.errors, 'startDateString', 'Start date must be earlier than end date')
      //       this.$set(this.errors, 'endDateString', 'Start date must be earlier than end date')
      //     }
      //   }
      // }
    },
    selectedTanggalStartDate (event) {
      if (event !== null) {
        this.cmd.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
      this.cmd.years = event.getFullYear()
      if (this.cmd.leaveTypeId === 1) {
        if (this.cmd.endDate !== null) {
          this.getDaysBetween2Dates()
        }
      } else {
        this.cmd.endDateString = this.cmd.startDate
        this.getDaysBetween2Dates()
      }
    },
    selectedTanggalEndDate (event) {
      if (event !== null) {
        this.cmd.endDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
      if (this.cmd.startDate !== null) {
        if (this.cmd.leaveTypeId === 1) {
          if (this.cmd.endDate !== null) {
            this.getDaysBetween2Dates()
          }
        } else {
          this.getDaysBetween2Dates()
        }
      }
      // const oneDay = 24 * 60 * 60 * 1000 // hours*minutes*seconds*milliseconds
      // var arrayStartDate = this.cmd.startDateString.split('-')
      // var arrayEndDate = this.cmd.endDateString.split('-')
      // const firstDate = new Date(arrayStartDate[2], arrayStartDate[1], arrayStartDate[0])
      // const secondDate = new Date(arrayEndDate[2], arrayEndDate[1], arrayEndDate[0])
      // this.cmd.numOfdays = Math.round(Math.abs((firstDate - secondDate) / oneDay))
    },
    clearedTanggalStartDate (event) {
      this.cmd.startDateString = null
    },
    clearedTanggalEndDate (event) {
      this.cmd.endDateString = null
    },
    getDaysBetween2Dates () {
      console.log('this in')
      axios.get('/leave-request/diff/' + this.cmd.startDateString + '/' + this.cmd.endDateString + '/' + this.cmd.employeeId + '/' + this.cmd.years + '/' + this.cmd.leaveTypeId)
      .then((resp) => {
        console.log('Success GET calculate between 2 dates : ', resp)
        if (resp.data.numOfDays < 0) {
          bus.$emit('NOTIFY', 'danger', 'your leave request exceeds the limit')
          this.cmd.numOfDays = 0
        } else {
          this.cmd.numOfDays = resp.data.numOfDays + 1
          if (this.cmd.leaveTypeId !== 1) {
            this.cmd.endDate = resp.data.endDate
          }
        }
      })
    },
    onLeaveTypeSelect (item) {
      this.cmd.endDate = null
      this.cmd.startDate = null
      this.cmd.endDateString = null
      this.cmd.startDate = null
      this.cmd.numOfDays = 0
      if (this.cmd.leaveTypeId === 1) {
        this.statusEndDate = true
      } else {
        this.statusEndDate = true
      }
    },
    onBlurNumber () {
      this.cmd.pricewithSeparator = this.cmd.pricewithSeparator.replace(/\D/g, '')
      this.cmd.pricewithSeparator = this.thousandSeparator(this.cmd.pricewithSeparator)
    },
    thousandSeparator (amount) {
      this.cmd.rate = this.cmd.pricewithSeparator.replace(/\D/g, '')
      if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      } else {
        return amount
      }
    }
  }
}
</script>
