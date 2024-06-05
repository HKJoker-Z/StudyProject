<template>
  <div class="about">
    <h1>This is an about page</h1>

    <el-menu router style="border: none" :default-active="$route.path">
          <el-menu-item index="/">
            <template slot="title">
              <i class="el-icon-house"></i>
              <span>系统首页</span>
            </template>
          </el-menu-item>

          <el-menu-item index="/about">About</el-menu-item>
          <el-menu-item>系统首页</el-menu-item>
          <el-submenu>
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item>用户信息</el-menu-item>
          </el-submenu>
        </el-menu>
  </div>
</template>
<script lang="ts">
</script>