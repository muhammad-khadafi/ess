<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Organization Structure Name</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-3">
            <input disabled class="input" type="text" v-model="cmd.name"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Organization</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-3">
            <input disabled class="input" type="text" v-model="cmd.organizationName"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Job Position</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-3">
            <input disabled class="input" type="text" v-model="cmd.jobPositionName"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Parent Organization</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-3">
            <input disabled class="input" type="text" v-model="cmd.parentName"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Active ?</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-3">
            <input disabled class="checkbox" type="checkbox" v-model="cmd.isActive"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Notes</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-3">
            <input disabled class="input" type="text" v-model="cmd.notes"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>
      <!--{{cmd}}-->

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <div class="control">
            <button class="button is-warning" @click="cancel()">Back</button>
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
          name: null,
          parentId: null,
          organizationId: null,
          positionId: null,
          isActive: false,
          parentName: false,
          organizationName: false,
          jobPositionName: false,
          notes: null
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
        this.cmd.name = null
        this.cmd.parentId = null
        this.cmd.organizationId = null
        this.cmd.positionId = null
        this.cmd.isActive = null
        this.cmd.parentName = null
        this.cmd.organizationName = null
        this.cmd.jobPositionName = null
        this.cmd.notes = null
      },
      fetchData () {
        console.log('PROJECT DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/organization-structure/' + this.$route.params.id)
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
          this.$router.replace('/organization-structure')
        } else {
          this.$emit('close')
        }
      },
      submit () {
        bus.$emit('SHOW_LOADING')

        let thisVM = this

        let req
        req = axios.put('/organization-structure/' + this.$route.params.id, this.cmd)
        req.then(function (resp) {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCES')
          console.log(resp)
          bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')

          console.log('$$$ isNEW', thisVM.isNew)

          thisVM.$router.replace('/organization-structure')
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
