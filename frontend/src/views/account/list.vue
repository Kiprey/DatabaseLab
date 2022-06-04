<template>
  <div class="app-container">
    <el-form :model="queryData" ref="queryForm" :inline="true">
      <el-form-item label="用户名:">
        <el-input v-model="queryData.username" placeholder="请输入完整用户名"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
        <router-link :to="{path:'/account/edit'}" class="link-left">
          <el-button type="primary">添加新账户</el-button>
        </router-link>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="role_name" label="权限" />
      <el-table-column width="270px" label="操作" align="center">
        <template slot-scope="{row}">
          <router-link :to="{path:'/account/edit', query:{username:row.username}}" class="link-left">
            <el-button size="mini" >添加授权</el-button>
          </router-link>
          <el-button  size="mini" type="danger" @click="revokeRole(row)" class="link-left">撤销权限</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryData.pageIndex" :limit.sync="queryData.pageSize"
                @pagination="search"/>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import API from '@/api/login'

export default {
  components: { Pagination },
  data () {
    return {
      queryData: {
        username: '',

        pageIndex: 1,
        pageSize: 10
      },
      listLoading: true,
      tableData: [],
      total: 0
    }
  },
  created () {
    this.search()
  },
  methods: {
    search () {
      this.listLoading = true
      var queryParam = {
        'pageIndex': this.queryData.pageIndex,
        'pageSize': this.queryData.pageSize
      }
      API.selectUserRole(this.queryData, queryParam).then(data => {
        let _this = this
        if (data.code === '0') {
          const re = data.data
          this.tableData = re.tableData
          this.total = re.total
          this.queryData.pageIndex = re.pageIndex
        } else {
          this.tableData = []
          this.total = 0
          this.queryData.pageIndex = 0
          _this.$message.error(data.message)
        }
        this.listLoading = false
      })
    },
    revokeRole (row) {
      let _this = this

      this.$confirm('确定撤销 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = {
          username: row.username,
          roleName: row.role_name,
          superCode: ''
        }
        let deletReq = (data) => {
          API.deleteRole(data).then(re => {
            if (re.code === '0') {
              _this.search()
              _this.$message.success(re.message)
            } else {
              _this.$message.error(re.message)
            }
          })
        }
        if (data.roleName === 'ADMIN') {
          this.$prompt(
            '请输入超级权限码：',
            '撤销管理员权限',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(({ value }) => {
            data.superCode = value
            deletReq(data)
          }).catch(() => {})
        } else {
          deletReq(data)
        }
      }).catch(() => {})
    },
    submitForm () {
      this.queryData.pageIndex = 1
      this.search()
    }
  }
}
</script>
