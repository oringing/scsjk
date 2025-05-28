<template>
  <div>
    <div class="header">
      <div class="logo">
        <img src="@/assets/logo.jpg" alt="学院 logo" />
      </div>
      <h1 class="title">学生选课管理系统</h1>
    </div>
    <router-view></router-view> <!-- 添加这行以确保路由内容显示 -->
  </div>
</template>

<script>
export default {
  name: 'App'  // 修改组件名称为App
};
</script>

<style scoped>
/* 定义头部样式 */
.header {
  display: flex;
  align-items: center;
  background-color: #70a7ff;
  color: white;
  height: 150px;
  padding-right: 20px;
}

.title {
  font-size: 50px;
  font-weight: bold;
  margin: 0;
  flex: 1;
  text-align: center;
}

.logo {
  flex-shrink: 0;
  height: 100%;
  margin: 0;
}

.logo img {
  width: 100%;
  height: 100%;
  object-fit: contain; /* 确保图片比例正确 */
}
</style>