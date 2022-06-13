<template>
  <div>
    <div v-show="model.level !== -1">
      <p v-if="isFolder" class="menu-label">
        {{ model.label }}
      </p>
      <li :title="model.label == 'Project' ? 'Project, Project Contract, Project Assignment' : ''" v-else @click="selectMenu"
          :class="{selected: model.isSelected}">
        <router-link :to="getPath()">
          {{model.label}}
        </router-link>
      </li>
    </div>
    <ul class="menu-list" v-if="isFolder">
      <menu-node class="item"
        v-for="model in model.children"
        :model="model">
      </menu-node>
    </ul>
  </div>
</template>

<script>
  import bus from 'src/shared/bus'
  import {default as router, mapRouter} from 'src/shared/router'
  import _ from 'lodash'

  export default {
    props: {
      model: Object
    },
    computed: {
      isFolder: function () {
        return this.model.children && this.model.children.length
      }
    },
    methods: {
      toggle: function () {
        this.model.isExpanded = true
      },
      selectMenu () {
        this.model.isSelected = true
        bus.$emit('SELECT_MENU', this.model.id)
        if (!this.isFolder) {
          router.push(this.getPath())
        }
      },
      getPath () {
        let route = _.find(mapRouter, (route) => { return route.name === this.model.kode })
        return route === undefined ? '' : route.path
      }
    }
  }
</script>

<style lang="scss" scoped>
  .item {
    cursor: pointer;
  }
  .bold {
    font-weight: bold;
  }
  p {
    margin-top: 1.5em;
    margin-bottom: 0.5em;
  }
  ul {
    padding-left: 1em;
    line-height: 1.5em;
    list-style-type: none;
  }
</style>
