<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Contract No<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.contractNumber}"
                   type="text" v-model="cmd.contractNumber" placeholder="Contract"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.contractNumber"> 
            <span class="help is-danger">{{ errors.contractNumber }}</span> 
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
          <div class="control is-half">
            <datepicker v-model="cmd.endDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="End Date"
                        @selected="selectedEndDate($event)"
                        @cleared="clearedEndDate($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
          <div class="control" v-if="errors.endDate"> 
            <span class="help is-danger">{{ errors.endDate }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Signee<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.signee}"
                   type="text" v-model="cmd.signee" placeholder="Signee"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.signee">
            <span class="help is-danger">{{ errors.signee }}</span>
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
        <div class="column is-one-third">
          <div class="control"> 
            <button class="button is-info" @click="addContract()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Contract</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column">
        <table class="vuetable ui blue selectable celled stackable attached table">
          <th>No.</th>
          <th>Contract No</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Signee</th>
          <th>Is Active</th>
          <tr v-for="(sal, index) in cmd.ListContract">
            <td>{{ index + 1 }}</td>
            <td>{{ sal.contractNumber }}</td>
            <td>{{ sal.startDateString }}</td>
            <td>{{ sal.endDateString }}</td>
            <td>{{ sal.signee }}</td>
            <td  v-if="sal.isActive === true">Active</td>
            <td  v-if="sal.isActive === false || sal.isActive === null">Not Active</td>
            <td><a href="" @click.prevent="deleteContract(sal)">Hapus</a></td>
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
    console.log('CONTRACT FORM CREATED')
  },
  created () {

  },
  methods: {
    close () {
      $('.modal').removeClass('is-active')
    },
    confirm () {
      this.confirmDelete = true
      this.deleteContract(this.tempData)
      $('.modal').removeClass('is-active')
    },
    addContract () {
      this.validateAll()

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }

      this.cmd.ListContract.push({
        id: null,
        employeeId: null,
        contractNumber: this.cmd.contractNumber,
        startDate: this.cmd.startDate,
        endDate: this.cmd.endDate,
        signee: this.cmd.signee,
        isActive: this.cmd.isActive,
        startDateString: this.cmd.startDateString,
        endDateString: this.cmd.endDateString

      })
      this.clear()
    },

    validateAll () {
      this.errors = {}
      if (!this.cmd.contractNumber) {
        this.$set(this.errors, 'contractNumber', 'The field cannot be empty')
      }
      if (!this.cmd.startDate) {
        this.$set(this.errors, 'startDate', 'The field cannot be empty')
      }

      if (!this.cmd.signee) {
        this.$set(this.errors, 'signee', 'The field cannot be empty')
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
      this.cmd.contractNumber = null
      this.cmd.startDate = null
      this.cmd.startDateString = null
      this.cmd.endDate = null
      this.cmd.endDateString = null
      this.cmd.isActive = true
      this.cmd.signee = null
    },
    deleteContract (sal) {
      this.tempData = sal
      if (this.confirmDelete === true) {
        _.remove(this.cmd.ListContract, (delContract) => {
          return (delContract.contract === sal.contract) && (delContract.startDate === sal.startDate) && (delContract.endDate === sal.endDate)
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
    }
  }
}
</script>
