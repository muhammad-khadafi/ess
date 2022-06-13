<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <div class="column is-full">
            <label class="label">Name<font color="red">*</label>
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
            <label class="label">Reference Type<font color="red">*</label>
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
          <div class="column is-half">
                <button class="button is-info" @click="submit()">
                  <span>Update</span>
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
          gender: null,
          education: null,
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
        { value: 'Inventory Type', text: 'Inventory Type' }
      ]
    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      this.fetchData()
    },
    methods: {
      reset () {
        this.cmd.id = null
        this.cmd.name = null
        this.cmd.gender = null
        this.cmd.education = null
      },
      fetchData () {
        console.log('MASTER REFERENCE DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/master-reference/' + this.$route.params.id)
          .then(function (resp) {
            console.log('BERHASIL FETCH')
            thisVM.cmd = resp.data
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
        this.loading = true
      },
      cancel () {
        console.log('CANCEL')
        if (this.isNew) {
          this.$router.replace('/master-reference')
        } else {
          this.$emit('close')
        }
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
        bus.$emit('SHOW_LOADING')

        let thisVM = this

        let req
        req = axios.put('/master-reference/' + this.$route.params.id, this.cmd)
        req.then(function (resp) {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCES')
          console.log(resp)
          bus.$emit('NOTIFY', 'success', 'Berhasil menyimpan data')

          console.log('$$$ isNEW', thisVM.isNew)

          thisVM.$router.replace('/master-reference')
        })
          .catch(function (error) {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log(error.response)

            _.each(error.response.data.errors, function (value) {
              thisVM.$set(thisVM.errors, value.field, value.message)
            })

            console.log('SELESAI')
            if (thisVM.errors.exception) {
              bus.$emit('NOTIFY', 'danger', 'Gagal update data')
            }
          })
      }
    }
  }
</script>
