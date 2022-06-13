<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Salary<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.salary}"
                   type="text" v-on:keyup="onBlurNumber" v-model="pricewithSeparator" placeholder="Salary"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.salary">
            <span class="help is-danger">{{ errors.salary }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Start Date<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-half">
            <datepicker v-if="!errors.startDate" v-model="cmd.startDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedStartDate($event)"
                        @cleared="clearedStartDate($event)"
                        :clear-button="clearButton"></datepicker>
            <datepicker v-if="errors.startDate" v-model="cmd.startDate" input-class="input is-danger" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedStartDate($event)"
                        @cleared="clearedStartDate($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.startDate"> 
            <span class="help is-danger">{{ errors.startDate }}</span> 
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">End Date</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <datepicker class="control is-expanded" v-model="cmd.endDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="End Date"
                        @selected="selectedEndDate($event)"
                        @cleared="clearedEndDate($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Is Active</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="checkbox" :class="{'is-danger' : errors.isActive}"
                   type="checkbox" v-model="cmd.isActive"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.isActive"> 
            <span class="help is-danger">{{ errors.isActive }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control"> 
            <button class="button is-info" @click="addSalary()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Salary</span>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column">
        <vuetable ref="vuetable"
                  api-url="/employee-salary/table"
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
              <button class="button is-info" @click="editSalary(props.rowData)">
                <i class="fa fa-pencil"/>
              </button>
              <button class="button is-danger" @click="deleteSalary(props.rowData)">
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
        <salary-delete :deleteForm="deleteCmd"  @close="closeDeleteModal"></salary-delete>
      </modal>
      <modal :visible="showEditModal" @close="closeEditModal" transition="zoom">
        <salary-detail-item-edit :cmd="editCmd"  @close="closeEditModal"></salary-detail-item-edit>
      </modal>
    </div>

<!--    {{cmd}}-->

  </div>
</template>

<script>
  import axios from 'axios'
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
  import SalaryDelete from './SalaryDelete'
  import SalaryDetailItemEdit from './SalaryDetailItemEdit'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { Modal } from 'vue-bulma-modal'
  import Datepicker from 'vuejs-datepicker'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'salary',
      sortField: 'salary',
      title: 'Salary',
      titleClass: 'center aligned',
      callback: 'thousandSeparator'
    },
    {
      name: 'startDateString',
      sortField: 'startDate',
      title: 'Start Date',
      titleClass: 'center aligned'
    },
    {
      name: 'endDateString',
      sortField: 'endDate',
      title: 'End Date',
      titleClass: 'center aligned'
    },
    {
      name: 'isActiveString',
      sortField: 'isActive',
      title: 'Is Active',
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
    props: {
      cmd: {
        type: Object,
        required: true
      }
    },
    components: {
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo,
      SalaryDelete,
      SalaryDetailItemEdit,
      Modal,
      Datepicker
    },
    data () {
      return {
        clearButton: true,
        showDeleteModal: false,
        showEditModal: false,
        deleteCmd: {},
        editCmd: {},
        form: {
          employeeId: this.$route.params.id
        },
        pricewithSeparator: null,
        errors: {},
        fields: tableColumns,
        sortOrder: [{
          field: 'startDate',
          direction: 'desc'
        }],
        multiSort: true,
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
        listBenefit: []
      }
    },
    created () {
    },
    mounted () {
      let thisVM = this
      bus.$on('RELOAD_SALARY_LIST', function () {
        console.log('RELOAD_SALARY_LIST : ', thisVM.$refs.vuetable)

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
      onClick (action, data) {
        console.log('actions: on-click PARENT', data.name)
      },
      deleteSalary (item) {
        this.deleteCmd = item
        this.showDeleteModal = true
      },
      closeDeleteModal () {
        this.showDeleteModal = false
      },
      editSalary (item) {
        this.editCmd = item
        this.showEditModal = true
      },
      closeEditModal () {
        this.showEditModal = false
      },
      addSalary () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }

        this.cmd.employeeId = this.$route.params.id

        axios.post('/employee-salary/add', this.cmd)
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
        this.errors = {}
        if (!this.cmd.salary) {
          this.$set(this.errors, 'salary', 'The field cannot be empty')
        }
        if (!this.cmd.startDate) {
          this.$set(this.errors, 'startDate', 'The field cannot be empty')
        }
        if (this.cmd.startDate != null && this.cmd.endDate != null) {
          if (this.cmd.startDate > this.cmd.endDate) {
            this.$set(this.errors, 'startDate', 'Start date must be earlier than end date')
            this.$set(this.errors, 'endDate', 'Start date must be earlier than end date')
          }
        }
      },
      reset () {
        this.cmd.id = null
        this.cmd.employeeId = null
        this.cmd.salary = null
        this.cmd.startDate = null
        this.cmd.startDateString = null
        this.cmd.endDate = null
        this.cmd.endDateString = null
        this.cmd.isActive = true
      },
      selectedStartDate (event) {
        if (event !== null) {
          this.cmd.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedStartDate (event) {
        this.cmd.startDateString = null
      },
      selectedEndDate (event) {
        if (event !== null) {
          this.cmd.endDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedEndDate (event) {
        this.cmd.endDateString = null
      },
      onBlurNumber () {
        this.pricewithSeparator = this.pricewithSeparator.replace(/\D/g, '')
        this.pricewithSeparator = this.thousandSeparator(this.pricewithSeparator)
      },
      thousandSeparator (amount) {
        if (this.pricewithSeparator != null) {
          this.cmd.salary = this.pricewithSeparator.replace(/\D/g, '')
        }
        if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
          return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
        } else {
          return amount
        }
      }
    }
  }
</script>
