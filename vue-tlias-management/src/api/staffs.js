import request from '@/utils/request'
 //查询员工列表数据
 export const queryPageApi = (name,gender,createTime,updateTime,page,pageSize) => 
    request.get(`/staffs?name=${name}&gender=${gender}&createTime=${createTime}&updateTime=${updateTime}&page=${page}&pageSize=${pageSize}`)

 //新增
 export const addApi = (staffs) => request.post('/staffs',staffs)

 //根据id查询
 export const queryByIdApi = (id) => request.get(`/staffs/${id}`)

 //修改
 export const updateApi = (staffs) => request.put(`/staffs`,staffs)

 //删除
 export const deleteApi = (ids) => request.delete(`/staffs?ids=${ids}`)


 