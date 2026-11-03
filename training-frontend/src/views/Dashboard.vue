<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { getCoursesApi, getMyEnrollmentsApi, getExamRecordsApi, getCurrentUserApi } from '../api'
import { BookOpen, Users, FileText, ClipboardList, PlusCircle, Clock, GraduationCap, Calendar, ArrowRight, PlayCircle, Award, ChevronRight } from 'lucide-vue-next'
import type { Course, CourseEnroll } from '../types'

const cachedUser = localStorage.getItem('user')
const currentUser = ref({
  name: (cachedUser ? JSON.parse(cachedUser).name : '') || '管理员',
  avatar: ''
})

const today = computed(() => {
  const date = new Date()
  const weekdays = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
  return `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日 ${weekdays[date.getDay()]}`
})

const quickActions = [
  { label: '创建课程', icon: PlusCircle, color: 'bg-blue-500', path: '/courses' },
  { label: '添加学员', icon: Users, color: 'bg-green-500', path: '/students' },
  { label: '发布考试', icon: FileText, color: 'bg-orange-500', path: '/exams' },
  { label: '报名管理', icon: ClipboardList, color: 'bg-purple-500', path: '/enrollments' }
]

const myLearningProgress = ref<CourseEnroll[]>([])
const recommendedCourses = ref<Course[]>([])
const recentActivities = ref<{ type: string; title: string; time: string; icon: any }[]>([])

onMounted(async () => {
  try {
    const [coursesRes, enrollsRes, recordsRes, meRes] = await Promise.all([
      getCoursesApi(),
      getMyEnrollmentsApi(),
      getExamRecordsApi(),
      getCurrentUserApi()
    ])

    currentUser.value = {
      name: meRes.data.name || meRes.data.username,
      avatar: meRes.data.avatar || ''
    }
    localStorage.setItem('user', JSON.stringify(meRes.data))
    localStorage.setItem('userName', meRes.data.name)
    
    myLearningProgress.value = enrollsRes.data.slice(0, 3)
    recommendedCourses.value = coursesRes.data.slice(0, 6)
    
    const activities: { type: string; title: string; time: string; icon: any }[] = []
    coursesRes.data.slice(0, 2).forEach(course => {
      activities.push({ type: 'course', title: `新课程发布：${course.title}`, time: '刚刚', icon: BookOpen })
    })
    recordsRes.data.slice(0, 2).forEach(record => {
      activities.push({ type: 'exam', title: `考试完成：${record.examTitle}`, time: '30分钟前', icon: Award })
    })
    activities.push({ type: 'system', title: '系统已更新至最新版本', time: '1小时前', icon: Calendar })
    recentActivities.value = activities
  } catch (error) {
    console.error('获取数据失败:', error)
  }
})

const goToPage = (path: string) => {
  window.location.href = path
}
</script>

<template>
  <div class="space-y-6">
    <div class="bg-gradient-to-r from-blue-600 to-indigo-700 rounded-2xl p-6 text-white">
      <div class="flex items-start justify-between">
        <div>
          <div class="flex items-center gap-3">
            <div class="w-12 h-12 bg-white/20 rounded-full flex items-center justify-center">
              <GraduationCap class="w-6 h-6" />
            </div>
            <div>
              <h1 class="text-2xl font-bold">欢迎回来，{{ currentUser.name }}</h1>
              <p class="text-blue-100 mt-1">{{ today }}</p>
            </div>
          </div>
          <p class="mt-4 text-blue-100">开始今天的学习之旅，提升专业技能！</p>
        </div>
        <div class="flex items-center gap-2 text-blue-100">
          <Clock class="w-5 h-5" />
          <span class="text-sm">今日已学习 0 小时</span>
        </div>
      </div>
    </div>
    
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
      <button
        v-for="action in quickActions"
        :key="action.label"
        @click="goToPage(action.path)"
        class="bg-white rounded-xl p-5 shadow-sm hover:shadow-lg hover:-translate-y-1 transition-all duration-300 text-left group"
      >
        <div class="flex items-center justify-between">
          <div :class="['w-11 h-11 rounded-lg flex items-center justify-center', action.color]">
            <component :is="action.icon" class="w-6 h-6 text-white" />
          </div>
          <ArrowRight class="w-5 h-5 text-gray-400 group-hover:text-gray-600 transition-colors" />
        </div>
        <p class="mt-3 font-semibold text-gray-800">{{ action.label }}</p>
      </button>
    </div>
    
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <div class="lg:col-span-2 space-y-6">
        <div class="bg-white rounded-xl shadow-sm">
          <div class="p-6 border-b border-gray-200 flex items-center justify-between">
            <h2 class="text-lg font-semibold text-gray-800">我的学习进度</h2>
            <button class="text-blue-600 text-sm hover:text-blue-700 flex items-center">
              查看全部 <ChevronRight class="w-4 h-4 ml-1" />
            </button>
          </div>
          <div class="p-6 space-y-4">
            <div v-for="enroll in myLearningProgress" :key="enroll.id" class="flex items-center gap-4">
              <div class="w-14 h-14 bg-gradient-to-br from-blue-100 to-blue-200 rounded-lg flex items-center justify-center flex-shrink-0">
                <PlayCircle class="w-7 h-7 text-blue-600" />
              </div>
              <div class="flex-1">
                <div class="flex items-center justify-between">
                  <p class="font-medium text-gray-800">{{ enroll.courseTitle }}</p>
                  <span class="text-sm font-semibold text-blue-600">{{ enroll.progress }}%</span>
                </div>
                <div class="mt-2 h-2 bg-gray-100 rounded-full overflow-hidden">
                  <div :style="{ width: enroll.progress + '%' }" class="h-full bg-gradient-to-r from-blue-500 to-blue-600 rounded-full transition-all duration-500"></div>
                </div>
                <p class="text-xs text-gray-500 mt-1">{{ enroll.isCompleted === 1 ? '已完成' : '学习中' }}</p>
              </div>
            </div>
            <div v-if="myLearningProgress.length === 0" class="p-8 text-center">
              <div class="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto">
                <BookOpen class="w-8 h-8 text-gray-400" />
              </div>
              <p class="mt-4 text-gray-500">还没有报名任何课程</p>
              <button class="mt-3 px-4 py-2 bg-blue-600 text-white rounded-lg text-sm hover:bg-blue-700">
                去报名课程
              </button>
            </div>
          </div>
        </div>
        
        <div class="bg-white rounded-xl shadow-sm">
          <div class="p-6 border-b border-gray-200 flex items-center justify-between">
            <h2 class="text-lg font-semibold text-gray-800">推荐课程</h2>
            <button class="text-blue-600 text-sm hover:text-blue-700 flex items-center">
              查看全部 <ChevronRight class="w-4 h-4 ml-1" />
            </button>
          </div>
          <div class="p-6">
            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
              <div v-for="course in recommendedCourses" :key="course.id" class="bg-gray-50 rounded-xl overflow-hidden hover:shadow-md transition-shadow cursor-pointer">
                <div class="h-36 bg-gradient-to-br from-blue-500 to-indigo-600 relative">
                  <img v-if="course.coverImage" :src="course.coverImage" :alt="course.title" class="w-full h-full object-cover" />
                  <div v-else class="w-full h-full flex items-center justify-center">
                    <BookOpen class="w-12 h-12 text-white/50" />
                  </div>
                  <span class="absolute top-3 right-3 px-2 py-1 bg-white/90 rounded-full text-xs font-medium text-gray-700">
                    {{ course.totalStudents }}人学习
                  </span>
                </div>
                <div class="p-4">
                  <p class="font-medium text-gray-800 line-clamp-2">{{ course.title }}</p>
                  <p class="text-xs text-gray-500 mt-2">{{ course.categoryName }} · {{ course.duration }}课时</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <div class="bg-white rounded-xl shadow-sm">
        <div class="p-6 border-b border-gray-200">
          <h2 class="text-lg font-semibold text-gray-800">最近动态</h2>
        </div>
        <div class="p-6 space-y-4">
          <div v-for="(activity, index) in recentActivities" :key="index" class="flex items-start gap-3">
            <div :class="['w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0', activity.type === 'course' ? 'bg-blue-100' : activity.type === 'exam' ? 'bg-orange-100' : 'bg-gray-100']">
              <component :is="activity.icon" :class="['w-4 h-4', activity.type === 'course' ? 'text-blue-600' : activity.type === 'exam' ? 'text-orange-600' : 'text-gray-600']" />
            </div>
            <div class="flex-1 min-w-0">
              <p class="text-sm text-gray-800">{{ activity.title }}</p>
              <p class="text-xs text-gray-400 mt-1">{{ activity.time }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
