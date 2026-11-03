<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { getCurrentUserApi } from '../api'
import {
  LayoutDashboard,
  BookOpen,
  FileText,
  Users,
  ClipboardList,
  BarChart3,
  LogOut,
  Menu,
  X
} from 'lucide-vue-next'

const router = useRouter()
const route = useRoute()
const collapsed = ref(false)
const user = ref<any>(null)

onMounted(async () => {
  const userStr = localStorage.getItem('user')
  if (userStr) {
    user.value = JSON.parse(userStr)
  }
  try {
    const res = await getCurrentUserApi()
    user.value = res.data
    localStorage.setItem('user', JSON.stringify(res.data))
    localStorage.setItem('userName', res.data.name)
  } catch (error) {
    console.error('获取当前用户失败:', error)
  }
})

const menuItems = [
  { path: '/dashboard', name: '首页', icon: LayoutDashboard },
  { path: '/courses', name: '课程管理', icon: BookOpen },
  { path: '/exams', name: '考试管理', icon: FileText },
  { path: '/students', name: '学员管理', icon: Users },
  { path: '/enrollments', name: '报名管理', icon: ClipboardList },
  { path: '/statistics', name: '数据统计', icon: BarChart3 }
]

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  router.push('/login')
}

const isActive = (path: string) => {
  return route.path === path || route.path.startsWith(path + '/')
}
</script>

<template>
  <div class="flex h-screen bg-gray-50">
    <aside
      :class="[
        'bg-white border-r border-gray-200 flex flex-col transition-all duration-300',
        collapsed ? 'w-20' : 'w-64'
      ]"
    >
      <div class="p-4 border-b border-gray-200">
        <div class="flex items-center">
          <div class="w-10 h-10 bg-primary-500 rounded-lg flex items-center justify-center">
            <BookOpen class="w-5 h-5 text-white" />
          </div>
          <span
            v-if="!collapsed"
            class="ml-3 text-lg font-bold text-gray-800"
          >
            EduForge
          </span>
        </div>
      </div>
      
      <nav class="flex-1 p-4">
        <ul class="space-y-2">
          <li v-for="item in menuItems" :key="item.path">
            <router-link
              :to="item.path"
              :class="[
                'flex items-center px-4 py-3 rounded-lg transition-all duration-200',
                isActive(item.path)
                  ? 'bg-primary-50 text-primary-600'
                  : 'text-gray-600 hover:bg-gray-100'
              ]"
            >
              <component :is="item.icon" class="w-5 h-5" />
              <span v-if="!collapsed" class="ml-3">{{ item.name }}</span>
            </router-link>
          </li>
        </ul>
      </nav>
      
      <div class="p-4 border-t border-gray-200">
        <button
          @click="handleLogout"
          class="flex items-center w-full px-4 py-3 text-gray-600 hover:bg-gray-100 rounded-lg transition-all duration-200"
        >
          <LogOut class="w-5 h-5" />
          <span v-if="!collapsed" class="ml-3">退出登录</span>
        </button>
      </div>
    </aside>
    
    <div class="flex-1 flex flex-col overflow-hidden">
      <header class="bg-white border-b border-gray-200 px-6 py-4">
        <div class="flex items-center justify-between">
          <button
            @click="collapsed = !collapsed"
            class="p-2 hover:bg-gray-100 rounded-lg transition-colors"
          >
            <Menu v-if="!collapsed" class="w-5 h-5 text-gray-600" />
            <X v-else class="w-5 h-5 text-gray-600" />
          </button>
          
          <div class="flex items-center">
            <span class="text-gray-600 mr-4">欢迎，{{ user?.name || user?.username }}</span>
            <div class="w-8 h-8 bg-primary-100 rounded-full flex items-center justify-center">
              <span class="text-primary-600 font-medium">{{ (user?.name || user?.username || 'U')[0] }}</span>
            </div>
          </div>
        </div>
      </header>
      
      <main class="flex-1 overflow-y-auto p-6">
        <router-view />
      </main>
    </div>
  </div>
</template>
