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
      <div v-if="$route.params.menu != 'view'">
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
<!--        {{cmd}}-->
        <div class="columns">
          <div class="column is-3">
            <div class="level-right">
              <label class="label"></label>
            </div>
          </div>
          <div class="column is-one-third">
            <div class="control is-grouped level-left">
              <button class="button is-info" @click="submit()">
                <span>add</span>
              </button>
            </div>
          </div>
        </div>
      </div>

      <br>
      <br>

      <div class="columns">
        <div class="column">
          <vuetable ref="vuetable"
                    api-url="/projectContract/table"
                    :fields="fields"
                    pagination-path="pagination"
                    :sort-order="sortOrder"
                    :multi-sort="multiSort"
                    :per-page="perPage"
                    :append-params="form"
                    @vuetable:pagination-data="onPaginationData"
                    @vuetable:load-success="onLoadSuccess"
                    @vuetable:load-error="onLoadError"
                    @vuetable:loading="showLoader"
                    @vuetable:loaded="hideLoader">
            <template slot="actionsPayment" scope="props">
              <div class="custom-actions">
                <!--<button class="button is-info" @click="editContract(props.rowData)">
                  <i class="fa fa-pencil"/>
                </button>-->
                <a href="" @click.prevent="editPayment(props.rowData)">Contract Payment</a>
                <br>
              </div>
            </template>
            <template slot="actions" scope="props">
              <div class="custom-actions">
                <button class="button is-info" v-if="$route.params.menu != 'view'" @click="editContract(props.rowData)">
                  <i class="fa fa-pencil"/>
                </button>
                <button class="button is-danger" v-if="$route.params.menu != 'view'" @click="deleteContract(props.rowData)">
                  <i class="fa fa-trash"/>
                </button>
                <br>
              </div>
            </template>
          </vuetable>
          <nav class="columns">
            <div class="column">
              <vuetable-pagination-info ref="paginationInfo"
                                        :pagination-info-template="paginationInfoTemplate"
                                        :pagination-info-no-data-template="paginationInfoNoDataTemplate"/>
            </div>
            <div class="column">
              <vuetable-pagination ref="pagination" @vuetable-pagination:change-page="onChangePage"/>
            </div>
          </nav>
        </div>
        <modal :visible="showDeleteModal" @close="closeDeleteModal" transition="zoom">
          <project-contract-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></project-contract-delete>
        </modal>
        <modal :visible="showEditModal" @close="closeEditModal" transition="zoom">
          <project-contract-update :cmd="editCmd"  @close="closeEditModal"></project-contract-update>
        </modal>
        <modal :visible="showEditPaymentModal" @close="closeEditPaymentModal" transition="zoom">
          <project-payment-update :cmd="paymentCmd"  @close="closeEditPaymentModal"></project-payment-update>
        </modal>
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
              <div class="column is-4">
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
              <div class="column is-4">
                <div class="level-right">
                  <label class="label">Value</label>
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
                  <label class="label">Due Date</label>
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
                    <span>add</span>
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
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
  import ProjectContractUpdate from './ProjectContractUpdate'
  import ProjectContractDelete from './ProjectContractDelete'
  import ProjectPaymentUpdate from '../projectPayment/ProjectPaymentUpdate'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import ValidationMixin from '../../../mixin/ValidationMixin'
  import { Modal } from 'vue-bulma-modal'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'contractNumber',
      sortField: 'contractNumber',
      title: 'Contract Number',
      titleClass: 'center aligned'
    },
    {
      name: 'contractDateString',
      sortField: 'contractDateString',
      title: 'Contract Date',
      titleClass: 'center aligned'
    },
    {
      name: 'signee1',
      sortField: 'signee1',
      title: 'Signee 1',
      titleClass: 'center aligned'
    },
    {
      name: 'signee2',
      sortField: 'signee2',
      title: 'signee 2',
      titleClass: 'center aligned'
    },
    {
      name: '__slot:actionsPayment',
      title: 'Payment',
      titleClass: 'center aligned',
      dataClass: 'center aligned'
    },
    {
      name: '__slot:actions',
      title: 'Action',
      titleClass: 'center aligned',
      dataClass: 'center aligned'
    }
  ]

  export default {
    components: {
      BasicSelect,
      Datepicker,
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo,
      Modal,
      ProjectContractUpdate,
      ProjectContractDelete,
      ProjectPaymentUpdate
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
        showEditPaymentModal: false,
        showDeleteModal: false,
        showEditModal: false,
        deleteCmd: {},
        editCmd: {},
        paymentCmd: {
          contractId: null,
          contractNumber: null,
          term: null,
          percentage: null,
          value: null,
          valueString: null,
          dueDate: null,
          isPaid: false,
          notes: null
        },
        form: {
          projectId: this.$route.params.id
        },
        fields: tableColumns,
        sortOrder: [{
          field: 'id',
          direction: 'desc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
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
    mounted () {
      let thisVM = this
      bus.$on('RELOAD_CONTRACT_LIST', function () {
        console.log('RELOAD_CONTRACT_LIST : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    methods: {
      search () {
        this.errors = {}
        this.$refs.vuetable.refresh()
      },
      showLoader () {
        console.log('on LOADING')
        bus.$emit('SHOW_LOADING')
      },
      hideLoader () {
        console.log('on LOADED')
        bus.$emit('HIDE_LOADING')
      },
      onLoadSuccess (response) {
        console.log('on LOAD SUCCESS')
        console.log(response)
        // set pagination data to pagination-info component
        this.$refs.paginationInfo.setPaginationData(response.data)
      },
      onLoadError (response) {
        let thisVM = this

        console.log('on LOAD ERROR : ', response)
        if (response.status === 400) {
          bus.$emit('NOTIFY', 'danger', 'Gagal load data')
        } else if (response.status === 422) {
          console.log(response)

          _.each(response.data.errors, function (value) {
            thisVM.$set(thisVM.errors, value.field, value.message)
          })
        }

        this.$refs.vuetable.tableData = null
        this.$refs.paginationInfo.setPaginationData(null)
        this.$refs.pagination.setPaginationData(null)
      },
      onPaginationData (tablePagination) {
        console.log('on PAGINATION DATA')
        console.log(tablePagination)
        this.$refs.paginationInfo.setPaginationData(tablePagination)
        this.$refs.pagination.setPaginationData(tablePagination)
      },
      onChangePage (page) {
        console.log('on CHANGE PAGE')
        this.$refs.vuetable.changePage(page)
      },
      closePayment () {
        $('#paymentModal').removeClass('is-active')
      },
      confirmPayment () {
        this.confirmPay = true
        // this.deleteProjContrct(this.tempData)
        $('#deleteModal').removeClass('is-active')
      },
      closeEditPaymentModal () {
        this.showEditPaymentModal = false
      },
      editPayment (item) {
        this.paymentCmd.contractId = item.id
        this.paymentCmd.contractNumber = item.contractNumber
        this.showEditPaymentModal = true
      },
      closeDeleteModal () {
        this.showDeleteModal = false
      },
      deleteContract (item) {
        this.deleteCmd = item
        this.showDeleteModal = true
      },
      editContract (item) {
        this.editCmd = item
        this.showEditModal = true
      },
      closeEditModal () {
        this.showEditModal = false
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
        /*
        this.validateAll()

        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        */

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
      reset () {
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
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        this.cmd.projectId = this.$route.params.id
        axios.post('/projectContract', this.cmd)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            if (this.$refs.vuetable !== undefined) {
              this.$refs.vuetable.reload()
              this.reset()
            }
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
