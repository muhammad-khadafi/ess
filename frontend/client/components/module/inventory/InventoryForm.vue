<template>
	<div>
    <div class="box">
      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Type<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
              <basic-select v-if="$route.params.menu != 'view'"
                            :options="inventoryTypeList"
                            :selected-option="inventoryTypeItem"
                            placeholder="-- Please Select Type --"
                            @select="onTypeSelect"
                            :class="{'is-danger' : errors.type}">
              </basic-select>
              <input v-if="$route.params.menu == 'view'" class="input" type="text" v-model="cmd.typeString" disabled/>
            </div>
          </div>
          <div class="control" v-if="errors.name">
            <span class="help is-danger">{{ errors.name }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Name<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input class="input" :class="{'is-danger' : errors.name}" :disabled="$route.params.menu == 'view' ? true : false"
                    type="text" v-model="cmd.name" placeholder="Name" @input="validateTidakKosong('name', cmd.name)"/>
            </div>
          </div>
          <div class="control" v-if="errors.name">
            <span class="help is-danger">{{ errors.name }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Specification</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <textarea class="textarea" :class="{'is-danger' : errors.specification}" :disabled="$route.params.menu == 'view' ? true : false"
                    type="text" v-model="cmd.specification" placeholder="Specification"/>
            </div>
          </div>
          <div class="control" v-if="errors.specification">
            <span class="help is-danger">{{ errors.specification }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Location</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input class="input" :class="{'is-danger' : errors.location}" :disabled="$route.params.menu == 'view' ? true : false"
                    type="text" v-model="cmd.location" placeholder="Location"/>
            </div>
          </div>
          <div class="control" v-if="errors.location">
            <span class="help is-danger">{{ errors.location }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Purchase Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <datepicker v-model="cmd.purchaseDate" v-if="$route.params.menu != 'view'"
                        input-class="input" format="dd-MM-yyyy"
                        placeholder="Purchase Date"
                        @selected="selectedTanggal($event)"
                        @cleared="clearedTanggal($event)"
                        :clear-button="clearButton"></datepicker>
              <input v-if="$route.params.menu == 'view'" class="input"
                     type="text" v-model="cmd.purchaseDateString" :disabled="true"/>
            </div>
            <!-- <input class="input" :class="{'is-danger' : errors.purchaseDateString}"
                    type="text" v-model="cmd.purchaseDateString" placeholder="Purchase Date"/> -->
          </div>
          <div class="control" v-if="errors.purchaseDateString">
            <span class="help is-danger">{{ errors.purchaseDateString }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Purchase Price</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input class="input" :class="{'is-danger' : errors.purchasePrice}" :disabled="$route.params.menu == 'view' ? true : false"
                    type="text" v-on:keyup="onBlurNumber" v-model="cmd.pricewithSeparator" placeholder="Purchase Price"/>
            </div>
          </div>
          <div class="control" v-if="errors.purchasePrice">
            <span class="help is-danger">{{ errors.purchasePrice }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Condition</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input class="input" :class="{'is-danger' : errors.condition}" :disabled="$route.params.menu == 'view' ? true : false"
                    type="text" v-model="cmd.condition" placeholder="Condition"/>
            </div>
          </div>
          <div class="control" v-if="errors.condition">
            <span class="help is-danger">{{ errors.condition }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal" v-if="$route.params.menu == 'view' || $route.params.menu == 'edit' ? true : false">
        <div class="control-label">
          <label class="label">Inventory Code</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input class="input" :class="{'is-danger' : errors.inventoryCode}" :disabled="true"
                    type="text" v-model="cmd.inventoryCode" placeholder="Condition"/>
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal" v-if="$route.params.menu == 'view' || $route.params.menu == 'edit' ? true : false">
        <div class="control-label">
          <button v-if="$route.params.menu != 'add'" class="button is-warning" @click="getQRCode()">Download QR Code</button>
        </div>
      </div>

      <!-- <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Is Assigned ? </label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input class="checkbox" :class="{'is-danger' : errors.isAssigned}"
                    type="checkbox" v-model="cmd.isAssigned"/>
          </div>
          <div class="control" v-if="errors.isAssigned">
            <span class="help is-danger">{{ errors.isAssigned }}</span>
          </div>
        </div>
      </div> -->
<!--      {{cmd}}-->
      <br>
<!--      {{inventoryTypeItem}}-->
      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <!--<div class="control">
            <button class="button is-info" @click="submit()">
              <span>Save</span>
            </button>
          </div>
          <div class="control">
            <button class="button is-warning" @click="cancel()">Cancel</button>
          </div>-->
        </div>
      </div>
    </div>
	</div>
</template>

<script>
import axios from 'axios'
import _ from 'lodash'
import ValidationMixin from '../../mixin/ValidationMixin'
import bus from 'src/shared/bus'
import Datepicker from 'vuejs-datepicker'
import { BasicSelect } from 'vue-search-select'

export default {
  components: {
    BasicSelect,
    Datepicker
  },
  mixins: [ValidationMixin],
  props: {
    id: {
      type: Number
    },
    cmd: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      errorSelect: true,
      clearButton: true,
      inventoryTypeList: [],
      inventoryTypeItem: {
        value: '',
        text: ''
      },
      pricewithSeparator: 0,
      errors: {}
    }
  },
  mounted () {
    console.log('INVENTORY FORM CREATED')
  },
  created () {
    this.fetchData()
    if (this.$route.params.id) {
      axios.get('/inventory/' + this.$route.params.id)
        .then((resp) => {
          console.log('EMPLOYEE FORM FETCH DATA : ', resp.data)
          this.inventoryTypeItem.text = resp.data.typeString
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
    fetchData () {
      console.log('START FETCH')
      axios.all([
        axios.get('/master-reference/getReference/Inventory Type')
      ]).then(axios.spread((inventoryTypeList) => {
        for (let inventoryType of inventoryTypeList.data) {
          this.inventoryTypeList.push({value: inventoryType.id, text: inventoryType.name, codes: inventoryType.category})
        }
      }))
    },
    onTypeSelect (item) {
      this.errorSelect = false
      this.inventoryTypeItem = item
      this.cmd.type = item.value
      this.cmd.typeString = item.text
      this.cmd.inventoryCode = item.codes
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
      axios.post('/inventory', this.cmd)
        .then((resp) => {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCESS')
          console.log(resp)
          this.$router.replace('/inventory')
          bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')
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
    validateTidakKosong (field, value) {
      this.validateNotBlank(field, value)
    },
    validateAll () {
      this.validateNotBlank('type', this.cmd.type)
      this.validateNotBlank('name', this.cmd.name)
    },
    cancel () {
      console.log('CANCEL')
      this.$router.push('/inventory')
    },
    selectedTanggal (event) {
      if (event !== null) {
        this.cmd.purchaseDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedTanggal (event) {
      this.cmd.purchaseDateString = null
    },
    onBlurNumber () {
      this.cmd.pricewithSeparator = this.cmd.pricewithSeparator.replace(/\D/g, '')
      this.cmd.pricewithSeparator = this.thousandSeparator(this.cmd.pricewithSeparator)
    },
    thousandSeparator (amount) {
      if (this.cmd.pricewithSeparator !== null || this.cmd.pricewithSeparator !== '') {
        this.cmd.purchasePrice = this.cmd.pricewithSeparator.replace(/\D/g, '')
      }
      if (amount !== '' || amount !== undefined || amount !== 0 || amount !== '0' || amount !== null) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      } else {
        return amount
      }
    },
    getQRCode () {
      let root = window.location.protocol + '//' + window.location.host
      console.log('Ready to generate')
      window.open(root + '/api/download/qrCode/' + this.$route.params.id + '/' + this.cmd.name)
    }
  }
}
</script>
