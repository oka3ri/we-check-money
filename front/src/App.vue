<template lang="pug">
#app.main-content.bg-default
  auth-header
  .main-content
    zoom-center-transition(:duration="pageTransitionDuration" mode="out-in")
      router-view
  auth-footer
</template>

<script>
import { ZoomCenterTransition } from "vue2-transitions";
import AuthHeader from "./components/Guide/AuthHeader.vue";
import AuthFooter from "./components/Guide/AuthFooter.vue";
export default {
  components: {
    ZoomCenterTransition,
    AuthHeader,
    AuthFooter,
  },
  data() {
    return {
      pageTransitionDuration: 200,
    };
  },
};
</script>
