<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update</p>
    </div>
    <div class="modal-card-body">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Skillset<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
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
        <div class="column is-half">
          <div class="control"> 
            <button class="button is-info" @click="submit()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
              <span>Save</span>
            </button>&nbsp;
            <button class="button is-warning" @click="back()">Back</button>
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
  import { Modal } from 'vue-bulma-modal'

  export default {
    components: {
      Modal
    },
    props: {
      cmd: {
        type: Object
      }
    },
    data () {
      return {
        isNew: true,
        errors: {},
        clearButton: true,
        listSkillset: []
      }
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      console.log('SKILLSET EDIT FORM CREATED')
    },
    created () {
      this.fetchData()
    },
    methods: {
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
      close () {
        this.$emit('close')
      },
      back () {
        this.$emit('close')
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        let thisVM = this
        axios.post('/employee-skillset/checkduplicate', this.cmd)
          .then((resp) => {
            if (resp.data === false) {
              axios.post('/employee-skillset/add', this.cmd)
                .then((resp) => {
                  this.$emit('close')
                  bus.$emit('NOTIFY', 'success', 'Data has been updated')
                  bus.$emit('RELOAD_SKILLSET_LIST')
                })
                .catch(function (error) {
                  bus.$emit('HIDE_LOADING')
                  console.log('ERROR : ', error)
                  console.log(error.response)

                  _.each(error.response.data.errors, function (value) {
                    thisVM.$set(this.errors, value.field, value.message)
                  })

                  console.log('SELESAI')
                  if (thisVM.errors.exception) {
                    bus.$emit('NOTIFY', 'danger', 'Gagal menyimpan data')
                    thisVM.$router.replace('/employee/edit/' + thisVM.cmd.employeeId)
                  }
                })
            } else {
              bus.$emit('HIDE_LOADING')
              this.$set(this.errors, 'skillsetId', 'This value is already used')
            }
          })
          .catch((error) => {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log('isi dari error', error.response)
            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })
          })
      },
      validateAll () {
        this.errors = {}
        if (!this.cmd.skillsetId) {
          this.$set(this.errors, 'skillsetId', 'The field cannot be empty')
        }
      }
    }
  }
</script>
