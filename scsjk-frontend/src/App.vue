<template>
  <el-container>
    <el-header>
      <h1>学生选课管理系统</h1>
    </el-header>
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
</script>

<style>
.el-header {
  background-color: #409eff;
  color: white;
  text-align: center;
  line-height: 60px;
}
.el-main {
  padding: 20px;
}
</style>