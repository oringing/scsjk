<template>
  <div class="student-container">
    <el-card>
      <template #header>
        <div class="flex justify-between items-center">
          <h3>学生信息管理</h3>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </div>
      </template>
      <el-input v-model="searchName" placeholder="按姓名搜索" style="width: 200px; margin-bottom: 10px;"></el-input>
      <el-table :data="studentList" stripe border>
        <el-table-column prop="sno" label="学号"></el-table-column>
        <el-table-column prop="sname" label="姓名"></el-table-column>
        <el-table-column prop="ssex" label="性别"></el-table-column>
        <el-table-column prop="sage" label="年龄"></el-table-column>
        <el-table-column prop="sdept" label="系别"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope.row.sno)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
      <el-dialog :visible.sync="dialogVisible" title="学生信息">
        <StudentForm :formData="formData" :isEdit="isEdit" @updateFormData="updateFormData" />
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </template>
      </el-dialog>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import {
  getStudentPage,
  getStudentAll,
  getStudentById,
  addStudent,
  updateStudent,
  deleteStudent,
  searchStudentByName
} from '@/api/student'
import { ElMessage } from 'element-plus'
import StudentForm from '@/components/StudentForm.vue'

// 数据
const studentList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const searchName = ref('')
const dialogVisible = ref(false)
const formData = ref({
  sno: '',
  sname: '',
  ssex: '男',
  sage: null,
  sdept: ''
})
const isEdit = ref(false)

// 获取学生列表
const fetchData = async () => {
  try {
    let res
    if (searchName.value) {
      res = await searchStudentByName(searchName.value)
      studentList.value = res
    } else {
      res = await getStudentPage(currentPage.value, pageSize.value)
      studentList.value = res.records
      total.value = res.total
    }
  } catch (error) {
    console.error(error)
    ElMessage.error('获取学生信息失败')
  }
}

// 生命周期钩子
onMounted(() => {
  fetchData()
})

// 分页相关
const handleSizeChange = (newSize) => {
  pageSize.value = newSize
  fetchData()
}

const handleCurrentChange = (newPage) => {
  currentPage.value = newPage
  fetchData()
}

// 新增
const handleAdd = () => {
  isEdit.value = false
  formData.value = {
    sno: '',
    sname: '',
    ssex: '男',
    sage: null,
    sdept: ''
  }
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  isEdit.value = true
  formData.value = { ...row }
  dialogVisible.value = true
}

// 删除
const handleDelete = async (sno) => {
  ElMessage.confirm('确定要删除该学生信息吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteStudent(sno)
      ElMessage.success('删除成功')
      fetchData()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  }).catch(() => {
    // 取消操作
  })
}

// 提交表单
const submitForm = async () => {
  try {
    if (isEdit.value) {
      await updateStudent(formData.value)
      ElMessage.success('更新成功')
    } else {
      await addStudent(formData.value)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    fetchData()
  } catch (error) {
    ElMessage.error(isEdit.value? '更新失败' : '添加失败')
  }
}

const updateFormData = (newData) => {
  formData.value = { ...formData.value, ...newData }
}
</script>

<style scoped>
.student-container {
  padding: 20px;
}
</style>