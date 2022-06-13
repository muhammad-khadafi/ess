<template>
  <div>
    <div class="box">

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Organization Name</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <div class="column is-3">
              <input disabled class="input" :class="{'is-danger' : errors.name}"
                     type="text" v-model="cmd.name"
                     placeholder="Nama Organisasi"/>
              </div>
            </div>
            <div class="control" v-if="errors.name"> 
              <span class="help is-danger">{{ errors.name }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Organization Type</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <div class="column is-3">
              <input disabled class="input" :class="{'is-danger' : errors.type}"
                     type="text" v-model="cmd.type"
                     placeholder="Tipe Organisasi" />
              </div>
            </div>
            <div class="control" v-if="errors.type"> 
              <span class="help is-danger">{{ errors.type }}</span> 
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
              <span class="select">
                <select  disabled v-model="cmd.parentId" id="deptList">
                  <option v-bind:value="null">-- Tidak Ada Organisasi Induk --</option>
                  <option v-for="org in orgs" v-bind:value="org.id">
                      {{org.name}}
                  </option>
                </select>
              </span>
              </div>
            </div>
            <div class="control" v-if="errors.parentId"> 
              <span class="help is-danger">{{ errors.parentId }}</span> 
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
              <input disabled class="checkbox" :class="{'is-danger' : errors.isActive}"
                     type="checkbox" v-model="cmd.isActive"/>
              </div>
            </div>
            <div class="control" v-if="errors.isActive"> 
              <span class="help is-danger">{{ errors.isActive }}</span> 
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
        name: null,
        type: null,
        parentId: null,
        isActive: true
      },
      orgs: [],
      errors: {}
    }
  },
  created () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('ORGANIZATION FORM CREATED')
    this.fetchData()
  },
  methods: {
    fetchData () {
      console.log('INSTITUSI FORM FETCH DATA : ', this.$route)
      console.log(this.$route.params)

      this.cmd.id = this.id
      this.isNew = _.isNil(this.cmd.id)

      console.log(this.isNew)

      let thisVM = this
      this.getOrgs()
      axios.get('/organization/' + this.$route.params.id)
      .then(function (resp) {
        thisVM.cmd = resp.data
      })
      .catch(function (error) {
        console.log('ERROR FETCH DATA', thisVM.id)
        console.log(error.response)
        thisVM.$emit('close')
        bus.$emit('NOTIFY', 'danger', 'Tidak ada data')
      })
    },
    cancel () {
      console.log('CANCEL')
      this.$router.replace('/organisasi')
    },
    getOrgs () {
      console.log("I'm a little teapot")  // this appears in the log
      let thisVM = this
      axios.get('/organization-parent/' + this.$route.params.id)
      .then((resp) => {
        console.log('BERHASIL FETCH')
        thisVM.orgs = resp.data
      })
      .catch((error) => {
        console.log('GAGAL FETCH')
        console.log(error.response)
        thisVM.$emit('close')
      })
    }
  }
}
</script>
