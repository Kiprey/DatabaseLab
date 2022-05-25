<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="院系编号："  prop="facultyCode" required>
        <el-input v-model="form.facultyCode" @blur="facultyCodeBlur"></el-input>
      </el-form-item>

      <el-form-item label="院系名称：" prop="facultyName">
        <el-input v-model="form.facultyName" :readonly="'readonly'" placeholder="输入院系编号自动更新"></el-input>
      </el-form-item>

      <el-form-item label="专业编号：" prop="majorCode" required>
        <el-input v-model="form.majorCode" :disabled="isEditMode ? 'disabled' : false"></el-input>
      </el-form-item>

      <el-form-item label="专业名称：" prop="majorName" required>
        <el-input v-model="form.majorName"></el-input>
      </el-form-item>

      <el-form-item label="学位等级：" prop="degreeLevel" required>
        <el-select v-model="form.degreeLevel" clearable>
          <el-option v-for="item in degreeEnum" :key="item" :value="item" :label="item"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="毕业学分：" prop="graduationCredits" required>
        <el-input v-model="form.graduationCredits"></el-input>
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
        majorCode: '',
        majorName: '',
        facultyCode: '',
        facultyName: '',
        degreeLevel: '',
        graduationCredits: ''
      },
      isEditMode: false,
      formLoading: false,
      degreeEnum: [ '学士', '硕士', '博士' ],
      rules: {
        majorCode: [
          { required: true, message: '请输入专业编号', trigger: 'blur' }
        ],
        majorName: [
          { required: true, message: '请输入专业名称', trigger: 'blur' }
        ],
        facultyCode: [
          { required: true, message: '请输入院系编号', trigger: 'blur' }
        ],
        degreeLevel: [
          { required: true, message: '请输入学位等级', trigger: 'blur' }
        ],
        graduationCredits: [
          { required: true, message: '毕业学分格式不正确', trigger: 'blur', pattern: /^\d{1,3}$/ }
        ]
      }
    }
  },
  created () {
    let majorCode = this.$route.query.majorCode
    let _this = this
    if (majorCode && majorCode !== '') {
      _this.isEditMode = true
      _this.formLoading = true

      var queryData = {
        'majorCode': majorCode
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.queryMajor(queryData, queryParam).then(re => {
        if (re.code === '0') {
          _this.form = re.data.tableData[0]
          _this.facultyCodeBlur()
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
    facultyCodeBlur () {
      let _this = this

      _this.formLoading = true
      var tmpqueryData = {
        'facultyCode': _this.form.facultyCode
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.queryFaculty(tmpqueryData, queryParam).then(re => {
        if (re.code === '0' && re.data.tableData[0].facultyCode === _this.form.facultyCode) {
          _this.form.facultyName = re.data.tableData[0].facultyName
          _this.$message.success('院系名称获取成功')
          // 手动更新 this.form 以触发 el-input 载入时自动更新
          _this.form = JSON.parse(JSON.stringify(_this.form))
        } else {
          _this.form.majorName = ''
          _this.$message.error('未获取到院系名称')
        }
        _this.formLoading = false
      }).catch(e => {
        _this.formLoading = false
      })
    },
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
              api = API.updateMajor
            } else {
              api = API.createMajor
            }
            api(_this.form).then(data => {
              if (data.code === '0') {
                _this.$message.success(data.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/school/major/list')
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
        let majorCode = _this.form.majorCode

        _this.$refs['form'].resetFields()

        _this.form = {
          majorCode: '',
          majorName: '',
          facultyCode: '',
          facultyName: '',
          degreeLevel: '',
          graduationCredits: ''
        }
        _this.form.majorCode = majorCode
      }).catch(() => {})
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  }
}
</script>
