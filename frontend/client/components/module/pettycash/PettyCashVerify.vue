<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Verify Petty Cash</p>
      <button class="delete" @click="close"></button>
    </div>
    <div class="modal-card-body">
      <div class="box">
        <div class="control is-horizontal" v-if="detail.needApproval == true">
          <div class="control-label">
            <label class="label">Approval<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <span class="select">
                <select v-model="detail.isApproved" :class="{'control is-danger' : errors.isApproved}">
                  <option v-for="option in isApprovedList" :value="option.value">
                    {{ option.text }}
                  </option>
                </select>
              </span>
              <div class="control is-grouped" v-if="errors.isApproved"> 
                <span class="help is-danger">{{ errors.isApproved }}</span> 
              </div>
            </div>
          </div>
        </div>

        <div class="control is-horizontal" v-if="detail.isApproved == false">
          <div class="control-label">
            <label class="label">Reason of Rejection <font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <textarea style="height:260px;" class="input" type="text" v-model="detail.reasonOfRejection"/>
              <div class="control is-grouped" v-if="errors.reasonOfRejection"> 
                <span class="help is-danger">{{ errors.reasonOfRejection }}</span> 
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
                <span>Verify</span>
              </button>
            </div>
            <div class="control">
              <button class="button is-warning" @click="close()">Back</button>
            </div>
          </div>
        </div>

        <div class="modal" transition="zoom">
          <div class="modal-background"></div>
          <div class="modal-content">
            <header class="modal-card-head">
              <p class="modal-card-title">Verify Confirmation</p>
              <button class="delete" @click="close"></button>
            </header>
            <section class="modal-card-body">
              <p style="font-size:16px">Are you sure about your decision?</p>
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
  </div>
</template>

<script>
  import axios from 'axios'
  import bus from 'src/shared/bus'
  import _ from 'lodash'
  import auth from 'src/shared/auth'

  export default {
    props: {
      id: {
        type: Number
      }
    },
    data () {
      return {
        errors: {},
        detail: {
          id: null,
          reqNumber: '',
          reqDate: null,
          paidTo: null,
          reqAmount: null,
          amountInWord: '',
          category: '',
          description: '',
          needApproval: null,
          isApproved: null,
          reasonOfRejection: '',
          approvalDate: null,
          reqDateString: '',
          approvalDateString: '',
          paidToName: '',
          reqAmountSeparator: '',
          needApprovalStr: '',
          isApprovedStr: ''
        },
        isApprovedList: [
          {value: null, text: '--- Choose Decision ---'},
          {value: true, text: 'Approve'},
          {value: false, text: 'Reject'}
        ]
      }
    },
    mounted () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('PETTY CASH DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/petty-cash/view/' + this.id)
          .then(function (resp) {
            console.log('BERHASIL FETCH', resp.data)
            thisVM.detail = resp.data
            thisVM.detail.approvedBy = auth.getUserInfo().id
            thisVM.detail.reqAmountSeparator = thisVM.thousandSeparator(thisVM.detail.reqAmount)
            if (thisVM.detail.needApproval == null) {
              thisVM.detail.needApprovalStr = '-'
            } else if (thisVM.detail.needApproval === true) {
              thisVM.detail.needApprovalStr = 'Yes'
            } else {
              thisVM.detail.needApprovalStr = 'No'
            }

            if (thisVM.detail.isApproved == null) {
              thisVM.detail.isApprovedStr = 'Waiting for Approval'
            } else if (thisVM.detail.isApproved === true) {
              thisVM.detail.isApprovedStr = 'Approved'
            } else {
              thisVM.detail.isApprovedStr = 'Rejected'
            }
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
        this.loading = true
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
        axios.put('/petty-cash/' + this.id, this.detail)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/petty-cash')
            this.$emit('close')
            bus.$emit('RELOAD_PETTY_CASH_LIST')
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
      cancel () {
        this.$router.replace('/petty-cash')
      },
      thousandSeparator (amount) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      },
      validateAll () {
        this.errors = {}
        if (this.detail.isApproved === null || this.detail.isApproved === '') {
          this.$set(this.errors, 'isApproved', 'The field cannot be empty')
        }
        if (this.detail.isApproved === false) {
          if (this.detail.reasonOfRejection === null || this.detail.reasonOfRejection === '') {
            this.$set(this.errors, 'reasonOfRejection', 'The field cannot be empty')
          }
        }
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            this.checkError = true
          }
        }
      },
      showModal () {
        $('.modal').addClass('is-active')
      },
      confirm () {
        this.submit()
        this.$emit('close')
        bus.$emit('RELOAD_PETTY_CASH_LIST')
      },
      close () {
        this.$emit('close')
      },
      downloadDocument (item) {
        axios.post('/petty-cash/file/' + item)
          .then((resp) => {
            let ticket = resp.data.ticket
            let root = window.location.protocol + '//' + window.location.host
            window.location = root + '/api/download/petty-cash/' + item + '?ticket=' + ticket
          })
      }
    }
  }
</script>
