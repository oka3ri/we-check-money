import axios from "axios";
import { mapGetters } from "vuex";
axios.defaults.serverIp = process.env.VUE_APP_SERVER_IP;
axios.defaults.baseURL = axios.defaults.serverIp + "/";
// axios.defaults.admin = axios.defaults.serverIp + '/auth/';

const methods = {
  async axios(method, url, params) {
    let $_url = axios.defaults.baseURL + url;
    let $_res = "";

    if (this.$store.getters.userAccToken == null) {
      alert("로그인이 필요한 서비스입니다.");
      this.$_goTo({ name: "login" });
      return;
    }

    if (method == "GET") {
      $_res = axios({
        method: "GET",
        url: $_url,
        headers: {
          Authorization: `Bearer ${this.$store.getters.userAccToken}`,
        },
      }).then((res) => {
        if (res.status === 200 || res.status === 204) {
          return res;
        } else {
          return false;
        }
      });
    } else if (method == "POST") {
      $_res = axios({
        method: "POST",
        url: $_url,
        headers: {
          Authorization: `Bearer ${this.$store.getters.userAccToken}`,
        },
        params: params,
      })
        .then((res) => {
          if (res.status === 200) {
            return res;
          } else {
            return false;
          }
        })
        .catch((err) => {
          return err;
        });
    } else if (method == "PUT") {
      $_res = axios({
        method: "PUT",
        url: $_url,
        headers: {
          Authorization: `Bearer ${this.$store.getters.userAccToken}`,
        },
        params: params,
      })
        .then((res) => {
          if (res.status === 200) {
            return res;
          } else {
            return false;
          }
        })
        .catch((err) => {
          return err;
        });
    } else if (method == "DELETE") {
      $_res = axios({
        method: "DELETE",
        url: $_url,
        headers: {
          Authorization: `Bearer ${this.$store.getters.userAccToken}`,
        },
      })
        .then((res) => {
          if (res.status === 200) {
            return res;
          } else {
            return false;
          }
        })
        .catch((err) => {
          return err;
        });
    }
    return $_res;
  },
  //   login(param) {
  //     let url = axios.defaults.admin + 'session';
  //     return new Promise(resolve => {
  //       axios({ method: 'POST', url: url, params: param })
  //         .then(res => {
  //           resolve(res);
  //         })
  //         .catch(err => {
  //           resolve(err);
  //         });
  //     });
  //   },
};

export default {
  data() {
    return {};
  },
  computed: {
    ...mapGetters(["userAccToken"]),
  },
  methods: {},
  install(Vue) {
    Vue.prototype.$_axios = methods.axios;
    // Vue.prototype.$_login = methods.login;
  },
};
