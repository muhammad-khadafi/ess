<template>
  <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Delete Confirmation</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <p style="font-size:16px">Are you sure want to delete <strong>{{this.deleteForm.label}}</strong>?</p>
      </section>
      <footer class="modal-card-foot">
        <div class="column" style="padding:0">
          <div class="level-right">
            <a class="button is-primary" @click="submit()"><i class="fa fa-trash"></i>&nbsp;Delete</a>
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
    data () {
      return {
        errors: {}
      }
    },

    props: {
      deleteForm: Object
    },

    methods: {
      close () {
        this.$emit('close')
      },

      submit () {
        let req
        req = axios.delete('/menu/' + this.deleteForm.id)

        req.then((resp) => {
          this.$emit('close')
          bus.$emit('NOTIFY', 'success', this.deleteForm.label + ' has been successfully deleted')
          bus.$emit('RELOAD_MENU_LIST')
        })
          .catch((error) => {
            console.log('ERROR MESSAGE 1 ' + error.response.data.message)
            if (error.response.message === 'Tidak bisa delete') {
              bus.$emit('NOTIFY', 'danger', 'Data menu tidak bisa dihapus karena masih memiliki child')
            }

            if (error.response.status === 422) {
              console.log('error 422')
              this.$emit('close')
              bus.$emit('NOTIFY', 'danger', 'Data menu ' + this.deleteForm.label + ' tidak  dapat  dihapus karena masih memiliki child')
              return
            }

            console.log('GAGAL ', error)
            bus.$emit('NOTIFY', 'danger', 'Data menu gagal dihapus.')
            this.$emit('close')
          })
      }
    }
  }
</script>
