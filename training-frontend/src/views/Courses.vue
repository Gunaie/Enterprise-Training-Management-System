<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getCoursesApi, deleteCourseApi, getCourseCategoriesApi } from '../api'
import { BookOpen, Plus, Edit, Trash2, Eye, PlayCircle } from 'lucide-vue-next'
import type { Course, CourseCategory } from '../types'

const courses = ref<Course[]>([])
const categories = ref<CourseCategory[]>([])
const categoryFilter = ref(0)
const showModal = ref(false)
const editingCourse = ref<Course | null>(null)
const confirmDeleteId = ref<number | null>(null)

const formData = ref({
  title: '',
  description: '',
  coverImage: '',
  categoryId: 1,
  teacherId: 1,
  duration: 0,
  status: 1
})

onMounted(async () => {
  await loadCourses()
  await loadCategories()
})

const loadCourses = async () => {
  try {
    const response = await getCoursesApi()
    courses.value = response.data
  } catch (error) {
    console.error('获取课程失败:', error)
  }
}

const loadCategories = async () => {
  try {
    const response = await getCourseCategoriesApi()
    categories.value = response.data
  } catch (error) {
    console.error('获取分类失败:', error)
  }
}

const filteredCourses = () => {
  if (categoryFilter.value === 0) return courses.value
  return courses.value.filter(c => c.categoryId === categoryFilter.value)
}

const handleAdd = () => {
  editingCourse.value = null
  formData.value = {
    title: '',
    description: '',
    coverImage: '',
    categoryId: 1,
    teacherId: 1,
    duration: 0,
    status: 1
  }
  showModal.value = true
}

const handleEdit = (course: Course) => {
  editingCourse.value = course
  formData.value = {
    title: course.title,
    description: course.description,
    coverImage: course.coverImage,
    categoryId: course.categoryId,
    teacherId: course.teacherId,
    duration: course.duration,
    status: course.status
  }
  showModal.value = true
}

const handleDelete = async (id: number) => {
  try {
    await deleteCourseApi(id)
    await loadCourses()
    confirmDeleteId.value = null
  } catch (error) {
    console.error('删除课程失败:', error)
  }
}

const handleSubmit = async () => {
  try {
    if (editingCourse.value) {
      await updateCourseApi(editingCourse.value.id, formData.value)
    } else {
      await createCourseApi(formData.value)
    }
    showModal.value = false
    await loadCourses()
  } catch (error) {
    console.error('保存课程失败:', error)
  }
}

const createCourseApi = async (data: any) => {
  const api = await import('../api')
  return api.createCourseApi(data)
}

const updateCourseApi = async (id: number, data: any) => {
  const api = await import('../api')
  return api.updateCourseApi(id, data)
}

const handleImageError = (e: Event) => {
  const target = e.target as HTMLImageElement
  target.style.display = 'none'
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-bold text-gray-800">课程管理</h1>
        <p class="text-gray-500 mt-1">管理培训课程的创建、编辑和删除</p>
      </div>
      <button
        @click="handleAdd"
        class="flex items-center px-4 py-2 bg-primary-500 text-white rounded-lg hover:bg-primary-600 transition-colors"
      >
        <Plus class="w-5 h-5 mr-2" />
        新增课程
      </button>
    </div>
    
    <div class="bg-white rounded-xl shadow-sm">
      <div class="p-4 border-b border-gray-200">
        <div class="flex items-center">
          <select
            v-model="categoryFilter"
            class="px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
          >
            <option value="0">全部分类</option>
            <option v-for="cat in categories" :key="cat.id" :value="cat.id">
              {{ cat.name }}
            </option>
          </select>
        </div>
      </div>
      
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 p-6">
        <div
          v-for="course in filteredCourses()"
          :key="course.id"
          class="bg-gray-50 rounded-xl overflow-hidden hover:shadow-md transition-shadow"
        >
          <div class="h-40 bg-gradient-to-br from-primary-100 to-primary-200 relative">
            <img
              :src="course.coverImage"
              :alt="course.title"
              class="w-full h-full object-cover"
              @error="handleImageError"
            />
            <span :class="['absolute top-3 right-3 px-3 py-1 rounded-full text-xs font-medium', course.status === 1 ? 'bg-green-500 text-white' : 'bg-gray-400 text-white']">
              {{ course.status === 1 ? '已发布' : '草稿' }}
            </span>
          </div>
          
          <div class="p-4">
            <div class="flex items-center justify-between mb-2">
              <h3 class="font-semibold text-gray-800">{{ course.title }}</h3>
              <span class="text-xs text-gray-500 bg-gray-200 px-2 py-1 rounded">{{ course.categoryName }}</span>
            </div>
            
            <p class="text-sm text-gray-500 mb-4 line-clamp-2">{{ course.description }}</p>
            
            <div class="flex items-center justify-between text-sm text-gray-500 mb-4">
              <span class="flex items-center">
                <PlayCircle class="w-4 h-4 mr-1" />
                {{ course.duration }}小时
              </span>
              <span class="flex items-center">
                <BookOpen class="w-4 h-4 mr-1" />
                {{ course.totalStudents }}人学习
              </span>
            </div>
            
            <div class="flex items-center justify-between">
              <span class="text-xs text-gray-400">{{ new Date(course.createTime).toLocaleDateString() }}</span>
              <div class="flex items-center space-x-2">
                <router-link
                  :to="`/courses/${course.id}`"
                  class="p-2 text-blue-500 hover:bg-blue-50 rounded-lg transition-colors"
                  title="查看详情"
                >
                  <Eye class="w-4 h-4" />
                </router-link>
                <button
                  @click="handleEdit(course)"
                  class="p-2 text-gray-500 hover:bg-gray-100 rounded-lg transition-colors"
                  title="编辑"
                >
                  <Edit class="w-4 h-4" />
                </button>
                <button
                  @click="confirmDeleteId = course.id"
                  class="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-colors"
                  title="删除"
                >
                  <Trash2 class="w-4 h-4" />
                </button>
              </div>
            </div>
          </div>
        </div>
        
        <div
          v-if="filteredCourses().length === 0"
          class="col-span-full text-center py-12"
        >
          <BookOpen class="w-16 h-16 text-gray-300 mx-auto mb-4" />
          <p class="text-gray-500">暂无课程数据</p>
        </div>
      </div>
    </div>
    
    <div v-if="showModal" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-lg mx-4">
        <h2 class="text-xl font-semibold text-gray-800 mb-4">
          {{ editingCourse ? '编辑课程' : '新增课程' }}
        </h2>
        
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">课程名称</label>
            <input
              v-model="formData.title"
              type="text"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              placeholder="请输入课程名称"
            />
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">课程描述</label>
            <textarea
              v-model="formData.description"
              rows="3"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              placeholder="请输入课程描述"
            ></textarea>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">分类</label>
            <select
              v-model="formData.categoryId"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
            >
              <option v-for="cat in categories" :key="cat.id" :value="cat.id">
                {{ cat.name }}
              </option>
            </select>
          </div>
          
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">时长(小时)</label>
              <input
                v-model.number="formData.duration"
                type="number"
                class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">状态</label>
              <select
                v-model="formData.status"
                class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              >
                <option value="1">已发布</option>
                <option value="0">草稿</option>
              </select>
            </div>
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
            保存
          </button>
        </div>
      </div>
    </div>
    
    <div v-if="confirmDeleteId" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-sm mx-4">
        <h2 class="text-xl font-semibold text-gray-800 mb-2">确认删除</h2>
        <p class="text-gray-500 mb-6">确定要删除这个课程吗？此操作不可撤销。</p>
        <div class="flex justify-end space-x-3">
          <button
            @click="confirmDeleteId = null"
            class="px-4 py-2 border border-gray-200 text-gray-600 rounded-lg hover:bg-gray-50 transition-colors"
          >
            取消
          </button>
          <button
            @click="handleDelete(confirmDeleteId!)"
            class="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition-colors"
          >
            删除
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
