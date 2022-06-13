<template>
  <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Approve Confirmation</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <p style="font-size:16px">Are you sure want to approve this leave ?</p>
      </section>
      <footer class="modal-card-foot">
        <div class="column" style="padding:0">
          <div class="level-right">
            <a class="button is-success" @click="submit()">
              <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Approve</span>
            </a>
            <a class="button is-warning" @click="close()">Cancel</a>
          </div>
        </div>
      </footer>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import _ from 'lodash'
import bus from 'src/shared/bus'
import auth from 'src/shared/auth'

export default {
  props: {
    id: {
      type: Number
    }
  },
  data () {
    return {
      detail: {
        id: null,
        name: null,
        category: null,
        referenceType: null
      },
      auth: auth
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      console.log('APPROVED FETCH DATA, id : ' + this.id)
      this.detail.id = this.id
    },
    close () {
      this.$emit('close')
    },
    submit () {
      bus.$emit('SHOW_LOADING')

      this.errors = {}

      let thisVM = this

      let req = axios.put('/leave-approval/' + this.detail.id + '/APPROVE/' + this.auth.getUserInfo().label)

      req.then(function (resp) {
        bus.$emit('HIDE_LOADING')
        console.log('SUCCES')
        console.log(resp)
        thisVM.$emit('close')
        bus.$emit('NOTIFY', 'success', 'Leave has been successfully approved')
        location.reload()
        // this.$router.replace('/leave-approval')
      })
        .catch(function (error) {
          bus.$emit('HIDE_LOADING')
          console.log('ERROR')
          console.log(error.response)

          _.each(error.response.data.errors, function (value) {
            thisVM.$set(thisVM.errors, value.field, value.message)
          })

          console.log('SELESAI')
          if (thisVM.errors.exception) {
            bus.$emit('NOTIFY', 'danger', 'Failed to approve leave')

            thisVM.$emit('close')
          }
        })
    }
  }
}
</script>
