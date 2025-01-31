<script setup>
import { ref, watch, onMounted } from "vue";
import {queryByIdApi, updateApi } from "@/api/staffs";
import { ElMessage,ElMessageBox } from "element-plus";
//import axios from 'axios';

const token = ref('');
const id = ref('');

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
  username: "",
  password: "",
  name: "",
  gender: "",
  salary: "",
  createTime: "",
  image: "",
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
    console.log(id.value);
  const result = await  queryByIdApi(loginUser.id);
  console.log(result);
  if (result.code) {
    staffsList.value = [result.data];
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






// 控制弹窗
const dialogVisible = ref(false);

//文件上传
// 图片上传成功后触发
const handleAvatarSuccess = (response) => {
  employee.value.image = response.data;
};
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== "image/jpeg" && rawFile.type !== "image/png") {
    ElMessage.error("只支持上传图片");
    return false;
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error("只能上传10M以内图片");
    return false;
  }
  return true;
};

//编辑
const edit = async (id) => {
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  console.log("Edit clicked with scope.row:", loginUser.id); // 添加日志输出
  const result = await queryByIdApi(loginUser.id);
  console.log(result);
  if (result.code) {
    dialogVisible.value = true;
    employee.value = result.data;
    employee.value.id = loginUser.id;
  }
};

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
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    {
      min: 1,
      max: 20,
      message: "密码长度应在1到20个字符之间",
      trigger: "blur",
    },
  ],
  name: [
    { required: true, message: "请输入姓名", trigger: "blur" },
    {
      min: 1,
      max: 10,
      message: "姓名长度应在1到10个字符之间",
      trigger: "blur",
    },
  ],
  gender: [{ required: true, message: "请选择性别", trigger: "change" }] 
});

</script>

<template>
  <h1>员工管理</h1>
  <!-- 空行 -->
  <br />

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
        prop="password"
        label="密码"
        width="120"
        align="center"
      />
      <el-table-column prop="name" label="姓名" width="120" align="center" />
      <el-table-column prop="gender" label="性别" width="120" align="center">
        <template #default="scope">
          {{ scope.row.gender == 1 ? "男" : "女" }}
        </template>
      </el-table-column>
      <el-table-column prop="salary" label="薪水" width="120" align="center" />
      <el-table-column prop="image" label="头像" width="120" align="center">
        <template #default="scope">
          <img :src="scope.row.image" width="50" height="50" />
        </template>
      </el-table-column>
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
      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" @click="edit(scope.row.id)"
            ><el-icon><EditPen /></el-icon>编辑</el-button
          >
        </template>
      </el-table-column>
    </el-table>
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
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="employee.username"
              placeholder="请输入员工用户名"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第二行 -->

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="employee.password"
              placeholder="请输入员工密码"
            ></el-input>
          </el-form-item>
        </el-col>

      </el-row>


      <!-- 第四行 -->
      <!--  <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
                <el-option label="研发部" value="1"></el-option>
                <el-option label="市场部" value="2"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期">
              <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row> -->

      <!-- 第五行 -->
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="头像">
            <el-upload
              class="avatar-uploader"
              action="/api/upload"
              :headers="{'token':token}"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="employee.image" :src="employee.image" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
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