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
          <component :is="comTab.component" :cmd="comTab.cmd" :errors="comTab.errors"></component>
          <div class="card-content" v-if="comTab.component === 'Employee'">
            <div class="columns">
              <div class="column is-3">
                <div class="level-right">
                  <label class="label"></label>
                </div>
              </div>
              <div class="column is-one-third">
                <div class="control is-grouped is-pulled-right">
                  <button class="button is-info" @click="submit()">
                    <span>Update Employee Info</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    <!--</div>
    <div class="card-content">-->
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped">
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
  import Employee from './EmployeeEdit.vue'
  import Education from './education/EducationEdit.vue'
  import Family from './family/FamilyEdit.vue'
  import Benefit from './benefit/BenefitEdit.vue'
  import Salary from './salary/SalaryEdit.vue'
  import Skillset from './skillset/SkillsetEdit.vue'
  import Document from './document/DocumentEdit.vue'
  import Contract from './contract/ContractEdit.vue'
  import Position from './position/PositionEdit.vue'

  export default {
    components: {
      Employee,
      Family,
      Education,
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
            },
            errors: {}
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
              result: null
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
              birthDateString: null
            }
          },
          {
            nama: 'Benefit',
            open: false,
            component: 'Benefit',
            cmd: {
              id: null,
              employeeId: null,
              benefitId: null
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
              isActive: true
            }
          },
          {
            nama: 'Skillset',
            open: false,
            component: 'Skillset',
            cmd: {
              id: null,
              employeeId: null,
              skillsetId: null
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
              fileName: null
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
        ]
      }
    },

    created () {
      if (this.$route.params.id) {
        axios.get('/employee/' + this.$route.params.id)
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

      submit () {
        console.log('PRE CMD : ', this.listTab[0].cmd)

        this.validateEmployee()
        if (Object.keys(this.listTab[0].errors).length === 0) {
          let req
          req = axios.post('/employee/add', this.listTab[0].cmd)
          let thisVM = this
          req.then(function (resp) {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCES')
            console.log(resp)
            thisVM.$router.replace('/employee')
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
                bus.$emit('NOTIFY', 'danger', 'Failed adding data')
              }
            })
        } else {
          bus.$emit('HIDE_LOADING')
        }
        console.log('hasil', this.listTab)
      },

      validateEmployee () {
        this.listTab[0].errors = {}

        if (this.listTab[0].cmd.name === null || this.listTab[0].cmd.name === '') {
          this.$set(this.listTab[0].errors, 'name', 'The field cannot be empty')
        }
        if (this.listTab[0].cmd.gender === null || this.listTab[0].cmd.gender === '') {
          this.$set(this.listTab[0].errors, 'gender', 'The field cannot be empty')
        }
        if (this.listTab[0].cmd.email != null && this.listTab[0].cmd.email !== '') {
          var re = /^(([^<>()[\]\\.,;:\s@]+(\.[^<>()[\]\\.,;:\s@]+)*)|(.+))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
          if (!re.test(this.listTab[0].cmd.email)) {
            this.$set(this.listTab[0].errors, 'email', 'Email is not valid')
          }
        }

        for (var field in this.listTab[0].errors) {
          if (this.listTab[0].errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
      },

      kembali () {
        this.$router.replace('/employee')
        // this.formParent.formPegawai = this.listTab[0].dataForm
        // this.formParent.formPendidikan = this.listTab[1].dataForm
        console.log(this.listTab)
      }
    }
  }
</script>
