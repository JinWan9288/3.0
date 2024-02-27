<template>
  <el-menu background-color="rgb(48,65,86)" class="m-20">
    <!-- 系统头部-->
    <div style="height: 50px; line-height: 60px; font-size:25px">
      <img src="../assets/logo.png" style="width: 30px">
      <b style="color: azure"> 后台管理系统 </b>
    </div>
    <!--折叠效果-->
<!--    <el-radio-group v-model="isCollapse" style="margin-bottom: 20px">-->
<!--      <el-radio-button :label="false">展开</el-radio-button>-->
<!--      <el-radio-button :label="true">收起</el-radio-button>-->
<!--    </el-radio-group>:collapse="isCollapse" -->

    <!--导航栏-->
    <el-menu router :default-openeds="opens"  background-color="rgb(48,65,86)" class="m-20"
             text-color="#fff" active-text-color="#ffd04b" :collapse-transition="false" >
      <div v-for="item in menus" :key="item.id" >
        <div v-if="item.path">
          <el-menu-item :index="item.path">
            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title"><b>{{ item.menuname }} </b></span>
            </template>
          </el-menu-item>
        </div>
        <div v-else>
          <el-submenu :index="item.id + '' ">
            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title"><b> {{ item.menuname }} </b></span>
            </template>
            <div v-for="subItem in item.children" :key="subItem.id">
              <el-menu-item :index="subItem.path" style="background-color: #2c3e50">
                <template slot="title">
                  <i :class="subItem.icon"></i>
                  <span slot="title"> <b>{{ subItem.menuname }}</b></span>
                </template>
              </el-menu-item>
            </div>
          </el-submenu>
        </div>
      </div>

    </el-menu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    LogoTextShow:Boolean
  },
  data() {
    return {
      isCollapse: true,
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : []
    };
  },
  methods: {
  }
}
</script>
<style scoped>
</style>