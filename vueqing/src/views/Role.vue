<template>
  <el-container>
    <!--搜索栏-->
    <div>
      <el-input style="width: 250px" placeholder="请输入角色姓名" suffix-icon="el-icon-search" v-model="rolename" ></el-input>
      <el-button class="ml-20" type="success" round  @click="load">  搜索   <i class="el-icon-loading"></i></el-button>
      <el-button class="ml-30" type="warning" round @click="reset">  重置   <i class="el-icon-refresh"></i></el-button>
      <el-button class="ml-20" type="primary"  @click="AddUser">  新增  <i class="el-icon-circle-plus"></i></el-button>

      <el-popconfirm class="ml-10" confirm-button-text='是的' cancel-button-text='不用了'  icon="el-icon-info"
                     icon-color="red" title="您确定批量删除这些角色吗？"  @confirm="handleDeleteBatch">
        <el-button class="ml-10" type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <!--表格-->
      <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
                @selection-change="handleSelectionChange" style="margin-top: 20px"> <!-- 点击复选框后发生的事件 -->
        <el-table-column type="selection" width="40px"></el-table-column>
        <el-table-column prop="id" label="id" width="60px">
        </el-table-column>
        <el-table-column prop="flag" label="唯一标识" width="100px">
        </el-table-column>
        <el-table-column prop="rolename" label="角色姓名" width="150px">
        </el-table-column>
        <el-table-column prop="description" label="描述" width="150px">
        </el-table-column>

        <el-table-column label="操作" width="300px">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">  编辑  <i class="el-icon-edit-outline"></i></el-button>
            <el-button size="mini" @click="handleContributeMenu(scope.row.id)" type="info"> 分配菜单 <i class="el-icon-coordinate"></i>
            </el-button>
            <el-popconfirm class="ml-10" confirm-button-text='是的' cancel-button-text='不用了' icon="el-icon-info"
                           icon-color="red" title="您确定删除这个角色吗？"  @confirm="handleDelete(scope.row.id)" >
                <el-button size="mini" type="danger" slot="reference"> 删除 <i class="el-icon-delete-solid"></i></el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table><br/>

      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="pageNum" :page-sizes="[ 5 , 10, 20 , 100]" :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>

      <!-- Form弹窗 -->
      <el-dialog title="请填写角色信息" :visible.sync="dialogFormVisible" position="fixed">
        <el-form>
          <el-form-item label="角色名" :label-width="formLabelWidth">
            <el-input v-model="form.rolename" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="请输入角色唯一标识" :label-width="formLabelWidth">
            <el-input v-model="form.flag" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="请输入角色描述" :label-width="formLabelWidth">
            <el-input v-model="form.description" autocomplete="off" type="textarea" style="height:300px"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="请填写菜单分配" :visible.sync="menuDialogVisible" position="fixed" width="40%">
        <el-tree :data="menudata" :props="props" ref="tree" node-key="id" show-checkbox
                 :default-checked-keys="checks" default-expand-all highlight-current>
          <span class="custom-tree-node" slot-scope = "{ node,data }">
            <span><i :class="data.icon"></i> {{data.menuname}} </span>
          </span>
        </el-tree>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </el-container>
</template>

<script>
export default {
  name: "Role",
  data() {
    return {
      props: {
          label: "menuname"
      },
      menudata:[],
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      rolename: "",
      roleId:0,
      checks:[],
      form:{},
      multipleSelection: [], // 选择的数据条 数据

      dialogFormVisible: false,  //弹窗初始不可见
      menuDialogVisible: false,
      formLabelWidth: '120px',
      headerBg: "headerBg",
    }
  },

  created() {
    // 请求分页查询数据 第一个参数 url
    this.load();
  },
  methods: {
    load() {
      // mybatis plus 使用request实现分页查询
      this.request.get("/role/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          rolename: this.rolename,
        }
      }).then(res => {
        console.log(res.data)
        this.tableData = res.records
        this.total = res.total
      });
    },

    reset() {
      this.rolename = "";
      this.load();
    },
    AddUser(){
      this.dialogFormVisible = true;
      this.form ={};
    },
    save(){
      this.request.post("/role/update",this.form).then(res => {
        if(res){ // 传入数据
          this.$message.success("保存成功 !")
          this.dialogFormVisible = false
          this.load()
        }else { this.$message.error("保存失败 !")}
      })
    },
    saveRoleMenu(){ // 绑定角色与菜单
      this.request.post( "/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys() ).then(res => {  // 给后端传roleId menuId
        if(res) {
          console.log(this.$refs.tree.getCheckedKeys())
          console.log(res)
          this.menuDialogVisible = false
          this.$message.success("分配成功 ! 重新登录后新菜单生效 !!! ")
        }else { this.$message.error("分配失败 !")}
      })
    },
    handleContributeMenu(roleId){
      this.menuDialogVisible = true; //打开弹窗
      this.roleId = roleId;

      console.log(roleId);

      this.request.get("/menu/list",{  // 获取所有菜单名
        params: {
          menuname: this.menuname
        }
      }).then(res =>{
        console.log(res.data)
        this.menudata = res.data
        this.total = res.total
      })

      this.request.get("/role/menu/" + roleId).then(res =>{ // 返回已选择菜单项（数组）
        console.log(res.data)
        this.checks = res.data
        this.total = res.total

      })
    },
    handleSelectionChange(val){ // 加复选框
      console.log(val) // val就是选择的数据的数组
      this.multipleSelection =val  // 把数组给 multipleSelection
    },
    handleEdit(row) {
      this.form = row; // 把当行的数据赋予到弹窗中
      this.dialogFormVisible = true; //打开弹窗
      console.log(row);
    },

    handleDelete(id) {
      this.request.delete("/role/"+ id).then(res => {
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
      this.request.post("/role/del/batch", ids).then(res => {
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