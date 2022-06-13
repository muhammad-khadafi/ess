/**
 * Created by fahri on 3/27/17.
 */

import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

Vue.http.interceptors.push(function (request, next) {
  let idToken = localStorage.getItem('id_token')
  console.log('ID-TOKEN : ', idToken)
  if (idToken === null) {
    delete request.headers.Authorization
  } else {
    request.headers.set('Authorization', 'Bearer ' + idToken)
  }
  request.headers.set('Accept', 'application/json')

  console.log('$$$ VueResource INTERCEPT REQUEST : ', request)

  // continue to next interceptor
  next(function (response) {
    console.log('$$$ VueResource INTERCEPT RESPONSE : ', response)
  })
})
