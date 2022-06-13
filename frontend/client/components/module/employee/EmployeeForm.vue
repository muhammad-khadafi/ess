<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Name<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.name}"
                   type="text" v-model="cmd.name" placeholder="Name"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.name"> 
            <span class="help is-danger">{{ cmd.errors.name }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Gender<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input type="radio" id="M" value="M" v-model="cmd.gender">
            <label for="M">Male</label>
            &nbsp;&nbsp;
            <input type="radio" id="F" value="F" v-model="cmd.gender">
            <label for="F">Female</label>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.gender"> 
            <span class="help is-danger">{{ cmd.errors.gender }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">NIK</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.nik}"
                   type="text" v-model="cmd.nik" placeholder="NIK"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.nik"> 
            <span class="help is-danger">{{ cmd.errors.nik }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Birth Place</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.birthPlace}"
                   type="text" v-model="cmd.birthPlace" placeholder="Birth Place"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.birthPlace"> 
            <span class="help is-danger">{{ cmd.errors.birthPlace }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Birth Date</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <datepicker class="control is-expanded" v-model="cmd.birthDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedBirthDate($event)"
                        @cleared="clearedBirthDate($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.birthDate"> 
            <span class="help is-danger">{{ cmd.errors.birthDate }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Religion</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <div class="select is-fullwidth">
              <select :class="{'is-danger' : cmd.errors.religion}" v-model="cmd.religion">
                <option v-for="option in listReligion" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
            </div>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.religion"> 
            <span class="help is-danger">{{ cmd.errors.religion }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Blood Type</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <span class="select is-fullwidth">
              <select :class="{'is-danger' : cmd.errors.bloodType}" v-model="cmd.bloodType">
                <option v-for="option in listBloodType" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
            </span>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.bloodType"> 
            <span class="help is-danger">{{ cmd.errors.bloodType }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Phone Number (Main)</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.mainPhoneNumber}"
                   type="number" v-model="cmd.mainPhoneNumber" placeholder="Phone Number (Main)"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.mainPhoneNumber"> 
            <span class="help is-danger">{{ cmd.errors.mainPhoneNumber }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Phone Number (Secondary)</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.secondaryPhoneNumber}"
                   type="number" v-model="cmd.secondaryPhoneNumber" placeholder="Phone Number (Secondary)"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.secondaryPhoneNumber"> 
            <span class="help is-danger">{{ cmd.errors.secondaryPhoneNumber }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Email</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.email}"
                   type="text" v-model="cmd.email" placeholder="Email"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.email"> 
            <span class="help is-danger">{{ cmd.errors.email }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Emergency Contact</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.emergencyContact}"
                   type="text" v-model="cmd.emergencyContact" placeholder="Emergency Contact"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.emergencyContact"> 
            <span class="help is-danger">{{ cmd.errors.emergencyContact }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Emergency Phone</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.emergencyPhone}"
                   type="number" v-model="cmd.emergencyPhone" placeholder="Emergency Phone"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.emergencyPhone"> 
            <span class="help is-danger">{{ cmd.errors.emergencyPhone }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Address</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.address}"
                   type="text" v-model="cmd.address" placeholder="Address"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.address"> 
            <span class="help is-danger">{{ cmd.errors.address }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Postcode</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.postcode}"
                   type="number" v-model="cmd.postcode" placeholder="Postcode"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.postcode"> 
            <span class="help is-danger">{{ cmd.errors.postcode }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">City</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : cmd.errors.city}"
                   type="text" v-model="cmd.city" placeholder="City"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.city"> 
            <span class="help is-danger">{{ cmd.errors.city }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Entry Date</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <datepicker class="control is-expanded" v-model="cmd.entryDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedEntryDate($event)"
                        @cleared="clearedEntryDate($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="cmd.errors.entryDate"> 
            <span class="help is-danger">{{ cmd.errors.entryDate }}</span> 
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Datepicker from 'vuejs-datepicker'

  export default {
    components: {
      Datepicker
    },
    props: {
      cmd: {
        type: Object,
        required: true
      }
    },
    data () {
      return {
        clearButton: true
      }
    },
    created () {
      this.listReligion = [
        { value: null, text: '-Select Religion-' },
        { value: 'Budha', text: 'Budha' },
        { value: 'Hindu', text: 'Hindu' },
        { value: 'Islam', text: 'Islam' },
        { value: 'Kristen Katolik', text: 'Kristen Katolik' },
        { value: 'Kristen Protestan', text: 'Kristen Protestan' },
        { value: 'Other', text: 'Other' }
      ]
      this.listBloodType = [
        { value: null, text: '-Select Blood Type-' },
        { value: 'A', text: 'A' },
        { value: 'B', text: 'B' },
        { value: 'AB', text: 'AB' },
        { value: 'O', text: 'O' }
      ]
    },

    methods: {
      selectedBirthDate (event) {
        if (event !== null) {
          this.cmd.birthDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedBirthDate (event) {
        this.cmd.birthDateString = null
      },
      selectedEntryDate (event) {
        if (event !== null) {
          this.cmd.entryDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      clearedEntryDate (event) {
        this.cmd.entryDateString = null
      }
    }
  }
</script>
