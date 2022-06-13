<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Organization Name</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.organizationString"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Funding Type</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.fundingType"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Client</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.client"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Start Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.startDateString"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">End Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.endDateString"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Status</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.statusString"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Name</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.name"/>
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
  import _ from 'lodash'
  import bus from 'src/shared/bus'
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
          organizationId: null,
          organizationString: null,
          fundingType: null,
          client: null,
          startDateString: null,
          endDateString: null,
          name: null,
          statusString: null
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
        this.cmd.organizationId = null
        this.cmd.organizationString = null
        this.cmd.fundingType = null
        this.cmd.client = null
        this.cmd.startDateString = null
        this.cmd.endDateString = null
        this.cmd.name = null
        this.cmd.statusString = null
      },
      fetchData () {
        console.log('PROJECT DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/project/' + this.$route.params.id)
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
          this.$router.replace('/project')
        } else {
          this.$emit('close')
        }
      },
      submit () {
        bus.$emit('SHOW_LOADING')

        let thisVM = this

        let req
        req = axios.put('/project/' + this.$route.params.id, this.cmd)
        req.then(function (resp) {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCES')
          console.log(resp)
          bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')

          console.log('$$$ isNEW', thisVM.isNew)

          thisVM.$router.replace('/project')
        })
          .catch(function (error) {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log(error.response)

            _.each(error.response.data.errors, function (value) {
              thisVM.$set(thisVM.errors, value.field, value.message)
            })

            console.log('SELESAI')
            if (thisVM.errors.exception) {
              bus.$emit('NOTIFY', 'danger', 'Gagal update data')
            }
          })
      }
    }
  }
</script>
