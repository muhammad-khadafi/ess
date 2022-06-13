<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Organization<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="organizationList"
                          :selected-option="organizationItem"
                          placeholder="Organization"
                          @select="onOrganizationSelect"
                          :class="{'is-danger' : cmd.errors.organizationId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.organizationId">
            <span class="help is-danger">{{ cmd.errors.organizationId }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Funding Type<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="fundingList"
                          :selected-option="fundingItem"
                          placeholder="Funding Type"
                          @select="onFundingSelect"
                          :class="{'is-danger' : cmd.errors.fundingType}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.fundingType">
            <span class="help is-danger">{{ cmd.errors.fundingType }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Client</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.client}"
                   type="text" v-model="cmd.client" placeholder="Client"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.client">
            <span class="help is-danger">{{ cmd.errors.client }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Start Date<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <datepicker v-model="cmd.startDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedTanggal($event)"
                        @cleared="clearedTanggal($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.startDate">
            <span class="help is-danger">{{ cmd.errors.startDate }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">End Date</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <datepicker v-model="cmd.endDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="End Date"
                        @selected="selectedTanggal($event)"
                        @cleared="clearedTanggal($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.endDate">
            <span class="help is-danger">{{ cmd.errors.endDate }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Status<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="statusList"
                          :selected-option="statusItem"
                          placeholder="Status"
                          @select="onStatusSelect"
                          :class="{'is-danger' : cmd.errors.statusId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.statusId">
            <span class="help is-danger">{{ cmd.errors.statusId }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Project Name<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.name}"
                   type="text" v-model="cmd.name" placeholder="Project Name"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.name">
            <span class="help is-danger">{{ cmd.errors.name }}</span>
          </div>
        </div>
      </div>
<!--      {{cmd}}-->
      <!--<div class="control is-horizontal">
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
      </div>-->
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
    props: {
      cmd: {
        type: Object,
        required: true
      }
    },
    data () {
      return {
        errorSelect: true,
        clearButton: true,
        parentList: [],
        organizationList: [],
        fundingList: [],
        statusList: [],
        parentItem: {
          value: '',
          text: ''
        },
        organizationItem: {
          value: '',
          text: ''
        },
        fundingItem: {
          value: '',
          text: ''
        },
        statusItem: {
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
          axios.get('/organization-structure/structurelist'),
          axios.get('/organization/organizationlist'),
          axios.get('/master-reference/getReference/Project Status')
        ]).then(axios.spread((parentList, organizationList, statusList) => {
          for (let parent of parentList.data) {
            this.parentList.push({value: parent.id, text: parent.name})
          }
          for (let organization of organizationList.data) {
            this.organizationList.push({value: organization.id, text: organization.name})
            if (organization.id === this.cmd.organizationId) {
              this.organizationItem.text = organization.name
            }
          }
          for (let status of statusList.data) {
            this.statusList.push({value: status.id, text: status.name})
            if (status.id === this.cmd.statusId) {
              this.statusItem.text = status.name
            }
          }
        }))
        this.fundingList.push({value: 'internal', text: 'Internal'})
        this.fundingList.push({value: 'eksternal', text: 'Eksternal'})
        if (this.cmd.fundingType === 'internal') {
          this.fundingItem.text = 'Internal'
        } else if (this.cmd.fundingType === 'eksternal') {
          this.fundingItem.text = 'Eksternal'
        }
      },
      onParentSelect (item) {
        this.errorSelect = false
        this.parentItem = item
        this.cmd.parentId = item.value
      },
      onOrganizationSelect (item) {
        this.errorSelect = false
        this.organizationItem = item
        this.cmd.organizationId = item.value
      },
      onStatusSelect (item) {
        this.errorSelect = false
        this.statusItem = item
        this.cmd.statusId = item.value
      },
      onFundingSelect (item) {
        this.errorSelect = false
        this.fundingItem = item
        this.cmd.fundingType = item.value
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/project')
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        axios.post('/project', this.cmd)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/project')
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
      validateAll () {
        this.validateNotBlank('organizationId', this.cmd.organizationId)
        this.validateNotBlank('name', this.cmd.name)
        this.validateNotBlank('fundingType', this.cmd.fundingType)
        this.validateNotBlank('startDate', this.cmd.startDate)
        this.validateNotBlank('statusId', this.cmd.statusId)
        this.validateNotBlank('name', this.cmd.name)
      },
      selectedTanggal (event) {
        if (event !== null) {
          this.cmd.purchaseDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedTanggal (event) {
        this.cmd.purchaseDateString = null
      }
    }
  }
</script>
