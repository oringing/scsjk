<template>
  <div>
    <div class="header">
      <div class="logo">
        <img src="@/assets/logo.jpg" alt="学院 logo" />
      </div>
      <h1 class="title">学生选课管理系统</h1>
    </div>
    <nav class="nav">
      <router-link to="/student" class="nav-item">学生列表</router-link>
      <router-link to="/course" class="nav-item">课程列表</router-link>
      <router-link to="/sc" class="nav-item">选课成绩列表</router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script setup>
// 这里不需要额外的逻辑，使用setup语法糖简化
</script>

<style scoped>
/* 定义头部样式 */
.header {
  display: flex;
  align-items: center;
  background-color: #66ccff;
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

/* 新增导航栏样式 */
.nav {
  background-color: #f8f9fa;
  padding: 10px;
}

.nav-item {
  margin-right: 10px;
  padding: 5px 10px;
  text-decoration: none;
  color: #343a40;
  border-radius: 4px;
  transition: background-color 0.3s ease;
  background-color: transparent; /* 默认背景颜色为透明 */
}

.nav-item:hover {
  background-color: #e9ecef;
}

/* 添加激活状态的样式 */
.router-link-active {
  background-color: #70a7ff; /* 激活状态背景颜色为蓝色 */
  color: white; /* 激活状态文字颜色为白色 */
}
</style>

<!-- <template>
  <div>
    <div class="header">
      <div class="logo">
        <img src="@/assets/logo.jpg" alt="学院 logo" />
      </div>
      <h1 class="title">学生选课管理系统</h1>
    </div>
    <nav class="nav">
      <router-link to="/student" class="nav-item">学生列表</router-link>
      <router-link to="/course" class="nav-item">课程列表</router-link>
      <router-link to="/sc" class="nav-item">选课成绩列表</router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script setup>
// 这里不需要额外的逻辑，使用setup语法糖简化
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

/* 新增导航栏样式 */
.nav {
  background-color: #f8f9fa;
  padding: 10px;
}

.nav-item {
  margin-right: 10px;
  padding: 5px 10px;
  text-decoration: none;
  color: #343a40;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.nav-item:hover {
  background-color: #e9ecef;
}
</style>


<!-- <template>
  <div>
    <div class="header">
      <div class="logo">
        <img src="@/assets/logo.jpg" alt="学院 logo" />
      </div>
      <h1 class="title">学生选课管理系统</h1>
    </div>
    <router-view></router-view> //添加这行以确保路由内容显示
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
</style> --> -->