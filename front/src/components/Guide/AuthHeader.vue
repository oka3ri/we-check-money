<template lang="pug">
.main-content.bg-default
  base-nav.navbar-horizontal.navbar-main.navbar-top.navbar-dark(v-model="showMenu" :transparent="true" menu-classes="justify-content-end" expand="lg")
    .navbar-wrapper(slot="brand")
      b-navbar-brand(to="/")
        img(src="img/brand/white.png")
    <template lang='pug'>
      .navbar-collapse-header
        b-row
          b-col.collapse-brand(cols="6")
            router-link(to="/")
              img(src="img/brand/green.png")
          b-col.collapse-close(cols="6")
            button.navbar-toggler(type="button" @click="showMenu = false")
              span
              span
    </template>
    b-navbar-nav.align-items-lg-center.ml-lg-auto
      //- b-nav-item(to="/dashboard")
        i.ni.ni-planet
        span.nav-link-inner--text Dashboard
      b-nav-item(to="/register")
        i.ni.ni-circle-08
        span.nav-link-inner--text 회원가입
      b-nav-item(to="/login")
        i.ni.ni-key-25
        span.nav-link-inner--text 로그인
      //- b-nav-item(to="/profile")
        i.ni.ni-single-02
        span.nav-link-inner--text Profile
</template>
<script>
import { BaseNav } from "@/components";

export default {
  components: {
    BaseNav,
  },
  props: {
    backgroundColor: {
      type: String,
      default: "black",
    },
  },
  data() {
    return {
      showMenu: false,
      menuTransitionDuration: 250,
      pageTransitionDuration: 200,
      year: new Date().getFullYear(),
      pageClass: "login-page",
    };
  },
  computed: {
    title() {
      return `${this.$route.name} Page`;
    },
  },
  methods: {
    toggleNavbar() {
      document.body.classList.toggle("nav-open");
      this.showMenu = !this.showMenu;
    },
    closeMenu() {
      document.body.classList.remove("nav-open");
      this.showMenu = false;
    },
    setBackgroundColor() {
      document.body.classList.add("bg-default");
    },
    removeBackgroundColor() {
      document.body.classList.remove("bg-default");
    },
    updateBackground() {
      if (!this.$route.meta.noBodyBackground) {
        this.setBackgroundColor();
      } else {
        this.removeBackgroundColor();
      }
    },
  },
  beforeDestroy() {
    this.removeBackgroundColor();
  },
  beforeRouteUpdate(to, from, next) {
    // Close the mobile menu first then transition to next page
    if (this.showMenu) {
      this.closeMenu();
      setTimeout(() => {
        next();
      }, this.menuTransitionDuration);
    } else {
      next();
    }
  },
  watch: {
    $route: {
      immediate: true,
      handler: function () {
        this.updateBackground();
      },
    },
  },
};
</script>
<style lang="scss">
$scaleSize: 0.8;
@keyframes zoomIn8 {
  from {
    opacity: 0;
    transform: scale3d($scaleSize, $scaleSize, $scaleSize);
  }
  100% {
    opacity: 1;
  }
}

.main-content .zoomIn {
  animation-name: zoomIn8;
}

@keyframes zoomOut8 {
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
    transform: scale3d($scaleSize, $scaleSize, $scaleSize);
  }
}

.main-content .zoomOut {
  animation-name: zoomOut8;
}
</style>
