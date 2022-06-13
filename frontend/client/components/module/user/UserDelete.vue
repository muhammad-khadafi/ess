<template>
  <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Delete Confirmation</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <p style="font-size:16px">Are you sure want to delete <strong>{{this.deleteForm.username}}</strong>?</p>
      </section>
      <footer class="modal-card-foot">
        <div class="column" style="padding:0">
          <div class="level-right">
            <a class="button is-primary" @click="submit()">  Delete  </a>
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
        axios.post('/user/checkUserwebHasRole', this.deleteForm)
          .then((resp) => {
            if (resp.data === 0) {
              let req
              req = axios.delete('/user/' + this.deleteForm.id)

              req.then((resp) => {
                this.$emit('close')
                bus.$emit('NOTIFY', 'success', this.deleteForm.username + ' has been successfully deleted.')
                bus.$emit('RELOAD_USER_LIST')
              })
                .catch((error) => {
                  if (error.response.status === 422) {
                    console.log('error 422')
                    this.$emit('close')
                    bus.$emit('NOTIFY', 'danger', 'Data User ' + this.deleteForm.username + ' tidak  dapat  dihapus')
                    return
                  }
                  console.log('GAGAL ', error)
                  bus.$emit('NOTIFY', 'danger', 'Data User gagal dihapus.')
                  this.$emit('close')
                })
            } else {
              bus.$emit('HIDE_LOADING')
              bus.$emit('NOTIFY', 'danger', 'This user can not be deleted, this user still referenced in user role')
              this.$emit('close')
            }
          })
      }
    }
  }
</script>
