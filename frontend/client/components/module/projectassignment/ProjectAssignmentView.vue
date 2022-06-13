<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Employee Name</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="detail.employeeName"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Project Name</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="detail.projectName"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Role</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="detail.roleName"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Level</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="detail.levelName"/>
            </div>
          </div>
          <div class="control"> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Is Lead</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="detail.isLeadString"/>
            </div>
          </div>
          <div class="control"> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Load</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="detail.load"/>
            </div>
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
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="detail.startDateString"/>
            </div>
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
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="detail.endDateString"/>
            </div>
          </div>
          <div class="control"> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Is Active</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="detail.isActiveString"/>
            </div>
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
            <button class="button is-warning" @click="cancel()">Back</button>
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
          employeeName: '',
          projectame: ''
        }
      }
    },
    mounted () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('PROJECT ASSIGNMENT DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/project-assignment/view/' + this.$route.params.id)
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
        this.$router.replace('/project-assignment')
      }
    }
  }
</script>
