// 接口请求封装：Vue基于axios封装request接口请求——request.js文件
// Axios是一个基于promise的Ajax网络请求库，作用于node.js和浏览器中，在服务端它使用原生node.js http模块, 而在客户端 (浏览器端) 则使用XMLHttpRequests（XHR）
// 1. 创建axios实例request// 注意：这里是全局统一加上了'/api'前缀，即所有接口都会加上'/api'前缀，页面里面写接口的时候就不要加'/api'了，否则会出现2个'/api'，类似'/api/api/user'这样的报错，切记！！
// 请求超时时间
// 2. request添加 请求拦截器 request ：可以自请求发送前对请求做一些处理，比如统一加token，对请求参数统一加密
// 3. response添加 响应拦截器 response ：可以在接口响应后统一处理结果
// 使用axios动态获取response里的data数据,axios封装的data。存储token到localStorage 这一层是统一固定的
// 如果是返回的文件（blob）
// 兼容服务端返回的字符串数据 判断是不是字符串
// 是字符串就把json字符串变成json对象
// 最后导出
// 3件事 created request对请求做处理 response
import axios from 'axios'
import ElementUI from 'element-ui'

const request = axios.create({
    baseURL: 'http://localhost:9090',  // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    let user = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user"),):null ;
    if(user){  // user存在就解析 不存在就给user赋值为空对象 只有存在才设置header
        config.headers['token'] = user.token;  // 设置token请求头
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果返回的是文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        // token验证失败
        if(res.code === '401'){
            ElementUI.Message({
                message:res.msg,
                type: 'error'
            })
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)

export default request