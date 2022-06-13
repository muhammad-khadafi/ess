<template>
  <div class="card-content">
    <p>
      403 | Forbidden
    </p>
    <!--<button @click="toggle()">Toggle</button>-->
  </diV>
</template>

<script>
import auth from 'src/shared/auth'
import { mapRouter } from 'src/shared/router'

export default {
  methods: {
    toggle () {
      auth.toggle()
      console.log('MAP ROUTER : ', mapRouter)
//      router.replace('/login')
    }
  }
}
</script>
