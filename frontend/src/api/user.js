import { post } from '@/utils/request'

export default {
  getUserPageList: (data, params) => post('/api/student/query', data, params),
  deleteUser: id => post('/api/student/delete', null, { 'studentID': id }),
  createUser: data => post('/api/student/insert', data),
  updateUser: data => post('/api/student/update', data),

  getUserEventPageList: query => post('/api/admin/user/event/page/list', query),
  selectUser: id => post('/api/admin/user/select/' + id),
  getCurrentUser: () => post('/api/admin/user/current'),
  changeStatus: id => post('/api/admin/user/changeStatus/' + id),
  selectByUserName: query => post('/api/admin/user/selectByUserName', query)
}
