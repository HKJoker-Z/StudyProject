<template>
 <div>

   <el-table :data="tableData" stripe>
      <el-table-column prop="id" label="序号"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="phone" label="手机号"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="头像">
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            <el-image v-if="scope.row.avatar" :src="scope.row.avatar" style="height: 50px; width: 50px; border-radius: 50px"
            :preview-src-list="[scope.row.avatar]"
          ></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="role" label="角色"></el-table-column>
      <el-table-column label="操作">
        <el-button size="mini" type="primary" plain >编辑</el-button>
        <el-button size="mini" type="danger" plain >删除</el-button>
      </el-table-column>
   </el-table>
 </div>
</template>

<script>
export default {
  name: "User",

  data() {
    return {
      tableData: []
    }
  },

  created() {
    this.load()
  },

  methods: {
    load() {
      this.$request.get("/user/selectAll").then((res) => {
        this.tableData = res.data;
      })
    }
  },

}
</script>

<style scoped>

</style>