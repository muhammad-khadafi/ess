<template>
  <div>
    <div v-for="item in listGet">
      <organization-chart :datasource='item'></organization-chart>
    </div>
  </div>
</template>
 
<script>
  import OrganizationChart from 'vue-organization-chart'
  import 'vue-organization-chart/dist/orgchart.css'
  import axios from 'axios'

  export default {
    components: {
      OrganizationChart
    },
    data () {
      return {
        ds: {
          id: '1',
          name: 'Lao Lao',
          title: 'general manager sssssssffffffffffffs',
          children: [
            { id: '2', name: 'Bo Miao', title: 'department manager' },
            {
              id: '3',
              name: 'Su Miao',
              title: 'department manager',
              children: [
                { id: '4', name: 'Tie Hua', title: 'senior engineer' },
                {
                  id: '5',
                  name: 'Hei Hei',
                  title: 'senior engineer',
                  children: [
                    { id: '6', name: 'Pang Pang', title: 'engineer' },
                    { id: '7', name: 'Xiang Xiang', title: 'UE engineer' }
                  ]
                }
              ]
            },
            { id: '8', name: 'Hong Miao', title: 'department manager', children: [] },
            { id: '9', name: 'Chun Miao', title: 'department manager' }
          ]
        },
        listGet: []
      }
    },
    created () {
      this.fetchData()
    },
    methods: {
      fetchData () {
        console.log('START FETCH')
        let thisVM = this
        axios.get('/organization-structure/diagram')
        .then((resp) => {
          console.log('BERHASIL FETCH')
          thisVM.listGet = resp.data
        })
        .catch((error) => {
          console.log('GAGAL FETCH')
          console.log(error.response)
          thisVM.$emit('close')
        })
      }
    }
  }
</script> 