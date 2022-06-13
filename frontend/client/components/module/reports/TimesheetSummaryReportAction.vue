<template>
  <div>

    <tooltip label="Detail Report" placement="top">
      <div class="is-clearfix">
        <button class="button is-info" @click="onClick(rowData)">
          <span class="icon is-small">
                <i class="fa fa-download"></i>
          </span>
          <span>Detail Timesheet</span>
        </button>
      </div>
    </tooltip>
  
  </div>
</template>

<script>
import Tooltip from 'vue-bulma-tooltip'
import axios from 'axios'

export default {
  components: {
    Tooltip
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
    onClick (data) {
      console.log(data)
      axios.post('/report/generateticket/timesheet-detail', data)
            .then((resp) => {
              let ticket = resp.data.ticket
              let root = window.location.protocol + '//' + window.location.host
              window.open(root + '/api/download/cetakreport/timesheet-detail/' +
                data.employeeName + '/' + data.employeeId + '/' +
                data.startDate + '/' + data.endDate + '/' + data.aj +
                '/' + '?ticket=' + ticket)
            })
            .catch((error) => {
              console.log('GAGAL : ', error)
            })
    }
  }
}
</script>
