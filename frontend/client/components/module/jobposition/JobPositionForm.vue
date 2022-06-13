<template>
  <div>
    <div class="box">

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Position Name</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <div class="column is-one-third">
              <input class="input" :class="{'is-danger' : errors.name}"
                     type="text" v-model="cmd.name" placeholder="Position Name"/>
              </div>
            </div>
            <div class="control" v-if="errors.name"> 
              <span class="help is-danger">{{ errors.name }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Position Level</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <div class="column is-one-third">
              <span class="select">
                <select v-model="cmd.level" :class="{'is-danger' : errors.level}">
                <option v-for="option in listLevel" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
              </span>
              </div>
            </div>
            <div class="control" v-if="errors.level">
              <span class="help is-danger">{{ errors.level }}</span>
            </div>
          </div>
        </div>
        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Active ?</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <div class="column is-one-third">
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
  props: {
    id: {
      type: Number
    }
  },
  data () {
    return {
      cmd: {
        id: null,
        name: null,
        level: null,
        isActive: true
      },
      errors: {}
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('POSITION FORM CREATED')
    this.fetchData()
  },
  created () {
    this.listLevel = [
      { value: null, text: '--Choose Level--' },
      { value: 1, text: '1' },
      { value: 2, text: '2' },
      { value: 3, text: '3' },
      { value: 4, text: '4' },
      { value: 5, text: '5' }
    ]
  },
  methods: {
    reset () {
      this.cmd.id = null
      this.cmd.name = null
      this.cmd.level = null
      this.cmd.isActive = true
    },
    fetchData () {
      this.reset()
    },
    cancel () {
      console.log('CANCEL')
      this.$router.replace('/job-position')
    },
    submit () {
      bus.$emit('SHOW_LOADING')

      this.errors = {}

      if (!this.cmd.name) {
        this.$set(this.errors, 'name', 'The field cannot be empty')
      }
      if (!this.cmd.level) {
        this.$set(this.errors, 'level', 'The field cannot be empty')
      }

      console.log('testes', this.cmd.isActive)

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }

      let req
      req = axios.post('/job-position', this.cmd)

      let thisVM = this
      req.then(function (resp) {
        bus.$emit('HIDE_LOADING')
        console.log('SUCCESS')
        console.log(resp)
        bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')
        thisVM.$router.replace('/job-position')
      })
      .catch(function (error) {
        bus.$emit('HIDE_LOADING')
        console.log('ERROR : ', error)
        console.log(error.response)

        _.each(error.response.data.errors, function (value) {
          this.$set(this.errors, value.field, value.message)
        })

        console.log('SELESAI')
        if (this.errors.exception) {
          bus.$emit('NOTIFY', 'danger', 'Gagal menyimpan data')

          this.$router.replace('/job-position')
        }
      })
    }
  }
}
</script>
