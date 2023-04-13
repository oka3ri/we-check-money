<template lang="pug">
base-nav.navbar-horizontal.navbar-main.navbar-top.navbar-dark(v-model="showMenu" :transparent="true" menu-classes="justify-content-end" expand="lg")
  .navbar-wrapper(slot="brand")
    a.navbar-brand.active(@click="$_goTo({name: 'login'})")
      img.header-logo(src="@/assets/images/brand/white.png")
  template
    .navbar-collapse-header
      b-row
        b-col.collapse-brand(cols="6")
          a
            img(src="@/assets/images/brand/green.png")
        b-col.collapse-close(cols="6")
          //- navbar-toggle-button(@click.native="showMenu = false")
          button.navbar-toggler(type="button" @click="$event => showMenu = false")
            span
            span
    b-navbar-nav.align-items-lg-center.ml-lg-auto
      ul.navbar-nav.align-items-lg-center.ml-lg-auto
        b-nav-item(@click="$_goTo({name: 'login'})")
          i.fa-solid.fa-user-check
          span.nav-link-inner--text 로그인
        b-nav-item(@click="$_goTo({name: 'signup'})")
          i.fa-solid.fa-user-plus
          span.nav-link-inner--text 회원가입
</template>
<script>
import { BaseNav } from "@/components";
import { ZoomCenterTransition } from "vue2-transitions";

export default {
  components: {
    BaseNav,
    ZoomCenterTransition,
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
      console.log("close menu");
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
.header-logo {
  padding: 0 12px;
}
</style>
