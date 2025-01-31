import request from '@/utils/request'
 
 //新增
 export const addApi = (resignation) => request.post('/resignation',resignation)

 //根据id查询
 export const queryByIdApi = (ids) => request.get(`/resignation/${ids}`)

 export const queryPageApi = (page,pageSize) => 
    request.get(`/resignation?page=${page}&pageSize=${pageSize}`)

 export const queryByIndex = (index) => request.get(`/resignation/by-index/${index}`)

 //修改
 export const updateApi = (resignation) => request.put(`/resignation`,resignation)

 //删除
 export const deleteApi = (indexes) => request.delete(`/resignation?indexes=${indexes}`)

 //根据id找名字
 export const queryNameByIdApi = (id) => request.get(`/resignation/get-name/${id}`)

 //通过离职申请
 export const passApi = (index) => request.put(`/resignation/pass/${index}`)

 //驳回离职申请
 export const rejectApi = (index) => request.put(`/resignation/reject/${index}`)
