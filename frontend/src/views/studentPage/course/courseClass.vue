<template>
  <div class="app-container">
    <el-form :model="queryData" ref="queryForm" :inline="true">
      <el-form-item label="开课号">
        <el-input v-model="queryData.courseClassID"></el-input>
      </el-form-item>

      <el-form-item label="课程编号">
        <el-input v-model="queryData.courseID"></el-input>
      </el-form-item>

      <el-form-item label="课程名称">
        <el-input v-model="queryData.courseName"></el-input>
      </el-form-item>

      <el-form-item label="教师姓名">
        <el-input v-model="queryData.teacherName"></el-input>
      </el-form-item>

      <el-form-item label="开课时间">
        <el-input v-model="queryData.courseClassTime"></el-input>
      </el-form-item>

      <el-form-item label="开课地点">
        <el-input v-model="queryData.courseClassAddress"></el-input>
      </el-form-item>

      <el-form-item label="开课周">
        <el-input v-model="queryData.courseClassWeek"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="courseClassID" label="开课号" />
      <el-table-column prop="courseID" label="课程编号" />
      <el-table-column prop="courseName" label="课程名称" />
      <el-table-column prop="teacherName" label="教师姓名"/>
      <el-table-column prop="courseClassTime" label="开课时间" width="160px"/>
      <el-table-column prop="courseClassAddress" label="开课地点" />
      <el-table-column prop="courseClassWeek" label="开课周"/>
      <el-table-column width="270px" label="操作" align="center">
        <template slot-scope="{row}">
          <el-button  size="mini" type="danger" @click="selectStudentCourse(row)" class="link-left">选课</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryData.pageIndex" :limit.sync="queryData.pageSize"
                @pagination="search"/>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import API from '@/api/education'
import curriculumAPI from '@/api/curriculum'

export default {
  components: { Pagination },
  data () {
    return {
      queryData: {
        courseClassID: '',
        courseID: '',
        courseName: '',
        teacherName: '',
        courseClassTime: '',
        courseClassAddress: '',
        courseClassWeek: '',

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
      API.queryCourseClassByUser(this.queryData, queryParam).then(data => {
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
    },
    selectStudentCourse () {
      let _this = this
      this.$confirm('确定选课 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        _this.$refs.queryForm.validate((valid) => {
          if (valid) {
            curriculumAPI.createStudentCourseByStudent(_this.form).then(data => {
              if (data.code === '0') {
                _this.$message.success(data.message)
              } else {
                _this.$message.error(data.message)
              }
            }).catch(e => {
              _this.$message.error(e)
            })
          } else {
            return false
          }
        })
      }).catch(() => {})
    }
  }
}
</script>
