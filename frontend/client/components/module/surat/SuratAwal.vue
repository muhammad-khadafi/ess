<template>
  <div>
    <div class="box">
<!--          {{form}}-->
<!--          {{idlist}}-->
<!--      {{roleList[0]}}-->
<!--      {{form.information}}-->
      <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Category<font color="red">*</font></label>
<!--            <font color="red">*</font>-->
          </div>
        </div>
        <div class="column is-5">
        <span class="select">
          <select v-model="form.category" :class="{'is-danger' : errors.category}">
            <option v-for="option in listCategory" :value="option.value">
              {{ option.text }}
            </option>
          </select>
        </span>
          <div class="control is-grouped" v-if="errors.category">
            <span class="help is-danger">{{ errors.category }}</span>
          </div>
        </div>
      </div>


      <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">  <center>Created</center>Date<font color="red">*</font></label>

          </div>
        </div>
        <div class="column is-3">
          <datepicker v-model="form.createdDate" input-class="input" format="dd-MM-yyyy"
                      placeholder="Created Date"
                      @selected="selectedTanggalStartDate($event)"
                      @cleared="clearedTanggalStartDate($event)"
                      :clear-button="clearButton"></datepicker>
          <div class="control is-grouped" v-if="errors.createdDate">
            <span class="help is-danger">{{ errors.createdDate }}</span>
          </div>
        </div>
      </div>


      <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Description<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
          <textarea class="textarea" :class="{'is-danger' : errors.description}"
                    type="text" v-model="form.description" placeholder="Input Description "/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.description">
            <span class="help is-danger">{{ errors.description }}</span>
          </div>
        </div>
      </div>

      <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Send To<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
          <textarea class="textarea" :class="{'is-danger' : errors.purpose}"
                    type="text" v-model="form.purpose" placeholder="Input Send To"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.purpose">
            <span class="help is-danger">{{ errors.purpose }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Assigner<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-quarter">
          <div class="control is-grouped level-left">
            <span class="select">
            <select v-model="form.information" :class="{'is-danger' : errors.information}">
              <option v-for="option in roleList" :value="option.value">
                {{ option.text }}
              </option>
            </select>
          </span>
<!--            <basic-select :options="roleList"-->
<!--                          :selected-option="roleItem"-->
<!--                          placeholder="Disetujui Oleh"-->
<!--                          @select="onRoleSelect"-->
<!--                          :class="{'is-danger' : errors.roleId}">-->
<!--            </basic-select>-->
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.information">
            <span class="help is-danger">{{ errors.information }}</span>
          </div>
        </div>
      </div>
<!--

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
          </div>
        </div>


        <div class="column is-half">
          <table class="vuetable ui blue selectable celled stackable attached table">
            <th>nomor</th>
            <th>Menu</th>
            <tr v-for="(role, index) in roleHasMenuList">
              <td>{{ index + 1  }}</td>
              <td>{{ role.menuName }}</td>
              <td><a href="" @click.prevent="deleteRole(role)">Delete</a></td>
            </tr>
          </table>
        </div>
      </div>
-->

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
            <button class="button is-warning" @click="cancel()">Back</button>
          </div>
        </div>
      </div>
      <!--{{roaleHasMenuList}}-->
      <!--{{cmd}}-->
    </div>
  </div>
</template>

<script>
  import auth from 'src/shared/auth'
  import axios from 'axios'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import ValidationMixin from '../../mixin/ValidationMixin'
 // import auth from './auth'

  export default {
    components: {
      BasicSelect,
      Datepicker
    },
    mixins: [ValidationMixin],
    data () {
      return {
        errorSelect: true,
        cmd: {
          roleId: null,
          menuName: null,
          menuId: null
        },
        form: {
          id: null,
          category: null,
          createdDate: new Date(),
          description: null,
          purpose: null,
          information: null,
          subProId: null,
          idUser: auth.getUserInfo().id,
//          idUser: auth.getUserInfo().employeeId,
          serialNumber: null
        },
        tempDate: new Date(),
        listCategory: [
          { value: null, text: '--- Choose Category ---' },
          { value: 'U', text: 'Umum' },
          { value: 'C', text: 'Kontrak' },
          { value: 'P', text: 'Penawaran' },
          { value: 'B', text: 'Billing' }
        ],
        roleHasMenuList: [],
        roleList: [
          { value: null, text: '--- Choose Assigner ---' }
        ],
        idlist: [],
        menuList: [],
        roleItem: {
          value: '',
          text: ''
        },
        menuItem: {
          value: '',
          text: ''
        },
        errors: {},
        clearButton: true
      }
    },
    created () {
      this.fetchData()
      this.setTodayDate()
    },
    methods: {
      fetchData () {
        // console.log('START FETCH')
        // this.reset()
        // axios.all([
        //   axios.get('/letter-dropdown')
        // ]).then(axios.spread((roleList) => {
        //   this.roleList.push({value: null, text: 'Please Select'})
        //   for (let level of roleList.data) {
        //     this.roleList.push({value: level.id, text: level.name})
        //   }
       //  })
       //  var s = '1000'
       //  var x = '' + Math.floor(Math.random() * 100)
       //  this.form.serialNumber = s.substr(0, (s.length - (x.length)) + 1) + x + 'PIK_UI' + this.form.category
        // this.form.idUser = auth.getUserInfo().employeeId
       //  var s = '1000'
       //  var x = '' + this.form.idUser
       //  this.form.serialNumber = s.substr(0, (s.length - (x.length)) + 1) + x

        axios.get('/letter-dropdown').then((resp) => {
          for (var i = 0; i < resp.data.length; i++) {
            this.roleList.push({value: resp.data[i].category, text: resp.data[i].category})
          }
        })

        axios.get('/letter-id').then((resp) => {
          for (var i = 0; i < resp.data.length; i++) {
            this.idlist.push({value: resp.data[i].id, text: resp.data[i].id})
          }
        })
      },
      onRoleSelect (item) {
        // axios.get('/letter-dropdown').then((resp) => {
        //   for (var i = 0; i < resp.data.data.length; i++) {
        //     this.roleItem.push({value: resp.data[i].category, text: resp.data[i].category})
        //   }
        // })
        this.errorSelect = false
        this.roleItem = item
        this.form.information = item.value
      },
      selectedTanggalStartDate (event) {
        if (event !== null) {
          this.cmd.startDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedTanggalStartDate (event) {
        this.cmd.startDateString = null
      },
      onMenuSelect (item) {
        this.errorSelect = false
        this.menuItem = item
        this.cmd.menuName = item.text
        this.cmd.menuId = item.value
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/view-surat')
      },
      submit () {
        this.validateAll()
        bus.$emit('SHOW_LOADING')
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        axios.post('/letter-submit', this.form)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/view-surat')
            bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
          })
          .catch((error) => {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log('isi dari error', error.response)
            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })
          })
      },
      reset () {
        this.form.subProId = null
        this.cmd.roleId = null
        this.cmd.menuId = null
        this.roleItem = null
        this.menuItem = null
      },
      tambah () {
        this.validateAll()

        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        this.roleHasMenuList.push({
          id: null,
          idRole: this.cmd.roleId,
          idMenu: this.cmd.menuId,
          menuName: this.cmd.menuName
        })
        _.remove(this.menuList, (menus) => {
          return (menus.text === this.cmd.menuName)
        })
        _.remove(this.roleList, (roles) => {
          return (roles.value !== this.cmd.roleId)
        })
        this.clear()
        this.$forceUpdate()
      },
      clear () {
        this.menuItem.text = ''
        this.menuItem.value = ''
      },
      setTodayDate () {
        var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
        this.currentMonth = months[this.tempDate.getMonth()] + ' ' + this.tempDate.getFullYear()
        this.form.months = this.tempDate.getMonth() + 1
        this.form.years = this.tempDate.getFullYear()
      },
      validateAll () {
        this.validateNotBlank('category', this.form.category)
        this.validateNotBlank('createdDate', this.form.createdDate)
        this.validateNotBlank('description', this.form.description)
        this.validateNotBlank('purpose', this.form.purpose)
        this.validateNotBlank('information', this.form.information)
      },
      validateSubProId () {
        if (this.form.subProId === '' || (typeof this.form.subProId === 'undefined' || this.form.subProId === null)) {
          this.$set(this.errors, 'subProId', 'field cannot be empty')
        } else {
          this.errors.subProId = null
        }
      },
      deleteRole (role) {
        _.remove(this.roleHasMenuList, (roles) => {
          return (roles.menuName === role.menuName)
        })
        this.menuList.push({value: role.idMenu, text: role.menuName})
        this.$forceUpdate()
      }
    }
  }
</script>
