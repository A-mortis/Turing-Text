<script setup>
import { ref, watch, onMounted } from "vue";
import {queryPageApi,addApi, queryByIdApi, deleteApi,queryByIndex,queryNameByIdApi,passApi,rejectApi } from "@/api/leave";
import { ElMessage,ElMessageBox } from "element-plus";
//import axios from 'axios';

const token = ref('');
const getStatusText = (status) => {
  switch (status) {
    case 1:
      return "未审核";
    case 2:
      return "通过";
    case 3:
      return "不通过";
    default:
      return "未知状态";
  }
};

const currentPage = ref(1); //页码    
const pageSize = ref(10); //每页条数
const background = ref(true); //背景色
const total = ref(0);

/* //根据id查询名字
const getNameById = async (id) => {
  console.log("id为",id);
  const result = await queryNameByIdApi(id);
  if (result.code) {
    return result.data;
 } else {}
} */


onMounted(() => {
  getToken();
  search();
});

const employee = ref({
  index: "",
  id: "",
  reason:"",
  time:"",
  status:""
});



//获取token
const getToken = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if(loginUser && loginUser.token){
    token.value = loginUser.token;
  }
};

const staffsList = ref([]);
const nameCache = ref({}); // 用于缓存名字
//查询
const search = async () => {
  loading.value = true;
  try {
    const result = await queryPageApi(
      currentPage.value,
      pageSize.value
    );
    console.log(result);
    if (result.code) {
      staffsList.value = result.data.rows;
      total.value = result.data.total;
      fetchNames(); 
    }
  } finally {
    loading.value = false;
  }
};

// 批量获取名字
const fetchNames = async () => {
  const ids = staffsList.value.map(item => item.id);
  for (const id of ids) {
    if (!nameCache.value[id]) {
      const name = await getNameById(id);
      nameCache.value[id] = name;
    }
  }
};

const getNameById = async (id) => {
  console.log("id为", id);
  const result = await queryNameByIdApi(id);
  if (result.code) {
    return result.data;
  } else {
    return "未知";
  }
};


//保存
const save = async () => {
  //表单校验
  if (!staffsFormRef.value) return;
  staffsFormRef.value.validate(async (valid) => {
    //valid表示表单是否校验通过,true表示通过,false表示不通过
    if (valid) {
      let result;

      if (employee.value.index) {
        //修改       
          result = await updateApi(employee.value);
           if (result.code) {
        ElMessage.success("修改成功");
        dialogVisible.value = false;
        search();
      } else {
        ElMessage.error("保存失败");
      }
      } else {
        //新增
          result = await addApi(employee.value);
           if (result.code) {
        ElMessage.success("保存成功");
        dialogVisible.value = false;
        search();
      } else {
        ElMessage.error("保存失败");
      }
      }
      console.log(employee.value);

     
    } else {
      ElMessage.error("请输入正确的信息");
    }
  });
};

//表单引用
const staffsFormRef = ref();

search();



//新增/修改表单

// 控制弹窗
const dialogVisible = ref(false);
const dialogTitle = ref("新增员工");


//编辑
const edit = async (index) => {
  console.log("Edit clicked with scope.row:", index); // 添加日志输出
  const result = await queryByIndex(index);
  if (result.code) {
    dialogVisible.value = true;
    dialogTitle.value = "修改请假申请";
    employee.value = result.data;
    employee.value.index = index;
  }
};

//表单校验规则
const rules = ref({
  reason: [
    { required: true, message: "请输入请假理由", trigger: "blur" },
    {
      min: 1,
      max: 225,
      message: "理由应在1到225个字符之间",
      trigger: "blur",
    },
  ],
  
  time: [
    { required: true, message: "请输入请假时间", trigger: "blur" },
    {
      min: 1,
      max: 20,
      message: "在1到20个字符之间",
      trigger: "blur",
    },
  ]
});



// 删除
const deleteByIndex = (index) => {
    //弹出确认框
    ElMessageBox.confirm('此操作将永久删除该文件, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(
      async () => {
        const result = await deleteApi(index);
        if(result.code){
          ElMessage.success('删除成功');
          search();
        }else{
          ElMessage.error(result.msg);
        }
      }).catch(() => {
        ElMessage.info('已取消删除')
      })        

}                                      
 
//记录勾选的id
const selectionIds = ref([]);
const handleSelectionChange = (selection) => {
 selectionIds.value = selection.map(item => item.index)
}

const deleteByIds = () => {                       
    //弹出确认框
    ElMessageBox.confirm('此操作将永久删除该文件, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(
      async () => {
        if (selectionIds.value && selectionIds.value.length > 0){
          const result = await deleteApi(selectionIds.value);
        if(result.code){
          ElMessage.success('删除成功');
          search();
        }else{
          ElMessage.error(result.msg);
        }
        }else{
          ElMessage.warning('请选择要删除的记录');
        }
        
      }).catch(() => {
        ElMessage.info('已取消删除')
      })
      
}

const loading = ref(false);

//通过
const pass = (index) => {
  console.log("pass clicked with scope.row:", index);
  passApi(index);
   ElMessage.success('审核通过');
  search();
  search();//不知道为什么刷新两次才能更新出页面，有时候会卡
}

//驳回
const reject = (index) => {
  console.log("reject clicked with scope.row:", index);
  rejectApi(index);
  // 重新查询
  ElMessage.success('驳回成功');
  search();
  search();
}

</script>

<template>
  <h1>请假审核</h1>
  <!-- 空行 -->
  <br />


  <div class="container">
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <br>
  

  <div class="container">
    <el-table :data="staffsList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        prop="id"
        label="员工ID"
        width="120"
        align="center"
      />
      <el-table-column
        prop="reason"
        label="姓名"
        width="120"
        align="center"
        >
         <template #default="scope">
        {{ nameCache[scope.row.id] || '加载中...' }}
        </template>
         </el-table-column>
      <el-table-column
        prop="reason"
        label="理由"
        width="600"
        align="center"
      />
      <el-table-column
        prop="time"
        label="请假时间"
        width="120"
        align="center"
      />
      <el-table-column prop="gender" label="审核状态" width="120" align="center">
        <template #default="scope">
        {{ getStatusText(scope.row.status) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="pass(scope.row.index)"
            ><el-icon><EditPen /></el-icon>通过</el-button
          >
          <el-button type="danger" size="small" @click="reject( scope.row.index)"
            ><el-icon><Delete /></el-icon>驳回</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>

  <br />

  <!-- 新增员工操作 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      :model="employee"
      :rules="rules"
      ref="staffsFormRef"
      label-width="80px"
    >
      <!-- 基本信息 -->
      <!-- 第一行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="理由" prop="reason">
            <el-input
              v-model="employee.reason"
              placeholder="请输入请假原因"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="请假时间" prop="time">
            <el-input
              v-model="employee.time"
              placeholder="请输入请假时间"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>

    <!-- 底部按钮 -->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </span>
    </template>
  </el-dialog>
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
