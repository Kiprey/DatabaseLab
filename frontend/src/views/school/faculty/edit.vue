<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="院系编号："  prop="facultyCode" required>
        <el-input v-model="form.facultyCode" :disabled="isEditMode ? 'disabled' : false"></el-input>
      </el-form-item>

      <el-form-item label="院系名称：" prop="facultyName" required>
        <el-input v-model="form.facultyName"></el-input>
      </el-form-item>

      <el-form-item label="院系地址：" prop="facultyAddress">
        <el-input v-model="form.facultyAddress"></el-input>
      </el-form-item>

      <el-form-item label="院系电话：" prop="facultyTeleno">
        <el-input v-model="form.facultyTeleno"></el-input>
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
import API from '@/api/school'

export default {
  data () {
    return {
      form: {
        facultyCode: '',
        facultyName: '',
        facultyAddress: '',
        facultyTeleno: ''
      },
      isEditMode: false,
      formLoading: false,
      rules: {
        facultyCode: [
          { required: true, message: '请输入院系编号', trigger: 'blur' }
        ],
        facultyName: [
          { required: true, message: '请输入院系名称', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    let facultyCode = this.$route.query.facultyCode
    let _this = this
    if (facultyCode && facultyCode !== '') {
      _this.isEditMode = true
      _this.formLoading = true

      var queryData = {
        'facultyCode': facultyCode
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.queryFaculty(queryData, queryParam).then(re => {
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
              api = API.updateFaculty
            } else {
              api = API.createFaculty
            }
            api(_this.form).then(data => {
              if (data.code === '0') {
                _this.$message.success(data.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/school/faculty/list')
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
        let facultyCode = _this.form.facultyCode

        _this.$refs['form'].resetFields()

        _this.form = {
          facultyCode: '',
          facultyName: '',
          facultyAddress: '',
          facultyTeleno: ''
        }
        _this.form.facultyCode = facultyCode
      }).catch(() => {})
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  }
}
</script>
