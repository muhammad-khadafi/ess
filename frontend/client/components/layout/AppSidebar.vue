<template>
  <div :class="{ show: auth.isAuthenticated, hide: !auth.isAuthenticated }">
    <div class="column">
      <div class="box">
        <aside class="menu">
          <menu-node class="item"
                     :model="treeData">
          </menu-node>
        </aside>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'
import MenuNode from './MenuNode'
import TreeModel from 'tree-model'
import Expanding from 'vue-bulma-expanding'
import axios from 'axios'
import auth from 'src/shared/auth'
import bus from 'src/shared/bus'
import { Collapse, Item as CollapseItem } from 'vue-bulma-collapse'

Vue.component('menu-node', MenuNode)

let tree = new TreeModel()
let root = tree.parse({})

export default {
  components: {
    Expanding,
    Collapse,
    CollapseItem
  },
  data () {
    return {
      auth: auth,
      roleId: null,
      isAuthenticated: false,
      treeData: root.model
    }
  },
  created () {
    this.isAuthenticated = this.auth.isAuthenticated
    this.roleId = localStorage.getItem('id_role')
    // console.warn('>>> INJECTOR : ', this.WHY)
    console.log('DAFTAR MODUL CREATED')
    this.fetchData()
    this.populateMenu()

    bus.$on('REFRESH_SIDEBAR_MENU', () => {
      this.populateMenu()
    })
  },
  watch: {
    // call again the method if the route changes
    '$route': 'fetchData'
  },
  methods: {
    logout () {
      console.log('LOGOUT')
      this.auth.logout()
      console.log('ROUTER nya si sidebar : ', this.$router)
      this.$router.replace('/login')
    },
    populateMenu () {
      let thisVM = this
      if (thisVM.auth.isAuthenticated) {
        let role = localStorage.getItem('id_role')
        axios.get('/auth/menu/' + role)
          .then((resp) => {
            console.log('BERHASIL GET auth menu : ', resp)
            let menuNode = resp.data
            root = tree.parse(menuNode)

            this.$set(this, 'treeData', root.model)

            this.selectCurrentMenu()
          })
      }
    },
    selectMenu (idMenu) {
      root.walk((node) => {
        node.model.isExpanded = true
      })

      let selectedNode = root.first((node) => {
        return node.model.id === idMenu
      })

      let pathSelectedNode = selectedNode.getPath()
      pathSelectedNode.pop()
      for (let nodePath of pathSelectedNode) {
        root.walk((node) => {
          if (node.model.id === nodePath.model.id) {
            node.model.isExpanded = true
            return false
          }
        })
      }

      this.treeData = root.model
    },
    selectCurrentMenu () {
      console.log('MODUL LIST FETCH DATA:' + this.$route.name)
      let currentMenuNode = root.first((node) => {
        return node.model.kode === this.$route.name
      })

      console.log('currentMenuNode : ', currentMenuNode)
      if (currentMenuNode !== undefined) {
        this.selectMenu(currentMenuNode.model.id)
      }
    },
    fetchData () {
      this.selectCurrentMenu()
    }
  },
  mounted () {
    console.log('SIDEBAR MOUNTED')
    this.isAuthenticated = this.auth.isAuthenticated
    this.roleId = localStorage.getItem('id_role')
    let thisVM = this

    bus.$on('AUTH_CHANGED', function (authChanged) {
      console.log('SIDEBAR LISTEN AUTH_CHANGED')
//      thisVM.isAuthenticated = authChanged.user.authenticated
      thisVM.populateMenu()
    })

    bus.$on('SELECT_MENU', (idMenu) => {
      this.selectMenu(idMenu)
    })
  },
  beforeDestroy () {
    bus.$off('SELECT_MENU')
  }
}
</script>

<style lang="scss">
  .show {
    display: block;
    visibility: visible;
  }

  .hide {
    display: none;
    visibility: hidden;
  }
  .menu-list li ul {
    border-left: 0px;
    margin: 0px;
    padding-left: 20px;
  }
  .menu-list li a {
    text-align: left;
    margin: 0px;
    padding-left: 10px;
  }
  .menu-list ul li {
    margin: 0px;
  }
  
  .box{
    border-radius: 10px;
  }
</style>
