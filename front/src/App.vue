<template lang="pug">
#app.wrapper(:class='[{"bg-default": isAuthArea}]')
  side-bar(v-if="!isAuthArea && !isNotFoundPage")
  .main-content(:class="[{'bg-default': isAuthArea}]")
    auth-header(v-if="isAuthArea")
    zoom-center-transition(:duration="pageTransitionDuration" mode="out-in")
      router-view
    app-footer(v-if="(!isMobile || isAuthArea) && !isNotFoundPage" :class='[{"auth-footer": isAuthArea}]')
  mobile-footer(v-if="(isMobile && !isAuthArea) && !isNotFoundPage")
</template>

<script>
import { ZoomCenterTransition } from "vue2-transitions";
import AuthHeader from "./components/Guide/AuthHeader.vue";
import Footer from "./components/Guide/Footer.vue";
import MobileFooter from "./components/Guide/MobileFooter.vue";
import SideBar from "./components/Guide/SideBar.vue";
export default {
  components: {
    ZoomCenterTransition,
    AuthHeader,
    "app-footer": Footer,
    MobileFooter,
    SideBar,
  },
  computed: {
    isAuthArea() {
      let authPages = ["login", "signup"];
      if (authPages.includes(this.$route.name)) {
        return true;
      }
      return false;
    },
    isNotFoundPage() {
      return this.$route.name == undefined;
    },
    isMobile() {
      return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
        navigator.userAgent
      );
    },
  },
  data() {
    return {
      pageTransitionDuration: 200,
    };
  },
};
</script>
<style>
@import "./assets/css/main.css";
/*#app .container {
  background-color: #f8f9fe;
}*/
.main-content .auth-footer {
  background-color: #182a4e;
}
body .nav-link-text {
  font-family: "Gothic A1", sans-serif;
}
</style>
