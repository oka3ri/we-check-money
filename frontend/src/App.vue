<template lang="pug">
#app
  //- sidenav(:custom_class="$store.state.mcolor" :class="[$store.state.isTransparent, $store.state.isRTL ? 'fixed-end' : 'fixed-start']" v-if="$store.state.showSidenav")
  main.main-content.position-relative.max-height-vh-100.h-100.border-radius-lg
    navbar(:class="[navClasses]" :textWhite="$store.state.isAbsolute ? 'text-white opacity-8' : 'text-white'" :minNav="navbarMinimize" v-if="$store.state.showNavbar")
    router-view
    app-footer(v-show="$store.state.showFooter")
    configurator(:toggle="toggleConfigurator" :class="[$store.state.showConfig ? 'show' : '', $store.state.hideConfigButton ? 'd-none' : '']")
</template>

<script>
// import Sidenav from './components/Sidenav';
import Configurator from './components/Guide/Configurator.vue';
import Navbar from './components/Guide/Navbar.vue';
import AppFooter from './components/Guide/Footer.vue';
import { mapMutations } from 'vuex';

export default {
  name: 'App',
  components: {
    // Sidenav,
    Configurator,
    Navbar,
    AppFooter,
  },
  methods: {
    ...mapMutations(['toggleConfigurator', 'navbarMinimize']),
  },
  computed: {
    navClasses() {
      return {
        'position-sticky bg-white left-auto top-2 z-index-sticky':
          this.$store.state.isNavFixed && !this.$store.state.darkMode,
        'position-sticky bg-default left-auto top-2 z-index-sticky':
          this.$store.state.isNavFixed && this.$store.state.darkMode,
        'position-absolute px-4 mx-0 w-100 z-index-2':
          this.$store.state.isAbsolute,
        'px-0 mx-4': !this.$store.state.isAbsolute,
      };
    },
  },
  beforeMount() {
    this.$store.state.isTransparent = 'bg-transparent';
  },
};
</script>

<style>
@import './assets/css/nucleo-icons.css';
@import './assets/css/nucleo-svg.css';
/* @import './assets/css/bootstrap.css'; */
</style>
