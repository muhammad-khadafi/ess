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
            <input class="input" :class="{'is-danger' : errors.name}"
                   type="text" v-model="cmd.name" placeholder="Name"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.name"> 
            <span class="help is-danger">{{ errors.name }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Relation<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.relation}"
                   type="text" v-model="cmd.relation" placeholder="Relation"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.relation"> 
            <span class="help is-danger">{{ errors.relation }}</span> 
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
            <input class="input" :class="{'is-danger' : errors.birthPlace}"
                   type="text" v-model="cmd.birthPlace" placeholder="Birth Place"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.birthPlace"> 
            <span class="help is-danger">{{ errors.birthPlace }}</span> 
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
          <div class="control is-half">
            <datepicker v-model="cmd.birthDate" input-class="input" format="dd-MM-yyyy"
                        placeholder="Start Date"
                        @selected="selectedBirthDate($event)"
                        @cleared="clearedBirthDate($event)"
                        :clear-button="clearButton"></datepicker>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.birthDate"> 
            <span class="help is-danger">{{ errors.birthDate }}</span> 
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Phone Number</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.phoneNumber}"
                   type="number" v-model="cmd.phoneNumber" placeholder="Phone Number"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.phoneNumber">
            <span class="help is-danger">{{ errors.phoneNumber }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control"> 
            <button class="button is-info" @click="addFamily()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Family</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column">
        <table class="vuetable ui blue selectable celled stackable attached table">
          <th>No.</th>
          <th>Name</th>
          <th>Relation</th>
          <th>Birth Place</th>
          <th>Birth Date</th>
          <th>Phone Number</th>
          <tr v-for="(fam, index) in cmd.ListFamily">
            <td>{{ index + 1 }}</td>
            <td>{{ fam.name }}</td>
            <td>{{ fam.relation }}</td>
            <td>{{ fam.birthPlace }}</td>
            <td>{{ fam.birthDateString }}</td>
            <td>{{ fam.phoneNumber }}</td>
            <td><a href="" @click.prevent="deleteFamily(fam)">Hapus</a></td>
          </tr>
        </table>
      </div>
    </div>

    <div class="modal" transition="zoom">
      <div class="modal-background"></div>
      <div class="modal-content">
        <header class="modal-card-head">
          <p class="modal-card-title">Delete Confirmation</p>
          <button class="delete" @click="close"></button>
        </header>
        <section class="modal-card-body">
          <p style="font-size:16px">Are you sure to delete this item?</p>
        </section>
        <footer class="modal-card-foot">
          <div class="column" style="padding:0">
            <div class="level-right">
              <button class="button is-danger" @click="confirm">Delete</button>
              <button class="button" @click="close">Cancel</button>
            </div>
          </div>
        </footer>
      </div>
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import bus from 'src/shared/bus'
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
      isNew: true,
      errors: {},
      clearButton: true,
      confirmDelete: false,
      tempData: null
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('FAMILY FORM CREATED')
  },
  created () {

  },
  methods: {
    close () {
      $('.modal').removeClass('is-active')
    },
    confirm () {
      this.confirmDelete = true
      this.deleteFamily(this.tempData)
      $('.modal').removeClass('is-active')
    },
    reset () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.relation = null
      this.cmd.name = null
      this.cmd.birthDate = null
      this.cmd.birthPlace = null
      this.cmd.phoneNumber = null
    },
    addFamily () {
      this.validateAll()
      console.log('testestes', this.cmd)

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }

      this.cmd.ListFamily.push({
        id: null,
        employeeId: null,
        relation: this.cmd.relation,
        name: this.cmd.name,
        birthDate: this.cmd.birthDate,
        birthDateString: this.cmd.birthDateString,
        birthPlace: this.cmd.birthPlace,
        phoneNumber: this.cmd.phoneNumber
      })
      this.clear()
    },

    validateAll () {
      this.errors = {}
      if (!this.cmd.name) {
        this.$set(this.errors, 'name', 'The field cannot be empty')
      }
      if (!this.cmd.relation) {
        this.$set(this.errors, 'relation', 'The field cannot be empty')
      }
    },
    clear () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.relation = null
      this.cmd.name = null
      this.cmd.birthDate = null
      this.cmd.birthDateString = null
      this.cmd.birthPlace = null
      this.cmd.phoneNumber = null
    },
    deleteFamily (fam) {
      this.tempData = fam
      if (this.confirmDelete === true) {
        _.remove(this.cmd.ListFamily, (delFamily) => {
          return (delFamily.name === fam.name) &&
            (delFamily.relation === fam.relation)
        })
        this.$forceUpdate()
        this.confirmDelete = false
      }
      $('.modal').addClass('is-active')
    },
    selectedBirthDate (event) {
      if (event !== null) {
        this.cmd.birthDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
      }
    },
    clearedBirthDate (event) {
      this.cmd.birthDateString = null
    }
  }
}
</script>
