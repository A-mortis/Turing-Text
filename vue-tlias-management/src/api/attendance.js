import request from '@/utils/request'
// 分页查询
export const queryPageApi = (username,createTime,updateTime,page,pageSize) => 
    request.get(`/attendance?username=${username}&createTime=${createTime}&updateTime=${updateTime}&page=${page}&pageSize=${pageSize}`)

 //新增
 export const addApi = (attendance) => request.post('/attendance',attendance)

 //根据名字查询
 export const queryNameByIdApi = (id) => request.get(`/attendance/get-name/${id}`)