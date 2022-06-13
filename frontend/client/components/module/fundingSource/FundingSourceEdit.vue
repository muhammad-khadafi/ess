<template>
  <div class="card-content">
    <div class="columns">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Name<font color="red">*</font></label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-half">
          <input class="input"
                 type="text" v-model="cmd.name" placeholder="Funding Source Name"/>
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
          <label class="label">Is Active</label>
        </div>
      </div>
      <div class="column is-one-third">
        <div class="control is-half">
          <input class="checkbox" :class="{'is-danger' : errors.isActive}"
                 type="checkbox" v-model="cmd.isActive"/>
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.isActive">
          <span class="help is-danger">{{ errors.isActive }}</span>
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
            <span>Save</span>
          </button>&nbsp;
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
        cmd: {
          name: null
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
        let thisVM = this
        axios.get('/funding-source/' + this.$route.params.id)
          .then(function (resp) {
            thisVM.cmd = resp.data
            if (thisVM.cmd.isActive == null) thisVM.cmd.isActive = true
            console.log(thisVM.cmd)
            console.log('END FETCH')
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/funding-source')
      },
      submit () {
        this.validateAll()
        bus.$emit('SHOW_LOADING')
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        axios.post('/funding-source/add', this.cmd)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/funding-source')
            bus.$emit('NOTIFY', 'success', 'Data has been successfully updated')
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
      validateName () {
        if (this.cmd.name === '' || this.cmd.name === null) {
          this.$set(this.errors, 'name', 'The field cannot be empty')
        } else {
          this.errors.name = null
        }
      },
      validateAll () {
        this.validateName()
      }
    }
  }
</script>
