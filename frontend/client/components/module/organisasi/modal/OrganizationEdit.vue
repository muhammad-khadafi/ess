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
              <input class="input" :class="{'is-danger' : errors.name}"
                     type="text" v-model="cmd.name"
                     placeholder="Organization Name"/>
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
              <input class="input" :class="{'is-danger' : errors.type}"
                     type="text" v-model="cmd.type"
                     placeholder="Organization Type" />
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
                <select  v-model="cmd.parentId" id="deptList">
                  <option v-bind:value="null">-- Choose Parent Organization --</option>
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
              <input class="checkbox" :class="{'is-danger' : errors.isActive}"
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
            <button class="button is-info" @click="submit()">
              <span>Save</span>
            </button>
          </div>
          <div class="control">
            <button class="button is-warning" @click="cancel()">Cancel</button>
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
  data () {
    return {
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
    reset () {
      this.cmd.id = null
      this.cmd.name = null
      this.cmd.type = null
      this.cmd.parentId = null
      this.cmd.isActive = true
    },
    fetchData () {
      this.reset()
      console.log('INSTITUSI FORM FETCH DATA : ', this.$route)
      console.log(this.$route.params)

      let thisVM = this
      axios.get('/organization-parent/' + this.$route.params.id)
        .then((resp) => {
          console.log('BERHASIL FETCH')
          thisVM.orgs = resp.data
          axios.get('/organization/' + this.$route.params.id)
            .then(function (resp) {
              thisVM.cmd = resp.data
            })
            .catch(function (error) {
              console.log('ERROR FETCH DATA', this.$route.params.id)
              console.log(error.response)
              thisVM.$emit('close')
              thisVM.bus.$emit('NOTIFY', 'danger', 'Tidak ada data')
            })
        })
        .catch((error) => {
          console.log('GAGAL FETCH')
          console.log(error.response)
          thisVM.$emit('close')
        })
    },
    cancel () {
      console.log('CANCEL')
      this.$router.replace('/organisasi')
    },
    submit () {
      this.errors = {}

      if (this.cmd.name === null || this.cmd.name === '') {
        this.$set(this.errors, 'name', 'Tidak boleh kosong')
      }

      if (this.cmd.type === null || this.cmd.type === '') {
        this.$set(this.errors, 'type', 'Tidak boleh kosong')
      }

      for (var field in this.errors) {
        if (this.errors[field] !== null) return
      }

      let thisVM = this

      let req
      bus.$emit('SHOW_LOADING')

      req = axios.put('/organization/' + this.$route.params.id, this.cmd)
      req.then(function (resp) {
        bus.$emit('HIDE_LOADING')
        console.log('SUCCES')
        console.log(resp)
        bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')
        thisVM.$router.replace('/organisasi')
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
          bus.$emit('NOTIFY', 'danger', 'Gagal menyimpan data')
        }
      })
    }
  }
}
</script>
