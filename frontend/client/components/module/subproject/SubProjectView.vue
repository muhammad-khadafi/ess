<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Project</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.projectName"/>
            </div>
          </div>
          <div class="control"> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Sub Project Name</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.subProjectName"/>
            </div>
          </div>
          <div class="control"> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Sub Project Code</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.code"/>
            </div>
          </div>
          <div class="control"> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Description</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <textarea disabled class="textarea" type="text" v-model="cmd.description"/>
            </div>
          </div>
          <div class="control"> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Supervisor</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.spvName"/>
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
    components: {
    },
    data () {
      return {
        isNew: true,
        cmd: {
          projectName: null,
          subProjectName: null,
          code: null,
          description: null,
          spvName: null
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
        this.cmd.projectName = null
        this.cmd.subProjectName = null
        this.cmd.code = null
        this.cmd.description = null
        this.cmd.spvName = null
      },
      fetchData () {
        console.log('PROJECT DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/sub-project-by-id/' + this.$route.params.id)
          .then(function (resp) {
            console.log('BERHASIL FETCH')
            console.log(resp.data)
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
          this.$router.replace('/sub-project')
        } else {
          this.$emit('close')
        }
      }
    }
  }
</script>
