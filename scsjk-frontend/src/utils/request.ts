import axios from 'axios'//引入第三方 HTTP 请求库 axios，用于发送网络请求。

//创建一个自定义配置的 axios 实例，命名为 service。
const service = axios.create({
    baseURL: 'http://localhost:8080',//设置请求的基础 URL，所有请求都会以这个地址为前缀。
  timeout: 5000//设置请求超时时间为 5000 毫秒（即 5 秒）。
})

//  interceptors.request.use() 添加请求拦截器。
service.interceptors.request.use(  
  config => {
    //在请求发出前被调用，接收请求配置对象 config，打印请求配置信息到控制台
    console.log('请求配置:', config) // 添加调试日志
    return config
  },
  error => {
    //处理请求配置阶段发生的错误：打印错误信息到控制台。
    console.error('请求错误:', error) // 添加调试日志
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  //在接收到响应后被调用，接收响应对象 response，打印响应内容到控制台
  response => {
    console.log('响应数据:', response) // 添加调试日志
    return response.data
  },
  error => {
    console.error('响应错误:', error) // 添加调试日志
    return Promise.reject(error)
  }
)

export default service//将配置好的 axios 实例导出，供其他模块导入和使用。