<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update</p>
    </div>
    <div class="modal-card-body">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Name<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.name}"
                   type="text" v-model="cmd.name" placeholder="Name"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.name"> 
            <span class="help is-danger">{{ errors.name }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Relation<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.relation}"
                   type="text" v-model="cmd.relation" placeholder="Relation"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.relation"> 
            <span class="help is-danger">{{ errors.relation }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Birth Place</label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.birthPlace}"
                   type="text" v-model="cmd.birthPlace" placeholder="Birth Place"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.birthPlace"> 
            <span class="help is-danger">{{ errors.birthPlace }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Birth Date</label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-half">
            <datepicker v-model="cmd.birthDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedBirthDate($event)"
                        @cleared="clearedBirthDate($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.birthDate"> 
            <span class="help is-danger">{{ errors.birthDate }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Phone Number</label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.phoneNumber}"
                   type="number" v-model="cmd.phoneNumber" placeholder="Phone Number"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.phoneNumber">
            <span class="help is-danger">{{ errors.phoneNumber }}</span>
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
        errors: {},
        clearButton: true
      }
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      console.log('FAMILY EDIT FORM CREATED')
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
        axios.post('/employee-family/add', this.cmd)
          .then((resp) => {
            this.$emit('close')
            bus.$emit('NOTIFY', 'success', 'Data has been updated')
            bus.$emit('RELOAD_FAMILY_LIST')
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
        if (!this.cmd.name) {
          this.$set(this.errors, 'name', 'The field cannot be empty')
        }
        if (!this.cmd.relation) {
          this.$set(this.errors, 'relation', 'The field cannot be empty')
        }
      },
      selectedBirthDate (event) {
        if (event !== null) {
          this.cmd.birthDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedBirthDate (event) {
        this.cmd.birthDateString = null
      }
    }
  }
</script>
