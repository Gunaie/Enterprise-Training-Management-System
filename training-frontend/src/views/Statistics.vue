<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getStatisticsApi, getCoursesApi, getExamRecordsApi } from '../api'
import { BarChart3, BookOpen, Users, FileText, ClipboardList, Award, TrendingUp, TrendingDown } from 'lucide-vue-next'
import type { Course, ExamRecord } from '../types'

const stats = ref({
  totalCourses: 0,
  totalStudents: 0,
  totalExams: 0,
  totalEnrollments: 0
})

const courses = ref<Course[]>([])
const examRecords = ref<ExamRecord[]>([])

onMounted(async () => {
  await loadData()
})

const loadData = async () => {
  try {
    const [statsRes, coursesRes, recordsRes] = await Promise.all([
      getStatisticsApi(),
      getCoursesApi(),
      getExamRecordsApi()
    ])
    stats.value = statsRes.data
    courses.value = coursesRes.data
    examRecords.value = recordsRes.data
  } catch (error) {
    console.error('获取统计数据失败:', error)
  }
}

const courseStats = () => {
  return courses.value.map(course => ({
    name: course.title.length > 8 ? course.title.substring(0, 8) + '...' : course.title,
    students: course.totalStudents,
    chapters: course.chapters.length,
    duration: course.duration
  }))
}

const avgScore = () => {
  if (examRecords.value.length === 0) return 0
  const sum = examRecords.value.reduce((acc, record) => acc + (record.score || 0), 0)
  return Math.round(sum / examRecords.value.length)
}

const passRate = () => {
  if (examRecords.value.length === 0) return 0
  const passed = examRecords.value.filter(r => r.status === 1).length
  return Math.round((passed / examRecords.value.length) * 100)
}
</script>

<template>
  <div class="space-y-6">
    <div>
      <h1 class="text-2xl font-bold text-gray-800">数据统计</h1>
      <p class="text-gray-500 mt-1">查看培训系统的详细数据统计和分析</p>
    </div>
    
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <div class="bg-white rounded-xl p-6 shadow-sm">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-500 text-sm">课程总数</p>
            <p class="text-3xl font-bold text-gray-800 mt-2">{{ stats.totalCourses }}</p>
          </div>
          <div class="w-12 h-12 bg-blue-100 rounded-xl flex items-center justify-center">
            <BookOpen class="w-6 h-6 text-blue-600" />
          </div>
        </div>
      </div>
      
      <div class="bg-white rounded-xl p-6 shadow-sm">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-500 text-sm">学员总数</p>
            <p class="text-3xl font-bold text-gray-800 mt-2">{{ stats.totalStudents }}</p>
          </div>
          <div class="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center">
            <Users class="w-6 h-6 text-green-600" />
          </div>
        </div>
      </div>
      
      <div class="bg-white rounded-xl p-6 shadow-sm">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-500 text-sm">考试总数</p>
            <p class="text-3xl font-bold text-gray-800 mt-2">{{ stats.totalExams }}</p>
          </div>
          <div class="w-12 h-12 bg-orange-100 rounded-xl flex items-center justify-center">
            <FileText class="w-6 h-6 text-orange-600" />
          </div>
        </div>
      </div>
      
      <div class="bg-white rounded-xl p-6 shadow-sm">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-gray-500 text-sm">报名总数</p>
            <p class="text-3xl font-bold text-gray-800 mt-2">{{ stats.totalEnrollments }}</p>
          </div>
          <div class="w-12 h-12 bg-purple-100 rounded-xl flex items-center justify-center">
            <ClipboardList class="w-6 h-6 text-purple-600" />
          </div>
        </div>
      </div>
    </div>
    
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <div class="bg-white rounded-xl shadow-sm">
        <div class="p-6 border-b border-gray-200">
          <h2 class="text-lg font-semibold text-gray-800">课程学习人数统计</h2>
        </div>
        <div class="p-6">
          <div class="space-y-4">
            <div
              v-for="course in courseStats()"
              :key="course.name"
              class="space-y-2"
            >
              <div class="flex items-center justify-between">
                <span class="text-gray-700 font-medium">{{ course.name }}</span>
                <span class="text-gray-500">{{ course.students }}人</span>
              </div>
              <div class="h-2 bg-gray-100 rounded-full overflow-hidden">
                <div
                  class="h-full bg-gradient-to-r from-primary-400 to-primary-600 rounded-full transition-all duration-500"
                  :style="{ width: `${(course.students / Math.max(...courseStats().map(c => c.students), 1)) * 100}%` }"
                ></div>
              </div>
            </div>
            
            <div v-if="courseStats().length === 0" class="text-center py-8 text-gray-400">
              暂无课程数据
            </div>
          </div>
        </div>
      </div>
      
      <div class="bg-white rounded-xl shadow-sm">
        <div class="p-6 border-b border-gray-200">
          <h2 class="text-lg font-semibold text-gray-800">考试成绩统计</h2>
        </div>
        <div class="p-6">
          <div class="grid grid-cols-2 gap-6">
            <div class="text-center p-6 bg-gray-50 rounded-xl">
              <Award class="w-12 h-12 text-yellow-500 mx-auto mb-3" />
              <p class="text-gray-500 text-sm mb-1">平均分</p>
              <p class="text-3xl font-bold text-gray-800">{{ avgScore() }}</p>
            </div>
            <div class="text-center p-6 bg-gray-50 rounded-xl">
              <div :class="['w-12 h-12 rounded-xl flex items-center justify-center mx-auto mb-3', passRate() >= 60 ? 'bg-green-100' : 'bg-orange-100']">
                <TrendingUp v-if="passRate() >= 60" :class="['w-6 h-6', passRate() >= 60 ? 'text-green-600' : 'text-orange-600']" />
                <TrendingDown v-else :class="['w-6 h-6', passRate() >= 60 ? 'text-green-600' : 'text-orange-600']" />
              </div>
              <p class="text-gray-500 text-sm mb-1">及格率</p>
              <p class="text-3xl font-bold text-gray-800">{{ passRate() }}%</p>
            </div>
          </div>
          
          <div class="mt-6">
            <p class="text-sm text-gray-500 mb-3">最近考试记录</p>
            <div class="space-y-3">
              <div
                v-for="record in examRecords.slice(0, 5)"
                :key="record.id"
                class="flex items-center justify-between p-3 bg-gray-50 rounded-lg"
              >
                <div>
                  <p class="text-sm font-medium text-gray-700">{{ record.studentName }}</p>
                  <p class="text-xs text-gray-500">{{ record.examTitle }}</p>
                </div>
                <span :class="['text-lg font-bold', record.status === 1 ? 'text-green-500' : 'text-red-500']">
                  {{ record.score }}分
                </span>
              </div>
              <div v-if="examRecords.length === 0" class="text-center py-4 text-gray-400">
                暂无考试记录
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="bg-white rounded-xl shadow-sm">
      <div class="p-6 border-b border-gray-200">
        <h2 class="text-lg font-semibold text-gray-800">课程详情统计</h2>
      </div>
      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">课程名称</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">分类</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">学习人数</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">章节数</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">时长</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">状态</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr
              v-for="course in courses"
              :key="course.id"
              class="hover:bg-gray-50 transition-colors"
            >
              <td class="px-6 py-4">
                <div class="flex items-center">
                  <BookOpen class="w-5 h-5 text-primary-500 mr-3" />
                  <span class="font-medium text-gray-800">{{ course.title }}</span>
                </div>
              </td>
              <td class="px-6 py-4 text-gray-600">{{ course.categoryName }}</td>
              <td class="px-6 py-4 text-gray-600">{{ course.totalStudents }}</td>
              <td class="px-6 py-4 text-gray-600">{{ course.chapters.length }}</td>
              <td class="px-6 py-4 text-gray-600">{{ course.duration }}小时</td>
              <td class="px-6 py-4">
                <span :class="['px-3 py-1 rounded-full text-xs font-medium', course.status === 1 ? 'bg-green-100 text-green-600' : 'bg-gray-100 text-gray-600']">
                  {{ course.status === 1 ? '已发布' : '草稿' }}
                </span>
              </td>
            </tr>
            
            <tr v-if="courses.length === 0">
              <td colspan="6" class="px-6 py-12 text-center">
                <BarChart3 class="w-16 h-16 text-gray-300 mx-auto mb-4" />
                <p class="text-gray-500">暂无课程数据</p>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
