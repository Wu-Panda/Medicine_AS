<template>
  <el-menu :default-openeds="['1', '3']" style="height: 100vh; overflow-x: hidden"
           background-color="rgb(48, 65, 86)" text-color="#fff"
           active-text-color="#ffd04b" :collapse-transition = "false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center;">
      <img src="../assets/logo.png" style="width: 20px; height: 20px;position: relative; top: 5px;" alt="">
      <b style="color: #fff;margin-left: 5px" v-show="logoTextShow">药物管理系统</b>
    </div>
    <el-menu-item index="/home">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">主页</span>
      </template>
    </el-menu-item>
    <el-submenu index="1" >
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">基本管理</span>
      </template>
        <el-menu-item index="/user">
          <i class="el-icon-s-custom"></i>
          <span slot="title">用户管理</span>
        </el-menu-item>
      <el-menu-item index="/medicine">
        <i class="el-icon-suitcase"></i>
        <span slot="title">药品管理</span>
      </el-menu-item>
      <el-menu-item index="">
        <i class="el-icon-star-off"></i>
        <span slot="title">历史痕迹</span>
      </el-menu-item>
    </el-submenu>
    <el-submenu index="2" >
      <template slot="title">
        <i class="el-icon-more"></i>
        <span slot="title">更多</span>
      </template>
      <el-menu-item index="/information">
        <i class="el-icon-reading"></i>
        <span slot="title">系统简介</span>
      </el-menu-item>
      <el-menu-item index="/development">
        <i class="el-icon-data-analysis"></i>
        <span slot="title">开发流程</span>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  methods: {

  }
}
</script>

<style scoped>

</style>