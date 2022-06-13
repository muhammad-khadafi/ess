/**
 * Created by fahri on 1/6/17.
 */

import 'babel-polyfill'
import Vue from 'vue'
import App from './App.vue'
import router from 'src/shared/router'

require('./setup-vue-resource')
require('./setup-axios')

new Vue({ // eslint-disable-line no-new
  el: '#app',
  router: router,
  ...App
})
