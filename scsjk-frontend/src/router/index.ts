import { createRouter, createWebHistory } from 'vue-router'
import Student from '@/views/Student.vue'

const routes = [
  {
    path: '/student',
    name: 'Student',
    component: Student
  },
  // 其他路由配置...
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router