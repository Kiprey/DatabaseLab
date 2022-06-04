<template>
  <div class="app-container">

    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading" :rules="rules">
      <el-form-item label="用户名："  prop="username" required>
        <el-input
          ref="username"
          v-model="form.username"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
          :disabled="isEditMode ? 'disabled' : false"
        />
      </el-form-item>

      <el-form-item label="新密码：" prop="password" v-if="!isEditMode" required>
        <el-input
            ref="password"
            v-model="form.password"
            :type="'password'"
            name="password"
            tabindex="2"
            auto-complete="on"
          />
      </el-form-item>

      <el-form-item label="新增权限：" prop="roleName" required>
        <el-select v-model="form.roleName" clearable name="roleName">
          <el-option v-for="item in authEnum" :key="item" :value="item" :label="item"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import API from '@/api/login'

export default {
  data () {
    let _this = this
    const validateUsername = (rule, value, callback) => {
      if (value == null || value.length < 5) {
        callback(new Error('用户名不能少于5个字符'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (!_this.isEditMode && (value == null || value.length < 5)) {
        console.log('validatePassword,' + _this.isEditMode)
        callback(new Error('密码不能少于5个字符'))
      } else {
        callback()
      }
    }
    return {
      form: {
        username: null,
        roleName: null,
        password: null,
        superCode: null
      },
      authEnum: ['STUDENT', 'TEACHER'],
      formLoading: false,
      rules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }],
        roleName: [{ required: true, message: '请选择待授权的类型', trigger: 'blur' }]
      },
      isEditMode: false
    }
  },
  created () {
    let username = this.$route.query.username
    let _this = this
    if (username && username !== '') {
      _this.isEditMode = true
      _this.formLoading = true
      _this.authEnum.push('ADMIN')

      var queryData = {
        'username': username
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      API.selectUserRole(queryData, queryParam).then(re => {
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

      _this.$refs.form.validate((valid) => {
        if (valid) {
          this.$confirm('确定提交 ?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let req = () => {
              _this.formLoading = true
              let prom = null
              if (_this.isEditMode) {
                prom = API.insertRole({
                  username: _this.form.username,
                  superCode: _this.form.superCode,
                  roleName: _this.form.roleName
                })
              } else {
                prom = API.userRegister({
                  username: _this.form.username,
                  password: _this.form.password,
                  role: _this.form.roleName
                })
              }
              prom.then(data => {
                if (data.code === '0') {
                  _this.$message.success(data.message)
                  _this.delCurrentView(_this).then(() => {
                    _this.$router.push('/account/list')
                  })
                } else {
                  _this.$message.error(data.message)
                  _this.formLoading = false
                }
              }).catch(e => {
                _this.formLoading = false
              })
            }
            if (_this.form.roleName === 'ADMIN') {
              this.$prompt(
                '请输入超级权限码：',
                '赋予管理员权限',
                {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning'
                }).then(({ value }) => {
                _this.form.superCode = value
                req()
              }).catch(() => {})
            } else {
              req()
            }
          }).catch(() => {})
          return true
        } else {
          return false
        }
      })
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  }
}
</script>
