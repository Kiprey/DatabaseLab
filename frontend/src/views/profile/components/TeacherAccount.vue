<template>
  <el-form :model="form" ref="form" label-width="150px" v-loading="formLoading" :rules="rules">
    <el-form-item label="教师姓名："  prop="teacherName" required>
      <el-input v-model="form.teacherName"></el-input>
    </el-form-item>

    <el-form-item label="教师编号：" prop="teacherID" required>
      <el-input v-model="form.teacherID" :disabled="'disabled'"></el-input>
    </el-form-item>

    <el-form-item label="所属院系编号：" prop="facultyCode" required>
      <el-input v-model="form.facultyCode"></el-input>
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
      }
    }
  },
  props: {
    form: {
      type: Object,
      default: () => {
        return {
          teacherName: null,
          teacherID: null,
          facultyCode: null
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
