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

      <el-table :data="