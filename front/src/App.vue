<template lang="pug">
#app.wrapper(:class='[{"bg-default": isAuthArea}]')
  side-bar(v-if="!isAuthArea")
  .main-content
    auth-header(v-if="isAuthArea")
    zoom-center-transition(:duration="pageTransitionDuration" mode="out-in")
      router-view
    app-footer(:class='[{"auth-footer": isAuthArea}]')
</template>

<script>
import { ZoomCenterTransition } from "vue2-transitions";
import AuthHeader from "./components/Guide/AuthHeader.vue";
import Footer from "./components/Guide/Footer.vue";
import SideBar from "./components/Guide/SideBar.vue";
export default {
  components: {
    ZoomCenterTransition,
    AuthHeader,
    "app-footer": Footer,
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
