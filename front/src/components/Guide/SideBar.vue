<template lang="pug">
side-bar
  template(slot="links")
    a.nav-item
      a.nav-link
        i.fa-solid.fa-money-bill-transfer(style="color: #EF8D4B;")
        span.nav-link-text 가계부
      a.nav-link
        i.fa-solid.fa-chart-pie(style="color: #EF8D4B;")
        span.nav-link-text 통계
      a.nav-link
        i.fa-solid.fa-comments-dollar(style="color: #EF8D4B;")
        span.nav-link-text 그룹
      a.nav-link
        i.fa-solid.fa-piggy-bank(style="color: #EF8D4B;")
        span.nav-link-text 자산
      a.nav-link
        i.fa-solid.fa-gear(style="color: #EF8D4B;")
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
  color: #b96a35 !important;
}
</style>></style>
