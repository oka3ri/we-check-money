<template>
  <footer class="py-5" id="footer-main">
    <b-container>
      <b-row align-v="center" class="justify-content-xl-between">
        <b-col xl="6">
          <div class="copyright text-center text-xl-left text-muted">
            © {{ year }}
            <a href="" class="font-weight-bold ml-1" target="_blank">Wecheck</a>
          </div>
        </b-col>
        <b-col xl="6" class="col-xl-6">
          <b-nav
            class="nav-footer justify-content-center justify-content-xl-end"
          >
            <!-- <b-nav-item href="https://www.creative-tim.com" target="_blank">
              Creative Tim
            </b-nav-item> -->
            <b-nav-item
              href="https://we-check.notion.site/we-check-fa358ddd9fe94df4a5273071670c0607"
              target="_blank"
            >
              About Us
            </b-nav-item>
            <b-nav-item
              href="https://github.com/oka3ri/we-check-money"
              target="_blank"
            >
              GitHub
            </b-nav-item>
            <b-nav-item
              href="https://www.creative-tim.com/license"
              target="_blank"
            >
              License
            </b-nav-item>
          </b-nav>
        </b-col>
      </b-row>
    </b-container>
  </footer>
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
