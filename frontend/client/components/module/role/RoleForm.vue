<template>
  <div class="card-content">
      <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Role Code<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <input class="input"
                 type="text" v-model="cmd.kode" placeholder="Role Code"/>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.kode"> 
          <span class="help is-danger">{{ errors.kode }}</span> 
        </div>
        <div class="control" v-if="errors.kodeDuplicate"> 
          <span class="help is-danger">{{ errors.kodeDuplicate }}</span> 
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Role Name<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <input class="input"
                 type="text" v-model="cmd.label" placeholder="Role Name"/>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.label"> 
          <span class="help is-danger">{{ errors.label }}</span> 
        </div>
        <div class="control" v-if="errors.labelDuplicate"> 
          <span class="help is-danger">{{ errors.labelDuplicate }}</span> 
        </div>
      </div>
    </div>
    <div class="columns">
      <div class="column is-2">
        <div class="level-right">
          <label class="label">Note</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-grouped level-left">
          <textarea class="textarea"
                 type="text" v-model="cmd.keterangan" placeholder="Note"/>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.keterangan"> 
          <span class="help is-danger">{{ errors.keterangan }}</span> 
        </div>
      </div>
    </div>
    <div class="control is-horizontal">
      <div class="control-label">
        <label class="label"></label>
      </div>
      <div class="control is-grouped">
        <div class="control"> 
          <button class="button is-info" @click="submit()">
            <span>Save</span>
          </button>
        </div>
        <div class="control">
          <button class="button is-warning" @click="cancel()">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  import _ from 'lodash'
  import bus from 'src/shared/bus'
  import { BasicSelect } from 'vue-search-select'
  import ValidationMixin from '../../mixin/ValidationMixin'

  export default {
    components: {
      BasicSelect
    },
    mixins: [ValidationMixin],
    data () {
      return {
        errorSelect: true,
        kodeDuplicate: false,
        labelDuplicate: false,
        cmd: {
          kode: null,
          label: null,
          keterangan: null
        },
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/role')
      },
      submit () {
        this.validateAll()
        bus.$emit('SHOW_LOADING')
        axios.post('/role/checkduplicatekode', this.cmd)
          .then((resp) => {
            this.kodeDuplicate = resp.data
            this.$set(this.errors, 'kodeDuplicate', null)
            if (this.kodeDuplicate === true) {
              this.$set(this.errors, 'kodeDuplicate', 'Role Code can not be duplicate')
            }
            axios.post('/role/checkduplicatelabel', this.cmd)
              .then((resp) => {
                this.labelDuplicate = resp.data
                this.$set(this.errors, 'labelDuplicate', null)
                if (this.labelDuplicate === true) {
                  this.$set(this.errors, 'labelDuplicate', 'Role Name can not be duplicate')
                }
                for (var field in this.errors) {
                  if (this.errors[field] !== null) {
                    bus.$emit('HIDE_LOADING')
                    return
                  }
                }
                if (this.labelDuplicate === false && this.kodeDuplicate === false) {
                  console.log('masuk')
                  axios.post('/role', this.cmd)
                    .then((resp) => {
                      bus.$emit('HIDE_LOADING')
                      console.log('SUCCESS')
                      console.log(resp)
                      this.$router.replace('/role')
                      bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
                    })
                    .catch((error) => {
                      bus.$emit('HIDE_LOADING')
                      console.log('ERROR : ', error)
                      console.log('isi dari error', error.response)
                      _.each(error.response.data.errors, function (value) {
                        this.$set(this.errors, value.field, value.message)
                      })
                    })
                }
              })
          })
      },
      validateAll () {
        this.validateNotBlank('kode', this.cmd.kode)
        this.validateNotBlank('label', this.cmd.label)
      }
    }
  }
</script>
