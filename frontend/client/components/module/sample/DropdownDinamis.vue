<template>
  <div>
    <select v-model="cmd.idProvinsi">
      <option v-for="option in listProvinsi" :value="option.value">
        {{ option.text }}
      </option>
    </select>
    <select v-model="cmd.idKota">
      <option v-for="option in listKota" :value="option.value">
        {{ option.text }}
      </option>
    </select>

    <div>Selected Provinsi : {{cmd.idProvinsi}}</div>
    <div>Selected Kota : {{cmd.idKota}}</div>
  </div>
</template>

<script>
    export default{
      data () {
        return {
          cmd: {
            idProvinsi: null,
            idKota: null
          },
          listProvinsi: [],
          listKota: []
        }
      },
      created () {
        this.listProvinsi = [
          { value: null, text: '--- Pilih Provinsi ---' },
          { value: 1, text: 'Jakarta' },
          { value: 2, text: 'Sumatera Selatan' }
        ]
      },
      watch: {
        'cmd.idProvinsi': function (newVal, oldVal) {
          console.log(oldVal, newVal)

          this.cmd.idKota = null

          if (newVal === 1) {
            this.listKota = [
              { value: 1, text: 'Jakarta Timur' },
              { value: 2, text: 'Jakarta Selatan' }
            ]
          } else if (newVal === 2) {
            this.listKota = [
              { value: 101, text: 'Palembang' }
            ]
          }

          this.listKota.unshift({ value: null, text: '--- Pilih Kota ---' }) // unshift = prepend
        }
      }
    }

</script>
