<template>
 <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Delete Confirmation</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <p style="font-size:16px">Are you sure want to delete <strong>{{this.deleteForm.subProjectName}}</strong>?</p>
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
    props: {
      deleteForm: Object
    },
    components: {
      Modal
    },
    data () {
      return {
        errors: {}
      }
    },
    mounted () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('SUB PROJECT DETAIL FETCH DATA')
      },
      close () {
        this.$emit('close')
      },
      submit () {
        let req
        req = axios.delete('/sub-project-by-id/' + this.deleteForm.id)

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
      }
    }
  }
</script>
