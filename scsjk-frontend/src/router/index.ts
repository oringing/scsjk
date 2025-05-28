import { createRouter, createWebHistory } from 'vue-router'
import Student from '../views/Student.vue' 
import Course from '../views/Course.vue' 

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
    }
  ]
})

export default router