<template>
  <el-container>
    <!--搜索栏-->
    <div>
      <el-input style="width: 250px" placeholder="请输入菜单名称" suffix-icon="el-icon-search" v-model="menuname" ></el-input>
      <el-button class="ml-20" type="success" round  @click="load">  搜索   <i class="el-icon-loading"></i></el-button>
      <el-button class="ml-30" type="warning" round @click="reset">  重置   <i class="el-icon-refresh"></i></el-button>
      <el-button class="ml-20" type="primary"  @click="AddUser">  新增(父级)  <i class="el-icon-circle-plus"></i></el-button>

      <el-popconfirm class="ml-10" confirm-button-text='是的' cancel-button-text='不用了'  icon="el-icon-info"
                     icon-color="red" title="您确定批量删除这些菜单吗？"  @confirm="handleDeleteBatch">
        <el-button class="ml-10" type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
      <!--表格-->
      <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" style="margin-top: 20px"
                @selection-change="handleSelectionChange" row-key="id"><!-- row-key 在id列加上子级 -->
        <el-table-column type="selection" width="40px"></el-table-column>
        <el-table-column prop="id" label="id" width="80px"> </el-table-column>
        <el-table-column prop="pid" label="pid" width="50px"  > </el-table-column>
        <el-table-column prop="menuname" label="菜单名" width="120"> </el-table-column>
        <el-table-column prop="path" label="路径" width="90px">  </el-table-column>
        <el-table-column label="图标" width="100px">
          <template slot-scope="scope">
            <i :class="scope.row.icon" style="font-size: 24px; margin-left: 20px"></i>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" width="300px">   </el-table-column>
        <el-table-column prop="pagePath" label="页面路径" width="90px">   </el-table-column>

        <el-table-column label="操作" width="300px">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)"> 编辑 <i class="el-icon-edit-outline"></i></el-button>
            <el-button size="mini" type="info" @click="addChild(scope.row.id)"
                       v-if="!(scope.row.pid)"> 新增子菜单 <i class="el-icon-edit"></i></el-button><!-- pid不存在就不显示新增子菜单 -->
            <el-popconfirm class="ml-10" confirm-button-text='是的' cancel-button-text='不用了' icon="el-icon-info"
                           icon-color="red" title="您确定删除这个菜单吗？"  @confirm="handleDelete(scope.row.id)" >
              <el-button size="mini" type="danger" slot="reference"> 删除 <i class="el-icon-delete-solid"></i></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table><br/>

      <!-- Form -->
      <el-dialog title="请填写增加的菜单信息" :visible.sync="dialogFormVisible" position="fixed">
        <el-form>
          <el-form-item label="菜单名" :label-width="formLabelWidth">
            <el-input v-model="form.menuname" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="菜单路径" :label-width="formLabelWidth">
            <el-input v-model="form.path" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="图标" :label-width="formLabelWidth">
            <template slot-scope="scope">
              <el-select v-model="form.icon" clearable placeholder="请选择图标" style="width: 100%">
                <el-option v-for="item in options" :label="item.dictname" :key="item.dictname" :value="item.value">
                  <i :class="item.value"> {{ item.dictname }} </i>
                </el-option>
              </el-select>
            </template>
          </el-form-item>
          <el-form-item label="页面路径" :label-width="formLabelWidth">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
          <el-form-item label="请输入菜单描述" :label-width="formLabelWidth">
            <el-input v-model="form.description" autocomplete="off" type="textarea" style="height:200px"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </el-container>
</template>

<script>
export default {
  name: "Menu",
  data() {
    return {
      tableData: [],
      menuname:"",
      options: [],
      dictname:"",
      pagePath:"",
      form:{},
      multipleSelection: [], // 选择的数据条 数据行
      dialogFormVisible: false,  //弹窗初始不可见
      formLabelWidth: '120px',

      LogoTextShow: true,
      headerBg: "headerBg",
    }
  },

  created() {
    // 请求分页查询数据 第一个参数 url
    this.load();
  },

  methods: {
    load() {
      this.request.get("/menu/list", {
        params: {
          menuname: this.menuname,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total
      });

      this.request.get("/menu/icons", { // 请求图标数据
      }).then(res => {
        console.log(res.data)
        this.options = res.data
        this.total = res.total
      });

    },

    reset() {
      this.rolename = "";
      this.load();
    },
    addChild(pid){
      this.dialogFormVisible = true;
      this.form ={};
      if(pid){
        this.form.pid = pid
      }
    },
    AddUser(){
      this.dialogFormVisible = true;
      this.form ={};


    },
    save(){
      this.request.post("/menu/update",this.form).then(res => {
        if(res){ // 传入数据
          this.$message.success("保存成功 !")
          this.dialogFormVisible = false
          this.load()
        }else { this.$message.error("保存失败 !")}
      })
    },
    handleSelectionChange(val){ // 只是加复选框
      console.log(val) // val就是选择的数据的数组
      this.multipleSelection =val  // 把数组给 multipleSelection
    },
    handleEdit(row) {
      this.form = row; // 把当行的数据赋予到弹窗中
      this.dialogFormVisible = true; //打开弹窗
      console.log(row);
    },
    handleDelete(id) {
      this.request.delete("/menu/"+ id).then(res => {
        if(res){ // 传入数据
          this.$message.success("删除成功 !")
          this.dialogFormVisible = false
          this.load()
        }else { this.$message.error("删除失败 !")}
      })
      console.log(id);
    },
    handleDeleteBatch() { //对multipleSelection对象进行处理
      let ids = this.multipleSelection.map(v => v.id)  // 扁平化处理 将一个对象变成一个纯id 只有id 的 数组     [{1,},{2,},{3,}] -> [1,2,3]
      this.request.post("/menu/del/batch", ids).then(res => {
        if(res){ // 传入数据
          this.$message.success("删除成功 !")
          this.dialogFormVisible = false
          this.load()
        }else { this.$message.error("删除失败 !")}
      })
    },
    handleSizeChange(pageSize){
      console.log(`每页 ${pageSize} 条`);
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum){
      console.log(`当前页码: ${pageNum}`);
      this.pageNum = pageNum;
      this.load();
    },
  }
}
</script>
<style scoped>
.headerBg{
  background:azure!important;
}
</style>