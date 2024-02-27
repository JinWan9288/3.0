<template>
  <el-container>
    <!--搜索栏-->
    <div>
      <el-input style="width: 250px" placeholder="请输入文件名" suffix-icon="el-icon-search" v-model="filename" ></el-input>

      <el-button class="ml-20" type="success" round  @click="load">  搜索   <i class="el-icon-loading"></i></el-button>
      <el-button class="ml-30" type="warning" round @click="reset">  重置   <i class="el-icon-refresh"></i></el-button>

      <el-popconfirm class="ml-10" confirm-button-text='是的' cancel-button-text='不用了'  icon="el-icon-info"
                     icon-color="red" title="您确定批量删除这些文件吗？"  @confirm="handleDeleteBatch">
        <el-button class="ml-10" type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <el-upload action="http://localhost:9090/file/upload" style="display: inline-block" :show-file-list="false"
                 :on-success="handleFileUploadSuccess">
        <el-button class="ml-20" type="primary">导入   <i class="el-icon-download"></i></el-button></el-upload><br>

      <!--表格-->
      <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" class="mt-10"
                @selection-change="handleSelectionChange"> <!-- 点击复选框后发生的事件 -->
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="id" label="id" width="40"></el-table-column>
        <el-table-column prop="filename" label="文件名" width="320"></el-table-column>
        <el-table-column prop="size" label="文件大小 (kb)" width="120"></el-table-column>
        <el-table-column prop="type" label="文件类型" width="100"></el-table-column>
        <el-table-column prop="url" label="文件下载链接" width="120">
          <template slot-scope="scope">
             <el-button type="primary" @click="download(scope.row.url)"> 点击下载 </el-button>
          </template>
        </el-table-column>

        <el-table-column prop="enable" label="是否启用" width="80">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc"
                       @change="changeEnable(scope.row)"></el-switch>  <!-- @change就是在启用的布尔值改变的时候触发方法 -->
          </template>
        </el-table-column>

        <el-table-column label="操作" width="130"> <!--  操作列  -->
          <template slot-scope="scope">
            <el-popconfirm class="ml-10" confirm-button-text='是的' cancel-button-text='不用了' icon="el-icon-info"
                           icon-color="red" title="您确定删除这个文件吗？" @confirm="handleDelete(scope.row.id)">
              <el-button size="mini" type="danger" slot="reference"> 删除  <i class="el-icon-delete-solid"></i></el-button>
           </el-popconfirm>
          </template>
        </el-table-column>
      </el-table><br/>

      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="pageNum" :page-sizes="[ 5 , 10, 20, 100]" :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total" >
      </el-pagination>
    </div>
  </el-container>
</template>
<script>
export default {
  name: 'File',
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      filename: '',
      multipleSelection: [], // 选择的数据条 数据行
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
      this.request.get("/file/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          filename: this.filename
        }
      }).then(res => {
        console.log(res.data)
        this.tableData = res.data.records
        this.total = res.data.total
      });
    },
    reset() {
      this.filename = "";
      this.load();
    },
    handleSelectionChange(val){ // 只是加复选框
      console.log(val) // val就是选择的数据的数组
      this.multipleSelection = val  // 把数组给 multipleSelection
    },
    download(url){
      window.open(url)
    },
    handleFileUploadSuccess(res){
      console.log(res)
      this.load();
    },
    handleDelete(id) {
      this.request.delete("/file/"+ id).then(res => {
        if(res.code === '200'){ // 传入数据
          this.$message.success("删除成功 !")
          this.load()
        }else {
          this.$message.error("删除失败 !")}
      })
      console.log(id);
    },
    handleDeleteBatch() { //对multipleSelection对象进行处理
      let ids = this.multipleSelection.map(v => v.id)  // 扁平化处理 将一个对象变成一个纯id 只有id 的 数组     [{1,},{2,},{3,}] -> [1,2,3]
      this.request.post("/file/del/batch", ids).then(res => {
        if(res.code === '200'){ // 传入数据
          this.$message.success("删除成功 !")
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
    changeEnable(row){
      this.request.post("/file/update", row).then(res => {
        if(res.code === '200'){
          this.$message.success("操作成功 !")
        }
      })
    },

  }
}
</script>
<style>
.headerBg{
  background:azure!important;
}
</style>