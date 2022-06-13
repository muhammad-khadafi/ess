<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <div class="column is-full">
            <label class="label">Name</label>
          </div>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.name"/>
            </div>
          </div>
          <div class="control"> 
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
          <div class="control is-expanded">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.category"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <div class="column is-full">
            <label class="label">Reference Type</label>
          </div>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-half">
              <input disabled class="input" type="text" v-model="detail.referenceType"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <div class="column is-full">
            <label class="label"></label>
          </div>
        </div>
        <div class="control is-grouped">
          <div class="control">
            <div class="column is-half">
              <button class="button is-warning" @click="cancel()">Back</button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    props: {
      id: {
        type: Number
      }
    },
    data () {
      return {
        detail: {
          id: null,
          name: null,
          category: null,
          referenceType: null
        }
      }
    },
    mounted () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('MASTER REFERENCE DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/master-reference/' + this.$route.params.id)
          .then(function (resp) {
            console.log('BERHASIL FETCH')
            thisVM.detail = resp.data
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
        this.loading = true
      },
      cancel () {
        this.$router.replace('/master-reference')
      }
    }
  }
</script>
