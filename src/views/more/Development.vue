<template>

  <div style="height: 600px ; margin-left: 30%;" >
    <p style="margin-top: 30px; margin-left: 180px;margin-bottom: 80px; font-size: 22px;">开发流程</p>
    <el-steps direction="vertical" :active="4" :space="100" :align-center=true style="margin-left: 70px;" finish-status="success">
      <el-step title="步骤 1" description="创建基础类，例如User、Medicine、History等"></el-step>
      <el-step title="步骤 2" description="实现接口，例如UserMapper、MedicineMapper等"></el-step>
      <el-step title="步骤 3" description="完善相关功能，例如登录、注册、查询等"></el-step>
      <el-step title="步骤 3" description="美化界面"></el-step>
    </el-steps>
  </div>
</template>

<script>
export default {
  name: "Development"
}
</script>

<style scoped>

</style>