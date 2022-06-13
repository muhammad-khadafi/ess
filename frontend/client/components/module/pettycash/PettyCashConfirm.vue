<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Confirmation Petty Cash</p>
      <button class="delete" @click="close"></button>
    </div>
    <div class="modal-card-body">
      <p style="font-size:16px">Confirm that you have given the money to {{detail.paidToName}}?</p>
    </div>
    <footer class="modal-card-foot">
      <div class="column" style="padding:0">
        <div class="level-right">
          <button class="button is-danger" @click="confirm">Confirm</button>
          <button class="button" @click="close">Cancel</button>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
  import axios from 'axios'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import auth from 'src/shared/auth'

  export default {
    props: {
      id: {
        type: Number
      }
    },
    data () {
      return {
        detail: {
          id: '',
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
          isApprovedStr: '',
          fundingSourceName: '',
          isDone: null,
          paidBy: null
        },
        isApprovedList: [
          {value: null, text: '--- Choose Status ---'},
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
            thisVM.detail.processedBy = auth.getUserInfo().id
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
      cancel () {
        this.$router.replace('/petty-cash')
      },
      thousandSeparator (amount) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      },
      generate () {
        axios.post('/report/generateticket/pettycashreport', this.detail)
          .then((resp) => {
            console.log('BERHASIL : ', resp)
            let ticket = resp.data.ticket
            let root = window.location.protocol + '//' + window.location.host
            console.log('Ready to generate')
            if (this.detail.description === '') this.detail.description = null
            window.open(root + '/api/download/generatereport/pettycashreport/' +
              this.detail.reqDateString + '/' + this.detail.reqNumber + '/' + this.detail.category + '/' + this.detail.paidToName + '/' +
              this.detail.reqAmountSeparator + '/' + this.detail.amountInWord + '/' + this.detail.description + '/' + this.detail.fundingSourceName + '?ticket=' + ticket)
//            window.open(root + '/api/download2/sample2/cetakreport2/' +
//              this.form.pusilkomRole + '?ticket=' + ticket)
          })
          .catch((error) => {
            console.log('GAGAL : ', error)
          })
      },
      downloadDocument (item) {
        axios.post('/petty-cash/file/' + item)
          .then((resp) => {
            let ticket = resp.data.ticket
            let root = window.location.protocol + '//' + window.location.host
            window.location = root + '/api/download/petty-cash/' + item + '?ticket=' + ticket
          })
      },
      confirmFinal () {
        this.detail.paidBy = auth.getUserInfo().id
        axios.put('/petty-cash/confirmfinal/' + this.detail.id, this.detail)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/petty-cash')
            this.$emit('close')
            bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
            bus.$emit('RELOAD_PETTY_CASH_LIST')
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
      showModal () {
        $('.modal').addClass('is-active')
      },
      confirm () {
        this.confirmFinal()
        $('.modal').removeClass('is-active')
      },
      close () {
        this.$emit('close')
      }
    }
  }
</script>
