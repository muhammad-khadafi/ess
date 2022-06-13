import Vue from 'vue'
import axios from 'axios'
import jwtDecode from 'jwt-decode'
import CryptoJS from 'crypto-js'
import router from 'src/shared/router'
import bus from 'src/shared/bus'

// URL and endpoint constants
const LOGIN_URL = '/auth/login'
const WHITELIST_MENU = ['LOGIN', 'FORBIDDEN', 'HOME', 'CHANGE_PASSWORD']

const KEY_USERINFO = 'userInfo'
const KEY_IDROLE = 'id_role'
const KEY_LISTMENU = 'listMenu'

// export var auth = new Vue({
let auth = new Vue({
  data: {
    isAuthenticated: false,
    username: '',
    role: ''
  },
  methods: {
    toggle () {
      // this.isAuthenticated = !this.isAuthenticated
      this.username = Math.random().toString(36).substring(7)
      console.warn('>>> USERNAME', this.username)
    },
    login (context, creds, redirect) {
      axios.post(LOGIN_URL, creds)
        .then((resp) => {
          console.log('LOGIN')
          console.log(resp)

          localStorage.setItem('id_token', resp.data.idToken)
          localStorage.setItem('id_role', resp.data.roleId)
          this.setEncryptedLocalStorage(KEY_USERINFO, resp.data.userInfo)
          this.setEncryptedLocalStorage(KEY_LISTMENU, resp.data.listMenu)
          this.role = resp.data.roleId
          console.log('cek role ' + this.role)
          console.log('cek role ' + resp.data.listMenu)

          console.log('SETELAH SET LOCALSTORAGE')
          console.log('bus : ', bus)
          console.log('router : ', router)
          this.isAuthenticated = true
          bus.$emit('AUTH_CHANGED', this)

          console.log('SETELAH SET AUTHENTICATED')

          // Redirect to a specified route
          if (redirect) {
            console.log('MASUK REDIRECT')
            router.replace(redirect)
          }

          console.log('GAK MASUK REDIRECT')
        })
        .catch(function (error) {
          context.error = error
          bus.$emit('NOTIFY', 'danger', 'Login failed, please contact your administrator')
        })
    },
    logout () {
      localStorage.removeItem('id_token')
      localStorage.removeItem(KEY_USERINFO)
      localStorage.removeItem(KEY_IDROLE)
      this.isAuthenticated = false
      this.role = ''
      // bus.$emit('AUTH_CHANGED', this)
    },
    checkAuth () {
      console.log('$$ AUTH.checkAuth')
      let jwt = localStorage.getItem('id_token')
      let keyinfo = localStorage.getItem(KEY_USERINFO)
      let roleId = localStorage.getItem(KEY_IDROLE)
      if (jwt && keyinfo && roleId) {
        this.isAuthenticated = true
        this.role = roleId
      } else {
        this.isAuthenticated = false
      }
      // bus.$emit('AUTH_CHANGED', this)
    },
    getJwt () {
      let token = localStorage.getItem('id_token')
      return jwtDecode(token)
    },

    getSignKey () {
      let token = localStorage.getItem('id_token')
      if (token === null) return undefined
      return token.split('.')[2]
    },

    encrypt (data, pass) {
      return CryptoJS.AES.encrypt(JSON.stringify(data), pass)
    },

    decrypt (encrypted, pass) {
      if (pass === undefined) return undefined
      let decrypted = CryptoJS.AES.decrypt(encrypted.toString(), pass)
      return JSON.parse(decrypted.toString(CryptoJS.enc.Utf8))
    },

    // The object to be passed as a header for authenticated requests
    getAuthHeader () {
      let token = localStorage.getItem('id_token')
      console.log('$$ AUTH.getAuthHeder : ', token)
      return 'Bearer ' + token
    },

    setEncryptedLocalStorage (key, value) {
      localStorage.setItem(key, this.encrypt(value, this.getSignKey()))
    },

    getEncryptedLocalStorage (key) {
      let encrypted = localStorage.getItem(key)
      return this.decrypt(encrypted, this.getSignKey())
    },

    getUserInfo () {
      return this.getEncryptedLocalStorage(KEY_USERINFO)
    },

    hasAccess (menu) {
      console.log('cek isi to menu : ', menu)
      return this.getEncryptedLocalStorage(KEY_LISTMENU).includes(menu) || WHITELIST_MENU.includes(menu)
      // return true
    }

    // toggle () {
    //   this.isUser = !this.isUser
    //   console.log('INI DARI AUTH, why : ', bus.$emit('NOTIFY', 'danger', 'Tidak ada data'))
    //   router.replace('/grid-inline-editing')
    // }
  }
})

export default auth
