import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/counter",
    name: "counter",
    component: () => import("../views/counter/CounterView.vue"),
    children: [
      {
        path: "",
        component: () => import("../views/counter/SseCounterView.vue"),
      },
      {
        path: "sse",
        component: () => import("../views/counter/SseCounterView.vue"),
      },
      {
        path: "ws",
        component: () => import("../views/counter/WebSocketCounterView.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  routes,
});

export default router;
