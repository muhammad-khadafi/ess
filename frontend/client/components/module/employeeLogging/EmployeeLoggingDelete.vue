<template>
  <div>
    <div class="box">

        <div class="control is-horizontal">
          <label class="label">Apakah anda yakin untuk menghapus data tersebut ?</label>
        </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <div class="control"> 
            <button class="button is-info" @click="deleteData()">
                <span class="icon is-small">
                  <i class="fa fa-trash"></i>
                </span>
              <span>Hapus</span>
            </button>
          </div>
          <div class="control">
            <button class="button is-warning" @click="cancel()">Batal</button>
          </div>
        </div>
      </div>

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
        employeeName: null
      }
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      console.log('EMPLOYEE POSITION FETCH DATA')

      this.detail.id = this.id

      // let thisVM = this
      // axios.get('/log-act-detail/' + this.detail.id)
      // .then(function (resp) {
      //   console.log('BERHASIL FETCH')
      //   thisVM.detail = resp.data
      // })
      // .catch(function (error) {
      //   console.log('GAGAL FETCH')
      //   console.log(error.response)
      //   thisVM.$emit('close')
      // })

      this.loading = true
    },
    cancel () {
      this.$emit('close')
    },
    deleteData () {
      bus.$emit('SHOW_LOADING')

      this.errors = {}

      let thisVM = this

      let req = axios.delete('/log-act-detail/' + this.detail.id)

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
