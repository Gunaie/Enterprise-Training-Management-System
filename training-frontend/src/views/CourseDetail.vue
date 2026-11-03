<script setup lang="ts">import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getCourseByIdApi } from '../api';
import { ArrowLeft, BookOpen, PlayCircle, Clock, Users, ChevronDown, ChevronRight } from 'lucide-vue-next';
import type { Course } from '../types';
const route = useRoute();
const router = useRouter();
const course = ref<Course | null>(null);
const expandedChapters = ref<number[]>([]);
onMounted(async () => {
 const id = Number(route.params.id);
 try {
 const response = await getCourseByIdApi(id);
 course.value = response.data;
 if (course.value.chapters.length > 0) {
 expandedChapters.value = [course.value.chapters[0].id];
 }
 }
 catch (error) {
 console.error('获取课程详情失败:', error);
 }
});
const toggleChapter = (chapterId: number) => {
 const index = expandedChapters.value.indexOf(chapterId);
 if (index === -1) {
 expandedChapters.value.push(chapterId);
 }
 else {
 expandedChapters.value.splice(index, 1);
 }
};
const formatDuration = (minutes: number) => {
 if (minutes >= 60) {
 const hours = Math.floor(minutes / 60);
 const mins = minutes % 60;
 return mins > 0 ? `${hours}小时${mins}分钟` : `${hours}小时`;
 }
 return `${minutes}分钟`;
};
const handleImageError = (e: Event) => {
 const target = e.target as HTMLImageElement;
 target.style.display = 'none';
};
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center">
      <button
        @click="router.back()"
        class="flex items-center text-gray-600 hover:text-gray-800 transition-colors"
      >
        <ArrowLeft class="w-5 h-5 mr-2" />
        返回课程列表
      </button>
    </div>
    
    <div v-if="course" class="bg-white rounded-xl shadow-sm overflow-hidden">
      <div class="relative h-64 bg-gradient-to-br from-primary-500 to-primary-700">
        <img
          :src="course.coverImage"
          :alt="course.title"
          class="w-full h-full object-cover opacity-80"
          @error="handleImageError"
        />
        <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent flex items-end p-8">
          <div>
            <span class="px-3 py-1 bg-white/20 text-white rounded-full text-sm font-medium mb-3 inline-block">
              {{ course.categoryName }}
            </span>
            <h1 class="text-3xl font-bold text-white">{{ course.title }}</h1>
          </div>
        </div>
      </div>
      
      <div class="p-8">
        <div class="flex items-center space-x-8 mb-6">
          <div class="flex items-center text-gray-600">
            <Clock class="w-5 h-5 mr-2 text-primary-500" />
            <span>{{ course.duration }}小时</span>
          </div>
          <div class="flex items-center text-gray-600">
            <Users class="w-5 h-5 mr-2 text-primary-500" />
            <span>{{ course.totalStudents }}人学习</span>
          </div>
          <div class="flex items-center text-gray-600">
            <BookOpen class="w-5 h-5 mr-2 text-primary-500" />
            <span>{{ course.chapters.length }}章节</span>
          </div>
        </div>
        
        <div class="mb-8">
          <h2 class="text-lg font-semibold text-gray-800 mb-2">课程介绍</h2>
          <p class="text-gray-600 leading-relaxed">{{ course.description }}</p>
        </div>
        
        <div>
          <h2 class="text-lg font-semibold text-gray-800 mb-4">课程目录</h2>
          
          <div class="space-y-3">
            <div
              v-for="chapter in course.chapters"
              :key="chapter.id"
              class="border border-gray-200 rounded-lg overflow-hidden"
            >
              <button
                @click="toggleChapter(chapter.id)"
                class="w-full flex items-center justify-between p-4 bg-gray-50 hover:bg-gray-100 transition-colors text-left"
              >
                <div class="flex items-center">
                  <div class="w-8 h-8 bg-primary-100 rounded-lg flex items-center justify-center mr-3">
                    <span class="text-primary-600 font-medium">{{ chapter.sortOrder }}</span>
                  </div>
                  <span class="font-medium text-gray-800">{{ chapter.title }}</span>
                </div>
                <ChevronDown
                  v-if="expandedChapters.includes(chapter.id)"
                  class="w-5 h-5 text-gray-400"
                />
                <ChevronRight v-else class="w-5 h-5 text-gray-400" />
              </button>
              
              <div v-if="expandedChapters.includes(chapter.id)" class="divide-y divide-gray-100">
                <div
                  v-for="lesson in chapter.lessons"
                  :key="lesson.id"
                  class="flex items-center justify-between p-4 hover:bg-gray-50 transition-colors"
                >
                  <div class="flex items-center">
                    <PlayCircle class="w-5 h-5 text-primary-500 mr-3" />
                    <span class="text-gray-700">{{ lesson.title }}</span>
                  </div>
                  <span class="text-sm text-gray-500">{{ formatDuration(lesson.duration) }}</span>
                </div>
                <div v-if="chapter.lessons.length === 0" class="p-4 text-center text-gray-400">
                  该章节暂无课时
                </div>
              </div>
            </div>
            
            <div v-if="course.chapters.length === 0" class="text-center py-8 text-gray-400">
              课程暂无章节
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
