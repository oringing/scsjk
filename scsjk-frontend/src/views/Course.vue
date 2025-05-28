<template>
  <div class="course-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>课程列表</span>
          <el-button type="primary" @click="handleAdd">新增课程</el-button>
        </div>
      </template>

      <div class="search-container">
        <el-input
          v-model="searchCname"
          placeholder="请输入课程名称"
          style="width: 300px"
          clearable
          @clear="handleSearchClear"
          @keyup.enter="handleSearch"
        />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>

      <el-table :data="courseList" border style="width: 100%" v-loading="loading">
        <el-table-column prop="cno" label="课程号" width="180" align="center" />
        <el-table-column prop="cname" label="课程名" width="180" align="center" />
        <el-table-column prop="cpno" label="先修课号" width="180" align="center" />
        <el-table-column prop="ccredit" label="学分" width="80" align="center" />
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
              @click="handleDelete(scope.row.cno)"
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
        <el-form-item label="课程号" prop="cno" v-if="isEdit">
          <el-input v-model="form.cno" disabled />
        </el-form-item>
        <el-form-item label="课程号" prop="cno" v-else>
          <el-input v-model="form.cno" />
        </el-form-item>
        <el-form-item label="课程名" prop="cname">
          <el-input v-model="form.cname" />
        </el-form-item>
        <el-form-item label="先修课号" prop="cpno">
          <el-input v-model="form.cpno" />
        </el-form-item>
        <el-form-item label="学分" prop="ccredit">
          <el-input-number v-model="form.ccredit" :min="1" :max="10" />
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
  getCoursePage,
  deleteCourse,
  getCourseAll,
  addCourse,
  updateCourse,
  getCourseById
} from '@/api/course'

interface Course {
  cno: string;
  cname: string;
  cpno: string | null;
  ccredit: number;
}

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const courseList = ref<Course[]>([]);
const searchCname = ref('');
const loading = ref(false);

const dialogVisible = ref(false);
const dialogTitle = ref('');
const isEdit = ref(false);
const formRef = ref<FormInstance>();
const form = ref<Course>({
  cno: '',
  cname: '',
  cpno: null,
  ccredit: 1
});

const rules = reactive<FormRules>({
  cno: [{ required: true, message: '请输入课程号', trigger: 'blur' }],
  cname: [{ required: true, message: '请输入课程名', trigger: 'blur' }],
  ccredit: [{ required: true, message: '请输入学分', trigger: 'blur' }]
});

// 获取课程列表
const fetchCourses = async () => {
  try {
    loading.value = true;
    const res = await getCoursePage(currentPage.value, pageSize.value);
    courseList.value = res.data?.records || res.records || [];
    total.value = res.data?.total || res.total || 0;
  } catch (error) {
    console.error('获取课程数据失败:', error);
    ElMessage.error('获取课程数据失败');
  } finally {
    loading.value = false;
  }
};

// 搜索课程
const handleSearch = async () => {
  if (!searchCname.value.trim()) {
    fetchCourses();
    return;
  }
  try {
    loading.value = true;
    const res = await getCourseAll();
    const filtered = res.filter((course: Course) => course.cname.includes(searchCname.value));
    courseList.value = filtered;
    total.value = filtered.length;
  } catch (error) {
    console.error('搜索课程失败:', error);
    ElMessage.error('搜索课程失败');
  } finally {
    loading.value = false;
  }
};

// 清空搜索
const handleSearchClear = () => {
  fetchCourses();
};

// 分页大小改变
const handleSizeChange = (val: number) => {
  pageSize.value = val;
  fetchCourses();
};

// 当前页改变
const handleCurrentChange = (val: number) => {
  currentPage.value = val;
  fetchCourses();
};

// 新增课程
const handleAdd = () => {
  dialogTitle.value = '新增课程';
  isEdit.value = false;
  form.value = {
    cno: '',
    cname: '',
    cpno: null,
    ccredit: 1
  };
  dialogVisible.value = true;
  nextTick(() => {
    formRef.value?.clearValidate();
  });
};

// 编辑课程
const handleEdit = (row: Course) => {
  dialogTitle.value = '编辑课程';
  isEdit.value = true;
  form.value = { ...row };
  dialogVisible.value = true;
  nextTick(() => {
    formRef.value?.clearValidate();
  });
};

// 删除课程
const handleDelete = (cno: string) => {
  ElMessageBox.confirm('确定删除该课程吗?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
   .then(async () => {
      try {
        await deleteCourse(cno);
        ElMessage.success('删除成功');
        fetchCourses();
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
      await updateCourse(form.value);
      ElMessage.success('修改成功');
    } else {
      await addCourse(form.value);
      ElMessage.success('添加成功');
    }
    dialogVisible.value = false;
    fetchCourses();
  } catch (error) {
    console.error('表单提交失败:', error);
  }
};

onMounted(() => {
  fetchCourses();
});
</script>

<style scoped>
.course-container {
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