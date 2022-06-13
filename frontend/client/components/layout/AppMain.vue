<template>
  <div class="column" id="app-main-layout">
    <notification />

    <div class="card">
        <div v-show="loading" id="overlay">
          <img id="loadingIcon" src="~assets/squares.svg"/>
        </div>

        <title-component></title-component>
        <router-view></router-view>
    </div>
  </div>
</template>

<script>
import TitleComponent from 'components/common/TitleComponent'
import Notification from 'components/common/Notification'
import bus from 'src/shared/bus'

export default {
  components: {
    TitleComponent,
    Notification
  },
  data () {
    return {
      loading: false
    }
  },
  mounted () {
    let thisVM = this

    bus.$on('SHOW_LOADING', function () {
      thisVM.loading = true
    })
    bus.$on('HIDE_LOADING', function () {
      thisVM.loading = false
    })
  }
}
</script>

<style lang="scss">
  #overlay {
    position: fixed;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background: #000;
    opacity: 0.2;
    filter: alpha(opacity=50);
    z-index:1000;
  }
  .column{
    border-top-left-radius: 10px;
  }

  #loadingIcon {
    width: 125px;
    height: 125px;
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -28px 0 0 -25px;
  }

  .card {
    width: 100%;
  }

  h1 {
    font-size: 1.5em;
  }

  .card-header-title {
    background-color: #fffdb4;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
  }

  .card{
    border-radius: 10px;
  }
</style>
