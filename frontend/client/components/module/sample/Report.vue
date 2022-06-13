<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Name*</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input class="input"
                   type="text" v-model="form.name" placeholder="Name"/>
          </div>
          <div class="control" v-if="errors.name"> 
              <span class="help is-danger">{{ errors.name }}</span> 
           </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">NIK</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <input class="input"
                   type="text" v-model="form.nik" placeholder="NIK"/>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
         <div class="control"> 
              <button class="button is-info" @click="generate()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
            <span>Generate</span>
          </button>
          </div>

        </div>
        <div class="control"> 
              <button class="button is-info" @click="download()">
                <span class="icon is-small">
                  <i class="fa fa-check"></i>
                </span>
          <span>Download</span>
        </button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import axios from 'axios'
//  import _ from 'lodash'
//  import bus from 'src/shared/bus'

  export default {
    props: {
      id: {
        type: Number
      }
    },
    data () {
      return {
        form: {
          name: null,
          nik: null
        },
        errors: {}
      }
    },
    created () {
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
      generate () {
        axios.post('/report/generateticket', this.form)
          .then((resp) => {
            console.log('BERHASIL : ', resp)
            let ticket = resp.data.ticket
            let root = window.location.protocol + '//' + window.location.host
            console.log('Ready to generate')
            window.open(root + '/api/download/sample/cetakreport/' +
              this.form.name + '/' + this.form.nik + '?ticket=' + ticket)
          })
          .catch((error) => {
            console.log('GAGAL : ', error)
          })
      },
      download () {
        axios.post('/report/download', this.form)
          .then((resp) => {
            console.log('BERHASIL : ', resp)
          })
          .catch((error) => {
            console.log('GAGAL : ', error)
          })
      }
    }
  }
</script>
