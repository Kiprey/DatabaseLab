<template>
  <div class="app-container">
    <el-form :model="queryData" ref="queryForm" :inline="true">
      <el-form-item label="教师姓名">
        <el-input v-model="queryData.teacherName"></el-input>
      </el-form-item>

      <el-form-item label="院系代码">
        <el-input v-model="queryData.facultyCode"></el-input>
      </el-form-item>

      <el-form-item label="院系名称">
        <el-input v-model="queryData.facultyName"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="teacherName" label="教师姓名" />
      <el-table-column prop="facultyCode" label="院系代码"/>
      <el-table-column prop="facultyName" label="院系名称" />
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryData.pageIndex" :limit.sync="queryData.pageSize"
                @pagination="search"/>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import API from '@/api/education'

export default {
  components: { Pagination },
  data () {
    return {
      queryData: {
        teacherName: '',
        facultyCode: '',
        facultyName: '',

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
      API.queryTeacherWithoutID(this.queryData, queryParam).then(data => {
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
    submitForm () {
      this.queryData.pageIndex = 1
      this.search()
    }
  }
}
</script>
