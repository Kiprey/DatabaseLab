<template>
  <div class="app-container">
    <el-form :model="queryData" ref="queryForm" :inline="true">
      <el-form-item label="学生姓名：">
        <el-input v-model="queryData.studentName"></el-input>
      </el-form-item>

      <el-form-item label="学生编号：">
        <el-input v-model="queryData.studentID"></el-input>
      </el-form-item>

      <el-form-item label="班级编号：">
        <el-input v-model="queryData.classCode"></el-input>
      </el-form-item>

      <el-form-item label="班级名称：">
        <el-input v-model="queryData.className"></el-input>
      </el-form-item>

      <el-form-item label="专业名称：">
        <el-input v-model="queryData.majorName"></el-input>
      </el-form-item>

      <el-form-item label="院系名称：">
        <el-input v-model="queryData.facultyName"></el-input>
      </el-form-item>

      <el-form-item label="身份证号:">
        <el-input v-model="queryData.identifier"></el-input>
      </el-form-item>

      <el-form-item label="年级:">
        <el-input v-model="queryData.grade"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">查询</el-button>
        <router-link :to="{path:'/school/student/edit'}" class="link-left">
          <el-button type="primary">添加</el-button>
        </router-link>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
      <el-table-column prop="studentName" label="学生姓名" />
      <el-table-column prop="studentID" label="学生编号"/>
      <el-table-column prop="facultyCode" label="院系编号" />
      <el-table-column prop="facultyName" label="院系名称" />
      <el-table-column prop="majorCode" label="专业编号" />
      <el-table-column prop="majorName" label="专业名称" />
      <el-table-column prop="classCode" label="班级编号" />
      <el-table-column prop="className" label="班级名称" />
      <el-table-column prop="identifier" label="身份证号" />
      <el-table-column prop="dormitory" label="宿舍" />
      <el-table-column prop="address" label="家庭住址" />
      <el-table-column prop="teleno" label="手机" />
      <el-table-column prop="birthday" label="出生日期" width="160px"/>
      <el-table-column prop="sex" label="性别" width="60px;"/>
      <el-table-column prop="grade" label="年级" />
      <el-table-column prop="completedCredits" label="已修学分" />
      <el-table-column width="270px" label="操作" align="center">
        <template slot-scope="{row}">
          <router-link :to="{path:'/school/student/edit', query:{studentID:row.studentID}}" class="link-left">
            <el-button size="mini" >编辑</el-button>
          </router-link>
          <el-button  size="mini" type="danger" @click="deleteStudent(row)" class="link-left">删除</el-button>
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
        studentName: '',
        studentID: '',
        classCode: '',
        className: '',
        majorName: '',
        facultyName: '',
        identifier: '',
        grade: '',

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
        'className': this.queryData.className,
        'majorName': this.queryData.majorName,
        'facultyName': this.queryData.facultyName,
        'pageIndex': this.queryData.pageIndex,
        'pageSize': this.queryData.pageSize
      }
      API.queryStudent(this.queryData, queryParam).then(data => {
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
    deleteStudent (row) {
      let _this = this

      this.$confirm('确定删除 ?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        API.deleteStudent(row.studentID).then(re => {
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
