<template>
  <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Delete Confirmation</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <div class="column">
          <div class="control" v-if="errors.userWebHasRole"> 
            <span class="help is-danger">{{ errors.userWebHasRole }}</span> 
          </div>
          <div class="control" v-if="errors.roleHasMenu"> 
            <span class="help is-danger">{{ errors.roleHasMenu }}</span> 
          </div>
        </div>
        <p style="font-size:16px">Are you sure want to delete <strong>{{this.deleteForm.label}}</strong>?</p>
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
        axios.post('/role/checkUserwebHasRole', this.deleteForm)
          .then((resp) => {
            this.userWebHasRole = resp.data
            this.$set(this.errors, 'userWebHasRole', null)
            if (resp.data > 0) {
              this.$set(this.errors, 'userWebHasRole', 'This role can not be deleted, this role still referenced in user role')
            }
            axios.post('/role/checkRoleHasMenu', this.deleteForm)
              .then((resp) => {
                this.roleHasMenu = resp.data
                this.$set(this.errors, 'roleHasMenu', null)
                if (resp.data > 0) {
                  this.$set(this.errors, 'roleHasMenu', 'This role can not be deleted, this role still referenced in role menu')
                }
                for (var field in this.errors) {
                  if (this.errors[field] !== null) {
                    bus.$emit('HIDE_LOADING')
                    return
                  }
                }
                if (this.userWebHasRole === 0 && this.roleHasMenu === 0) {
                  let req
                  req = axios.delete('/role/' + this.deleteForm.id)

                  req.then((resp) => {
                    this.$emit('close')
                    bus.$emit('NOTIFY', 'success', this.deleteForm.label + ' has been successfully deleted')
                    bus.$emit('RELOAD_ROLE_LIST')
                  })
                    .catch((error) => {
                      if (error.response.status === 422) {
                        console.log('error 422')
                        this.$emit('close')
                        bus.$emit('NOTIFY', 'danger', 'Data User ' + this.deleteForm.label + ' tidak  dapat  dihapus')
                        return
                      }
                      console.log('GAGAL ', error)
                      bus.$emit('NOTIFY', 'danger', 'Data Role gagal dihapus.')
                      this.$emit('close')
                    })
                }
              })
          })
      }
    }
  }
</script>
