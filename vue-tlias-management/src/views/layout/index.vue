<script setup>
import {ref,onMounted} from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import {useRouter} from 'vue-router';

const loginName = ref('');
const router = useRouter();

onMounted(()=>{
  const loginUser = JSON.parse(localStorage.getItem('loginUser'));
  if( loginUser && loginUser.username){
    loginName.value = loginUser.username;
  }
})

const logout = () => {
   ElMessageBox.confirm('您确认退出吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(
      async () => {
       ElMessage.success('退出成功');
       localStorage.removeItem('loginUser');
       router.push('/login');
      }).catch(() => {
        ElMessage.info('已取消')
      })
}

</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <span class="title">员工管理系统（管理员）</span>
        <span class="right_tool">
          <a href="">
            <el-icon><EditPen /></el-icon> 修改密码 &nbsp;&nbsp;&nbsp; |
            &nbsp;&nbsp;&nbsp;
          </a>
          <a href="javascript:;" @click="logout">
            <el-icon><SwitchButton /></el-icon> 退出登录【{{loginName}}】
          </a>
        </span>
      </el-header>

      <el-container>
        <!-- 左侧菜单 -->
        <el-aside width="200px" class="aside">
          <el-menu :default-openeds="['1', '3']">
            <el-sub-menu index="1">
              <template #title>
                <el-icon><HomeFilled /></el-icon>管理系统
              </template>
              
               

                <router-link to="/information">
                <el-menu-item index="1-1">
                  <el-icon><User /></el-icon>员工信息</el-menu-item>
                </router-link>

                <router-link to="/attendance">
                <el-menu-item index="1-2">
                  <el-icon><Aim /></el-icon>缺勤统计</el-menu-item>
                </router-link>
                
              
             
                <router-link to="/leave">
                  <el-menu-item index="1-3">
                    <el-icon><Position /></el-icon>请假申请</el-menu-item>
                </router-link>
                <router-link to="/resignation">
                  <el-menu-item index="1-4">
                    <el-icon><Edit /></el-icon>离职申请</el-menu-item>
                </router-link>
                <router-link to="activity">
                   <el-menu-item index="1-5">
                    <el-icon><UploadFilled /></el-icon>活动管理</el-menu-item>
                </router-link>
                
               
                
               
              
            </el-sub-menu>
       
          </el-menu>
        </el-aside>

        <el-main> 
          <router-view></router-view>
          </el-main>         
      </el-container>

    </el-container>
  </div>
</template>

<style scoped>
.header {
  background-image: linear-gradient(
    to right,
    #00547d,
    #007fa4,
    #00aaa0,
    #00d072,
    #a8eb12
  );
}

.title {
  color: white;
  font-size: 40px;
  font-family: 楷体;
  line-height: 60px;
  font-weight: bolder;
}

.right_tool {
  float: right;
  line-height: 60px;
}

a {
  color: white;
  text-decoration: none;
}

.aside {
  width: 220px;
  border-right: 1px solid #ccc;
  height: 730px;
}
</style>
