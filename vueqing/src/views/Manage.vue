<template>
  <div>
    <el-container>
      <el-aside  style="height: 800px;position: fixed; background-color: #2c3e50">
        <Aside/><!--  引入Aside -->
      </el-aside>

      <el-header style=" border-bottom: gray 1px solid; margin-top: 50px; display: inline">
        <Header :user="user" /><!--  引入Header 接收person的数据 最后是要传给header-->
      </el-header>

     <el-main>
       <router-view style="margin-left: 300px" @refreshUser="getUser" /> <!-- 引入主体 User File等 -->
       <!-- @refreshUser 是更新头像数据 不用退出登录头像才显示 头像是通过token获取的 刷新界面头像不会变化-->
   <!-- person 收到前端的数据（照片文件），交给父级（通过refreshUser header的父级是manage 的 router view）通过父级获取后台数据并返回给前端header 就不需要退出登录 -->
       <!-- person manage header -->
     </el-main>

    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";
export default {
  name: 'Manage',
  components: {Header, Aside},
  data() {
    return {
      headerBg: "headerBg",
      user: {}
    }
  },
  created() {
    this.getUser() // 从后台获取最新的数据
  },
  methods:{
    getUser(){
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""

      this.request.get("/user/username/" + username).then( res =>{ // 从后台获取最新的用户数据
      this.user = res.data
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