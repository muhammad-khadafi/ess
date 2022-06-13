<template>
  <div>
    <modal :visible="visible" @open="onOpen" @close="onClose">
      <component :is="contentComponent" @close="onClose"
      :id="rowData.id" 
      :employeeName="rowData.employeeName"
      :leaveTypeName="rowData.leaveTypeName"
      :startDateStr="rowData.startDateStr"
      :endDateStr="rowData.endDateStr"
      :status="rowData.status"
      :leaveDateDetail="rowData.leaveDateDetail"
      :managerName="rowData.managerName"
      :deputyDirectorName="rowData.deputyDirectorName"
      :numOfDays="rowData.numOfDays"
      :reason="rowData.reason"></component>
    </modal>

    <tooltip label="View" placement="top">
      <div class="is-clearfix">
        <button class="button is-warning" @click="onClick('view-item', rowData)"><i class="fa fa-eye"/></button>
      </div>
    </tooltip>
    <tooltip label="Approve" placement="top">
      <div class="is-clearfix">
        <button class="button is-success" :disabled="!buttonState" @click="onClick('approve-item', rowData)"><i class="fa fa-check"/></button>
      </div>
    </tooltip>
    <tooltip label="Reject" placement="top">
      <div class="is-clearfix">
        <button class="button is-danger" :disabled="!buttonState" @click="onClick('reject-item', rowData)"><i class="fa fa-times"/></button>
      </div>
    </tooltip>
  </div>
</template>

<script>
import Tooltip from 'vue-bulma-tooltip'
import { Modal } from 'vue-bulma-modal'
import LeaveApprovalReject from './LeaveApprovalReject.vue'
import LeaveApprovalApprove from './LeaveApprovalApprove.vue'
import LeaveApprovalView from './LeaveApprovalView.vue'
import bus from 'src/shared/bus'
import auth from 'src/shared/auth'

export default {
  components: {
    Tooltip,
    Modal,
    LeaveApprovalReject,
    LeaveApprovalApprove,
    LeaveApprovalView
  },
  mounted () {
    if (this.auth.getUserInfo().label === 'Manager') {
      if (this.rowData.status === 'Waiting Manager Approval') {
        this.buttonState = true
      } else {
        this.buttonState = false
      }
    } else if (this.auth.getUserInfo().label === 'Deputy Director') {
      if (this.rowData.status === 'Waiting Deputy Director Approval') {
        this.buttonState = true
      } else {
        this.buttonState = false
      }
    }
    console.log('DEBUG!!', this.rowData)
  },
  data () {
    return {
      visible: false,
      contentComponent: '',
      buttonState: false,
      auth: auth
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
      console.log('actions: on-click', action, data)
      if (action === 'view-item') {
        this.contentComponent = 'leave-approval-view'
        this.onOpen()
      } else if (action === 'approve-item') {
        this.contentComponent = 'leave-approval-approve'
        this.onOpen()
      } else if (action === 'reject-item') {
        this.contentComponent = 'leave-approval-reject'
        this.onOpen()
      }
    }
  }
}
</script>
