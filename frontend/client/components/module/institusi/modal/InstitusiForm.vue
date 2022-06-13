<template>
  <div>
    <div class="box">

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Kode Institusi</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input" :class="{'is-danger' : errors.kodeInstitusi}"
                     type="text" v-model="cmd.kodeInstitusi" placeholder="Kode Institusi"/>
            </div>
            <div class="control" v-if="errors.kodeInstitusi"> 
              <span class="help is-danger">{{ errors.kodeInstitusi }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Nama</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input" :class="{'is-danger' : errors.nama}"
                     type="text" v-model="cmd.nama" placeholder="Nama"/>
            </div>
            <div class="control" v-if="errors.nama"> 
              <span class="help is-danger">{{ errors.nama }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Nama Singkat</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input" :class="{'is-danger' : errors.namaSingkat}"
                     type="text" v-model="cmd.namaSingkat" placeholder="Kode Institusi"/>
            </div>
            <div class="control" v-if="errors.namaSingkat"> 
              <span class="help is-danger">{{ errors.namaSingkat }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Aktif ?</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="checkbox" :class="{'is-danger' : errors.flagAktif}"
                     type="checkbox" v-model="cmd.flagAktif"/>
            </div>
            <div class="control" v-if="errors.flagAktif"> 
              <span class="help is-danger">{{ errors.flagAktif }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label"></label>
          </div>
          <div class="control is-grouped">
            <div class="control"> 
              <button class="button is-info" @click="submit()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
                <span>Simpan</span>
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
      isNew: true,
      cmd: {
        id: null,
        kodeInstitusi: null,
        nama: null,
        namaSingkat: null,
        flagAktif: true
      },
      errors: {}
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('INSTITUSI FORM CREATED')
    this.fetchData()
  },
  methods: {
    reset () {
      this.cmd.id = null
      this.cmd.kodeInstitusi = null
      this.cmd.nama = null
      this.cmd.namaSingkat = null
      this.cmd.flagAktif = true
    },
    fetchData () {
      this.reset()
      console.log('INSTITUSI FORM FETCH DATA : ', this.$route)
      console.log(this.$route.params)

      this.cmd.id = this.id
      this.isNew = _.isNil(this.cmd.id)

      console.log(this.isNew)

      let thisVM = this
      if (!this.isNew) {
        axios.get('/institusi/' + this.cmd.id)
        .then(function (resp) {
          thisVM.cmd = resp.data
        })
        .catch(function (error) {
          console.log('ERROR FETCH DATA', thisVM.id)
          console.log(error.response)
          thisVM.$emit('close')
          thisVM.bus.$emit('NOTIFY', 'danger', 'Tidak ada data')
        })
      }
    },
    cancel () {
      console.log('CANCEL')
      if (this.isNew) {
        this.$router.replace('/institusi')
      } else {
        this.$emit('close')
      }
    },
    submit () {
      bus.$emit('SHOW_LOADING')

      this.errors = {}

      let thisVM = this

      let req
      if (this.isNew) {
        req = axios.post('/institusi', this.cmd)
      } else {
        req = axios.put('/institusi/' + this.cmd.id, this.cmd)
      }

      req.then(function (resp) {
        thisVM.bus.$emit('HIDE_LOADING')
        console.log('SUCCES')
        console.log(resp)
        thisVM.bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')

        console.log('$$$ isNEW', thisVM.isNew)

        if (thisVM.isNew) {
          this.$router.replace('/institusi')
        } else {
          console.log('$$$ MASUK isNEW', thisVM.isNew)
          thisVM.$emit('close')
        }
      })
      .catch(function (error) {
        thisVM.bus.$emit('HIDE_LOADING')
        console.log('ERROR : ', error)
        console.log(error.response)

        _.each(error.response.data.errors, function (value) {
          thisVM.$set(thisVM.errors, value.field, value.message)
        })

        console.log('SELESAI')
        if (thisVM.errors.exception) {
          thisVM.bus.$emit('NOTIFY', 'danger', 'Gagal menyimpan data')

          if (thisVM.isNew) {
            this.$router.replace('/institusi')
          } else {
            thisVM.$emit('close')
          }
        }
      })
    }
  }
}
</script>
