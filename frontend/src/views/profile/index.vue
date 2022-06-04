<template>
  <div class="app-container">
    <div>
      <el-row :gutter="20">

        <el-col :span="6" :xs="24">
          <user-card></user-card>
        </el-col>

        <el-col :span="18" :xs="24">
          <el-card>
            <el-tabs>
              <el-tab-pane label="学生账号" name="studentAccount" v-if="auth.includes('ROLE_STUDENT')" :key="changeTable">
                <StudentAccount></StudentAccount>
              </el-tab-pane>
              <el-tab-pane label="教师账号" name="teacherAccount" v-if="auth.includes('ROLE_TEACHER')" :key="changeTable">
                <TeacherAccount></TeacherAccount>
              </el-tab-pane>
              <el-tab-pane label="管理员账号" name="adminAccount" v-if="auth.includes('ROLE_ADMIN')" :key="changeTable">
                <label>管理员暂无个人信息</label>
              </el-tab-pane>
              <el-tab-pane label="修改密码" name="changepass">
                <ChangePass></ChangePass>
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>

      </el-row>
    </div>
  </div>
</template>

<script>
import UserCard from './components/UserCard'
import ChangePass from './components/ChangePass'
import StudentAccount from './components/StudentAccount'
import TeacherAccount from './components/TeacherAccount'
import { mapGetters } from 'vuex'

export default {
  name: 'Profile',
  components: { UserCard, ChangePass, StudentAccount, TeacherAccount },
  data () {
    return {
      auth: '',
      changeTable: false
    }
  },
  created () {
    this.auth = this.getUserAuthorities()
    this.changeTable = !this.changeTable
  },
  methods: {
    ...mapGetters('user', ['getUserAuthorities'])
  }
}
</script>
