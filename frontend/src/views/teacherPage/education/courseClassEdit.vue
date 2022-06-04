<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">

      <el-form-item label="开课号："  prop="courseClassID" required>
        <el-input v-model="form.courseClassID" :disabled="isEditMode ? 'disabled' : false"></el-input>
      </el-form-item>

      <el-form-item label="课程编号：" prop="courseID" required>
        <el-input v-model="form.courseID"></el-input>
      </el-form-item>

      <el-form-item label="教师编号：" prop="teacherID" required>
        <el-input v-model="form.teacherID"></el-input>
      </el-form-item>

      <el-form-item label="开课时间："  prop="courseClassTime" >
        <el-date-picker v-model="form.courseClassTime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" />
      </el-form-item>

      <el-form-item label="开课地点：" prop="courseClassAddress" >
        <el-input v-model="form.courseClassAddress"></el-input>
      </el-form-item>

      <el-form-item label="开课周：" prop="courseClassWeek" >
        <el-input v-model="form.courseClassWeek"></el-input>
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
        courseClassID: null,
        courseID: null,
        teacherID: null,
        courseClassTime: null,
        courseClassAddress: null,
        courseClassWeek: null
      },
      formLoading: false,
      rules: {
        courseClassID: [
          { required: true, message: '请输入开课号', trigger: 'blur' }
        ],
        courseID: [
          { required: true, message: '请输入课程编号', trigger: 'blur' }
        ],
        teacherID: [
          { required: true, message: '请输入教师编号', trigger: 'blur' }
        ]
      },
      isEditMode: false
    }
  },
  created () {
    let courseClassID = this.$route.query.courseClassID
    let _this = this
    if (courseClassID && courseClassID !== '') {
      _this.isEditMode = true
      _this.formLoading = true

      var queryData = {
        'courseClassID': courseClassID
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.queryCourseClassByUser(queryData, queryParam).then(re => {
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
              api = API.updateCourseClassByTeacher
            } else {
              api = API.createCourseClassByTeacher
            }
            api(_this.form).then(data => {
              if (data.code === '0') {
                _this.$message.success(data.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/teacherPage/education/courseClass')
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
        let lastcourseClassID = _this.form.courseClassID

        _this.$refs['form'].resetFields()

        _this.form = {
          courseClassID: null,
          courseID: null,
          teacherID: null,
          courseClassTime: null,
          courseClassAddress: null,
          courseClassWeek: null
        }
        _this.form.courseClassID = lastcourseClassID
      }).catch(() => {})
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  }
}
</script>
