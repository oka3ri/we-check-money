<template lang="pug">
base-nav.navbar-top.navbar-expand(container-classes="container-fluid" :class="{'navbar-dark': type === 'default'}")
  a.h4.mb-0.text-white.text-uppercase.d-none.d-lg-inline-block.active.router-link-active(href="#" aria-current="page") {{$route.name}}
  //- Navbar links
  b-navbar-nav.align-items-center.ml-md-auto
    //- <!-- This item don't have <b-nav-item> because item have data-action/data-target on tag <a>, which we cant add -->
    li.nav-item.d-sm-none
      a.nav-link(href="#" data-action="search-show" data-target="#navbar-search-main")
        i.ni.ni-zoom-split-in
  b-navbar-nav.align-items-center.ml-auto.ml-md-0
    b-form#navbar-search-main.navbar-search.form-inline.mr-sm-3(:class="{'navbar-search-dark': type === 'default', 'navbar-search-light': type === 'light'}")
      b-form-group.mb-0
        b-input-group.input-group-alternative.input-group-merge
          b-form-input(placeholder="Search" type="text")
          .input-group-append
            span.input-group-text
              i.fas.fa-search
    base-dropdown.nav-item(menu-on-right tag="li" title-tag="a" title-classes="nav-link pr-0")
      //- TODO: slot 지원중단 버전이라 수정해야 함
      a.nav-link.pr-0(href="#" @click.prevent slot="title-container")
        b-media.align-items-center(no-body)
          span.avatar.avatar-sm.rounded-circle
            img(alt="Image placeholder" src="@/assets/images/theme/team-4.jpg")
          b-media-body.ml-2.d-none.d-lg-block
            span.mb-0.text-sm.font-weight-bold John Snow
  template
    b-dropdown-header.noti-title
      h6.text-overflow.m-0 Welcome!
    b-dropdown-item(href="#!")
      i.ni.ni-single-02
      span My profile
    b-dropdown-item(href="#!")
      i.ni.ni-settings-gear-65
      span Settings
    b-dropdown-item(href="#!")
      i.ni.ni-calendar-grid-58
      span Activity
    b-dropdown-item(href="#!")
      i.ni.ni-support-16
      span Support
    div.dropdown-divider
    b-dropdown-item(href="#!")
      i.ni.ni-user-run
      span Logout
</template>
<script>
import { CollapseTransition } from "vue2-transitions";
import { BaseNav, Modal } from "../../components";

export default {
  components: {
    CollapseTransition,
    BaseNav,
    Modal,
  },
  props: {
    type: {
      type: String,
      default: "default", // default|light
      description:
        "Look of the dashboard navbar. Default (Green) or light (gray)",
    },
  },
  computed: {
    routeName() {
      const { name } = this.$route;
      return this.capitalizeFirstLetter(name);
    },
  },
  data() {
    return {
      activeNotifications: false,
      showMenu: false,
      searchModalVisible: false,
      searchQuery: "",
    };
  },
  methods: {
    capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    },
    toggleNotificationDropDown() {
      this.activeNotifications = !this.activeNotifications;
    },
    closeDropDown() {
      this.activeNotifications = false;
    },
  },
};
</script>
