<template>
  <div>
    <modal :visible="visible" @open="onOpen" @close="onClose">
      <component :is="contentComponent" :id="rowData.id" @close="onClose"></component>
    </modal>
    <tooltip label="View" placement="top">
      <div class="is-clearfix">
        <button class="button is-warning" @click="onClick('view-item', rowData)"><i class="fa fa-eye"/></button>
      </div>
    </tooltip>
    <tooltip label="Picture" placement="top">
      <div class="is-clearfix">
        <button class="button is-warning" :disabled="rowData.uploadPath==null" @click="onClick('picture-item', rowData)"><i class="fa fa-camera"/></button>
      </div>
    </tooltip>
    <tooltip label="Verify" placement="top">
      <div class="is-clearfix">
        <button class="button is-info" @click="onClick('edit-item', rowData)" v-if="roleName == 'DIRECTOR' || roleName == 'DEPUTY_DIRECTOR'" :disabled="((rowData.isApproved != null || rowData.needApproval == false)) ? true : false"><i class="fa fa-pencil"/></button>
      </div>
    </tooltip>
    <tooltip label="Confirm" placement="top">
      <div class="is-clearfix">
        <button class="button is-info" @click="onClick('confirm-item', rowData)" v-if="roleName == 'ACCOUNTING_STAFF'" :disabled="((rowData.isDone != null ) || (rowData.needApproval == true && rowData.isApproved != true) || (rowData.reasonOfCancellation != null)) ? true : false"><i class="fa fa-pencil"/></button>
      </div>
    </tooltip>
    <tooltip label="Cancel" placement="top">
      <div class="is-clearfix">
        <button class="button is-danger" @click="onClick('cancel-item', rowData)" v-if="roleName == 'ACCOUNTING_STAFF'" :disabled="((rowData.isDone != null ) || (rowData.needApproval == true && rowData.isApproved != true ) || (rowData.reasonOfCancellation != null)) ? true : false"><i class="fa fa-times"/></button>
      </div>
    </tooltip>
    <modal :visible="showViewModal" @close="closeViewModal" transition="zoom">
      <petty-cash-view :id="id"  @close="closeViewModal"></petty-cash-view>
    </modal>
    <modal :visible="showVerifyModal" @close="closeVerifyModal" transition="zoom">
      <petty-cash-verify :id="id"  @close="closeVerifyModal"></petty-cash-verify>
    </modal>
    <modal :visible="showConfirmModal" @close="closeConfirmModal" transition="zoom">
      <petty-cash-confirm :id="id"  @close="closeConfirmModal"></petty-cash-confirm>
    </modal>
    <modal :visible="showPicModal" @close="closePicModal" transition="zoom">
      <div @close="close">
        <div class="modal-card-head">
          <button class="delete" @click="closePicModal"></button>
        </div>
        <div class="modal-card-body">
          <div class="box">
            <img id= "imgPreview" v-if="showPicModal" :src="getImgUrl(rowData.id, rowData.uploadPath)" />
          </div>
        </div>
      </div>
    </modal>
    <modal :visible="showCancelModal" @close="closeCancelModal" transition="zoom">
      <div @close="close">
        <div class="modal-card-head">
          <p class="modal-card-title">Confirmation Petty Cash Cancellation</p>
          <button class="delete" @click="close"></button>
        </div>
        <div class="modal-card-body">
          <div class="control is-horizontal">
            <div class="control-label">
              <label class="label">Reason of Cancellation <font color="red">*</font></label>
            </div>
            <div class="control is-grouped">
              <div class="column is-half">
                <textarea style="height:260px;" class="input" type="text" v-model="detail.reasonOfCancellation"/>
                <div class="control is-grouped" v-if="errors.reasonOfCancellation"> 
                  <span class="help is-danger">{{ errors.reasonOfCancellation }}</span> 
                </div>
              </div>
            </div>
          </div>
        </div>
        <footer class="modal-card-foot">
          <div class="column" style="padding:0">
            <div class="level-right">
              <button class="button is-danger" @click="confirmCancel(rowData.id)">Confirm</button>
              <button class="button" @click="closeCancelModal">Cancel</button>
            </div>
          </div>
        </footer>
      </div>
    </modal>
  </div>
</template>
<style>
  .modal-content {
    width: 750px
  }
</style>
<script>
import Tooltip from 'vue-bulma-tooltip'
import { Modal } from 'vue-bulma-modal'
import PettyCashForm from './PettyCashForm.vue'
import PettyCashVerify from './PettyCashVerify.vue'
import PettyCashConfirm from './PettyCashConfirm.vue'
import PettyCashView from './PettyCashView.vue'
import bus from 'src/shared/bus'
import axios from 'axios'
import _ from 'lodash'
import auth from 'src/shared/auth'

export default {
  components: {
    Tooltip,
    Modal,
    PettyCashForm,
    PettyCashVerify,
    PettyCashConfirm,
    PettyCashView
  },
  data () {
    return {
      visible: false,
      contentComponent: '',
      roleName: '',
      showViewModal: false,
      showVerifyModal: false,
      showConfirmModal: false,
      showPicModal: false,
      showCancelModal: false,
      id: null,
      errors: {},
      detail: {
        id: null,
        reasonOfCancellation: null,
        processedBy: auth.getUserInfo().id
      }
    }
  },
  props: {
    rowData: {
      type: Object,
      required: true
    }
  },
  mounted () {
    let thisVM = this
    axios.get('/role-by-id/' + localStorage.getItem('id_role'))
      .then(function (resp) {
        console.log('BERHASIL FETCH')
        thisVM.roleName = resp.data.kode
        console.log('roleName', thisVM.roleName)
      })
      .catch(function (error) {
        console.log('GAGAL FETCH')
        console.log(error.response)
        thisVM.$emit('close')
      })
  },
  methods: {
    close () {
      this.$emit('close')
    },
    onOpen () {
      this.visible = true
    },
    onClose () {
      this.visible = false
      bus.$emit('RELOAD_PETTY_CASH_LIST')
    },
    onClick (action, data) {
      this.id = data.id
      console.log('actions: on-click', action, data.name)
      if (action === 'view-item') {
        this.showViewModal = true
        // this.$router.replace('/petty-cash/view/' + data.id)
      } else if (action === 'edit-item') {
        // this.$router.replace('/petty-cash/verify/' + data.id)
        this.showVerifyModal = true
      } else if (action === 'confirm-item') {
        this.showConfirmModal = true
        // this.$router.replace('/petty-cash/confirm/' + data.id)
      } else if (action === 'picture-item') {
        this.showPicModal = true
        // this.$router.replace('/petty-cash/confirm/' + data.id)
      } else if (action === 'cancel-item') {
        this.showCancelModal = true
        // this.$router.replace('/petty-cash/confirm/' + data.id)
      }
    },
    closeViewModal () {
      this.showViewModal = false
    },
    closeVerifyModal () {
      this.showVerifyModal = false
    },
    closeConfirmModal () {
      this.showConfirmModal = false
    },
    closePicModal () {
      this.showPicModal = false
    },
    closeCancelModal () {
      this.showCancelModal = false
    },
    getImgUrl (pet, uploadPath) {
      var thisVM = this
      var res = uploadPath.split('.')
      axios.get('/image/petty-cash/' + pet)
        .then(function (resp) {
          var str = ''
          if (res[res.length - 1] === 'png') {
            str = 'data:image/png;base64,'
          } else if (res[res.length - 1] === 'jpeg') {
            str = 'data:image/jpeg;base64,'
          } else if (res[res.length - 1] === 'jpg') {
            str = 'data:image/jpg;base64,'
          }
          document.getElementById('imgPreview').src = str + resp.data
        })
        .catch(function (error) {
          console.log('GAGAL FETCH')
          console.log(error.response)
          thisVM.$emit('close')
        })
    },
    confirmCancel (id) {
      this.detail.id = id
      this.confirmFinal()
    },
    confirmFinal () {
      this.errors = {}
      if (this.detail.reasonOfCancellation == null) {
        this.$set(this.errors, 'reasonOfCancellation', 'The field cannot be empty')
      } else {
        axios.put('/petty-cash/cancel/' + this.detail.id, this.detail)
          .then((resp) => {
            bus.$emit('HIDE_LOADING')
            console.log('SUCCESS')
            console.log(resp)
            this.showCancelModal = false
            bus.$emit('NOTIFY', 'success', 'Data has been successfully saved')
            bus.$emit('RELOAD_PETTY_CASH_LIST')
          })
          .catch((error) => {
            bus.$emit('HIDE_LOADING')
            console.log('ERROR : ', error)
            console.log('isi dari error', error.response)
            _.each(error.response.data.errors, function (value) {
              this.$set(this.errors, value.field, value.message)
            })
          })
      }
    }
  }
}
</script>
