<template>
  <div class="wrapper">
    <div style="margin: 200px auto;background-color: #fff;width: 350px;height: 300px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px auto; text-align: center;font-size: 24px;"><b>登 陆 界 面</b></div>
      <el-form :model="user" :rules="rules" ref="userForm" style="text-align: center">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0; width: 260px"
                    prefix-icon="el-icon-user" v-model="user.username" >
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0; width: 260px"
                    prefix-icon="el-icon-lock" show-password v-model="user.password">
          </el-input>
        </el-form-item>

        <el-form-item style="margin:10px 0;text-align: center">
          <el-button type="primary" size="small" autocomplete="off" @click="login">登陆</el-button>
          <el-button type="primary" size="small" autocomplete="off" @click="$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods:{
    login(){
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          this.request.post("/user/login", this.user).then(res =>{
            if(res.code === "200") {
              localStorage.setItem("user", JSON.stringify(res.data)); // 存储用户信息
              this.$router.push("/");
              this.$message.success("登陆成功！");
            }else {
              this.$message.error(res.msg);
            }
          })
        }
      });
    },
  }
}
</script>

<style scoped>
  .wrapper{
    height: 100vh;
    background-image: linear-gradient(to bottom right, #FC4668, #3F5EFB);
    overflow: hidden;
  }
</style>