<template>
    <div>
        <p>Contoh wrap Jquery plugin timepicker menjadi komponen Vue</p>
        <p>
          Lib yang perlu di-install di package.json :
          <ul>
            <li>- <code>yarn add -D expose-loader</code></li>
            <li>- <code>yarn add timepicker</code></li>
          </uL>
        </p>
        <p>
          Di file .eslintrc, tambahkan "jquery": true.
          Supaya eslinter tidak komplain <code>error  '$' is not defined  no-undef</code>
        </p>
        <p>
          Implementasi detail bagaimana cara wrap Jquery plugin timepicker,
          lihat source code <strong>sample/JqueryTimepicker.vue</strong>
        </p>
        <hr/>
        <jquery-timepicker v-model="selectedTime"/>
        Selected time : {{ selectedTime }}
    </div>
</template>
<style scoped>
    body{
        background-color:#ff0000;
    }
</style>
<script>
  import JqueryTimepicker from './JqueryTimepicker'

  export default{
    data () {
      return {
        selectedTime: null
      }
    },
    components: {
      JqueryTimepicker
    }
  }
</script>
