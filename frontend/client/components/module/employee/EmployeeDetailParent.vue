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

    <br/>
    <div class="column level-right">
      <button class="button" @click="kembali()" >Back</button>
    </div>
  </div>
</template>
<script>
import _ from 'lodash'
// import bus from 'src/shared/bus'
import axios from 'axios'
import Employee from './EmployeeDetail.vue'
import Education from './education/EducationDetail.vue'

export default {
  components: {
    Employee,
    Education
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
            listPendidikan: []
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
        }
      ]
    }
  },
  created () {
    this.fetchData()
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
    fetchData () {
      console.log('EMPLOYEE FORM FETCH DATA : ', this.$route)

      let thisVM = this
      if (!this.isNew) {
        axios.get('/employee/' + this.$route.params.id)
          .then(function (resp) {
            thisVM.detail = resp.data
            console.log('EMPLOYEE FETCH DATA : ', resp.data)
          })
          .catch(function (error) {
            console.log('ERROR FETCH DATA', thisVM.id)
            console.log(error.response)
            thisVM.$emit('close')
            thisVM.$emit('NOTIFY', 'danger', 'Data not found')
          })
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
