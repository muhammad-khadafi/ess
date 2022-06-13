<template>
  <div @close="close">
    <div class="modal-card-head">
      <p class="modal-card-title">Update</p>
    </div>
    <div class="modal-card-body">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Benefit<font color="red">*</font></label>
          </div>
        </div>
        <div class="column is-half">
          <div class="control is-grouped level-left">
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
          <div class="level-right">
            <label class="label"></label>
          </div>
        </div>
        <div class="column is-one-third">
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
        listBenefit: []
      }
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      console.log('BENEFIT EDIT FORM CREATED')
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        axios.all([
          axios.get('/benefit/all')
        ]).then(axios.spread((listBenefit) => {
          for (let benefit of listBenefit.data) {
            this.listBenefit.push({value: benefit.id, text: benefit.name})
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
        axios.post('/employee-benefit/checkduplicate', this.cmd)
          .then((resp) => {
            if (resp.data === false) {
              axios.post('/employee-benefit/add', this.cmd)
                .then((resp) => {
                  this.$emit('close')
                  bus.$emit('NOTIFY', 'success', 'Data has been updated')
                  bus.$emit('RELOAD_BENEFIT_LIST')
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
              this.$set(this.errors, 'benefitId', 'Benefit cannot be added more than one')
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
        if (!this.cmd.benefitId) {
          this.$set(this.errors, 'benefitId', 'The field cannot be empty')
        }
      }
    }
  }
</script>
