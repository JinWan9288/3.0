"use strict";(self["webpackChunkvueqing"]=self["webpackChunkvueqing"]||[]).push([[65],{8065:function(e,s,r){r.r(s),r.d(s,{default:function(){return l}});r(560);var t=function(){var e=this,s=e._self._c;return s("div",{staticClass:"wrapper"},[s("div",{staticStyle:{margin:"200px auto","background-color":"#ffffff",width:"450px",height:"350px",padding:"20px","border-radius":"10px"}},[e._m(0),s("el-form",{ref:"userForm",attrs:{rules:e.rules,model:e.user}},[s("el-form-item",{attrs:{prop:"username"}},[s("el-input",{staticStyle:{margin:"10px 0"},attrs:{size:"medium",placeholder:"请输入用户名","prefix-icon":"el-icon-user"},model:{value:e.user.username,callback:function(s){e.$set(e.user,"username",s)},expression:"user.username"}})],1),s("el-form-item",{attrs:{prop:"passwords"}},[s("el-input",{staticStyle:{margin:"10px 0"},attrs:{size:"medium",placeholder:"请输入密码","prefix-icon":"el-icon-lock","show-password":""},model:{value:e.user.passwords,callback:function(s){e.$set(e.user,"passwords",s)},expression:"user.passwords"}})],1),s("el-form-item",{attrs:{prop:"ConfirmPasswords"}},[s("el-input",{staticStyle:{margin:"10px 0"},attrs:{size:"medium",placeholder:"确认密码","prefix-icon":"el-icon-lock","show-password":""},model:{value:e.user.ConfirmPasswords,callback:function(s){e.$set(e.user,"ConfirmPasswords",s)},expression:"user.ConfirmPasswords"}})],1),s("div",{staticStyle:{margin:"10px 0","text-align":"right"}},[s("el-button",{attrs:{type:"primary",size:"small",autocomplete:"off"},on:{click:function(s){return e.$router.push("/login")}}},[e._v("返回登录界面")]),s("el-button",{attrs:{type:"warning",size:"small",autocomplete:"off"},on:{click:e.register}},[e._v("注册")])],1)],1)],1)])},i=[function(){var e=this,s=e._self._c;return s("div",{staticStyle:{margin:"20px 0","text-align":"center","font-size":"24px"}},[s("b",[e._v("注 册")])])}],a={name:"Register",data(){return{user:{},rules:{username:[{required:!0,message:"请输入用户姓名",trigger:"blur"}],passwords:[{required:!0,message:"请输入用户密码",trigger:"blur"},{min:1,max:20,message:"密码长度在 1 到 20 个字符",trigger:"blur"}],ConfirmPasswords:[{required:!0,message:"请确认用户密码",trigger:"blur"},{min:1,max:20,message:"密码长度在 1 到 20 个字符",trigger:"blur"}]}}},methods:{register(){this.$refs["userForm"].validate((e=>{if(e){if(this.user.passwords!=this.user.ConfirmPasswords)return this.$message("两次的密码不一致, 请重新输入"),!1;this.request.post("/user/register",this.user).then((e=>{"200"==e.code?(this.$message.success("注册成功"),this.$router.push("/login")):this.$message.error(e.msg)}))}}))}}},o=a,u=r(1001),n=(0,u.Z)(o,t,i,!1,null,"9c23790c",null),l=n.exports}}]);
//# sourceMappingURL=65.c1e0b70c.js.map