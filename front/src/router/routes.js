import DashboardLayout from "@/views/Layout/DashboardLayout.vue";
// import AuthLayout from "@/views/Pages/AuthLayout.vue";
import NotFound from "@/views/NotFoundPage.vue";
const Login = () =>
  import(/* webpackChunkName: "login" */ "../views/Login/Login.vue");
const SignUp = () =>
  import(/* webpackChunkName: "home" */ "../views/Login/SignUp.vue");
const Home = () =>
  import(/* webpackChunkName: "home" */ "../views/Home/Home.vue");
const Chart = () =>
  import(/* webpackChunkName: "chart" */ "../views/Chart/Chart.vue");
const Assets = () =>
  import(/* webpackChunkName: "assets" */ "../views/Assets/Assets.vue");
const Group = () =>
  import(/* webpackChunkName: "group" */ "../views/Group/Group.vue");
const Setting = () =>
  import(/* webpackChunkName: "setting" */ "../views/Setting/Setting.vue");

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
  {
    path: "/statistics",
    name: "statistics",
    component: Chart,
    //   children: [{},]
  },
  {
    path: "/group",
    name: "group",
    component: Group,
  },
  {
    path: "/assets",
    name: "assets",
    component: Assets,
  },
  {
    path: "/setting",
    name: "setting",
    component: Setting,
  },
  { path: "*", component: NotFound },
];

export default routes;
