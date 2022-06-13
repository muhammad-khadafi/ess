<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Benefit<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-expanded">
              <div class="select is-fullwidth">
                <select v-model="cmd.benefitId" :class="{'is-danger' : errors.benefitId}">
                  <option v-for="option in listBenefit" :value="option.value">
                    {{ option.text }}
                  </option>
                </select>
              </div>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.benefitId">
            <span class="help is-danger">{{ errors.benefitId }}</span>
          </div>
        </div>
      </div>

      <div class="columns">
        <div class="column is-3">
          <label class="label"></label>
        </div>
        <div class="column is-one-third">
          <div class="control"> 
            <button class="button is-info" @click="addBenefit()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Benefit</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column">
        <table class="vuetable ui blue selectable celled stackable attached table">
          <th>No.</th>
          <th>Benefit</th>
          <tr v-for="(ben, index) in cmd.ListBenefit">
            <td>{{ index + 1 }}</td>
            <td>{{ ben.benefitName }}</td>
            <td><a href="" @click.prevent="deleteBenefit(ben)">Hapus</a></td>
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
import axios from 'axios'

export default {
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
      listBenefit: [],
      confirmDelete: false,
      tempData: null
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('BENEFIT FORM CREATED')
  },
  created () {
    this.fetchData()
  },
  methods: {
    close () {
      $('.modal').removeClass('is-active')
    },
    confirm () {
      this.confirmDelete = true
      this.deleteBenefit(this.tempData)
      $('.modal').removeClass('is-active')
    },
    fetchData () {
      console.log('START FETCH')
      axios.all([
        axios.get('/benefit/all')
      ]).then(axios.spread((listBenefit) => {
        this.listBenefit.push({value: null, text: 'Select the following choices'})
        for (let benefit of listBenefit.data) {
          this.listBenefit.push({value: benefit.id, text: benefit.name})
        }
      }))
    },
    reset () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.benefitId = null
      this.cmd.benefitName = null
    },
    addBenefit () {
      this.validateAll()

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }

      axios.get('/master-reference/' + this.cmd.benefitId)
        .then((resp) => {
          this.cmd.benefitName = resp.data.name
          this.cmd.ListBenefit.push({
            id: null,
            employeeId: null,
            benefitId: resp.data.id,
            benefitName: resp.data.name
          })
        })

      this.clear()
    },

    validateAll () {
      this.errors = {}
      if (!this.cmd.benefitId) {
        this.$set(this.errors, 'benefitId', 'The field cannot be empty')
      } else {
        this.cmd.ListBenefit.forEach((value) => {
          if (this.cmd.benefitId === value.benefitId) {
            this.$set(this.errors, 'benefitId', 'This value is already used')
          }
        })
      }
    },
    clear () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.benefitId = null
      this.cmd.benefitName = null
    },
    deleteBenefit (ben) {
      this.tempData = ben
      if (this.confirmDelete === true) {
        _.remove(this.cmd.ListBenefit, (delBenefit) => {
          return (delBenefit.benefitId === ben.benefitId)
        })
        this.$forceUpdate()
        this.confirmDelete = false
      }
      $('.modal').addClass('is-active')
    }
  }
}
</script>
