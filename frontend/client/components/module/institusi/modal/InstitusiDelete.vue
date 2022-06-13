<template>
  <div>
    <div class="box">

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Kode Institusi</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input disabled class="input" type="text" v-model="detail.kodeInstitusi" placeholder="Kode Institusi"/>
            </div>
            <div class="control"> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Nama</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input disabled class="input" type="text" v-model="detail.nama" placeholder="Nama"/>
            </div>
            <div class="control"> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Nama Singkat</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input disabled class="input" type="text" v-model="detail.namaSingkat" placeholder="Kode Institusi"/>
            </div>
            <div class="control"> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Aktif ?</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <label>{{ detail.flagAktif ? 'Ya' : 'Tidak' }}</label>
            </div>
            <div class="control"> 
            </div>
          </div>
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
        kodeInstitusi: null,
        nama: null,
        namaSingkat: null,
        flagAktif: true
      }
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      console.log('INSTITUSI DETAIL FETCH DATA')

      this.detail.id = this.id

      let thisVM = this
      axios.get('/institusi/' + this.detail.id)
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
    cancel () {
      this.$emit('close')
    },
    deleteData () {
      bus.$emit('SHOW_LOADING')

      this.errors = {}

      let thisVM = this

      let req = axios.delete('/institusi/' + this.detail.id)

      req.then(function (resp) {
        thisVM.bus.$emit('HIDE_LOADING')
        console.log('SUCCES')
        console.log(resp)
        thisVM.bus.$emit('NOTIFY', 'success', 'Data has been successfully deleted')

        thisVM.$emit('close')
      })
      .catch(function (error) {
        thisVM.bus.$emit('HIDE_LOADING')
        console.log('ERROR')
        console.log(error.response)

        _.each(error.response.data.errors, function (value) {
          thisVM.$set(thisVM.errors, value.field, value.message)
        })

        console.log('SELESAI')
        if (thisVM.errors.exception) {
          thisVM.bus.$emit('NOTIFY', 'danger', 'Failed to delete data')

          thisVM.$emit('close')
        }
      })
    }
  }
}
</script>
