<template>
  <div class="about">
    <h1>This is an about page page aboutpage</h1>

    <h1> {{ msg }} </h1>
    <el-button> {{ msg }}</el-button>
    <el-button type= "danger"> {{ msg2 }}</el-button>
    <el-button> 这是一个测试按钮</el-button>

    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  components: {
    HelloWorld
  },
  data() {
    return {
      msg : " Message hello",
      msg2: "Second Msg",
    }
  }
}
</script>