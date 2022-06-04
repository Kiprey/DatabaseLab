<template>
  <div class="app-container">
    <el-form :model="queryData" ref="queryForm" :inline="true">
      <el-form-item label="院系编号">
        <el-input v-model="queryData.facultyCode"></el-input>
      </el-form-item>

      <el-form-item label="院系名称">
        <el-input v-model="queryData.facultyName"></el-input>
      </el-form-item>

      <el-form-item label="专业编号">
        <el-input v-model="queryData.majorCode"></el-input>
      </el-form-item>

      <el-form-item label="专业名称">
        <el-input v-model="queryData.majorName"></el-input>
      </el-form-item>

      <el-form-item label="学位等级">
        <el-input v-model="queryData.degreeLevel"></el-input>
      </el-form-item>

      <el-form-item label="毕业学分">
        <el-input v-model="queryData.graduationCredits"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
        <router-link :to="{path:'/school/major/edit'}" class="link-left">
          <el-button type="primary">添加</el-button>
        </router-link>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="facultyCode" label="院系编号" />
      <el-table-column prop="facultyName" label="院系名称"/>
      <el-table-column prop="majorCode" label="专业编号" />
      <el-table-column prop="majorName" label="专业名称" />
      <el-table-column prop="degreeLevel" label="学位等级" />
      <el-table-column prop="graduationCredits" label="毕业学分" />
      <el-table-column width="270px" label="操作" align="center">
        <template slot-scope="{row}">
          <router-link :to="{path:'/school/major/edit', query:{majorCode:row.majorCode}}" class="link-left">
            <el-button size="mini" >编辑</el-button>
          </router-link>
          <el-button  size="mini" type="danger" @click="deleteMajor(row)" class="link-left">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryData.pageIndex" :limit.sync="queryData.pageSize"
                @pagination="search"/>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import API from '@/api/school'

export default {
  components: { Pagination },
  data () {
    return {
      queryData: {
        facultyCode: '',
        facultyName: '',
        majorCode: '',
        majorName: '',
        degreeLevel: '',
        graduationCredits: '',

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
        'facultyName': this.queryData.facultyName,
        'pageIndex': this.queryData.pageIndex,
        'pageSize': this.queryData.pageSize
      }
      API.queryMajor(this.queryData, queryParam).then(async data => {
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
    deleteMajor (row) {
      let _this = this

      this.$confirm('确定删除 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        API.deleteMajor(row.majorCode).then(re => {
          if (re.code === '0') {
            _this.search()
            _this.$message.success(re.message)
          } else {
            _this.$message.error(re.message)
          }
        })
      }).catch(() => {})
    },
    submitForm () {
      this.queryData.pageIndex = 1
      this.search()
    }
  }
}
</script>
