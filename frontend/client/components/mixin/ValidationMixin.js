// import _ from 'lodash'
export default {
  methods: {
    validateNotBlank: function (field, value) {
      if (value === null || value === '') {
        this.$set(this.errors, field, 'field cannot be empty.')
      } else {
        this.$set(this.errors, field, null)
      }
    },
    validatePersentage: function (field, value) {
      if (value < 0 || value > 100) {
        this.$set(this.errors, field, 'Value must be greater than 0 and less than 100.')
      } else {
        this.$set(this.errors, field, null)
      }
    },
    validatePositifValue: function (field, value) {
      if (value < 0) {
        this.$set(this.errors, field, 'Nilai tidak boleh kurang dari 0')
      } else {
        this.$set(this.errors, field, null)
      }
    },
    validatePositifValueandGreaterThanZero: function (field, value) {
      if (value < 0) {
        this.$set(this.errors, field, 'Nilai tidak boleh kurang dari 0')
      } else {
        this.$set(this.errors, field, null)
      }
    }
  }
}
