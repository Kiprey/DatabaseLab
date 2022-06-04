<template>
  <div class="app-container">
    <el-form :model="queryData" ref="queryForm" :inline="true">
      <el-form-item label="专业编号">
        <el-input v-model="queryData.majorCode"></el-input>
      </el-form-item>

      <el-form-item label="专业名称">
        <el-input v-model="queryData.majorName"></el-input>
      </el-form-item>

      <el-form-item label="班级编号">
        <el-input v-model="queryData.classCode"></el-input>
      </el-form-item>

      <el-form-item label="班级名称">
        <el-input v-model="queryData.className"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="majorCode" label="专业编号" />
      <el-table-column prop="majorName" label="专业名称"/>
      <el-table-column prop="classCode" label="班级编号" />
      <el-table-column prop="className" label="班级名称" />
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
        className: '',
        classCode: '',
        majorName: '',
        majorCode: '',

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
    async getMajorName (i) {
      let _this = this

      var tmpqueryData = {
        'majorCode': _this.tableData[i].majorCode
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      return API.queryMajor(tmpqueryData, queryParam)
    },
    search () {
      this.listLoading = true
      var queryParam = {
        'majorName': this.queryData.majorName,
        'pageIndex': this.queryData.pageIndex,
        'pageSize': this.queryData.pageSize
      }
      API.queryClass(this.queryData, queryParam).then(async data => {
        let _this = this
        if (data.code === '0') {
          const re = data.data
          this.tableData = re.tableData
          for (let i = 0; i < this.tableData.length; i++) {
            try {
              let re = await _this.getMajorName(i)
              if (re.code === '0') {
                _this.tableData[i].majorName = re.data.tableData[0].majorName
              } else {
                _this.$message.error('未获取到专业名称', _this.tableData[i].majorCode)
              }
            } catch (e) {
              console.log('getMajorName fail: ', e)
            }
          }
          _this.tableData = JSON.parse(JSON.stringify(_this.tableData))

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
