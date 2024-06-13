<template>
  <div>
    <el-container>
      <!--    侧边栏  -->
      <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #001529">
        <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
          <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px">
          <span class="logo-title" v-show="!isCollapse">赵二偷学Vue</span>
        </div>

        <el-menu :collapse="isCollapse" :collapse-transition="false" router background-color="#001529"
                 text-color="rgba(255, 255, 255, 0.65)" active-text-color="#fff" style="border: none"
                 :default-active="$route.path">
          <el-menu-item index="/">
            <i class="el-icon-menu"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-menu-item index="/1">
            <i class="el-icon-house"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-menu-item index="/2">
            <i class="el-icon-house"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item>用户信息</el-menu-item>
            <el-menu-item>管理员信息</el-menu-item>
            <el-menu-item index="/">系统首页</el-menu-item>
          </el-submenu>
        </el-menu>

      </el-aside>

      <el-container>
        <!--        头部区域-->
        <el-header>

          <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/user' }">用户管理</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <i class="el-icon-quanping" style="font-size: 26px" @click="handleFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: default">
                <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px; margin: 0 5px">
                <span>管理员</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-header>

        <!--        主体区域-->
        <el-main>
          <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
            我要当香港程序员！
          </div>
          <div style="display: flex">
            <el-card style="width: 50%; margin-right: 10px">
              <div slot="header" class="clearfix">
                <span>简单</span>
              </div>
              <div>
                真的ez，有困难模式吗？
                <div style="margin-top: 20px">
                  <div style="margin: 10px 0"><strong>主题色</strong></div>
                  <el-button type="primary">按钮</el-button>
                  <el-button type="success">按钮</el-button>
                  <el-button type="warning">按钮</el-button>
                  <el-button type="danger">按钮</el-button>
                  <el-button type="info">按钮</el-button>
                </div>
              </div>
            </el-card>

            <el-card style="width: 50%">
              <div slot="header" class="clearfix">
                <span>渲染用户的数据</span>
              </div>
              <div>
                <el-table :data="users">
                  <el-table-column label="id" prop="id"></el-table-column>
                  <el-table-column label="用户名" prop="username"></el-table-column>
                  <el-table-column label="姓名" prop="name"></el-table-column>
                  <el-table-column label="地址" prop="address"></el-table-column>
                  <el-table-column label="文件上传">
                    <template v-slot="scope">
                      <el-upload
                          action="http://localhost:9090/file/upload"
                          :headers="{token: user.token}"
                          :on-success="(row, file, fileList) => handleTableFileUpload(scope.row, file, fileList)"
                      >
                        <el-button size="mini" type="primary">单文件上传</el-button>
                      </el-upload>
                    </template>
                  </el-table-column>
                  <el-table-column label="文件上传">
                    <template v-slot="scope">
                      <el-image v-if="scope.row.avatar" :src="scope.row.avatar"
                                style="width: 50px; height: 50px"></el-image>
                      <div>
                        <el-button style="" @click="preView(scope.row.avatar)">预览</el-button>
                      </div>
                    </template>

                  </el-table-column>


                </el-table>
              </div>
            </el-card>
          </div>

          <div style="display: flex; margin: 10px 0">
            <el-card style="width: 50%; margin-right: 10px;">
              <div slot="header" class="clearfix">
                <span>文件上传下载</span>
              </div>
              <div>
                <el-upload
                    action="http://localhost:9090/file/upload"
                    :headers="{token: user.token}"
                    :on-success="handleFileUpload"
                >
                  <el-button size="mini" type="primary">单文件上传</el-button>
                </el-upload>
              </div>

              <div style="margin: 10px 0">
                <el-upload
                    action="http://localhost:9090/file/upload"
                    :headers="{token: user.token}"
                    :on-success="handleMultipleFileUpload"
                    multiple
                >
                  <el-button size="mini" type="success">多文件上传</el-button>
                </el-upload>
                <el-button type="primary" style="margin:  10px 0" @click="showUrls">显示上传的链接</el-button>
              </div>
            </el-card>
          </div>

        </el-main>

      </el-container>


    </el-container>
  </div>
</template>

<script>

import request from '@/utils/request'

export default {
  name: 'HomeView',
  data() {
    return {
      isCollapse: false,  // 不收缩
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      users: [],
      user: JSON.parse(localStorage.getItem('honey-user') || {}),
      urls: []
    }
  },

  mounted() {
    // axios.get('http://localhost:9090/user/selectAll').then(res => {
    //   console.log(res.data);
    //   this.users = res.data.data;
    // })
    request.get('/user/selectAll').then(res => {
      this.users = res.data;
    })
  },

  methods: {
    preView(url) {
        window.open(url);
    },

    showUrls() {
      console.log(this.urls)
    },

    handleMultipleFileUpload(row, file, fileList) {
      this.urls = fileList.map(v => v.response?.data)

    },

    handleTableFileUpload(row, file, fileList) {
      console.log(row, file, fileList);
      row.avatar = file.response.data
      // this.$set(row, 'avatar', file.response.data)
      console.log(row);
      //触发更新
      request.put('/user/update', row).then(res => {
        if (res.code == 200) {
          this.$message.success("上传成功")
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleFileUpload(response, file, fileList) {

    },
    handleFull() {
      document.documentElement.requestFullscreen()
    },
    handleCollapse() {
      this.isCollapse = !this.isCollapse
      this.asideWidth = this.isCollapse ? '64px' : '200px'
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
    },
    logout() {
      localStorage.removeItem('honey-user') //退出登录时清除浏览器中用户数据
      this.$router.push("/login")
    }
  }
}
</script>

<style>
.el-menu--inline {
  background-color: #000c17 !important;
}

.el-menu--inline .el-menu-item {
  background-color: #000c17 !important;
  padding-left: 49px !important;
}

.el-menu-item:hover, .el-submenu__title:hover {
  color: #fff !important;
}

.el-submenu__title:hover i {
  color: #fff !important;
}

.el-menu-item:hover i {
  color: #fff !important;
}

.el-menu-item.is-active {
  background-color: #1890ff !important;
  border-radius: 5px !important;
  width: calc(100% - 8px);
  margin-left: 4px;
}

.el-menu-item.is-active i, .el-menu-item.is-active .el-tooltip {
  margin-left: -4px;
}

.el-menu-item {
  height: 40px !important;
  line-height: 40px !important;
}

.el-submenu__title {
  height: 40px !important;
  line-height: 40px !important;
}

.el-submenu .el-menu-item {
  min-width: 0 !important;
}

.el-menu--inline .el-menu-item.is-active {
  padding-left: 45px !important;
}

/*.el-submenu__icon-arrow {*/
/*  margin-top: -5px;*/
/*}*/

.el-aside {
  transition: width .3s;
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
}

.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s; /* 0.3s */
}

.el-header {
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
  display: flex;
  align-items: center;
}
</style>