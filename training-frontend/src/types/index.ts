export interface LoginInfo {
  username: string
  password: string
}

export interface User {
  id: number
  username: string
  name: string
  token: string
}

export interface Course {
  id: number
  title: string
  description: string
  coverImage: string
  categoryId: number
  categoryName: string
  teacherId: number
  teacherName: string | null
  duration: number
  status: number
  totalStudents: number
  createTime: string
  updateTime: string
  chapters: Chapter[]
}

export interface Chapter {
  id: number
  courseId: number
  title: string
  sortOrder: number
  lessons: Lesson[]
}

export interface Lesson {
  id: number
  chapterId: number
  title: string
  type: number
  duration: number
  content: string
  videoUrl: string | null
  sortOrder: number
}

export interface CourseCategory {
  id: number
  name: string
  description: string
  createTime: string
}

export interface Exam {
  id: number
  title: string
  courseId: number
  courseTitle?: string
  duration: number
  totalScore: number
  passScore: number
  status: number
  createTime?: string
}

export interface Question {
  id: number
  examId: number
  type: number
  content: string
  options: string
  answer: string
  score: number
  sortOrder: number
}

export interface Student {
  id: number
  username: string
  name: string
  gender: number
  phone: string
  email: string
  status: number
  createTime: string
}

export interface CourseEnroll {
  id: number
  courseId: number
  courseTitle: string
  studentId: number
  studentName: string
  enrollTime: string
  status: number
  progress: number
  isCompleted: number
}

export interface LearningProgress {
  id: number
  studentId: number
  courseId: number
  courseTitle: string
  lessonId: number
  lessonTitle: string
  progress: number
  lastStudyTime: string
}

export interface ExamRecord {
  id: number
  examId: number
  examTitle: string
  studentId: number
  studentName: string
  score: number
  status: number
  submitTime: string
}

export interface Result<T = any> {
  code: number
  msg: string
  data: T
}

export interface PageResult<T = any> {
  code: number
  msg: string
  data: {
    list: T[]
    total: number
  }
}
