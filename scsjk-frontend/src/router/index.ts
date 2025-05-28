import { createRouter, createWebHistory } from 'vue-router'
import StudentList from '../views/Student.vue'

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
      component: StudentList
    }
    // 可以继续添加其他路由
  ]
})

export default router