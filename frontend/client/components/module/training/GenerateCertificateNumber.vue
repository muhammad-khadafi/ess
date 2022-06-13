<template>
  <div>
    <div class="box">
      <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Category<font color="red">*</font></label>
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
            <label class="label">Month/Year<font color="red">*</font></label>

          </div>
        </div>
        <div class="column is-2">
            <vue-monthly-picker 
                v-model="form.courseDate" 
                :monthLabels=monthLabels 
                dateFormat="MMM-YYYY"
                @selected="onDateSelect()">
                </vue-monthly-picker>
          <div class="control is-grouped" v-if="errors.courseDate">
            <span class="help is-danger">{{ errors.courseDate }}</span>
          </div>
        </div>
      </div>

      <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Course Name<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-4">
          <div class="control is-grouped level-left">
          <input class="input"
                     type="text" v-model="form.courseName"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.courseName">
            <span class="help is-danger">{{ errors.courseName }}</span>
          </div>
        </div>
      </div>

      <div class="columns is-mobile">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Certificate Number</label>
          </div>
        </div>
        <div class="column is-4">
          <div class="control is-grouped level-left">
          <label class="label">{{certificateNumber}}</label>
          </div>
        </div>
        <div class="column">
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <div class="control">
            <button class="button is-info" @click="submit()">
              <span>Generate Certificate Number</span>
            </button>
          </div>
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
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import ValidationMixin from '../../mixin/ValidationMixin'
  import VueMonthlyPicker from 'vue-monthly-picker'
  import moment from 'moment'

  export default {
    components: {
      BasicSelect,
      Datepicker,
      VueMonthlyPicker
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
          category: null,
          courseDate: null,
          courseName: null
        },
        tempDate: new Date(),
        listCategory: [
          { value: null, text: '--- Choose Category ---' },
          { value: 'REGULER', text: 'Reguler' },
          { value: 'SHORT_COURSE', text: 'Short Course' },
          { value: 'IHT', text: 'IHT' }
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
        clearButton: true,
        sampleDate: null,
        monthLabels: ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'],
        categoryList: [],
        certificateNumber: ''
      }
    },
    created () {
    //   bus.$emit('SHOW_LOADING')
      this.fetchData()
      this.setTodayDate()
      this.sampleDate = moment()
    },
    methods: {
      fetchData () {
        axios.get('/master-reference/getReference/Training Category').then((resp) => {
          for (var i = 0; i < resp.data.length; i++) {
            this.categoryList.push({value: resp.data[i].name, text: resp.data[i].name})
          }
        })
      },

      onDateSelect () {
        // this.form.courseDate = moment(this.form.courseDate, 'DD-MM-YYYY').add(1, 'days')
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
        this.$router.replace('/certificate')
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
        axios.post('/certificate-generator', this.form)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.certificateNumber = resp.data
            bus.$emit('NOTIFY', 'success', 'Generate certificate number success!')
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
        this.validateNotBlank('courseDate', this.form.courseDate)
        this.validateNotBlank('courseName', this.form.courseName)
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
