<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Project Payment</p>
      <button class="delete" @click="close"></button>
    </div>
    <div class="modal-card-body">
      <div v-if="$route.params.menu != 'view'">
        <div class="columns">
          <div class="column is-4">
            <div class="level-right">
              <label class="label">Project Contract Number</label>
            </div>
          </div>
          <div class="column is-one-third">
            <label class="label">{{ cmd.contractNumber }}</label>
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
<!--        {{cmd}}-->
        <div class="columns">
          <div class="column is-4">
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

      <div class="columns">
        <div class="column">
          <vuetable ref="vuetable"
                    api-url="/projectPayment/table"
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
            <template slot="actions" scope="props">
              <div class="custom-actions">
                <button class="button is-info" v-if="$route.params.menu != 'view'" @click="editPayment(props.rowData)">
                  <i class="fa fa-pencil"/>
                </button>
                <button class="button is-danger" v-if="$route.params.menu != 'view'" @click="deletePayment(props.rowData)">
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
          <project-payment-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></project-payment-delete>
        </modal>
        <modal :visible="showEditModal" @close="closeEditModal" transition="zoom">
          <project-payment-update-detail :cmd="editCmd"  @close="closeEditModal"></project-payment-update-detail>
        </modal>
      </div>

      <!--<div class="control is-horizontal">
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
      </div>-->
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import { Modal } from 'vue-bulma-modal'
  import ValidationMixin from '../../../mixin/ValidationMixin'
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
  import ProjectPaymentDelete from './ProjectPaymentDelete'
  import ProjectPaymentUpdateDetail from './ProjectPaymentUpdateDetail'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'term',
      sortField: 'term',
      title: 'Term',
      titleClass: 'center aligned'
    },
    {
      name: 'percentage',
      sortField: 'percentage',
      title: 'Percentage',
      titleClass: 'center aligned'
    },
    {
      name: 'value',
      sortField: 'value',
      title: 'value',
      callback: 'thousandSeprator',
      titleClass: 'center aligned'
    },
    {
      name: 'dueDateString',
      sortField: 'dueDateString',
      title: 'Due Date',
      titleClass: 'center aligned'
    },
    {
      name: 'isPaidString',
      sortField: 'isPaidString',
      title: 'Is Paid',
      titleClass: 'center aligned'
    },
    {
      name: 'notes',
      sortField: 'notes',
      title: 'Notes',
      titleClass: 'center aligned'
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
      Modal,
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo,
      ProjectPaymentDelete,
      ProjectPaymentUpdateDetail
    },
    props: {
      cmd: {
        type: Object
      }
    },
    mixins: [ValidationMixin],
    data () {
      return {
        priceWithSeparator: null,
        errorSelect: true,
        clearButton: true,
        projectList: [],
        projectItem: {
          value: '',
          text: ''
        },
        showEditModal: false,
        showDeleteModal: false,
        deleteCmd: {},
        editCmd: {},
        form: {
          contractId: this.cmd.contractId
        },
        fields: tableColumns,
        sortOrder: [{
          field: 'term',
          direction: 'desc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
        errors: {}
      }
    },
    created () {

    },
    mounted () {
      let thisVM = this
      bus.$on('RELOAD_PAYMENT_LIST', function () {
        console.log('RELOAD_PAYMENT_LIST : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    methods: {
      close () {
        this.$emit('close')
      },
      cancel () {
        this.$emit('close')
      },
      reset () {
        this.cmd.term = null
        this.cmd.percentage = null
        this.cmd.value = null
        this.cmd.valueString = null
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
        axios.post('/projectPayment', this.cmd)
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
        this.validateNotBlank('value', this.cmd.value)
        this.validateNotBlank('dueDate', this.cmd.dueDate)
        this.validatePersentage('percentage', this.cmd.percentage)
      },
      populateData () {

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
      closeDeleteModal () {
        this.showDeleteModal = false
      },
      deletePayment (item) {
        this.deleteCmd = item
        this.showDeleteModal = true
      },
      editPayment (item) {
        this.editCmd = item
        this.showEditModal = true
      },
      closeEditModal () {
        this.showEditModal = false
      },
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
      }
    }
  }
</script>
