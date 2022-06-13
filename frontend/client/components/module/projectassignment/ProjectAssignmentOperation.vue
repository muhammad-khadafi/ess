<template>
  <div>
    <modal :visible="visible" @open="onOpen" @close="onClose">
      <component :is="contentComponent" :id="rowData.id" :isActive="rowData.isActiveString" @close="onClose"></component>
    </modal>
    <tooltip label="View" placement="top" v-if="rowData.id !== -1">
      <div class="is-clearfix">
        <button class="button is-warning" @click="onClick('view-item', rowData)"><i class="fa fa-eye"/></button>
      </div>
    </tooltip>
    <tooltip label="Update" placement="top">
      <div class="is-clearfix">
        <button class="button is-info" @click="onClick('edit-item', rowData)"><i class="fa fa-pencil"/></button>
      </div>
    </tooltip>
    <tooltip label="Deactivate" placement="top" v-if="rowData.isActiveString === 'Yes'">
      <div class="is-clearfix">
        <button class="button is-danger" @click="onClick('delete-item', rowData)"><i class="fa fa-user-times"/></button>
      </div>
    </tooltip>
    <tooltip label="Activate" placement="top" v-if="rowData.isActiveString === 'No'">
      <div class="is-clearfix">
        <button class="button is-success" @click="onClick('delete-item', rowData)"><i class="fa fa-user-o"/></button>
      </div>
    </tooltip>
  </div>
</template>

<script>
import Tooltip from 'vue-bulma-tooltip'
import { Modal } from 'vue-bulma-modal'
import ProjectAssignmentForm from './ProjectAssignmentForm.vue'
import ProjectAssignmentDelete from './ProjectAssignmentDelete.vue'
import bus from 'src/shared/bus'

export default {
  components: {
    Tooltip,
    Modal,
    ProjectAssignmentForm,
    ProjectAssignmentDelete
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
      if (action === 'edit-item' && data.id !== -1) {
        this.$router.replace('/project-assignment/edit/' + data.id)
      } else if (action === 'edit-item' && data.id === -1) {
        this.$router.replace('/project-assignment/add/' + data.employeeId + '/' + data.employeeName + '/' + data.months + '/' + data.years)
      } else if (action === 'view-item') {
        this.$router.replace('/project-assignment/view/' + data.id)
      } else if (action === 'delete-item') {
        this.contentComponent = 'project-assignment-delete'
        this.onOpen()
      }
    }
  }
}
</script>
