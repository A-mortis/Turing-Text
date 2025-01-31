import request from '@/utils/request'
 
 //新增
 export const addApi = (leave) => request.post('/leave',leave)

 //根据id查询
 export const queryByIdApi = (ids) => request.get(`/leave/${ids}`)

 export const queryPageApi = (page,pageSize) => 
    request.get(`/leave?page=${page}&pageSize=${pageSize}`)

 export const queryByIndex = (index) => request.get(`/leave/by-index/${index}`)

 //修改
 export const updateApi = (leave) => request.put(`/leave`,leave)

 //删除
 export const deleteApi = (indexes) => request.delete(`/leave?indexes=${indexes}`)

 //根据id找名字
 export const queryNameByIdApi = (id) => request.get(`/leave/get-name/${id}`)

 //通过请假申请
 export const passApi = (index) => request.put(`/leave/pass/${index}`)

 //驳回请假申请
 export const rejectApi = (index) => request.put(`/leave/reject/${index}`)
