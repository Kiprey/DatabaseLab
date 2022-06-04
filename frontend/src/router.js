import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'

Vue.use(Router)

const constantRoutesAdmin = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    hidden: true,
    component: () => import('@/views/login/index'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/profile/index'
  },
  {
    path: '/school',
    component: Layout,
    name: 'SchoolPage',
    meta: {
      title: '校建管理',
      icon: 'users'
    },
    children: [
      {
        path: 'student/list',
        component: () => import('@/views/school/student/list'),
        name: 'UserStudentPageList',
        meta: { title: '学生列表', noCache: true }
      },
      {
        path: 'student/edit',
        component: () => import('@/views/school/student/edit'),
        name: 'UserStudentEdit',
        meta: { title: '学生编辑', noCache: true, activeMenu: '/school/student/list' },
        hidden: true
      },
      {
        path: 'class/list',
        component: () => import('@/views/school/class/list'),
        name: 'classPageList',
        meta: { title: '班级列表', noCache: true }
      },
      {
        path: 'class/edit',
        component: () => import('@/views/school/class/edit'),
        name: 'classEdit',
        meta: { title: '班级编辑', noCache: true, activeMenu: '/school/class/list' },
        hidden: true
      },
      {
        path: 'major/list',
        component: () => import('@/views/school/major/list'),
        name: 'majorPageList',
        meta: { title: '专业列表', noCache: true }
      },
      {
        path: 'major/edit',
        component: () => import('@/views/school/major/edit'),
        name: 'majorEdit',
        meta: { title: '专业编辑', noCache: true, activeMenu: '/school/major/list' },
        hidden: true
      },
      {
        path: 'faculty/list',
        component: () => import('@/views/school/faculty/list'),
        name: 'facultyPageList',
        meta: { title: '院系列表', noCache: true }
      },
      {
        path: 'faculty/edit',
        component: () => import('@/views/school/faculty/edit'),
        name: 'facultyEdit',
        meta: { title: '院系编辑', noCache: true, activeMenu: '/school/faculty/list' },
        hidden: true
      }
    ]
  },
  // {
  //   path: '/exam',
  //   component: Layout,
  //   name: 'ExamPage',
  //   meta: {
  //     title: '卷题管理',
  //     icon: 'exam'
  //   },
  //   children: [
  //     {
  //       path: 'paper/list',
  //       component: () => import('@/views/exam/paper/list'),
  //       name: 'ExamPaperPageList',
  //       meta: { title: '试卷列表', noCache: true }
  //     },
  //     {
  //       path: 'paper/edit',
  //       component: () => import('@/views/exam/paper/edit'),
  //       name: 'ExamPaperEdit',
  //       meta: { title: '试卷编辑', noCache: true, activeMenu: '/exam/paper/list' },
  //       hidden: true
  //     },
  //     {
  //       path: 'question/list',
  //       component: () => import('@/views/exam/question/list'),
  //       name: 'ExamQuestionPageList',
  //       meta: { title: '题目列表', noCache: true }
  //     },
  //     {
  //       path: 'question/edit/singleChoice',
  //       component: () => import('@/views/exam/question/edit/single-choice'),
  //       name: 'singleChoicePage',
  //       meta: { title: '单选题编辑', noCache: true, activeMenu: '/exam/question/list' },
  //       hidden: true
  //     },
  //     {
  //       path: 'question/edit/multipleChoice',
  //       component: () => import('@/views/exam/question/edit/multiple-choice'),
  //       name: 'multipleChoicePage',
  //       meta: { title: '多选题编辑', noCache: true, activeMenu: '/exam/question/list' },
  //       hidden: true
  //     },
  //     {
  //       path: 'question/edit/trueFalse',
  //       component: () => import('@/views/exam/question/edit/true-false'),
  //       name: 'trueFalsePage',
  //       meta: { title: '判断题编辑', noCache: true, activeMenu: '/exam/question/list' },
  //       hidden: true
  //     },
  //     {
  //       path: 'question/edit/gapFilling',
  //       component: () => import('@/views/exam/question/edit/gap-filling'),
  //       name: 'gapFillingPage',
  //       meta: { title: '填空题编辑', noCache: true, activeMenu: '/exam/question/list' },
  //       hidden: true
  //     },
  //     {
  //       path: 'question/edit/shortAnswer',
  //       component: () => import('@/views/exam/question/edit/short-answer'),
  //       name: 'shortAnswerPage',
  //       meta: { title: '简答题编辑', noCache: true, activeMenu: '/exam/question/list' },
  //       hidden: true
  //     }
  //   ]
  // },
  // {
  //   path: '/task',
  //   component: Layout,
  //   name: 'TaskPage',
  //   meta: {
  //     title: '任务管理',
  //     icon: 'task'
  //   },
  //   alwaysShow: true,
  //   children: [
  //     {
  //       path: 'list',
  //       component: () => import('@/views/task/list'),
  //       name: 'TaskListPage',
  //       meta: { title: '任务列表', noCache: true }
  //     },
  //     {
  //       path: 'edit',
  //       component: () => import('@/views/task/edit'),
  //       name: 'TaskEditPage',
  //       meta: { title: '任务创建', noCache: true }
  //     }
  //   ]
  // },
  {
    path: '/education',
    component: Layout,
    name: 'EducationPage',
    meta: {
      title: '教师管理',
      icon: 'education'
    },
    alwaysShow: true,
    children: [
      {
        path: 'courseClass/list',
        component: () => import('@/views/education/courseClass/list'),
        name: 'EducationCourseClassPage',
        meta: { title: '授课列表', noCache: true }
      },
      {
        path: 'courseClass/edit',
        component: () => import('@/views/education/courseClass/edit'),
        name: 'EducationCourseClassEditPage',
        meta: { title: '授课编辑', noCache: true, activeMenu: '/education/subject/list' },
        hidden: true
      },
      {
        path: 'teacher/list',
        component: () => import('@/views/education/teacher/list'),
        name: 'EducationTeacherPage',
        meta: { title: '教师列表', noCache: true }
      },
      {
        path: 'teacher/edit',
        component: () => import('@/views/education/teacher/edit'),
        name: 'EducationTeacherEditPage',
        meta: { title: '教师编辑', noCache: true, activeMenu: 'education/teacher/list' },
        hidden: true
      }
    ]
  },
  {
    path: '/curriculum',
    component: Layout,
    name: 'CurriculumPage',
    meta: {
      title: '课程管理',
      icon: 'answer'
    },
    alwaysShow: true,
    children: [
      {
        path: 'course/list',
        component: () => import('@/views/curriculum/course/list'),
        name: 'CurriculumCoursePage',
        meta: { title: '课程列表', noCache: true }
      },
      {
        path: 'course/edit',
        component: () => import('@/views/curriculum/course/edit'),
        name: 'CurriculumCourseEditPage',
        meta: { title: '课程编辑', noCache: true, activeMenu: 'curriculum/course/list' },
        hidden: true
      },
      {
        path: 'elective/list',
        component: () => import('@/views/curriculum/elective/list'),
        name: 'CurriculumElectivePage',
        meta: { title: '选修列表', noCache: true }
      },
      {
        path: 'elective/edit',
        component: () => import('@/views/curriculum/elective/edit'),
        name: 'CurriculumElectiveEditPage',
        meta: { title: '课程编辑', noCache: true, activeMenu: 'curriculum/elective/list' },
        hidden: true
      }
    ]
  },
  {
    path: '/account',
    component: Layout,
    name: 'AccountPage',
    meta: {
      title: '权限管理',
      icon: 'education'
    },
    alwaysShow: true,
    children: [
      {
        path: 'list',
        component: () => import('@/views/account/list'),
        name: 'AccountListPage',
        meta: { title: '账号列表', noCache: true }
      },
      {
        path: 'edit',
        component: () => import('@/views/account/edit'),
        name: 'AccountEditPage',
        meta: { title: '账号编辑', noCache: true, activeMenu: '/account/list' },
        hidden: true
      }
    ]
  },
  // {
  //   path: '/message',
  //   component: Layout,
  //   name: 'MessagePage',
  //   meta: {
  //     title: '消息中心',
  //     icon: 'message'
  //   },
  //   alwaysShow: true,
  //   children: [
  //     {
  //       path: 'list',
  //       component: () => import('@/views/message/list'),
  //       name: 'MessageListPage',
  //       meta: { title: '消息列表', noCache: true }
  //     },
  //     {
  //       path: 'send',
  //       component: () => import('@/views/message/send'),
  //       name: 'MessageSendPage',
  //       meta: { title: '消息发送', noCache: true }
  //     }
  //   ]
  // },
  // {
  //   path: '/log',
  //   component: Layout,
  //   name: 'LogPage',
  //   meta: {
  //     title: '日志中心',
  //     icon: 'log'
  //   },
  //   alwaysShow: true,
  //   children: [
  //     {
  //       path: 'user/list',
  //       component: () => import('@/views/log/list'),
  //       name: 'LogUserPage',
  //       meta: { title: '用户日志', noCache: true }
  //     }
  //   ]
  // },
  {
    path: '/profile',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: '个人简介', icon: 'user', noCache: true }
      }
    ]
  },
  { path: '*',
    hidden: true,
    component: () => import('@/views/error-page/404'),
    meta: { title: '404', noCache: true }
  }
]

const constantRoutesStudent = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    hidden: true,
    component: () => import('@/views/login/index'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/profile/index'
  },
  // todo
  {
    path: '/profile',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: '个人简介', icon: 'user', noCache: true }
      }
    ]
  },
  {
    path: '/studentPage',
    component: Layout,
    name: 'studentSchoolPage',
    meta: {
      title: '校建信息',
      icon: 'users'
    },
    children: [
      {
        path: 'school/class',
        component: () => import('@/views/studentPage/school/class'),
        meta: { title: '班级列表', noCache: true }
      },
      {
        path: 'school/faculty',
        component: () => import('@/views/studentPage/school/faculty'),
        meta: { title: '学院列表', noCache: true }
      },
      {
        path: 'school/major',
        component: () => import('@/views/studentPage/school/major'),
        meta: { title: '专业列表', noCache: true }
      },
      {
        path: 'school/teacher',
        component: () => import('@/views/studentPage/school/teacher'),
        meta: { title: '教师列表', noCache: true }
      }
    ]
  },
  {
    path: '/studentPage',
    component: Layout,
    name: 'studentCoursePage',
    meta: {
      title: '课程操作',
      icon: 'exam'
    },
    children: [
      {
        path: 'course/course',
        component: () => import('@/views/studentPage/course/course'),
        meta: { title: '所有课程列表', noCache: true }
      },
      {
        path: 'course/courseClass',
        component: () => import('@/views/studentPage/course/courseClass'),
        meta: { title: '可选课程列表', noCache: true }
      },
      {
        path: 'course/studentCourse',
        component: () => import('@/views/studentPage/course/studentCourse'),
        meta: { title: '已选课程列表', noCache: true }
      },
      {
        path: 'course/studentCourseEdit',
        component: () => import('@/views/studentPage/course/studentCourseEdit'),
        meta: { title: '已选课程编辑', noCache: true, activeMenu: '/studentPage/course/studentCourseEdit' },
        hidden: true
      }
    ]
  },
  { path: '*',
    hidden: true,
    component: () => import('@/views/error-page/404'),
    meta: { title: '404', noCache: true }
  }
]

const constantRoutesTeacher = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    hidden: true,
    component: () => import('@/views/login/index'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/profile/index'
  },
  // todo
  {
    path: '/profile',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: '个人简介', icon: 'user', noCache: true }
      }
    ]
  },
  { path: '*',
    hidden: true,
    component: () => import('@/views/error-page/404'),
    meta: { title: '404', noCache: true }
  }
]

// 以消除警告
console.log(constantRoutesAdmin)
console.log(constantRoutesTeacher)
console.log(constantRoutesStudent)

const constantRoutes = constantRoutesStudent

const router = new Router({
  routes: constantRoutes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
  } else {
    let token = window.sessionStorage.getItem('token')
    if (token === null || token === '') {
      next('/login')
    } else {
      next()
    }
  }
})

export {
  constantRoutes,
  router
}
