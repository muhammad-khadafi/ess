<template>
  <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">{{activeSentence}} Confirmation</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <p style="font-size:16px">Are you sure to {{activeSentence}} this employee's project assignment?</p>
      </section>
      <footer class="modal-card-foot">
        <div class="column" style="padding:0">
          <div class="level-right">
            <a class="button is-info" @click="deleteData()">
              <span class="icon is-small">
                  <i class="fa fa-user  "></i>
                </span>
              <span>{{activeSentence}}</span>
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
    },
    isActive: {
      type: String
    }
  },
  data () {
    return {
      detail: {
        id: null
      },
      activeSentence: ''
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      if (this.isActive === 'Yes') {
        this.activeSentence = 'Deactivate'
      } else {
        this.activeSentence = 'Activate'
      }
      console.log('PROJECT ASSIGNMENT DETAIL FETCH DATA')
      this.detail.id = this.id
      let thisVM = this
      axios.get('/project-assignment/view/' + this.detail.id)
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
    deleteData () {
      bus.$emit('SHOW_LOADING')

      this.errors = {}

      let thisVM = this

      let req = axios.delete('/project-assignment/' + this.detail.id)

      req.then(function (resp) {
        bus.$emit('HIDE_LOADING')
        console.log('SUCCES')
        console.log(resp)
        bus.$emit('NOTIFY', 'success', 'Data has been successfully changed')
        thisVM.$emit('close')
      })
        .catch((error) => {
          bus.$emit('HIDE_LOADING')
          console.log('ERROR')
          console.log(error)
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
