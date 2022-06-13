<template>
  <div>
    <div class="box">

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Menu Code<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input column is-half" :class="{'is-danger' : errors.kode}"
                     type="text" v-model="cmd.kode"
                     placeholder="Menu Code"/>
            </div>
            <div class="control" v-if="errors.kode"> 
              <span class="help is-danger">{{ errors.kode }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label ">
            <label class="label">Menu Name<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input column is-half" :class="{'is-danger' : errors.label}"
                     type="text" v-model="cmd.label"
                     placeholder="Menu Name"/>
            </div>
            <div class="control" v-if="errors.label"> 
              <span class="help is-danger">{{ errors.label }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Menu Parent</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <span class="select">
                <select  v-model="cmd.idParent" id="deptList">
                  <option v-bind:value="null">-- Please Select --</option>
                  <option v-for="menu in menus" v-bind:value="menu.id">
                      {{ menu.kode + ' - ' + menu.label }}
                  </option>
                </select> 
              </span>
            </div>
            <div class="control" v-if="errors.idParent"> 
              <span class="help is-danger">{{ errors.idParent }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Menu Order<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-half">
              <input class="input" type="number" v-model="cmd.menuOrder"/>
            </div>
            <div class="control" v-if="errors.menuOrder"> 
              <span class="help is-danger">{{ errors.menuOrder }}</span> 
            </div>
          </div>
        </div>
        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Is Displayed ?</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="checkbox" :class="{'is-danger' : errors.isDisplayed}"
                     type="checkbox" v-model="cmd.isDisplayed"/>
            </div>
            <div class="control" v-if="errors.isDisplayed"> 
              <span class="help is-danger">{{ errors.isDisplayed }}</span> 
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
                <span>Add</span>
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
      isNew: true,
      cmd: {
        id: null,
        kode: null,
        label: null,
        isDisplayed: null,
        menuOrder: null,
        idParent: null
      },
      menus: [],
      errors: {}
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('INSTITUSI FORM CREATED')
    this.fetchData()
  },
  methods: {
    reset () {
      this.cmd.id = null
      this.cmd.kode = null
      this.cmd.label = null
      this.cmd.idParent = null
      this.cmd.menuOrder = null
      this.cmd.isDisplayed = true
    },
    fetchData () {
      this.reset()
      console.log('INSTITUSI FORM FETCH DATA : ', this.$route)
      console.log(this.$route.params)

      this.cmd.id = this.id
      this.isNew = _.isNil(this.cmd.id)

      console.log(this.isNew)
      let thisVM = this
      thisVM.getMenu()
    },
    cancel () {
      console.log('CANCEL')
      this.$router.replace('/menu')
    },
    submit () {
      this.errors = {}

      if (this.cmd.kode === null || this.cmd.kode === '') {
        this.$set(this.errors, 'kode', 'Tidak boleh kosong')
      }

      if (this.cmd.label === null || this.cmd.label === '') {
        this.$set(this.errors, 'label', 'Tidak boleh kosong')
      }

      console.log('menu order ' + this.cmd.menuOrder)

      if (this.cmd.menuOrder === null || this.cmd.menuOrder === '') {
        this.$set(this.errors, 'menuOrder', 'Tidak boleh kosong')
      }

      for (var field in this.errors) {
        if (this.errors[field] !== null) return
      }

      let thisVM = this

      let req
      bus.$emit('SHOW_LOADING')

      req = axios.post('/menu', this.cmd)
      req.then(function (resp) {
        bus.$emit('HIDE_LOADING')
        console.log('SUCCES')
        console.log(resp)
        bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
        thisVM.$router.replace('/menu')
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
    },
    getMenu () {
      console.log("I'm a little teapot")  // this appears in the log
      let thisVM = this
      axios.get('/menu/getAll')
      .then((resp) => {
        console.log('BERHASIL FETCH')
        thisVM.menus = resp.data
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
