<template>
<div class="wrapper">
  <div style="margin: 200px auto; background-color: #ffffff; width: 450px;height: 350px;padding: 20px;border-radius: 10px ">
    <div style="margin: 20px 0; text-align: center;font-size: 24px;"><b>注   册</b></div>
    <el-form :rules="rules" :model="user" ref="userForm">
      <el-form-item prop="username">
        <el-input size="medium" style="margin: 10px 0" placeholder="请输入用户名"
                  prefix-icon="el-icon-user" v-model="user.username" ></el-input>
      </el-form-item>
      <el-form-item prop="passwords">
      <el-input size="medium" style="margin: 10px 0" placeholder="请输入密码"
                prefix-icon="el-icon-lock"
                show-password v-model="user.passwords" ></el-input>
      </el-form-item>
      <el-form-item prop="ConfirmPasswords">
        <el-input size="medium" style="margin: 10px 0" placeholder="确认密码"
                  prefix-icon="el-icon-lock"
                  show-password v-model="user.ConfirmPasswords" ></el-input>
      </el-form-item>
      <div style="margin: 10px 0;text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="$router.push('/login')">返回登录界面</el-button>
        <el-button type="warning" size="small" autocomplete="off" @click="register">注册</el-button>
      </div>
    </el-form>
  </div>
</div>
</template>

<script>
export default {
  name: "Register",
  data(){
    return{
      user:{},
      rules: {
        username: [
          {required: true, message: '请输入用户姓名', trigger: 'blur'},  // required 表示必须要输入用户名, blur是当鼠标失焦 移开鼠标的时候触发这个message提示
        ],
        passwords:[
          {required: true, message: '请输入用户密码', trigger: 'blur'},
          {min: 1, max: 20, message: '密码长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        ConfirmPasswords:[
          {required: true, message: '请确认用户密码', trigger: 'blur'},
          {min: 1, max: 20, message: '密码长度在 1 到 20 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    register() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) { // 判断是否合法, 再判断输入的密码是否一致, 只有用户名密码都符合上述rules, 而且一致, 才向后端发送request
          if (this.user.passwords !=  this.user.ConfirmPasswords){
            this.$message("两次的密码不一致, 请重新输入")
            return false
          }
          this.request.post("/user/register", this.user).then(res => { // 默认是get接口 但是实际传入的是json对象 400 bad
            if (res.code == "200") {   // 在登录的时候不仅要push到主页, 而且要存下登录的用户信息, 要知道具体是谁登录
              this.$message.success("注册成功")
              this.$router.push("/login")
            } else {
              this.$message.error(res.msg);
            }
          })
        }
      });
    },
  }
}
</script>

<style scoped>
.wrapper{
  height: 100vh;
  background-image: linear-gradient(to bottom right,#FC466B,#3F5EFB) ;
  overflow: hidden;
}
</style>