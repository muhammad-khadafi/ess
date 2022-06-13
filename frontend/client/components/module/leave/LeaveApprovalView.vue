<template>
  <div @close="close">
    <div class="modal-card">
      <header class="modal-card-head">
        <p class="modal-card-title">View Leave Detail</p>
        <button class="delete" @click="close"></button>
      </header>
      <section class="modal-card-body">
        <div class="columns">
          <div class="column is-one-quarter">
            Employee Name
          </div>
          <div class="column">
            : 
          </div>
          <div class="column is-two-thirds">
            {{detail.employeeName}}
          </div>
        </div>
        <div class="columns">
          <div class="column is-one-quarter">
            Leave Type
          </div>
          <div class="column">
            : 
          </div>
          <div class="column is-two-thirds">
            {{detail.leaveTypeName}}
          </div>
        </div>
        <div class="columns">
          <div class="column is-one-quarter">
            Start Date
          </div>
          <div class="column">
            : 
          </div>
          <div class="column is-two-thirds">
            {{detail.startDateStr}}
          </div>
        </div>
        <div class="columns">
          <div class="column is-one-quarter">
            End Date
          </div>
          <div class="column">
            : 
          </div>
          <div class="column is-two-thirds">
           {{detail.endDateStr}}
          </div>
        </div>
        <div class="columns">
          <div class="column is-one-quarter">
            Number of Days
          </div>
          <div class="column">
            : 
          </div>
          <div class="column is-two-thirds">
            {{detail.numOfDays}} Days
          </div>
        </div>
        <div class="columns">
          <div class="column is-one-quarter">
            Leave Date Detail
          </div>
          <div class="column">
            : 
          </div>
          <div class="column is-two-thirds">
            {{detail.leaveDateDetail}}
          </div>
        </div>
        <div class="columns">
          <div class="column is-one-quarter">
            Reason
          </div>
          <div class="column">
            : 
          </div>
          <div class="column is-two-thirds">
            {{detail.reason}}
          </div>
        </div>
        <div class="columns">
          <div class="column is-one-quarter">
            Manager
          </div>
          <div class="column">
            : 
          </div>
          <div class="column is-two-thirds">
            {{detail.managerName}}
          </div>
        </div>
        
      </section>
      <footer class="modal-card-foot">
        <div class="column" style="padding:0">
          <div class="level-right">
            <a class="button is-warning" @click="close()">Back to List</a>
          </div>
        </div>
      </footer>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    employeeName: String,
    leaveTypeName: String,
    startDateStr: String,
    endDateStr: String,
    status: String,
    leaveDateDetail: String,
    managerName: String,
    deputyDirectorName: String,
    numOfDays: Number,
    reason: String
  },
  data () {
    return {
      detail: {
        employeeName: null,
        leaveTypeName: null,
        startDateStr: null,
        endDateStr: null,
        status: null,
        leaveDateDetail: null,
        managerName: null,
        deputyDirectorName: null,
        numOfDays: null,
        reason: null
      }
    }
  },
  mounted () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      console.log('VIEW FETCH DATA, name : ' + this.employeeName)
      this.detail.employeeName = this.employeeName
      this.detail.leaveTypeName = this.leaveTypeName
      this.detail.startDateStr = this.startDateStr
      this.detail.endDateStr = this.endDateStr
      this.detail.status = this.status
      this.detail.leaveDateDetail = this.leaveDateDetail
      this.detail.managerName = this.managerName
      this.detail.deputyDirectorName = this.deputyDirectorName
      this.detail.numOfDays = this.numOfDays
      this.detail.reason = this.reason
    },
    close () {
      this.$emit('close')
    }
  }
}
</script>
