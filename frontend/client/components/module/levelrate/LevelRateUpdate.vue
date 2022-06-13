<template>
  <div class="box">
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Level<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <span class="select">
            <select v-model="cmd.levelId" :class="{'is-danger' : errors.levelId}">
              <option v-for="option in levelList" :value="option.value">
                {{ option.text }}
              </option>
            </select>
          </span>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.levelId">
          <span class="help is-danger">{{ errors.levelId }}</span>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Rate<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <input class="input" v-on:keyup="onBlurNumber" type="text" v-model="pricewithSeparator"/>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.rate">
          <span class="help is-danger">{{ errors.rate }}</span>
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
</template>

<script>
import axios from 'axios'
import _ from 'lodash'
import bus from 'src/shared/bus'
import Datepicker from 'vuejs-datepicker'

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
        levelId: null,
        rate: null,
        startDate: null,
        endDate: null,
        startDateString: '',
        endDateString: ''
      },
      levelList: [],
      pricewithSeparator: null,
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
      axios.post('/level-rate/add', this.cmd)
        .then((resp) => {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCESS')
          console.log(resp)
          this.$router.replace('/level-rate')
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
    reset () {
      this.cmd.levelId = null
      this.cmd.rate = null
      this.cmd.startDate = null
      this.cmd.endDate = null
      this.cmd.startDateString = ''
      this.cmd.endDateString = ''
    },
    fetchData () {
      console.log('START FETCH')
      this.reset()
      axios.all([
        axios.get('/level/all')
      ]).then(axios.spread((levelList) => {
        this.levelList.push({value: null, text: 'Please Select'})
        for (let level of levelList.data) {
          this.levelList.push({value: level.id, text: level.name})
        }

        axios.get('/level-rate/view/' + this.$route.params.id)
          .then((resp) => {
            this.cmd = resp.data
            this.pricewithSeparator = this.cmd.rate.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
            this.employeeItem.value = this.cmd.employeeId
            this.employeeItem.text = this.cmd.employeeName
            this.projectItem.value = this.cmd.projectId
            this.projectItem.text = this.cmd.projectName
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            bus.$emit('close')
          })
      }))
    },
    cancel () {
      console.log('CANCEL')
      this.$router.replace('/level-rate')
    },
    validateAll () {
      this.errors = {}
      if (this.cmd.rate === null) {
        this.$set(this.errors, 'rate', 'The field cannot be empty')
      }
      if (this.cmd.rate === '' || this.cmd.rate < 0) {
        this.$set(this.errors, 'rate', 'Rate must contain integer')
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
    },
    onBlurNumber () {
      this.pricewithSeparator = this.pricewithSeparator.replace(/\D/g, '')
      this.pricewithSeparator = this.thousandSeparator(this.pricewithSeparator)
    },
    thousandSeparator (amount) {
      this.cmd.rate = this.pricewithSeparator.replace(/\D/g, '')
      if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      } else {
        return amount
      }
    }
  }
}
</script>
