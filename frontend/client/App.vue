<template>
  <div id="app">
    <app-header></app-header>

    <div class="columns app-content">
        <app-sidebar></app-sidebar>
        <app-main></app-main>
    </div>

    <app-footer></app-footer>
  </div>
</template>

<script>
import {AppHeader, AppSidebar, AppMain, AppFooter} from 'components/layout/'
import auth from 'src/shared/auth'

export default {
  components: {
    AppHeader,
    AppSidebar,
    AppMain,
    AppFooter
  },
  data () {
    return {
      auth: auth
    }
  },
  created () {
    console.log('APP CREATED')
    console.log(this.auth)
    this.auth.checkAuth()
  }
}
</script>

<style lang="scss">
  @import '~bulma';
  @import '~semantic-ui-css/semantic.css';
  $fa-font-path: '~font-awesome/fonts/';
  @import '~font-awesome/scss/font-awesome';

  .app-content {
    margin: 40px;
  }

  body{
    /*background-color: #6dd5fa;*/
    background: #ffb347;  /* fallback for old browsers */
    background: -webkit-linear-gradient(to right, #ffcc33, #ffb347);  /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(to right, #ffcc33, #ffb347); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
  }
</style>
