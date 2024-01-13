<template>
  <div>
    <div style="margin-bottom: 15px;">
      <el-input suffix-icon="el-icon-search" placeholder="请输入内容" style="width: 200px" v-model="searchInput">
      </el-input><el-button style="margin-left: 10px" type="primary" @click="search">搜索</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline" style="margin-left: 3px"></i></el-button>
      <el-popconfirm
          style="margin-left: 8px"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定删除吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" style="margin-left: 3px;margin-right: 3px">删除<i class="el-icon-remove-outline" ></i></el-button>
      </el-popconfirm>
      <el-upload action="http://localhost:9090/user/import" style="display: inline-block" :show-file-list="false"
                 accept="xlsx" :on-success="handleExcelImportSuccess" :on-error="handleExcelImportFailed">
        <el-button type="primary" style="margin-left: 8px">导入<i class="el-icon-upload2"  style="margin-left: 3px">
        </i></el-button>
      </el-upload>
      <el-button type="primary" @click="exportInfo" style="margin-left: 10px">导出<i class="el-icon-download" style="margin-left: 3px"></i></el-button>
    </div>
    <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" ></el-table-column>
      <el-table-column prop="id" label="ID" width="70">
      </el-table-column>
      <el-table-column prop="medicineName" label="药品名称" width="100">
      </el-table-column>
      <el-table-column prop="medicineSpec" label="规格" width="100">
      </el-table-column>
      <el-table-column prop="manufacturer" label="生产厂家">
      </el-table-column>
      <el-table-column prop="expiryDate" label="有效期" width="100">
      </el-table-column>
      <el-table-column prop="address" label="价格" width="50">
      </el-table-column>
      <el-table-column prop="indications" label="适应症" width="100">
      </el-table-column>
      <el-table-column prop="contraindications" label="禁忌症" width="100">
      </el-table-column>
      <el-table-column prop="control" label="操作" >
        <template slot-scope="scope">
          <el-button type="primary" plain @click="handleEdit(scope.row)" >编辑
            <i class="el-icon-edit" style="margin-left: 3px"></i>
          </el-button>
          <el-popconfirm
              style="margin-left: 8px"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="handleDelete(scope.row.id)"
          >
            <el-button type="danger" plain  slot="reference">删除
              <i class="el-icon-delete" style="margin-left: 3px"></i>
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="药物信息" :visible.sync="dialogFormVisible" width="40%" >
      <el-form label-width="100px" :model="form" :rules="rules" ref="ruleForm" required>
        <el-form-item label="药物名称">
          <el-input v-model="form.medicineName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="规格">
          <el-input v-model="form.medicineSpec" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="生产厂家">
          <el-input v-model="form.manufacturer" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="有效期" prop="expiryDate">
          <el-date-picker type="date" placeholder="选择日期" v-model="form.expiryDate" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="适应症">
          <el-input v-model="form.indications" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="禁忌症">
          <el-input v-model="form.contraindications" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Medicine",
  created(){
    this.load()
  },
  data() {
    return {
      tableData: [] ,
      total: 0,
      pageNum: 1,
      pageSize: 5,
      searchInput: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      rules:{
        expiryDate: [
          {  type: 'date', required: true, message: '请选择日期', trigger: 'change'  }
        ],
      }
    }
  },
  methods: {
    // 加载表格信息
    load(){
      this.request.get("/medicine/page", {
        params:{
          pageNum : this.pageNum,
          pageSize : this.pageSize
        }
      }).then(res =>{
        //console.log(res)
        this.tableData = res.data;
        this.total = res.total;
      })
    },
    // 添加信息
    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },
    // 保存信息
    save(){
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          // Get the current date
          const currentDate = new Date();
          if(this.form.expiryDate <= currentDate){
            this.$message.error("日期必须大于当前日期！");
            return false;
          }
          this.form.expiryDate = this.form.expiryDate.toLocaleDateString('en-CA');
          this.request.post("/medicine", this.form).then(res => {
            if (res) {
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error("保存失败")
              this.dialogFormVisible = false
            }
          })
        }
      })
    },
    // 编辑信息
    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true
    },
    // 删除信息
    handleDelete(id){
      this.request.delete("medicine/" + id).then(res =>{
        if(res){
          this.$message.success("删除成功！")
          this.load()
        }else{
          this.$message.error("删除失败！")
        }
      })
    },
    // 改变当前表格大小
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    // 改变当前表格数量
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    // 获取选中的表格信息
    handleSelectionChange(val){
      //console.log(val)
      this.multipleSelection = val
    },
    // 删除选中的行
    delBatch(){
      //console.log(this.multipleSelection)
      let ids = this.multipleSelection.map(v => v.id)
      //console.log(ids)
      this.request.post("medicine/del/batch", ids).then(res =>{
        if(res){
          this.$message.success("批量删除成功！")
          this.load()
        }else{
          this.$message.error("批量删除失败！")
        }
      })
    },
    // 模糊搜索
    search(){
      //console.log(this.searchInput)
      this.request.get("/medicine/search", {
        params:{
          searchInput : this.searchInput
        }
      }).then(res =>{
        //console.log(res)
        this.tableData = res.data;
        this.total = res.total;
        this.searchInput = ""
      });
    },
    //导出
    exportInfo(){
      window.open("http://localhost:9090/medicine/export");
    },
    // 导入成功
    handleExcelImportSuccess(){
      this.$message.success("导入成功！")
      this.load()
    },
    handleExcelImportFailed(){
      this.$message.error("导入失败！")
    }
  }
}
</script>

<style scoped>

</style>