<template>
  <div
      style="height: 100vh; overflow: hidden; display: flex; align-items: center; justify-content: center; background-color: #1890ff">
    <div style="display: flex; background-color: white; width: 50%; border-radius: 5px; overflow: hidden;">
      <div style="flex: 1">
        <img src="@/assets/login.png" alt="" style="width: 100%">
      </div>
      <div style="flex: 1; display: flex; align-items: center; justify-content: center;">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="loginRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px">
            欢迎登录后台管理系统
          </div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号"
                      v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码"
                      v-model="user.password"></el-input>
          </el-form-item>

          <el-form-item prop="code">
            <div style="display: flex">
              <el-input prefix-icon="el-icon-check" style="flex: 1" v-model="user.code"
                        placeholder="请输入验证码"></el-input>
              <div style="flex: 1; height: 36px">
                <valid-code @update:value="getCode"/>
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button @click="login" type="primary" style="width: 100%">
              登 录
            </el-button>
          </el-form-item>
          <div style="display: flex">
            <div style="flex: 1">还没有账号？请<span style="color: #1890ff; cursor: pointer"
                                                    @click="$router.push('/register')">注册</span></div>
            <div style="flex: 1; text-align: right"><span style="color: #1890ff; cursor: pointer">忘记密码</span></div>
          </div>
        </el-form>
      </div>
    </div>


  </div>
</template>


<script>
import ValidCode from "@/conponents/ValidCode.vue";

export default {
  name: "Login.vue",
  components: {
    ValidCode
  },
  data() {

    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'))
      } else if (value.toLowerCase() !== this.code) {
        callback(new Error('验证码错误'))
      } else {
        callback()
      }
    }

    return {
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        code: [
          {validator: validateCode, trigger: 'blur'}
        ],
      },

      code: '',//验证码组件传递过来的code
      user: {
        code: '',//表单里用户输入的code
        username: '',
        password: ''
      },
    }
  },

  methods: {
    login() {
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/login', this.user).then(res => {
            if (res.code === '200') {
              this.$router.push('/')
              this.$message.success('登录成功')
              localStorage.setItem("honey-user", JSON.stringify(res.data))  // 存储用户数据
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },

    getCode(code) {
      this.code = code.toLowerCase()
    }
  },
}
</script>


<style scoped>

</style>