<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Salary<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.salary}"
                   type="text" v-on:keyup="onBlurNumber"  v-model="pricewithSeparator" placeholder="Salary"/>
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
        <div class="column is-one-third">
          <div class="control is-half">
            <datepicker v-if="!errors.startDate" v-model="cmd.startDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedStartDate($event)"
                        @cleared="clearedStartDate($event)"
                        :clear-button="clearButton"></datepicker>
            <datepicker v-if="errors.startDate" v-model="cmd.startDate" input-class="input is-danger" format="dd-MM-yyyy"
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
        <div class="column is-one-third">
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
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="checkbox" :class="{'is-danger' : errors.isActive}"
                   type="checkbox" v-model="cmd.isActive"/>
          </div>
          <div class="column">
          <div class="control" v-if="errors.isActive"> 
            <span class="help is-danger">{{ errors.isActive }}</span> 
          </div>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control"> 
            <button class="button is-info" @click="addSalary()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Salary</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column">
        <table class="vuetable ui blue selectable celled stackable attached table">
          <th>No.</th>
          <th>Salary</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Is Active</th>
          <tr v-for="(sal, index) in cmd.ListSalary">
            <td>{{ index + 1 }}</td>
            <td>{{ sal.salary }}</td>
            <td>{{ sal.startDateString }}</td>
            <td>{{ sal.endDateString }}</td>
            <td  v-if="sal.isActive === true">Active</td>
            <td  v-if="sal.isActive === false || sal.isActive === null">Not Active</td>
            <td><a href="" @click.prevent="deleteSalary(sal)">Hapus</a></td>
          </tr>
        </table>
      </div>
    </div>



    <div class="modal" transition="zoom">
      <div class="modal-background"></div>
      <div class="modal-content">
        <header class="modal-card-head">
          <p class="modal-card-title">Delete Confirmation</p>
          <button class="delete" @click="close"></button>
        </header>
        <section class="modal-card-body">
          <p style="font-size:16px">Are you sure to delete this item?</p>
        </section>
        <footer class="modal-card-foot">
          <div class="column" style="padding:0">
            <div class="level-right">
              <button class="button is-danger" @click="confirm">Delete</button>
              <button class="button" @click="close">Cancel</button>
            </div>
          </div>
        </footer>
      </div>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import bus from 'src/shared/bus'
import Datepicker from 'vuejs-datepicker'

export default {
  components: {
    Datepicker
  },
  props: {
    cmd: {
      type: Object,
      required: true
    }
  },

  data () {
    return {
      pricewithSeparator: null,
      isNew: true,
      errors: {},
      clearButton: true,
      checkError: false,
      confirmDelete: false,
      tempData: null
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('FAMILY FORM CREATED')
  },
  created () {

  },
  methods: {
    close () {
      $('.modal').removeClass('is-active')
    },
    confirm () {
      this.confirmDelete = true
      this.deleteSalary(this.tempData)
      $('.modal').removeClass('is-active')
    },
    addSalary () {
      this.validateAll()
      console.log('testestes', this.cmd)

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }

      this.cmd.ListSalary.push({
        id: null,
        employeeId: null,
        salary: this.cmd.salary,
        startDate: this.cmd.startDate,
        endDate: this.cmd.endDate,
        isActive: this.cmd.isActive,
        startDateString: this.cmd.startDateString,
        endDateString: this.cmd.endDateString

      })
      this.clear()
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
    clear () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.salary = null
      this.cmd.startDate = null
      this.cmd.startDateString = null
      this.cmd.endDate = null
      this.cmd.endDateString = null
      this.cmd.isActive = null
      this.pricewithSeparator = null
    },
    deleteSalary (sal) {
      this.tempData = sal
      if (this.confirmDelete === true) {
        _.remove(this.cmd.ListSalary, (delSalary) => {
          return (delSalary.salary === sal.salary) && (delSalary.startDate === sal.startDate) && (delSalary.endDate === sal.endDate)
        })
        this.$forceUpdate()
        this.confirmDelete = false
      }
      $('.modal').addClass('is-active')
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
