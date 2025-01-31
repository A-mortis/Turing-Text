import request from '@/utils/request'


 //新增
 export const addApi = (activity) => request.post('/activity',activity)

 //根据id查询
 export const queryByIdApi = (id) => request.get(`/activity/${id}`)

 //修改
 export const updateApi = (activity) => request.put(`/activity`,activity)

 //删除
 export const deleteApi = (ids) => request.delete(`/activity?ids=${ids}`)