import Vue from "vue";
import VueSimpleAlert from "vue-simple-alert";
import DashboardPlugin from "./plugins/dashboard-plugin";
import RouterPushPlugIn from "./plugins/routerPush";
import ServerAPI from "./plugins/serverAPI";
import App from "./App.vue";
import router from "./router/router";
import store from "./store";

// Vue.config.productionTip = false;
Vue.use(DashboardPlugin);
Vue.use(RouterPushPlugIn);
Vue.use(ServerAPI);
Vue.use(VueSimpleAlert, {
  customClass: {
    container: "vue-simple-alert-container",
  },
});

window.Kakao.init("a31431011cd4fbc7a117ec76d269aee3");

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
