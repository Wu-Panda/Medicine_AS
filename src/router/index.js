import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../views/Manage.vue'),
    redirect : "/home",
    children : [
      {
        path: 'home', name: '首页', component: () => import('../views/Home.vue')
      },
      {
        path: 'user', name: '基本管理 / 用户管理', component: () => import('../views/User.vue')
      },
      {
        path: 'person', name: '个人信息', component: () => import('../views/Person.vue')
      },
      {
        path: 'medicine', name: '基本管理 / 药物管理', component: () => import('../views/Medicine.vue')
      },
      {
        path: 'information', name: '更多 / 系统简介', component: () => import('../views/more/Information')
      },
      {
        path: 'development', name: '更多 / 开发流程', component: () => import('../views/more/Development')
      }
    ]
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/About.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  //console.log(to)
  localStorage.setItem("currentPathName", to.name)
  store.commit("setPath")
  next()
})

export default router
