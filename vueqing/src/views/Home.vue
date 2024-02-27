<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 50px">
      <el-col :span="6">
        <el-card><!-- style="color: #409EFF"-->
        <div >用户总数</div>
          <div style="padding: 10px 0; text-align: center ; font-weight: bold" >100</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div >用户总数</div>
          <div style="padding: 10px 0; text-align: center ; font-weight: bold" ></div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div >用户总数</div>
          <div style="padding: 10px 0; text-align: center ; font-weight: bold" ></div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div >用户总数</div>
          <div style="padding: 10px 0; text-align: center ; font-weight: bold" ></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="5">
        <div style="width: 400px;height: 500px" id="main"></div>
      </el-col>
      <el-col :span="8">
        <div style="width: 950px;height: 500px" id="MainPie"></div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <div style="width: 400px;height: 500px" id="line"></div>
      </el-col>
      <el-col :span="8">
        <div style="width: 900px;height: 500px" id="pie"></div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import * as echarts from 'echarts';
export default {
  name: "Home",
  data() {
    return{
    }
  },
  mounted() { // 页面元素渲染好之后再去触发 div创建好后触发
    // main
    var MainChartDom = document.getElementById('main');
    var MainChart = echarts.init(MainChartDom);
    var MainOption = {
      title: {
        text: 'Member Chart',
        subtext: '趋势图',
        left: 'center'
      },
      xAxis: { // x轴
        type: 'category',
        data: ["第一季度","第二季度","第三季度","第四季度"]
      },
      yAxis: {
        type: 'value'
      },
      series: [ // y轴
        {
          data: [],
          type: 'line',
          smooth: true
        },
        {
          data: [],
          type: 'bar',
          smooth: true
        }
      ]
    };
    // MainPie
    var PieChart = document.getElementById('MainPie');
    var PieChart = echarts.init(PieChart);
    var PieOption = {
      title: {
        text: 'Nightingale Chart 各季度用户人数统计',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: ' {b} : {c} ( {d}%) ' // 放置显示比例 a是名字 b是具体项 c是数量 d是比例
      },
      legend: {
        top: 'bottom',
       },
      toolbox: {
        show: true,
        feature: {
          mark: { show: true },
          dataView: { show: true, readOnly: false },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },
      series: [
        {
          name: 'Mode',
          type: 'pie',
          radius: [20, 140],
          roseType: 'radius',
          center: ['35%', '50%'],
          itemStyle: {
            borderRadius: 1
          },
          data: []
        },
        {
          name: 'Area',
          type: 'pie',
          radius: [20, 140],
          center: ['75%', '50%'],
          roseType: 'area',
          itemStyle: {
            borderRadius: 2
          },
          data: []
        }
      ]
    };

    this.request.get("/echarts/member").then( res => {

      MainOption.series[0].data = res.data // 折线图
      MainOption.series[1].data = res.data // 柱状图
      MainChart.setOption(MainOption);

      PieOption.series[0].data = [ // 饼图1的数据
        {name: "第一季度" ,value: res.data[0]},
        {name: "第二季度" ,value: res.data[1]},
        {name: "第三季度" ,value: res.data[2]},
        {name: "第四季度" ,value: res.data[3]}
      ]
      PieOption.series[1].data = [ // 饼图2的数据
        {name: "第一季度" ,value: res.data[0]},
        {name: "第二季度" ,value: res.data[1]},
        {name: "第三季度" ,value: res.data[2]},
        {name: "第四季度" ,value: res.data[3]}
      ]
      PieChart.setOption(PieOption);

    })


    // line
    var LineChartDom = document.getElementById('line');
    var myChartLine = echarts.init(LineChartDom);
    var LineOption = {
      xAxis: { // x轴
        type: 'category',
        data: []
      },
      yAxis: {
        type: 'value'
      },
      series: [ // y轴
        {
          data: [],
          type: 'line',
          smooth: true
        },
        {
          data: [],
          type: 'bar',
          smooth: true
        }
      ]
    };
    this.request.get("/echarts/example").then( res => {
      LineOption.xAxis.data = res.data.x
      LineOption.series[0].data = res.data.y // 折线图的y
      LineOption.series[1].data = res.data.y // 柱状图的y
      myChartLine.setOption(LineOption); // 数据都准备好后再set
    })

    // pie
    var PieChartDom = document.getElementById('pie');
    var PieChartDom = echarts.init(PieChartDom);
    var PieOptionDom = {
          title: {
            text: 'Nightingale Chart',
            subtext: 'Fake Data',
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            left: 'center',
            top: 'bottom',
            data: [
              'rose1',
              'rose2',
              'rose3',
              'rose4',
              'rose5',
              'rose6',
              'rose7',
              'rose8'
            ]
          },
          toolbox: {
            show: true,
            feature: {
              mark: { show: true },
              dataView: { show: true, readOnly: false },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          series: [
            {
              name: 'Radius Mode',
              type: 'pie',
              radius: [20, 140],
              center: ['25%', '50%'],
              roseType: 'radius',
              itemStyle: {
                borderRadius: 5
              },
              label: {
                show: false
              },
              emphasis: {
                label: {
                  show: true
                }
              },
              data: [
                { value: 40, name: 'rose 1' },
                { value: 33, name: 'rose 2' },
                { value: 28, name: 'rose 3' },
                { value: 22, name: 'rose 4' },
                { value: 20, name: 'rose 5' },
                { value: 15, name: 'rose 6' },
                { value: 12, name: 'rose 7' },
                { value: 10, name: 'rose 8' }
              ]
            },
            {
              name: 'Area Mode',
              type: 'pie',
              radius: [20, 140],
              center: ['75%', '50%'],
              roseType: 'area',
              itemStyle: {
                borderRadius: 5
              },
              data: [
                { value: 30, name: 'rose 1' },
                { value: 28, name: 'rose 2' },
                { value: 26, name: 'rose 3' },
                { value: 24, name: 'rose 4' },
                { value: 22, name: 'rose 5' },
                { value: 20, name: 'rose 6' },
                { value: 18, name: 'rose 7' },
                { value: 16, name: 'rose 8' }
              ]
            }
          ]
        };
    PieChartDom.setOption(PieOptionDom);
  },
  methods:{
  }
}
</script>
<style scoped>
</style>