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

    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped">
            <button class="button is-info" @click="submit()">Save</button>&nbsp;
            <button class="button is-warning" @click="kembali()">Cancel</button>
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
  import Employee from './EmployeeForm.vue'
  import Education from './education/EducationForm.vue'
  import Family from './family/FamilyForm.vue'
  import Benefit from './benefit/BenefitForm.vue'
  import Salary from './salary/SalaryForm.vue'
  import Skillset from './skillset/SkillsetForm.vue'
  import Document from './document/DocumentForm.vue'
  import Contract from './contract/ContractForm.vue'
  import Position from './position/PositionForm.vue'

  export default {
    components: {
      Employee,
      Education,
      Family,
      Benefit,
      Salary,
      Skillset,
      Document,
      Contract,
      Position
    },
    data () {
      return {
        formParent: {
          formPegawai: {},
          formPendidikan: {}
        },
        listTab: [
          {
            nama: 'Employee',
            open: true,
            component: 'Employee',
            cmd: {
              id: null,
              name: null,
              gender: null,
              nik: null,
              religion: null,
              birthDate: null,
              birthPlace: null,
              bloodType: null,
              mainPhoneNumber: null,
              secondaryPhoneNumber: null,
              email: null,
              emergencyContact: null,
              emergencyPhone: null,
              address: null,
              postcode: null,
              city: null,
              entryDate: null,
              listPendidikan: [],
              errors: {},
              birthDateString: '',
              entryDateString: ''
            }
          },
          {
            nama: 'Education',
            open: false,
            component: 'Education',
            cmd: {
              id: null,
              employeeId: null,
              grade: null,
              institution: null,
              major: null,
              startYear: null,
              endYear: null,
              result: null,
              ListPendidikan: []
            }
          },
          {
            nama: 'Family',
            open: false,
            component: 'Family',
            cmd: {
              id: null,
              employeeId: null,
              relation: null,
              name: null,
              birthDate: null,
              birthPlace: null,
              phoneNumber: null,
              birthDateString: null,
              ListFamily: []
            }
          },
          {
            nama: 'Benefit',
            open: false,
            component: 'Benefit',
            cmd: {
              id: null,
              employeeId: null,
              benefitId: null,
              ListBenefit: []
            }
          },
          {
            nama: 'Salary',
            open: false,
            component: 'Salary',
            cmd: {
              id: null,
              employeeId: null,
              salary: null,
              startDate: null,
              endDate: null,
              startDateString: null,
              endDateString: null,
              isActive: true,
              ListSalary: []
            }
          },
          {
            nama: 'Skillset',
            open: false,
            component: 'Skillset',
            cmd: {
              id: null,
              employeeId: null,
              skillsetId: null,
              ListSkillset: []
            }
          },
          {
            nama: 'Document',
            open: false,
            component: 'Document',
            cmd: {
              id: null,
              employeeId: null,
              documentId: null,
              fileName: null,
              documentType: null,
              ListDocument: [],
              ListUpload: new FormData()
            }
          },
          {
            nama: 'Contract',
            open: false,
            component: 'Contract',
            cmd: {
              id: null,
              employeeId: null,
              contractNumber: null,
              startDate: null,
              endDate: null,
              startDateString: null,
              endDateString: null,
              isActive: true,
              signee: null,
              ListContract: []
            }
          },
          {
            nama: 'Position',
            open: false,
            component: 'Position',
            cmd: {
              id: null,
              employeeId: null,
              structureId: null,
              structureName: null,
              startDate: null,
              endDate: null,
              startDateString: null,
              endDateString: null,
              isActive: true,
              notes: null,
              ListPosition: []
            }
          }
        ],
        listFileName: []
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
        thisVm.$router.replace('/employee')
        // this.formParent.formPegawai = this.listTab[0].dataForm
        // this.formParent.formPendidikan = this.listTab[1].dataForm
        console.log(this.listTab)
      },
      submit () {
        console.log('PRE CMD : ', this.listTab)
        this.listTab[0].cmd.listPendidikan = this.listTab[1].cmd.ListPendidikan
        this.listTab[0].cmd.listFamily = this.listTab[2].cmd.ListFamily
        this.listTab[0].cmd.listBenefit = this.listTab[3].cmd.ListBenefit
        this.listTab[0].cmd.listSalary = this.listTab[4].cmd.ListSalary
        this.listTab[0].cmd.listSkillset = this.listTab[5].cmd.ListSkillset
        this.listTab[0].cmd.listDocument = this.listTab[6].cmd.ListDocument
        this.listTab[0].cmd.listContract = this.listTab[7].cmd.ListContract
        this.listTab[0].cmd.listPosition = this.listTab[8].cmd.ListPosition

        console.log('PRE CMD : ', this.listTab[0].cmd)

        this.validateEmployee()
        if (Object.keys(this.listTab[0].cmd.errors).length === 0) {
          axios.post('/employee-document/upload', this.listTab[6].cmd.ListUpload)
            .then((resp) => {
              for (var key in resp.data) {
                this.listFileName.push(resp.data[key])
              }
              var i = 0
              this.listTab[0].cmd.listDocument.forEach((value) => {
                value.fileName = this.listFileName[i]
                i++
              })
              let req
              req = axios.post('/employee/add', this.listTab[0].cmd)
              req.then((resp) => {
                bus.$emit('HIDE_LOADING')
                console.log('FETCH DATA SUCCEED')
                console.log(resp.data)
                this.$router.replace('/employee')
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
            })
            .catch((error) => {
              if (error.response.status === 422) {
                this.onCloseModal()
                bus.$emit('NOTIFY', 'danger', 'Failed uploading document.')
              }
            })
        } else {
          bus.$emit('HIDE_LOADING')
        }
        console.log('hasil', this.listTab)
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
