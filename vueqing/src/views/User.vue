<template>
  <el-container>
    <!--搜索栏-->
    <div>
      <el-input style="width: 250px" placeholder="请输入用户姓名 " suffix-icon="el-icon-search" v-model="username" ></el-input>
      <el-input style="width: 250px" class="ml-10" suffix-icon="el-icon-position" placeholder="请输入用户地址 "
                v-model="address"></el-input>
      <el-input style="width: 250px; margin-bottom: 20px"  suffix-icon="el-icon-message" placeholder="请输入邮箱 "
                 v-model="email" class="ml-10"></el-input>
      <el-button class="ml-20" type="success" round  @click="load"> 搜索  <i class="el-icon-loading"></i></el-button>
      <el-button class="ml-30" type="warning" round @click="reset"> 重置  <i class="el-icon-refresh"></i></el-button>
      <!-- 点击后就进行分页查询 load  @click 点击后就... --> <br/>
      <el-button class="ml-20" type="primary"  @click="AddUser">  新增  <i class="el-icon-circle-plus"></i></el-button>

      <el-popconfirm class="ml-10" confirm-button-text='是的' cancel-button-text='不用了'  icon="el-icon-info"
                     icon-color="red" title="您确定批量删除这些用户吗？"  @confirm="handleDeleteBatch">
      <el-button class="ml-10" type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <el-upload action="http://localhost:9090/user/import" style="display: inline-block" :show-file-list="false"
                 accept=".xlsx" :on-success="handleExcelImportSuccess">
        <el-button class="ml-20" type="primary"> 导入  <i class="el-icon-download"></i></el-button></el-upload>

      <el-button type="primary" @click="exportt" class="ml-20"> 导出 <i class="el-icon-upload2"></i></el-button> <br/><br/>

      <!--表格-->
      <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
                                                                                      <!-- 点击复选框后发生的事件 -->
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="id" label="id" width="35"> </el-table-column>
        <el-table-column prop="username" label="姓名" width="120"></el-table-column>
        <el-table-column prop="nickname" label="昵称" width="80"></el-table-column>
        <el-table-column prop="age" label="年龄" width="50"></el-table-column>
        <el-table-column prop="sex" label="性别" width="50"></el-table-column>
        <el-table-column prop="phone" label="电话号码" width="100"></el-table-column>
        <el-table-column prop="address" label="地址" width="80"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="100"></el-table-column>
        <el-table-column prop="role" label="身份" width="100"></el-table-column>
        <el-table-column prop="isvalid" label="是否有效" width="70"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="200"></el-table-column>

        <el-table-column label="操作" width="170"> <!--  操作列  -->
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)"> 编辑 <i class="el-icon-edit-outline"></i></el-button>
            <el-popconfirm class="ml-10" confirm-button-text='是的' cancel-button-text='不用了' icon="el-icon-info"
                           icon-color="red" title="您确定删除这个用户吗？"  @confirm="handleDelete(scope.row.id)" >
            <el-button size="mini" type="danger" slot="reference"> 删除<i class="el-icon-delete-solid"></i></el-button>
            </el-popconfirm>                 <!--加上 slot reference 才能看见这个按钮   专门用来触发 popconfirm 弹窗显示-->
          </template>
        </el-table-column>

      </el-table> <br/>


      <!-- Form -->
      <el-dialog title="请填写用户信息" :visible.sync="dialogFormVisible" position="fixed">
        <el-form>
          <el-form-item label="用户姓名" :label-width="formLabelWidth">
            <el-input v-model="form.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户昵称" :label-width="formLabelWidth">
            <el-input v-model="form.nickname" autocomplete="off"></el-input>
          </el-form-item>
          <!--    <el-form-item label="请设置用户密码" :label-width="formLabelWidth">-->
          <!--    <el-input v-model="form.passwords" autocomplete="off"></el-input>  密码要加密 -->
          <!--    </el-form-item>-->
          <el-form-item label="年龄" :label-width="formLabelWidth">
            <el-input v-model="form.age" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item label="性别" :label-width="formLabelWidth">
            <el-select v-model="form.sex" placeholder="请选择用户性别" style="width: 100%">
              <el-option label="男" value="1"></el-option>
              <el-option label="女" value="0"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="电话号码" :label-width="formLabelWidth">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户家庭住址" :label-width="formLabelWidth">
            <el-input v-model="form.address" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="电子邮箱" :label-width="formLabelWidth">
            <el-input v-model="form.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户身份" :label-width="formLabelWidth">
            <template slot-scope="scope">
              <el-select v-model="form.role" clearable placeholder="请选择角色" style="width: 100%">
                <el-option v-for="item in roles" :label="item.flag" :key="item.flag" :value="item.flag">
                </el-option>
              </el-select>
            </template>
          </el-form-item>
          <el-form-item label="上传用户头像" :label-width="formLabelWidth">
            <el-input v-model="form.avatarUrl" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
      <div class="block">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" background
                    :current-page="pageNum" :page-sizes="[ 5 , 10, 20 , 100]"  :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper" :total="total">

      </el-pagination>
      </div>

    </div>
  </el-container>
</template>
<script>
export default {
  name: 'User',
  data() {
    return {
      tableData: [],
      roles:[],
      pageNum: 1,
      total: 0,
      pageSize: 5,
      username: "",
      address: "",
      email: "",
      nickname:"",
      age:"",
      sex:"",
      phone:"",
      roleId:1,
      isvaild:"Y",
      avatarUrl:"",
      form:{},
      multipleSelection: [], // 选择的数据条 数据行
      dialogFormVisible: false,  //弹窗初始不可见
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
      this.request.get("/user/pageMbP", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          address: this.address,
          email: this.email
        }
      }).then(res => {
        console.log(res.data)
        this.tableData = res.records
        this.total = res.total
      });

      // 请求分页查询数据 第一个参数 url mybatis写法
      // fetch( "http://localhost:9090/user/page?pageNum= "
      //     + this.pageNum + "&pageSize=" + this.pageSize + "&username="+ this.username)
      //     .then(res => res.json() ).then(res => {
      //   console.log(res);
      //   this.tableData = res.data;  // 将前端的数据传入后端
      //   this.total =res.total;
      //   console.log(res.total);
      // });

      this.request.get("/role/list").then(res => { // 向后端接口请求所有角色
        console.log(res.data)
        this.roles = res.data
      });

    },

    reset() {
      this.username = "";
      this.address = "";
      this.email = "";
      this.load();
    },
    AddUser(){
      this.dialogFormVisible = true;
      this.form ={};
    },
    save(){
      this.request.post("/user",this.form).then(res => {
        if(res){ // 传入数据
          this.$message.success("保存成功 !")
          this.dialogFormVisible = false
          this.load()
        }else { this.$message.error("保存失败 !")}
      })
    },
    handleSelectionChange(val){ // 只是加复选框
      console.log(val) // val就是选择的数据的数组
      this.multipleSelection = val  // 把数组给 multipleSelection
    },
    handleEdit(row) {
      this.form = row; // 把当行的数据赋予到弹窗中
      this.dialogFormVisible = true; //打开弹窗
      console.log(row);
    },
    handleDelete(id) {
      this.request.delete("/user/"+ id).then(res => {
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
      this.request.post("/user/del/batch", ids).then(res => {
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
    exportt(){
      window.open("http://localhost:9090/user/export")
    },
    handleExcelImportSuccess(){ // on-success  导入成功的钩子函数
      this.$message.success("导入成功!!!");
      this.load(); // 导入成功后需要刷新一下表格, 加载新数据
    },
  }
}
</script>
<style>
.headerBg{
  background:azure!important;
}
</style>