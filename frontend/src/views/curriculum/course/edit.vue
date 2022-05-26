<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">

      <el-form-item label="课程名："  prop="courseName" required>
        <el-input v-model="form.courseName"></el-input>
      </el-form-item>

      <el-form-item label="课程性质：" prop="courseNature" >
        <el-input v-model="form.courseNature"></el-input>
      </el-form-item>

      <el-form-item label="课程类别：" prop="courseCategory" >
        <el-input v-model="form.courseCategory"></el-input>
      </el-form-item>

      <el-form-item label="课程编号："  prop="courseID" required>
        <el-input v-model="form.courseID" :disabled="isEditMode ? 'disabled' : false"></el-input>
      </el-form-item>

      <el-form-item label="院系代码：" prop="facultyCode" required>
        <el-input v-model="form.facultyCode"></el-input>
      </el-form-item>

      <el-form-item label="学时：" prop="courseHours" >
        <el-input v-model="form.courseHours"></el-input>
      </el-form-item>

      <el-form-item label="学分："  prop="credit" >
        <el-input v-model="form.credit"></el-input>
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
        courseName: null,
        courseNature: null,
        courseCategory: null,
        courseID: null,
        facultyCode: null,
        courseHours: null,
        credit: null
      },
      formLoading: false,
      rules: {
        courseName: [
          { required: true, message: '请输入课程名', trigger: 'blur' }
        ],
        courseID: [
          { required: true, message: '请输入课程编号', trigger: 'blur' }
        ],
        facultyCode: [
          { required: true, message: '请输入院系代码', trigger: 'blur' }
        ]
      },
      isEditMode: false
    }
  },
  created () {
    let courseID = this.$route.query.courseID
    let _this = this
    if (courseID && courseID !== '') {
      _this.isEditMode = true
      _this.formLoading = true

      var queryData = {
        'teacherID': courseID
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.queryCourse(queryData, queryParam).then(re => {
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
              api = API.updateCourse
            } else {
              api = API.createCourse
            }
            api(_this.form).then(data => {
              if (data.code === '0') {
                _this.$message.success(data.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/curriculum/course/list')
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
        let lastcourseID = _this.form.courseID

        _this.$refs['form'].resetFields()

        _this.form = {
          courseName: null,
          courseNature: null,
          courseCategory: null,
          courseID: null,
          facultyCode: null,
          courseHours: null,
          credit: null
        }
        _this.form.courseID = lastcourseID
      }).catch(() => {})
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  }
}
</script>
