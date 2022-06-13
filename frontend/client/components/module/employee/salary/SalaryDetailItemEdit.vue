<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update</p>
    </div>
    <div class="modal-card-body">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Salary<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.salary}"
                   type="text" v-on:keyup="onBlurNumber" v-model="pricewithSeparator" placeholder="Salary"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.salary">
            <span class="help is-danger">{{ errors.salary }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Start Date<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <datepicker class="control is-expanded" v-model="cmd.startDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedStartDate($event)"
                        @cleared="clearedStartDate($event)"
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
        <div class="column is-3">
          <div class="level-right">
            <label class="label">End Date</label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <datepicker class="control is-expanded" v-model="cmd.endDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="End Date"
                        @selected="selectedEndDate($event)"
                        @cleared="clearedEndDate($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Is Active</label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <input class="checkbox" :class="{'is-danger' : errors.isActive}"
                   type="checkbox" v-model="cmd.isActive"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.isActive"> 
            <span class="help is-danger">{{ errors.isActive }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control"> 
            <button class="button is-info" @click="submit()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Save</span>
            </button>&nbsp;
            <button class="button is-warning" @click="back()">Back</button>
          </div>
        </div>
      </div>

<!--      {{cmd}}-->

    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { Modal } from 'vue-bulma-modal'
  import Datepicker from 'vuejs-datepicker'

  export default {
    components: {
      Modal,
      Datepicker
    },
    props: {
      cmd: {
        type: Object
      }
    },
    data () {
      return {
        isNew: true,
        pricewithSeparator: null,
        errors: {},
        clearButton: true
      }
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      this.pricewithSeparator = this.cmd.salary

      if (this.pricewithSeparator !== '' && this.pricewithSeparator !== undefined && this.pricewithSeparator !== 0 && this.pricewithSeparator !== '0' && this.pricewithSeparator !== null) {
        this.pricewithSeparator = this.pricewithSeparator.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      }
      console.log('SALARY EDIT FORM CREATED')
    },
    methods: {
      close () {
        this.$emit('close')
      },
      back () {
        this.$emit('close')
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        let thisVM = this
        axios.post('/employee-salary/add', this.cmd)
          .then((resp) => {
            this.$emit('close')
            bus.$emit('NOTIFY', 'success', 'Data has been updated')
            bus.$emit('RELOAD_SALARY_LIST')
          })
          .catch(function (error) {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log(error.response)

            _.each(error.response.data.errors, function (value) {
              thisVM.$set(this.errors, value.field, value.message)
            })

            console.log('SELESAI')
            if (thisVM.errors.exception) {
              bus.$emit('NOTIFY', 'danger', 'Gagal menyimpan data')
              thisVM.$router.replace('/employee/edit/' + thisVM.cmd.employeeId)
            }
          })
      },
      validateAll () {
        this.errors = {}
        if (!this.cmd.salary) {
          this.$set(this.errors, 'salary', 'The field cannot be empty')
        }
        if (!this.cmd.startDate) {
          this.$set(this.errors, 'startDate', 'The field cannot be empty')
        }

        if (this.cmd.startDate != null && this.cmd.endDate != null) {
          if (this.cmd.startDate > this.cmd.endDate) {
            this.$set(this.errors, 'startDate', 'Start date must be earlier than end date')
            this.$set(this.errors, 'endDate', 'Start date must be earlier than end date')
          }
        }
      },
      selectedStartDate (event) {
        if (event !== null) {
          this.cmd.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedStartDate (event) {
        this.cmd.startDateString = null
      },
      selectedEndDate (event) {
        if (event !== null) {
          this.cmd.endDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedEndDate (event) {
        this.cmd.endDateString = null
      },
      onBlurNumber () {
        this.pricewithSeparator = this.pricewithSeparator.replace(/\D/g, '')
        this.pricewithSeparator = this.thousandSeparator(this.pricewithSeparator)
      },
      thousandSeparator (amount) {
        this.cmd.salary = this.pricewithSeparator.replace(/\D/g, '')
        if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
          return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
        } else {
          return amount
        }
      }
    }
  }
</script>
