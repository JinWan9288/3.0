<template>
  <div style="margin-left: 300px;">
    <span>
          <el-breadcrumb separator="/" style="font-size: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
          </el-breadcrumb>
        </span>
    <span  >
       <el-dropdown style="cursor: pointer; font-size: 20px ; margin-top: -70px; margin-right: -980px">

         <div style=" ">
           <img :src="user.avatarUrl" alt="" style="width:80px; height: 80px; border-radius: 50%; position: relative; ">

           <i class="el-icon-setting"></i> <b>{{user.username}}</b>
         </div>

          <el-dropdown-menu slot="dropdown" style="width: 80px; text-align: center; text-decoration: none">
            <el-dropdown-item>
              <router-link to="/person" style="color: gray;text-decoration: none"> <b>个人信息</b></router-link>
            </el-dropdown-item>
            <el-dropdown-item><b></b></el-dropdown-item>
            <el-dropdown-item>
             <span style="text-decoration: none;color: gray" @click="logout"><b>退 出 </b></span>
              <!-- 退出链接到login界面 -->
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </span>
  </div>
</template>
<script>
export default {
  name: "Header",
  props:{
    user: Object // header的渲染 接收manage传来的数据
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;
    }
  },
  watch: {   // 监听路由变化
    currentPathName(newVal,oldVal){
      console.log(newVal)
  }
},
  data() {
    return{
     //   user: localStorage.getItem("user")?JSON.parse(localStorage.getItem("user"),):{}, object接收前端的数据
    //   // 判断user存不存在, 存在就解析json字符串将其转换为对象, 不存在就返回空对象
    }
  },
  methods:{
    logout(){ //退出之后首先返回到登录界面, 再清空登录的用户信息
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  }
}
</script>
<style scoped>
</style>