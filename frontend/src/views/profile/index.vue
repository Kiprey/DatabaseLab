<template>
  <div class="app-container">
    <div>
      <el-row :gutter="20">

        <el-col :span="6" :xs="24">
          <user-card></user-card>
        </el-col>

        <el-col :span="18" :xs="24">
          <el-card>
            <el-tabs active-name="account">
              <el-tab-pane label="账号" name="account">
                <account></account>
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
import Account from './components/Account'

export default {
  name: 'Profile',
  components: { UserCard, ChangePass, Account }
}
</script>
