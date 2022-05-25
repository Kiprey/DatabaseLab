<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">

      <el-form-item label="教师姓名："  prop="teacherName" required>
        <el-input v-model="form.teacherName"></el-input>
      </el-form-item>

      <el-form-item label="教师编号：" prop="teacherID" required>
        <el-input v-model="form.teacherID" :disabled="isEditMode ? 'disabled' : false"></el-input>
      </el-form-item>

      <el-form-item label="院系代码：" prop="facultyCode" required>
        <el-input v-model="form.facultyCode"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import API from '@/api/education'

export default {
  data () {
    return {
      form: {
        teacherName: null,
        teacherID: null,
        facultyCode: null
      },
      formLoading: false,
      rules: {
        teacherName: [
          { required: true, message: '请输入教师姓名', trigger: 'blur' }
        ],
        teacherID: [
          { required: true, message: '请输入教师生ID', trigger: 'blur' }
        ],
        facultyCode: [
          { required: true, message: '请输入院系代码', trigger: 'blur' }
        ]
      },
      isEditMode: false
    }
  },
  created () {
    let teacherID = this.$route.query.teacherID
    let _this = this
    if (teacherID && teacherID !== '') {
      _this.isEditMode = true
      _this.formLoading = true

      var queryData = {
        'teacherID': teacherID
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.queryTeacher(queryData, queryParam).then(re => {
        if (re.code === '0') {
          _this.form = re.data.tableData[0]
        } else {
          _this.$message.error(re.message)
        }
        _this.formLoading = false
      }).catch(e => {
        _this.formLoading = false
      })
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
            let api = null
            if (_this.isEditMode) {
              api = API.updateTeacher
            } else {
              api = API.createTeacher
            }
            api(_this.form).then(data => {
              if (data.code === '0') {
                _this.$message.success(data.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/education/teacher/list')
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
    resetForm () {
      let _this = this

      this.$confirm('确定重置表单 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let lastteacherID = _this.form.teacherID

        _this.$refs['form'].resetFields()

        _this.form = {
          teacherName: null,
          teacherID: null,
          facultyCode: null
        }
        _this.form.teacherID = lastteacherID
      }).catch(() => {})
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  }
}
</script>
