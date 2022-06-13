<template>
  <div>

  <div class="box">

    <div class="columns">
      <div class="column">

          <div class="control is-horizontal">
            <div class="control-label">
              <label class="label">Kode Institusi</label>
            </div>
            <div class="control is-grouped">
              <div class="control is-expanded">
                <input class="input" :class="{'is-danger' : errors.kodeInstitusi}"
                       type="text" v-model="form.kodeInstitusi" placeholder="Kode Institusi"/>
              </div>
              <div class="control" v-if="errors.kodeInstitusi"> 
                <span class="help is-danger">{{ errors.kodeInstitusi }}</span> 
              </div>
            </div>
          </div>

          <div class="control is-horizontal">
            <div class="control-label">
              <label class="label">Nama</label>
            </div>
            <div class="control is-grouped">
              <div class="control is-expanded">
                <input class="input" :class="{'is-danger' : errors.nama}"
                       type="text" v-model="form.nama" placeholder="Nama"/>
              </div>
              <div class="control" v-if="errors.nama"> 
                <span class="help is-danger">{{ errors.nama }}</span> 
              </div>
            </div>
          </div>
        </div>

        <div class="column">
          <div class="control is-horizontal">
            <div class="control-label">
              <label class="label">Nama Singkat</label>
            </div>
            <div class="control is-grouped">
              <div class="control is-expanded">
                <input class="input" :class="{'is-danger' : errors.namaSingkat}"
                       type="text" v-model="form.namaSingkat" placeholder="Kode Institusi"/>
              </div>
              <div class="control" v-if="errors.namaSingkat"> 
                <span class="help is-danger">{{ errors.namaSingkat }}</span> 
              </div>
            </div>
          </div>

          <div class="control is-horizontal">
            <div class="control-label">
              <label class="label">Aktif ?</label>
            </div>
            <div class="control is-grouped">
              <div class="control is-expanded">

                <select v-model="form.flagAktif">
                  <option v-for="option in listFlagAktif" :value="option.value">
                    {{ option.text }}
                  </option>
                </select>

              </div>
              <div class="control" v-if="errors.flagAktif"> 
                <span class="help is-danger">{{ errors.flagAktif }}</span> 
              </div>
            </div>
          </div>
        </div>

      </div>

      <div class="columns">
        <div class="column is-half is-offset-one-quarter has-text-centered">
            <button class="button is-info" @click="search()">
                  <span class="icon is-small">
                    <i class="fa fa-search"></i>
                  </span>
              <span>Cari</span>
            </button>
        </div>
      </div>
    </div>

  <div>
      <vuetable ref="vuetable"
                api-url="/institusi/table"
                :fields="fields"
                pagination-path="pagination"
                :sort-order="sortOrder"
                :multi-sort="multiSort"
                :per-page="perPage"
                :append-params="form"
                @vuetable:pagination-data="onPaginationData"
                @vuetable:load-success="onLoadSuccess"
                @vuetable:load-error="onLoadError"
                @vuetable:loading="showLoader"
                @vuetable:loaded="hideLoader"
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
  </div><!-- content -->

  </div>
</template>

<script>
import Vue from 'vue'
import Vuetable from 'vuetable-2/src/components/Vuetable.vue'
import VuetablePagination from 'vuetable-2/src/components/VuetablePagination.vue'
import VuetablePaginationDropdown from 'vuetable-2/src/components/VuetablePaginationDropdown.vue'
import VuetablePaginationInfo from 'vuetable-2/src/components/VuetablePaginationInfo.vue'
import InstitusiOperation from './InstitusiOperation'
import _ from 'lodash'
import bus from 'src/shared/bus'

Vue.component('institusi-operation', InstitusiOperation)

let tableColumns = [
  {
    name: '__sequence',
    title: 'No.',
    titleClass: 'center aligned',
    dataClass: 'right aligned'
  },
  {
    name: 'kodeInstitusi',
    sortField: 'kodeInstitusi',
    title: 'Kode Institusi',
    titleClass: 'center aligned'
  },
  {
    name: 'nama',
    sortField: 'nama',
    title: 'Nama',
    titleClass: 'center aligned'
  },
  {
    name: 'namaSingkat',
    sortField: 'namaSingkat',
    title: 'Nama Singkat',
    titleClass: 'center aligned'
  },
  {
    name: 'flagAktif',
    sortField: 'flagAktif',
    title: 'Aktif ?',
    callback: 'formatFlagAktif',
    titleClass: 'center aligned',
    dataClass: 'center aligned'
  },
  {
    name: '__component:institusi-operation',
    dataClass: 'center aligned'
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
      form: {
        kodeInstitusi: null,
        nama: null,
        namaSingkat: null,
        flagAktif: null
      },
      listFlagAktif: [
        { value: null, text: '--- Semua Status ---' },
        { value: true, text: 'Aktif' },
        { value: false, text: 'Tidak Aktif' }
      ],
      errors: {},
      fields: tableColumns,
      sortOrder: [{
        field: 'nama',
        direction: 'asc'
      }],
      multiSort: true,
      perPage: 10,
      paginationInfoTemplate: '{from} - {to} dari {total} data',
      paginationInfoNoDataTemplate: ''
    }
  },
  methods: {
    search () {
      this.errors = {}
      this.$refs.vuetable.refresh()
    },
    showLoader () {
      console.log('on LOADING')
      bus.$emit('SHOW_LOADING')
    },
    hideLoader () {
      console.log('on LOADED')
      bus.$emit('HIDE_LOADING')
    },
    formatFlagAktif (value) {
      return value === true ? 'Aktif' : 'Tidak Aktif'
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
    onChangePage (page) {
      console.log('on CHANGE PAGE')
      this.$refs.vuetable.changePage(page)
    },
    onClick (action, data) {
      console.log('actions: on-click PARENT', data.name)
    }
  }
}
</script>
