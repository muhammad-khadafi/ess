<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Employee Name</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.employeeName"/>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Position</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" type="text" v-model="cmd.structureName"/>
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
          <label class="label">Start Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="checkbox" type="checkbox" v-model="cmd.active"/>
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
            <input disabled class="input" type="text" v-model="cmd.notes"/>
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
            <button class="button is-warning" @click="cancel()">Kembali</button>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  // import _ from 'lodash'
  // import bus from 'src/shared/bus'
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
          employeeName: null,
          structureName: null,
          startDateString: null,
          endDateString: null,
          active: null,
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
        axios.get('/employee-position/' + this.$route.params.id)
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
          this.$router.replace('/employeePosition')
        } else {
          this.$emit('close')
        }
      }
    }
  }
</script>
