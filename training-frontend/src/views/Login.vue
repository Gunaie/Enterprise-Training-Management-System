<script setup lang="ts">
import { ref } from 'vue'
import { loginApi } from '../api'
import { useRouter } from 'vue-router'
import { BookOpen, User, Lock } from 'lucide-vue-next'

const router = useRouter()
const username = ref('')
const password = ref('')
const loading = ref(false)
const errorMsg = ref('')

const handleLogin = async () => {
  if (!username.value || !password.value) {
    errorMsg.value = '请输入用户名和密码'
    return
  }
  
  loading.value = true
  errorMsg.value = ''
  
  try {
    const response = await loginApi({
      username: username.value,
      password: password.value
    })
    
    localStorage.setItem('token', response.data.token)
    localStorage.setItem('user', JSON.stringify(response.data))
    
    router.push('/dashboard')
  } catch (error: any) {
    errorMsg.value = error.response?.data?.msg || '登录失败，请检查用户名和密码'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-50 via-white to-indigo-50">
    <div class="w-full max-w-md mx-4">
      <div class="bg-white rounded-2xl shadow-xl p-8">
        <div class="text-center mb-8">
          <div class="w-16 h-16 bg-primary-500 rounded-xl flex items-center justify-center mx-auto mb-4">
            <BookOpen class="w-8 h-8 text-white" />
          </div>
          <h1 class="text-2xl font-bold text-gray-800">EduForge 企业培训平台</h1>
          <p class="text-gray-500 mt-2">登录后管理课程、学员与考试</p>
        </div>
        
        <div class="space-y-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">用户名</label>
            <div class="relative">
              <User class="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
              <input
                v-model="username"
                type="text"
                placeholder="请输入用户名"
                class="w-full pl-10 pr-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none transition-all"
              />
            </div>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">密码</label>
            <div class="relative">
              <Lock class="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
              <input
                v-model="password"
                type="password"
                placeholder="请输入密码"
                class="w-full pl-10 pr-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none transition-all"
              />
            </div>
          </div>
          
          <div v-if="errorMsg" class="bg-red-50 text-red-500 text-sm px-4 py-3 rounded-lg">
            {{ errorMsg }}
          </div>
          
          <button
            @click="handleLogin"
            :disabled="loading"
            class="w-full bg-primary-500 hover:bg-primary-600 text-white font-medium py-3 rounded-lg transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="loading" class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              登录中...
            </span>
            <span v-else>登 录</span>
          </button>
        </div>
        
        <div class="mt-8 text-center">
          <p class="text-gray-400 text-sm">默认账号：admin / admin</p>
        </div>
      </div>
    </div>
  </div>
</template>
