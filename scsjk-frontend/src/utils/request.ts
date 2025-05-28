import axios from 'axios'

const service = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(  
  config => {
    console.log('请求配置:', config) // 添加调试日志
    return config
  },
  error => {
    console.error('请求错误:', error) // 添加调试日志
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    console.log('响应数据:', response) // 添加调试日志
    return response.data
  },
  error => {
    console.error('响应错误:', error) // 添加调试日志
    return Promise.reject(error)
  }
)

export default service