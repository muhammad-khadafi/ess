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
            </div>
            <div class="control">
              <button class="button is-warning" @click="back()">Kembali</button>
            </div>
          </div>
        </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios'

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
  beforeCreate () {
    console.log('INSTITUSI DETAIL BEFORE CREATE')
  },
  created () {
    console.log('INSTITUSI DETAIL CREATED')
    this.fetchData()
  },
  beforeMount () {
    console.log('INSTITUSI DETAIL BEFORE MOUNT')
  },
  mounted () {
    console.log('INSTITUSI DETAIL MOUNTED')
  },
  beforeUpdate () {
    console.log('INSTITUSI DETAIL BEFORE UPDATE')
  },
  updated () {
    console.log('INSTITUSI DETAIL UPDATED')
  },
  beforeDestroy () {
    console.log('INSTITUSI DETAIL BEFORE DESTROY')
  },
  destroyed () {
    console.log('INSTITUSI DETAIL DESTROYED')
  },
  methods: {
    fetchData () {
      console.log('INSTITUSI DETAIL FETCH DATA')
      console.log(this.$route.params)

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
    back () {
      this.$emit('close')
    }
  }
}
</script>
