<template>
  <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
    <el-form-item label="学生姓名："  prop="studentName">
      <el-input v-model="form.studentName" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="学生编号：" prop="studentID">
      <el-input v-model="form.studentID" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="班级编号：" prop="classCode">
      <el-input v-model="form.classCode" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="班级名称：" prop="className">
      <el-input v-model="form.className" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="专业编号：" prop="majorCode">
      <el-input v-model="form.majorCode" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="专业名称：" prop="majorName">
      <el-input v-model="form.majorName" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="院系编号：" prop="facultyCode">
      <el-input v-model="form.facultyCode" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="院系名称：" prop="facultyName">
      <el-input v-model="form.facultyName" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="身份证号：" prop="identifier">
      <el-input v-model="form.identifier"></el-input>
    </el-form-item>

    <el-form-item label="宿舍：" prop="dormitory">
      <el-input v-model="form.dormitory"></el-input>
    </el-form-item>

    <el-form-item label="家庭住址：" prop="address">
      <el-input v-model="form.address"></el-input>
    </el-form-item>

    <el-form-item label="手机：" prop="teleno">
      <el-input v-model="form.teleno"></el-input>
    </el-form-item>

    <el-form-item label="出生日期：" prop="birthday">
      <el-date-picker v-model="form.birthday" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" />
    </el-form-item>

    <el-form-item label="性别：" prop="sex">
      <el-select v-model="form.sex" clearable>
        <el-option v-for="item in sexEnum" :key="item" :value="item" :label="item"></el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="年级：" prop="grade">
      <el-input v-model="form.grade"></el-input>
    </el-form-item>

    <el-form-item label="已修学分：" prop="completedCredits">
      <el-input v-model="form.completedCredits" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm">修改</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { mapActions } from 'vuex'
import API from '@/api/login'

export default {
  name: 'StudentAccount',
  data () {
    return {
      sexEnum: ['男', '女'],
      formLoading: false,
      rules: {
        teleno: [
          { required: false, message: '手机号格式不正确', trigger: 'blur', pattern: /^1[3456789]\d{9}$/ }
        ],
        identifier: [
          { required: false, message: '身份证号格式不正确', trigger: 'blur', pattern: /^[1-9]\d{17}$/ }
        ]
      },
      form: {
        studentName: null,
        studentID: null,
        classCode: null,
        identifier: null,
        dormitory: null,
        address: null,
        teleno: null,
        birthday: null,
        sex: null,
        grade: null,
        completedCredits: 0
      }
    }
  },
  create () {
    this.listLoading = true
    API.getStudentInfo().then(data => {
      let _this = this
      if (data.code === '0') {
        this.form = data.data
      } else {
        this.form = {
          studentName: null,
          studentID: null,
          classCode: null,
          identifier: null,
          dormitory: null,
          address: null,
          teleno: null,
          birthday: null,
          sex: null,
          grade: null,
          completedCredits: 0
        }
        _this.$message.error(data.message)
      }
      this.listLoading = false
    })
  },
  methods: {
    submitForm () {
      let _this = this
      _this.$confirm('确定提交 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        _this.$refs.form.validate((valid) => {
          if (valid) {
            _this.formLoading = true
            API.saveStudentInfo(_this.form).then(data => {
              if (data.code === '0') {
                _this.$message.success(data.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/')
                })
              } else {
                _this.$message.error(data.message)
                _this.formLoading = false
              }
            }).catch(e => {
              _this.formLoading = false
            })
          } else {
            return false
          }
        })
      }).catch(() => {})
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  }
}
</script>
