import DashboardLayout from "@/views/Layout/DashboardLayout.vue";
// import AuthLayout from "@/views/Pages/AuthLayout.vue";
import NotFound from "@/views/NotFoundPage.vue";
const Login = () =>
  import(/* webpackChunkName: "login" */ "../views/Login/Login");
const SignUp = () =>
  import(/* webpackChunkName: "home" */ "../views/Login/SignUp");
const Home = () => import(/* webpackChunkName: "home" */ "../views/Home/Home");

const routes = [
  {
    path: "/",
    redirect: "login",
    component: DashboardLayout,
  },
  {
    path: "/login",
    name: "login",
    component: Login,
  },
  {
    path: "/home",
    name: "home",
    component: Home,
  },
  {
    path: "/signup",
    name: "signup",
    component: SignUp,
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
  { path: "*", component: NotFound },
];

export default routes;
