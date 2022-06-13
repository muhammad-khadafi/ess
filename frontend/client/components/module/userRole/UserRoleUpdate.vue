<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Username<font color="red">*</label>
          </div>
        </div>
        <div class="column is-one-quarter">
          <div class="control is-grouped level-left">
            <basic-select :options="userList"
                          :selected-option="userItem"
                          placeholder="Please Select"
                          @select="onUserSelect"
                          :class="{'is-danger' : errors.userId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.userId">
            <span class="help is-danger">{{ errors.userId }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Role<font color="red">*</label>
          </div>
        </div>
        <div class="column is-one-quarter">
          <div class="control is-grouped level-left">
            <basic-select :options="roleList"
                          :selected-option="roleItem"
                          placeholder="Please Select"
                          @select="onRoleSelect"
                          :class="{'is-danger' : errors.projectId}">
            </basic-select>
          </div>
        </div>
        <div class="column is-2-mobile">
          <button class="button is-info" @click="tambah()">
            <span>Add Role</span>
          </button>
        </div>
        <div class="column is-2-mobile">
          <div class="control" v-if="errors.userId">
            <span class="help is-danger">{{ errors.userId }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
          </div>
        </div>
        <div class="column is-half">
          <table class="vuetable ui blue selectable celled stackable attached table">
            <th>nomor</th>
            <th>Role</th>
            <tr v-for="(role, index) in userWebRole">
              <td>{{ index + 1  }}</td>
              <td>{{ role.roleName }}</td>
              <td><a href="" @click.prevent="deleteRole(role)">Delete</a></td>
            </tr>
          </table>
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
      <!--{{userWebRole}}-->
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
          roleName: null,
          userId: null,
          UserWebRoleList: null,
          projectId: null,
          contractNumber: null,
          contractDate: null,
          signee1: null,
          signee2: null
        },
        userWebRole: [],
        roleList: [],
        userList: [],
        roleItem: {
          value: '',
          text: ''
        },
        userItem: {
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
        axios.all([
          axios.get('/role/getUserHasRole/' + this.$route.params.id),
          axios.get('/user-role/update/' + this.$route.params.id)
        ]).then(axios.spread((roleList, userWebRole) => {
          for (let roles of roleList.data) {
            this.roleList.push({value: roles.id, text: roles.label})
          }
          for (let item of userWebRole.data) {
            this.userItem.value = item.idUserweb
            this.userItem.text = item.usernameString
            this.cmd.userId = item.idUserweb
            this.userWebRole.push({
              id: item.id,
              idRole: item.idRole,
              idUserweb: item.idUserweb,
              roleName: item.roleString
            })
          }
        }))
      },
      onRoleSelect (item) {
        this.errorSelect = false
        this.roleItem = item
        this.cmd.roleName = item.text
        this.cmd.roleId = item.value
      },
      onUserSelect (item) {
        this.errorSelect = false
        this.userItem = item
        this.cmd.userId = item.value
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/user-role')
      },
      submit () {
        console.log('mulai submit')

        if (this.userWebRole === undefined || this.userWebRole.length === 0) {
          bus.$emit('HIDE_LOADING')
          bus.$emit('NOTIFY', 'danger', 'please select at least one user/role')
          return
        }

        axios.put('/user-role/' + this.$route.params.id, this.userWebRole)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/user-role')
            bus.$emit('NOTIFY', 'success', 'Data has been successfully updated')
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
      tambah () {
        this.validateAll()

        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        this.userWebRole.push({
          id: null,
          idRole: this.cmd.roleId,
          idUserweb: this.cmd.userId,
          roleName: this.cmd.roleName
        })
        _.remove(this.roleList, (roles) => {
          return (roles.text === this.cmd.roleName)
        })
        _.remove(this.userList, (users) => {
          return (users.value !== this.cmd.userId)
        })
        this.clear()
        this.$forceUpdate()
      },
      clear () {
        this.roleItem.text = ''
        this.roleItem.value = ''
      },
      validateAll () {
        this.validateNotBlank('roleId', this.cmd.roleId)
        this.validateNotBlank('userId', this.cmd.userId)
      },
      deleteRole (role) {
        _.remove(this.userWebRole, (roles) => {
          return (roles.roleName === role.roleName)
        })
        this.roleList.push({value: role.idRole, text: role.roleName})
        this.$forceUpdate()
      }
    }
  }
</script>
