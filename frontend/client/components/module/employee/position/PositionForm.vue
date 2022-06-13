<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Organization Structure<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
              <span class="select is-fullwidth">
                <select v-model="cmd.structureId" :class="{'is-danger' : errors.structureId}">
                  <option v-for="option in listStructure" :value="option.value">
                    {{ option.text }}
                  </option>
                </select>
              </span>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.structureId">
            <span class="help is-danger">{{ errors.structureId }}</span>
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
          <div class="control" v-if="errors.endDate"> 
            <span class="help is-danger">{{ errors.endDate }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Notes</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <textarea class="textarea" :class="{'is-danger' : errors.notes}"
                   type="text" v-model="cmd.notes" placeholder="Notes"/>
          </div>
          <div class="control" v-if="errors.notes">
            <span class="help is-danger">{{ errors.notes }}</span>
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
            <button class="button is-info" @click="addPosition()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Position</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column">
        <table class="vuetable ui blue selectable celled stackable attached table">
          <th>No.</th>
          <th>Organization Structure</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Notes</th>
          <th>Is Active</th>
          <tr v-for="(sal, index) in cmd.ListPosition">
            <td>{{ index + 1 }}</td>
            <td>{{ sal.structureName }}</td>
            <td>{{ sal.startDateString }}</td>
            <td>{{ sal.endDateString }}</td>
            <td>{{ sal.notes }}</td>
            <td  v-if="sal.isActive === true">Active</td>
            <td  v-if="sal.isActive === false || sal.isActive === null">Not Active</td>
            <td><a href="" @click.prevent="deletePosition(sal)">Hapus</a></td>
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
import axios from 'axios'

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
      listStructure: [],
      confirmDelete: false,
      tempData: null
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('POSITION FORM CREATED')
  },
  created () {
    this.fetchData()
  },

  methods: {
    close () {
      $('.modal').removeClass('is-active')
    },
    confirm () {
      this.confirmDelete = true
      this.deletePosition(this.tempData)
      $('.modal').removeClass('is-active')
    },
    fetchData () {
      console.log('START FETCH')
      axios.all([
        axios.get('/organization-structure/structurelist')
      ]).then(axios.spread((listStructure) => {
        this.listStructure.push({value: null, text: 'Select the following choices'})
        for (let position of listStructure.data) {
          this.listStructure.push({value: position.id, text: position.name})
        }
      }))
    },
    addPosition () {
      this.validateAll()

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }
      axios.get('/organization-structure/' + this.cmd.structureId)
        .then((resp) => {
          this.cmd.structureName = resp.data.name
          this.cmd.ListPosition.push({
            id: null,
            employeeId: null,
            structureId: this.cmd.structureId,
            structureName: this.cmd.structureName,
            startDate: this.cmd.startDate,
            endDate: this.cmd.endDate,
            notes: this.cmd.notes,
            isActive: this.cmd.isActive,
            startDateString: this.cmd.startDateString,
            endDateString: this.cmd.endDateString
          })
          this.clear()
          console.log('cek cmd', this.cmd.ListPosition)
        })
    },

    validateAll () {
      this.errors = {}
      if (!this.cmd.structureId) {
        this.$set(this.errors, 'structureId', 'The field cannot be empty')
      } else {
        this.cmd.ListPosition.forEach((value) => {
          if (this.cmd.structureId === value.structureId) {
            this.$set(this.errors, 'structureId', 'This value is already used')
          }
        })
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
      this.cmd.structureId = null
      this.cmd.startDate = null
      this.cmd.startDateString = null
      this.cmd.endDate = null
      this.cmd.endDateString = null
      this.cmd.isActive = true
      this.cmd.notes = null
    },
    deletePosition (sal) {
      this.tempData = sal
      if (this.confirmDelete === true) {
        _.remove(this.cmd.ListPosition, (delPosition) => {
          return (delPosition.position === sal.position) && (delPosition.startDate === sal.startDate) && (delPosition.endDate === sal.endDate)
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
