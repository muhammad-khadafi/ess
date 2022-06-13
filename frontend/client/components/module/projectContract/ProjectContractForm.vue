<template>
  <div>
    <div class="box">
      <!--<div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Project Name</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="projectList"
                          :selected-option="projectItem"
                          placeholder="Project Name"
                          @select="onProjectSelect"
                          :class="{'is-danger' : errors.projectId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.projectId">
            <span class="help is-danger">{{ errors.projectId }}</span>
          </div>
        </div>
      </div>-->
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Contract Numbers<font color="red">*</font></label>
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
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <button class="button is-info" @click="add()">
              <span>Add</span>
            </button>
          </div>
        </div>
      </div>

      <br>
      <br>

      <div class="columns">
        <div class="column">
          <table class="vuetable ui blue selectable celled stackable attached table">
            <th>No.</th>
            <th>Contract Number</th>
            <th>Contract Date</th>
            <th>Signee 1</th>
            <th>Signee 2</th>
            <th>Payment</th>
            <tr v-for="(projContrct, index) in cmd.listProjectContract">
              <td>{{ index + 1 }}</td>
              <td>{{ projContrct.contractNumber }}</td>
              <td>{{ projContrct.contractDateString }}</td>
              <td>{{ projContrct.signee1 }}</td>
              <td>{{ projContrct.signee2 }}</td>
              <td><a href="" @click.prevent="addPayment(projContrct)">Contract Payment</a></td>
              <td><a href="" @click.prevent="deleteProjContrct(projContrct)">Delete</a></td>
            </tr>
          </table>
        </div>
      </div>

      <div id="deleteModal" class="modal" transition="zoom">
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

      <!--   payment Contract modal start   -->
      <div id="paymentModal" class="modal" transition="zoom">
        <div class="modal-background"></div>
        <div class="modal-content">
          <header class="modal-card-head">
            <p class="modal-card-title">Add Project Payment</p>
            <button class="delete" @click="closePayment"></button>
          </header>
          <section class="modal-card-body">
            <div class="columns">
              <div class="column is-4">
                <div class="level-right">
                  <label class="label">Project Contract Number</label>
                </div>
              </div>
              <div class="column is-one-third">
                <label class="label">{{ contractNumberSelected }}</label>
              </div>
            </div>

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
                              @selected="selectedTanggalModal($event)"
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

            <div class="columns">
              <div class="column is-4">
                <div class="level-right">
                  <label class="label"></label>
                </div>
              </div>
              <div class="column is-one-third">
                <div class="control is-grouped level-left">
                  <button class="button is-info" @click="addListPayment()">
                    <span>Add</span>
                  </button>
                </div>
              </div>
            </div>

            <div class="columns">
              <div class="column">
                <table class="vuetable ui blue selectable celled stackable attached table">
                  <th>No.</th>
                  <th>term</th>
                  <th>Percentage</th>
                  <th>value</th>
                  <th>dueDate</th>
                  <th>is Paid ?</th>
                  <th>Notes</th>
                  <tr v-for="(projPayment, index) in cmd.listProjectPayment">
                    <td v-if="projPayment.contractNumber === contractNumberSelected">{{ index + 1 }}</td>
                    <td v-if="projPayment.contractNumber === contractNumberSelected">{{ projPayment.term }}</td>
                    <td v-if="projPayment.contractNumber === contractNumberSelected">{{ projPayment.percentage }}</td>
                    <td v-if="projPayment.contractNumber === contractNumberSelected">{{ projPayment.valueString }}</td>
                    <td v-if="projPayment.contractNumber === contractNumberSelected">{{ projPayment.dueDateString }}</td>
                    <td v-if="projPayment.contractNumber === contractNumberSelected">{{ projPayment.isPaid }}</td>
                    <td v-if="projPayment.contractNumber === contractNumberSelected">{{ projPayment.notes }}</td>
                    <td v-if="projPayment.contractNumber === contractNumberSelected"><a href="" @click.prevent="deleteProjpayment(projPayment)">Delete</a></td>
                  </tr>
                </table>
              </div>
            </div>
<!--            {{cmd}}-->
          </section>
          <footer class="modal-card-foot">
            <div class="column" style="padding:0">
              <div class="level-right">
                <button class="button is-success" @click="closePayment">Save</button>
<!--                <button class="button is-danger" @click="confirmPayment">Delete</button>-->
<!--                <button class="button" @click="closePayment">Cancel</button>-->
              </div>
            </div>
          </footer>
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
  import { Modal } from 'vue-bulma-modal'

  export default {
    components: {
      BasicSelect,
      Datepicker,
      Modal
    },
    mixins: [ValidationMixin],
    props: {
      cmd: {
        type: Object,
        required: true
      }
    },
    data () {
      return {
        errorSelect: true,
        clearButton: true,
        priceWithSeparator: null,
        contractNumberSelected: 10,
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      closePayment () {
        $('#paymentModal').removeClass('is-active')
      },
      confirmPayment () {
        this.confirmPay = true
        // this.deleteProjContrct(this.tempData)
        $('#deleteModal').removeClass('is-active')
      },
      close () {
        $('#deleteModal').removeClass('is-active')
      },
      confirm () {
        this.confirmDelete = true
        this.deleteProjContrct(this.tempData)
        $('#deleteModal').removeClass('is-active')
      },
      fetchData () {
        /* axios.all([
          axios.get('/project/getAllProject'),
          axios.get('/master-reference/getReference/Project Status')
        ]).then(axios.spread((projectList, statusList) => {
          for (let project of projectList.data) {
            this.projectList.push({value: project.id, text: project.name})
            if (project.id === this.cmd.projectId) {
              this.projectItem.text = project.name
            }
          }
          for (let status of statusList.data) {
            this.statusList.push({value: status.id, text: status.name})
          }
        }))
        this.fundingList.push({value: 'internal', text: 'internal'})
        this.fundingList.push({value: 'eksternal', text: 'eksternal'})

         */
      },
      add () {
        this.validateAll()

        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }

        this.cmd.listProjectContract.push({
          id: null,
          projectId: null,
          contractNumber: this.cmd.contractNumber,
          contractDate: this.cmd.contractDate,
          contractDateString: this.cmd.contractDateString,
          signee1: this.cmd.signee1,
          signee2: this.cmd.signee2
        })

        this.clear()
      },
      addListPayment () {
        this.validatePaymentAll()

        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }

        this.cmd.listProjectPayment.push({
          id: null,
          term: this.cmd.term,
          contractNumber: this.contractNumberSelected,
          percentage: this.cmd.percentage,
          value: this.cmd.value,
          valueString: this.priceWithSeparator,
          dueDate: this.cmd.dueDate,
          dueDateString: this.cmd.dueDateString,
          isPaid: this.cmd.isPaid,
          notes: this.cmd.notes
        })

        this.clearPayment()
      },
      clear () {
        this.cmd.contractNumber = null
        this.cmd.contractDate = null
        this.cmd.signee1 = null
        this.cmd.signee2 = null
      },
      clearPayment () {
        this.cmd.term = null
        this.cmd.percentage = null
        this.cmd.value = null
        this.cmd.dueDate = null
        this.cmd.isPaid = null
        this.cmd.notes = null
        this.priceWithSeparator = null
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        axios.post('/projectContract', this.cmd)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/projectContract')
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
        this.validateNotBlank('contractNumber', this.cmd.contractNumber)
      },
      validatePaymentAll () {
        this.validateNotBlank('term', this.cmd.term)
        this.validatePersentage('percentage', this.cmd.percentage)
        this.validateNotBlank('value', this.cmd.value)
        this.validateNotBlank('dueDate', this.cmd.dueDate)
      },
      deleteProjContrct (ProjContrct) {
        this.tempData = ProjContrct
        if (this.confirmDelete === true) {
          _.remove(this.cmd.listProjectContract, (delProjContrct) => {
            return (delProjContrct.contractNumber === ProjContrct.contractNumber)
          })
          this.$forceUpdate()
          this.confirmDelete = false
        }
        $('#deleteModal').addClass('is-active')
      },
      deleteProjpayment (Projpayment) {
        this.tempData = Projpayment
        _.remove(this.cmd.listProjectPayment, (delProjContrct) => {
          return (delProjContrct.term === Projpayment.term)
        })
        this.$forceUpdate()
      },
      addPayment (ProjContrct) {
        this.contractNumberSelected = ProjContrct.contractNumber
        $('#paymentModal').addClass('is-active')
        this.$forceUpdate()
      },
      selectedTanggal (event) {
        if (event !== null) {
          this.cmd.contractDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      selectedTanggalModal (event) {
        if (event !== null) {
          this.cmd.dueDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedTanggal (event) {
        this.cmd.contractDateString = null
      },
      onBlurNumber () {
        this.priceWithSeparator = this.thousandSeprator(this.priceWithSeparator)
      },
      thousandSeprator (amount) {
        this.cmd.value = this.priceWithSeparator.toString().replace(/\./g, '')
        if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
          return amount.toString().replace(/\./g, '').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
        } else {
          return amount
        }
      }
    }
  }
</script>
