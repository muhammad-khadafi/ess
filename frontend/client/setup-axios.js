/**
 * Created by fahri on 3/27/17.
 */

import axios from 'axios'

axios.defaults.baseURL = '/api/'
// axios.defaults.headers.common['Authorization'] = auth.getAuthHeader()
// axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('id_token')
axios.defaults.headers.post['Content-Type'] = 'application/json'
axios.defaults.headers.put['Content-Type'] = 'application/json'

// Add a request interceptor
axios.interceptors.request.use(function (config) {
  let idToken = localStorage.getItem('id_token')
  console.log('ID-TOKEN : ', idToken)
  if (idToken === null) {
    delete config.headers.Authorization
  } else {
    config.headers['Authorization'] = 'Bearer ' + idToken
  }

  console.log('$$$ Axios INTERCEPT REQUEST : ', config)
  return config
}, function (error) {
  // Do something before request is sent
  console.log('$$$ Axios INTERCEPT REQUEST error : ', error)
  // Do something with request error
  return Promise.reject(error)
})

// Add a response interceptor
axios.interceptors.response.use(function (response) {
  console.log('$$$ Axios INTERCEPT RESPONSE : ', response)
  return response
}, function (error) {
  // Do something with response data
  console.log('$$$ Axios INTERCEPT RESPONSE error : ', error)
  // Do something with response error
  return Promise.reject(error)
})
