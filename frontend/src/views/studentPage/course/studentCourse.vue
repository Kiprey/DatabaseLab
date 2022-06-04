<template>
  <div class="app-container">
    <el-form :model="queryData" ref="queryForm" :inline="true">
      <el-form-item label="开课号">
        <el-input v-model="queryData.courseClassID"></el-input>
      </el-form-item>

      <el-form-item label="学生编号">
        <el-input v-model="queryData.studentID"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
        <router-link :to="{path:'/studentPage/course/studentCourseEdit'}" class="link-left">
          <el-button type="primary">添加</el-button>
        </router-link>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="courseClassID" label="开课号" />
      <el-table-column prop="studentID" label="学生编号" />
      <el-table-column prop="studentName" label="学生姓名" />
      <el-table-column prop="score" label="课程分数"/>
      <el-table-column prop="courseName" label="课程名称" />

      <el-table-column width="270px" label="操作" align="center">
        <template slot-scope="{row}">
          <el-button  size="mini" type="danger" @click="deleteStudentCourse(row)" class="link-left">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryData.pageIndex" :limit.sync="queryData.pageSize"
                @pagination="search"/>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import API from '@/api/curriculum'

export default {
  components: { Pagination },
  data () {
    return {
      queryData: {
        courseClassID: '',
        studentID: '',

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
      API.queryStudentCourseByStudent(this.queryData, queryParam).then(data => {
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
    deleteStudentCourse (row) {
      let _this = this
      var QParam = {
        'courseClassID': row.courseClassID,
      }
      this.$confirm('确定删除 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        API.deleteStudentCourseByStudent(QParam).then(re => {
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
