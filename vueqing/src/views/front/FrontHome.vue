<template>
<div>
  <div> <!-- 主页 轮播图 -->
    <el-carousel indicator-position="outside" height="500px">
      <el-carousel-item v-for="item in img" :key="item">
        <img src="../../assets/daa61fa7d4704592b837a19b1aab0874.jpg" alt="" style="height: 500px; width: 1200px">
      </el-carousel-item>
    </el-carousel>
  </div>
  <div><!-- 列表 -->


  </div>
</div>
</template>
<script>
export default {
  name: "FrontHome",
  data(){
    return{
      img:[

      ],
    }
  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}
.el-carousel__item:nth-child(2n) {  /*偶数页*/
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) { /*奇数页*/
  background-color: #d3dce6;
}
</style>