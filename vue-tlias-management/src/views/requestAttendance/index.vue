<script setup>
import { ref, watch, onMounted } from "vue";
import {queryNameByIdApi } from "@/api/attendance";
import { ElMessage,ElMessageBox } from "element-plus";
//import axios from 'axios';

const token = ref('');
const username = ref('');

const init = async () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  console.log(loginUser.username);
  if (loginUser.username) {
    username.value = loginUser.username;
    await search();
  } else {
    ElMessage.error("未找到有效的用户名");
  }
};


onMounted(() => {
  init();
  getToken();
  search();
});
const employee = ref({
 createTime: ""
});


//获取token
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if(loginUser && loginUser.token){
    token.value = loginUser.token;
  }
};

const staffsList = ref([]);

//查询
const search = async () => {
    const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  const result = await  queryNameByIdApi(loginUser.username);
  console.log(result);
  if (result.code) {
    staffsList.value = result.data;
  }
};




//表单引用
const staffsFormRef = ref();




</script>

<template>
  <h1>考勤查看</h1>
  <!-- 空行 -->
  <br />

  <div class="container">
    <el-table :data="staffsList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
     
     
      <el-table-column
        prop="createTime"
        label="缺勤时间"
        width="180"
        align="center"
      />
    </el-table>
  </div>



</template>




<style scoped>
.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>