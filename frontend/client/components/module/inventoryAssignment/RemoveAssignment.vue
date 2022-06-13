<template>
  <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Remove Assignment Confirmation</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <p style="font-size:16px">Are you sure to remove <strong>{{this.deleteForm.employeeName +'  '}}</strong>from assignment<strong>{{ ' '+ this.deleteForm.inventoryName}}</strong>?</p>
      </section>
      <footer class="modal-card-foot">
        <div class="column" style="padding:0">
          <div class="level-right">
            <a class="button is-primary" @click="submit()">  remove  </a>
            <a class="button" @click="close()">Cancel</a>
          </div>
        </div>
      </footer>
    </div>
  </div>
</template>
<script>
import { Modal } from 'vue-bulma-modal'
import axios from 'axios'
import bus from 'src/shared/bus'

export default {
  components: {
    Modal
  },
  props: {
    deleteForm: Object
  },
  data () {
    return {
      errors: {}
    }
  },
  methods: {
    close () {
      this.$emit('close')
    },
    submit () {
      axios.put('/inventory-assignment/remove-assignment/' + this.deleteForm.id)
      .then((resp) => {
        this.$emit('close')
        bus.$emit('NOTIFY', 'success', 'Success to Remove Assignment for ' + this.deleteForm.employeeName + ' - ' + this.deleteForm.inventoryName)
        bus.$emit('RELOAD_INVENTORY_ASSIGN_LIST')
      })
      .catch((error) => {
        if (error.response.status === 422) {
          console.log('error 422')
          this.$emit('close')
          bus.$emit('NOTIFY', 'danger', 'Failed to Remove Assignment for ' + this.deleteForm.employeeName + ' - ' + this.deleteForm.inventoryName + ' cannot be deleted')
          return
        }
        console.log('GAGAL ', error)
        bus.$emit('NOTIFY', 'danger', 'Failed to remove inventory assignment.')
        this.$emit('close')
      })
    }
  }
}

</script>
