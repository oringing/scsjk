import { createRouter, createWebHistory } from 'vue-router'
import Student from '../views/Student.vue' 
import Course from '../views/Course.vue' 
import Sc from '../views/Sc.vue' // 引入Sc组件

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/student'
    },
    {
      path: '/student',
      name: 'Student',
      component: Student
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

export default router