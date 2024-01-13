<template>

  <div style="line-height: 60px; display: flex; font-size: 12px ">
    <div style="flex: 1;">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 18px" @click="collapse"></span>
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item>
          <router-link :to="'/'">首页</router-link>
        </el-breadcrumb-item>
        <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 100px;cursor:pointer;">
      <div style="display: inline-block">
        <img :src="user.avatarUrl" alt="" style="width: 30px;border-radius: 50%;position:relative;top: 10px;right: 5px">
        <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="text-align: center">
        <el-dropdown-item>
          <span @click="$router.push('/person')">个人信息</span>
        </el-dropdown-item>
        <el-dropdown-item>
          <span @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass: String,
    collapse: Function,
  },
  data() {
    return{
      user: localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },
  computed:{
    currentPathName() {
      return this.$store.state.currentPathName;
    }
  },
  watch: {
    currentPathName(newVal, oldVal) {
      //console.log(newVal)
    }
  },
  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功！")
    }
  }
}
</script>

<style scoped>

</style>