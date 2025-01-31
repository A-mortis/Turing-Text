<script setup>
import { ref, watch, onMounted } from "vue";
import { queryPageApi, addApi, queryByIdApi, updateApi,deleteApi,getStaffApi} from "@/api/activity";
import { ElMessage,ElMessageBox } from "element-plus";
//import axios from 'axios';

const employee = ref({
  username: "",
  password: "",
  name: "",
  gender: ""
});

const currentPage = ref(1); //页码
const pageSize = ref(10); //每页条数
const background = ref(true); //背景色
const total = ref(0);

const staffsDialogVisible = ref(false); // 控制对话框的显示和隐藏
const enrolledStaffs = ref([]); // 存储查询到的员工数据

const getStaffs = async (id) => {
  try {
    const result = await getStaffApi(id);
    if (result.code) {
      enrolledStaffs.value = result.data; // 假设返回的数据是员工列表
      staffsDialogVisible.value = true; // 显示对话框
    } else {
      ElMessage.error("查询失败");
    }
  } catch (error) {
    ElMessage.error("发生错误：" + error.message);
  }
};
//新增活动
const addStaffs = () => {
  dialogVisible.value = true;
  dialogTitle.value = "新增员工";
  employee.value = {
    content:"",
    createTime: "",
  };
};

const searchStaffs = ref({
  content:"",
  date: [],
  createTime: "",
  updateTime: "",
});

onMounted(() => {
  search();
});

const staffsList = ref([]);

//查询
const search = async () => {
  const result = await queryPageApi(
    searchStaffs.value.content,
    searchStaffs.value.createTime,
    searchStaffs.value.updateTime,
    currentPage.value,
    pageSize.value
  );
  console.log(result);
  if (result.code) {
    staffsList.value = result.data.rows;
    total.value = result.data.total;
  }
};

const clean = () => {
  searchStaffs.value = {
    content: "",
    date: [],
  };
};
//保存
const save = async () => {
  //表单校验
  if (!staffsFormRef.value) return;
  staffsFormRef.value.validate(async (valid) => {
    //valid表示表单是否校验通过,true表示通过,false表示不通过
    if (valid) {
      let result;

      if (employee.value.id) {
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

watch(
  () => searchStaffs.value.date,
  (newVal, oldVal) => {
    if (newVal.length === 2) {
      searchStaffs.value.createTime = newVal[0];
      searchStaffs.value.updateTime = newVal[1];
    } else {
      searchStaffs.value.createTime = "";
      searchStaffs.value.updateTime = "";
    }
  }
);



const handleSizeChange = (val) => {
  console.log(`每页展示 ${val} 条记录`);
};
const handleCurrentChange = (val) => {
  console.log(`当前页码: ${val}`);
};

//新增/修改表单

// 控制弹窗
const dialogVisible = ref(false);
const dialogTitle = ref("新增活动");



//编辑
const edit = async (id) => {
  console.log("Edit clicked with scope.row:", id); // 添加日志输出
  const result = await queryByIdApi(id);
  
  if (result.code) {
    dialogVisible.value = true;
    dialogTitle.value = "修改活动信息";
    employee.value = result.data;
    employee.value.id = id;
  }
};

//表单校验规则
const rules = ref({
  content: [
    { required: true, message: "活动内容", trigger: "blur" },
    {
      min: 1,
      max: 225,
      message: "应在1到225个字符之间",
      trigger: "blur",
    },
  ]
});

// 删除
const deleteById = (id) => {
    //弹出确认框
    ElMessageBox.confirm('此操作将永久删除该文件, 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(
      async () => {
        const result = await deleteApi(id);
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
 selectionIds.value = selection.map(item => item.id)
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
  <h1>活动管理</h1>
  <!-- 空行 -->
  <br />

  <el-form :inline="true" :model="searchStaffs" class="demo-form-inline">
    <el-form-item label="活动内容">
      <el-input v-model="searchStaffs.content" placeholder="请输入活动内容" />
    </el-form-item>


    <el-form-item label="发布时间">
      <el-date-picker
        v-model="searchStaffs.date"
        type="daterange"
        range-separator="到"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="YYYY-MM-DD"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="search">查询</el-button>
      <el-button type="info" @click="clean">清空</el-button>
    </el-form-item>
  </el-form>

  <div class="container">
    <el-button type="primary" @click="addStaffs">+ 新增活动</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
  </div>

  <br>
  

  <div class="container">
    <el-table :data="staffsList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        prop="content"
        label="活动内容"
        width="600"
        align="center"
      />
      
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="180"
        align="center"
      />
      <el-table-column
        prop="updateTime"
        label="最后修改时间"
        width="180"
        align="center"
      />
      <el-table-column label="操作" width="300" align="center">
        <template #default="scope">
          <el-button type="success" size="small" @click="getStaffs(scope.row.id)"
            ><el-icon><EditPen /></el-icon>查看报名员工</el-button
          >
          <el-button type="primary" size="small" @click="edit(scope.row.id)"
            ><el-icon><EditPen /></el-icon>编辑</el-button
          >
          <el-button type="danger" size="small" @click="deleteById(scope.row.id)"
            ><el-icon><Delete /></el-icon>删除</el-button
          >
           
        </template>
      </el-table-column>
    </el-table>
  </div>

  <br />
  <!-- 分页 -->
  <div class="container">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[5, 10, 20, 30, 50, 75, 100]"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      align="center"
    />
  </div>

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
      <el-col :span="24">
        <el-form-item label="活动内容" prop="content">
          <el-input
            v-model="employee.content"
            placeholder="请输入活动内容"
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

  <el-dialog v-model="staffsDialogVisible" title="报名员工列表">
    <el-table :data="enrolledStaffs" border style="width: 100%">
      <el-table-column prop="username" label="用户名" align="center" />
      <el-table-column prop="password" label="密码" align="center" />
      <el-table-column prop="name" label="姓名" align="center" />
      <!-- 根据实际返回的数据结构添加更多列 -->
    </el-table>
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