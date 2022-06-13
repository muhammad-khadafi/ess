<template>
  <div>
    <div class="box">
      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Level</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-half">
            <input disabled class="input" type="text" v-model="detail.levelName"/>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Rate</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-half">
            <input disabled class="input" type="text" v-model="detail.rate"/>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Start Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-half">
            <input disabled class="input" type="text" v-model="detail.startDateString"/>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">End Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-half">
            <input disabled class="input" type="text" v-model="detail.endDateString"/>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label"></label>
        </div>
        <div class="control is-grouped">
          <div class="control">
            <button class="button is-warning" @click="cancel()">Back</button>
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
          levelName: '',
          rate: null,
          startDate: null,
          endDate: null,
          startDateString: '',
          endDateString: ''
        }
      }
    },
    mounted () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('LEVEL RATE DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/level-rate/view/' + this.$route.params.id)
          .then(function (resp) {
            console.log('BERHASIL FETCH')
            thisVM.detail = resp.data
            thisVM.detail.rate = thisVM.thousandSeparator(thisVM.detail.rate)
          })
          .catch(function (error) {
            console.log('GAGAL FETCH')
            console.log(error.response)
            thisVM.$emit('close')
          })
        this.loading = true
      },
      cancel () {
        this.$router.replace('/level-rate')
      },
      thousandSeparator (amount) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      }
    }
  }
</script>
