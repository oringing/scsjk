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
        <el-select v-model="searchType" placeholder="请选择查询类型">
          <el-option label="姓名查询" value="name"></el-option>
          <el-option label="学号查询" value="sno"></el-option>
        </el-select>
        <div class="custom-search-input">
          <el-input
            v-model="searchValue"
            :placeholder="searchType === 'name' ? '请输入学生姓名' : '请输入学生学号'"
            clearable
            @clear="handleSearchClear"
            @keyup.enter="handleSearch"
          />
        </div>
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
          :layout="'total, sizes, prev, pager, next, jumper'"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          class="custom-pagination"
        >
          <!-- 自定义 total 文本 -->
          <template #total>
            共计 {{ total }} 条数据
          </template>
          <!-- 自定义 sizes 文本 -->
          <template #sizes>
            <el-select v-model="pageSize" @change="handleSizeChange">
              <el-option
                v-for="item in [5, 10, 20, 50]"
                :key="item"
                :label="item + '/页'"
                :value="item"
              />
            </el-select>
          </template>
          <!-- 自定义 jumper 文本 -->
          <template #jumper>
            跳转到
            <el-input
              v-model.number="jumpPage"
              size="small"
              @keyup.enter="handleJump"
              @blur="handleJump"
            />
          </template>
        </el-pagination>
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
import { ref, onMounted, nextTick, reactive, watch } from 'vue';
import { useRoute } from 'vue-router';
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus';
import {
  getStudentPage,
  deleteStudent,
  searchStudentByName,
  addStudent,
  updateStudent,
  getStudentBySno
} from '@/api/student';

interface Student {
  sno: string;
  sname: string;
  ssex: string;
  sage: number;
  sdept: string;
}

const currentPage = ref(1); // 当前页
const pageSize = ref(10); // 每页显示的条数
const total = ref(0); // 总条数
const studentList = ref<Student[]>([]); // 学生列表
const searchType = ref('name');   
const searchValue = ref(''); // 搜索值
const loading = ref(false); // 添加/修改对话框显示状态

const dialogVisible = ref(false); // 添加/修改对话框显示状态
const dialogTitle = ref(''); // 对话框标题
const isEdit = ref(false); // 是否为编辑场景
const formRef = ref<FormInstance>();  // 表单实例
const form = ref<Student>({ 
  sno: '',
  sname: '',
  ssex: '男',
  sage: 20,
  sdept: ''
}); // 表单数据

const rules = reactive<FormRules>({  // 表单验证规则
  sno: [{ required: true, message: '请输入学号', trigger: 'blur' }], 
  sname: [{ required: true, message: '请输入姓名', trigger: 'blur' }], 
  ssex: [{ required: true, message: '请选择性别', trigger: 'change' }],
  sage: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  sdept: [{ required: true, message: '请输入院系', trigger: 'blur' }]
});

// 获取学生列表
const fetchStudents = async () => {
  try {
    loading.value = true;
    const res = await getStudentPage(currentPage.value, pageSize.value);
    studentList.value = res.records;
    total.value = res.total;
  } catch (error) {
    console.error('获取学生数据失败:', error);
    ElMessage.error('获取学生数据失败');
  } finally {
    loading.value = false;
  }
};

// 搜索学生
const handleSearch = async () => { 
  //检查搜索值是否为空：为空则显示全部学生
  if (!searchValue.value.trim()) {
    fetchStudents();
    return;
  }
  try {
    loading.value = true;//设置加载状态
    let res: any; //定义变量res，用于接收搜索结果
    if (searchType.value === 'name') { //如果搜索类型为姓名
      res = await searchStudentByName(searchValue.value); //调用searchStudentByName方法
    } else if (searchType.value === 'sno') { //如果搜索类型为学号
      res = await getStudentBySno(searchValue.value); //调用getStudentBySno方法
      res = [res]; //将结果转换为数组
    }
    studentList.value = res; //将结果赋值给studentList变量
    total.value = res.length; //设置总记录数
  } catch (error) { //如果搜索失败
    console.error('搜索学生失败:', error); //打印错误信息
    ElMessage.error('搜索学生失败');
  } finally {
    loading.value = false; //结束加载状态
  }
};

// 清空搜索
const handleSearchClear = () => {
  fetchStudents();
};

// 分页大小改变
const handleSizeChange = (val: number) => {
  pageSize.value = val;
  fetchStudents();
};

// 当前页改变
const handleCurrentChange = (val: number) => {
  currentPage.value = val;
  fetchStudents();
};

// 新增学生
const handleAdd = () => {
  dialogTitle.value = '新增学生';
  isEdit.value = false;
  form.value = {
    sno: '',
    sname: '',
    ssex: '男',
    sage: 20,
    sdept: ''
  };
  dialogVisible.value = true;
  nextTick(() => {
    formRef.value?.clearValidate();
  });
};

// 编辑学生
const handleEdit = (row: Student) => {
  dialogTitle.value = '编辑学生';
  isEdit.value = true;
  form.value = { ...row };
  dialogVisible.value = true;
  nextTick(() => {
    formRef.value?.clearValidate();
  });
};

// 删除学生
const handleDelete = (sno: string) => {
  ElMessageBox.confirm('确定删除该学生吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        await deleteStudent(sno);
        ElMessage.success('删除成功');
        fetchStudents();
      } catch (error) {
        console.error('删除失败:', error);
        ElMessage.error('删除失败');
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除');
    });
};

// 提交表单
const submitForm = async () => {
  try {
    await formRef.value?.validate();

    if (isEdit.value) {
      await updateStudent(form.value);
      ElMessage.success('修改成功');
    } else {
      await addStudent(form.value);
      ElMessage.success('添加成功');
    }

    dialogVisible.value = false;
    fetchStudents();
  } catch (error) {
    console.error('表单提交失败:', error);
  }
};

// 跳转页码
const jumpPage = ref(1);

const handleJump = () => {
  if (jumpPage.value > 0 && jumpPage.value <= Math.ceil(total.value / pageSize.value)) {
    currentPage.value = jumpPage.value;
    fetchStudents();
  } else {
    ElMessage.warning('请输入正确的页码');
  }
};

onMounted(() => {
  fetchStudents();
});
</script>

<style scoped> 
.student-container { /* 容器样式 */
  padding: 20px;
}

.card-header { /* 标题样式 */
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-container {  /* 搜索框样式 */
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  width: 500px; 
  justify-content: center; /* 搜索框居中 */
}

.custom-search-input {
  align-items: center;
  display: inline-block;
  width: 800px; /* 搜索框宽度适应内容 */
  margin: 0 10px; /* 调整左右间距 */
}

.pagination-container { 
  margin-top: 20px;
  display: flex;
  justify-content: center; /* 页数部分居中 */
}

.custom-pagination ::v-deep .el-pagination__total,
.custom-pagination ::v-deep .el-pagination__sizes,
.custom-pagination ::v-deep .el-pagination__jump {
  font-size: 20px; /* 字体调大一倍 */
}

.el-table { /* 表格样式 */
  margin-top: 20px;
}

.el-tag { /* 课程标签样式 */
  margin: 2px;
}
</style>
<!-- 
 

  <div class="student-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>学生列表</span>
          <el-button type="primary" @click="handleAdd">新增学生</el-button>
        </div>
      </template>

      <div class="search-container">
        <el-select v-model="searchType" placeholder="请选择查询类型">
          <el-option label="姓名查询" value="name"></el-option>
          <el-option label="学号查询" value="sno"></el-option>
        </el-select>
        <el-input
          v-model="searchValue"
          :placeholder="searchType === 'name'? '请输入学生姓名' : '请输入学生学号'"
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
            <el-tag :type="scope.row.ssex === '男'? 'primary' : 'danger'">
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
import { ref, onMounted, nextTick, reactive } from 'vue' 
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import {
  getStudentPage,
  deleteStudent,
  searchStudentByName,
  addStudent,
  updateStudent,
  getStudentBySno
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
const searchType = ref('name')
const searchValue = ref('')
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
    // 强制更新视图
    this.$forceUpdate() 
  } catch (error) {
    //console.error('获取学生数据失败:', error)
    //ElMessage.error('获取学生数据失败')
  } finally {
    loading.value = false
  }
}

// 搜索学生
const handleSearch = async () => {
  if (!searchValue.value.trim()) {
    fetchStudents()
    return
  }
  try {
    loading.value = true
    let res: any
    if (searchType.value === 'name') {
      res = await searchStudentByName(searchValue.value)
    } else if (searchType.value === 'sno') {
      res = await getStudentBySno(searchValue.value)
      res = [res]
    }
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
/* 新增或修改以下样式 */
.search-container input {
  width: 100px; /* 根据实际情况调整宽度值，这里设置为150px，你可以按需修改 */
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

   -->



