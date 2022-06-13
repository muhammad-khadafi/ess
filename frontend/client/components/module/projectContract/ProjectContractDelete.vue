<template>
  <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">Delete Confirmation</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <p style="font-size:16px">Are you sure want to delete contract <strong>{{detail.projectName}}</strong>?</p>
      </section>
      <footer class="modal-card-foot">
        <div class="column" style="padding:0">
          <div class="level-right">
            <a class="button is-danger" @click="submit()">
              <span class="icon is-small">
                  <i class="fa fa-trash"></i>
                </span>
              <span>Delete</span>
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
        projectName: null,
        contractNumber: null,
        contractDateString: null,
        signee1: null,
        signee2: null
      }
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      console.log('PROJECT CONTRACT DETAIL FETCH DATA')

      this.detail.id = this.id

      let thisVM = this
      axios.get('/projectContract/' + this.detail.id)
      .then(function (resp) {
        console.log('BERHASIL FETCH')
        thisVM.detail = resp.data
      })
      .catch(function (error) {
        console.log('GAGAL FETCH')
        console.log(error.response)
        thisVM.$emit('close')
      })

      this.loading = true
    },
    close () {
      this.$emit('close')
    },
    submit () {
      bus.$emit('SHOW_LOADING')

      this.errors = {}

      let thisVM = this

      let req = axios.delete('/projectContract/' + this.detail.id)

      req.then(function (resp) {
        bus.$emit('HIDE_LOADING')
        console.log('SUCCES')
        console.log(resp)
        bus.$emit('NOTIFY', 'success', 'Data has been successfully deleted')

        thisVM.$emit('close')
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
            bus.$emit('NOTIFY', 'danger', 'Failed to delete data')

            thisVM.$emit('close')
          }
        })
    }
  }
}
</script>
