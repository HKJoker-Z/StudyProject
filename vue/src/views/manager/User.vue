<template>
 <div>

   <div>
    <el-input style="width: 200px" placeholder="查询用户名" v-model="username"></el-input>
    <el-input style="width: 200px; margin: 0 8px" placeholder="查询姓名" v-model="name"></el-input>
     <el-button type="primary" @click="load(1)" >查询</el-button>
     <el-button type="info" @click="reset" >重置</el-button>
   </div>

   <div style="margin: 10px 0 ">
     <el-button type="primary" plain @click="handleAdd">新增</el-button>
     <el-button type="danger" plain @click="delBatch" >批量删除</el-button>
   </div>

   <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666'}" @selection-change="handleSelectionChange" >
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="id" label="序号" width="70" ></el-table-column>
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
      <el-table-column label="操作" align="center" width="180px">
        <template v-slot="scope">
          <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)" >编辑</el-button>
          <el-button size="mini" type="danger" plain @click="del(scope.row.id)" >删除</el-button>
        </template>
      </el-table-column>
   </el-table>

    <div style="margin: 10px 0">
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[100, 200, 300, 400]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="收货地址" :visible.sync="fromVisible" width="30%">
      <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input type="textarea" v-model="form.address" placeholder="地址"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-radio-group v-model="form.role">
            <el-radio label="管理员"></el-radio>
            <el-radio label="用户"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/file/upload"
              :headers="{ token: user.token }"
              :file-list="form.avatar? [form.avatar] : []"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

 </div>
</template>

<script>
export default {
  name: "User",

  data() {
    return {
      tableData: [],
      pageNum: 1, //当前页码
      pageSize: 10,  //每页多少个
      username: '',
      name: '',
      total: 0,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('honey-user') || {}),
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ]
      },
      ids: [],
    }
  },

  created() {
    this.load()
  },

  methods: {
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning("请选择数据！")
        return
      }
      this.$confirm("确认要把它们全都删除吗？", "确认删除", {type : "warning"}).then(resp => {
        this.$request.delete('/user/delete/batch', {
          data: this.ids
        }).then(res => {
          if (res.code == '200'){
            this.$message.success("删除成功！")
            this.load(1)
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },

    handleSelectionChange(rows) {//当前选中的所有的行的数据
      this.ids = rows.map(v => v.id)

    },

    del(id) {
      this.$confirm("确认要删除吗？", "确认删除", {type : "warning"}).then(resp => {
        this.$request.delete('/user/delete/' + id).then(res => {
          if (res.code == '200'){
            this.$message.success("删除成功！")
            this.load(1)
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },

    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))//给form对象复制 要深拷贝数据
      this.fromVisible = true//打开弹窗
    },

    handleAdd() {
      this.form = {role: "用户"}//新增数据的时候清空数据
      this.fromVisible = true//打开弹窗
    },

    save() {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/user/update' : '/user/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code == '200') {
              this.$message.success("保存成功！")
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },

    reset() {
      this.name = ''
      this.username = ''
      this.load()
    },

    load(pageNum) {
      if (pageNum) {
        this.pageNum = pageNum
      }
      this.$request.get("/user/selectByPage",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          name: this.name
        }

      }).then((res) => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },

    handleAvatarSuccess(res, file, fileList) {
      this.form.avatar = res.data
    },

  },
}
</script>

<style scoped>

</style>