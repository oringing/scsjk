<template>
  <div class="student-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>学生列表</span>
          <el-button type="primary" @click="handleAdd">新增学生</el-button>
        </div>
      </template>

      <div class="search-container">
        <el-input
          v-model="searchName"
          placeholder="请输入学生姓名"
          style="width: 300px"
          clearable
          @clear="handleSearchClear"
          @keyup.enter="handleSearch"
        />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>

      <el-table :data="studentList" border style="width: 100%" v-loading="loading">
        <el-table-column prop="sno" label="学号" width="180" align="center" />
        <el-table-column prop="sname" label="姓名" width="180" align="center" />
        <el-table-column prop="ssex" label="性别" width="80" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.ssex === '男' ? 'primary' : 'danger'">
              {{ scope.row.ssex }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="sage" label="年龄" width="80" align="center" />
        <el-table-column prop="sdept" label="院系" align="center" />
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button
              size="small"
              type="primary"
              @click="handleEdit(scope.row)"
              style="border-radius: 0; background-color: #1989fa; color: white;"
            >
              修改
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row.sno)"
              style="border-radius: 0; background-color: #f5222d; color: white;"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 50]"
          :small="false"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="50%">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="学号" prop="sno" v-if="isEdit">
          <el-input v-model="form.sno" disabled />
        </el-form-item>
        <el-form-item label="学号" prop="sno" v-else>
          <el-input v-model="form.sno" />
        </el-form-item>
        <el-form-item label="姓名" prop="sname">
          <el-input v-model="form.sname" />
        </el-form-item>
        <el-form-item label="性别" prop="ssex">
          <el-radio-group v-model="form.ssex">
            <el-radio label="男" />
            <el-radio label="女" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="sage">
          <el-input-number v-model="form.sage" :min="15" :max="30" />
        </el-form-item>
        <el-form-item label="院系" prop="sdept">
          <el-input v-model="form.sdept" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, nextTick, reactive } from 'vue' // 确保reactive已导入
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import {
  getStudentPage,
  deleteStudent,
  searchStudentByName,
  addStudent,
  updateStudent
} from '@/api/student'

interface Student {
  sno: string
  sname: string
  ssex: string
  sage: number
  sdept: string
}

const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const studentList = ref<Student[]>([])
const searchName = ref('')
const loading = ref(false)

const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const form = ref<Student>({
  sno: '',
  sname: '',
  ssex: '男',
  sage: 20,
  sdept: ''
})

const rules = reactive<FormRules>({
  sno: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  sname: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  ssex: [{ required: true, message: '请选择性别', trigger: 'change' }],
  sage: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  sdept: [{ required: true, message: '请输入院系', trigger: 'blur' }]
})

// 获取学生列表
const fetchStudents = async () => {
  try {
    loading.value = true
    const res = await getStudentPage(currentPage.value, pageSize.value)
    studentList.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取学生数据失败:', error)
    ElMessage.error('获取学生数据失败')
  } finally {
    loading.value = false
  }
}

// 搜索学生
const handleSearch = async () => {
  if (!searchName.value.trim()) {
    fetchStudents()
    return
  }
  try {
    loading.value = true
    const res = await searchStudentByName(searchName.value)
    studentList.value = res
    total.value = res.length
  } catch (error) {
    console.error('搜索学生失败:', error)
    ElMessage.error('搜索学生失败')
  } finally {
    loading.value = false
  }
}

// 清空搜索
const handleSearchClear = () => {
  fetchStudents()
}

// 分页大小改变
const handleSizeChange = (val: number) => {
  pageSize.value = val
  fetchStudents()
}

// 当前页改变
const handleCurrentChange = (val: number) => {
  currentPage.value = val
  fetchStudents()
}

// 新增学生
const handleAdd = () => {
  dialogTitle.value = '新增学生'
  isEdit.value = false
  form.value = {
    sno: '',
    sname: '',
    ssex: '男',
    sage: 20,
    sdept: ''
  }
  dialogVisible.value = true
  nextTick(() => {
    formRef.value?.clearValidate()
  })
}

// 编辑学生
const handleEdit = (row: Student) => {
  dialogTitle.value = '编辑学生'
  isEdit.value = true
  form.value = { ...row }
  dialogVisible.value = true
  nextTick(() => {
    formRef.value?.clearValidate()
  })
}

// 删除学生
const handleDelete = (sno: string) => {
  ElMessageBox.confirm('确定删除该学生吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        await deleteStudent(sno)
        ElMessage.success('删除成功')
        fetchStudents()
      } catch (error) {
        console.error('删除失败:', error)
        ElMessage.error('删除失败')
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}

// 提交表单
const submitForm = async () => {
  try {
    await formRef.value?.validate()
    
    if (isEdit.value) {
      await updateStudent(form.value)
      ElMessage.success('修改成功')
    } else {
      await addStudent(form.value)
      ElMessage.success('添加成功')
    }
    
    dialogVisible.value = false
    fetchStudents()
  } catch (error) {
    console.error('表单提交失败:', error)
  }
}

onMounted(() => {
  fetchStudents()
})
</script>

<style scoped>
.student-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-container {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.el-table {
  margin-top: 20px;
}

.el-tag {
  margin: 2px;
}
</style>