<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update</p>
    </div>
    <div class="modal-card-body">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Grade<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
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
        <div class="column is-half">
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
        <div class="column is-half">
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
        <div class="column is-half">
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
        <div class="column is-half">
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
        <div class="column is-half">
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
        type: Object
      }
    },
    data () {
      return {
        isNew: true,
        errors: {}
      }
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      console.log('EDUCATION EDIT FORM CREATED')
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
        this.$emit('close')
      },
      back () {
        this.$emit('close')
      },
      submit () {
        bus.$emit('SHOW_LOADING')

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
        let thisVM = this
        axios.post('/education/add', this.cmd)
          .then((resp) => {
            this.$emit('close')
            bus.$emit('NOTIFY', 'success', 'Data has been updated')
            bus.$emit('RELOAD_EDUCATION_LIST')
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
      }
    }
  }
</script>
