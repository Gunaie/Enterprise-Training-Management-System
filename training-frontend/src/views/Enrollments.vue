<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getEnrollmentsApi, enrollCourseApi, getCoursesApi, getStudentsApi } from '../api'
import { ClipboardList, Plus, Calendar, BookOpen } from 'lucide-vue-next'
import type { CourseEnroll, Course, Student } from '../types'

const enrollments = ref<CourseEnroll[]>([])
const courses = ref<Course[]>([])
const students = ref<Student[]>([])
const showModal = ref(false)

const formData = ref({
  courseId: 0,
  studentId: 0
})

onMounted(async () => {
  await loadEnrollments()
  await loadCourses()
  await loadStudents()
})

const loadEnrollments = async () => {
  try {
    const response = await getEnrollmentsApi()
    enrollments.value = response.data
  } catch (error) {
    console.error('获取报名记录失败:', error)
  }
}

const loadCourses = async () => {
  try {
    const response = await getCoursesApi()
    courses.value = response.data.filter(c => c.status === 1)
  } catch (error) {
    console.error('获取课程失败:', error)
  }
}

const loadStudents = async () => {
  try {
    const response = await getStudentsApi()
    students.value = response.data
  } catch (error) {
    console.error('获取学员失败:', error)
  }
}

const handleAdd = () => {
  formData.value = {
    courseId: courses.value[0]?.id || 0,
    studentId: students.value[0]?.id || 0
  }
  showModal.value = true
}

const handleSubmit = async () => {
  try {
    await enrollCourseApi(formData.value.courseId)
    showModal.value = false
    await loadEnrollments()
  } catch (error) {
    console.error('报名失败:', error)
  }
}

const getStatusText = (status: number) => {
  const statusMap: Record<number, string> = {
    0: '待审核',
    1: '已通过',
    2: '已拒绝'
  }
  return statusMap[status] || '未知'
}

const getStatusColor = (status: number) => {
  const colorMap: Record<number, string> = {
    0: 'bg-yellow-100 text-yellow-600',
    1: 'bg-green-100 text-green-600',
    2: 'bg-red-100 text-red-600'
  }
  return colorMap[status] || 'bg-gray-100 text-gray-600'
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-bold text-gray-800">报名管理</h1>
        <p class="text-gray-500 mt-1">管理学员的课程报名记录</p>
      </div>
      <button
        @click="handleAdd"
        class="flex items-center px-4 py-2 bg-primary-500 text-white rounded-lg hover:bg-primary-600 transition-colors"
      >
        <Plus class="w-5 h-5 mr-2" />
        新增报名
      </button>
    </div>
    
    <div class="bg-white rounded-xl shadow-sm overflow-hidden">
      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">课程名称</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">学员姓名</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">报名时间</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">状态</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr
              v-for="enroll in enrollments"
              :key="enroll.id"
              class="hover:bg-gray-50 transition-colors"
            >
              <td class="px-6 py-4">
                <div class="flex items-center">
                  <BookOpen class="w-5 h-5 text-primary-500 mr-3" />
                  <span class="font-medium text-gray-800">{{ enroll.courseTitle }}</span>
                </div>
              </td>
              <td class="px-6 py-4">
                <div class="flex items-center">
                  <div class="w-8 h-8 bg-primary-100 rounded-full flex items-center justify-center mr-3">
                    <span class="text-primary-600 text-sm font-medium">{{ enroll.studentName[0] }}</span>
                  </div>
                  <span class="text-gray-600">{{ enroll.studentName }}</span>
                </div>
              </td>
              <td class="px-6 py-4">
                <span class="flex items-center text-gray-600">
                  <Calendar class="w-4 h-4 mr-1" />
                  {{ new Date(enroll.enrollTime).toLocaleString() }}
                </span>
              </td>
              <td class="px-6 py-4">
                <span :class="['px-3 py-1 rounded-full text-xs font-medium', getStatusColor(enroll.status)]">
                  {{ getStatusText(enroll.status) }}
                </span>
              </td>
            </tr>
            
            <tr v-if="enrollments.length === 0">
              <td colspan="4" class="px-6 py-12 text-center">
                <ClipboardList class="w-16 h-16 text-gray-300 mx-auto mb-4" />
                <p class="text-gray-500">暂无报名记录</p>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    
    <div v-if="showModal" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-lg mx-4">
        <h2 class="text-xl font-semibold text-gray-800 mb-4">新增报名</h2>
        
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">选择课程</label>
            <select
              v-model="formData.courseId"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
            >
              <option v-for="course in courses" :key="course.id" :value="course.id">
                {{ course.title }}
              </option>
            </select>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">选择学员</label>
            <select
              v-model="formData.studentId"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
            >
              <option v-for="student in students" :key="student.id" :value="student.id">
                {{ student.name }} ({{ student.username }})
              </option>
            </select>
          </div>
        </div>
        
        <div class="flex justify-end space-x-3 mt-6">
          <button
            @click="showModal = false"
            class="px-4 py-2 border border-gray-200 text-gray-600 rounded-lg hover:bg-gray-50 transition-colors"
          >
            取消
          </button>
          <button
            @click="handleSubmit"
            class="px-4 py-2 bg-primary-500 text-white rounded-lg hover:bg-primary-600 transition-colors"
          >
            确认报名
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
