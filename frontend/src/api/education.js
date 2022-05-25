import { post } from '@/utils/request'

export default {
  queryCourseClass: (data, params) => post('/api/courseClass/query', data, params),
  deleteCourseClass: id => post('/api/courseClass/delete', null, { 'courseClassID': id }),
  createCourseClass: data => post('/api/courseClass/insert', data),
  updateCourseClass: data => post('/api/courseClass/update', data),

  queryTeacher: (data, params) => post('/api/teacher/query', data, params),
  deleteTeacher: id => post('/api/teacher/delete', null, { 'teacherID': id }),
  createTeacher: data => post('/api/teacher/insert', data),
  updateTeacher: data => post('/api/teacher/update', data)
}
