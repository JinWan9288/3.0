<template>
  <div>
    <el-card style=" width:900px;">
      <el-form style=" width: 800px ; padding-top: 30px; ">
        <el-form-item label="上传用户头像" :label-width="formLabelWidth">
          <el-upload
            class="avatar-uploader"
            action= "http://localhost:9090/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess" >
          <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        </el-form-item>
        <el-form-item label="用户姓名" :label-width="formLabelWidth">
          <el-input disabled v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称" :label-width="formLabelWidth">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="请设置用户密码" :label-width="formLabelWidth">
          <el-input v-model="form.passwords" autocomplete="off"></el-input>  <!--  密码要加密-->
        </el-form-item>
        <el-form-item label="年龄" :label-width="formLabelWidth">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
          <el-select v-model="form.sex" placeholder="请选择用户性别">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话号码" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户身份" :label-width="formLabelWidth">
          <el-input v-model="form.roleId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户家庭住址" :label-width="formLabelWidth">
        <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
      </el-form>
      <el-button @click="$router.push('/')">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </el-card>
  </div>
</template>
<script>
export default {
  name: "Person",
  data(){
    return{
      form:{},
      formLabelWidth: '120px',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user"),) : {},  //从拿
    }
  },
  created() { // 在查看用户信息的界面返回用户信息
    this.getUser().then(res =>{
      console.log(res)
      this.form = res
    })
  },
  methods: {
    async getUser(){
      return (await this.request.get("/user/username/" + this.user.username)).data
    },

    save(){
      this.request.post("/user", this.form).then(res => {
        if(!(res.code === '200')){ // 有传入数据
          this.$message.success("保存成功 !")
          this.$emit("refreshUser")  // 触发父级更新方法

          // 更新浏览器存储的用户信息
          this.getUser().then(res =>{
            res.token = JSON.parse(localStorage.getItem("user")).token  //解析获取token
            localStorage.setItem("user",JSON.stringify(res)) // 将对象转换为字符串
          })
          this.$router.push('/user')
         }else { this.$message.error("保存失败 !")}
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res;
    },
  }
}
</script>
<style>
.avatar-uploader .el-upload {
  border: 2px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader :hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: inline;
  background-color: black;
}
</style>