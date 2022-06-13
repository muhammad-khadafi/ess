<template>
  <div>
    <div class="box">
      <div class="tabs is-medium is-boxed">
        <ul>
          <li v-for="tab in listTab" :class="{'is-active': tab.open}" @click="open(tab)"><a>{{tab.nama}}</a></li>
        </ul>
      </div>
      <div class="tabs-content">
        <div v-for="comTab in listTab" v-if="comTab.open">
          <component :is="comTab.component" :cmd="comTab.cmd"></component>
        </div>
      </div>
    </div>

      <!--<div class="columns">
        <div class="column">
          <div class="control is-grouped pull-right">
            <button v-if="$route.params.menu == 'add'" class="button is-info" @click="submit()">Save</button>&nbsp;
            <button v-if="$route.params.menu == 'add'" class="button is-warning" @click="kembali()">Cancel</button>

            <button v-if="$route.params.menu == 'edit'" class="button is-info" @click="updates()" style="margin-right: 10px">Update</button>
            <button v-if="$route.params.menu != 'add'" class="button is-warning" @click="kembali()">Back</button>
          </div>
        </div>
        <div class="column is-1">
          <div class="level-right">
            <label class="label"></label>
            &lt;!&ndash;            {{listTab[0].cmd}}&ndash;&gt;
          </div>
        </div>
      </div>-->

    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped">
            <button v-if="$route.params.menu == 'add'" class="button is-info" @click="submit()">Save</button>&nbsp;
            <button v-if="$route.params.menu == 'add'" class="button is-warning" @click="kembali()">Cancel</button>

            <button v-if="$route.params.menu == 'edit'" class="button is-info" @click="updates()" style="margin-right: 10px">Update</button>
            <button v-if="$route.params.menu != 'add'" class="button is-warning" @click="kembali()">Back</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import axios from 'axios'
  import Inventory from './InventoryForm'
  import InventoryAssignment from '../inventoryAssignment/Form'

  export default {
    components: {
      Inventory,
      InventoryAssignment
    },
    data () {
      return {
        listTab: [
          {
            nama: 'Inventory',
            open: true,
            component: 'Inventory',
            cmd: {
              isNew: true,
              id: null,
              type: null,
              typeString: null,
              name: null,
              specification: null,
              location: null,
              purchaseDateString: null,
              purchaseDate: null,
              purchasePrice: null,
              pricewithSeparator: null,
              condition: null,
              isAssigned: false,
              notes: null,
              isInputActive: false,
              inventoryCode: null
            }
          },
          {
            nama: 'Inventory Assignment',
            open: false,
            component: 'InventoryAssignment',
            cmd: {
              employeeName: null,
              employeeId: null,
              inventoryName: null,
              inventoryId: null,
              startDate: null,
              startDateString: null,
              endDate: null,
              endDateString: null,
              id: null,
              listInventoryAssignment: []
            }
          }
        ],
        listFileName: []
      }
    },
    mounted () {
      console.log('INVENTORY FORM CREATED')
    },
    created () {
      if (this.$route.params.id) {
        axios.get('/inventory/' + this.$route.params.id)
          .then((resp) => {
            console.log('EMPLOYEE FORM FETCH DATA : ', resp.data)
            this.listTab[0].cmd = resp.data
          })
          .catch((error) => {
            console.log('ERROR : ', error)
            console.log('isi dari error', error.response)
            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })
          })
      }
    },
    methods: {
      open (dataTab) {
        _.forEach(this.listTab, (tab) => {
          if (tab.nama === dataTab.nama) {
            tab.open = true
          } else {
            tab.open = false
          }
        })
      },
      kembali () {
        let thisVm = this
        thisVm.$router.replace('/inventory')
        // this.formParent.formPegawai = this.listTab[0].dataForm
        // this.formParent.formPendidikan = this.listTab[1].dataForm
        console.log(this.listTab)
      },
      submit () {
        console.log('PRE CMD : ', this.listTab)
        this.listTab[0].cmd.listInventoryAssignment = this.listTab[1].cmd.listInventoryAssignment

        console.log('PRE CMD : ', this.listTab[0].cmd)

        // this.validateEmployee()
        // if (Object.keys(this.listTab[0].cmd.errors).length === 0) {
        let req
        req = axios.post('/inventory', this.listTab[0].cmd)
        req.then((resp) => {
          bus.$emit('HIDE_LOADING')
          console.log('FETCH DATA SUCCEED')
          console.log(resp.data)
          this.$router.replace('/inventory')
          bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
        })
          .catch(function (error) {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log(error.response)

            console.log('SELESAI')
            bus.$emit('NOTIFY', 'danger', 'Failed saving data')
          })
        // }
      },
      updates () {
        console.log('PRE CMD : ', this.listTab[0].cmd)
        let req
        req = axios.put('/inventory/' + this.$route.params.id, this.listTab[0].cmd)
        req.then((resp) => {
          bus.$emit('HIDE_LOADING')
          console.log('FETCH DATA SUCCEED')
          console.log(resp.data)
          this.$router.replace('/inventory')
          bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
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
              bus.$emit('NOTIFY', 'danger', 'Failed updating data')
            }
          })
      },
      validateEmployee () {
        this.listTab[0].cmd.errors = {}

        if (this.listTab[0].cmd.name === null || this.listTab[0].cmd.name === '') {
          this.$set(this.listTab[0].cmd.errors, 'name', 'The field cannot be empty')
        }
        if (this.listTab[0].cmd.gender === null || this.listTab[0].cmd.gender === '') {
          this.$set(this.listTab[0].cmd.errors, 'gender', 'The field cannot be empty')
        }
        if (this.listTab[0].cmd.email != null && this.listTab[0].cmd.email !== '') {
          var re = /^(([^<>()[\]\\.,;:\s@]+(\.[^<>()[\]\\.,;:\s@]+)*)|(.+))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
          if (!re.test(this.listTab[0].cmd.email)) {
            this.$set(this.listTab[0].cmd.errors, 'email', 'Email is not valid')
          }
        }

        for (var field in this.listTab[0].cmd.errors) {
          if (this.listTab[0].cmd.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
      }
    }
  }
</script>
