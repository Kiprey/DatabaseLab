import { post } from '@/utils/request'

export default {
  queryStudentCourse: (data, params) => post('/api/studentcourse/query', data, params),
  queryStudentCourseByTeacher: (data, params) => post('/api/studentcourse/queryByTeacher', data, params),
  deleteStudentCourse: params => post('/api/studentcourse/delete', null, params),
  createStudentCourse: data => post('/api/studentcourse/insert', data),
  updateStudentCourse: data => post('/api/studentcourse/update', data),
  updateStudentCourseByTeacher: data => post('/api/studentcourse/updateByTeacher', data),

  queryCourse: (data, params) => post('/api/course/query', data, params),
  deleteCourse: id => post('/api/course/delete', null, { 'courseID': id }),
  createCourse: data => post('/api/course/insert', data),
  updateCourse: data => post('/api/course/update', data)
}
