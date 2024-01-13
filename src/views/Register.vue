<template>
  <div class="wrapper" >
    <div style="margin: 200px auto;background-color: #fff;width: 400px;height: 350px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px auto; text-align: center;font-size: 24px;"><b>注 册 界 面</b></div>
      <el-form :model="user" :rules="rules" ref="userForm" style="text-align: center">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 5px 0; width: 260px" placeholder="请输入用户名"
                    prefix-icon="el-icon-user" v-model="user.username" >
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin:5px 0; width: 260px" placeholder="请输入密码"
                    prefix-icon="el-icon-lock" show-password v-model="user.password">
          </el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input size="medium" style="margin: 5px 0; width: 260px" placeholder="请确认密码" autocomplete="off"
                    prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword">
          </el-input>
        </el-form-item>

        <el-form-item style="margin:10px 0;text-align: center;">
          <el-button type="primary" size="small" autocomplete="off" @click="confirm" style="margin-right: 20px">确定</el-button>
          <el-button type="primary" size="small" autocomplete="off" @click="$router.push('/login')">返回</el-button>
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
          { required: true, message: '请输入密码', trigger: 'blur'},
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods:{
    confirm(){
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          if(this.user.password !== this.user.confirmPassword){
            this.$message.error("两次输入密码不一致！");
            return false;
          }
          this.request.post("/user/register", this.user).then(res =>{
            if(res.code === "200") {
              this.$message.success("注册成功！");
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