import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

//创建axios实例对象
const request = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 600000
})

//axios的请求 request 拦截器
request.interceptors.request.use(
  (config) => { //成功回调
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
    console.log(loginUser);
    if(loginUser && loginUser.token){ 
      config.headers.token = loginUser.token
    }
    return config;
  },
  (error) => { //失败回调
   return Promise.reject(error)
  }
)

//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { //成功回调
    return response.data
  },
  (error) => { //失败回调
    if(error.response.status === 401){
      ElMessage.error("登录超时，请重新登录")
      router.push("/login")
    }
    return Promise.reject(error)
  }
)

export default request