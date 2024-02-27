<template>
<div class="wrapper">
  <div style="margin: 200px auto; background-color: #ffffff; width: 400px; height: 300px;padding: 20px;border-radius: 10px">
    <div style="margin: 20px 0; text-align: center;font-size: 24px;"><b>登   录</b></div>
    <el-form :rules="rules" :model="user" ref="userForm">
      <el-form-item prop="username">
        <el-input size="medium" style="margin: 10px 0" placeholder="请输入用户名" prefix-icon="el-icon-user"
                  v-model="user.username" ></el-input>
      </el-form-item>
      <el-form-item prop="passwords">
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password
                 v-model="user.passwords" ></el-input>
      </el-form-item>
      <div style="margin: 10px 0;text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="login"> 登 录</el-button>
        <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')"> 注 册</el-button>
      </div>
    </el-form>
  </div>
</div>
</template>

<script>
import {setRoutes} from "@/router";

export default {
  name: "Login",
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
        ]
      }
    }
  },
  methods:{
    login() {
      this.$refs["userForm"].validate((valid) => { // 一个回调参数, 只有校验后数据合法才会返回true
        if (valid) { // 先一步判断是否合法, 只有用户名密码都合法,符合上述rules, 才向后端发送request, 后端继续判断用户名密码是否与数据库中相等
          this.request.post("/user/login", this.user).then(res => { // 默认是get接口 但是实际传入的是json对象 400 bad
            if (res.code == "200") {   // 在登录的时候不仅要push到主页, 而且要存下登录的用户信息, 要知道具体是谁登录
              this.$message.success("欢迎登录")

              setRoutes() // 登录之后动态分配路由

              this.$router.push("/") //正确就跳转到主页
              localStorage.setItem("user",JSON.stringify(res.data)); // 存储用户信息到浏览器 将用户对象转换为json字符串  key value
              localStorage.setItem("menus",JSON.stringify(res.data.menus));
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