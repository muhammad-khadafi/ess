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
                   type="text" v-on:keyup="onBlurNumber" v-model="pricewithSeparator" placeholder="Value"/>
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
          isPaid: null,
          notes: null
        },
        contractList: [],
        contractItem: {
          value: '',
          text: ''
        },
        projectList: [],
        projectItem: {
          value: '',
          text: ''
        },
        pricewithSeparator: null,
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        let thisVM = this
        axios.get('/projectPayment/' + this.$route.params.id)
          .then(function (resp) {
            console.log('START FETCH')
            thisVM.cmd = resp.data
            thisVM.pricewithSeparator = thisVM.cmd.value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
            console.log('Populate data Start ' + thisVM.cmd.contractId)
            console.log('END FETCH')
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
        axios.all([
          axios.get('/projectContract/getAll')
        ]).then(axios.spread((contractList) => {
          for (let contract of contractList.data) {
            this.contractList.push({value: contract.id, text: contract.contractNumber})
            if (thisVM.cmd.contractId === contract.id) {
              thisVM.contractItem.value = contract.id
              thisVM.contractItem.text = contract.contractNumber
            }
          }
        }))
        this.loading = true
      },
      onProjectSelect (item) {
        this.errorSelect = false
        this.projectItem = item
        this.cmd.projectId = item.value
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
        let thisVM = this

        let req
        req = axios.put('/projectPayment/' + this.$route.params.id, this.cmd)
        req.then(function (resp) {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCES')
          console.log(resp)
          bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')

          console.log('$$$ isNEW', thisVM.isNew)

          thisVM.$router.replace('/projectPayment')
        })
          .catch(function (error) {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log(error.response)

            _.each(error.response.data.errors, function (value) {
              thisVM.$set(thisVM.errors, value.field, value.message)
            })

            console.log('SELESAI')
            if (thisVM.errors.exception) {
              bus.$emit('NOTIFY', 'danger', 'Gagal update data')
            }
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

      },
      onBlurNumber () {
        this.pricewithSeparator = this.pricewithSeparator.replace(/\D/g, '')
        this.pricewithSeparator = this.thousandSeparator(this.pricewithSeparator)
      },
      thousandSeparator (amount) {
        this.cmd.value = this.pricewithSeparator.replace(/\D/g, '')
        if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
          return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
        } else {
          return amount
        }
      }
    }
  }
</script>
