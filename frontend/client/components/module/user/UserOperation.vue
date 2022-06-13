<template>
  <div>
    {{rowData}}
    <modal :visible="visible" @open="onOpen" @close="onClose">
      <component :is="contentComponent" :id="rowData.id" @close="onClose"></component>
    </modal>
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
  import UserDelete from './UserDelete.vue'
  import bus from 'src/shared/bus'

  export default {
    components: {
      Tooltip,
      Modal,
      UserDelete
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
        bus.$emit('RELOAD_USER_LIST')
      },
      onClick (action, data) {
        console.log('actions: on-click', action, data.username)
        if (action === 'edit-item') {
          this.$router.replace('/user/edit/' + data.id)
        } else if (action === 'delete-item') {
          this.contentComponent = 'user-delete'
          this.onOpen()
        }
      }
    }
  }
</script>
