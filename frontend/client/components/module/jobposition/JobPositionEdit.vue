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
            <div class="column is-3">
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
              <span>Update</span>
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
          level: null,
          isActive: true,
          createdDate: null,
          createdBy: null,
          updatedDate: null,
          updatedBy: null
        },
        errors: {}
      }
    },
    created () {
      this.fetchData()
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
      fetchData () {
        console.log('POSITION FORM FETCH DATA : ', this.$route)
        console.log(this.$route.params)

        this.cmd.id = this.$route.params.id

        let thisVM = this
        axios.get('/job-position/' + this.$route.params.id)
          .then(function (resp) {
            thisVM.cmd = resp.data
          })
          .catch(function (error) {
            console.log('ERROR FETCH DATA', thisVM.cmd.id)
            console.log(error.response)
            bus.$emit('NOTIFY', 'danger', 'Tidak ada data')
          })
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

        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        let thisVm = this
        let req
        req = axios.put('/job-position/' + this.cmd.id, this.cmd)
        req.then(function (resp) {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCES')
          console.log(resp)
          bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')

          thisVm.$router.replace('/job-position')
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
