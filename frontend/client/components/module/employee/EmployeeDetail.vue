<template>
  <div>
    <div class="box">
      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Name</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.name}"
                   type="text" v-model="detail.name" placeholder="Name"/>
          </div>
          <div class="control" v-if="errors.name"> 
            <span class="help is-danger">{{ errors.name }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Gender</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.gender}"
                   type="text" v-model="detail.gender" placeholder="Gender"/>
            <br>
          </div>
          <div class="control" v-if="errors.gender"> 
            <span class="help is-danger">{{ errors.gender }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">NIK</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.nik}"
                   type="text" v-model="detail.nik" placeholder="NIK"/>
          </div>
          <div class="control" v-if="errors.nik"> 
            <span class="help is-danger">{{ errors.nik }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Birth Place</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.birthPlace}"
                   type="text" v-model="detail.birthPlace" placeholder="Birth Place"/>
          </div>
          <div class="control" v-if="errors.birthPlace"> 
            <span class="help is-danger">{{ errors.birthPlace }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Birth Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.birthDateString}"
                   type="date" v-model="detail.birthDateString" placeholder="Birth Date"/>
          </div>
          <div class="control" v-if="errors.birthDateString"> 
            <span class="help is-danger">{{ errors.birthDateString }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Religion</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.religion}"
                   type="text" v-model="detail.religion" placeholder="Religion"/>
          </div>
          <div class="control" v-if="errors.religion"> 
            <span class="help is-danger">{{ errors.religion }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Blood Type</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.bloodType}"
                   type="text" v-model="detail.bloodType" placeholder="Blood Type"/>
          </div>
          <div class="control" v-if="errors.bloodType"> 
            <span class="help is-danger">{{ errors.bloodType }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Phone Number (Main)</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.mainPhoneNumber}"
                   type="number" v-model="detail.mainPhoneNumber" placeholder="Phone Number (Main)"/>
          </div>
          <div class="control" v-if="errors.mainPhoneNumber"> 
            <span class="help is-danger">{{ errors.mainPhoneNumber }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Phone Number (Secondary)</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.secondaryPhoneNumber}"
                   type="number" v-model="detail.secondaryPhoneNumber" placeholder="Phone Number (Secondary)"/>
          </div>
          <div class="control" v-if="errors.secondaryPhoneNumber"> 
            <span class="help is-danger">{{ errors.secondaryPhoneNumber }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Email</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.email}"
                   type="text" v-model="detail.email" placeholder="Email"/>
          </div>
          <div class="control" v-if="errors.email"> 
            <span class="help is-danger">{{ errors.email }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Emergency Contact</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.emergencyContact}"
                   type="text" v-model="detail.emergencyContact" placeholder="Emergency Contact"/>
          </div>
          <div class="control" v-if="errors.emergencyContact"> 
            <span class="help is-danger">{{ errors.emergencyContact }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Emergency Phone</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.emergencyPhone}"
                   type="number" v-model="detail.emergencyPhone" placeholder="Emergency Phone"/>
          </div>
          <div class="control" v-if="errors.emergencyPhone"> 
            <span class="help is-danger">{{ errors.emergencyPhone }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Address</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.address}"
                   type="text" v-model="detail.address" placeholder="Address"/>
          </div>
          <div class="control" v-if="errors.address"> 
            <span class="help is-danger">{{ errors.address }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Postcode</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.postcode}"
                   type="text" v-model="detail.postcode" placeholder="Postcode"/>
          </div>
          <div class="control" v-if="errors.postcode"> 
            <span class="help is-danger">{{ errors.postcode }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">City</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.city}"
                   type="text" v-model="detail.city" placeholder="City"/>
          </div>
          <div class="control" v-if="errors.city"> 
            <span class="help is-danger">{{ errors.city }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Entry Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input disabled class="input" :class="{'is-danger' : errors.entryDateString}"
                   type="date" v-model="detail.entryDateString" placeholder="Entry Date"/>
          </div>
          <div class="control" v-if="errors.entryDateString"> 
            <span class="help is-danger">{{ errors.entryDateString }}</span> 
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <div class="control"> 
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

  export default {
    data () {
      return {
        detail: {
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
          entryDateString: null,
          birthDateString: null
        },
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },

    methods: {
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
      cancel () {
        this.$router.replace('/employee')
      }
    }
  }
</script>
