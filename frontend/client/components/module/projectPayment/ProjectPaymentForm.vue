<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Project Contract Number</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="contractList"
                          :selected-option="contractItem"
                          placeholder="Contract Number"
                          @select="onContractSelect"
                          :class="{'is-danger' : errors.contractId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.contractId">
            <span class="help is-danger">{{ errors.contractId }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Term</label>
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
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Percentage</label>
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
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Value</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.value}"
                   type="text" v-on:keyup="onBlurNumber" v-model="cmd.pricewithSeparator" placeholder="Value"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.value">
            <span class="help is-danger">{{ errors.value }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Due Date</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <datepicker v-model="cmd.dueDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Due Date"
                        @selected="selectedTanggal($event)"
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
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Is Paid ?</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="checkbox" :class="{'is-danger' : errors.isPaid}"
                   type="checkbox" v-model="cmd.isPaid"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.isPaid">
            <span class="help is-danger">{{ errors.isPaid }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
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
      <!--{{cmd}}-->
      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <div class="control">
            <button class="button is-info" @click="submit()">
              <span>Save</span>
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
    mixins: [ValidationMixin],
    data () {
      return {
        errorSelect: true,
        clearButton: true,
        cmd: {
          contractId: null,
          term: null,
          percentage: null,
          value: null,
          dueDate: null,
          isPaid: false,
          notes: null,
          pricewithSeparator: null
        },
        contractList: [],
        contractItem: {
          value: '',
          text: ''
        },
        pricewithSeparator: 0,
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        axios.all([
          axios.get('/projectContract/getAll')
        ]).then(axios.spread((contractList) => {
          for (let contract of contractList.data) {
            this.contractList.push({value: contract.id, text: contract.contractNumber})
          }
        }))
      },
      onContractSelect (item) {
        this.errorSelect = false
        this.contractItem = item
        this.cmd.contractId = item.value
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/projectPayment')
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        axios.post('/projectPayment', this.cmd)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/projectPayment')
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
      validateAll () {
        this.validateNotBlank('contractId', this.cmd.contractId)
        this.validateNotBlank('dueDate', this.cmd.dueDate)
        this.validatePersentage('percentage', this.cmd.percentage)
      },
      selectedTanggal (event) {
        if (event !== null) {
          this.cmd.purchaseDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedTanggal (event) {
        this.cmd.purchaseDateString = null
      },
      onBlurNumber () {
        this.cmd.pricewithSeparator = this.cmd.pricewithSeparator.replace(/\D/g, '')
        this.cmd.pricewithSeparator = this.thousandSeparator(this.cmd.pricewithSeparator)
      },
      thousandSeparator (amount) {
        this.cmd.value = this.cmd.pricewithSeparator.replace(/\D/g, '')
        if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
          return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
        } else {
          return amount
        }
      }
    }
  }
</script>
