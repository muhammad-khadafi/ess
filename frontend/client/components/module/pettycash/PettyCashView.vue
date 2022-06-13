<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">View Petty Cash</p>
      <button class="delete" @click="close"></button>
    </div>
    <div class="modal-card-body">
      <div class="box">
        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Request Number</label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.reqNumber"/>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Category</label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.category"/>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Request Date</label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.reqDateString"/>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Paid To</label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.paidToName"/>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Funding Source</label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.fundingSourceName"/>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Request Amount</label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.reqAmountSeparator"/>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Request Amount In Word</label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.amountInWord"/>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Description</label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <textarea style="height:260px;" disabled class="input" type="text" v-model="detail.description"/>
            </div>
          </div>
        </div>

        <div v-if="detail.needApproval == true">
          <div class="control is-horizontal" v-if="detail.needApproval == true">
            <div class="control-label">
              <label class="label">Approval Status</label>
            </div>
            <div class="control is-grouped">
              <div class="column is-half">
                <input disabled class="input" type="text" v-model="detail.isApprovedStr"/>
              </div>
            </div>
          </div>

          <div class="control is-horizontal" v-if="detail.isApproved == false">
            <div class="control-label">
              <label class="label">Reason of Rejection</label>
            </div>
            <div class="control is-grouped">
              <div class="column is-half">
                <textarea style="height:260px;" disabled class="input" type="text" v-model="detail.reasonOfRejection"/>
              </div>
            </div>
          </div>

          <div class="control is-horizontal" v-if="detail.isApproved == true">
            <div class="control-label">
              <label class="label">Approval Date</label>
            </div>
            <div class="control is-grouped">
              <div class="column is-half">
                <input disabled class="input" type="text" v-model="detail.approvalDateString"/>
              </div>
            </div>
          </div>
        </div>
        <div class="control is-horizontal" v-if="detail.reasonOfCancellation != null">
          <div class="control-label">
            <label class="label">Reason Of Cancellation</label>
          </div>
          <div class="control is-grouped">
            <div class="column is-half">
              <textarea style="height:260px;" disabled class="input" type="text" v-model="detail.reasonOfCancellation"/>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label"></label>
          </div>
          <div class="control is-grouped">
            <div class="control" v-if="detail.isApproved === true || detail.needApproval === false">
              <button class="button is-info" @click="generate()">Generate</button>
            </div>
            <div class="control">
              <button class="button is-warning" @click="cancel()">Back</button>
            </div>
          </div>
        </div>

      </div>
  </div>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    props: {
      id: {
        type: Number
      }
    },
    data () {
      return {
        detail: {
          isDoneString: null
        }
      }
    },
    mounted () {
      this.fetchData()
    },
    methods: {
      close () {
        this.$emit('close')
      },
      fetchData () {
        console.log('PETTY CASH DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/petty-cash/view/' + thisVM.id)

          .then(function (resp) {
            console.log('BERHASIL FETCH', resp.data)
            thisVM.detail = resp.data
            if (thisVM.detail.isDone === true) {
              thisVM.detail.isDoneString = 'Yes'
            } else {
              thisVM.detail.isDoneString = '-'
            }
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
        this.$emit('close')
      },
      thousandSeparator (amount) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      },
      generate () {
        axios.post('/report/generateticket/pettycashreport', this.detail)
          .then((resp) => {
            var escapeSlash = this.detail.reqNumber.replace(/\//g, '-')
            console.log('BERHASIL : ', resp)
            let ticket = resp.data.ticket
            let root = window.location.protocol + '//' + window.location.host
            console.log('Ready to generate')
            if (this.detail.description === '') this.detail.description = null
            window.open(root + '/api/download/generatereport/pettycashreport/' +
              this.detail.reqDateString + '/' + escapeSlash + '/' + this.detail.category + '/' + this.detail.paidToName + '/' +
              this.detail.reqAmountSeparator + '/' + this.detail.amountInWord + '?ticket=' + ticket)
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
      }
    }
  }
</script>
