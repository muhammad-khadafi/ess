<template>
  <div>
    <div class="box">

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Project Contract Number</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.projectContractName"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Term</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.term"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Percentage</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.percentage"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Value</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.value"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Due Date</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.dueDateString"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Is Paid ?</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="checkbox" type="checkbox" v-model="cmd.paid"/>
            </div>
          </div>
        </div>
      </div>

      <div class="control is-horizontal">
        <div class="control-label">
          <label class="label">Notes</label>
        </div>
        <div class="control is-grouped">
          <div class="control is-expanded">
            <div class="column is-one-third">
            <input disabled class="input" type="text" v-model="cmd.notes"/>
            </div>
          </div>
          <div class="control"> 
            </div>
        </div>
      </div>
      <!--{{cmd}}-->
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
  import Datepicker from 'vuejs-datepicker'

  export default {
    props: {
      id: {
        type: Number
      }
    },
    components: {
      Datepicker
    },
    data () {
      return {
        isNew: true,
        cmd: {
          projectContractName: null,
          contractId: null,
          term: null,
          percentage: null,
          value: null,
          dueDate: null,
          dueDateString: null,
          paid: null,
          notes: null
        },
        errors: {}
      }
    },
    created () {

    },
    mounted () {
      // fetch the data when the view is created and the data is
      // already being observed
      this.fetchData()
    },
    methods: {
      reset () {
        this.cmd.id = null
        this.cmd.projectName = null
        this.cmd.contractNumber = null
        this.cmd.contractDateString = null
        this.cmd.signee1 = null
        this.cmd.signee2 = null
      },
      fetchData () {
        console.log('PROJECT DETAIL FETCH DATA')
        let thisVM = this
        axios.get('/projectPayment/getDetail/' + this.$route.params.id)
          .then(function (resp) {
            console.log('BERHASIL FETCH')
            thisVM.cmd = resp.data
            thisVM.cmd.value = thisVM.thousandSeparator(thisVM.cmd.value)
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
          this.$router.replace('/projectPayment')
        } else {
          this.$emit('close')
        }
      },
      thousandSeparator (amount) {
        return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      }
    }
  }
</script>
