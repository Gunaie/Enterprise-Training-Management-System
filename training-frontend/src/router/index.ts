import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue')
      },
      {
        path: '/courses',
        name: 'Courses',
        component: () => import('../views/Courses.vue')
      },
      {
        path: '/courses/:id',
        name: 'CourseDetail',
        component: () => import('../views/CourseDetail.vue')
      },
      {
        path: '/exams',
        name: 'Exams',
        component: () => import('../views/Exams.vue')
      },
      {
        path: '/students',
        name: 'Students',
        component: () => import('../views/Students.vue')
      },
      {
        path: '/enrollments',
        name: 'Enrollments',
        component: () => import('../views/Enrollments.vue')
      },
      {
        path: '/statistics',
        name: 'Statistics',
        component: () => import('../views/Statistics.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, _from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    next()
  } else {
    if (token) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router
