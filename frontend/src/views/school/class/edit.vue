<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="专业编号："  prop="majorCode" required>
        <el-input v-model="form.majorCode" @blur="majorCodeBlur"></el-input>
      </el-form-item>

      <el-form-item label="专业名称：" prop="majorName">
        <el-input v-model="form.majorName" :readonly="'readonly'" placeholder="输入专业编号自动更新"></el-input>
      </el-form-item>

      <el-form-item label="班级编号：" prop="classCode" required>
        <el-input v-model="form.classCode" :disabled="isEditMode ? 'disabled' : false"></el-input>
      </el-form-item>

      <el-form-item label="班级名称：" prop="className">
        <el-input v-model="form.className"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import API from '@/api/school'

export default {
  data () {
    return {
      form: {
        majorCode: '',
        majorName: '',
        classCode: '',
        className: ''
      },
      isEditMode: false,
      formLoading: false,
      rules: {
        majorCode: [
          { required: true, message: '请输入专业编号', trigger: 'blur' }
        ],
        classCode: [
          { required: true, message: '请输入班级编号', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    let classCode = this.$route.query.classCode
    let _this = this
    if (classCode && classCode !== '') {
      _this.isEditMode = true
      _this.formLoading = true

      var queryData = {
        'classCode': classCode
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.queryClass(queryData, queryParam).then(re => {
        if (re.code === '0') {
          _this.form = re.data.tableData[0]
          _this.majorCodeBlur()
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
    majorCodeBlur () {
      let _this = this

      _this.formLoading = true
      var tmpqueryData = {
        'majorCode': _this.form.majorCode
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.queryMajor(tmpqueryData, queryParam).then(re => {
        if (re.code === '0' && re.data.tableData[0].majorCode === _this.form.majorCode) {
          _this.form.majorName = re.data.tableData[0].majorName
          _this.$message.success('专业名称获取成功')
          // 手动更新 this.form 以触发 el-input 载入时自动更新
          _this.form = JSON.parse(JSON.stringify(_this.form))
        } else {
          _this.form.majorName = ''
          _this.$message.error('未获取到专业名称')
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
              api = API.updateClass
            } else {
              api = API.createClass
            }
            api(_this.form).then(data => {
              if (data.code === '0') {
                _this.$message.success(data.message)
                _this.delCurrentView(_this).then(() => {
                  _this.$router.push('/school/class/list')
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
        let classCode = _this.form.classCode

        _this.$refs['form'].resetFields()

        _this.form = {
          majorCode: '',
          majorName: '',
          classCode: '',
          className: ''
        }
        _this.form.classCode = classCode
      }).catch(() => {})
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      sexEnum: state => state.user.sexEnum
    })
  }
}
</script>
