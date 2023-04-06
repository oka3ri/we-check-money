// import tokenDecoder from 'jwt-decode';
// import { store } from '../store/index';

const methods = {
  goTo(value) {
    // if (!store?.getters?.userAccToken) {
    //   // 토큰이 없을 때 login으로 보냄
    //   this.$router.push({ name: "login" }).catch(() => {});
    //   return;
    // }
    this.$router
      .push({
        path: value.path,
        name: value.name,
        params: value.params,
        query: value.query,
        hash: value.hash,
      })
      .catch(() => {
        // this.$router.go();
      });
  },
};

export default {
  install(Vue) {
    Vue.prototype.$_goTo = methods.goTo;
  },
  data() {
    return {};
  },
};
