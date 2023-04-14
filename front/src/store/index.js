import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: {
      accToken: null,
      accTokenExp: null,
      uid: "",
    },
  },
  getters: {
    userInfo(state) {
      return state.user;
    },
    userAccTokenExp: (state) => {
      return state.user.accTokenExp;
    },
    userAccToken: (state) => {
      return state.user.accToken;
    },
    userUID: (state) => {
      return state.user.userUID;
    },
  },
  mutations: {},
  actions: {},
  modules: {},
});
