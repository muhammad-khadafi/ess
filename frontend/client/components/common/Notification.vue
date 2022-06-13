<template>
  <article v-show="show" class="message" :class="[classType]">
    <div class="message-header">
      <button class="delete touchable" @click="clickClose()"></button>
      <p>{{ title }}</p>
    </div>
    <div class="message-body">
      {{ content }}
    </div>
  </article>
</template>

<script>
import bus from 'src/shared/bus'

export default {
  data () {
    return {
      show: false,
      classType: '',
      title: ' ', // <-- ini bukan space biasa yah, ini space pake unicode
      content: ''
    }
  },
  mounted () {
    console.log('[Notification] mounted')
    let thisVM = this

    bus.$on('NOTIFY', function (type, msg) {
      console.log('Notif 2 param : ', type, msg)

      thisVM.$nextTick(function () {
        console.log('nextTick : Notif 2 param')
        thisVM.show = true
        thisVM.classType = 'is-' + type
        // thisVM.title = title
        thisVM.content = msg

        setTimeout(
          function () {
            thisVM.show = false
          }, 10000)

        document.getElementById('app-main-layout').scrollIntoView()
      })
    })

    bus.$on('CLOSE_NOTIFICATION', function () {
      thisVM.show = false
    })
  },
  watch: {
    '$route' () {
      console.log('Notification WATCH ROUTE')
      this.show = false

      this.$nextTick(function () {
        console.log('nextTick : Notification WATCH ROUTE')
      })
    }
  },
  methods: {
    clickClose () {
      this.show = false
    }
  }
}
</script>

<style lang="scss">
  .message .delete {
    float: right;
  }
</style>

