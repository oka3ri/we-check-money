<template lang="pug">
side-bar(ref="sideBarPlugin")
  template(slot="links")
    a.nav-item.side-menu
      a.nav-link(:class='checkMenu("home")' @click='selectMenu("home")')
        i.fa-solid.fa-money-bill-transfer
        span.nav-link-text 가계부
      a.nav-link(:class='checkMenu("statistics")' @click='selectMenu("statistics")')
        i.fa-solid.fa-chart-pie
        span.nav-link-text 통계
      a.nav-link(:class='checkMenu("group")' @click='selectMenu("group")')
        i.fa-solid.fa-comments-dollar
        span.nav-link-text 그룹
      a.nav-link(:class='checkMenu("assets")' @click='selectMenu("assets")')
        i.fa-solid.fa-piggy-bank
        span.nav-link-text 자산
      a.nav-link(:class='checkMenu("setting")' @click='selectMenu("setting")')
        i.fa-solid.fa-gear
        span.nav-link-text 설정
</template>
<script>
import PerfectScrollbar from "perfect-scrollbar";
import "perfect-scrollbar/css/perfect-scrollbar.css";

function hasElement(className) {
  return document.getElementsByClassName(className).length > 0;
}

function initScrollbar(className) {
  if (hasElement(className)) {
    new PerfectScrollbar(`.${className}`);
  } else {
    // try to init it later in case this component is loaded async
    setTimeout(() => {
      initScrollbar(className);
    }, 100);
  }
}

export default {
  components: {
  },
  methods: {
    selectMenu(menu) {
      this.$_goTo({ name: menu })
      this.$refs.sideBarPlugin.closeSidebar()
      // console.log(this.$refs.sideBarPlugin.closeSidebar())
    },
    checkMenu(name) {
      if (name == this.$route.name) {
        return 'selected-menu'
      }
    },
    initScrollbar() {
      let isWindows = navigator.platform.startsWith("Win");
      if (isWindows) {
        initScrollbar("sidenav");
      }
    },
  },
  mounted() {
    this.initScrollbar();
  },
};
</script>
<style scoped>
.navbar-light .navbar-nav  .nav-item .nav-link:hover .fa-solid{
  color: #b96a35;
}
.side-menu .fa-solid {
  color: #ef8d4b;
}
.selected-menu .fa-solid {
  color: #000000b3 !important;
}
.selected-menu .nav-link-text {
  color: #000000b3 !important;
}
</style>></style>
