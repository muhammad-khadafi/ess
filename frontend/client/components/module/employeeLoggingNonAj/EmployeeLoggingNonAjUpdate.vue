<template>
  <div>
    <div class="box">
      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Employee Name<font color="red">*</font></label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input class="input" :class="{'is-danger' : errors.employeeName}"
                   type="text" v-model="cmd.employeeName" placeholder="Employee Name" disabled/>
          </div>
          <div class="control" v-if="errors.employeeName">
            <span class="help is-danger">{{ errors.employeeName }}</span>
          </div>
        </div>
      </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Date<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <datepicker v-model="cmd.date" input-class="input" format="dd-MM-yyyy"
                          placeholder="Date"
                          :clear-button="clearButton"></datepicker>
            </div>
            <div class="control" v-if="errors.date">
              <span class="help is-danger">{{ errors.date }}</span>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Project<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <basic-select :options="parentList"
                            :selected-option="parentItem"
                            placeholder="Parent Organization"
                            @select="onParentSelect"
                            :class="{'is-danger' : errors.subProId}">
              </basic-select>
            </div>
            <div class="control" v-if="errors.subProId">
              <span class="help is-danger">{{ errors.subProId }}</span>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">SPV Assigned<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input" :class="{'is-danger' : errors.spvAssigned}"
                     type="text" v-model="cmd.spvAssigned" placeholder="SPV Assigned"/>
            </div>
            <div class="control" v-if="errors.spvAssigned">
              <span class="help is-danger">{{ errors.spvAssigned }}</span>
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Activity Detail<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <textarea class="textarea" :class="{'is-danger' : errors.activityDetail}"
                     type="text" v-model="cmd.activityDetail" placeholder="Activity Detail"/>
            </div>
            <div class="control" v-if="errors.activityDetail"> 
              <span class="help is-danger">{{ errors.activityDetail }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Collaboration With<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <textarea class="textarea" :class="{'is-danger' : errors.thirdParty}"
                     type="text" v-model="cmd.thirdParty" placeholder="Collaboration With"/>
            </div>
            <div class="control" v-if="errors.thirdParty"> 
              <span class="help is-danger">{{ errors.thirdParty }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Duration<font color="red">*</font></label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input" min="0" :class="{'is-danger' : errors.duration}"
                     type="number" onkeypress="return (event.charCode == 8 || event.charCode == 0 || event.charCode == 13) ? null : event.charCode >= 48 && event.charCode <= 57"
                     v-model="cmd.duration" placeholder="Duration"/>
            </div>
            <div class="control" v-if="errors.duration"> 
              <span class="help is-danger">{{ errors.duration }}</span> 
            </div>
          </div>
        </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label">Overtime</label>
          </div>
          <div class="control is-grouped">
            <div class="control is-expanded">
              <input class="input" min="0" :class="{'is-danger' : errors.overtime}"
                     type="number" onkeypress="return (event.charCode == 8 || event.charCode == 0 || event.charCode == 13) ? null : event.charCode >= 48 && event.charCode <= 57"
                     v-model="cmd.overtime" placeholder="Overtime"/>
            </div>
            <div class="control" v-if="errors.overtime"> 
              <span class="help is-danger">{{ errors.overtime }}</span> 
            </div>
          </div>
        </div>

      <div class="control is-horizontal">
        <div class="control" v-if="errors.dateString">
          <span class="help is-danger">{{ errors.dateString }}</span>
        </div>
      </div>

        <div class="control is-horizontal">
          <div class="control-label">
            <label class="label"></label>
          </div>
          <div class="control is-grouped">
            <div class="control"> 
              <button class="button is-info" @click="submit()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
                <span>Update</span>
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
import Datepicker from 'vuejs-datepicker'
import ValidationMixin from '../../mixin/ValidationMixin'
import { BasicSelect } from 'vue-search-select'

export default {
  props: {
    id: {
      type: Number
    }
  },
  components: {
    BasicSelect,
    Datepicker
  },
  mixins: [ValidationMixin],
  data () {
    return {
      isNew: true,
      clearButton: true,
      cmd: {
        id: null,
        activityDetail: null,
        date: null,
        subProAssignmentId: null,
        thirdParty: null,
        duration: null,
        overtime: null,
        spvAssigned: null,
        subProId: null,
        subProjectName: null,
        projectAssignId: null,
        projectId: null,
        idEmployee: null
      },
      parentList: [],
      parentItem: {
        value: '',
        text: ''
      },
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
      this.cmd.kodeInstitusi = null
      this.cmd.nama = null
      this.cmd.namaSingkat = null
      this.cmd.flagAktif = true
    },
    fetchData () {
      console.log('fetchiiing..')
      this.reset()
      console.log('INSTITUSI FORM FETCH DATA : ', this.$route)
      console.log(this.$route.params)

      this.cmd.id = this.id
      this.isNew = _.isNil(this.cmd.id)

      console.log(this.isNew)

      let thisVM = this
      if (!this.isNew) {
        axios.get('/log-act-detail/' + this.cmd.id)
        .then(function (resp) {
          thisVM.cmd = resp.data
          thisVM.cmd.idEmployee = resp.data.employeeId
          thisVM.cmd.projectId = 3
          axios.all([
            axios.get('sub-project-by-project/3')
          ]).then(axios.spread((parentList) => {
            for (let parent of parentList.data) {
              thisVM.parentList.push({value: parent.id, text: parent.code, spv: parent.spvName})
              if (thisVM.cmd.subProId === parent.id) {
                thisVM.parentItem.value = parent.id
                thisVM.parentItem.text = parent.code
              }
            }
          }))
        })
        .catch(function (error) {
          console.log('ERROR FETCH DATA', thisVM.id)
          console.log(error.response)
          thisVM.$emit('close')
          thisVM.bus.$emit('NOTIFY', 'danger', 'Tidak ada data')
        })
      }
    },
    cancel () {
      this.$emit('close')
    },
    validateSubProId () {
      if (this.cmd.subProId === '' || (typeof this.cmd.subProId === 'undefined' || this.cmd.subProId === null)) {
        this.$set(this.errors, 'subProId', 'Tidak boleh kosong')
      } else {
        this.errors.subProId = null
      }
    },
    validateAll () {
      this.errors.dateString = null
      this.validateNotBlank('date', this.cmd.date)
      this.validateNotBlank('spvAssigned', this.cmd.spvAssigned)
      this.validateNotBlank('activityDetail', this.cmd.activityDetail)
      this.validateNotBlank('thirdParty', this.cmd.thirdParty)
      this.validatePositifValueandGreaterThanZero('duration', this.cmd.duration)
      this.validatePositifValue('overtime', this.cmd.overtime)
      this.validateSubProId()
    },
    onParentSelect (item) {
      this.errorSelect = false
      this.parentItem = item
      this.cmd.subProId = item.value
      this.cmd.spvAssigned = item.spv
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

      this.errors = {}

      let thisVM = this

      let req

      axios.post('/log-act-detail/checkProjectEmployee', this.cmd)
        .then((resp) => {
          console.log('asd' + resp.data)
          this.cmd.projectAssignId = resp.data
          if (this.cmd.projectAssignId !== '') {
            req = axios.put('/log-act-detail/' + this.cmd.id, this.cmd)
            req.then(function (resp) {
              bus.$emit('HIDE_LOADING')
              console.log('SUCCES')
              console.log(resp)
              bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')

              thisVM.$emit('close')
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
                if (error.response.status === 422) {
                  bus.$emit('NOTIFY', 'danger', 'Anda sudah menginputkan log dengan tanggal dan project yang sama')
                }
              })
          } else {
            this.cmd.dateString = (this.cmd.date.getDate() < 10 ? '0' + this.cmd.date.getDate() : this.cmd.date.getDate()) + '-' + (this.cmd.date.getMonth() + 1 < 10 ? ('0' + (this.cmd.date.getMonth() + 1)) : (this.cmd.date.getMonth() + 1)) + '-' + this.cmd.date.getFullYear()
            this.$set(this.errors, 'dateString', 'Tidak memiliki project assignment di tanggal ' + this.cmd.dateString)
          }
        })
        .catch((error) => {
          _.each(error.response.data.errors, function (value) {
            this.$set(this.errors, value.field, value.message)
          })
        })
    }
  }
}
</script>
