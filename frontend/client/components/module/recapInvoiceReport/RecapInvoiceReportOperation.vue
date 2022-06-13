<template>
  <div>
    <modal :visible="visible" @open="onOpen" @close="onClose">
      <component :is="contentComponent" :id="rowData.id" @close="onClose"></component>
    </modal>

    <tooltip label="View" placement="top">
      <div class="is-clearfix">
        <button class="button is-warning" @click="onClick('view-item', rowData)"><i class="fa fa-download"/></button>
      </div>
    </tooltip>
    <tooltip label="Delete" placement="top">
      <div class="is-clearfix">
        <button class="button is-danger" @click="onClick('delete-item', rowData)"><i class="fa fa-trash"/></button>
      </div>
    </tooltip>
  </div>
</template>

<script>
import Tooltip from 'vue-bulma-tooltip'
import { Modal } from 'vue-bulma-modal'
import RecapInvoiceReportDelete from './RecapInvoiceReportDelete.vue'
import bus from 'src/shared/bus'
import axios from 'axios'

export default {
  components: {
    Tooltip,
    Modal,
    RecapInvoiceReportDelete
  },
  data () {
    return {
      visible: false,
      contentComponent: ''
    }
  },
  props: {
    rowData: {
      type: Object,
      required: true
    }
  },
  methods: {
    onOpen () {
      this.visible = true
    },
    onClose () {
      this.visible = false
      bus.$emit('RELOAD_INSTITUSI_LIST')
    },
    onClick (action, data) {
      console.log('actions: on-click', action, data.name)
      if (action === 'edit-item') {
        axios.get('/log-activity-report/get-report/' + data.id)
          .then(function (resp) {
            console.log('BERHASIL FETCH')
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
          })
      } else if (action === 'view-item') {
        // axios.post('/report/generateticket/downloadreportlogactivity/' + data.id)
        //   .then((resp) => {
        //     console.log('BERHASIL : ', resp)
        //     let ticket = resp.data.ticket
        //     let root = window.location.protocol + '//' + window.location.host
        //     console.log('Ready to generate')
        //     window.open(root + '/api/log-activity-report/get-report/' + data.id +
        //       '?ticket=' + ticket)
        //   })
        //   .catch((error) => {
        //     console.log('GAGAL : ', error)
        //   })
        // axios.get('/log-activity-report/get-report/' + data.id)
        //   .then(function (resp) {
        //     console.log('BERHASIL FETCH')
        //   })
        //   .catch(function (error) {
        //     console.log('GAGAL FETCH')
        //     console.log(error.response)
        //   })
        let root = window.location.protocol + '//' + window.location.host
        console.log('Ready to generate')
        window.open(root + '/api/download/recap-invoice-report/get-report/' + data.id + '/' + data.name)
        // axios.get('/download/log-activity-report/get-report/poc/' + data.id)
        //   .then(function (resp) {
        //     console.log('BERHASIL FETCH')
        //   })
        //   .catch(function (error) {
        //     console.log('GAGAL FETCH')
        //     console.log(error.response)
        //   })
      } else if (action === 'delete-item') {
        this.contentComponent = 'recap-invoice-report-delete'
        this.onOpen()
      }
    }
  }
}
</script>
