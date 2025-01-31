<script setup>
import { ref, watch, onMounted } from "vue";
import { queryPageApi, addApi} from "@/api/attendance";
import { ElMessage,ElMessageBox } from "element-plus";
//import axios from 'axios';

const employee = ref({
  username: "",
  createTime: "",
});

const currentPage = ref(1); //页码
const pageSize = ref(10); //每页条数
const background = ref(true); //背景色
const total = ref(0);

//新增员工
const addStaffs = () => {
  dialogVisible.value = true;
  dialogTitle.value = "新增员工";
  employee.value = {
    username: "",
    createTime: "",
  };
};

const searchStaffs = ref({
  username: "",
  date: [],
  createTime: "",
  updateTime: ""
});

onMounted(() => {
  search();
});

const staffsList = ref([]);

//查询
const search = async () => {
  const result = await queryPageApi(
    searchStaffs.value.username,
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
    name: "",
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
        //新增
          result = await addApi(employee.value);
           if (result.code) {
        ElMessage.success("保存成功");
        dialogVisible.value = false;
        search();
      } else {
        ElMessage.error("保存失败");
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


// 控制弹窗
const dialogVisible = ref(false);
const dialogTitle = ref("记录缺勤");



//表单校验规则
const rules = ref({
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    {
      min: 1,
      max: 20,
      message: "用户名长度应在1到20个字符之间",
      trigger: "blur",
    },
  ]

 /* ,
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
     正则表达式：/ …… /；^: 开头  $：结尾  \d：数字[0-9]
    [3-9]范围3到9之间
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ] */,
});




</script>

<template>
  <h1>缺勤信息</h1>
  <!-- 空行 -->
  <br />

  <el-form :inline="true" :model="searchStaffs" class="demo-form-inline">
    <el-form-item label="姓名">
      <el-input v-model="searchStaffs.name" placeholder="请输入员工注册的用户名" />
    </el-form-item>

    <el-form-item label="入职时间">
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
    <el-button type="primary" @click="addStaffs">+ 新增缺勤记录</el-button>
  </div>

  <br>
  

  <div class="container">
    <el-table :data="staffsList" border style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        prop="username"
        label="账号"
        width="120"
        align="center"
      />

      <el-table-column
        prop="createTime"
        label="记录时间"
        width="180"
        align="center"
      />
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

  <!-- 新增操作 -->
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
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="employee.username"
              placeholder="新增缺勤记录"
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