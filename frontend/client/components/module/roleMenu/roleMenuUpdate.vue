<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Role*</label>
          </div>
        </div>
        <div class="column is-one-quarter">
          <div class="control is-grouped level-left">
            <basic-select :options="roleList"
                          :selected-option="roleItem"
                          placeholder="Please Select"
                          @select="onRoleSelect"
                          :class="{'is-danger' : errors.roleId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.roleId">
            <span class="help is-danger">{{ errors.roleId }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Menu*</label>
          </div>
        </div>
        <div class="column is-one-quarter">
          <div class="control is-grouped level-left">
            <basic-select :options="menuList"
                          :selected-option="menuItem"
                          placeholder="Please Select"
                          @select="onMenuSelect"
                          :class="{'is-danger' : errors.menuId}">
            </basic-select>
          </div>
        </div>
        <div class="column is-2-mobile">
          <button class="button is-info" @click="tambah()">
            <span>Add Menu</span>
          </button>
        </div>
        <div class="column is-2-mobile">
          <div class="control" v-if="errors.menuId">
            <span class="help is-danger">{{ errors.menuId }}</span>
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
            <th>Menu</th>
            <tr v-for="(role, index) in roleHasMenuList">
              <td>{{ index + 1  }}</td>
              <td>{{ role.menuName }}</td>
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
      <!--{{roleHasMenuList}}-->
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
          menuName: null,
          menuId: null
        },
        roleHasMenuList: [],
        roleList: [],
        menuList: [],
        roleItem: {
          value: '',
          text: ''
        },
        menuItem: {
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
          axios.get('/role-menu/update/' + this.$route.params.id),
          axios.get('/menu/getMenuRoleHasMenu/' + this.$route.params.id)
        ]).then(axios.spread((roleHasMenuList, menuList) => {
          for (let item of roleHasMenuList.data) {
            this.roleItem.value = item.idRole
            this.roleItem.text = item.roleString
            this.cmd.roleId = item.idRole
            this.roleHasMenuList.push({
              id: item.id,
              idRole: item.idRole,
              idMenu: item.idMenu,
              menuName: item.menuString
            })
          }
          for (let menus of menuList.data) {
            this.menuList.push({value: menus.id, text: menus.label})
          }
        }))
      },
      onRoleSelect (item) {
        this.errorSelect = false
        this.roleItem = item
        this.cmd.roleId = item.value
      },
      onMenuSelect (item) {
        this.errorSelect = false
        this.menuItem = item
        this.cmd.menuName = item.text
        this.cmd.menuId = item.value
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/role-menu')
      },
      submit () {
        console.log('mulai submit')

        if (this.roleHasMenuList === undefined || this.roleHasMenuList.length === 0) {
          bus.$emit('HIDE_LOADING')
          bus.$emit('NOTIFY', 'danger', 'please select at least one role/menu')
          return
        }

        axios.put('/role-menu/' + this.$route.params.id, this.roleHasMenuList)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/role-menu')
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
      reset () {
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
      validateAll () {
        this.validateNotBlank('roleId', this.cmd.roleId)
        this.validateNotBlank('menuId', this.cmd.menuId)
      },
      deleteRole (menu) {
        _.remove(this.roleHasMenuList, (menus) => {
          return (menus.menuName === menu.menuName)
        })
        this.menuList.push({value: menu.idMenu, text: menu.menuName})
        this.$forceUpdate()
      }
    }
  }
</script>
