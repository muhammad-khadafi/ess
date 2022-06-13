<template>
  <div class="card-content">
<!--    {{form}}-->
<!--    {{form}}-->
    <div class="columns">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Category</label>
<!--          <font color="red">*</font>-->
        </div>
      </div>
      <div class="column is-one-quarter">
        <div class="control is-grouped level-left">
            <span class="select">
            <select v-model="form.category" :class="{'is-danger' : errors.levelId}">
              <option v-for="option in categoryList" :value="option.value">
                {{ option.text }}
              </option>
            </select>
          </span>
          <!--            <basic-select :options="roleList"-->
          <!--                          :selected-option="roleItem"-->
          <!--                          placeholder="Disetujui Oleh"-->
          <!--                          @select="onRoleSelect"-->
          <!--                          :class="{'is-danger' : errors.roleId}">-->
          <!--            </basic-select>-->
        </div>
      </div>
      <div class="column">
        <div class="control" v-if="errors.roleId">
          <span class="help is-danger">{{ errors.roleId }}</span>
        </div>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">  <center>Created</center>Date </label>

        </div>
      </div>
      <div class="column is-3">
        <datepicker v-model="form.createdDate" input-class="input" format="dd-MM-yyyy"
                    todayHighlight= true
                    placeholder="Created Date"
                    @selected="selectedTanggalStartDate($event)"
                    @cleared="clearedTanggalStartDate($event)"
                    :clear-button="clearButton"></datepicker>
      </div>
    </div>

    <div class="columns is-mobile">
      <div class="column is-3">
        <div class="level-right">
          <label class="label">Assigner</label>
        </div>
      </div>
      <div class="column is-3">
      <input class="input" :class="{'is-danger' : errors.information}"
             type="text" v-model="form.information" placeholder="Assigner"/>

      </div>
    </div>
    <div class="columns">
      <div class="column is-half is-offset-one-quarter has-text-centered">
        <button class="button is-info" @click="search()">
                  <span class="icon is-small">
                    <i class="fa fa-search"></i>
                  </span>
          <span>Search</span>
        </button>
      </div>
    </div>
    <div>
      <vuetable ref="vuetable"
                api-url="/letter-list"
                :fields="fields"
                pagination-path="pagination"
                :sort-order="sortOrder"
                :multi-sort="multiSort"
                :per-page="perPage"
                :append-params="form"
                @vuetable:pagination-data="onPaginationData"
      />
      <nav class="columns">
        <div class="column">
          <vuetable-pagination-info ref="paginationInfo"
                                    :pagination-info-template="paginationInfoTemplate"
                                    :pagination-info-no-data-template="paginationInfoNoDataTemplate"/>
        </div>
        <div class="column">
          <vuetable-pagination ref="pagination" @vuetable-pagination:change-page="onChangePage"/>
        </div>
      </nav>

      <div class="columns">
        <div class="column has-text-centered">
          <button class="button is-info" @click="addNew()">
                  <span class="icon is-small">
                    <i class="fa fa-plus"></i>
                  </span>
            <span>Add New</span>
          </button>
        </div>
      </div>

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
  import moment from 'moment'

  import bus from 'src/shared/bus'
  import _ from 'lodash'
  import axios from 'axios'
  import Datepicker from 'vuejs-datepicker'

  let tableColumns = [
    {
      name: '__sequence',
      title: 'No.',
      titleClass: 'center aligned',
      dataClass: 'center aligned'
    },
    {
      name: 'category',
      // sortField: 'category',
      title: 'Category',
      sortField: 'category',
      titleClass: 'center aligned'
    },
    {
      name: 'description',
      //  sortField: 'load',
      title: 'Description',
      sortField: 'description',
      titleClass: 'center aligned'
    },
    {
      name: 'serial_number',
     // sortField: 'projectName',
      title: 'Letter Number',
      sortField: 'serial_number',
      titleClass: 'center aligned'
    },
    {
      name: 'created_date',
     //  sortField: 'Tanggal Pendaftaran',
      title: 'Created Date',
      titleClass: 'center aligned',
      sortField: 'created_date',
      callback: 'toDate'
    },
    {
      name: 'purpose',
     //  sortField: 'load',
      title: 'Send To',
      sortField: 'purpose',
      titleClass: 'center aligned'
    },
    {
      name: 'information',
      //  sortField: 'load',
      title: 'Assigner',
      sortField: 'information',
      titleClass: 'center aligned'
    },
    {
      name: 'ename',
      //  sortField: 'load',
      title: 'Created By',
      sortField: 'information',
      titleClass: 'center aligned'
    }
  ]

  export default {
    components: {
      Vuetable,
      VuetablePagination,
      VuetablePaginationDropdown,
      VuetablePaginationInfo,
      Datepicker
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
        selectedDate: '',
        bootstrapStyling: true,
        openDate: new Date(),
        cmd: {
          roleId: null,
          menuName: null,
          menuId: null
        },
        fields: tableColumns,
        auth: auth,
        form: {
          id: null,
          category: null,
          createdDate: null,
          description: null,
          purpose: null,
          information: null,
//          idUser: auth.getUserInfo().employeeId,
          serialNumber: null,
          idUser: auth.getUserInfo().id,
          ename: null,
          createdDateString: ''
        },
        tempDate: new Date(),
        clearButton: true,
        multiSort: true,
        categoryList: [
          {value: null, text: '--- Choose Category ---'}
        ],
        perPage: 10,
        paginationInfoTemplate: '{from} - {to} dari {total} data',
        paginationInfoNoDataTemplate: '',
        sortOrder: [{
          field: 'id',
          direction: 'desc'
        }],
        errors: {}
      }
    },
    created () {
      this.setTodayDate()
      this.fetchData()
    },
    methods: {
      changeBootstrapStyling: function () {
        this.bootstrapStyling = !this.bootstrapStyling
      },
      changeLanguage: function () {
        this.openDate = new Date(this.openDate.setDate(this.openDate.getDate() + 90))
      },
      setTodayDate () {
        var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
        this.currentMonth = months[this.tempDate.getMonth()] + ' ' + this.tempDate.getFullYear()
        this.form.months = this.tempDate.getMonth() + 1
        this.form.years = this.tempDate.getFullYear()
      },
      fetchData () {
        axios.get('/letter-category').then((resp) => {
          for (var i = 0; i < resp.data.length; i++) {
            this.categoryList.push({value: resp.data[i].category, text: resp.data[i].category})
          }
        })
      },
      selectedTanggalStartDate (event) {
        if (event !== null) {
          this.form.createdDateString = (event.getDate() < 10 ? '0' + event.getDate() : event.getDate()) + '-' + (event.getMonth() + 1 < 10 ? ('0' + (event.getMonth() + 1)) : (event.getMonth() + 1)) + '-' + event.getFullYear()
        }
      },
      toggle () {
        auth.toggle()
        console.log('MAP ROUTER : ', mapRouter)
//      router.replace('/login')
      },
      toDate: function (value) {
        var date

        if (value == null) {
          date = ''
        } else {
          date = moment(value).format('DD-MM-YYYY')
         // date = moment(value).format('YYYY-MM-DD')
        }

        return date
      },
      clearedTanggalStartDate (event) {
        this.form.createdDateString = null
      },
      addNew () {
        this.$router.replace('/surat')
      },
      onLoadSuccess (response) {
        console.log('on LOAD SUCCESS')
        console.log(response)
        // set pagination data to pagination-info component
        this.$refs.paginationInfo.setPaginationData(response.data)
      },
      onLoadError (response) {
        let thisVM = this

        console.log('on LOAD ERROR : ', response)
        if (response.status === 400) {
          bus.$emit('NOTIFY', 'danger', 'Gagal load data')
        } else if (response.status === 422) {
          console.log(response)

          _.each(response.data.errors, function (value) {
            thisVM.$set(thisVM.errors, value.field, value.message)
          })
        }

        this.$refs.vuetable.tableData = null
        this.$refs.paginationInfo.setPaginationData(null)
        this.$refs.pagination.setPaginationData(null)
      },
      onPaginationData (tablePagination) {
        console.log('on PAGINATION DATA')
        console.log(tablePagination)
        this.$refs.paginationInfo.setPaginationData(tablePagination)
        this.$refs.pagination.setPaginationData(tablePagination)
      },
      search () {
        this.errors = {}
        this.$refs.vuetable.refresh()
      },
      onChangePage (page) {
        console.log('on CHANGE PAGE')
        this.$refs.vuetable.changePage(page)
      }
    }
  }
</script>
