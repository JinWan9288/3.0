<template>
<!-- 前台主体 -->
  <div>
  <!--头部导航栏-->
    <div style="display: flex; height: 60px; border-bottom: #8c939d solid 2px; line-height: 60px ">
      <!--logo welcome -->
      <div>
      <img src="../../assets/logo.png" style="width: 50px;height: 50px">
      </div>
      <div style="background-color:#555D67; color: azure; font-size: larger"> &nbsp;
        <b style=""> 欢迎来到管理系统  </b>
      </div>
    <div style="flex: 1">
    <!--导航菜单-->
      <el-menu mode="horizontal" @select="handleSelect "text-color="#fff" active-text-color="#ffd04b"
               background-color="#545c64">
        <el-menu-item index="1">首页</el-menu-item>
        <el-submenu index="2">
          <template slot="title">我的工作台</template>
          <el-menu-item index="2-1">选项1</el-menu-item>
          <el-menu-item index="2-2">选项2</el-menu-item>
          <el-submenu index="2-4">
            <template slot="title">选项4</template>
            <el-menu-item index="2-4-1">选项1</el-menu-item>
            <el-menu-item index="2-4-3">选项3</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item index="3">消息中心</el-menu-item>
        <el-menu-item index="4">订单管理</el-menu-item>
      </el-menu>
    </div>
    <div style="width: 100px"><!-- 头像 -->
      头像
    </div>

  </div><!-- 主体 -->
    <div style="width: 1400px; height:600px; margin: 0 20px ">
    <router-view></router-view>
  </div>

</div>
</template>
<script>
export default {
  name: "Front",
  data(){
    return{

    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>
<style scoped>
</style>