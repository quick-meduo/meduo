<template>
  <a-layout :class="['layout', device]">
    <!-- SideMenu -->
    <a-drawer
      v-if="isMobile()"
      placement="left"
      :wrapClassName="`drawer-sider ${navTheme}`"
      :closable="false"
      :visible="collapsed"
      @close="drawerClose"
    >
    <side-menu
        mode="inline"
        :menus="menus"
        :theme="navTheme"
        :collapsed="false"
        :collapsible="true"
        @menuSelect="menuSelect"
      ></side-menu>
    </a-drawer>

    <side-menu
      v-else-if="isSideMenu()"
      mode="inline"
      :menus="menus"
      :theme="navTheme"
      :collapsed="collapsed"
      :collapsible="true"
    ></side-menu>

    <a-layout :class="[layoutMode, `content-width-${contentWidth}`]" :style="{ paddingLeft: contentPaddingLeft, minHeight: '100vh' }">
      <!-- layout header -->
      <global-header
        :mode="layoutMode"
        :menus="menus"
        :theme="navTheme"
        :collapsed="collapsed"
        :device="device"
        @toggle="toggle"
      />

      <!-- layout content -->
      <a-layout-content :style="{ height: '100%', margin: '24px 24px 0', paddingTop: fixedHeader ? '64px' : '0' }">
        <multi-tab v-if="multiTab" @removed="close"></multi-tab>
        <transition name="page-transition">
          <route-view/>
        </transition>
        <!-- iframe页 -->
        <component
            v-for="item in eachOpenIframes"
            :key="item.name"
            :is="item.name"
            v-show="$route.path === item.path"
        ></component>
      </a-layout-content>
      <!-- layout footer -->
      <a-layout-footer>
        <global-footer />
      </a-layout-footer>

      <!-- Setting Drawer (show in development mode) -->
      <setting-drawer v-if="!production"></setting-drawer>
    </a-layout>
  </a-layout>

</template>

<script>
import Vue from 'vue'
import { triggerWindowResizeEvent } from '@/utils/util'
import { mapState, mapActions } from 'vuex'
import { mixin, mixinDevice } from '@/utils/mixin'
import config from '@/config/defaultSettings'

import RouteView from './RouteView'
import SideMenu from '@/components/Menu/SideMenu'
import GlobalHeader from '@/components/GlobalHeader'
import GlobalFooter from '@/components/GlobalFooter'
import SettingDrawer from '@/components/SettingDrawer'

export default {
  name: 'BasicLayout',
  mixins: [mixin, mixinDevice],
  components: {
    RouteView,
    SideMenu,
    GlobalHeader,
    GlobalFooter,
    SettingDrawer
  },
  data () {
    return {
      production: config.production,
      iframeViews: [],
      collapsed: false,
      menus: []
    }
  },
  computed: {
    ...mapState({
      // 动态主路由
      mainMenu: state => state.permission.addRouters
    }),
    contentPaddingLeft () {
      if (!this.fixSidebar || this.isMobile()) {
        return '0'
      }
      if (this.sidebarOpened) {
        return '256px'
      }
      return '80px'
    },
    eachOpenIframes() {
      return this.iframeViews.filter(item => item.meta.hasOpen);
    }
  },
  watch: {
    sidebarOpened (val) {
      this.collapsed = !val
    },
    $route() {
      this.LazyAddIframe()
      this.markOpenIframeFlag()
    }
  },
  created () {
    this.menus = this.mainMenu.find(item => item.path === '/').children;
    this.collapsed = !this.sidebarOpened;
    this.LazyAddIframe();
    this.markOpenIframeFlag();
  },
  mounted () {
    const userAgent = navigator.userAgent
    if (userAgent.indexOf('Edge') > -1) {
      this.$nextTick(() => {
        this.collapsed = !this.collapsed
        setTimeout(() => {
          this.collapsed = !this.collapsed
        }, 16)
      })
    }
  },
  methods: {
    ...mapActions(['setSidebar']),
    toggle () {
      this.collapsed = !this.collapsed
      this.setSidebar(!this.collapsed)
      triggerWindowResizeEvent()
    },
    paddingCalc () {
      let left = ''
      if (this.sidebarOpened) {
        left = this.isDesktop() ? '256px' : '80px'
      } else {
        left = (this.isMobile() && '0') || ((this.fixSidebar && '80px') || '0')
      }
      return left
    },
    menuSelect () {
    },
    drawerClose () {
      this.collapsed = false
    },
    close(path){
      this.iframeViews = this.iframeViews.filter(item => item.path !== path);
    },
    markOpenIframeFlag() {
        const target = this.iframeViews.find(item => {
            return item.path === this.$route.path
        });
        if (target && !target.meta.hasOpen) {
            target.meta.hasOpen = true;
        }
    },
    LazyAddIframe(){
      const count = this.iframeViews.length
      for(let i=0; i<count; i++){
         if(this.iframeViews[i].path === this.$route.path){
           this.iframeViews = this.iframeViews;
           return;
         }
      }
      if(this.$route.meta &&
         this.$route.meta.iframeComponent){
         this.iframeViews.push(this.$route);
         Vue.component(this.$route.name, this.$route.meta.iframeComponent);
      }

      this.iframeViews = this.iframeViews
      if(this.iframeViews.length === 1){
        Vue.nextTick( () => {
           this.$forceUpdate()
        })
      }
    },
    getIframeViews() {
      return this.iframeViews;
    }
  }
}
</script>

<style lang="less">
/*
 * The following styles are auto-applied to elements with
 * transition="page-transition" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the page transition by editing
 * these styles.
 */

.page-transition-enter {
  opacity: 0;
}

.page-transition-leave-active {
  opacity: 0;
}

.page-transition-enter .page-transition-container,
.page-transition-leave-active .page-transition-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
