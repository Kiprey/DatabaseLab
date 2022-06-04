import { post } from '@/utils/request'

export default {
  queryCourseClass: (data, params) => post('/api/courseClass/query', data, params),
  queryCourseClassByUser: (data, params) => post('/api/courseClass/queryByUser', data, params),
  queryCourseClassByTeacher: (data, params) => post('/api/courseClass/queryByTeacher', data, params),
  deleteCourseClass: id => post('/api/courseClass/delete', null, { 'courseClassID': id }),
  deleteCourseClassByTeacher: id => post('/api/courseClass/deleteByTeacher', null, { 'courseClassID': id }),
  createCourseClass: data => post('/api/courseClass/insert', data),
  createCourseClassByTeacher: data => post('/api/courseClass/insertByTeacher', data),
  updateCourseClass: data => post('/api/courseClass/update', data),
  updateCourseClassByTeacher: data => post('/api/courseClass/updateByTeacher', data),

  queryTeacher: (data, params) => post('/api/teacher/query', data, params),
  queryTeacherWithoutID: (data, params) => post('/api/teacher/queryWithoutID', data, params),
  deleteTeacher: id => post('/api/teacher/delete', null, { 'teacherID': id }),
  createTeacher: data => post('/api/teacher/insert', data),
  updateTeacher: data => post('/api/teacher/update', data)
}
