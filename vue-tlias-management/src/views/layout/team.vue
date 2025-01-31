<script setup>
import indexPage from './index.vue'

import { ref, reactive, onMounted, onBeforeUnmount } from 'vue'
 
 const user=ref('')
const get=()=>{
   user.value= JSON.parse(localStorage.getItem('token')).userr; 
}
</script>

<template>

<indexPage v-if="user==='dfhjsdhfjksdhfjsd'"></indexPage>
</template>

<style scoped>

</style>