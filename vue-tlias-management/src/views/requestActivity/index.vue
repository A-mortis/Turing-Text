<script setup>
import { ref, watch, onMounted } from "vue";
import {
  queryPageApi,
  addApi,
  queryByIdApi,
  updateApi,
  deleteApi,
  signUpApi,
  cancelApi,
  confirmApi
} from "@/api/activity";
import { ElMessage, ElMessageBox } from "element-plus";
//import axios from 'axios';

const employee = ref({
  content: "",
  createTime: "",
});

const updateStatusInLocalStorage = (id, status) => {
  let savedState = JSON.parse(localStorage.getItem('staffsState')) || [];
  const index = savedState.findIndex((item) => item.id === id);
  if (index !== -1) {
    savedState[index].status = status;
  } else {
    savedState.push({ id, status });
  }
  localStorage.setItem('staffsState', JSON.stringify(savedState));
};


const currentPage = ref(1); //页码
const pageSize = ref(10); //每页条数
const background = ref(true); //背景色
const total = ref(0);


//报名
const enroll = async (id) => {
  const loginUser = JSON.parse(localStorage.getItem("loginUser"));
  const result = await signUpApi(loginUser.id,id);
  if (result.code) {
    ElMessage.success("报名成功");

    // 更新前端状态
    const index = staffsList.value.findIndex((item) => item.id === id);
    if (index !== -1) {
      staffsList.value[index].status = "已报名";
       updateStatusInLocalStorage(id, "已报名");
    }
  } else {
    ElMessage.error("报名失败");
  }
};

//取消报名
const cancel = async (id) => {
  const loginUser = JSON.parse(localStorage.getItem("loginUser"));
  const result = await cancelApi(loginUser.id,id);
  if (result.code) {
    ElMessage.success("取消报名成功");
    const index = staffsList.value.findIndex((item) => item.id === id);
    if (index !== -1) {
      staffsList.value[index].status = "未报名";
       updateStatusInLocalStorage(id, "未报名");
    }
  } else {
    ElMessage.error("报名失败");
  }
};


const searchStaffs = ref({
  content: "",
  date: [],
  createTime: "",
  updateTime: "",
  status: "未报名",
});

onMounted(() => {
  search();

 const savedState = JSON.parse(localStorage.getItem('staffsState')) || [];
  if (savedState.length > 0) {
    staffsList.value = staffsList.value.map(item => {
      const savedItem = savedState.find(saved => saved.id === item.id);
      return { ...item, status: savedItem ? savedItem.status : item.status };
    });
  }
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
    let updatedList = result.data.rows.map((item) => ({
      ...item,
      status: item.status || "未报名", // 初始化为未报名状态
    }));

    // 合并本地存储的状态
    const savedState = JSON.parse(localStorage.getItem('staffsState')) || [];
    updatedList = updatedList.map(item => {
      const savedItem = savedState.find(saved => saved.id === item.id);
      return { ...item, status: savedItem ? savedItem.status : item.status };
    });

    staffsList.value = updatedList;
    total.value = result.data.total;
  }
};

const clean = () => {
  searchStaffs.value = {
    content: "",
    date: [],
  };
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


// 控制弹窗
const dialogVisible = ref(false);
const dialogTitle = ref("新增活动");


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
  ],
});


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


  <br />

  <div class="container">
    <el-table
      :data="staffsList"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        prop="content"
        label="活动内容"
        width="600"
        align="center"
      />

      <el-table-column
        prop="status"
        label="是否报名"
        width="120"
        align="center"
      />
      <el-table-column
        prop="createTime"
        label="发布时间"
        width="180"
        align="center"
      />

      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="enroll(scope.row.id)"
            ><el-icon><EditPen /></el-icon>报名</el-button
          >
          <el-button type="danger" size="small" @click="cancel(scope.row.id)"
            ><el-icon><Delete /></el-icon>取消报名</el-button
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