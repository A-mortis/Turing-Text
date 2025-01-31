import request from '@/utils/request'
 //查询员工列表数据
 export const queryPageApi = (content,createTime,updateTime,page,pageSize) => 
    request.get(`/activity?content=${content}&createTime=${createTime}&updateTime=${updateTime}&page=${page}&pageSize=${pageSize}`)

 //新增
 export const addApi = (activity) => request.post('/activity',activity)

 //根据id查询
 export const queryByIdApi = (id) => request.get(`/activity/${id}`)

 //修改
 export const updateApi = (activity) => request.put(`/activity`,activity)

 //删除
 export const deleteApi = (ids) => request.delete(`/activity?ids=${ids}`)

 //找对应员工
 export const getStaffApi = (id) => request.get(`/activity/getStaffs/${id}`)

 //报名
 export const signUpApi = (staff_id,activity_id) => request.post(`/enrollActivity?staff_id=${staff_id}&activity_id=${activity_id}`)

 //取消报名
 export const cancelApi = (staff_id,activity_id) => request.delete(`/enrollActivity?staff_id=${staff_id}&activity_id=${activity_id}`)

 //确认报名状态
 export const confirmApi = (staff_id,activity_id) => request.get(`/enrollActivity?staff_id=${staff_id}&activity_id=${activity_id}`)