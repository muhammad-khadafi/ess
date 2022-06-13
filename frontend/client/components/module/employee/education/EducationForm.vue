<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Grade<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <div class="select is-fullwidth">
              <select :class="{'is-danger' : errors.grade}" v-model="cmd.grade">
                <option v-for="option in listGrade" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.grade">
            <span class="help is-danger">{{ errors.grade }}</span>
          </div>
        </div>
      </div>

        <div class="columns">
          <div class="column is-3">
            <div class="level-right">
              <label class="label">Institution<font color="red">*</font></label>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="control is-grouped level-left">
              <input class="input" :class="{'is-danger' : errors.institution}"
                     type="text" v-model="cmd.institution" placeholder="Institution"/>
            </div>
          </div>
          <div class="column">
            <div class="control" v-if="errors.institution"> 
              <span class="help is-danger">{{ errors.institution }}</span> 
            </div>
          </div>
        </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Major<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.major}"
                   type="text" v-model="cmd.major" placeholder="Major"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.major">
            <span class="help is-danger">{{ errors.major }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Start Year<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.startYear}"
                   type="number" v-model="cmd.startYear" placeholder="Start Year"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.startYear"> 
            <span class="help is-danger">{{ errors.startYear }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">End Year<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.endYear}"
                   type="number" v-model="cmd.endYear" placeholder="End Year"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.endYear">
            <span class="help is-danger">{{ errors.endYear }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">IPK</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.result}"
                   type="text" v-model="cmd.result" placeholder="IPK"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.result">
            <span class="help is-danger">{{ errors.result }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <label class="label"></label>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left"> 
            <button class="button is-info" @click="tambah()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Education</span>
            </button>&nbsp;
            <button class="button is-warning" @click="cancel()">Cancel</button>
          </div>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column">
        <table class="vuetable ui blue selectable celled stackable attached table">
          <th>No.</th>
          <th>Grade</th>
          <th>Institution</th>
          <th>Major</th>
          <th>Start Year</th>
          <th>End Year</th>
          <th>IPK</th>
          <tr v-for="(edu, index) in cmd.ListPendidikan">
            <td>{{ index + 1 }}</td>
            <td>{{ edu.grade }}</td>
            <td>{{ edu.institution }}</td>
            <td>{{ edu.major }}</td>
            <td>{{ edu.startYear }}</td>
            <td>{{ edu.endYear }}</td>
            <td>{{ edu.result }}</td>
            <td><a href="" @click.prevent="deletePendidikan(edu)">Hapus</a></td>
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
import axios from 'axios'
import _ from 'lodash'
import bus from 'src/shared/bus'
import { Modal } from 'vue-bulma-modal'

export default {
  components: {
    Modal
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
      confirmDelete: false,
      tempData: null
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('EDUCATION FORM CREATED')
    this.fetchData()
  },
  created () {
    this.listGrade = [
      { value: null, text: '-Select Grade-' },
      { value: 'SMA', text: 'SMA' },
      { value: 'S1', text: 'S1' },
      { value: 'S2', text: 'S2' },
      { value: 'S3', text: 'S3' }
    ]
    this.listMajor = [
      { value: null, text: '-Select Major-' },
      { value: 'Ilmu Komputer', text: 'Ilmu Komputer' },
      { value: 'Sistem Informasi', text: 'Sistem Informasi' }
    ]
  },
  methods: {
    close () {
      $('.modal').removeClass('is-active')
    },
    confirm () {
      this.confirmDelete = true
      this.deletePendidikan(this.tempData)
      $('.modal').removeClass('is-active')
    },
    reset () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.grade = null
      this.cmd.major = null
      this.cmd.institution = null
      this.cmd.startYear = null
      this.cmd.endYear = null
      this.cmd.result = null
    },
    fetchData () {
      // this.reset()
      console.log('EDUCATION FORM FETCH DATA : ', this.$route)
      console.log(this.$route.params)

      this.cmd.id = this.id
      this.isNew = _.isNil(this.cmd.id)

      console.log(this.isNew)

      let thisVM = this
      if (!this.isNew) {
        axios.get('/education/' + this.cmd.id)
        .then(function (resp) {
          thisVM.cmd = resp.data
        })
        .catch(function (error) {
          console.log('ERROR FETCH DATA', thisVM.id)
          console.log(error.response)
          thisVM.$emit('close')
          thisVM.bus.$emit('NOTIFY', 'danger', 'Data not found')
        })
      }
    },
    cancel () {
      console.log('CANCEL')
      if (this.isNew) {
        this.$router.replace('/employee')
      } else {
        this.$emit('close')
      }
    },
    tambah () {
      this.errors = {}

      if (!this.cmd.grade) {
        this.$set(this.errors, 'grade', 'The field cannot be empty')
      }
      if (!this.cmd.institution) {
        this.$set(this.errors, 'institution', 'The field cannot be empty')
      }
      if (!this.cmd.major) {
        this.$set(this.errors, 'major', 'The field cannot be empty')
      }
      if (!this.cmd.startYear) {
        this.$set(this.errors, 'startYear', 'The field cannot be empty')
      } else {
        if (this.cmd.startYear < 1000 || this.cmd.startYear > 9999) {
          this.$set(this.errors, 'startYear', 'Start year must contain 4 digit number')
        }
        if (this.cmd.startYear === '' || this.cmd.startYear < 0) {
          this.$set(this.errors, 'startYear', 'Start year must contain integer')
        }
      }
      if (!this.cmd.endYear) {
        this.$set(this.errors, 'endYear', 'The field cannot be empty')
      } else {
        if (this.cmd.endYear < 1000 || this.cmd.endYear > 9999) {
          this.$set(this.errors, 'endYear', 'End year must contain 4 digit number')
        }
        if (this.cmd.endYear === '' || this.cmd.endYear < 0) {
          this.$set(this.errors, 'endYear', 'End year must contain integer')
        }
      }
      if (!this.cmd.result) {
        // this.$set(this.errors, 'result', 'The field cannot be empty')
      } else {
        if (this.cmd.result.match(/\d\.\d+[0-9]/) === null && this.cmd.result.length !== 4) {
          this.$set(this.errors, 'result', 'Format IPK (x.xx)')
        }
      }
      if (this.cmd.startYear != null && this.cmd.endYear != null && (this.cmd.startYear >= this.cmd.endYear)) {
        this.$set(this.errors, 'startYear', 'Start date must be earlier than end date')
      }

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }

      this.cmd.ListPendidikan.push({
        id: null,
        employeeId: null,
        grade: this.cmd.grade,
        institution: this.cmd.institution,
        major: this.cmd.major,
        startYear: this.cmd.startYear,
        endYear: this.cmd.endYear,
        result: this.cmd.result
      })
      this.clear()
    },
    clear () {
      this.cmd.grade = null
      this.cmd.institution = null
      this.cmd.major = null
      this.cmd.startYear = null
      this.cmd.endYear = null
      this.cmd.result = null
    },
    deletePendidikan (edu) {
      this.tempData = edu
      if (this.confirmDelete === true) {
        _.remove(this.cmd.ListPendidikan, (delPendidikan) => {
          return (delPendidikan.grade === edu.grade) &&
            (delPendidikan.institution === edu.institution)
        })
        this.$forceUpdate()
        this.confirmDelete = false
      }
      $('.modal').addClass('is-active')
    }
  }
}
</script>
