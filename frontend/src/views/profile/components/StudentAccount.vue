<template>
  <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
    <el-form-item label="学生姓名："  prop="studentName" required>
      <el-input v-model="form.studentName"></el-input>
    </el-form-item>

    <el-form-item label="学生编号：" prop="studentID" required>
      <el-input v-model="form.studentID" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="班级编号：" prop="classCode" required>
      <el-input v-model="form.classCode"></el-input>
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
      <el-button type="primary" @click="submitForm">提交</el-button>
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
        studentName: [
          { required: true, message: '请输入学生姓名', trigger: 'blur' }
        ],
        studentID: [
          { required: true, message: '请输入学生ID', trigger: 'blur' }
        ],
        classCode: [
          { required: true, message: '请输入班级编号', trigger: 'blur' }
        ],
        teleno: [
          { required: false, message: '手机号格式不正确', trigger: 'blur', pattern: /^1[3456789]\d{9}$/ }
        ],
        identifier: [
          { required: false, message: '身份证号格式不正确', trigger: 'blur', pattern: /^[1-9]\d{17}$/ }
        ]
      }
    }
  },
  props: {
    form: {
      type: Object,
      default: () => {
        return {
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
    }
  },
  methods: {
    submitForm () {
      let _this = this

      this.$confirm('确定提交 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        _this.$refs.form.validate((valid) => {
          if (valid) {
            _this.formLoading = true
            API.saveInfo(_this.form).then(data => {
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
