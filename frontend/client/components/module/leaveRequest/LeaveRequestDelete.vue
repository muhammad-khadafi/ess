<template>
  <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Delete Confirmation</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <p style="font-size:16px">Are you sure want to delete <strong>{{this.deleteForm.leaveTypeIdString}}</strong> on date <strong>{{this.deleteForm.startDateString}}</strong> to <strong>{{this.deleteForm.endDateString}}</strong> ? </p>
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
        userWebHasRole: 0,
        roleHasMenu: 0,
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
        axios.post('/leave-request/delete', this.deleteForm)
        .then((resp) => {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCESS')
          console.log(resp)
          bus.$emit('NOTIFY', 'success', 'Data has been successfully canceled')
          this.$emit('close')
        })
        .catch((error) => {
          bus.$emit('HIDE_LOADING')
          console.log('ERROR : ', error)
          console.log('isi dari error', error.response)
        })
      }
    }
  }
</script>
