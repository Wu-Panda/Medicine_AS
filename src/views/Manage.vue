<template>
  <el-container style="height: 100vh;">
    <el-aside width="sideWidth +'px'" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(0 21 41 / 35%)" >
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
    </el-aside>

    <el-container>
      <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :pathName="pathName"/>
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
// @ is an alias to /src
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Manage',
  components: {
    Aside,
    Header
  },
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      pathName: ""
    }
  },
  methods:{
    // 左向伸缩
    collapse() {
      this.isCollapse = !this.isCollapse;
      if(this.isCollapse){
        this.sidewidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      }
      else{
        this.collapseBtnClass = 'el-icon-s-fold'
        this.sidewidth = 200
        this.logoTextShow = true
      }
    },
  }
}
</script>

