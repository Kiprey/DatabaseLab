import { post, postWithLoadTip } from '@/utils/request'

export default {
  login: query => postWithLoadTip(`/api/user/login`, {}, query),
  logout: () => post(`/api/user/logout`),
  register: query => post(`/api/admin/userRegister`, query),
  changePass: query => post(`/api/user/changePassword`, query),

  getStudentInfo: () => postWithLoadTip('/api/student/info'),
  saveStudentInfo: (data) => post('/api/student/updateByStudent', data),
  getTeacherInfo: () => postWithLoadTip('/api/teacher/info'),

  queryuserbyadmin: (data, params) => post('/api/account/queryuserbyadmin', data, params),
  deluserbyadmin: id => post('/api/account/deluserbyadmin', null, { 'username': id }),
  adduserbyadmin: data => post('/api/account/adduserbyadmin', data),
  modifypassbyadmin: data => post('/api/account/modifypassbyadmin', data)
}
