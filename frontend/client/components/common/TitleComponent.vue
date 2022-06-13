<template>
  <div class="card-header">
    <h1 class="card-header-title">{{ pageTitle + subTitle }}</h1>
  </div>
  <!--<div>-->
    <!--<h1 class="subtitle">{{ pageTitle + subTitle }}</h1>-->
    <!--<hr/>-->
  <!--</div>-->
</template>

<script>
import _ from 'lodash'
import bus from 'src/shared/bus'

export default {
  created () {
    this.setTitle()

    let thisVM = this

    bus.$on('CHANGE_SUBTITLE', function (subTitle) {
      console.log('CHANGE SUBTITLE')
      if (!_.isNil(subTitle)) {
        thisVM.subTitle = ' - ' + subTitle
        document.title += thisVM.subTitle
      }
    })
  },
  watch: {
    '$route' () {
      this.setTitle()
    }
  },
  methods: {
    setTitle () {
      this.subTitle = ''
      if (typeof this.$route.meta.title === 'string') {
        this.pageTitle = this.$route.meta.title
      } else {
        this.pageTitle = ''
        console.error('No title set for path [%s]', this.$route.path)
      }

      document.title = 'ESS System'
      document.title += (this.pageTitle ? ' - ' + this.pageTitle : '')
    }
  },
  data () {
    return {
      pageTitle: '',
      subTitle: ''
    }
  }
}
</script>
