import { post, postWithLoadTip } from '@/utils/request'

export default {
  login: query => postWithLoadTip(`/api/user/login`, {}, query),
  logout: () => post(`/api/user/logout`),
  register: query => post(`/api/admin/userRegister`, query),
  changePass: query => post(`/api/user/changePassword`, query),

  getStudentInfo: () => postWithLoadTip('/api/student/info'),
  saveStudentInfo: (data) => post('/api/student/updateByStudent', data),
  getTeacherInfo: () => postWithLoadTip('/api/teacher/info'),

  selectUserRole: (data, params) => post('/api/admin/selectUserRole', data, params),
  deleteRole: data => post('/api/admin/deleteRole', data),
  insertRole: data => post('/api/admin/insertRole', data),
  userRegister: data => post('/api/admin/userRegister', {}, data)
}
