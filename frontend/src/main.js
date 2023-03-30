import Vue from 'vue';
import App from './App.vue';
import store from './store';
import router from './router';
import ArgonDashboard from './argon-dashboard';

Vue.config.productionTip = false;
Vue.use(router);
Vue.use(store);
Vue.use(ArgonDashboard);

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
