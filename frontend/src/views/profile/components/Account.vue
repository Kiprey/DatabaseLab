<template>
  <div>
    <span v-if="form.studentID !== undefined">
      <StudentAccount :form="form"></StudentAccount>
    </span>
    <span v-else-if="form.teacherID !== undefined">
      <TeacherAccount :form="form"></TeacherAccount>
    </span>
    <span v-else>
      <label>暂无个人信息</label>
    </span>
  </div>
</template>

<script>
import API from '@/api/login'
import StudentAccount from './StudentAccount'
import TeacherAccount from './TeacherAccount'

export default {
  components: { StudentAccount, TeacherAccount },
  data () {
    return {
      form: {}
    }
  },
  created  () {
    let _this = this
    API.getInfo().then(re => {
      if (re.code === '0') {
        _this.form = re.data
      } else {
        _this.$message.error(re.message)
      }
    }).catch(e => {
      _this.form = {
        // 学生测试
        // studentName:  'kp',
        // studentID: '123',
        // classCode: '2019080601',
        // identifier: '350111100109027163',
        // dormitory: '天马学生公寓',
        // address: '湖南大写天马学生公寓三区506',
        // teleno: '13534348989',
        // birthday: '2001-01-03',
        // sex: '男',
        // grade: '19',
        // completedCredits: 24

        // 教师测试
        // teacherName: '小王',
        // teacherID: '123',
        // facultyCode: '334'
      }
    })
  }
}
</script>
