<script setup>
import { ref, watch, onMounted } from "vue";
import { queryPageApi, addApi, queryByIdApi, updateApi,deleteApi } from "@/api/staffs";
import { ElMessage,ElMessageBox } from "element-plus";
//import axios from 'axios';

const token = ref('');

const employee = ref({
  username: "",
  password: "",
  name: "",
  gender: "",
  salary: "",
  createTime: "",
  image: "",
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
    password: "",
    name: "",
    gender: "",
    salary: "",
    createTime: "",
    image: "",
  };
};

const searchStaffs = ref({
  name: "",
  gender: "",
  date: [],
  createTime: "",
  updateTime: "",
});

onMounted(() => {
  search();
  getToken();
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
  const result = await queryPageApi(
    searchStaffs.value.name,
    searchStaffs.value.gender,
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
    gender: "",
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
    if(dialogVisible.value == true){
      ElMessage.error("用户名重复");
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

// 这是一个watch侦听
/* const a = ref('');
watch(a,(newVal,oldVal) => {
  console.log(`变化前的值是 ${oldVal}, 变化后的值是 ${newVal}`);
}) */

//侦听一个对象全部属性
/* const user = ref({name:'',age:18});
watch(user,(newVal,oldVal) => {
  console.log(newVal);
},{deep : true}) */

//员工列表数据
/* const staffsList = ref([
  {
  "id": 1,
  "username": "testUser",
  "password": "123456",
  "name": "张三",
  "salary": 8000,
  "createTime": "2023-10-01T09:00:00",
  "updateTime": "2023-10-01T09:00:00",
  "gender": 1,
  "image": "profile.jpg"
}
]);
 */

const handleSizeChange = (val) => {
  console.log(`每页展示 ${val} 条记录`);
};
const handleCurrentChange = (val) => {
  console.log(`当前页码: ${val}`);
};

//新增/修改表单

// 控制弹窗
const dialogVisible = ref(false);
const dialogTitle = ref("新增员工");

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
  console.log("Edit clicked with scope.row:", id); // 添加日志输出
  const result = await queryByIdApi(id);
  
  if (result.code) {
    dialogVisible.value = true;
    dialogTitle.value = "修改员工信息";
    employee.value = result.data;
    employee.value.id = id;
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
  gender: [{ required: true, message: "请选择性别", trigger: "change" }] /* ,
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
     正则表达式：/ …… /；^: 开头  $：结尾  \d：数字[0-9]
    [3-9]范围3到9之间
    { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号', trigger: 'blur' }
  ] */,
});

// 删除员工
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
 
//记录勾选员工的id
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
  <h1>员工管理</h1>
  <!-- 空行 -->
  <br />

  <el-form :inline="true" :model="searchStaffs" class="demo-form-inline">
    <el-form-item label="姓名">
      <el-input v-model="searchStaffs.name" placeholder="请输入员工姓名" />
    </el-form-item>

    <el-form-item label="性别">
      <el-select v-model="searchStaffs.gender" placeholder="请选择">
        <el-option label="男" value="1" />
        <el-option label="女" value="2" />
      </el-select>
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
    <el-button type="primary" @click="addStaffs">+ 新增员工（注册）</el-button>
    <el-button type="danger" @click="deleteByIds">- 批量删除</el-button>
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
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="employee.username"
              placeholder="请输入员工用户名"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input
              v-model="employee.name"
              placeholder="请输入员工姓名"
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

        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select
              v-model="employee.gender"
              placeholder="请选择性别"
              style="width: 100%"
            >
              <el-option label="男" value="1"></el-option>
              <el-option label="女" value="2"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!-- 第三行 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="薪资">
            <el-input
              v-model="employee.salary"
              placeholder="请输入员工薪资"
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

      <!-- 工作经历 -->
      <!-- 第六行 -->
      <!-- <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作经历">
              <el-button type="success" size="small" @click="">+ 添加工作经历</el-button>
            </el-form-item>
          </el-col>
        </el-row> -->

      <!-- 第七行 ...  工作经历 -->
      <!-- <el-row :gutter="3">
          <el-col :span="10">
            <el-form-item size="small" label="时间" label-width="80px">
              <el-date-picker type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="公司" label-width="60px">
              <el-input placeholder="请输入公司名称"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item size="small" label="职位" label-width="60px">
              <el-input placeholder="请输入职位"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="2">
            <el-form-item size="small" label-width="0px">
              <el-button type="danger" >- 删除</el-button>
            </el-form-item>
          </el-col>
        </el-row> -->
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