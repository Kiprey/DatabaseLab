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
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="facultyCode" label="院系编号" />
      <el-table-column prop="facultyName" label="院系名称"/>
      <el-table-column prop="majorCode" label="专业编号" />
      <el-table-column prop="majorName" label="专业名称" />
      <el-table-column prop="degreeLevel" label="学位等级" />
      <el-table-column prop="graduationCredits" label="毕业学分" />
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
    async getFacultyName (i) {
      let _this = this

      var tmpqueryData = {
        'facultyCode': _this.tableData[i].facultyCode
      }
      var queryParam = {
        'pageIndex': 1,
        'pageSize': 1
      }

      return API.queryFaculty(tmpqueryData, queryParam)
    },
    search () {
      this.listLoading = true
      var queryParam = {
        'majorName': this.queryData.majorName,
        'pageIndex': this.queryData.pageIndex,
        'pageSize': this.queryData.pageSize
      }
      API.queryMajor(this.queryData, queryParam).then(async data => {
        let _this = this
        if (data.code === '0') {
          const re = data.data
          this.tableData = re.tableData
          for (let i = 0; i < this.tableData.length; i++) {
            try {
              let re = await _this.getFacultyName(i)
              if (re.code === '0') {
                _this.tableData[i].facultyName = re.data.tableData[0].facultyName
              } else {
                _this.$message.error('未获取到院系名称', _this.tableData[i].majorCode)
              }
            } catch (e) {
              console.log('getFacultyName fail: ', e)
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
