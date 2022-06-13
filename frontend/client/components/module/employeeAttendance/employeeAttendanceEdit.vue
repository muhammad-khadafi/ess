<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update Attendance</p>
      <button class="delete" @click="close"></button>
    </div>
    <div class="modal-card-body">
      {{cmd}}
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Clock In</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <label class="label">{{ cmd.checkInString }}</label>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.checkInString">
            <span class="help is-danger">{{ errors.checkInString }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Clock Out</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <label class="label">{{ cmd.checkOutString }}</label>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.checkOutString">
            <span class="help is-danger">{{ errors.checkOutString }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Contract Number<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.contractNumber}"
                   type="text" v-model="cmd.contractNumber" placeholder="Contract Number"/>
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
            <label class="label">Contract Date</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <datepicker v-model="cmd.contractDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Contract Date"
                        @selected="selectedTanggal($event)"
                        @cleared="clearedTanggal($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.contractDate">
            <span class="help is-danger">{{ errors.contractDate }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Signee 1</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.signee1}"
                   type="text" v-model="cmd.signee1" placeholder="Signee 1"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.signee1">
            <span class="help is-danger">{{ errors.signee1 }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Signee 2</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.signee2}"
                   type="text" v-model="cmd.signee2" placeholder="Signee 2"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.signee2">
            <span class="help is-danger">{{ errors.signee2 }}</span>
          </div>
        </div>
      </div>
<!--      {{cmd}}-->
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
  import axios from 'axios'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import ValidationMixin from '../../mixin/ValidationMixin'

  export default {
    components: {
      BasicSelect,
      Datepicker
    },
    props: {
      cmd: {
        type: Object
      }
    },
    mixins: [ValidationMixin],
    data () {
      return {
        errorSelect: true,
        clearButton: true,
        projectList: [],
        projectItem: {
          value: '',
          text: ''
        },
        errors: {}
      }
    },
    created () {

    },
    methods: {
      close () {
        this.$emit('close')
      },
      cancel () {
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
        axios.put('/projectContract/' + this.$route.params.id, this.cmd)
          .then((resp) => {
            this.$emit('close')
            bus.$emit('NOTIFY', 'success', 'Data has been updated')
            bus.$emit('RELOAD_CONTRACT_LIST')
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
      selectedTanggal (event) {
        if (event !== null) {
          this.cmd.purchaseDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedTanggal (event) {
        this.cmd.purchaseDateString = null
      },
      validateAll () {
        this.validateNotBlank('projectId', this.cmd.projectId)
        this.validateNotBlank('contractNumber', this.cmd.contractNumber)
      },
      populateData () {

      }
    }
  }
</script>
