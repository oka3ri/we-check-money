import Vue from "vue";
import DashboardPlugin from "./plugins/dashboard-plugin";
import App from "./App.vue";
import router from "./router/router";
import store from "./store";

// Vue.config.productionTip = false;
Vue.use(DashboardPlugin);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
