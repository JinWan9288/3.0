import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";
import axios from "axios";
import Manage from "@/views/Manage";

Vue.use(VueRouter)

const routes = [

  // {  静态路由
  //   path: '/', // 父路由 / 和子路由组合
  //   name: 'Manage',
  //   component: () => import('../views/Manage.vue'),
  //   redirect: "/home",
  //   children:[  //子路由 数组
  //     {  path: 'home', name: 'Home', component: () => import('../views/Home.vue') },
  //     {  path: 'user', name: '用户管理', component: () => import('../views/User.vue') }, // 完整路由是/user
  //     {  path: 'file', name: '文件管理', component: () => import('../views/File.vue') },
  //     {  path: 'person', name: '个人信息', component: () => import('../views/Person.vue') },
  //     {  path: 'role', name: '角色管理', component: () => import('../views/Role.vue') },
  //     {  path: 'menu', name: '菜单管理', component: () => import('../views/Menu.vue') },
  //   ]
  // },

  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },

  {
    path: '/404', // * 所有不在这里的路由都会显示404界面
    name: '404',
    component: () => import('../views/404.vue')
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/front/Front.vue'),
    children:[
      {
        path: '/frontHome',
        name: 'FrontHome',
        component: () => import('../views/front/FrontHome.vue')
      }
    ]
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/AboutView.vue')
  },
]

// 刷新页面会导致页面路由重置 因为只有登录set路由
export const setRoutes = () =>{ // 方法
  const storeMenus = localStorage.getItem("menus");
  if (storeMenus){ // 只在有分配菜单才会调用路由
    const manageRoute = {
      path: '/',
      name: 'Manage',
      component: () => import('../views/Manage.vue'),
      redirect:"/home",
      children:[
         {  path: 'person', name: '个人信息', component: () => import('../views/Person.vue') }, // 固定路由
      ]
    }
    const menus = JSON.parse(storeMenus); //  解析存储的菜单
    menus.forEach(item => { // 遍历循环每个菜单 这就是子路由
      // 菜单有路径 没有路径
      // 有路径就 拼接 是一级菜单
      // 没有路径 就看有没有子路径 返回子路由 就是二级菜单
      if (item.path){ // 当分配的菜单有路径就拼接
        let itemMenus = { // home
          path: item.path.replace("/",""),
          name:  item.menuname,
          component: () => import('../views/' + item.pagePath + '.vue'),
        }
          manageRoute.children.push(itemMenus); // 将itemMenus加入children中

      } else if (item.children.length){ // 分配的这个菜单存在子菜单，就拼接返回子路由（系统管理）
        item.children.forEach(item => {
          if (item.path){
            let itemMenus = {
              path: item.path.replace("/",""),
              name:  item.menuname,
              component: () => import('../views/' + item.pagePath + '.vue'),
            }
            manageRoute.children.push(itemMenus)
          }
        })
      }
    })

    const currentRoutesName = router.getRoutes().map(v => v.menuname) // 得到获取的所有路由 并将其映射为一个数组
    if(!(currentRoutesName.includes('Manage'))){ // 访问的路由不在获取的路由中才加到路由对象中去
      console.log(currentRoutesName)
      router.addRoute(manageRoute) // 添加到现有的路由对象中去
    }


  }
}


const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to ,from,next) =>{  // 路由守卫
  console.log(from);
  localStorage.setItem("currentPathName", to.name); // 存储  设置当前路由名称 set 为了在Header中使用
  store.commit("setPath")  // 触发 store 的 路由更新

  const storeMenus = localStorage.getItem("menus"); // 在登录前的时候从缓存里拿到对应身份的所有菜单
  if (!to.matched.length){ // 路由不匹配
    if (storeMenus){ // 有被分配菜单但路由错误 不匹配
      next("/404");
    } else { // 用户初次运行 但路由不匹配 跳转至登录界面
      next("/login");
    }
  }

  next(); // 路由匹配  放行
})

export default router