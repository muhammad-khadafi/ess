<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column">
          <div class="columns is-mobile">
            <div class="column is-2">
              <div class="level-right">
                <label class="label">Periode<font color="red">*</font></label>
              </div>
            </div>
            <div class="column is-2">
              <datepicker v-model="form.startDate" input-class="input" format="dd-MM-yyyy"
                          placeholder="Start Date"
                          :clear-button="clearButton">
              </datepicker>
            </div>
            <div class="column is-1">
              <div class="level-item has-text-centered">
                <label class="label">s.d</label>
              </div>
            </div>
            <div class="column is-2">
              <datepicker v-model="form.endDate" input-class="input" format="dd-MM-yyyy"
                          placeholder="End Date"
                          @selected="selectedTanggal($event)"
                          :clear-button="clearButton">
              </datepicker>
            </div>
            <div class="column is-1">
              <div class="control" v-if="errors.startDate">
                <span class="help is-danger">{{ errors.startDate }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Sub Project</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="subProjectList"
                          :selected-option="subProjectItem"
                          placeholder="Sub Project Name"
                          @select="onSubProjectSelect"
                          :class="{'is-danger' : errors.subProjectId}">
            </basic-select>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Supervisor</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="spvList"
                          :selected-option="spvItem"
                          placeholder="Supervisor Name"
                          @select="onSpvSelect"
                          :class="{'is-danger' : errors.spvId}">
            </basic-select>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Client Signature<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
          <input class="input" :class="{'is-danger' : errors.clientPersonel}"
                   type="text" v-model="form.clientPersonel" />
          </div>
        </div>
        <div class="column is-1">
          <div class="control" v-if="errors.clientPersonel">
            <span class="help is-danger">{{ errors.clientPersonel }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Client Role<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <textarea class="textarea" :class="{'is-danger' : errors.clientRole}"
                   style="resize: none;" type="text" v-model="form.clientRole" />
          </div>
        </div>
        <div class="column is-1">
          <div class="control" v-if="errors.clientRole">
            <span class="help is-danger">{{ errors.clientRole }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Pusilkom Signature<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.pusilkomPersonel}"
                   type="text" v-model="form.pusilkomPersonel" />
          </div>
        </div>
        <div class="column is-1">
          <div class="control" v-if="errors.pusilkomPersonel">
            <span class="help is-danger">{{ errors.pusilkomPersonel }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-2">
          <div class="level-right">
            <label class="label">Pusilkom Role<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <textarea class="textarea" :class="{'is-danger' : errors.pusilkomRole}"
                      style="resize: none;" type="text" v-model="form.pusilkomRole" />
          </div>
        </div>
        <div class="column is-1">
          <div class="control" v-if="errors.pusilkomRole">
            <span class="help is-danger">{{ errors.pusilkomRole }}</span>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
         <div class="control"> 
            <button class="button is-info" @click="generate()">
              <span class="icon is-small">
                <i class="fa fa-check"></i>
              </span>
              <span>Generate</span>
            </button>
          </div>

        </div>
        <!-- {{form}} -->
      </div>

    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import { BasicSelect } from 'vue-search-select'
  import Datepicker from 'vuejs-datepicker'
  import ValidationMixin from '../../mixin/ValidationMixin'
//  import _ from 'lodash'
//  import bus from 'src/shared/bus'

  export default {
    components: {
      BasicSelect,
      Datepicker
    },
    mixins: [ValidationMixin],
    props: {
      id: {
        type: Number
      }
    },
    data () {
      return {
        clearButton: true,
        form: {
          startDate: null,
          endDate: null,
          subProjectId: null,
          subProjectString: null,
          spvId: null,
          spvName: null,
          clientPersonel: null,
          clientRoleReplace: null,
          pusilkomPersonel: null,
          pusilkomRole: null,
          pusilkomRoleReplace: null,
          dateString: null
        },
        subProjectList: [],
        subProjectItem: {
          value: '',
          text: ''
        },
        spvList: [],
        spvItem: {
          value: '',
          text: ''
        },
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/invoice-report/getSubProject'),
          axios.get('/invoice-report/getSpv')
        ]).then(axios.spread((subProjectList, spvList) => {
          for (let subProject of subProjectList.data) {
            this.subProjectList.push({value: subProject.id, text: subProject.code})
          }
          for (let spv of spvList.data) {
            this.spvList.push({value: spv.id, text: spv.name})
          }
        }))
      },
      generate () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            return
          }
        }
        this.form.pusilkomRoleReplace = this.form.pusilkomRole.replace(/\n/g, '|')
        this.form.clientRoleReplace = this.form.clientRole.replace(/\n/g, '|')
        console.log('Ready to generate ' + this.form.pusilkomRole)
        axios.post('/report/generateticket/invoicereport', this.form)
          .then((resp) => {
            console.log('BERHASIL : ', resp)
            let ticket = resp.data.ticket
            let root = window.location.protocol + '//' + window.location.host
            console.log('Ready to generate')
            window.open(root + '/api/download/generatereport/invoicereport/' +
              this.form.startDate + '/' + this.form.endDate + '/' + this.form.dateString + '/' + this.form.subProjectId + '/' +
              this.form.subProjectString + '/' + this.form.spvName + '/' + this.form.clientPersonel + '/' + this.form.clientRoleReplace + '/' +
              this.form.pusilkomPersonel + '/' + this.form.pusilkomRoleReplace + '?ticket=' + ticket)
//            window.open(root + '/api/download2/sample2/cetakreport2/' +
//              this.form.pusilkomRole + '?ticket=' + ticket)
          })
          .catch((error) => {
            console.log('GAGAL : ', error)
          })
      },
      download () {
        let root = window.location.protocol + '//' + window.location.host
        console.log('Ready to generate')
        window.open(root + '/api/log-activity-report/get-report/1')
      },
      onSpvSelect (item) {
        this.errorSelect = false
        this.spvItem = item
        this.form.spvId = item.value
        this.form.spvName = item.text
      },
      onSubProjectSelect (item) {
        this.errorSelect = false
        this.subProjectItem = item
        this.form.subProjectId = item.value
        this.form.subProjectString = item.text
      },
      selectedTanggal (event) {
        if (event !== null) {
          this.form.dateString = event.getFullYear() + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1))
        }
      },
      validateAll () {
        this.validateNotBlank('startDate', this.form.startDate)
        this.validateNotBlank('startDate', this.form.endDate)
        this.validateNotBlank('clientPersonel', this.form.clientPersonel)
        this.validateNotBlank('clientRole', this.form.clientRole)
        this.validateNotBlank('pusilkomPersonel', this.form.pusilkomPersonel)
        this.validateNotBlank('pusilkomRole', this.form.pusilkomRole)
      }
    }
  }
</script>
