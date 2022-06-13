<template>
  <div>
    <div class="box">
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label"> Organization Structure Name </label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.name}"
                   type="text" v-model="cmd.name" placeholder="Organization Structure Name"/>
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
            <label class="label">Organization</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="organizationList"
                          :selected-option="organizationItem"
                          placeholder="Organization"
                          @select="onOrganizationSelect"
                          :class="{'is-danger' : errors.organizationId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.organizationId">
            <span class="help is-danger">{{ errors.organizationId }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Job Position</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="positionList"
                          :selected-option="positionItem"
                          placeholder="Job Position"
                          @select="onPositionSelect"
                          :class="{'is-danger' : errors.positionId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.positionId">
            <span class="help is-danger">{{ errors.positionId }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Parent Organization</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <basic-select :options="parentList"
                          :selected-option="parentItem"
                          placeholder="Parent Organization"
                          @select="onParentSelect"
                          :class="{'is-danger' : errors.parentId}">
            </basic-select>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.parentId">
            <span class="help is-danger">{{ errors.parentId }}</span>
          </div>
        </div>
      </div>
      <div class="columns">
        <div class="column is-3">
          <div class="level-right">
            <label class="label">Is Active ?</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
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
            <label class="label">Notes</label>
          </div>
        </div>
        <div class="column is-one-third">
          <div class="control is-grouped level-left">
            <input class="input" :class="{'is-danger' : errors.notes}"
                   type="text" v-model="cmd.notes" placeholder="Notes"/>
          </div>
        </div>
        <div class="column">
          <div class="control" v-if="errors.notes">
            <span class="help is-danger">{{ errors.notes }}</span>
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
        cmd: {
          name: null,
          parentId: null,
          organizationId: null,
          positionId: null,
          isActive: true,
          notes: null
        },
        parentList: [],
        organizationList: [],
        positionList: [],
        parentItem: {
          value: '',
          text: ''
        },
        organizationItem: {
          value: '',
          text: ''
        },
        positionItem: {
          value: '',
          text: ''
        },
        jobPosName: null,
        orgName: null,
        errors: {}
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        axios.all([
          axios.get('/organization-structure/structurelist'),
          axios.get('/organization/organizationlist'),
          axios.get('/job-position/positionlist')
        ]).then(axios.spread((parentList, organizationList, positionList) => {
          for (let parent of parentList.data) {
            this.parentList.push({value: parent.id, text: parent.name})
          }
          for (let organization of organizationList.data) {
            this.organizationList.push({value: organization.id, text: organization.name})
          }
          for (let position of positionList.data) {
            this.positionList.push({value: position.id, text: position.name})
          }
        }))
      },
      onParentSelect (item) {
        this.errorSelect = false
        this.parentItem = item
        this.cmd.parentId = item.value
      },
      onOrganizationSelect (item) {
        this.errorSelect = false
        this.organizationItem = item
        this.cmd.organizationId = item.value
        // this.orgName = item.text
        // this.cmd.name = this.jobPosName + ' ' + item.text
      },
      onPositionSelect (item) {
        this.errorSelect = false
        this.positionItem = item
        this.cmd.positionId = item.value
        // this.jobPosName = item.text
        // this.cmd.name = item.text + ' ' + this.orgName
      },
      cancel () {
        console.log('CANCEL')
        this.$router.replace('/organization-structure')
      },
      submit () {
        this.validateAll()
        for (var field in this.errors) {
          if (this.errors[field] !== null) {
            bus.$emit('HIDE_LOADING')
            return
          }
        }
        let thisVM = this
        let req
        req = axios.post('/organization-structure', this.cmd)
        req.then(function (resp) {
          bus.$emit('HIDE_LOADING')
          console.log('SUCCES')
          console.log(resp)
          thisVM.$router.replace('/organization-structure')
          bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
        })
          .catch(function (error) {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log(error.response)

            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })

            console.log('SELESAI')
            if (this.errors.exception) {
              this.bus.$emit('NOTIFY', 'danger', 'Failed to save data')

              if (this.isNew) {
                this.$router.replace('/organization-structure')
              } else {
                this.$emit('close')
              }
            }
          })
      },
      validateAll () {
        this.validateNotBlank('organizationId', this.cmd.organizationId)
        this.validateNotBlank('positionId', this.cmd.positionId)
        this.validatePersentage('name', this.cmd.name)
      }
    }
  }
</script>
