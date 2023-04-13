<template lang="pug">
nav#sidenav-main.navbar.navbar-vertical.fixed-left.navbar-expand-md.navbar-light.bg-white
  .container-fluid
    navbar-toggle-button(v-if="!isMobile" @click.native="showSidebar")
    a.navbar-brand(@click='goTo({name: "home"})')
      img.navbar-brand-img(src="@/assets/images/brand/green.png")
    slot(name="mobile-right")
      ul.nav.align-items-center.d-md-none
        base-dropdown.nav-item(menu-on-right tag="li" title-tag="a")
          a.nav-link.nav-link-icon(slot="title-container" role="button" aria-haspopup="true" aria-expanded="false")
            i.ni.ni-bell-55
          a.dropdown-item Action 
          .dropdown-divider
          a.dropdown-item Something else here
        base-dropdown.nav-item(menu-on-right tag="li" title-tag="a")
          a.nav-link(slot="title-container" role="button")
            i.ni.ni-single-02
          a.dropdown-item
            i.ni.ni-single-02
            span 프로필
          a.dropdown-item
            i.ni.ni-settings-gear-65
            span 설정
          //- a.dropdown-item
            i.ni.ni-calendar-grid-58
            span Activity
          //- a.dropdown-item
            i.ni.ni-support-16
            span Support
          .dropdown-divider
          a.dropdown-item
            i.fa-solid.fa-right-from-bracket
            span 로그아웃
    slot
    #sidenav-collapse-main.navbar-collapse.collapse.show(v-show="$sidebar.showSidebar")
      .navbar-collapse-header.d-md-none
        .row
          .col-6.collapse-brand
            a
              img(src="@/assets/images/brand/green.png")
          .col-6.collapse-close
            navbar-toggle-button(@click.native="closeSidebar")
      ul.navbar-nav
        slot(name="links")
      hr.my-3
      h6.navbar-heading.text-muted Documentation
      ul.navbar-nav.mb-md-3
        li.nav-item
          a.nav-link(href="https://we-check.notion.site/we-check-fa358ddd9fe94df4a5273071670c0607")
            i.ni.ni-spaceship
            span Notion
        li.nav-item
          a.nav-link(href="https://github.com/oka3ri/we-check-money")
            i.fa-brands.fa-github
            span GitHub
</template>
<script>
import NavbarToggleButton from "@/components/NavbarToggleButton";

export default {
  name: "sidebar",
  components: {
    NavbarToggleButton,
  },
  props: {
    logo: {
      type: String,
      default: "@/assets/images/brand/green.png",
      description: "Sidebar app logo",
    },
    autoClose: {
      type: Boolean,
      default: true,
      description:
        "Whether sidebar should autoclose on mobile when clicking an item",
    },
  },
  computed: {
    isMobile() {
      return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
        navigator.userAgent
      );
    },
  },
  provide() {
    return {
      autoClose: this.autoClose,
    };
  },
  methods: {
    closeSidebar() {
      this.$sidebar.displaySidebar(false);
    },
    showSidebar() {
      this.$sidebar.displaySidebar(true);
    },
  },
  beforeDestroy() {
    if (this.$sidebar.showSidebar) {
      this.$sidebar.showSidebar = false;
    }
  },
};
</script>
