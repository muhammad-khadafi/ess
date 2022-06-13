<template>
  <div>
    <modal :visible="visible" @open="onOpen" @close="onClose">
      <component :is="contentComponent" :id="rowData.id" @close="onClose"></component>
    </modal>

    <tooltip label="View" placement="top">
      <div class="is-clearfix">
        <button class="button is-warning" @click="onClick('view-item', rowData)"><i class="fa fa-eye"/></button>
      </div>
    </tooltip>
    <tooltip label="Update" placement="top">
      <div class="is-clearfix">
        <button class="button is-info" @click="onClick('edit-item', rowData)"><i class="fa fa-pencil"/></button>
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
import LevelRateForm from './LevelRateForm.vue'
import LevelRateDelete from './LevelRateDelete.vue'
import bus from 'src/shared/bus'

export default {
  components: {
    Tooltip,
    Modal,
    LevelRateForm,
    LevelRateDelete
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
      bus.$emit('RELOAD_LEVEL_RATE_LIST')
    },
    onClick (action, data) {
      console.log('actions: on-click', action, data.name)
      if (action === 'edit-item') {
        this.$router.replace('/level-rate/edit/' + data.id)
      } else if (action === 'view-item') {
        this.$router.replace('/level-rate/view/' + data.id)
      } else if (action === 'delete-item') {
        this.contentComponent = 'level-rate-delete'
        this.onOpen()
      }
    }
  }
}
</script>
