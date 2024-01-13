<template>
  <div>
    <div style="height: 60px; line-height: 60px; text-align: center;" >
        <img src="./medicine.png" style="width: 30px; height: 30px;position: relative; top: 8px" >
        <b style="font-size: 22px; margin-left: 10px">药物管理系统</b>
    </div>
    <div style="margin: 0 90px; padding: 20px 0" >
      <p style="font-size: 18px; font-weight: lighter;">本系统的最终用户为医疗机构的药物管理员以及相关医护人员。</p>
      <p style="font-size: 18px; font-weight: lighter; margin-top: 10px">根据用户需求，将药物管理系统划分为以下两个部分：</p>
    </div>
    <div style="justify-content: center;display: flex">
      <el-card shadow="hover" style="width: 80%;">
        <div style="text-align: center">
          <i class="el-icon-user-solid"></i>
          <b style="font-size: 20px; margin-left: 5px">医务人员</b>
        </div>
        <div style="padding: 20px 0;margin-left: 30px">
          <ul style="list-style-type: none;">
            <li>
              <b>处理医务人员注册</b>
              <p style="margin: 10px 0 25px 33px;">医务人员可以进行系统注册，提供必要的个人信息和身份验证。</p>
            </li>
            <li>
              <b>处理医务人员登录</b>
              <p style="margin: 10px 0 25px 33px;">医务人员使用注册信息登录系统，确保合法访问。</p>
            </li>
            <li>
              <b>查询药品信息</b>
              <p style="margin: 10px 0 25px 33px;">医务人员能够查询药品的基本情况、库存信息、价格、效果与副作用等。</p>
            </li>
            <li>
              <b>药品申领</b>
              <p style="margin: 10px 0 25px 33px;">医务人员能够向系统提交药品申领请求，系统记录申领时间、申领数量以及领取的医生信息。</p>
            </li>
            <li>
              <b>查询药品领用历史</b>
              <p style="margin: 10px 0 25px 33px;">医务人员可以查询已领取的药品历史记录，以追踪患者用药情况。</p>
            </li>
          </ul>
        </div>
      </el-card>
    </div>
    <div style="justify-content: center;display: flex; margin-top: 50px;margin-bottom: 100px">
      <el-card shadow="hover" style="width: 80%;">
        <div style="text-align: center">
          <i class="el-icon-user"></i>
          <b style="font-size: 20px; margin-left: 5px">药品管理员</b>
        </div>
        <div style="padding: 20px 0;margin-left: 30px">
          <ul style="list-style-type: none;">
            <li>
              <b>处理药品管理员登录</b>
              <p style="margin: 10px 0 25px 33px;">药品管理员使用合法的身份信息登录系统，确保合法访问。</p>
            </li>
            <li>
              <b>查询药品信息</b>
              <p style="margin: 10px 0 25px 33px;">药品管理员能够查询所有药品的详细信息，包括药品的基本情况、库存信息、价格、效果与副作用等。</p>
            </li>
            <li>
              <b>药品入库</b>
              <p style="margin: 10px 0 25px 33px;">药品管理员能够录入新的药品入库信息，包括药品名称、规格、数量、生产厂家、效果与副作用等。</p>
            </li>
            <li>
              <b>药品出库</b>
              <p style="margin: 10px 0 25px 33px;">药品管理员记录药品出库信息，包括出库时间、数量以及接收药品的医疗科室或护士。</p>
            </li>
            <li>
              <b>药品效果与副作用更新</b>
              <p style="margin: 10px 0 25px 33px;">药品管理员能够及时更新药品的效果与副作用信息，以提供医务人员最新的用药参考。</p>
            </li>
            <li>
              <b>系统安全性与完整性管理</b>
              <p style="margin: 10px 0 0 33px;">药品管理员负责维护系统的安全性，确保只有合法用户可以访问和修改药品信息；</p>
              <p style="margin: 5px 0 25px 33px;">药品管理员负责数据的完整性，包括对药品信息的准确性、库存数量的正确性等。</p>
            </li>
          </ul>
        </div>
      </el-card>
    </div>
  </div>


</template>

<script>
export default {
  name: "Information",
  data(){
    return {

    }
  }
}
</script>

<style scoped>

</style>