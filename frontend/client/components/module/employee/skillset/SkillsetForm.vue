<template>
  <div>
    <div class="card-content">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Skillset<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
              <span class="select is-fullwidth">
                <select v-model="cmd.skillsetId" :class="{'is-danger' : errors.skillsetId}">
                  <option v-for="option in listSkillset" :value="option.value">
                    {{ option.text }}
                  </option>
                </select>
              </span>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.skillsetId">
            <span class="help is-danger">{{ errors.skillsetId }}</span>
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
            <button class="button is-info" @click="add()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Add Skillset</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="columns">
      <div class="column">
        <table class="vuetable ui blue selectable celled stackable attached table">
          <th>No.</th>
          <th>Skillset</th>
          <tr v-for="(skillset, index) in cmd.ListSkillset">
            <td>{{ index + 1 }}</td>
            <td>{{ skillset.skillsetName }}</td>
            <td><a href="" @click.prevent="deleteSkillset(skillset)">Hapus</a></td>
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
      listSkillset: [],
      confirmDelete: false,
      tempData: null
    }
  },
  mounted () {
    // fetch the data when the view is created and the data is
    // already being observed
    console.log('SKILLSET FORM CREATED')
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
      this.deleteSkillset(this.tempData)
      $('.modal').removeClass('is-active')
    },
    fetchData () {
      console.log('START FETCH')
      axios.all([
        axios.get('/skillset/all')
      ]).then(axios.spread((listSkillset) => {
        this.listSkillset.push({value: null, text: 'Select the following choices'})
        for (let skillset of listSkillset.data) {
          this.listSkillset.push({value: skillset.id, text: skillset.name})
        }
      }))
    },
    reset () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.skillsetId = null
      this.cmd.skillsetName = null
    },
    add () {
      this.validateAll()

      for (var field in this.errors) {
        if (this.errors[field] !== null) {
          bus.$emit('HIDE_LOADING')
          return
        }
      }

      axios.get('/master-reference/' + this.cmd.skillsetId)
        .then((resp) => {
          this.cmd.skillsetName = resp.data.name
          this.cmd.ListSkillset.push({
            id: null,
            employeeId: null,
            skillsetId: resp.data.id,
            skillsetName: resp.data.name
          })
        })

      this.clear()
    },

    validateAll () {
      this.errors = {}
      if (!this.cmd.skillsetId) {
        this.$set(this.errors, 'skillsetId', 'The field cannot be empty')
      } else {
        this.cmd.ListSkillset.forEach((value) => {
          if (this.cmd.skillsetId === value.skillsetId) {
            this.$set(this.errors, 'skillsetId', 'This value is already used')
          }
        })
      }
    },
    clear () {
      this.cmd.id = null
      this.cmd.employeeId = null
      this.cmd.skillsetId = null
      this.cmd.skillsetName = null
    },
    deleteSkillset (skillset) {
      this.tempData = skillset
      if (this.confirmDelete === true) {
        _.remove(this.cmd.ListSkillset, (delSkillset) => {
          return (delSkillset.skillsetId === skillset.skillsetId)
        })
        this.$forceUpdate()
        this.confirmDelete = false
      }
      $('.modal').addClass('is-active')
    }
  }
}
</script>
