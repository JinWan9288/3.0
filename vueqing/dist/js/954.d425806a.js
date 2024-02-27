"use strict";(self["webpackChunkvueqing"]=self["webpackChunkvueqing"]||[]).push([[954],{6954:function(e,t,l){l.r(t),l.d(t,{default:function(){return c}});var o=function(){var e=this,t=e._self._c;return t("el-container",[t("div",[t("el-input",{staticStyle:{width:"250px"},attrs:{placeholder:"请输入角色姓名","suffix-icon":"el-icon-search"},model:{value:e.rolename,callback:function(t){e.rolename=t},expression:"rolename"}}),t("el-button",{staticClass:"ml-20",attrs:{type:"success",round:""},on:{click:e.load}},[e._v(" 搜索 "),t("i",{staticClass:"el-icon-loading"})]),t("el-button",{staticClass:"ml-30",attrs:{type:"warning",round:""},on:{click:e.reset}},[e._v(" 重置 "),t("i",{staticClass:"el-icon-refresh"})]),t("el-button",{staticClass:"ml-20",attrs:{type:"primary"},on:{click:e.AddUser}},[e._v(" 新增 "),t("i",{staticClass:"el-icon-circle-plus"})]),t("el-popconfirm",{staticClass:"ml-10",attrs:{"confirm-button-text":"是的","cancel-button-text":"不用了",icon:"el-icon-info","icon-color":"red",title:"您确定批量删除这些角色吗？"},on:{confirm:e.handleDeleteBatch}},[t("el-button",{staticClass:"ml-10",attrs:{slot:"reference",type:"danger"},slot:"reference"},[e._v("批量删除"),t("i",{staticClass:"el-icon-remove-outline"})])],1),t("el-table",{staticStyle:{"margin-top":"20px"},attrs:{data:e.tableData,border:"",stripe:"","header-cell-class-name":e.headerBg},on:{"selection-change":e.handleSelectionChange}},[t("el-table-column",{attrs:{type:"selection",width:"40px"}}),t("el-table-column",{attrs:{prop:"id",label:"id",width:"60px"}}),t("el-table-column",{attrs:{prop:"flag",label:"唯一标识",width:"100px"}}),t("el-table-column",{attrs:{prop:"rolename",label:"角色姓名",width:"150px"}}),t("el-table-column",{attrs:{prop:"description",label:"描述",width:"150px"}}),t("el-table-column",{attrs:{label:"操作",width:"300px"},scopedSlots:e._u([{key:"default",fn:function(l){return[t("el-button",{attrs:{size:"mini"},on:{click:function(t){return e.handleEdit(l.row)}}},[e._v(" 编辑 "),t("i",{staticClass:"el-icon-edit-outline"})]),t("el-button",{attrs:{size:"mini",type:"info"},on:{click:function(t){return e.handleContributeMenu(l.row.id)}}},[e._v(" 分配菜单 "),t("i",{staticClass:"el-icon-coordinate"})]),t("el-popconfirm",{staticClass:"ml-10",attrs:{"confirm-button-text":"是的","cancel-button-text":"不用了",icon:"el-icon-info","icon-color":"red",title:"您确定删除这个角色吗？"},on:{confirm:function(t){return e.handleDelete(l.row.id)}}},[t("el-button",{attrs:{slot:"reference",size:"mini",type:"danger"},slot:"reference"},[e._v(" 删除 "),t("i",{staticClass:"el-icon-delete-solid"})])],1)]}}])})],1),t("br"),t("el-pagination",{attrs:{background:"","current-page":e.pageNum,"page-sizes":[5,10,20,100],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}}),t("el-dialog",{attrs:{title:"请填写角色信息",visible:e.dialogFormVisible,position:"fixed"},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[t("el-form",[t("el-form-item",{attrs:{label:"角色名","label-width":e.formLabelWidth}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.rolename,callback:function(t){e.$set(e.form,"rolename",t)},expression:"form.rolename"}})],1),t("el-form-item",{attrs:{label:"请输入角色唯一标识","label-width":e.formLabelWidth}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.flag,callback:function(t){e.$set(e.form,"flag",t)},expression:"form.flag"}})],1),t("el-form-item",{attrs:{label:"请输入角色描述","label-width":e.formLabelWidth}},[t("el-input",{staticStyle:{height:"300px"},attrs:{autocomplete:"off",type:"textarea"},model:{value:e.form.description,callback:function(t){e.$set(e.form,"description",t)},expression:"form.description"}})],1)],1),t("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),t("el-button",{attrs:{type:"primary"},on:{click:e.save}},[e._v("确 定")])],1)],1),t("el-dialog",{attrs:{title:"请填写菜单分配",visible:e.menuDialogVisible,position:"fixed",width:"40%"},on:{"update:visible":function(t){e.menuDialogVisible=t}}},[t("el-tree",{ref:"tree",attrs:{data:e.menudata,props:e.props,"node-key":"id","show-checkbox":"","default-checked-keys":e.checks,"default-expand-all":"","highlight-current":""},scopedSlots:e._u([{key:"default",fn:function({node:l,data:o}){return t("span",{staticClass:"custom-tree-node"},[t("span",[t("i",{class:o.icon}),e._v(" "+e._s(o.menuname)+" ")])])}}])}),t("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),t("el-button",{attrs:{type:"primary"},on:{click:e.saveRoleMenu}},[e._v("确 定")])],1)],1)],1)])},a=[],i={name:"Role",data(){return{props:{label:"menuname"},menudata:[],tableData:[],total:0,pageNum:1,pageSize:5,rolename:"",roleId:0,checks:[],form:{},multipleSelection:[],dialogFormVisible:!1,menuDialogVisible:!1,formLabelWidth:"120px",headerBg:"headerBg"}},created(){this.load()},methods:{load(){this.request.get("/role/page",{params:{pageNum:this.pageNum,pageSize:this.pageSize,rolename:this.rolename}}).then((e=>{console.log(e.data),this.tableData=e.records,this.total=e.total}))},reset(){this.rolename="",this.load()},AddUser(){this.dialogFormVisible=!0,this.form={}},save(){this.request.post("/role/update",this.form).then((e=>{e?(this.$message.success("保存成功 !"),this.dialogFormVisible=!1,this.load()):this.$message.error("保存失败 !")}))},saveRoleMenu(){this.request.post("/role/roleMenu/"+this.roleId,this.$refs.tree.getCheckedKeys()).then((e=>{e?(console.log(this.$refs.tree.getCheckedKeys()),console.log(e),this.menuDialogVisible=!1,this.$message.success("分配成功 ! 重新登录后新菜单生效 !!! ")):this.$message.error("分配失败 !")}))},handleContributeMenu(e){this.menuDialogVisible=!0,this.roleId=e,console.log(e),this.request.get("/menu/list",{params:{menuname:this.menuname}}).then((e=>{console.log(e.data),this.menudata=e.data,this.total=e.total})),this.request.get("/role/menu/"+e).then((e=>{console.log(e.data),this.checks=e.data,this.total=e.total}))},handleSelectionChange(e){console.log(e),this.multipleSelection=e},handleEdit(e){this.form=e,this.dialogFormVisible=!0,console.log(e)},handleDelete(e){this.request.delete("/role/"+e).then((e=>{e?(this.$message.success("删除成功 !"),this.dialogFormVisible=!1,this.load()):this.$message.error("删除失败 !")})),console.log(e)},handleDeleteBatch(){let e=this.multipleSelection.map((e=>e.id));this.request.post("/role/del/batch",e).then((e=>{e?(this.$message.success("删除成功 !"),this.dialogFormVisible=!1,this.load()):this.$message.error("删除失败 !")}))},handleSizeChange(e){console.log(`每页 ${e} 条`),this.pageSize=e,this.load()},handleCurrentChange(e){console.log(`当前页码: ${e}`),this.pageNum=e,this.load()}}},s=i,n=l(1001),r=(0,n.Z)(s,o,a,!1,null,"717fa2f0",null),c=r.exports}}]);
//# sourceMappingURL=954.d425806a.js.map