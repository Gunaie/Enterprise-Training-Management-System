<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getStudentsApi, deleteStudentApi, createStudentApi, updateStudentApi } from '../api'
import { Users, Plus, Edit, Trash2, Phone, Mail } from 'lucide-vue-next'
import type { Student } from '../types'

const students = ref<Student[]>([])
const showModal = ref(false)
const editingStudent = ref<Student | null>(null)
const confirmDeleteId = ref<number | null>(null)

const formData = ref({
  username: '',
  name: '',
  gender: 1,
  phone: '',
  email: '',
  status: 1
})

onMounted(async () => {
  await loadStudents()
})

const loadStudents = async () => {
  try {
    const response = await getStudentsApi()
    students.value = response.data
  } catch (error) {
    console.error('获取学员失败:', error)
  }
}

const handleAdd = () => {
  editingStudent.value = null
  formData.value = {
    username: '',
    name: '',
    gender: 1,
    phone: '',
    email: '',
    status: 1
  }
  showModal.value = true
}

const handleEdit = (student: Student) => {
  editingStudent.value = student
  formData.value = {
    username: student.username,
    name: student.name,
    gender: student.gender,
    phone: student.phone,
    email: student.email,
    status: student.status
  }
  showModal.value = true
}

const handleDelete = async (id: number) => {
  try {
    await deleteStudentApi(id)
    await loadStudents()
    confirmDeleteId.value = null
  } catch (error) {
    console.error('删除学员失败:', error)
  }
}

const handleSubmit = async () => {
  try {
    if (editingStudent.value) {
      await updateStudentApi(editingStudent.value.id, formData.value)
    } else {
      await createStudentApi(formData.value)
    }
    showModal.value = false
    await loadStudents()
  } catch (error) {
    console.error('保存学员失败:', error)
  }
}

const getGenderText = (gender: number) => {
  return gender === 1 ? '男' : '女'
}
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-bold text-gray-800">学员管理</h1>
        <p class="text-gray-500 mt-1">管理培训学员的信息</p>
      </div>
      <button
        @click="handleAdd"
        class="flex items-center px-4 py-2 bg-primary-500 text-white rounded-lg hover:bg-primary-600 transition-colors"
      >
        <Plus class="w-5 h-5 mr-2" />
        新增学员
      </button>
    </div>
    
    <div class="bg-white rounded-xl shadow-sm overflow-hidden">
      <div class="overflow-x-auto">
        <table class="w-full">
          <thead class="bg-gray-50">
            <tr>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">姓名</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">用户名</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">性别</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">电话</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">邮箱</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">状态</th>
              <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">创建时间</th>
              <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">操作</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-100">
            <tr
              v-for="student in students"
              :key="student.id"
              class="hover:bg-gray-50 transition-colors"
            >
              <td class="px-6 py-4">
                <div class="flex items-center">
                  <div class="w-10 h-10 bg-primary-100 rounded-full flex items-center justify-center mr-3">
                    <span class="text-primary-600 font-medium">{{ student.name[0] }}</span>
                  </div>
                  <span class="font-medium text-gray-800">{{ student.name }}</span>
                </div>
              </td>
              <td class="px-6 py-4 text-gray-600">{{ student.username }}</td>
              <td class="px-6 py-4 text-gray-600">{{ getGenderText(student.gender) }}</td>
              <td class="px-6 py-4">
                <span class="flex items-center text-gray-600">
                  <Phone class="w-4 h-4 mr-1" />
                  {{ student.phone }}
                </span>
              </td>
              <td class="px-6 py-4">
                <span class="flex items-center text-gray-600">
                  <Mail class="w-4 h-4 mr-1" />
                  {{ student.email }}
                </span>
              </td>
              <td class="px-6 py-4">
                <span :class="['px-3 py-1 rounded-full text-xs font-medium', student.status === 1 ? 'bg-green-100 text-green-600' : 'bg-gray-100 text-gray-600']">
                  {{ student.status === 1 ? '正常' : '禁用' }}
                </span>
              </td>
              <td class="px-6 py-4 text-gray-500 text-sm">
                {{ new Date(student.createTime).toLocaleDateString() }}
              </td>
              <td class="px-6 py-4 text-right">
                <div class="flex items-center justify-end space-x-2">
                  <button
                    @click="handleEdit(student)"
                    class="p-2 text-gray-500 hover:bg-gray-100 rounded-lg transition-colors"
                    title="编辑"
                  >
                    <Edit class="w-4 h-4" />
                  </button>
                  <button
                    @click="confirmDeleteId = student.id"
                    class="p-2 text-red-500 hover:bg-red-50 rounded-lg transition-colors"
                    title="删除"
                  >
                    <Trash2 class="w-4 h-4" />
                  </button>
                </div>
              </td>
            </tr>
            
            <tr v-if="students.length === 0">
              <td colspan="8" class="px-6 py-12 text-center">
                <Users class="w-16 h-16 text-gray-300 mx-auto mb-4" />
                <p class="text-gray-500">暂无学员数据</p>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    
    <div v-if="showModal" class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-lg mx-4">
        <h2 class="text-xl font-semibold text-gray-800 mb-4">
          {{ editingStudent ? '编辑学员' : '新增学员' }}
        </h2>
        
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">用户名</label>
            <input
              v-model="formData.username"
              type="text"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              placeholder="请输入用户名"
            />
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">姓名</label>
            <input
              v-model="formData.name"
              type="text"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              placeholder="请输入姓名"
            />
          </div>
          
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">性别</label>
              <select
                v-model="formData.gender"
                class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              >
                <option value="1">男</option>
                <option value="0">女</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">状态</label>
              <select
                v-model="formData.status"
                class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              >
                <option value="1">正常</option>
                <option value="0">禁用</option>
              </select>
            </div>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">电话</label>
            <input
              v-model="formData.phone"
              type="tel"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              placeholder="请输入电话"
            />
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">邮箱</label>
            <input
              v-model="formData.email"
              type="email"
              class="w-full px-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none"
              placeholder="请输入邮箱"
            />
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
        <p class="text-gray-500 mb-6">确定要删除这个学员吗？此操作不可撤销。</p>
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
