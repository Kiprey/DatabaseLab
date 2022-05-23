<template>
  <StudentAccount :form="form"></StudentAccount>
</template>

<script>
import API from '@/api/login'
import StudentAccount from './StudentAccount'

export default {
  components: { StudentAccount },
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
      }
    })
  }
}
</script>
