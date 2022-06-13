<template>
  <div>
    <div class="box">

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Position Name</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <div class="column is-3">
                <input disabled class="input" type="text" v-model="detail.name" placeholder="Position Name"/>
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
              <div class="column is-3">
              <input disabled class="input" type="text" v-model="detail.level" placeholder="Level"/>
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
              <label>{{ detail.isActive ? 'Yes' : 'No' }}</label>
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
            </div>
            <div class="control">
              <button class="button is-warning" @click="back()">Back</button>
            </div>
          </div>
        </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      detail: {
        id: null,
        name: null,
        level: null,
        isActive: null
      }
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      console.log('JOB POSITION DETAIL FETCH DATA')
      console.log(this.$route.params)

      this.detail.id = this.$route.params.id

      let thisVM = this
      axios.get('/job-position/' + this.$route.params.id)
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
      this.$router.replace('/job-position')
    }
  }
}
</script>
