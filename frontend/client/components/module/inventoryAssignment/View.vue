<template>
    <div>
        <div class="box">
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Employee Name</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.employeeName" disabled/>
                      </div>
                    </div>
                    <div class="control">

                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Inventory Name</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.inventoryName" disabled/>
                      </div>
                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Start Date</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.startDateString" disabled/>
                      </div>
                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">End Date</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.endDateString" disabled/>
                      </div>
                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label"></label>
                </div>
                <div class="control is-grouped">
                    <div class="control">

                    </div>
                    <div class="ccontrol">
                        <button class="button is-warning" @click="back()">Back</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios'
import bus from 'src/shared/bus'

export default {
  data () {
    return {
      form: {}
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      bus.$emit('SHOW_LOADING')
      console.log('isi dari route params', this.$route.params)
      axios.get('/inventory-assignment/' + this.$route.params.id)
      .then((resp) => {
        this.form = resp.data
        console.log('isi dari data ', resp.data)
      })
      bus.$emit('HIDE_LOADING')
    },
    back () {
      this.$router.push('/inventory-assignment')
    }
  }
}
</script>
