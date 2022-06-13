<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <div class="column is-full">
            <label class="label">Name<font color="red">*</font></label>
          </div>
        </div>
        <div class="control is-grouped">
          <div class="column is-half">
              <input class="input" :class="{'is-danger' : errors.name}"
                     type="text" v-model="cmd.name" placeholder="Name"/>
          </div>
          <div class="control" v-if="errors.name"> 
              <div class="column is-full">
                <span class="help is-danger">{{ errors.name }}</span> 
               </div>
           </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <div class="column is-full">
            <label class="label">Category</label>
          </div>
        </div>
        <div class="control is-grouped">
            <div class="column is-half">
              <input class="input"
                     type="text" v-model="cmd.category" placeholder="Category"/>
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <div class="column is-full">
            <label class="label">Reference Type<font color="red">*</font></label>
          </div>
        </div>
        <div class="control is-grouped">
            <div class="column is-half">
              <select class="select" :class="{'is-danger' : errors.referenceType}" v-model="cmd.referenceType">
                <option v-for="option in listReferenceType" :value="option.value">
                  {{ option.text }}
                </option>
              </select>
            </div>
            <div class="control" v-if="errors.referenceType"> 
              <div class="column is-full">
                <span class="help is-danger">{{ errors.referenceType }}</span> 
              </div>
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <div class="column is-full">
              <button class="button is-info" @click="submit()">
                <span>Save</span>
              </button>
              <button class="button is-warning" @click="cancel()">Back</button>
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

  export default {
    props: {
      id: {
        type: Number
      }
    },
    data () {
      return {
        isNew: true,
        cmd: {
          id: null,
          name: null,
          category: null,
          referenceType: null
        },
        errors: {}
      }
    },
    created () {
      this.listReferenceType = [
        { value: null, text: '- Select Reference Type -' },
        { value: 'Benefit', text: 'Benefit' },
        { value: 'Skillset', text: 'Skillset' },
        { value: 'Supervisor', text: 'Supervisor' },
        { value: 'Document', text: 'Document' },
        { value: 'Project Status', text: 'Project Status' },
        { value: 'Project Role', text: 'Project Role' },
        { value: 'Inventory Type', text: 'Inventory Type' },
        { value: 'Training Category', text: 'Training Category' }
      ]
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
    },
    methods: {
      reset () {
        this.cmd.id = null
        this.cmd.name = null
        this.cmd.category = null
        this.cmd.referenceType = null
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/master-reference')
      },
      submit () {
        this.errors = {}
        if (this.cmd.name === null || this.cmd.name === '') {
          this.$set(this.errors, 'name', 'Tidak boleh kosong')
        }

        if (this.cmd.referenceType === null || this.cmd.referenceType === '') {
          this.$set(this.errors, 'referenceType', 'Tidak boleh kosong')
        }

        for (var field in this.errors) {
          if (this.errors[field] !== null) return
        }
//      let thisVM = this
        bus.$emit('SHOW_LOADING')
        axios.post('/master-reference', this.cmd)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.$router.replace('/master-reference')
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
    }
  }
</script>
