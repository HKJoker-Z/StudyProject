

<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      {{user.name}}要当香港程序员！
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
    </div>

    <div style="display: flex; margin: 10px 0">

    </div>
  </div>
</template>

<script>
export default {
  name: "Shit",

  data() {
    return {
      user: JSON.parse(localStorage.getItem('honey-user') || {}),
    }
  }


}


</script>

<style scoped>

</style>