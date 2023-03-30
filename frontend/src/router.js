import Vue from 'vue';
import Router from 'vue-router';

const Home = () => import(/* webpackChunkName: "home" */ './views/Home/Home');
const Login = () =>
  import(/* webpackChunkName: "login" */ './views/Login/Login');

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/login',
      // component: Login,
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
    },
    // {
    //   path: '/statistics',
    //   name: 'statistics',
    //   component: Statistics,
    //   //   children: [{},]
    // },
    // {
    //   path: '/group',
    //   name: 'group',
    //   component: Group,
    // },
    // {
    //   path: '/assets',
    //   name: 'assets',
    //   component: Assets,
    // },
    // {
    //   path: '/user',
    //   name: 'user',
    //   component: User,
    // },
    {
      path: '*',
      redirect: '/login',
    },
  ],
  scrollBehavior() {
    return { x: 0, y: 0 };
  },
});

// router.beforeEach((to, from, next) => {
//   // to and from are both route objects. must call `next`.
// });
