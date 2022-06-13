<template>
  <div>
    <div class="box">
      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Category<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="column is-half">
            <span class="select">
              <select v-model="cmd.category" :class="{'control is-danger' : errors.category}">
                <option v-for="option in categoryList" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
            </span>
            <div class="control is-grouped" v-if="errors.category"> 
              <span class="help is-danger">{{ errors.category }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Request Date<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="column is-half">
            <datepicker v-model="cmd.reqDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Request Date"
                        @selected="selectedTanggalReqDate($event)"
                        @cleared="clearedTanggalReqDate($event)"
                        :clear-button="clearButton"
                        :disabledDates="disabledDates"></datepicker>
            <div class="control is-grouped" v-if="errors.reqDate"> 
              <span class="help is-danger">{{ errors.reqDate }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Paid To<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="column is-half">
            <basic-select
                          :options="employeeList"
                          :selected-option="employeeItem"
                          placeholder="Employee"
                          @select="onEmployeeSelect"
                          :class="{'is-danger' : errors.paidTo}">
            </basic-select>
            <div class="control is-grouped" v-if="errors.paidTo"> 
              <span class="help is-danger">{{ errors.paidTo }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Funding Source<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="column is-half">
            <basic-select :options="fundingList"
                          :selected-option="fundingItem"
                          placeholder="Funding List"
                          @select="onFundingSelect"
                          :class="{'is-danger' : errors.fundingSourceId}">
            </basic-select>
            <div class="control is-grouped" v-if="errors.fundingSourceId"> 
              <span class="help is-danger">{{ errors.fundingSourceId }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Request Amount<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="column is-half">
            <input class="input" v-on:keyup="onBlurNumber" type="text" v-model="cmd.reqAmountSeparator"/>
            <div class="control is-grouped" v-if="errors.reqAmount"> 
              <span class="help is-danger">{{ errors.reqAmount }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Request Amount In Word<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="column is-half">
            <input disabled class="input" type="text" v-model="cmd.amountInWord"/>
            <div class="control is-grouped" v-if="errors.amountInWord"> 
              <span class="help is-danger">{{ errors.amountInWord }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Description<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="column is-half">
            <textarea style="height:200px;" class="input" type="text" v-model="cmd.description"/>
            <div class="control is-grouped" v-if="errors.description"> 
              <span class="help is-danger">{{ errors.description }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Proof of Payment (jpg/jpeg/png)</label>
        </div>
        <div class="control is-grouped">
          <div class="column is-half">
            <input ref="fileUpload" type="file" name="fileDoc"
                   @change="changeFile($event.target.name, $event.target.files);"
                   accept="image/x-png,image/jpeg,application/pdf" multiple/>
            <div class="control is-grouped" v-if="errors.fileDoc"> 
              <span class="help is-danger">{{ errors.fileDoc }}</span> 
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <div class="control"> 
            <button class="button is-info" @click="showModal()">
              <span>Add</span>
            </button>
          </div>
          <div class="control">
            <button class="button is-warning" @click="cancel()">Cancel</button>
          </div>
        </div>
      </div>

      <div class="modal" transition="zoom">
        <div class="modal-background"></div>
        <div class="modal-content">
          <header class="modal-card-head">
            <p class="modal-card-title">Add New Confirmation</p>
            <button class="delete" @click="close"></button>
          </header>
          <section class="modal-card-body">
            <p style="font-size:16px">Are you sure to add new item? The item that has been added cannot be changed</p>
          </section>
          <footer class="modal-card-foot">
            <div class="column" style="padding:0">
              <div class="level-right">
                <button class="button is-danger" @click="confirm">Confirm</button>
                <button class="button" @click="close">Cancel</button>
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
  import Datepicker from 'vuejs-datepicker'
  import {BasicSelect} from 'vue-search-select'
  import Terbilang from 'terbilang-ts'

  export default {
    components: {
      Datepicker,
      BasicSelect
    },
    props: {
      id: {
        type: Number
      }
    },
    data () {
      return {
        errorSelect: true,
        clearButton: true,
        cmd: {
          reqNumber: '',
          reqDate: '',
          paidTo: null,
          reqAmount: null,
          amountInWord: '',
          category: '',
          description: '',
          needApproval: null,
          isApproved: null,
          reasonOfRejection: null,
          approvalDate: null,
          reqDateString: '',
          approvalDateString: '',
          paidToName: '',
          reqAmountSeparator: '',
          needApprovalStr: '',
          isApprovedStr: '',
          counter: null,
          fundingSourceId: null,
          listDocument: [],
          formUpload: null,
          createdBy: null
        },
        fundingList: [],
        errors: {},
        checkError: false,
        categoryList: [
          {value: null, text: '--- Choose Category ---'},
          {value: 'Transport', text: 'Transport'},
          {value: 'Non Transport', text: 'Non Transport'}
        ],
        employeeList: [],
        employeeItem: {
          value: '',
          text: ''
        },
        fundingItem: {
          value: '',
          text: ''
        },
        disabledDates: {
          from: new Date(Date.now())
        },
        roleName: '',
        paidToDisabled: false
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      showModal () {
        $('.modal').addClass('is-active')
      },
      confirm () {
        this.submit()
        $('.modal').removeClass('is-active')
      },
      close () {
        $('.modal').removeClass('is-active')
      },
      submit () {
        this.validateAll()
        bus.$emit('SHOW_LOADING')
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        this.cmd.createdBy = this.cmd.paidTo
        axios.post('/petty-cash/guest/add', this.cmd)
          .then((resp) => {
            if (this.cmd.formUpload != null) {
              axios.post('/petty-cash/upload/' + resp.data, this.cmd.formUpload)
                .then((resp) => {
                  bus.$emit('HIDE_LOADING')
                  console.log('SUCCESS')
                  console.log(resp)
                  bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
                  this.$router.go()
                })
                .catch((error) => {
                  this.onCloseModal()
                  bus.$emit('NOTIFY', 'danger', 'Gagal menyetujui data rekam medik.')
                  console.log('gagal', error.response)
                })
            } else {
              bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
              this.$router.go()
            }
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
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/funding-source/all'),
          axios.get('/employee/all')
        ]).then(axios.spread((fundingList, employeeList) => {
          this.fundingList.push({value: null, text: 'Please Select'})
          for (let funding of fundingList.data) {
            this.fundingList.push({value: funding.id, text: funding.name})
          }
          for (let employee of employeeList.data) {
            this.employeeList.push({value: employee.id, text: employee.name})
          }
        }))
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/petty-cash')
      },
      reset () {
        this.cmd.reqNumber = ''
        this.cmd.reqDate = ''
        this.cmd.paidTo = null
        this.cmd.reqAmount = null
        this.cmd.amountInWord = ''
        this.cmd.category = ''
        this.cmd.description = ''
        this.cmd.needApproval = null
        this.cmd.isApproved = null
        this.cmd.reasonOfRejection = null
        this.cmd.approvalDate = null
        this.cmd.reqDateString = ''
        this.cmd.approvalDateString = ''
        this.cmd.paidToName = ''
        this.cmd.reqAmountSeparator = ''
        this.cmd.needApprovalStr = ''
        this.cmd.isApprovedStr = ''
        this.cmd.counter = null
        this.cmd.fundingSourceId = null
        this.cmd.listDocument = []
        this.cmd.formUpload = null
        this.cmd.createdBy = null
      },
      validateAll () {
        console.log('validate form')
        if (this.errors.fileDoc == null) {
          this.errors = {}
        } else {
          this.errors = {}
          this.$set(this.errors, 'fileDoc', 'Must image files (jpg/jpeg/png)')
        }
//        if (this.cmd.formUpload !== null) {
//            this.$set(this.errors, 'fileDoc', 'The field cannot be empty')
//        }
        if (this.cmd.category === null || this.cmd.category === '') {
          this.$set(this.errors, 'category', 'The field cannot be empty')
        }
        if (this.cmd.description === null || this.cmd.description === '') {
          this.$set(this.errors, 'description', 'The field cannot be empty')
        }
        if (this.cmd.reqDate === null || this.cmd.reqDate === '') {
          this.$set(this.errors, 'reqDate', 'The field cannot be empty')
        }
        if (this.cmd.paidTo === null || this.cmd.paidTo === '') {
          this.$set(this.errors, 'paidTo', 'The field cannot be empty')
        }
        if (this.cmd.fundingSourceId === null || this.cmd.fundingSourceId === '') {
          this.$set(this.errors, 'fundingSourceId', 'The field cannot be empty')
        }
        if (this.cmd.reqAmountSeparator === null || this.cmd.reqAmountSeparator === '') {
          this.$set(this.errors, 'reqAmount', 'The field cannot be empty')
        }
        if (this.cmd.amountInWord === null || this.cmd.amountInWord === '') {
          this.$set(this.errors, 'amountInWord', 'The field cannot be empty')
        }
        console.log('list of error', this.errors)
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            this.checkError = true
          }
        }
      },
      selectedTanggalReqDate (event) {
        if (event !== null) {
          this.cmd.reqDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      selectedTanggalApprovalDate (event) {
        if (event !== null) {
          this.cmd.approvalDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedTanggalReqDate (event) {
        this.cmd.reqDateString = null
      },
      clearedTanggalApprovalDate (event) {
        this.cmd.approvalDateString = null
      },
      onBlurNumber () {
        this.cmd.reqAmountSeparator = this.cmd.reqAmountSeparator.replace(/\D/g, '')
        this.cmd.reqAmountSeparator = this.thousandSeparator(this.cmd.reqAmountSeparator)
      },
      thousandSeparator (amount) {
        this.cmd.reqAmount = this.cmd.reqAmountSeparator.replace(/\D/g, '')
        this.cmd.amountInWord = Terbilang(this.cmd.reqAmount)
        if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
          return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
        } else {
          return amount
        }
      },
      onEmployeeSelect (item) {
        this.errorSelect = false
        this.employeeItem = item
        this.cmd.paidTo = item.value
      },
      onFundingSelect (item) {
        this.errorSelect = false
        this.fundingItem = item
        this.cmd.fundingSourceId = item.value
      },
      changeFile (fieldName, files) {
        this.cmd.formUpload = new FormData()
        for (let setiap of files) {
          var extName = setiap.name.split('.').pop().toLowerCase()
          if (extName === 'png' || extName === 'jpg' || extName === 'jpeg') {
            this.cmd.formUpload.append(fieldName, setiap, setiap.name.toLowerCase())
            this.$set(this.errors, 'fileDoc', null)
          } else {
            this.$set(this.errors, 'fileDoc', 'Must image files (jpg/jpeg/png)')
          }
        }
      }
    }
  }
</script>
