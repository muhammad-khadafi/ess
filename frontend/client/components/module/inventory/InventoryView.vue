<template>
    <div>
        <div class="box">
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Type</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.type" disabled/>
                      </div>
                    </div>
                    <div class="control">

                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Name</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.name" disabled/>
                      </div>
                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Specification</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <textarea class="textarea" type="text" v-model="form.specification" disabled/>
                      </div>
                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Location</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.location" disabled/>
                      </div>
                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Purchase Date</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.purchaseDateString" disabled/>
                      </div>
                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Purchase Price</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.purchasePrice" disabled/>
                      </div>
                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Condition</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" v-model="form.condition" disabled/>
                      </div>
                    </div>
                </div>
            </div>
            <div class="control is-horizontal">
                <div class="control-label">
                    <label class="label">Is Assigned</label>
                </div>
                <div class="control is-grouped">
                    <div class="control is-expanded">
                      <div class="column is-one-third">
                        <input class="input" type="text" value="Yes" disabled v-if="form.isAssigned == true"/>
                        <input class="input" type="text" value="No" disabled v-if="form.isAssigned == false"/>
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
      axios.get('/inventory/' + this.$route.params.id)
      .then((resp) => {
        this.form = resp.data
        this.form.purchasePrice = this.thousandSeparator(this.form.purchasePrice)
        console.log('isi dari data ', resp.data)
      })
      bus.$emit('HIDE_LOADING')
    },
    back () {
      this.$router.push('/inventory')
    },
    thousandSeparator (amount) {
      return amount.toString().replace(/\./g, '.').replace(/\B(?=(\d{3})+(?!\d))/g, '.')
    }
  }
}
</script>
