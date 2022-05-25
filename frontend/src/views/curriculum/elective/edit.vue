<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">

      <el-form-item label="开课号："  prop="courseClassID" required>
        <el-input v-model="form.courseClassID"></el-input>
      </el-form-item>

      <el-form-item label="学生编号：" prop="studentID" >
        <el-input v-model="form.studentID" :disabled="isEditMode ? 'disabled' : false"></el-input>
      </el-form-item>

      <el-form-item label="课程分数："  prop="score" required>
        <el-input v-model="form.score"></el-input>
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
import API from '@/api/curriculum'

export default {
  data () {
    return {
      form: {
        courseClassID: null,
        studentID: null,
        score: null
      },
      formLoading: false,
      rules: {
        courseClassID: [
          { required: true, message: '请输入开课号', trigger: 'blur' }
        ],
        studentID: [
          { required: true, message: '请输入学生编号', trigger: 'blur' }
        ],
        score: [
          { required: true, message: '请输入课程分数', trigger: 'blur' }
        ]
      },
      isEditMode: false
    }
  },
  created () {
    let studentID = this.$route.query.studentID
    let _this = this
    if (studentID && studentID !== '') {
      _this.isEditMode = true
      _this.formLoading = true

      var queryData = {
        'teacherID': studentID
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.queryStudentCourse(queryData, queryParam).then(re => {
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
              api = API.updateStudentCourse
            } else {
              api = API.createStudentCourse
            }
            api(_this.form).then(data => {
              if (data.code === '0') {
                _this.$message.success(data.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/curriculum/elective/list')
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
        let laststudentID = _this.form.studentID

        _this.$refs['form'].resetFields()

        _this.form = {
          courseClassID: null,
          studentID: null,
          score: null
        }
        _this.form.studentID = laststudentID
      }).catch(() => {})
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  }
}
</script>
