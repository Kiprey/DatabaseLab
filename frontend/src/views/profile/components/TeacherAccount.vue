<template>
  <el-form :model="form" ref="form" label-width="150px" v-loading="formLoading" :rules="rules">
    <el-form-item label="教师姓名："  prop="teacherName" required>
      <el-input v-model="form.teacherName" :readonly="'readonly'"></el-input>
    </el-form-item>

    <el-form-item label="教师编号：" prop="teacherID" required>
      <el-input v-model="form.teacherID" :readonly="'readonly'"></el-input>
    </el-form-item>

    <el-form-item label="所属院系名称：" prop="facultyName" required>
      <el-input v-model="form.facultyName" :readonly="'readonly'"></el-input>
    </el-form-item>

    <el-form-item label="所属院系编号：" prop="facultyCode" required>
      <el-input v-model="form.facultyCode" :readonly="'readonly'"></el-input>
    </el-form-item>
  </el-form>
</template>

<script>
import API from '@/api/login'

export default {
  name: 'TeacherAccount',
  data () {
    return {
      sexEnum: ['男', '女'],
      formLoading: false,
      rules: {
        teacherName: [
          { required: true, message: '请输入教师姓名', trigger: 'blur' }
        ],
        teacherID: [
          { required: true, message: '请输入教师ID', trigger: 'blur' }
        ],
        facultyCode: [
          { required: true, message: '请输入所属院系编号', trigger: 'blur' }
        ]
      },
      form: {
        teacherName: null,
        teacherID: null,
        facultyCode: null,
        facultyName: null
      }
    }
  },
  created () {
    this.formLoading = true
    API.getTeacherInfo().then(data => {
      let _this = this
      if (data.code === '0') {
        this.form = data.data[0]
      } else {
        this.form = {
          teacherName: null,
          teacherID: null,
          facultyCode: null,
          facultyName: null
        }
        _this.$message.error(data.message)
      }
      this.formLoading = false
    })
  }
}
</script>
