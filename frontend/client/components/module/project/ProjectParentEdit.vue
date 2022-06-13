<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column">
          <div class="control is-grouped pull-right">
            <!--<button v-if="isDisplay" class="button is-info" @click="submit()">Update</button>&nbsp;
            <button class="button is-warning" @click="kembali()">Back</button>-->
          </div>
        </div>
        <div class="column is-1">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
      </div>
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
<!--    {{listTab[0].cmd}}-->
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped">
            <button v-if="isDisplay" class="button is-info" @click="submit()">Update</button>&nbsp;
            <button class="button is-warning" @click="kembali()">Back</button>
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
  import Project from './ProjectUpdate.vue'
  import ProjectContract from './projectContract/ProjectContractEdit.vue'

  export default {
    components: {
      Project,
      ProjectContract
    },
    data () {
      return {
        formParent: {
          formPegawai: {},
          formPendidikan: {}
        },
        listTab: [
          {
            nama: 'Project',
            open: true,
            component: 'Project',
            cmd: {
              organizationId: null,
              fundingType: null,
              client: null,
              startDate: null,
              endDate: null,
              statusId: null,
              name: null,
              errors: {}
            }
          },
          {
            nama: 'Project Contract',
            open: false,
            component: 'ProjectContract',
            cmd: {
              projectId: null,
              contractNumber: null,
              contractDate: null,
              signee1: null,
              signee2: null
              /* listProjectContract: [],
              term: null,
              percentage: null,
              value: null,
              valueString: null,
              dueDate: null,
              isPaid: false,
              notes: null,
              listProjectPayment: [] */
            }
          }
        ],
        listFileName: [],
        isDisplay: true
      }
    },
    created () {
      if (this.$route.params.id) {
        axios.get('/project/' + this.$route.params.id)
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
      if (this.$route.params.menu === 'view') {
        this.isDisplay = false
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
        thisVm.$router.replace('/project')
        // this.formParent.formPegawai = this.listTab[0].dataForm
        // this.formParent.formPendidikan = this.listTab[1].dataForm
        console.log(this.listTab)
      },
      submit () {
        console.log('PRE CMD : ', this.listTab)
        this.listTab[0].cmd.listProjectContract = this.listTab[1].cmd.listProjectContract
        this.listTab[0].cmd.listProjectPayment = this.listTab[1].cmd.listProjectPayment

        console.log('PRE CMD Update : ', this.listTab[0].cmd)

        this.validateProject()
        if (Object.keys(this.listTab[0].cmd.errors).length === 0) {
          let req
          req = axios.put('/project/' + this.$route.params.id, this.listTab[0].cmd)
          req.then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('FETCH DATA SUCCEED')
            console.log(resp.data)
            this.$router.replace('/project')
            bus.$emit('NOTIFY', 'success', 'Data has been successfully updated')
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
        } else {
          bus.$emit('HIDE_LOADING')
        }
      },

      validateProject () {
        console.log('masuk validasi')
        this.listTab[0].cmd.errors = {}

        if (this.listTab[0].cmd.organizationId === null || this.listTab[0].cmd.organizationId === '') {
          this.$set(this.listTab[0].cmd.errors, 'organizationId', 'The field cannot be empty')
        }
        if (this.listTab[0].cmd.fundingType === null || this.listTab[0].cmd.fundingType === '') {
          this.$set(this.listTab[0].cmd.errors, 'fundingType', 'The field cannot be empty')
        }
        if (this.listTab[0].cmd.statusId === null || this.listTab[0].cmd.statusId === '') {
          this.$set(this.listTab[0].cmd.errors, 'statusId', 'The field cannot be empty')
        }
        if (this.listTab[0].cmd.startDate === null || this.listTab[0].cmd.startDate === '') {
          this.$set(this.listTab[0].cmd.errors, 'startDate', 'The field cannot be empty')
        }
        if (this.listTab[0].cmd.name === null || this.listTab[0].cmd.name === '') {
          this.$set(this.listTab[0].cmd.errors, 'name', 'The field cannot be empty')
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
