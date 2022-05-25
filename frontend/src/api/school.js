import { post } from '@/utils/request'

export default {
  queryStudent: (data, params) => post('/api/student/query', data, params),
  deleteStudent: id => post('/api/student/delete', null, { 'studentID': id }),
  createStudent: data => post('/api/student/insert', data),
  updateStudent: data => post('/api/student/update', data),

  queryClass: (data, params) => post('/api/class/query', data, params),
  deleteClass: id => post('/api/class/delete', null, { 'classCode': id }),
  createClass: data => post('/api/class/insert', data),
  updateClass: data => post('/api/class/update', data),

  queryMajor: (data, params) => post('/api/major/query', data, params),
  deleteMajor: id => post('/api/major/delete', null, { 'majorCode': id }),
  createMajor: data => post('/api/major/insert', data),
  updateMajor: data => post('/api/major/update', data),

  queryFaculty: (data, params) => post('/api/faculty/query', data, params),
  deleteFaculty: id => post('/api/faculty/delete', null, { 'facultyCode': id }),
  createFaculty: data => post('/api/faculty/insert', data),
  updateFaculty: data => post('/api/faculty/update', data)
}
