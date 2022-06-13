<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Project Name</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.projectName"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Contract Number</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.contractNumber"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Contract Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.contractDateString"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Signee 1</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.signee1"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Signee 2</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.signee2"/>
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
            <button class="button is-warning" @click="cancel()">Kembali</button>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import Datepicker from 'vuejs-datepicker'

  export default {
    props: {
      id: {
        type: Number
      }
    },
    components: {
      Datepicker
    },
    data () {
      return {
        isNew: true,
        cmd: {
          id: null,
          projectName: null,
          contractNumber: null,
          contractDateString: null,
          signee1: null,
          signee2: null
        },
        errors: {}
      }
    },
    created () {

    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      this.fetchData()
    },
    methods: {
      reset () {
        this.cmd.id = null
        this.cmd.projectName = null
        this.cmd.contractNumber = null
        this.cmd.contractDateString = null
        this.cmd.signee1 = null
        this.cmd.signee2 = null
      },
      fetchData () {
        console.log('PROJECT DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/projectContract/' + this.$route.params.id)
          .then(function (resp) {
            console.log('BERHASIL FETCH')
            thisVM.cmd = resp.data
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
        this.loading = true
      },
      cancel () {
        console.log('CANCEL')
        if (this.isNew) {
          this.$router.replace('/projectContract')
        } else {
          this.$emit('close')
        }
      }
    }
  }
</script>
