<template>
  <div class="card-content">
    <br>
    <br>
    <p v-if="auth.isAuthenticated">
      Hi <b>{{auth.getUserInfo().username}}</b>, This is your current assignment ;
    </p>
    <br>
    <div>
      <vuetable ref="vuetable"
                api-url="/project-assignment/table-for-home/"
                :fields="fields"
                pagination-path="pagination"
                :sort-order="sortOrder"
                :multi-sort="multiSort"
                :per-page="perPage"
                :append-params="form"
      />
      <!--<nav class="columns">
        <div class="column">
          <vuetable-pagination-info ref="paginationInfo"
                                    :pagination-info-template="paginationInfoTemplate"
                                    :pagination-info-no-data-template="paginationInfoNoDataTemplate"/>
        </div>
        <div class="column">
          <vuetable-pagination ref="pagination" @vuetable-pagination:change-page="onChangePage"/>
        </div>
      </nav>-->
    </div>
    <br>
    <br>
    <br>
    <p v-if="auth.isAuthenticated">
      List of past assignment ;
    </p>
    <br>
    <div>
      <vuetable ref="vuetable"
                api-url="/project-assignment/table-for-home-prev/"
                :fields="fields"
                pagination-path="pagination"
                :sort-order="sortOrder"
                :multi-sort="multiSort"
                :per-page="perPage"
                :append-params="form"
      />
    </div>
    <!--{{form}}-->
    <!--<button @click="toggle()">Toggle</button>-->
  </diV>
</template>

<script>
  import auth from 'src/shared/auth'
  import { mapRouter } from 'src/shared/router'
  import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
  import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
  import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
  import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'

  import bus from 'src/shared/bus'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'right aligned'
    },
    {
      name: 'startDateString',
      sortField: 'startDate',
      title: 'Date',
      titleClass: 'center aligned'
    },
    {
      name: 'projectName',
      sortField: 'projectName',
      title: 'Project',
      titleClass: 'center aligned'
    },
    {
      name: 'roleName',
      sortField: 'roleName',
      title: 'Role',
      titleClass: 'center aligned'
    },
    {
      name: 'loadString',
      sortField: 'load',
      title: 'Load',
      titleClass: 'center aligned'
    }
  ]

  export default {
    components: {
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo
    },
    mounted () {
      console.log('Institusi List MOUNTED')
      let thisVM = this
      bus.$on('RELOAD_INSTITUSI_LIST', function () {
        console.log('Institusi List RELOAD_INSTITUSI_LIST', thisVM.$refs)
        console.log('Institusi List RELOAD_INSTITUSI_LIST is kosong : ', thisVM.$refs.vuetable)

        if (thisVM.$refs.vuetable !== undefined) {
          thisVM.$refs.vuetable.reload()
        }
      })
    },
    data () {
      return {
        fields: tableColumns,
        auth: auth,
        form: {
          employeeId: null,
          subProAssignmentId: null,
          date: new Date(),
          activityDetail: null,
          thirdParty: null,
          duration: 8,
          overtime: 0,
          spvAssigned: null,
          projectAssignId: null,
          projectId: null,
          subProId: null,
          months: null,
          years: null
        },
        tempDate: new Date(),
        sortOrder: [{
          field: 'startDate',
          direction: 'desc'
        }]
      }
    },
    created () {
      this.form.employeeId = auth.getUserInfo().employeeId
      this.form.months = this.tempDate.getMonth() + 1
      this.form.years = this.tempDate.getFullYear()
    },
    methods: {
      toggle () {
        auth.toggle()
        console.log('MAP ROUTER : ', mapRouter)
//      router.replace('/login')
      }
    }
  }
</script>
