<script setup>
import { ref, watch, onMounted } from "vue";
import {addApi, queryByIdApi, updateApi,deleteApi,queryByIndex } from "@/api/resignation";
import { ElMessage,ElMessageBox } from "element-plus";
//import axios from 'axios';

const token = ref('');
const id = ref('');
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
const init = async () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  console.log(loginUser.id);
  if (loginUser.id) {
    id.value = loginUser.id;
    await search();
  } else {
    ElMessage.error("未找到有效的用户ID");
  }
};

onMounted(() => {
  init();
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


//新增员工
const addStaffs = () => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  console.log(loginUser.id);
  dialogVisible.value = true;
  dialogTitle.value = "发出请假";
  employee.value = {
  id: loginUser.id,
  reason:"",
  time:"",
  status:""
  };
};



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
    console.log(id.value);
  const result = await  queryByIdApi(loginUser.id);
  console.log(result);
  if (result.code) {
    staffsList.value = result.data;
     console.log("staffsList:", staffsList.value); // 检查每个对象是否有 index 属性
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
const dialogTitle = ref("请假申请");


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

// 删除员工
const deleteByIndex = (index) => {
  if (!index) {
    ElMessage.error('无效的索引');
    return;
  }

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
 
//记录勾选员工的index
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


</script>

<template>
  <h1>离职申请</h1>
  <!-- 空行 -->
  <br />


  <div class="container">
    <el-button type="primary" @click="addStaffs">+ 离职申请</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <br>
  

  <div class="container">
    <el-table :data="staffsList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        prop="reason"
        label="理由"
        width="600"
        align="center"
      />
      <el-table-column
        prop="time"
        label="离职日期"
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
          <el-button type="primary" size="small" @click="edit( scope.row.index)"
            ><el-icon><EditPen /></el-icon>修改</el-button
          >
          <el-button type="danger" size="small" @click="deleteByIndex( scope.row.index)"
            ><el-icon><Delete /></el-icon>取消</el-button
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
              placeholder="请输入离职原因"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="请假日期" prop="time">
            <el-input
              v-model="employee.time"
              placeholder="请输入离职日期"
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