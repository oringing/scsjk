import axios from 'axios'

const service = axios.create({
  baseURL: 'http://localhost:8080', // 后端服务地址，需根据实际情况修改
  timeout: 5000
})

service.interceptors.request.use(
  config => {
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code!== 200) {
      console.error('请求失败', res.message)
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error)
    return Promise.reject(error)
  }
)

export default service