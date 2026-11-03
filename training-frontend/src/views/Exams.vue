<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getExamsApi, deleteExamApi, getCoursesApi, createExamApi, updateExamApi } from '../api'
import { FileText, Plus, Edit, Trash2, Clock, Target } from 'lucide-vue-next'
import type { Exam, Course } from '../types'

const exams = ref<Exam[]>([])
const courses = ref<Course[]>([])
const showModal = ref(false)
const editingExam = ref<Exam | null>(null)
const confirmDeleteId = ref<number | null>(null)

const formData = ref({
  title: '',
  courseId: 0,
  duration: 60,
  totalScore: 100,
  passScore: 60,
  status: 1
})

onMounted(async () => {
  await loadExams()
  await loadCourses()
})

const loadExams = async () => {
  try {
    const response = await getExamsApi()
    exams.value = response.data
  } catch (error) {
    console.error('获取考试失败:', error)
  }
}

const loadCourses = async () => {
  try {
    const response = await getCoursesApi()
    courses.value = response.data
  } catch (error) {
    console.error('获取课程失败:', error)
  }
}

const handleAdd = () => {
  editingExam.value = null
  formData.value = {
    title: '',
    courseId: courses.value[0]?.id || 0,
    duration: 60,
    totalScore: 100,
    passScore: 60,
    status: 1
  }
  showModal.value = true
}

const handleEdit = (exam: Exam) => {
  editingExam.value = exam
  formData.value = {
    title: exam.title,
    courseId: exam.courseId,
    duration: exam.duration,
    totalScore: exam.totalScore,
    passScore: exam.passScore,
    status: exam.status
  }
  showModal.value = true
}

const handleDelete = async (id: number) => {
  try {
    await deleteExamApi(id)
    await loadExams()
    confirmDeleteId.value = null
  } catch (error) {
    console.error('删除考试失败:', error)
  }
}

const handleSubmit = async () => {
  try {
    if (editingExam.value) {
      await updateExamApi(editingExam.value.id, formData.value)
    } else {
      await createExamApi(formData.value)
    }
    showModal.value = false
    await loadExams()
  } catch (error) {
    console.error('保存考试失败:', error)
  }
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-bold text-gray-800">考试管理</h1>
        <p class="text-gray-500 mt-1">管理考核考试的创建、编辑和删除</p>
      </div>
      <button
        @click="handleAdd"
        class="flex items-center px-4 py-2 bg-primary-500 text-white rounded-lg hover:bg-primary-600 transition-colors"
      >
        <Plus class="w-5 h-5 mr-2" />
        新增考试
      </button>
    </div>
    
    <div class="bg-white rounded-xl shadow-sm overflow-hidden">
      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">考试名称</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">所属课程</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">时长</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">总分/及格分</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">状态</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">创建时间</th>
              <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr
              v-for="exam in exams"
              :key="exam.id"
              class="hover:bg-gray-50 transition-colors"
            >
              <td class="px-6 py-4">
                <div class="flex items-center">
                  <FileText class="w-5 h-5 text-primary-500 mr-3" />
                  <span class="font-medium text-gray-800">{{ exam.title }}</span>
                </div>
              </td>
              <td class="px-6 py-4 text-gray-600">{{ exam.courseTitle }}</td>
              <td class="px-6 py-4">
                <span class="flex items-center text-gray-600">
                  <Clock class="w-4 h-4 mr-1" />
                  {{ exam.duration }}分钟
                </span>
              </td>
              <td class="px-6 py-4">
                <span class="flex items-center text-gray-600">
                  <Target class="w-4 h-4 mr-1" />
                  {{ exam.totalScore }}/{{ exam.passScore }}分
                </span>
              </td>
              <td class="px-6 py-4">
                <span :class="['px-3 py-1 rounded-full text-xs font-medium', exam.status === 1 ? 'bg-green-100 text-green-600' : 'bg-gray-100 text-gray-600']">
                  {{ exam.status === 1 ? '可用' : '禁用' }}
                </span>
              </td>
              <td class="px-6 py-4 text-gray-500 text-sm">
                {{ exam.createTime ? new Date(exam.createTime).toLocaleDateString() : '' }}
              </td>
              <td class="px-6 py-4 text-right">
                <div class="flex items-center justify-end space-x-2">
                  <button
                    @click="handleEdit(exam)"
                    class="p-2 text-gray-500 hover:bg-gray-100 rounded-lg transition-colors"
                    title="编辑"
                  >
                    <Edit class="w-4 h-4" />
                  </button>
                  <button
                    @click="confirmDeleteId = exam.id"
                    class="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-colors"
                    title="删除"
                  >
                    <Trash2 class="w-4 h-4" />
                  </button>
                </div>
              </td>
            </tr>
            
            <tr v-if="exams.length === 0">
              <td colspan="7" class="px-6 py-12 text-center">
                <FileText class="w-16 h-16 text-gray-300 mx-auto mb-4" />
                <p class="text-gray-500">暂无考试数据</p>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    
    <div v-if="showModal" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-lg mx-4">
        <h2 class="text-xl font-semibold text-gray-800 mb-4">
          {{ editingExam ? '编辑考试' : '新增考试' }}
        </h2>
        
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">考试名称</label>
            <input
              v-model="formData.title"
              type="text"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              placeholder="请输入考试名称"
            />
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">所属课程</label>
            <select
              v-model="formData.courseId"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
            >
              <option v-for="course in courses" :key="course.id" :value="course.id">
                {{ course.title }}
              </option>
            </select>
          </div>
          
          <div class="grid grid-cols-3 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">时长(分钟)</label>
              <input
                v-model.number="formData.duration"
                type="number"
                class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">总分</label>
              <input
                v-model.number="formData.totalScore"
                type="number"
                class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">及格分</label>
              <input
                v-model.number="formData.passScore"
                type="number"
                class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              />
            </div>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">状态</label>
            <select
              v-model="formData.status"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
            >
              <option value="1">可用</option>
              <option value="0">禁用</option>
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
            保存
          </button>
        </div>
      </div>
    </div>
    
    <div v-if="confirmDeleteId" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-sm mx-4">
        <h2 class="text-xl font-semibold text-gray-800 mb-2">确认删除</h2>
        <p class="text-gray-500 mb-6">确定要删除这个考试吗？此操作不可撤销。</p>
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
