<template>
  <div class="sc-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>选课记录列表</span>
          <el-button type="primary" @click="handleAdd">新增选课记录</el-button>
        </div>
      </template>

      <div class="search-container">
        <el-input
          v-model="searchSno"
          placeholder="请输入学生学号"
          style="width: 200px"
          clearable
          @clear="handleSearchClear"
          @keyup.enter="handleSnoSearch"
        />
        <el-button type="primary" @click="handleSnoSearch">按学号查询</el-button>
        
        <el-input
          v-model="searchCno"
          placeholder="请输入课程号"
          style="width: 200px"
          clearable
          @clear="handleSearchClear"
          @keyup.enter="handleCnoSearch"
        />
        <el-button type="primary" @click="handleCnoSearch">按课程号查询</el-button>
      </div>

      <el-table 
        :data="scList" 
        border 
        style="width: 100%" 
        v-loading="loading"
      >
        <el-table-column prop="sno" label="学号" width="180" align="center" />
        <el-table-column prop="cno" label="课程号" width="180" align="center" />
        <el-table-column prop="grade" label="成绩" width="120" align="center">
          <template #default="scope">
            {{ scope.row.grade || '未录入' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button
              size="small"
              type="primary"
              @click="handleEdit(scope.row)"
              style="border-radius: 0; margin-right: 5px;"
            >
              编辑
            </el-button>
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row.sno, scope.row.cno)"
              style="border-radius: 0;"
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
    <el-dialog 
      v-model="dialogVisible" 
      :title="dialogTitle" 
      width="40%"
    >
      <el-form 
        :model="form" 
        :rules="rules" 
        ref="formRef" 
        label-width="100px"
      >
        <el-form-item label="学号" prop="sno">
          <el-input 
            v-model="form.sno" 
            :disabled="isEdit"
          />
        </el-form-item>
        <el-form-item label="课程号" prop="cno">
          <el-input 
            v-model="form.cno" 
            :disabled="isEdit"
          />
        </el-form-item>
        <el-form-item label="成绩" prop="grade">
          <el-input-number 
            v-model="form.grade" 
            :min="0" 
            :max="100" 
            placeholder="请输入成绩（0-100）"
          />
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
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import {
  getScPage,
  deleteSc,
  getScAll,
  addSc,
  updateSc,
  getScByIds
} from '@/api/sc'

interface Sc {
  sno: string;
  cno: string;
  grade?: number; // 允许未录入成绩
}

const currentPage = ref(1); // 当前页
const pageSize = ref(10); // 每页显示的条数
const total = ref(0); // 数据总数
const scList = ref<Sc[]>([]); // 数据列表
const searchSno = ref('');
const searchCno = ref('');
const loading = ref(false);

const dialogVisible = ref(false);
const dialogTitle = ref('');
const isEdit = ref(false);
const formRef = ref<FormInstance>();
const form = ref<Sc>({
  sno: '',
  cno: '',
  grade: undefined
});

const rules = reactive<FormRules>({
  sno: [{ required: true, message: '请输入学号', trigger: 'blur' }], 
  cno: [{ required: true, message: '请输入课程号', trigger: 'blur' }],
  grade: [{ required: true, message: '请输入成绩', trigger: 'blur' }]
});

// 获取选课记录列表
const fetchScs = async (params?: Record<string, any>) => { 
  try {
    loading.value = true;
    const res = await getScPage(currentPage.value, pageSize.value, params);
    scList.value = res.data?.records || res.records || [];
    total.value = res.data?.total || res.total || 0;
  } catch (error) {
    console.error('获取选课记录失败:', error);
    ElMessage.error('获取选课记录失败');
  } finally {
    loading.value = false;
  }
};

// 按学号搜索
const handleSnoSearch = async () => {
  if (searchSno.value.trim()) { 
    try {
      const res = await getScAll(); 
      const filtered = res.filter(sc => sc.sno.includes(searchSno.value));
      scList.value = filtered;
      total.value = filtered.length;
    } catch (error) {
      ElMessage.error('按学号搜索失败');
    }
  } else {
    fetchScs();
  }
};

// 按课程号搜索
const handleCnoSearch = async () => {
  if (searchCno.value.trim()) {
    try {
      const res = await getScAll();
      const filtered = res.filter(sc => sc.cno.includes(searchCno.value));
      scList.value = filtered;
      total.value = filtered.length;
    } catch (error) {
      ElMessage.error('按课程号搜索失败');
    }
  } else {
    fetchScs();
  }
};

// 清空搜索
const handleSearchClear = () => {
  searchSno.value = '';
  searchCno.value = '';
  fetchScs();
};

// 分页处理
const handleSizeChange = (val: number) => {
  pageSize.value = val;
  fetchScs();
};

const handleCurrentChange = (val: number) => {
  currentPage.value = val;
  fetchScs();
};

// 新增选课记录
const handleAdd = () => {
  dialogTitle.value = '新增选课记录';
  isEdit.value = false;
  form.value = { sno: '', cno: '', grade: undefined };
  dialogVisible.value = true;
  nextTick(() => formRef.value?.clearValidate());
};

// 编辑选课记录
const handleEdit = (row: Sc) => {
  dialogTitle.value = '编辑选课记录';
  isEdit.value = true;
  form.value = { ...row }; // 复制选中行数据
  dialogVisible.value = true;
  nextTick(() => formRef.value?.clearValidate());
};

// 删除选课记录
const handleDelete = async (sno: string, cno: string) => {
  await ElMessageBox.confirm('确定删除该选课记录吗？', '提示', {
    type: 'warning'
  }).then(async () => {
    try {
      await deleteSc(sno, cno);
      ElMessage.success('删除成功');
      fetchScs();
    } catch (error) {
      ElMessage.error('删除失败');
    }
  });
};

// 提交表单
const submitForm = async () => {
  try {
    await formRef.value?.validate();
    
    if (isEdit.value) {
      // 编辑场景：根据学号和课程号更新
      await updateSc(form.value);
      ElMessage.success('修改成功');
    } else {
      // 新增场景
      await addSc(form.value);
      ElMessage.success('添加成功');
    }
    
    dialogVisible.value = false;
    fetchScs();
  } catch (error) {
    console.error('表单提交失败:', error);
    ElMessage.error('操作失败，请检查输入');
  }
};

onMounted(() => {
  fetchScs();
});
</script>

<style scoped>
.sc-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-container { /* 搜索栏样式 */
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.pagination-container { /* 分页容器样式 */
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: center; /* 使分页部分水平居中 */
}

.el-pagination {
  font-size: 20px; /* 根据需要调整字体大小 */
}

.el-pagination .number,
.el-pagination .btn-prev,
.el-pagination .btn-next,
.el-pagination .el-pager li {
  font-size: 20px !important; /* 确保内部元素字体大小一致 */
}

.el-table {
  margin-top: 20px;
}
</style>