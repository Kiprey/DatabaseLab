import { post } from '@/utils/request'

export default {
  queryStudent: (data, params) => post('/api/student/query', data, params),
  deleteStudent: id => post('/api/student/delete', null, { 'studentID': id }),
  createStudent: data => post('/api/student/insert', data),
  updateStudent: data => post('/api/student/update', data),
}
