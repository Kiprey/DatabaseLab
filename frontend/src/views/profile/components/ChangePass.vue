<template>
  <el-form :model="userInfo" ref="form" :rules="rules">
    <el-form-item label="旧密码" prop="oldPassword" required>
      <el-input v-model.trim="userInfo.oldPassword" :type="'password'" />
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword" required>
      <el-input v-model.trim="userInfo.newPassword" :type="'password'" />
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmnewpass" required>
      <el-input v-model.trim="userInfo.confirmnewpass" :type="'password'" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">修改密码</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import API from '@/api/login'
export default {
  name: 'changepass',
  data () {
    const validatePassword = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error(rule.message))
      } else {
        callback()
      }
    }
    const validateConfirmPass = (rule, value, callback) => {
      if (value === '' || value !== this.userInfo.newPassword) {
        callback(new Error(rule.message))
      } else {
        callback()
      }
    }
    return {
      userInfo: {
        oldPassword: '',
        newPassword: '',
        confirmnewpass: ''
      },
      rules: {
        oldPassword: [
          { required: true, message: '密码不能少于5个字符', trigger: 'blur', validator: validatePassword }
        ],
        newPassword: [
          { required: true, message: '密码不能少于5个字符', trigger: 'blur', validator: validatePassword }
        ],
        confirmnewpass: [
          { required: true, message: '两次密码不匹配', trigger: 'blur', validator: validateConfirmPass }
        ]
      }
    }
  },
  methods: {
    submit () {
      let _this = this
      _this.$refs.form.validate((valid) => {
        if (valid) {
          _this.$confirm('确定修改密码 ?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            API.changePass(_this.userInfo).then(re => {
              if (re.code === '0' || re.code === '200') {
                _this.$message.success(re.message)
              } else {
                _this.$message.error(re.message)
              }
            })
          }).catch(() => {})
        } else {}
      })
    }
  }
}
</script>
