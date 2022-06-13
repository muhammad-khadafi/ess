<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update</p>
    </div>
    <div class="modal-card-body">
      <div class="columns">
        <div class="column is-4">
          <div class="level-right">
            <label class="label">Term<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.term}"
                   type="number" v-model="cmd.term" placeholder="Term"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.term">
            <span class="help is-danger">{{ errors.term }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-4">
          <div class="level-right">
            <label class="label">Percentage<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.percentage}"
                   type="number" v-model="cmd.percentage" placeholder="Percentage"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.percentage">
            <span class="help is-danger">{{ errors.percentage }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-4">
          <div class="level-right">
            <label class="label">Value<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" v-on:keyup="onBlurNumber" :class="{'is-danger' : errors.value}"
                   type="text" v-model="priceWithSeparator" placeholder="Value"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.value">
            <span class="help is-danger">{{ errors.value }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-4">
          <div class="level-right">
            <label class="label">Due Date<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <datepicker v-model="cmd.dueDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Due Date"
                        @cleared="clearedTanggal($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.dueDate">
            <span class="help is-danger">{{ errors.dueDate }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-4">
          <div class="level-right">
            <label class="label">Is Paid ?</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="checkbox" :class="{'is-danger' : errors.paid}"
                   type="checkbox" v-on:click="clickedBox" v-model="cmd.paid"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.paid">
            <span class="help is-danger">{{ errors.paid }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-4">
          <div class="level-right">
            <label class="label">Notes</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.notes}"
                   type="text" v-model="cmd.notes" placeholder="Notes"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.notes">
            <span class="help is-danger">{{ errors.notes }}</span>
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
  import ValidationMixin from '../../../mixin/ValidationMixin'

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
        priceWithSeparator: null,
        errors: {}
      }
    },
    created () {
      this.priceWithSeparator = this.thousandSeprator(this.cmd.value)
      this.cmd.isPaid = this.cmd.paid
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
        axios.put('/projectPayment/' + this.$route.params.id, this.cmd)
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
      onBlurNumber () {
        this.priceWithSeparator = this.thousandSeprator(this.priceWithSeparator)
      },
      thousandSeprator (amount) {
        if (this.priceWithSeparator !== null) {
          this.cmd.value = this.priceWithSeparator.toString().replace(/\./g, '')
        }
        if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
          return amount.toString().replace(/\./g, '').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
        } else {
          return amount
        }
      },
      clickedBox () {
        this.cmd.isPaid = !this.cmd.paid
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
        this.validateNotBlank('term', this.cmd.term)
        this.validatePersentage('percentage', this.cmd.percentage)
        this.validateNotBlank('value', this.cmd.value)
        this.validateNotBlank('dueDate', this.cmd.dueDate)
      },
      populateData () {

      }
    }
  }
</script>
