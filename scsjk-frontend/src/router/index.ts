import { createRouter, createWebHistory } from 'vue-router'
import Student from '../views/Student.vue' 

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
    }
  ]
})

export default router