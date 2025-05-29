import { createRouter, createWebHistory } from 'vue-router'//createRouter用于创建路由实例，createWebHistory用于使用HTML5 History API来管理前端路由。
//用作路由指向的不同页面内容。
import Student from '../views/Student.vue' 
import Course from '../views/Course.vue' 
import Sc from '../views/Sc.vue' // 引入Sc组件

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  //设置了路由模式为HTML5 History API，并使用了环境变量配置的基础URL。
  routes: [
  //定义了应用的所有路由配置。
    {
      path: '/',//路径匹配规则，匹配根路径。
      redirect: '/student'//重定向，当匹配到该路径时，将自动跳转到'/student'路径。
    },
    {
      path: '/student',//URL路径，用于匹配导航地址。
      name: 'Student',//路由名称，用于路由跳转时指定目标路由。
      component: Student//对应的Vue组件，当匹配到该路径时，将渲染该组件。
    },
    {
      path: '/course',
      name: 'Course',
      component: Course
    },
    {
      path: '/sc',
      name: 'Sc',
      component: Sc // 添加Sc组件的路由配置
    }
  ]
})

export default router//将创建的路由实例router导出，以便在Vue应用中使用。