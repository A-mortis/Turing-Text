import { createRouter, createWebHistory } from 'vue-router'

import InformationView from '@/views/information/index.vue'
import AttendanceView from '@/views/attendance/index.vue'
import LeaveView from '@/views/leave/index.vue'
import ResignationView from '@/views/resignation/index.vue'
import ActivityView from '@/views/activity/index.vue'
import LoginView from '@/views/login/index.vue'
import LayoutView from '@/views/layout/index.vue'
import LogView from '@/views/log/index.vue'
import StaffView from '@/views/layout/staff.vue'
import RequestLeaveView from '@/views/requestLeave/index.vue'
import RequestResignationView from '@/views/requestResignation/index.vue'
import RequestActivityView from '@/views/requestActivity/index.vue'
import RequestInformationView from '@/views/requestInformation/index.vue'
import RequestAttendanceView from '@/views/requestAttendance/index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { 
      path: '/',
      name: '',
      component: LayoutView,
      children:[
        {
          path: 'information',
          name: 'information',
          component: InformationView
        },
        {
          path: 'attendance',
          name: 'attendance',
          component: AttendanceView
        },
        {
          path: 'leave',
          name: 'leave',
          component: LeaveView
        },
        {
          path: 'resignation',
          name: 'resignation',
          component: ResignationView
        },
        {
          path: 'activity',
          name: 'activity',
          component: ActivityView
        },
        {
          path: 'log',
          name: 'log',
          component: LogView
        }       
      ]
    },
    
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },


    {
      path: '/staff',
      name: 'staff',
      component: StaffView,
      children:[
        {
          path: '/requestLeave',
          name: 'requestLeave',
          component: RequestLeaveView
        },
    
        {
          path: '/requestResignation',
          name: 'requestResignation',
          component: RequestResignationView
        },
    
        {
          path: '/requestActivity',
          name: 'requestActivity',
          component: RequestActivityView
        },
    
        {
          path: '/requestInformation',
          name: 'requestInformation',
          component: RequestInformationView
        },

        {
          path: '/requestAttendance',
          name: 'requestAttendance',
          component: RequestAttendanceView
        }
    
        
      ]
    }
   
   
  ]
})
router.beforeEach((to, from, next) => {
  const publicPages = ['/login'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = !!localStorage.getItem('loginUser');

  if (authRequired && !loggedIn) {
    // 如果需要登录且未登录，则重定向到登录页面
    return next('/login');
  }

  // 如果用户已登录且尝试访问根路径 '/' 或其他受保护的页面，重定向到 '/log'
  if (authRequired && to.path === '/') {
    return next('/log');
  }

  next();
});


export default router
