<template>
  <div class="card-content">
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Username<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <input class="input"
                 type="text" v-model="cmd.username" placeholder="Username"/>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.username"> 
          <span class="help is-danger">{{ errors.username }}</span> 
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Employee Name<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <basic-select :options="employeeList"
                        :selected-option="employeeItem"
                        placeholder="Employee"
                        @select="onEmployeeSelect"
                        :class="{'is-danger' : errors.employeeId}">
          </basic-select>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.employeeId"> 
          <span class="help is-danger">{{ errors.employeeId }}</span> 
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Is Active</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <input type="checkbox" v-model="cmd.flagAktif" name="isActive">
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.username"> 
          <span class="help is-danger">{{ errors.flagAktif }}</span> 
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
</template>

<script>
  import axios from 'axios'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { BasicSelect } from 'vue-search-select'
  import ValidationMixin from '../../mixin/ValidationMixin'

  export default {
    components: {
      BasicSelect
    },
    mixins: [ValidationMixin],
    data () {
      return {
        errorSelect: true,
        checkDuplicate: false,
        cmd: {
          employeeId: null,
          username: null,
          flagAktif: true
        },
        employeeList: [],
        employeeItem: {
          value: '',
          text: ''
        },
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/employee/all')
        ]).then(axios.spread((employeeList) => {
          for (let employee of employeeList.data) {
            this.employeeList.push({value: employee.id, text: employee.name})
          }
        }))
      },
      onEmployeeSelect (item) {
        this.errorSelect = false
        this.employeeItem = item
        this.cmd.employeeId = item.value
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/user')
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
        axios.post('/user/checkduplicate', this.cmd)
          .then((resp) => {
            this.checkDuplicate = resp.data
            if (this.checkDuplicate === false) {
              axios.post('/user', this.cmd)
                .then((resp) => {
                  bus.$emit('HIDE_LOADING')
                  console.log('SUCCESS')
                  console.log(resp)
                  this.$router.replace('/user')
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
            } else {
              bus.$emit('HIDE_LOADING')
              bus.$emit('NOTIFY', 'danger', 'Username can not be duplicate')
            }
          })
          .catch((error) => {
            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })
          })
      },
      validateEmployee () {
        if (this.cmd.employeeId === '' || (typeof this.cmd.employeeId === 'undefined' || this.cmd.employeeId === null)) {
          this.$set(this.errors, 'employeeId', 'Employee Name not empty')
        } else {
          this.errors.employeeId = null
        }
      },
      validateUsername () {
        if (this.cmd.username === '' || (typeof this.cmd.username === 'undefined' || this.cmd.username === null)) {
          this.$set(this.errors, 'username', 'Username not empty')
        } else {
          this.errors.username = null
        }
      },
      validateAll () {
        this.validateEmployee()
        this.validateUsername()
      }
    }
  }
</script>
