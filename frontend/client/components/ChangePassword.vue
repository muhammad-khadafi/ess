<template>
      <div class="card-content">
        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Old Password<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input column is-half" :class="{'is-danger' : errors.password}"
                     type="password" v-model="cmd.password"
                     placeholder="Old Password"/>
            </div>
            <div class="control" v-if="errors.password"> 
              <span class="help is-danger">{{ errors.password }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">New Password<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input column is-half" :class="{'is-danger' : errors.newPassword}"
                     type="password" v-model="cmd.newPassword"
                     placeholder="New Password"/>
            </div>
            <div class="control" v-if="errors.newPassword"> 
              <span class="help is-danger">{{ errors.newPassword }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label"> Repeat New Password<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input column is-half" :class="{'is-danger' : errors.newPasswordRepeat}"
                     type="password" v-model="cmd.newPasswordRepeat"
                     placeholder="Repeat New Password"/>
            </div>
            <div class="control" v-if="errors.newPasswordRepeat"> 
              <span class="help is-danger">{{ errors.newPasswordRepeat }}</span> 
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
                <span>Change Password</span>
              </button>
            </div>
            <div class="control">
              <button class="button is-warning" @click="cancel()">Cancel</button>
            </div>
          </div>
        </div>
      </div>
    <!--<button @click="toggle()">Toggle</button>-->
</template>

<script>
import auth from 'src/shared/auth'
import axios from 'axios'
import _ from 'lodash'
import bus from 'src/shared/bus'
import { mapRouter } from 'src/shared/router'

export default {
  data () {
    return {
      cmd: {
        id: null,
        username: null,
        password: null,
        newPassword: null,
        newPasswordRepeat: null
      },
      errors: {},
      auth: auth
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    this.cmd.username = auth.getUserInfo().username
    this.cmd.id = auth.getUserInfo().id
    console.log('CHANGE PASSWORD FORM CREATED')
  },
  methods: {
    toggle () {
      auth.toggle()
      console.log('MAP ROUTER : ', mapRouter)
//      router.replace('/login')
    },
    submit () {
      this.errors = {}

      if (this.cmd.password === null || this.cmd.password === '') {
        this.$set(this.errors, 'password', 'Tidak boleh kosong')
      }

      if (this.cmd.newPassword === null || this.cmd.newPassword === '') {
        this.$set(this.errors, 'newPassword', 'Tidak boleh kosong')
      }

      if (this.cmd.newPasswordRepeat === null || this.cmd.newPasswordRepeat === '') {
        this.$set(this.errors, 'newPasswordRepeat', 'Tidak boleh kosong')
      }

      if (this.cmd.newPassword !== null && this.cmd.newPasswordRepeat !== null && this.cmd.newPassword !== this.cmd.newPasswordRepeat) {
        this.$set(this.errors, 'newPassword', 'Isi Harus Sama')
        this.$set(this.errors, 'newPasswordRepeat', 'Isi Harus Sama')
      }

      for (var field in this.errors) {
        if (this.errors[field] !== null) return
      }

      let thisVM = this

      let req
      bus.$emit('SHOW_LOADING')

      req = axios.put('/user/change-password', this.cmd)
      req.then(function (resp) {
        bus.$emit('HIDE_LOADING')
        console.log('SUCCES')
        console.log(resp)
        bus.$emit('NOTIFY', 'success', 'Password berhasil diganti')
        thisVM.$router.replace('/home')
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
          bus.$emit('NOTIFY', 'danger', 'Password gagal diganti')
        }
      })
    }
  }
}
</script>